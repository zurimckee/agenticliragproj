"""
run_harness.py

Runs every task in manifest.json through all three retrieval conditions
(none / bm25 / embedding) against the local qwen2.5-coder:7b model, with a
human-judged retry loop (cap: 2 retries, so 3 attempts max per task per
condition) matching the retry protocol you defined.

IMPORTANT: this is NOT a walk-away job. After every generation, you'll be
shown the response and asked whether to retry it. That's inherent to
judging retries by hand rather than automatically -- there's no way around
needing you present for all 45 task x condition runs.

It IS resumable, though: results already written to results.jsonl are
skipped on a re-run, so you can do this across multiple sessions (e.g. all
of baseline today, bm25 tomorrow) rather than in one long sitting.

Loops condition-major (all 15 tasks under one condition, then the next)
rather than task-major, specifically so you're not reviewing all three
conditions' answers to the same task back-to-back -- that would make it too
easy to unconsciously compare them against each other while deciding
whether to retry, which is exactly the kind of bias the retry protocol is
supposed to avoid.

Requires: pip install ollama --break-system-packages
(bm25_search.py / embedding_search.py's own dependencies are needed too)
"""

import json
import os
import time
from datetime import datetime, timezone

import ollama

from bm25_search import BM25Index
from embedding_search import EmbeddingIndex

MANIFEST_PATH = "manifest.json"
RESULTS_PATH = "results.jsonl"
CODER_MODEL = "qwen2.5-coder:7b"

TOP_K = 5
MAX_ATTEMPTS = 3          # 1 initial attempt + 2 retries, per your cap
NUM_CTX = 8192            # explicit, not Ollama's default -- must be big enough
                          # to hold TOP_K retrieved chunks + the task prompt
                          # without silent truncation (see your earlier num_ctx
                          # investigation during onboarding)
TEMPERATURE = 0.8         # fixed explicitly and used for EVERY condition,
                          # including baseline -- retries only mean anything
                          # if generation isn't deterministic, and every
                          # condition needs the same sampling settings to be
                          # a fair comparison
KEEP_ALIVE = "30m"        # avoid model unload/reload thrashing between calls


def load_manifest(path=MANIFEST_PATH):
    with open(path, encoding="utf-8") as f:
        return json.load(f)


def load_task_prompt_text(task):
    with open(task["prompt-path"], encoding="utf-8") as f:
        return f.read()


def build_prompt(task_prompt_text, retrieved_chunks):
    if not retrieved_chunks:
        return task_prompt_text

    parts = ["Below is code from the existing codebase that may be relevant "
             "to this task.\n"]
    for chunk in retrieved_chunks:
        parts.append(f"--- {chunk['project']}/{chunk['file_path']} "
                      f"(lines {chunk['start_line']}-{chunk['end_line']}) ---")
        parts.append(chunk["text"])
        parts.append("")
    parts.append("Task:")
    parts.append(task_prompt_text)
    return "\n".join(parts)


def generate(prompt):
    start = time.time()
    response = ollama.generate(
        model=CODER_MODEL,
        prompt=prompt,
        options={"num_ctx": NUM_CTX, "temperature": TEMPERATURE},
        keep_alive=KEEP_ALIVE,
    )
    elapsed = time.time() - start
    return response["response"], elapsed


def get_target_file_hits(task, retrieved_chunks):
    """Which of the task's target-files (if any) actually showed up among
    the retrieved chunks, restricted to the task's own project -- a quick
    programmatic check of retrieval relevance against your manifest."""
    target_files = set(task.get("target-files", []))
    if not target_files:
        return []
    hits = set()
    for chunk in retrieved_chunks:
        if chunk["project"] == task["src-project"] and chunk["file_path"] in target_files:
            hits.add(chunk["file_path"])
    return sorted(hits)


def already_done(task_id, condition, results_path=RESULTS_PATH):
    if not os.path.exists(results_path):
        return False
    with open(results_path, encoding="utf-8") as f:
        for line in f:
            record = json.loads(line)
            if record["task_id"] == task_id and record["condition"] == condition:
                return True
    return False


def append_result(record, results_path=RESULTS_PATH):
    with open(results_path, "a", encoding="utf-8") as f:
        f.write(json.dumps(record) + "\n")


def run_one(task, condition, index):
    task_prompt_text = load_task_prompt_text(task)
    retrieved = index.search(task["prompt"], k=TOP_K) if index else []
    prompt = build_prompt(task_prompt_text, retrieved)

    attempts = []
    hit_retry_cap = False

    for attempt_num in range(1, MAX_ATTEMPTS + 1):
        response_text, elapsed = generate(prompt)

        print(f"\n{'=' * 70}")
        print(f"Task {task['task-id']} [{task['difficulty']}] -- {condition} -- "
              f"attempt {attempt_num}/{MAX_ATTEMPTS}")
        print(f"Prompt: {task['prompt']}")
        print(f"({elapsed:.1f}s)")
        print("-" * 70)
        print(response_text)
        print("=" * 70)

        attempts.append({
            "attempt": attempt_num,
            "response": response_text,
            "elapsed_sec": round(elapsed, 1),
        })

        if attempt_num < MAX_ATTEMPTS:
            answer = input("Retry? Thin/inaccurate per your checklist -- [y/N]: ").strip().lower()
            attempts[-1]["retry_requested"] = (answer == "y")
            if answer != "y":
                break
        else:
            answer = input("At retry cap (no more retries left). "
                            "Would you have retried anyway? [y/N]: ").strip().lower()
            attempts[-1]["retry_requested"] = (answer == "y")
            hit_retry_cap = (answer == "y")

    target_file_hits = get_target_file_hits(task, retrieved)

    return {
        "task_id": task["task-id"],
        "condition": condition,
        "timestamp": datetime.now(timezone.utc).isoformat(),
        "attempts": attempts,
        "attempts_used": len(attempts),
        "hit_retry_cap": hit_retry_cap,
        "final_response": attempts[-1]["response"],
        "retrieved_chunks": [
            {
                "project": c["project"],
                "file_path": c["file_path"],
                "start_line": c["start_line"],
                "end_line": c["end_line"],
                "relevance": c["relevance"],
            }
            for c in retrieved
        ],
        "target_file_hits": target_file_hits,
        "succeeded": None,   # filled in later during separate formal grading --
                              # deliberately not decided here, in the moment
    }


def run_condition(condition, index, tasks):
    print(f"\n\n########## CONDITION: {condition} ##########")
    for task in tasks:
        if already_done(task["task-id"], condition):
            print(f"Task {task['task-id']} / {condition}: already done, skipping.")
            continue
        result = run_one(task, condition, index)
        append_result(result)
        print(f"Logged task {task['task-id']} / {condition} "
              f"({result['attempts_used']} attempt(s)).")


def main():
    tasks = load_manifest()

    conditions = [
        ("baseline", None),
        ("bm25", BM25Index()),
        ("embedding", EmbeddingIndex()),
    ]

    for condition_name, index in conditions:
        run_condition(condition_name, index, tasks)

    print("\nAll conditions complete. Results in", RESULTS_PATH)
    print("Next: run the separate grading pass to fill in 'succeeded' for each record.")


if __name__ == "__main__":
    main()
