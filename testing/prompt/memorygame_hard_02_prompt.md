# Task: Add a persisted "best score" per difficulty level

**Project:** javamemorygame (JavaFX Concentration game)
**Files involved:** `GamePane.java`
**Language:** Java
**Difficulty:** Hard
**Retrieval style:** paraphrased (describes the feature; doesn't name specific fields)

## Description

Track how many attempts (times the player flips a second card and finds out
whether it's a match, win or lose) it takes to finish a game, and remember
the fewest attempts ever taken to win at each difficulty level -- saved
permanently, so it's still there the next time the app is opened, not just
during the current run.

Display the current level's best score somewhere in the status bar (for
example, `"best: 7"`, or something like `"best: --"` if that level has never
been won yet).

When the player wins a game, compare that game's attempt count to the saved
best for the level that was just played:
- If there's no saved best yet for that level, or the new count is lower
  than the saved one, save the new value as the best and update the display.
- Otherwise, leave the saved best untouched.

## Constraints
- Use Java's built-in `java.util.prefs.Preferences` class to persist the
  scores -- don't add a database, file, or external library for this.
- Best scores must be tracked **per level** (e.g. "level one" and "level
  three" each have their own independent best score).
- Treat this task as self-contained: don't assume any other counter or
  field already exists beyond what's currently in `GamePane.java` -- add
  whatever attempt-tracking you need for this feature specifically.
- Only save a new best when the just-finished game was actually won (all
  pairs matched) -- an abandoned or in-progress game should never overwrite
  a saved best.
- Apply your change consistently across both constructors.
