// ROOT CAUSE
// sw.js registers TWO separate "message" event listeners that both react
// to type "CACHE_TRACK":
//
//   self.addEventListener("message", (event) => {
//       if (event.data?.type === "CACHE_TRACK") {
//           const trackId = event.data.trackId;
//           event.waitUntil(cacheTrackForOffline(trackId));
//       }
//   });
//
//   self.addEventListener("message", (event) => {
//       if (event.data?.type === "CACHE_TRACK") {
//           const trackId = event.data.trackId;
//           event.waitUntil(
//               cacheTrackForOffline(trackId).then(() => {
//                   event.source.postMessage({ type: "TRACK_CACHED", trackId });
//               })
//           );
//       }
//   });
//
// Both handlers fire on every CACHE_TRACK message, so cacheTrackForOffline()
// runs twice per user action -- two full network fetches, two cache writes.

// EXPECTED FIX -- remove the first (redundant) listener entirely,
// keeping only the one that also sends the confirmation back:

self.addEventListener("message", (event) => {
    if (event.data?.type === "CACHE_TRACK") {
        const trackId = event.data.trackId;
        event.waitUntil(
            cacheTrackForOffline(trackId).then(() => {
                event.source.postMessage({ type: "TRACK_CACHED", trackId });
            })
        );
    }
});

/* GRADING NOTE
   Accept any solution that results in cacheTrackForOffline() running exactly
   once per CACHE_TRACK message, with TRACK_CACHED still posted back
   afterward. Merging into one listener (as above) or deleting either
   duplicate and patching the other to send the confirmation are both fine.
*/
