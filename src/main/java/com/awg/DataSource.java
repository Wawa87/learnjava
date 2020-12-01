package com.awg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataSource {
    public static final String DB_NAME = "chinook.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:Z:/dev/learnjava/" + DB_NAME;

    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ALBUM_ID = "albumid";
    public static final String COLUMN_ALBUM_TITLE = "title";
    public static final String COLUMN_ALBUM_ARTIST = "artistid";
    public static final int INDEX_ALBUM_ID = 1;
    public static final int INDEX_ALBUM_NAME = 2;
    public static final int INDEX_ALBUM_ARTIST = 3;

    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTIST_ID = "artistid";
    public static final String COLUMN_ARTIST_NAME = "name";
    public static final int INDEX_ARTIST_ID = 1;
    public static final int INDEX_ARTIST_NAME = 2;
    
    public static final String TABLE_TRACKS = "tracks";
    public static final String COLUMN_TRACK_ID = "trackid";
    public static final String COLUMN_TRACK_NAME = "name";
    public static final int INDEX_TRACK_ID = 1;
    public static final int INDEX_TRACK = 2;
    public static final int INDEX_TRACK_TITLE = 3;
    public static final int INDEX_TRACK_ALBUM = 4;

    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2;
    public static final int ORDER_BY_DESC = 3;

    public static final String QUERY_ALBUMS_BY_ARTIST_START = "SELECT " + TABLE_ALBUMS + "." + COLUMN_ALBUM_TITLE + " FROM " + TABLE_ALBUMS + " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST + " = " + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID + " WHERE " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + " = \"";
    public static final String QUERY_ALBUMS_BY_ARTIST_SORT = " ORDER BY " + TABLE_ALBUMS + "." + COLUMN_ALBUM_TITLE + " COLLATE NOCASE ";
    public static final String QUERY_ARTIST_FOR_SONG_START = "SELECT " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " + TABLE_ALBUMS + "." + COLUMN_ALBUM_TITLE + ", " + TABLE_TRACKS + "." + COLUMN_TRACK_NAME + " FROM " + TABLE_TRACKS
                                                            + " INNER JOIN " + TABLE_ALBUMS + " ON "
                                                            + TABLE_TRACKS + "." + COLUMN_ALBUM_ID + " = " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ID
                                                            + " INNER JOIN " + TABLE_ARTISTS + " ON "
                                                            + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST + " = " + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID
                                                            + " WHERE " + TABLE_TRACKS + "." + COLUMN_TRACK_NAME + " =\"";

    public static final String TABLE_ARTIST_SONG_VIEW = "artist_list";
    public static final String CREATE_ARTIST_FOR_SONG_VIEW = "CREATE VIEW IF NOT EXISTS " + 
        TABLE_ARTIST_SONG_VIEW + " AS SELECT " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " + 
        TABLE_ALBUMS + "." + COLUMN_ALBUM_TITLE + " AS " + COLUMN_ALBUM_TITLE;

    private Connection con;

    public boolean open() {
        try {
            con = DriverManager.getConnection(CONNECTION_STRING);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Artist> queryArtists(int sortOrder) {
        StringBuilder sb = new StringBuilder();
        sb.append(TABLE_ARTISTS);
        if (sortOrder != ORDER_BY_NONE) {
            sb.append(" ORDER BY ");
            sb.append(COLUMN_ARTIST_NAME);
            sb.append(" COLLATE NOCASE ");
            if (sortOrder == ORDER_BY_DESC) {
                sb.append("DESC");
            } else {
                sb.append("ASC");
            }
        }

        try (
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + sb.toString());
        ) {
            List<Artist> artists = new ArrayList<>();
            while (rs.next()) {
                Artist artist = new Artist();
                artist.setId(rs.getInt(INDEX_ARTIST_ID));
                artist.setName(rs.getString(INDEX_ARTIST_NAME));
                artists.add(artist);
            }
            return artists;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> queryAlbumsForArtist(String artistName, int sortOrder) {
        StringBuilder sb = new StringBuilder();
        sb.append(QUERY_ALBUMS_BY_ARTIST_START);
        sb.append(artistName);
        sb.append("\"");

        if (sortOrder != ORDER_BY_NONE) {
            sb.append(" ORDER BY ");
            sb.append(TABLE_ALBUMS);
            sb.append(".");
            sb.append(COLUMN_ALBUM_TITLE);
            sb.append(" COLLATE NOCASE ");
            if (sortOrder == ORDER_BY_DESC) {
                sb.append("DESC");
            } else {
                sb.append("ASC");
            }
        }

        System.out.println("SQL statement = " + sb.toString());

        try (
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sb.toString())
        ) {
            List<String> albums = new ArrayList<>();
            while (rs.next()) {
                albums.add(rs.getString(1));
            }
            return albums;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SongArtist> queryArtistsForSong(String songName, int sortOrder) {
        StringBuilder sb = new StringBuilder(QUERY_ARTIST_FOR_SONG_START);
        sb.append(songName);
        sb.append("\"");

        if (sortOrder != ORDER_BY_NONE) {
            sb.append(QUERY_ALBUMS_BY_ARTIST_SORT);
            if (sortOrder == ORDER_BY_DESC) {
                sb.append("DESC");
            } else {
                sb.append("ASC");
            }
        }

        System.out.println("SQL Statement = " + sb.toString());

        try (
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sb.toString())
        ) {
            List<SongArtist> songArtists = new ArrayList<>();
            while (rs.next()) {
                SongArtist songArtist = new SongArtist();
                songArtist.setArtistName(rs.getString(1));
                songArtist.setAlbumName(rs.getString(2));
                songArtist.setTrack(rs.getInt(3));
                songArtists.add(songArtist);
            }
            return songArtists;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void querySongsMetaData() {
        String sql = "SELECT * FROM " + TABLE_TRACKS;

        try (
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        ) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumns = rsmd.getColumnCount();
            for (int i=1; i<=numColumns; i++) {
                System.out.format("Column %d in the songs table is named %s\n", i, rsmd.getColumnName(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCount(String table) {
        String sql = "SELECT COUNT(*) AS count FROM " + table;
        try (
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        ) {
            int count = rs.getInt(1);
            System.out.format("Count = %d\n", count);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}