"""
summarize_results.py

Reads results.jsonl (from run_harness.py) + manifest.json and prints the
per-condition summary table: success rate, average retries, average time.

Also writes the same tables to results_summary.md so they can be committed
alongside the raw results.

Definitions (keep these consistent with the write-up):
  - Success rate : records with succeeded == true / records graded.
                   Ungraded records (succeeded == null) are excluded and
                   reported separately, never counted as fails.
  - Retries      : attempts_used - 1 (0, 1, or 2). Counted for every run,
                   regardless of whether the final answer passed.
  - Time         : sum of elapsed_sec across all attempts for a task
                   (model generation time only; excludes your review time).
  - Target hit   : retrieved chunks included at least one of the task's
                   target-files (bm25 / embedding only).

Usage:
  python summarize_results.py
  python summarize_results.py --results results.jsonl --manifest manifest.json --out results_summary.md
"""

import argparse
import json
from collections import defaultdict

CONDITIONS = ["baseline", "bm25", "embedding"]
TOTAL_TASKS = 15


def load_results(path):
    records = []
    with open(path, encoding="utf-8") as f:
        for line in f:
            line = line.strip()
            if line:
                records.append(json.loads(line))
    return records


def load_manifest(path):
    with open(path, encoding="utf-8") as f:
        return {t["task-id"]: t for t in json.load(f)}


def task_time(record):
    return sum(a.get("elapsed_sec", 0) for a in record["attempts"])


def fmt_pct(num, den):
    return f"{num}/{den} ({num / den:.0%})" if den else "n/a"


def fmt_avg(values, unit=""):
    return f"{sum(values) / len(values):.2f}{unit}" if values else "n/a"


def summarize(records, condition=None):
    """Stats for a group of records."""
    graded = [r for r in records if r.get("succeeded") is not None]
    passed = [r for r in graded if r["succeeded"] is True]
    retries = [r["attempts_used"] - 1 for r in records]
    times = [task_time(r) for r in records]
    cap_hits = sum(1 for r in records if r.get("hit_retry_cap"))

    stats = {
        "runs": len(records),
        "ungraded": len(records) - len(graded),
        "success": fmt_pct(len(passed), len(graded)),
        "avg_retries": fmt_avg(retries),
        "avg_time": fmt_avg(times, " s"),
        "cap_hits": cap_hits,
    }

    if condition and condition != "baseline":
        with_targets = [r for r in records if r.get("target_file_hits") is not None]
        hits = sum(1 for r in with_targets if r["target_file_hits"])
        stats["target_hit"] = fmt_pct(hits, len(with_targets))
    else:
        stats["target_hit"] = "—"
    return stats


def main_table(by_condition):
    lines = [
        "| Method | Runs | Graded | Success rate | Avg. retries | Avg. time / task | Hit retry cap | Retrieved target file |",
        "|---|---|---|---|---|---|---|---|",
    ]
    for cond in CONDITIONS:
        s = summarize(by_condition.get(cond, []), cond)
        graded = s["runs"] - s["ungraded"]
        lines.append(
            f"| {cond} | {s['runs']}/{TOTAL_TASKS} | {graded}/{s['runs']} | "
            f"{s['success']} | {s['avg_retries']} | {s['avg_time']} | "
            f"{s['cap_hits']} | {s['target_hit']} |"
        )
    return "\n".join(lines)


def breakdown_table(by_condition, manifest, field, label):
    values = sorted({t[field] for t in manifest.values()},
                    key=lambda v: ["easy", "medium", "hard"].index(v)
                    if v in ("easy", "medium", "hard") else v)
    lines = [
        f"| {label} | " + " | ".join(CONDITIONS) + " |",
        "|---|" + "---|" * len(CONDITIONS),
    ]
    for v in values:
        row = [v]
        for cond in CONDITIONS:
            group = [r for r in by_condition.get(cond, [])
                     if manifest.get(r["task_id"], {}).get(field) == v]
            s = summarize(group)
            row.append(f"{s['success']}, {s['avg_retries']} retries")
        lines.append("| " + " | ".join(row) + " |")
    return "\n".join(lines)


def build_report(records, manifest):
    by_condition = defaultdict(list)
    for r in records:
        by_condition[r["condition"]].append(r)

    total_ungraded = sum(1 for r in records if r.get("succeeded") is None)
    out = ["# Results Summary", ""]
    out.append(f"Runs logged: {len(records)}/{TOTAL_TASKS * len(CONDITIONS)} · "
               f"ungraded: {total_ungraded}")
    if total_ungraded:
        out.append("")
        out.append("> Success rates exclude ungraded runs. Fill in `succeeded` "
                   "for every record before reporting final numbers.")
    out += ["", "## By retrieval method", "", main_table(by_condition)]
    out += ["", "## Success rate by difficulty", "",
            breakdown_table(by_condition, manifest, "difficulty", "Difficulty")]
    out += ["", "## Success rate by prompt style", "",
            breakdown_table(by_condition, manifest, "retrieval-style", "Prompt style")]
    out += ["", "_Retries = attempts_used − 1. Time = model generation time "
            "summed over all attempts (excludes review time)._", ""]
    return "\n".join(out)


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--results", default="results.jsonl")
    parser.add_argument("--manifest", default="manifest.json")
    parser.add_argument("--out", default="results_summary.md")
    args = parser.parse_args()

    report = build_report(load_results(args.results), load_manifest(args.manifest))
    print(report)
    with open(args.out, "w", encoding="utf-8") as f:
        f.write(report)
    print(f"\nWritten to {args.out}")


if __name__ == "__main__":
    main()