// src/components/LotSelector.jsx — full expected file

import { useState, useEffect } from 'react';

const FAVORITES_KEY = 'parkingplus_favorite_lots';

function loadFavorites() {
    try {
        const raw = localStorage.getItem(FAVORITES_KEY);
        return raw ? new Set(JSON.parse(raw)) : new Set();
    } catch {
        return new Set();
    }
}

export default function LotSelector({ lots, selectedLot, onSelect }) {
    const [favorites, setFavorites] = useState(loadFavorites);

    useEffect(() => {
        localStorage.setItem(FAVORITES_KEY, JSON.stringify([...favorites]));
    }, [favorites]);

    const toggleFavorite = (e, lotId) => {
        e.stopPropagation(); // don't also trigger onSelect on the parent button
        setFavorites(prev => {
            const next = new Set(prev);
            if (next.has(lotId)) {
                next.delete(lotId);
            } else {
                next.add(lotId);
            }
            return next;
        });
    };

    // Build a sorted copy for display without mutating the incoming prop.
    // Favorited lots first; relative order otherwise preserved (stable sort).
    const sortedLots = [...lots].sort((a, b) => {
        const aFav = favorites.has(a.Lot_ID) ? 0 : 1;
        const bFav = favorites.has(b.Lot_ID) ? 0 : 1;
        return aFav - bFav;
    });

    return (
        <div className="p-4 bg-white shadow">
            <div className="flex items-center gap-2 mb-3">
                <svg className="text-green-700" width="20" height="20" fill="currentColor" viewBox="0 0 24 24">
                    <path d="M12,11.5A2.5,2.5 0 0,1 9.5,9A2.5,2.5 0 0,1 12,6.5A2.5,2.5 0 0,1 14.5,9A2.5,2.5 0 0,1 12,11.5M12,2A7,7 0 0,0 5,9C5,14.25 12,22 12,22C12,22 19,14.25 19,9A7,7 0 0,0 12,2Z"/>
                </svg>
                <span className="font-semibold text-gray-700">Select Parking Lot</span>
            </div>
            <div className="flex gap-2 overflow-x-auto pb-2">
                {sortedLots.map(lot => (
                    <button
                        key={lot.Lot_ID}
                        onClick={() => onSelect(lot.Lot_ID)}
                        className={`relative px-4 py-2 rounded-lg whitespace-nowrap transition flex-shrink-0 ${
                            selectedLot === lot.Lot_ID
                                ? 'bg-green-700 text-white shadow-md'
                                : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
                        }`}
                    >
                        <button
                            onClick={(e) => toggleFavorite(e, lot.Lot_ID)}
                            className="absolute top-1 right-1 text-sm leading-none"
                            aria-label={favorites.has(lot.Lot_ID) ? 'Unfavorite lot' : 'Favorite lot'}
                        >
                            {favorites.has(lot.Lot_ID) ? '★' : '☆'}
                        </button>
                        <div className="font-semibold pr-3">{lot.Lot_Name}</div>
                        <div className="text-xs">{lot.Num_Spaces} open</div>
                    </button>
                ))}
            </div>
        </div>
    );
}

/* GRADING NOTE
   Accept any solution that:
   1. Persists favorites via localStorage (or equivalent durable browser
      storage) keyed in a way that survives a full reload.
   2. Sorts favorited lots to the front without mutating the `lots` prop
      array in place (e.g. using [...lots] or .slice() before .sort()).
   3. Stops the star click from also firing the lot-selection handler
      (e.g. via stopPropagation, or by restructuring so the star isn't
      nested inside the selectable button).
   4. Correctly re-renders the star's filled/empty state as favorites change.
   A nested <button> inside a <button> (as shown above) is invalid HTML in
   strict terms; a solution that instead uses a <span>/<div> with an
   onClick and stopPropagation for the star is equally acceptable and
   arguably better -- don't penalize for choosing that structure instead.
*/
