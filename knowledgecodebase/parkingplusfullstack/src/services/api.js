const BASE_URL = import.meta.env.VITE_API_URL || '';

export const fetchLotSummary = () =>
    fetch(`${BASE_URL}/api/lots/summary`).then(r=> r.json());

export const fetchSpotTypeBreakdown = () =>
    fetch(`${BASE_URL}/api/spots/type`).then(r => r.json());

export const fetchSpots = () =>
    fetch(`${BASE_URL}/api/spots/all`).then(r => r.json()); 

export const fetchOfflineSensors= () =>
    fetch(`${BASE_URL}/api/sensors/offline`).then(r => r.json());

export const fetchActiveLots= () =>
    fetch(`${BASE_URL}/api/lots/active`).then(r => r.json());

export const fetchNearCapacity= () =>
    fetch(`${BASE_URL}/api/lots/nearcap`).then(r => r.json());


export const fetchHourlyOccupancy= () =>
    fetch(`${BASE_URL}/api/occupancy/hourly`).then(r => r.json());

export const fetchDailyOccupancy= () =>
    fetch(`${BASE_URL}/api/occupancy/daily`).then(r => r.json());

export const fetchPeakOccupancy= () =>
    fetch(`${BASE_URL}/api/occupancy/peak`).then(r => r.json());