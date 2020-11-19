package com.awg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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

    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTIST_ID = "artistid";
    public static final String COLUMN_ARTIST_NAME = "name";

    public static final String TABLE_TRACKS = "tracks";
    public static final String COLUMN_TRACK_ID = "trackid";
    public static final String COLUMN_TRACK_NAME = "name";
    //public static final String COLUMN_ALBUM_ID = "albumid";

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

    public List<Artist> queryArtists() {
        try (
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + TABLE_ARTISTS);
        ) {
            List<Artist> artists = new ArrayList<>();
            while (rs.next()) {
                Artist artist = new Artist();
                artist.setId(rs.getInt(COLUMN_ARTIST_ID));
                artist.setName(rs.getString(COLUMN_ARTIST_NAME));
                artists.add(artist);
            }
            return artists;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}