# Acceptance Criteria — 15 Coding Tasks

Pass/fail criteria for grading each `final_response` in `results.jsonl`. Derived from each task's prompt (`testing/prompt/`) and the grading note in its expected file (`testing/expected/`).

## Grading rules (apply to every task)

1. **Binary outcome.** A response **passes** only if it meets *every* "Pass requires" item and triggers *no* "Automatic fail" item. Anything else is a fail — no partial credit.
2. **Grade the final response only.** Retries are recorded separately (`attempts_used`). A task can use 3 attempts and pass, or 1 attempt and fail.
3. **Grade the behavior, not the wording.** The expected file is one correct answer, not the only one. A different approach passes if it meets the criteria.
4. **Instructions count as a change.** If the model describes an edit in prose (e.g. "delete this rule from `mediaqueries.css`") instead of writing out the code, grade the described edit as if it were applied.
5. **Unresolvable responses fail.** If the response is too vague to apply (e.g. "add appropriate styles here") or edits a file that doesn't exist in the project, it fails.
6. **Record the reason.** For each fail, note the criterion ID it missed (e.g. `T1-P1`). This makes the analysis by retrieval method much easier.
7. **Grade blind to condition if possible.** Shuffle records or hide the `condition` field while grading.

---

## Task 1 — Fix contact page box alignment
**Project:** zurisite1.5 · CSS · Easy · keyword
**Root cause:** an unscoped `.contact-wrapper { align-items: center; }` rule in `mediaqueries.css` overrides `styles.css` because it loads later.

**Pass requires**
- **T1-P1** After the change, `.contact-wrapper` resolves to `align-items: flex-start` at **all** screen widths. Acceptable: delete the rule from `mediaqueries.css`, change `center` → `flex-start` there, or scope it inside a non-conflicting `@media` block.
- **T1-P2** The change is CSS only.

**Automatic fail**
- **T1-F1** Fix is only added to `styles.css` (or any file loaded *before* `mediaqueries.css`) without addressing the overriding rule — the bug remains. *(Note: the current baseline response in `results.jsonl` falls into this case.)*
- **T1-F2** `contact.html` is modified.
- **T1-F3** Uses `!important` or an inline style to force alignment instead of fixing the conflicting rule.
- **T1-F4** Changes other `.contact-wrapper` properties in a way that alters layout (e.g. changes `flex-direction`, adds `justify-content` that shifts the boxes).

---

## Task 2 — Make the homepage folder grid responsive
**Project:** zurisite1.5 · CSS · Easy · paraphrased

**Pass requires**
- **T2-P1** At widths **≤ 768px**, `.home-grid` has `grid-template-columns: repeat(2, 1fr)` (or an equivalent 2-column definition).
- **T2-P2** At widths **> 768px**, `.home-grid` still has 6 columns.
- **T2-P3** Uses the 768px breakpoint (`max-width: 768px`).
- **T2-P4** Rule is placed in `mediaqueries.css`, or added to the existing 768px block in `styles.css`.

**Automatic fail**
- **T2-F1** The default (desktop) rule for `.home-grid` is changed.
- **T2-F2** Uses a different breakpoint (e.g. 600px, 720px).
- **T2-F3** Rule is placed outside any `@media` block, so it applies on desktop too.

---

## Task 3 — Fix duplicate track downloads in the service worker
**Project:** tuneup · JavaScript · Easy · keyword
**Root cause:** two `message` listeners in `sw.js` both handle `CACHE_TRACK`.

**Pass requires**
- **T3-P1** `cacheTrackForOffline(trackId)` runs **exactly once** per `CACHE_TRACK` message (one listener handles it).
- **T3-P2** `{ type: "TRACK_CACHED", trackId }` is still posted back to the page **after** caching finishes.
- **T3-P3** Only `sw.js` is modified.

**Automatic fail**
- **T3-F1** Both listeners remain and still both call `cacheTrackForOffline`.
- **T3-F2** The listener that sends `TRACK_CACHED` is removed and no confirmation is sent.
- **T3-F3** Message format changed (type names or `trackId` field).
- **T3-F4** Adds a deduplication workaround (e.g. a "seen" set, cache-exists check) but leaves the duplicate listener in place. This masks the bug instead of fixing it.

---

## Task 4 — Fix duplicate "now playing" track in the upcoming queue
**Project:** tuneup · JavaScript · Easy · paraphrased
**Root cause:** `track.id` (Number) `===` `curr_track.src.split('/').pop()` (String) is always false.

