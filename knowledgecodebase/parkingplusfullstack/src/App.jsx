import { useState, useEffect } from 'react';
import {fetchLotSummary, fetchSpots } from './services/api';
import Header from './components/Header';
import LotSelector from './components/LotSelector';
import SpotGrid from './components/SpotGrid';
import Footer from './components/Footer';
import './App.css' 

export default function App() {
    const [lots, setLots] = useState([]);
    const [spots, setSpots] = useState([]);
    const [selectedLot, setSelectedLot] = useState(null);
    const [loading, setLoading] = useState(true);
    const [lastUpdate, setLastUpdate] = useState(new Date());


    const loadData = async () => {
        try {
            const [lotData, spotData] = await Promise.all([
                fetchLotSummary(), 
                fetchSpots()
            ]);
            setLots(lotData);
            setSpots(spotData);
            if(!selectedLot && lotData.length > 0) {
                setSelectedLot(lotData[0].Lot_ID);
            }
            setLastUpdate(new Date());
        } catch (error) {
            console.error('Failed to load data:', error);
        } finally {
            setLoading(false);
        }    
    };

    useEffect(() => {
        loadData();
    }, []);

    //auto refresh every 60 seconds
    useEffect(() => {
        const interval = setInterval(loadData, 60000);
        return () => clearInterval(interval);
    }, []);

    const currentLot = lots.find(l => l.Lot_ID === selectedLot);
    const currentSpots = spots.filter(s => s.Lot_ID === selectedLot);

    if (loading) return (
        <div className="min-h-screen flex items-center justify-center bg-green-50">
            <div className="text-green-700 text-xl font-semibold">Loading...</div>
        </div>
    );

    if (lots.length === 0) return <div>No lots found — check your API</div>

    return (
        <div className="min-h-screen bg-gradient-to-b from-green-50 to-white">
            <Header
                lot={currentLot}
                lots={lots}
                onRefresh={loadData}
                lastUpdate={lastUpdate}

            />

            <LotSelector
                lots = {lots}
                selectedLot = {selectedLot}
                onSelect = {setSelectedLot}
            />

            <SpotGrid
                lot = {currentLot}
                spots = {currentSpots}
                lastUpdate = {lastUpdate}
            />
            <Footer/>
        </div>
            
    );
}