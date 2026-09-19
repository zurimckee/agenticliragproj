# Task: Fix contact page box alignment

**Project:** zurisite1.5 (personal portfolio website)
**Files involved:** `mediaqueries.css`, `styles.css`, `contact.html`
**Language:** CSS
**Difficulty:** Easy
**Retrieval style:** exact-term match (uses the same class names as the code)

## Description

On the contact page (`contact.html`), there are two side-by-side boxes inside
`.contact-wrapper`: the social links box (`.contact-container`) and the
message form box (`.form-container`). The form box is taller than the social
links box, and these two boxes are supposed to align at the **top** of the
row.

Right now, the two boxes are vertically centered against each other instead
of top-aligned. Find and fix the CSS rule causing this, without changing the
layout on any other page.

## Constraints
- Do not modify `contact.html`.
- The fix must be a CSS-only change.
- Do not change how `.contact-wrapper` behaves on any other page that uses it.
