"""
build_embeddings.py

One-time build step: generates an embedding for every chunk in
chunks.jsonl using the locally-running qwen3-embedding:0.6b model via
Ollama, and stores them in a persistent ChromaDB collection so they don't
need to be regenerated every time you run the harness.

Run this AFTER build_index.py. Re-run it any time chunks.jsonl changes
(each run wipes and rebuilds the collection from scratch, so it always
reflects the current chunks.jsonl exactly).

This is the slow step, since it's CPU-only inference through Ollama --
expect it to take a while depending on total chunk count. Based on your
earlier benchmarking (a coding prompt took ~50 seconds on this hardware),
budget real time for this; it's a good one to kick off and step away from.

Requires: pip install chromadb ollama --break-system-packages

NOTE ON THE OLLAMA API: this uses ollama.embeddings() (singular, one text
at a time), which has been the standard call in the `ollama` Python
package for a long time. If your installed version instead expects the
newer batch-capable ollama.embed(model=..., input=...) call (plural), swap
the embed() function body for:
    return ollama.embed(model=EMBED_MODEL, input=text)["embeddings"][0]
Run `python3 -c "import ollama; help(ollama.embeddings)"` first if you hit
an AttributeError here, to check which one your installed version has.
"""

import json
import ollama
import chromadb

CHUNKS_PATH = "chunks.jsonl"
CHROMA_PATH = "./chroma_db"
COLLECTION_NAME = "codebase_chunks"
EMBED_MODEL = "qwen3-embedding:0.6b"
BATCH_SIZE = 25


def load_chunks(path=CHUNKS_PATH):
    records = []
    with open(path, encoding="utf-8") as f:
        for line in f:
            records.append(json.loads(line))
    return records


def embed(text):
    response = ollama.embeddings(
        model=EMBED_MODEL,
        prompt=text,
        options={"num_ctx": 16384},  # explicit, rather than trusting Ollama's default --
                                     # this is what caused the "input length exceeds
                                     # context length" error before the chunker fix
    )
    return response["embedding"]


def main():
    records = load_chunks()
    print(f"Loaded {len(records)} chunks from {CHUNKS_PATH}")

    client = chromadb.PersistentClient(path=CHROMA_PATH)

    # Wipe and rebuild each run so stale chunks never linger after edits.
    try:
        client.delete_collection(COLLECTION_NAME)
    except Exception:
        pass
    collection = client.create_collection(COLLECTION_NAME)

    for start in range(0, len(records), BATCH_SIZE):
        batch = records[start:start + BATCH_SIZE]
        ids = [r["chunk_id"] for r in batch]
        documents = [r["text"] for r in batch]
        metadatas = [
            {
                "project": r["project"],
                "file_path": r["file_path"],
                "start_line": r["start_line"],
                "end_line": r["end_line"],
            }
            for r in batch
        ]
        embeddings = [embed(text) for text in documents]

        collection.add(
            ids=ids,
            embeddings=embeddings,
            documents=documents,
            metadatas=metadatas,
        )
        print(f"  embedded {min(start + BATCH_SIZE, len(records))}/{len(records)} chunks")

    print(f"\nDone. {len(records)} chunks embedded into '{COLLECTION_NAME}' at {CHROMA_PATH}")


if __name__ == "__main__":
    main()