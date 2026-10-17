import express from 'express';
import cors from 'cors';
import dotenv from 'dotenv';
import pool from './dbcall.js';

dotenv.config();

const app = express();
app.use(cors({
    origin:[
        /\.vercel\.app$/,
    'http://localhost:5173'
    ],
    methods: ['GET', 'POST', 'PUT', 'DELETE'],
    credentials: true
}));

app.use(express.json());


// lots enpoint - lot occupancy summary
app.get('/api/lots/summary', async (req, res) => {
    try {
        const [rows] = await pool.query('SELECT ls.*, l.Latitude, l.Longitude, l.Lot_Type FROM Lot_Status_Summary ls JOIN Lots l ON ls.Lot_ID = l.Lot_ID');
        res.json(rows);
    
    } catch (error) {
        res.status(500).json({error: error.message});
    }
});

// grab all spots
app.get('/api/spots/all', async (req, res) => {
    try {
        const [rows] = await pool.query('SELECT s.*, l.Lot_Type FROM Spots s JOIN Lots l ON s.Lot_ID = l.Lot_ID');
        res.json(rows);
    } catch (error) {
        res.status(500).json({error: error.message});
    }
});

//spots type breakdown
app.get('/api/spots/type', async (req, res) => {
    try {
        const [rows] = await pool.query('SELECT * FROM Spot_Status_Summary');
        res.json(rows);
    } catch (error) {
        res.status(500).json({error: error.message});
    }
});




// offline sensor count
app.get('/api/sensors/offline', async (req, res) => {
    try{
        const [rows] = await pool.query('SELECT COUNT(*) AS offline_count FROM Sensor_Health WHERE Is_Online = FALSE')
        res.json(rows[0]);
    } catch (error) {
        res.status(500).json({error: error.message});
    }
});

//num of active lots
app.get('/api/lots/active', async (req, res) => {
    try {
         const [rows] = await pool.query('SELECT COUNT(*) AS active_count FROM Lots WHERE Is_Active = TRUE')
        res.json(rows[0]);
    } catch (error) {
        res.status(500).json({error: error.message});
    }
   
});

//lots near capacity
app.get('/api/lots/nearcap', async (req, res) => {
    try {
        const [rows] = await pool.query('SELECT * FROM Near_Capacity');
        res.json(rows);
    } catch (error) {
        res.status(500).json({error: error.message});
    }
});

//hourly occupancy trends
app.get('/api/occupancy/hourly', async (req, res) => {
    try { 
        const [rows] = await pool.query('SELECT * FROM Hourly_Occupancy');
        res.json(rows);
    } catch (error) {
        res.status(500).json({error: error.message});
    }
});

//daily occupancy trends
app.get('/api/occupancy/daily', async (req, res) => {
    try { 
        const [rows] = await pool.query('SELECT * FROM Daily_Occupancy');
        res.json(rows);
    } catch (error) {
        res.status(500).json({error: error.message});
    }
});

//peak occupancy trends
app.get('/api/occupancy/peak', async (req, res) => {
    try { 
        const [rows] = await pool.query('SELECT * FROM Peak_Occupancy LIMIT 1');
        res.json(rows[0]);
    } catch (error) {
        res.status(500).json({error: error.message});
    }
});


const PORT = process.env.PORT || 3001; 
app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`)
});
