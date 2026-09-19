// ===== src/App.jsx =====
// (only changed lines shown)

import { fetchLotSummary, fetchSpots, fetchOfflineSensors } from './services/api';

export default function App() {
    const [lots, setLots] = useState([]);
    const [spots, setSpots] = useState([]);
    const [offlineSensors, setOfflineSensors] = useState(0);   // new
    const [selectedLot, setSelectedLot] = useState(null);
    const [loading, setLoading] = useState(true);
    const [lastUpdate, setLastUpdate] = useState(new Date());

    const loadData = async () => {
        try {
            const [lotData, spotData, sensorData] = await Promise.all([
                fetchLotSummary(),
                fetchSpots(),
                fetchOfflineSensors()          // new
            ]);
            setLots(lotData);
            setSpots(spotData);
            setOfflineSensors(sensorData?.offline_count ?? 0);   // matches
            // the { offline_count } shape returned by /api/sensors/offline
            if (!selectedLot && lotData.length > 0) {
                setSelectedLot(lotData[0].Lot_ID);
            }
            setLastUpdate(new Date());
        } catch (error) {
            console.error('Failed to load data:', error);
        } finally {
            setLoading(false);
        }
    };

    // ...unchanged useEffects...

    return (
        // ...
        <Header
            lot={currentLot}
            lots={lots}
            onRefresh={loadData}
            lastUpdate={lastUpdate}
            offlineSensors={offlineSensors}   // new
        />
        // ...
    );
}


// ===== src/components/Header.jsx =====
// (only changed lines shown)

export default function Header({ lot, lots = [], onRefresh, offlineSensors = 0 }) {
    // ...unchanged calculations...

    return (
        <div className="bg-gradient-to-r from-green-700 to-green-600 text-white p-4 shadow-lg">
            {/* ...unchanged top row... */}

            {/* was: grid-cols-3, now grid-cols-4 to fit the new stat */}
            <div className="grid grid-cols-4 gap-2 text-center">
                <div className="bg-white/10 backdrop-blur rounded-lg p-2">
                    <div className="text-2xl font-bold">{totalAvailable}</div>
                    <div className="text-xs text-green-100">Available</div>
                </div>
                <div className="bg-white/10 backdrop-blur rounded-lg p-2">
                    <div className="text-2xl font-bold">{totalSpaces}</div>
                    <div className="text-xs text-green-100">Total Spots</div>
                </div>
                <div className="bg-white/10 backdrop-blur rounded-lg p-2">
                    <div className="text-2xl font-bold">{percent}%</div>
                    <div className="text-xs text-green-100">Available</div>
                </div>
                <div className="bg-white/10 backdrop-blur rounded-lg p-2">
                    <div className="text-2xl font-bold">{offlineSensors}</div>
                    <div className="text-xs text-green-100">Offline Sensors</div>
                </div>
            </div>
        </div>
    );
}

/* GRADING NOTE
   Accept any solution that:
   1. Calls the existing fetchOfflineSensors (doesn't hand-roll a new fetch).
   2. Fetches it as part of the same batch/cycle as the other data (e.g.
      inside the existing Promise.all in loadData), not a separate
      untracked call.
   3. Correctly unwraps the { offline_count } response shape.
   4. Displays the value somewhere in Header's stats area -- exact grid
      layout (4 columns vs. a wrapped 2x2, etc.) is flexible.
*/
