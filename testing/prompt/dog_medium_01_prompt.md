# Task: Add a composite filter that reuses two existing filters

**Project:** thatdamndog (Streamlit image filter app)
**Files involved:** new file (e.g. `jailscream.py`), `app.py`
**Language:** Python
**Difficulty:** Medium
**Retrieval style:** exact-term match (names the two filters to combine)

## Description

Add a new filter called "jail+scream" that applies the existing `jail`
filter (from `dogjail.py`) first, and then runs its result through the
existing `screamo` filter (from `screamodog.py`) — reusing both functions as
they already exist rather than reimplementing their pixel logic from
scratch.

Register the combined filter in `app.py`'s filter dictionary alongside the
existing options.

## Constraints
- Reuse the existing `jail` and `screamo` functions by importing and calling
  them — don't copy their internal logic into a new standalone
  implementation.
- Do not modify `dogjail.py`, `screamodog.py`, or `image_converter.py`.
- The output of your new filter must be the result of running an image
  through `jail`, then feeding that result into `screamo` (in that order).
