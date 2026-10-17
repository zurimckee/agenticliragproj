
export default function Header({ lot, lots =[], onRefresh}) {

    const safeLots = Array.isArray(lots) ? lots : [];
    const totalAvailable = safeLots.reduce((sum, l) => sum + (Number(l.Num_Spaces) || 0), 0);
    const totalSpaces = safeLots.reduce((sum, l) => sum + Number((l.Total_Spaces) || 0), 0);
    const percent = totalSpaces > 0 
        ? Math.round((totalAvailable / totalSpaces) * 100) 
        : 0;

    return (     
        <div className="bg-gradient-to-r from-green-700 to-green-600 text-white p-4 shadow-lg">
            <div className="flex items-center justify-between mb-4">
                <div className="flex items-center gap-3">
                    <div className="w-10 h-10 bg-white rounded-full flex items-center justify-center">
                        <svg className="text-green-700" width="24" height="24" fill="currentColor" viewBox="0 0 24 24">
                            <path d="M5 11l1.5-4.5h11L19 11m-1.5 5h-11L5 11h14l-1.5 5M12 18.5A1.5 1.5 0 0 1 10.5 17 1.5 1.5 0 0 1 12 15.5a1.5 1.5 0 0 1 1.5 1.5 1.5 1.5 0 0 1-1.5 1.5m-5 0A1.5 1.5 0 0 1 5.5 17 1.5 1.5 0 0 1 7 15.5 1.5 1.5 0 0 1 8.5 17 1.5 1.5 0 0 1 7 18.5z"/>
                        </svg>
                    </div>
                    <div>
                        <h1 className="text-2xl font-bold">Smart Parking+</h1>
                        <p className="text-xs text-green-100">UNC Charlotte Parking</p>
                    </div>
                </div>
                <button
                    onClick={onRefresh}
                    className="p-2 hover:bg-green-800 rounded-full transition"
                >
                    <svg width="20" height="20" fill="currentColor" viewBox="0 0 24 24">
                        <path d="M17.65,6.35C16.2,4.9 14.21,4 12,4A8,8 0 0,0 4,12A8,8 0 0,0 12,20C15.73,20 18.84,17.45 19.73,14H17.65C16.83,16.33 14.61,18 12,18A6,6 0 0,1 6,12A6,6 0 0,1 12,6C13.66,6 15.14,6.69 16.22,7.78L13,11H20V4L17.65,6.35Z"/>
                    </svg>
                </button>
            </div>

            {/* Quick Stats - same layout as your prototype */}
            <div className="grid grid-cols-3 gap-2 text-center">
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
            </div>
        </div>
    );
}
