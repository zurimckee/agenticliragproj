# Task: Add a live elapsed-time timer

**Project:** javamemorygame (JavaFX Concentration game)
**Files involved:** `GamePane.java`
**Language:** Java
**Difficulty:** Hard
**Retrieval style:** paraphrased (describes behavior; avoids naming the existing AnimationTimer field)

## Description

Add a running timer that shows how long the current game has been going,
displayed in the status bar next to the existing turn/match label. It should
behave as follows:

- Starts counting up from 0:00 the moment the player flips the **first**
  card of a fresh game — not from when the level is picked or the window
  opens, since the player might sit on the level-select screen for a while
  first.
- Updates once per second while a game is in progress.
- Stops (freezes at its current value) the moment all pairs have been
  matched and the win dialog appears.
- Resets back to 0:00 (and stays stopped, waiting for the next first flip)
  whenever a new game begins — via the "new game" button, picking a new
  level, or choosing "new game" from the win dialog.

Display the time as `M:SS` (e.g. `0:07`, `1:42`).

## Constraints
- This class already has a timing mechanism (an `AnimationTimer` used to
  briefly show two non-matching cards before flipping them back). Don't
  reuse, rename, or repurpose that one — it serves a different, short-lived
  purpose. Add a separate mechanism for the elapsed-game-time display.
- This project has two nearly-identical constructors (`GamePane()` and
  `GamePane(int cardSize)`) — apply your change consistently in both.
- Only start the elapsed timer once per game (the "first flip" condition
  should not re-trigger on every subsequent flip).
