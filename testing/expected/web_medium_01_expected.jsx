// src/components/Header.jsx
// Only the changed parts are shown below.

// 1. Destructure the prop that was already being passed but unused:
export default function Header({ lot, lots = [], onRefresh, lastUpdate }) {

    // ...(unchanged calculations for totalAvailable, totalSpaces, percent)...

    return (
        <div className="bg-gradient-to-r from-green-700 to-green-600 text-white p-4 shadow-lg">
            <div className="flex items-center justify-between mb-4">
                <div className="flex items-center gap-3">
                    {/* ...unchanged logo/title... */}
                </div>

                {/* 2. Group the refresh button with the new timestamp text */}
                <div className="flex items-center gap-2">
                    {lastUpdate && (
                        <span className="text-xs text-green-100">
                            Updated {lastUpdate.toLocaleTimeString()}
                        </span>
                    )}
                    <button
                        onClick={onRefresh}
                        className="p-2 hover:bg-green-800 rounded-full transition"
                    >
                        {/* ...unchanged refresh icon svg... */}
                    </button>
                </div>
            </div>

            {/* ...unchanged Quick Stats grid... */}
        </div>
    );
}

/* GRADING NOTE
   Accept any solution that:
   1. Reads the already-passed lastUpdate prop (doesn't reinvent it via
      new Date() locally, which would show render time, not fetch time).
   2. Uses .toLocaleTimeString() (or equivalent human-readable time format)
      matching the convention already used in SpotGrid.jsx.
   3. Guards against lastUpdate being undefined/null without crashing.
   Exact placement/styling of the text is flexible.
*/
