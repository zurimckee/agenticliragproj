# Task: Add a live search box to the projects page filter

**Project:** zurisite1.5 (personal portfolio website)
**Files involved:** `projects.html` (inline `<script>` block + HTML markup)
**Language:** JavaScript + HTML
**Difficulty:** Hard
**Retrieval style:** paraphrased (describes behavior, doesn't quote variable/class names)

## Description

The projects page already lets visitors filter project cards by category
using the row of buttons above the grid (all / stack / python / web / java /
ai-ml / ui-ux / data). Each card carries a data attribute holding its tags,
and clicking a filter button hides any card that doesn't match by toggling a
class on it.

Add a text input next to the filter buttons that lets visitors search
projects by name or tech stack. Typing in the box should filter the visible
cards live, as the user types -- no submit button, no page reload.

The search and the category filter buttons must work **together**: if a
visitor has "java" selected as the active category filter and then types
"budget" in the search box, only cards that are both tagged java AND match
"budget" in their name or stack should stay visible. Switching the category
filter afterward should re-apply whatever is currently typed in the search
box (the two filters combine, they don't reset each other).

## Constraints
- Reuse the existing hidden-class / data-tag filtering approach already in
  the file -- don't introduce a second, separate filtering mechanism.
- The search should be case-insensitive.
- Add the input inside the existing filter bar container, styled minimally --
  functionality matters more than styling for this task.
