"""
grade_results.py

Interactive grading pass for results.jsonl. For each ungraded record it
shows the task's acceptance criteria and the model's final response, asks
pass/fail, and writes these fields back into the record:

  succeeded     : true / false
  criterion_id  : list of failed criterion IDs (empty for a pass)
  grader_notes  : one-line note on why

Blind grading: records are shuffled across conditions and the condition
name, retry count, and retrieved chunks are hidden while you grade, so you
judge each answer on its own merits.

Safe to stop at any time: every grade is saved immediately, and re-running
picks up where you left off (only ungraded records are shown).

Usage:
  python grade_results.py
  python grade_results.py --regrade        # also revisit already-graded records
  python grade_results.py --task 9         # only grade task 9
"""

import argparse
import json
import os
import random
import re
import tempfile

SEED = 42  # fixed so the shuffled order is the same each session


# ---------- loading ----------

def load_results(path):
    with open(path, encoding="utf-8") as f:
        return [json.loads(line) for line in f if line.strip()]


def save_results(path, records):
    """Write atomically so a crash mid-save can't corrupt the file."""
    folder = os.path.dirname(os.path.abspath(path))
    fd, tmp = tempfile.mkstemp(dir=folder, suffix=".tmp")
    with os.fdopen(fd, "w", encoding="utf-8") as f:
        for r in records:
            f.write(json.dumps(r) + "\n")
    os.replace(tmp, path)


def load_manifest(path):
    with open(path, encoding="utf-8") as f:
        return {t["task-id"]: t for t in json.load(f)}


def load_criteria(path):
    """Split acceptance_criteria.md into {task_id: section text}."""
    with open(path, encoding="utf-8") as f:
        text = f.read()
    sections = {}
    parts = re.split(r"^## Task (\d+)", text, flags=re.MULTILINE)
    # parts = [preamble, "1", body1, "2", body2, ...]
    for i in range(1, len(parts) - 1, 2):
        task_id = int(parts[i])
        body = parts[i + 1].split("\n---")[0]  # stop at the section divider
        sections[task_id] = f"## Task {task_id}{body}".strip()
    return sections


def criterion_ids(section_text):
    return set(re.findall(r"\b(T\d+-[PF]\d+)\b", section_text))


# ---------- prompts ----------

def ask(prompt, valid):
    while True:
        answer = input(prompt).strip().lower()
        if answer in valid:
            return answer
        print(f"  Please enter one of: {', '.join(valid)}")


def ask_ids(task_id, known_ids):
    while True:
        raw = input("Failed criterion IDs (comma-separated, e.g. "
                    f"T{task_id}-F1, T{task_id}-P2): ").strip()
        ids = [x.strip().upper() for x in raw.split(",") if x.strip()]
        if not ids:
            print("  A fail needs at least one criterion ID.")
            continue
        unknown = [x for x in ids if x not in known_ids]
        if unknown:
            print(f"  Not in Task {task_id}'s criteria: {', '.join(unknown)}")
            if ask("  Use them anyway? [y/n]: ", ["y", "n"]) == "n":
                continue
        return ids


# ---------- main loop ----------

def grade(args):
    records = load_results(args.results)
    manifest = load_manifest(args.manifest)
    criteria = load_criteria(args.criteria)

    queue = [
        i for i, r in enumerate(records)
        if (args.regrade or r.get("succeeded") is None)
        and (args.task is None or r["task_id"] == args.task)
    ]
    random.Random(SEED).shuffle(queue)

    total_ungraded = sum(1 for r in records if r.get("succeeded") is None)
    print(f"\n{len(queue)} record(s) to grade "
          f"({total_ungraded} ungraded in file). "
          "Enter p = pass, f = fail, s = skip, q = save & quit.\n")

    for n, idx in enumerate(queue, start=1):
        record = records[idx]
        task_id = record["task_id"]
        task = manifest.get(task_id, {})
        section = criteria.get(task_id, "(no criteria found for this task)")

        print("\n" + "#" * 78)
        print(f"[{n}/{len(queue)}]  Task {task_id}: {task.get('prompt', '')}"
              f"   ({task.get('difficulty', '?')})")
        if args.regrade and record.get("succeeded") is not None:
            print(f"Previously graded: succeeded={record['succeeded']} "
                  f"{record.get('criterion_id', [])}")
        print("#" * 78)
        print("\n" + section + "\n")
        print("-" * 30 + " MODEL RESPONSE " + "-" * 32)
        print(record.get("final_response", "(empty)"))
        print("-" * 78)

        choice = ask("\nGrade [p/f/s/q]: ", ["p", "f", "s", "q"])
        if choice == "q":
            break
        if choice == "s":
            continue

        if choice == "p":
            record["succeeded"] = True
            record["criterion_id"] = []
        else:
            record["succeeded"] = False
            record["criterion_id"] = ask_ids(task_id, criterion_ids(section))

        record["grader_notes"] = input("Note (one line, Enter to skip): ").strip()

        save_results(args.results, records)
        print("  Saved.")

    remaining = sum(1 for r in records if r.get("succeeded") is None)
    print(f"\nDone for now. {remaining} record(s) still ungraded.")
    if remaining == 0:
        print("All graded -- run summarize_results.py for the final tables.")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--results", default="results.jsonl")
    parser.add_argument("--manifest", default="manifest.json")
    parser.add_argument("--criteria", default="acceptance_criteria.md")
    parser.add_argument("--regrade", action="store_true",
                        help="also show records that already have a grade")
    parser.add_argument("--task", type=int, help="only grade this task id")
    grade(parser.parse_args())


if __name__ == "__main__":
    main()