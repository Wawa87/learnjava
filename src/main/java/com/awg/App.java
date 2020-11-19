package com.awg;

import java.sql.Statement;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

/**
 * Hello world!
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
        System.out.println( "Hello Universe!" );

        DataSource ds = new DataSource();
        if (!ds.open()) {
            System.out.println("Can't open the data source!");
            return;
        }

        List<Artist> artists = ds.queryArtists();
        if (artists == null) {
            System.out.println("No artists!");
            return;
        }

        for (Artist artist : artists) {
            System.out.println("ID=" + artist.getId() + ", Name=" + artist.getName());
        }
        ds.close();

        // try {
        //     Connection con = DriverManager.getConnection(CONNECTION_STRING);
        //     Statement stmt = con.createStatement();
            
        //     stmt.execute("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        //     stmt.execute("CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + " ("
        //         + COLUMN_NAME + " TEXT,"
        //         + COLUMN_PHONE + " INTEGER,"
        //         + COLUMN_EMAIL + " TEXT)");

        //     insertContact(stmt, "Chocolate", 1234, "chocolate@dogmail.com");
        //     insertContact(stmt, "Coco", 4321, "coco@catmail.com");
        //     insertContact(stmt, "Dende", 3256, "dende@dogmail.com");

        //     ResultSet rs = stmt.executeQuery("SELECT * FROM contacts");
        //     while (rs.next()) {
        //         System.out.println(rs.getString(1));
        //         System.out.println(rs.getString(2));
        //         System.out.println(rs.getString(3));
        //     }
        //     rs.close();
        //     stmt.close();
        //     con.close();
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }
    }

    public static void insertContact(Statement stmt, String name, int phone, String email) throws SQLException {
        stmt.execute("INSERT INTO " + TABLE_CONTACTS + " (" + COLUMN_NAME + "," + COLUMN_PHONE + "," + COLUMN_EMAIL + ") VALUES ('"
        + name + "','" + phone + "','" + email + "')");
    }
}
