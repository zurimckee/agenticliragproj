
"""
build_index.py
 
Walks the knowledge base and chunks every source file with
chunker.chunk_file(), writing one JSON record per chunk to chunks.jsonl.
 
This file is the shared source of truth for BOTH retrieval methods:
bm25_search.py reads it directly, and build_embeddings.py reads it to
generate embeddings. Building both indexes from the same chunk boundaries
means neither retrieval method gets finer- or coarser-grained chunks than
the other -- differences you see later come from the retrieval method
itself, not from uneven chunking between conditions.
 
Run this first, before build_embeddings.py. Re-run it (and then
build_embeddings.py) any time you add, remove, or edit files in the
knowledge base.
"""
 
import os
import json
import re
from chunker import chunk_file
 
KB_ROOT = "knowledgecodebase"   # adjust if your knowledge base folder is elsewhere/renamed
OUTPUT_PATH = "chunks.jsonl"
 
# Matches long d="..." / points="..." attribute values (inline SVG path
# and polygon data). This content is dense, non-linguistic, and tokenizes
# far worse than normal code/text -- a few thousand characters of it can
# quietly cost more tokens than the same length of ordinary code, which is
# what caused the "input length exceeds context length" error even on a
# chunk that looked modest by character count alone. It also carries
# essentially no retrieval value for any coding task, so stripping it is a
# pure win rather than a lossy tradeoff.
DENSE_ATTR_PATTERN = re.compile(r'(d|points)="([^"]{150,})"')
 
 
def sanitize_dense_attrs(text):
    def replace(match):
        attr, value = match.group(1), match.group(2)
        return f'{attr}="[SVG_{attr.upper()}_DATA_OMITTED:{len(value)}_chars]"'
    return DENSE_ATTR_PATTERN.sub(replace, text)
 
# Only index real source/text files -- an allowlist is safer than a
# blocklist here, since it fails safe if some other junk file type slipped
# through your cleanup pass.
INCLUDED_EXTENSIONS = {
    ".java", ".py", ".js", ".jsx", ".css", ".html", ".sql", ".md",
}
 
# Defensive skip list in case any of these still exist despite cleanup.
SKIP_DIR_NAMES = {
    ".git", "node_modules", "target", "build", ".idea", "__pycache__",
    ".vscode", "venv", ".venv", "dist",
}
 
 
def find_files(root):
    for dirpath, dirnames, filenames in os.walk(root):
        dirnames[:] = [d for d in dirnames if d not in SKIP_DIR_NAMES]
        for name in filenames:
            ext = os.path.splitext(name)[1].lower()
            if ext in INCLUDED_EXTENSIONS:
                yield os.path.join(dirpath, name)
 
 
def build_records(kb_root=KB_ROOT):
    records = []
    skipped = []
 
    for full_path in find_files(kb_root):
        rel_to_kb = os.path.relpath(full_path, kb_root)
        parts = rel_to_kb.split(os.sep)
        project = parts[0]
        file_path = "/".join(parts[1:])  # project-relative, matches your manifest's target-files convention
 
        try:
            text = open(full_path, encoding="utf-8").read()
        except UnicodeDecodeError:
            skipped.append(full_path)
            continue
 
        if not text.strip():
            continue
 
        text = sanitize_dense_attrs(text)
 
        for chunk in chunk_file(text):
            records.append({
                "chunk_id": f"{project}::{file_path}::{chunk['start_line']}-{chunk['end_line']}",
                "project": project,
                "file_path": file_path,
                "start_line": chunk["start_line"],
                "end_line": chunk["end_line"],
                "text": chunk["text"],
            })
 
    return records, skipped
 
 
def main():
    records, skipped = build_records()
 
    with open(OUTPUT_PATH, "w", encoding="utf-8") as f:
        for r in records:
            f.write(json.dumps(r) + "\n")
 
    if skipped:
        print(f"Skipped {len(skipped)} file(s) that weren't valid UTF-8:")
        for s in skipped:
            print(f"  {s}")
 
    by_project = {}
    for r in records:
        by_project[r["project"]] = by_project.get(r["project"], 0) + 1
 
    print(f"\nWrote {len(records)} chunks from '{KB_ROOT}' to {OUTPUT_PATH}")
    for project, count in sorted(by_project.items()):
        print(f"  {project}: {count} chunks")
 
 
if __name__ == "__main__":
    main()
 
