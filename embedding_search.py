"""
embedding_search.py

Semantic (embedding) retrieval over the persisted ChromaDB collection
built by build_embeddings.py. Run build_index.py then build_embeddings.py
before using this.

Requires: pip install chromadb ollama --break-system-packages
"""

import ollama
import chromadb

CHROMA_PATH = "./chroma_db"
COLLECTION_NAME = "codebase_chunks"
EMBED_MODEL = "qwen3-embedding:0.6b"


def embed(text):
    # See the note in build_embeddings.py if this raises an AttributeError --
    # your installed ollama package may expect ollama.embed() instead.
    response = ollama.embeddings(
        model=EMBED_MODEL,
        prompt=text,
        options={"num_ctx": 16384},
    )
    return response["embedding"]


class EmbeddingIndex:
    def __init__(self, path=CHROMA_PATH, collection_name=COLLECTION_NAME):
        client = chromadb.PersistentClient(path=path)
        self.collection = client.get_collection(collection_name)

    def search(self, query, k=5):
        query_embedding = embed(query)
        results = self.collection.query(
            query_embeddings=[query_embedding],
            n_results=k,
        )

        out = []
        for i in range(len(results["ids"][0])):
            meta = results["metadatas"][0][i]
            distance = results["distances"][0][i]
            out.append({
                "chunk_id": results["ids"][0][i],
                "project": meta["project"],
                "file_path": meta["file_path"],
                "start_line": meta["start_line"],
                "end_line": meta["end_line"],
                "text": results["documents"][0][i],
                "distance": distance,                  # raw Chroma distance, kept for reference (lower = more similar)
                "relevance": 1 / (1 + distance),        # converted so higher = better match, matching bm25_search.py
            })
        return out


if __name__ == "__main__":
    # Quick manual sanity check -- try a paraphrased query (one that doesn't
    # share exact keywords with the target code) and see whether the right
    # file still surfaces near the top.
    index = EmbeddingIndex()
    for r in index.search("figure out how much money is left in a category", k=3):
        print(f"relevance={r['relevance']:.4f}  {r['project']}/{r['file_path']}  "
              f"lines {r['start_line']}-{r['end_line']}")