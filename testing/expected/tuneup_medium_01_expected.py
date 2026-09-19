# ===== search.py =====
# search_tracks now accepts and applies offset

def search_tracks(conn, query, limit=25, offset=0):
    cleaned = re.sub(r'["*():^-]', ' ', query).strip()
    if not cleaned:
        return []

    fts_query = f'"{cleaned}"*'

    cursor = conn.execute("""
        SELECT tracks.id, tracks.r2_key, tracks.title, tracks.artist,
               tracks.album, tracks.duration
        FROM tracks_fts
        JOIN tracks ON tracks.id = tracks_fts.rowid
        WHERE tracks_fts MATCH ?
        ORDER BY rank
        LIMIT ? OFFSET ?
    """, (fts_query, limit, offset))

    return [
        {
            "id": row[0], "r2_key": row[1], "title": row[2],
            "artist": row[3], "album": row[4], "duration": row[5],
        }
        for row in cursor.fetchall()
    ]


# ===== app.py =====
# pass the already-parsed offset through to search_tracks

    if query:
        results = search_tracks(db_conn, query, limit=limit, offset=offset)
        return jsonify({"results": results, "query": query})

# GRADING NOTE
# Accept any solution where:
# 1. search_tracks() takes an offset parameter (any reasonable default,
#    e.g. 0) and applies it via SQL OFFSET (or equivalent result slicing).
# 2. app.py's /library route passes its already-parsed `offset` value
#    through to search_tracks() when a search query is present.
# 3. The FTS5 query construction (the re.sub cleaning step + fts_query
#    format) is left untouched.
