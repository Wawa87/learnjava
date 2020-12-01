package com.awg;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Hello universe!
 *
 */
public class App {
    public static final String DB_NAME = "test.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:Z:/dev/learnjava/" + DB_NAME;
    public static final String TABLE_CONTACTS = "contacts";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "email";

    public static void main( String[] args ) {
    //     System.out.println( "Hello Universe!" );

    //     DataSource ds = new DataSource();
    //     if (!ds.open()) {
    //         System.out.println("Can't open the data source!");
    //         return;
    //     }

    //     List<Artist> artists = ds.queryArtists(DataSource.ORDER_BY_DESC);
    //     if (artists == null) {
    //         System.out.println("No artists!");
    //         return;
    //     }

    //     for (Artist artist : artists) {
    //         System.out.println("ID=" + artist.getId() + ", Name=" + artist.getName());
    //     }

    //     List<String> albums = ds.queryAlbumsForArtist("Iron Maiden", DataSource.ORDER_BY_ASC);
    //     if (albums == null) {
    //         System.out.println("No albums!");
    //         return;
    //     }

    //     for (String album : albums) {
    //         System.out.println(album);
    //     }

    //     List<SongArtist> songArtists = ds.queryArtistsForSong("Balls to the Wall", DataSource.ORDER_BY_ASC);
    //     if (songArtists == null) {
    //         System.out.println("Couldn't find any artists!");
    //         return;
    //     }

    //     for (SongArtist artist : songArtists) {
    //         System.out.println("Artist name = " + artist.getArtistName() +
    //             " Album name = " + artist.getAlbumName() +
    //             " Track = " + artist.getTrack());
    //     }

    //     ds.querySongsMetaData();

    //     int count = ds.getCount(DataSource.TABLE_TRACKS);
    //     System.out.println("Number of songs: " + count);

    //     ds.close();

        
    }

    // public static void insertContact(Statement stmt, String name, int phone, String email) throws SQLException {
    //     stmt.execute("INSERT INTO " + TABLE_CONTACTS + " (" + COLUMN_NAME + "," + COLUMN_PHONE + "," + COLUMN_EMAIL + ") VALUES ('"
    //     + name + "','" + phone + "','" + email + "')");
    // }
    
}