**Pass requires**
- **T4-P1** The comparison in `renderQueue()` makes both sides the same type: `String(track.id) === …`, `parseInt(…, 10) === track.id`, `Number(…) === track.id`, or equivalent.
- **T4-P2** The `now_player.textContent === "playing from queue"` check is still present.
- **T4-P3** Change is limited to that comparison.

**Automatic fail**
- **T4-F1** Switches to loose equality (`==`) only. *(Judgment call — decide once and apply it to every condition. Recommended: **fail**, since the prompt asks for the comparison that's "silently failing" to be fixed properly, and `==` hides the type mismatch.)*
- **T4-F2** Restructures `renderQueue()` beyond the comparison (e.g. rewrites the loop or the `remainingSlots` logic).
- **T4-F3** Identifies a different, non-existent cause.

---

## Task 5 — Add an "invert" filter
**Project:** thatdamndog · Python · Easy · keyword

**Pass requires**
- **T5-P1** A new filter function that takes the pixel list (rows of `(R, G, B)` tuples) and returns a new list in the same format.
- **T5-P2** Each channel becomes `255 - value`.
- **T5-P3** Uses the nested per-pixel loop style of the existing filters.
- **T5-P4** Includes an `if __name__ == '__main__':` block mirroring the existing filters (open image → `ImageToList` → filter → `ListToImage` → save).
- **T5-P5** Imported into `app.py` and added to the `FILTERS` dict.

**Automatic fail**
- **T5-F1** `image_converter.py` is modified.
- **T5-F2** Uses numpy, `ImageOps.invert`, or other vectorized/PIL-level operations.
- **T5-F3** Mutates the input list in place instead of returning a new one.
- **T5-F4** Inverts alpha or returns a different tuple shape than the input pixels.

---

## Task 6 — Show the last-updated time in the header
**Project:** parkingplusfullstack · JSX · Medium · keyword

**Pass requires**
- **T6-P1** `Header` destructures and uses the `lastUpdate` prop.
- **T6-P2** Displays it with `.toLocaleTimeString()` (matching `SpotGrid.jsx`).
- **T6-P3** Guards against `lastUpdate` being undefined/null (e.g. `lastUpdate && …`, optional chaining) so nothing crashes.
- **T6-P4** Text is placed near the refresh button.

**Automatic fail**
- **T6-F1** Creates `new Date()` inside `Header` instead of using the prop (shows render time, not fetch time).
- **T6-F2** `App.jsx` or any other file is modified.
- **T6-F3** Uses a different format (e.g. `toISOString()`, a date library, manual formatting).

---

## Task 7 — Add an offline-sensors stat to the header
**Project:** parkingplusfullstack · JSX · Medium · keyword

**Pass requires**
- **T7-P1** Imports and calls the existing `fetchOfflineSensors` from `services/api.js`.
- **T7-P2** The call is part of the existing `loadData` cycle (e.g. added to the `Promise.all`), not a separate `useEffect`/interval.
- **T7-P3** Unwraps the `{ offline_count }` response shape before display (doesn't render the raw object).
- **T7-P4** New state in `App.jsx`, passed to `Header` as a new prop.
- **T7-P5** `Header` renders a fourth stat box styled like the other three, and the grid is updated to fit it (`grid-cols-4` or a 2×2 wrap).

**Automatic fail**
- **T7-F1** Writes a new `fetch()` / axios call instead of reusing `fetchOfflineSensors`.
- **T7-F2** Fetches in its own separate cycle.
- **T7-F3** Renders the response object directly (would show `[object Object]` or crash).
- **T7-F4** Leaves the grid at `grid-cols-3`, so the fourth box wraps awkwardly.

---

## Task 8 — Fix broken pagination for search results
**Project:** tuneup · Python · Medium · keyword

**Pass requires**
- **T8-P1** `search_tracks()` accepts an `offset` parameter with a sensible default (e.g. `offset=0`).
- **T8-P2** Offset is applied in the query (`LIMIT ? OFFSET ?` with a bound parameter) or by equivalent slicing.
- **T8-P3** The `/library` route in `app.py` passes its already-parsed `offset` into `search_tracks()` when `q` is present.
- **T8-P4** The non-search (full library) path is unchanged.

**Automatic fail**
- **T8-F1** The `re.sub` cleaning step or the `fts_query` format is changed.
- **T8-F2** Offset is inserted into the SQL with string formatting/f-string (SQL injection risk; inconsistent with the existing parameterized style).
- **T8-F3** Only one side is fixed (function accepts offset but route never passes it, or vice versa).

---

## Task 9 — Fix the mislabeled turn counter
**Project:** javamemorygame · Java · Medium · keyword

**Pass requires**
- **T9-P1** A new turn counter field increments **once per pair** (when the second card is flipped), whether or not it matches.
- **T9-P2** The label shows both values (e.g. `"turns: 4 | matches: 2"`).
- **T9-P3** Counter resets to 0 in `newGame()` (covers the button and the win-dialog path).
- **T9-P4** Counter resets to 0 in the level dropdown's `menu.setOnAction` handler.
- **T9-P5** Initial label text is consistent in **both** constructors.
- **T9-P6** Only `GamePane.java` is modified.

**Automatic fail**
- **T9-F1** Increments on every single card click (counts 2× per turn).
- **T9-F2** Increments only on matches or only on misses.
- **T9-F3** Misses any reset path (P3 or P4).
- **T9-F4** Updates only one constructor.
- **T9-F5** Just renames the label to "matches:" without adding a real turn counter.

---

## Task 10 — Add a composite filter (jail + scream)
**Project:** thatdamndog · Python · Medium · keyword

**Pass requires**
- **T10-P1** New file imports `jail` from `dogjail` and `screamo` from `screamodog`.
- **T10-P2** Applies them in order: `screamo(jail(pixels))`.
- **T10-P3** Returns a pixel list in the standard format.
- **T10-P4** Registered in `app.py`'s `FILTERS` dict as `"jail+scream"` (or similar clear name).

**Automatic fail**
- **T10-F1** Reimplements either filter's pixel logic instead of calling the existing function.
- **T10-F2** Wrong order (`jail(screamo(...))`), or both applied separately to the original and merged.
- **T10-F3** `dogjail.py`, `screamodog.py`, or `image_converter.py` is modified.
- **T10-F4** Wrong import path (e.g. `from screamo import screamo`) — the code won't run.

---

## Task 11 — Add a live search box to the projects page filter
**Project:** zurisite1.5 · JS/HTML · Hard · paraphrased

**Pass requires**
- **T11-P1** A text `<input>` is added inside the existing filter bar container (`#filters` / `.filter-bar`).
- **T11-P2** Filtering runs on the `input` (or `keyup`) event — live, with no submit button or reload.
- **T11-P3** Matches against each card's name **and** stack text.
- **T11-P4** Case-insensitive (both sides lowercased or equivalent).
- **T11-P5** Category and search combine with **AND**: a card is visible only if it matches both.
- **T11-P6** Clicking a category button re-applies the current search term, and typing re-applies the current category (shared state, one apply function or equivalent).
- **T11-P7** Hides cards using the existing `.hidden` class and `data-tags` attribute.

**Automatic fail**
- **T11-F1** Category click clears or ignores the search (or vice versa) — "last one wins."
- **T11-F2** OR logic between search and category.
- **T11-F3** Hides cards with inline `style.display` or a second, separate class instead of `.hidden`.
- **T11-F4** Adds a library or re-renders the card list from scratch.

---

## Task 12 — Add a "favorite lots" feature with persistence
**Project:** parkingplusfullstack · JSX · Hard · paraphrased

**Pass requires**
- **T12-P1** Each lot button has a star toggle that shows filled/empty state and updates on click.
- **T12-P2** Favorites persist across a full page reload via `localStorage` (read on init, written on change).
- **T12-P3** Favorited lots sort to the front; otherwise original order is preserved (stable sort or equivalent).
- **T12-P4** Sorting uses a copy (`[...lots]`, `.slice()`) — the `lots` prop is not mutated.
- **T12-P5** Clicking the star does **not** also select the lot (`stopPropagation` or a non-nested structure).
- **T12-P6** All changes are in `LotSelector.jsx`.

**Automatic fail**
- **T12-F1** `lots.sort(...)` called directly on the prop.
- **T12-F2** Persistence only in React state / context (lost on reload).
- **T12-F3** Star click also triggers `onSelect`.
- **T12-F4** Modifies other components or the data-fetching flow.
- **T12-F5** `localStorage.getItem` result used without `JSON.parse` or with no fallback, so a first visit crashes.

*Note: a star rendered as a nested `<button>` inside the lot `<button>` is acceptable (the reference solution does this); a `<span>` with `onClick` + `stopPropagation` is equally acceptable.*

---

## Task 13 — Add a persisted "best score" per difficulty level
**Project:** javamemorygame · Java · Hard · paraphrased

**Pass requires**
- **T13-P1** Uses `java.util.prefs.Preferences` to store best scores.
- **T13-P2** Scores are keyed **per level** (each level has its own independent best).
- **T13-P3** Adds its own attempt counter that increments once per pair check (second-card flip).
- **T13-P4** On a win only (all pairs matched), saves the new count if there's no saved best **or** it is strictly lower.
- **T13-P5** Status bar shows the current level's best (e.g. `"best: 7"` / `"best: --"`), and it updates when the level changes and when a new best is set.
- **T13-P6** Attempt counter resets on every new-game path (`newGame()` and the level dropdown handler).
- **T13-P7** Handles `level == null` (new game before any level picked) without passing `null` as a Preferences key.
- **T13-P8** Both constructors updated consistently.

**Automatic fail**
- **T13-F1** Overwrites the saved best unconditionally on win.
- **T13-F2** Saves on non-win events (new game, level change, abandoned game).
- **T13-F3** One shared best across all levels.
- **T13-F4** Uses a file, database, or external library for persistence.
- **T13-F5** Relies on a turn counter that doesn't exist in the current `GamePane.java` (e.g. assumes Task 9's `numTurns` exists).
- **T13-F6** Counts attempts per single card flip.

---

## Task 14 — Add a live elapsed-time timer
**Project:** javamemorygame · Java · Hard · paraphrased

**Pass requires**
- **T14-P1** Uses a **separate** timing mechanism from the existing `timer` AnimationTimer (e.g. a `Timeline` with a 1-second `KeyFrame`, or a second AnimationTimer with its own start time).
- **T14-P2** Starts on the **first card flip** of a fresh game — not on window open or level pick.
- **T14-P3** Starts only once per game (guard flag or equivalent).
- **T14-P4** Updates about once per second.
- **T14-P5** Displays as `M:SS` with a zero-padded seconds field (`0:07`, `1:42`).
- **T14-P6** Stops when all pairs are matched (before or as the win dialog appears).
- **T14-P7** Resets to `0:00` and stays stopped on every new-game path: `newGame()` (button + win dialog) and the level dropdown handler.
- **T14-P8** Label added to the status bar in both constructors.

**Automatic fail**
- **T14-F1** Reuses, renames, or repurposes the existing `timer` field.
- **T14-F2** Timer starts at construction or level selection.
- **T14-F3** Restarts or resets on every flip.
- **T14-F4** Wrong format (e.g. `0:7`, total seconds only, `MM:SS:ms`).
- **T14-F5** Any reset path is missed (timer keeps running into the next game).

---

## Task 15 — Add a filter-intensity slider
**Project:** thatdamndog · Python · Hard · paraphrased

**Pass requires**
- **T15-P1** A slider from 0 to 100 (or 0.0–1.0 shown as percent) is added in `app.py`.
- **T15-P2** The slider only appears when both an image is uploaded and a filter is selected (inside the existing `if user_image and filter_options is not None:` block).
- **T15-P3** Blending is done on **pixel lists** in a helper module (e.g. `blend.py`): each channel = `orig × (1 − t) + filtered × t`.
- **T15-P4** At 0% the output equals the original; at 100% it equals the fully filtered image.
- **T15-P5** Blended values are integers in 0–255 (rounded and/or clamped).
- **T15-P6** Both the on-screen preview and the download use the blended result.
- **T15-P7** Works for any selected filter (uses `FILTERS[filter_options]`, nothing hard-coded).

**Automatic fail**
- **T15-F1** Uses `Image.blend`, numpy, or other PIL/array-level blending.
- **T15-F2** `image_converter.py` is modified, or its conversion logic is duplicated.
- **T15-F3** Only the preview *or* only the download is blended.
- **T15-F4** Blend direction is reversed (0% = fully filtered).
- **T15-F5** Produces floats or out-of-range values passed to `ListToImage`.

---

## Recording template

Add these fields to each record in `results.jsonl` during the grading pass:

```json
{
  "succeeded": true,
  "failed_criteria": [],
  "grader_notes": "Deleted the unscoped rule in mediaqueries.css."
}
```

For a fail:

```json
{
  "succeeded": false,
  "failed_criteria": ["T1-F1"],
  "grader_notes": "Added align-items: flex-start to styles.css only; mediaqueries.css override still wins."
}
```