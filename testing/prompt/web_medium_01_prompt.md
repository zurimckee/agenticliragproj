# Task: Show the last-updated time in the header

**Project:** parkingplusfullstack (React + Vite)
**Files involved:** `src/components/Header.jsx`
**Language:** JavaScript (JSX)
**Difficulty:** Medium
**Retrieval style:** exact-term match (names the prop and method already in the code)

## Description

`App.jsx` already passes a `lastUpdate` prop (a `Date` object, updated every
time data refreshes) into `Header`, but `Header` currently ignores it.

Add a small line of text next to the refresh button showing when the data
was last updated, formatted the same way the rest of the codebase already
formats timestamps (see how `SpotGrid.jsx` displays its "Updated" text).

## Constraints
- Only modify `Header.jsx`. Do not change `App.jsx` (the prop is already
  being passed).
- Match the existing formatting convention used elsewhere in the project for
  displaying times.
- Don't break the layout if `lastUpdate` happens to be undefined.
