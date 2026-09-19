"""
chunker.py

Splits a file's text into chunks for the retrieval index.

- Files at or under `whole_file_threshold` lines are kept as a single chunk
  (this is the common case for this codebase).
- Longer files are split into paragraphs (blocks of consecutive non-blank
  lines -- works the same way regardless of language, no per-language
  parser needed) and greedily merged into chunks capped at
  `max_chunk_lines`, carrying the last paragraph of each chunk into the
  next one for a bit of overlap/continuity across the cut.

Each chunk is returned with 1-indexed start/end line numbers so you can
trace it back to its source file for the target-files check against your
manifest.
"""


def chunk_file(text, whole_file_threshold=200, max_chunk_lines=130, overlap_paragraphs=1):
    lines = text.splitlines()
    total_lines = len(lines)

    if total_lines <= whole_file_threshold:
        return [{"start_line": 1, "end_line": total_lines, "text": text}]

    # Group into paragraphs: blocks of consecutive non-blank lines.
    paragraphs = []
    para_start = None
    for i, line in enumerate(lines, start=1):
        if line.strip():
            if para_start is None:
                para_start = i
        elif para_start is not None:
            paragraphs.append((para_start, i - 1))
            para_start = None
    if para_start is not None:
        paragraphs.append((para_start, total_lines))

    # Greedily merge paragraphs into chunks capped at max_chunk_lines.
    def flush(chunk_paras, lines=lines):
        start = chunk_paras[0][0]
        end = chunk_paras[-1][1]
        return {"start_line": start, "end_line": end, "text": "\n".join(lines[start - 1:end])}

    results = []
    current = []
    for para in paragraphs:
        p_start, p_end = para
        p_span = p_end - p_start + 1

        if p_span > max_chunk_lines:
            # This single paragraph is already bigger than the cap on its
            # own (e.g. a long unbroken block with no internal blank
            # lines -- log entries, a big SQL INSERT, dense JSON). Close
            # out whatever's pending, then hard-split just this paragraph
            # into fixed-size pieces rather than letting it through whole.
            if current:
                results.append(flush(current))
                current = []
            sub_start = p_start
            while sub_start <= p_end:
                sub_end = min(sub_start + max_chunk_lines - 1, p_end)
                results.append({
                    "start_line": sub_start,
                    "end_line": sub_end,
                    "text": "\n".join(lines[sub_start - 1:sub_end]),
                })
                sub_start = sub_end + 1
            continue

        candidate = current + [para]
        span = candidate[-1][1] - candidate[0][0] + 1
        if span > max_chunk_lines and current:
            results.append(flush(current))
            current = (current[-overlap_paragraphs:] if overlap_paragraphs else []) + [para]
        else:
            current.append(para)
    if current:
        results.append(flush(current))

    return results


# ----- Usage in your indexer -----
# for file_path in all_files:
#     text = open(file_path, encoding="utf-8").read()
#     for chunk in chunk_file(text):
#         record = {
#             "file_path": relative_path(file_path),   # e.g. "GamePane.java"
#             "start_line": chunk["start_line"],
#             "end_line": chunk["end_line"],
#             "text": chunk["text"],
#         }
#         # feed `record` to both your BM25 index and your embedding index