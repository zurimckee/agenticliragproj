# Task: Add an offline-sensors stat to the header

**Project:** parkingplusfullstack (React + Vite)
**Files involved:** `src/App.jsx`, `src/components/Header.jsx`, `src/services/api.js`
**Language:** JavaScript (JSX)
**Difficulty:** Medium
**Retrieval style:** exact-term match (the function you need already exists, unused)

## Description

`src/services/api.js` already defines a `fetchOfflineSensors` function that
hits the backend's offline-sensor-count endpoint, but nothing in the app
currently calls it.

Add a fourth stat box to the header's "Quick Stats" row (which currently
shows Available, Total Spots, and Available %) showing the current number of
offline sensors. You'll need to fetch this value as part of the app's
existing data-loading flow and pass it down to `Header` as a new prop.

## Constraints
- Reuse the existing `fetchOfflineSensors` function — don't write a new fetch
  call from scratch.
- Fetch the offline sensor count alongside the other data already being
  loaded together (don't add a separate, uncoordinated fetch cycle).
- Update the Quick Stats grid to fit a fourth box cleanly.
