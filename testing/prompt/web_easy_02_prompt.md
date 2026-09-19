# Task: Make the homepage folder grid responsive

**Project:** zurisite1.5 (personal portfolio website)
**Files involved:** `mediaqueries.css`, `styles.css`, `index.html`
**Language:** CSS
**Difficulty:** Easy
**Retrieval style:** paraphrased (doesn't name the exact breakpoint value)

## Description

The homepage (`index.html`) displays six folder icons (about, contact,
projects, media, log, art) in a grid using the `.home-grid` class, currently
fixed at six columns (`grid-template-columns: repeat(6, 1fr)`). On narrow or
mobile screens, this squeezes all six icons into one cramped row.

Add a responsive rule so `.home-grid` drops to 2 columns on small screens,
using the same 768px breakpoint the site already uses elsewhere for
`.project-grid`.

## Constraints
- Add your fix to `mediaqueries.css`.
- Do not change the default (desktop) column count.
