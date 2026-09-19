# Task: Add a "favorite lots" feature with persistence

**Project:** parkingplusfullstack (React + Vite)
**Files involved:** `src/components/LotSelector.jsx`
**Language:** JavaScript (JSX)
**Difficulty:** Hard
**Retrieval style:** paraphrased (describes behavior, doesn't name internal variables)

## Description

Add the ability for a visitor to mark a parking lot as a favorite from the
lot selector row. Each lot button should get a small star toggle. Clicking
the star should:

1. Toggle that lot's favorite status.
2. Persist favorite status across page reloads (the visitor's favorites
   should still be starred if they reload the page later).
3. Cause favorited lots to be sorted to the front of the lot list, ahead of
   non-favorited lots, while otherwise preserving the existing relative
   order of lots.

Clicking the star should **not** also select that lot (selecting a lot is a
separate action, already handled by clicking the rest of the button).

## Constraints
- Don't modify any other component or the data-fetching flow — this should
  be self-contained to the lot selector.
- Don't mutate the array of lots passed in from outside; build a sorted copy
  for display instead.
- Persisted favorites should survive a full page reload, not just
  re-renders within the same session.
