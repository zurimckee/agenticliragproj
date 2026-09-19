"""
bm25_search.py

BM25 (keyword) retrieval over the chunks in chunks.jsonl.

Rebuilds the index in memory on load rather than persisting a separate
BM25 index file -- at this corpus size (a few hundred chunks), that takes
well under a second, so there's nothing to gain from persisting it and it
sidesteps any pickle version-compatibility headaches down the line.

Requires: pip install rank_bm25 --break-system-packages
"""

import json
import re
from rank_bm25 import BM25Okapi

CHUNKS_PATH = "chunks.jsonl"


def tokenize(text):
    return re.findall(r"[a-zA-Z0-9_]+", text.lower())


def load_chunks(path=CHUNKS_PATH):
    records = []
    with open(path, encoding="utf-8") as f:
        for line in f:
            records.append(json.loads(line))
    return records


class BM25Index:
    def __init__(self, path=CHUNKS_PATH):
        self.records = load_chunks(path)
        tokenized_corpus = [tokenize(r["text"]) for r in self.records]
        self.bm25 = BM25Okapi(tokenized_corpus)

    def search(self, query, k=5):
        tokenized_query = tokenize(query)
        scores = self.bm25.get_scores(tokenized_query)
        ranked = sorted(range(len(scores)), key=lambda i: scores[i], reverse=True)

        results = []
        for i in ranked[:k]:
            r = dict(self.records[i])
            r["score"] = float(scores[i])       # raw BM25 score, kept for reference
            r["relevance"] = float(scores[i])   # BM25 is already higher-is-better
            results.append(r)
        return results


if __name__ == "__main__":
    # Quick manual sanity check -- try a query and eyeball what comes back
    # before wiring this into the harness.
    index = BM25Index()
    for r in index.search("budget category over limit", k=3):
        print(f"relevance={r['relevance']:.2f}  {r['project']}/{r['file_path']}  "
              f"lines {r['start_line']}-{r['end_line']}")
