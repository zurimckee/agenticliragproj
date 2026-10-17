import {useState, useEffect} from 'react';

export default function SpotGrid({ lot, spots, lastUpdate }) {
    const [selectedLevel, setSelectedLevel] = useState('all');

    useEffect(() => {
        setSelectedLevel('all');
    }, [lot?.Lot_ID]);

    const levels = ['all', ...new Set(
        spots
            .map(s => s.Level)
            .filter(l => l !== null && l !== undefined)
            .sort((a, b) => a - b)
    )];

    const filteredSpots = selectedLevel === 'all'
        ? spots
        : spots.filter(s => s.Level === Number(selectedLevel));
    
    const getNavigateUrl = (lot) => {
        if (!lot?.Latitude || !lot?.Longitude) return 'https://maps.charlotte.edu';
        return `https://maps.charlotte.edu/#/?ctr=${lot.Latitude},${lot.Longitude}&z=18&blnd=t,cc-parking,cc-amenities&vis=t,park`;
    };


    const available = lot?.Num_Spaces ?? 0;
    const total = lot?.Total_Spaces ?? 0;
    const percent = total > 0 ? Math.round((available / total) * 100) : 0;

    const tip = percent > 50
        ? 'Great availability! Head over now for easy parking.'
        : percent > 25
        ? 'Moderate availability. Consider alternative lots if this fills up.'
        : 'Limited spaces available. Try another lot for more options.';


    return (
        
        <div className="p-4">
            <div className="bg-white rounded-xl shadow-lg p-4 mb-4">
                <div className="flex items-center justify-between mb-3">
                    <h2 className="font-bold text-lg text-gray-800">
                        {lot?.Lot_Name} - Live Map
                    </h2>
                    <span className="text-xs text-gray-500">
                        Updated {lastUpdate.toLocaleTimeString()}
                    </span>
                </div>

            
                {/* Level dropdown — only shows for Decks */}
                {(spots[0]?.Lot_Type === 'Deck' || lot?.Lot_Type === 'Deck') && levels.length > 1 && (
                    <div className="flex items-center gap-2 mb-4">
                        <label className="text-sm font-medium text-gray-600">
                            Level:
                        </label>
                        <select
                            value={selectedLevel}
                            onChange={e => setSelectedLevel(e.target.value)}
                            className="border border-gray-300 rounded-lg px-3 py-1.5 text-sm text-gray-700 bg-white focus:outline-none focus:ring-2 focus:ring-green-500"
                        >
                            <option value="all">All Levels</option>
                            {levels
                                .filter(l => l !== 'all')
                                .map(level => {
                                    const levelSpots = spots.filter(s => s.Level === level);
                                    const levelAvailable = levelSpots.filter(s => s.Status).length;
                                    return (
                                        <option key={level} value={level}>
                                            {level} 
                                        </option>
                                    );
                                })
                            }
                        </select>


                        {/* Spot count for current view */}
                        <span className="text-xs text-gray-400 ml-auto">
                            {filteredSpots.filter(s => s.Status).length} of {filteredSpots.length} available
                        </span>
                    </div>
                )}


                {/* Spot grid — Status TRUE = available in your schema */}
                <div className="grid grid-cols-5 gap-2 mb-4">
                    {filteredSpots.map(spot => (
                        <div
                            key={spot.Spot_ID}
                            className={`aspect-square rounded-lg flex flex-col items-center justify-center text-xs font-semibold transition-all ${
                                spot.Status
                                    ? 'bg-green-500 text-white hover:scale-105 cursor-pointer'
                                    : 'bg-red-500 text-white'
                            }`}
                        >
                            <svg width="16" height="16" fill="currentColor" viewBox="0 0 24 24" className="mb-1">
                                <path d="M5 11l1.5-4.5h11L19 11m-1.5 5h-11L5 11h14l-1.5 5M12 18.5A1.5 1.5 0 0 1 10.5 17 1.5 1.5 0 0 1 12 15.5a1.5 1.5 0 0 1 1.5 1.5 1.5 1.5 0 0 1-1.5 1.5m-5 0A1.5 1.5 0 0 1 5.5 17 1.5 1.5 0 0 1 7 15.5 1.5 1.5 0 0 1 8.5 17 1.5 1.5 0 0 1 7 18.5z"/>
                            </svg>
                            <span>{spot.Spot_ID}</span>
                            {spot.Spot_Type && (
                                <span className="text-xs opacity-80">{spot.Spot_Type}</span>
                            )}
                        </div>
                    ))}
                </div>

                {/* Legend */}
                <div className="flex justify-center gap-6 pt-3 border-t border-gray-200">
                    <div className="flex items-center gap-2">
                        <div className="w-4 h-4 bg-green-500 rounded"></div>
                        <span className="text-sm text-gray-600">Available</span>
                    </div>
                    <div className="flex items-center gap-2">
                        <div className="w-4 h-4 bg-red-500 rounded"></div>
                        <span className="text-sm text-gray-600">Occupied</span>
                    </div>
                </div>
            </div>

            {/* Navigate button */}
            <button 
                onClick={() => window.open(getNavigateUrl(lot), '_blank')}
                className="w-full bg-gradient-to-r from-green-700 to-green-600 text-white py-4 rounded-xl font-semibold flex items-center justify-center gap-2 shadow-lg hover:shadow-xl transition">
                Navigate to {lot?.Lot_Name}

            </button>

            {/* Tip card */}
            <div className="mt-4 bg-green-50 border border-green-200 rounded-lg p-4">
                <h3 className="font-semibold text-green-900 mb-2">💡 Parking Tip</h3>
                <p className="text-sm text-green-800">{tip}</p>
            </div>
        </div>
    );
}