# Task: Fix duplicate "now playing" track in the upcoming queue

**Project:** tuneup (Flask + vanilla JS music player, PWA)
**Files involved:** `static/script.js`
**Language:** JavaScript
**Difficulty:** Easy
**Retrieval style:** paraphrased (describes the symptom, not the exact comparison)

## Description

`renderQueue()` is supposed to skip showing the currently-playing track again
in the "upcoming" section when it's playing from the manual queue (it checks
this using the player's status text alongside the track's id). Right now that
check never actually works, so the current track can still show up
duplicated near the top of the upcoming list.

Find and fix the comparison that's silently failing.

## Constraints
- Only change the failing comparison inside `renderQueue()` — don't restructure
  the rest of the queue-rendering logic.
