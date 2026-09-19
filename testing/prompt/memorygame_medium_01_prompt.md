# Task: Fix the mislabeled turn counter

**Project:** javamemorygame (JavaFX Concentration game)
**Files involved:** `GamePane.java`
**Language:** Java
**Difficulty:** Medium
**Retrieval style:** exact-term match (names the existing label and field)

## Description

The status label at the top of the game reads `"turns: " + numMatched`, but
`numMatched` actually counts successful matches, not the number of turns
(attempts) the player has taken. A "turn" here means flipping a second card
and finding out whether it matches — whether or not it's a successful match.

Add a real turn counter that increments once per attempt (each time the
player flips their second card of a pair), and update the label to show both
values, e.g. `"turns: 4 | matches: 2"`.

## Constraints
- Only modify `GamePane.java`.
- The turn counter must reset to 0 whenever a new game starts — this
  includes the "new game" button, picking a new level from the dropdown, and
  choosing "new game" from the win dialog (all three currently reset
  `numClicks`/`numMatched` in different places — make sure the new counter
  resets everywhere they do).
- This project has two constructors (`GamePane()` and `GamePane(int
  cardSize)`) with nearly duplicate setup code — make sure the label's
  initial text is consistent in both.
