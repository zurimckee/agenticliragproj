# Task: Fix broken pagination for search results

**Project:** tuneup (Flask + vanilla JS music player, PWA)
**Files involved:** `app.py`, `search.py`
**Language:** Python
**Difficulty:** Medium
**Retrieval style:** exact-term match (uses the same parameter/function names as the code)

## Description

The `/library` endpoint already supports an `offset` query parameter for
paging through the full track list (used by the frontend's `fetchLibrary()`
to load the whole library in pages of 500). However, when a search query
(`q`) is present instead, `offset` is silently ignored — every page of
search results starts back at the beginning.

Fix the search path so it respects `offset` the same way the full-library
path already does.

## Constraints
- `search_tracks()` should accept and apply an offset, with a sensible
  default when none is given.
- Don't change the FTS5 query-building logic (the `re.sub` cleaning step or
  the `fts_query` format) — only add offset support.
- The existing non-search pagination behavior must be unaffected.
