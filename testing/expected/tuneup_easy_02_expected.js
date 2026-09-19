// ROOT CAUSE (inside renderQueue(), in the remainingSlots block):
//
//   if (now_player.textContent === "playing from queue" && track.id === curr_track.src.split('/').pop()) {
//       return;
//   }
//
// track.id is a Number (comes straight from the JSON API response).
// curr_track.src.split('/').pop() returns a String (the last URL segment,
// e.g. "42"). 42 === "42" is always false under JavaScript's strict
// equality, so this condition can never be true and the skip never fires.

// EXPECTED FIX -- make both sides the same type before comparing:

if (now_player.textContent === "playing from queue" && String(track.id) === curr_track.src.split('/').pop()) {
    return;
}

/* GRADING NOTE
   Accept any fix that makes the id comparison type-safe -- converting
   track.id to a string (as above), parsing the URL segment to a number
   with parseInt(..., 10) and comparing numbers instead, or an equivalent
   approach. What matters is that the currently-playing track is correctly
   skipped when rendering the upcoming queue.
*/
