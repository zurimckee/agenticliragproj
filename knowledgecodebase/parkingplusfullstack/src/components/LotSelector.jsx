export default function LotSelector({ lots, selectedLot, onSelect }) {
    return (
        <div className="p-4 bg-white shadow">
            <div className="flex items-center gap-2 mb-3">
                <svg className="text-green-700" width="20" height="20" fill="currentColor" viewBox="0 0 24 24">
                    <path d="M12,11.5A2.5,2.5 0 0,1 9.5,9A2.5,2.5 0 0,1 12,6.5A2.5,2.5 0 0,1 14.5,9A2.5,2.5 0 0,1 12,11.5M12,2A7,7 0 0,0 5,9C5,14.25 12,22 12,22C12,22 19,14.25 19,9A7,7 0 0,0 12,2Z"/>
                </svg>
                <span className="font-semibold text-gray-700">Select Parking Lot</span>
            </div>
            <div className="flex gap-2 overflow-x-auto pb-2">
                {lots.map(lot => (
                    <button
                        key={lot.Lot_ID}
                        onClick={() => onSelect(lot.Lot_ID)}
                        className={`px-4 py-2 rounded-lg whitespace-nowrap transition flex-shrink-0 ${
                            selectedLot === lot.Lot_ID
                                ? 'bg-green-700 text-white shadow-md'
                                : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
                        }`}
                    >
                        <div className="font-semibold">{lot.Lot_Name}</div>
                        <div className="text-xs">{lot.Num_Spaces} open</div>
                    </button>
                ))}
            </div>
        </div>
    );
}