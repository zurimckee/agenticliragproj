# Task: Fix duplicate track downloads in the service worker

**Project:** tuneup (Flask + vanilla JS music player, PWA)
**Files involved:** `static/sw.js`
**Language:** JavaScript
**Difficulty:** Easy
**Retrieval style:** exact-term match (names the message type used in the code)

## Description

When a user taps "save for offline," the page posts a `CACHE_TRACK` message
to the service worker so it can fetch and cache the track file. Right now,
saving a single track for offline listening ends up fetching and caching the
same file twice instead of once — wasting bandwidth and hitting the server
twice for one download.

Find and fix the cause of the duplicate work in `sw.js`, making sure the page
still receives its `TRACK_CACHED` confirmation message afterward.

## Constraints
- Only modify `sw.js`.
- Don't change the message format (`{ type: "CACHE_TRACK", trackId }` coming
  in, `{ type: "TRACK_CACHED", trackId }` going back out).
