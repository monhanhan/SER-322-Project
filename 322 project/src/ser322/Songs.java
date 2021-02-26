package ser322;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Songs {
    private static Scanner scanner;

    public static void songMenu(Scanner inputScanner, String[] args) {
        scanner = inputScanner;
        
        System.out.println("Welcome to the Songs menu");
        printArtistMenuOptions();

        int inInt = Integer.parseInt(scanner.next());

        while (inInt != 0) {
            if (inInt == 1) {
                listSongs(args);

            } else if (inInt == 2) {
                searchSongName(args);

            } else if (inInt == 3) {
                searchSongId(args);

            } else if (inInt == 4) {
                listArtists(args);

            } else if (inInt == 5) {
                addSong(args);

            } else if (inInt == 6) {
                removeSong(args);

            } else if (inInt == 7) {
                removeArtistSong(args);

            } else if (inInt == 8) {
                addArtistSong(args);

            } else if (inInt == 9) {
                changeSongName(args);

            } else {
                System.out.println("Sorry, you did not select a valid menu option.");
                System.out.println();
            }

            printArtistMenuOptions();

            inInt = Integer.parseInt(scanner.next());
        }

    }

    private static void printArtistMenuOptions() {
        System.out.println("Please make a selection");
        System.out.println("0: Return to main menu");
        System.out.println("1: List all songs");
        System.out.println("2: Search songs by name");
        System.out.println("3: Search songs by song ID");
        System.out.println("4: See which artists have performed a song");
        System.out.println("5: Add a song");
        System.out.println("6: Remove a song");
        System.out.println("7: Remove an artist from a song");
        System.out.println("8: Add an artist to a song");
        System.out.println("9: Change the name of a song");
        System.out.println();
    }

    private static void closeDBResources(ResultSet rs, PreparedStatement stmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (Throwable t1) {
            System.out.println("A problem closing db resources!");
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Throwable t2) {
            System.out.println("Oh-oh! Connection leaked!");
        }
    }

    private static String promptString(String prompt) {
        System.out.println(prompt);

        // We need a buffered reader so that we can take input that has
        // whitespace. When our scanner object gets closed it will close
        // buffered reader as well since they both use system.in. We cannot
        // close system.in without closing it for the entire program, so we
        // shouldn't close it here.
        BufferedReader stdin = new BufferedReader(
                new InputStreamReader(System.in));

        String value = "";

        try {
            value = stdin.readLine();
        } catch (IOException e) {
            System.out.println(
                    "Something went wrong. The system is going to crash now. Will I dream? Daaaisy, Daaaaisy");
        }

        return value;
    }

    private static int promptInt(String prompt) {
        System.out.println(prompt);
        return scanner.nextInt();
    }

    //method to handle option 1
    private static void listSongs(String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement("SELECT * FROM SONG");

            // Step 4: Make a query
            rs = stmt.executeQuery();

            // Step 5: Display the results
            while (rs.next()) {
                System.out.printf("%-15s", rs.getInt("song_id"));
                System.out.printf("%-25s", rs.getInt("release_year"));
                System.out.printf("%-35s \n", rs.getString("name"));
                System.out.println();
            }
            System.out.println();
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally { // ALWAYS clean up your DB resources
            closeDBResources(rs, stmt, conn);
        }

    }

    //method to handle option 2
    private static void searchSongName(String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        String songName = promptString("Please type the name of song you are looking for: ");

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement("SELECT *" + "FROM SONG WHERE name=?");

            stmt.setString(1, songName);

            // Step 4: Make a query
            rs = stmt.executeQuery();

            // Step 5: Display the results
            while (rs.next()) {
                System.out.printf("%-6s", rs.getInt("song_id"));
                System.out.printf("%-10s", rs.getInt("release_year"));
                System.out.printf("%-35s \n", rs.getString("name"));
                System.out.println();
            }
            System.out.println();
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally { // ALWAYS clean up your DB resources
            closeDBResources(rs, stmt, conn);
        }
    }

    //method to handle option 3
    private static void searchSongId(String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        int song_id = promptInt("Please type the ID of your song: ");

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement("SELECT *" + "FROM SONG WHERE song_id=?");

            stmt.setInt(1, song_id);

            // Step 4: Make a query
            rs = stmt.executeQuery();

            // Step 5: Display the results
            while (rs.next()) {
                System.out.printf("%-6s", rs.getInt("song_id"));
                System.out.printf("%-10s", rs.getInt("release_year"));
                System.out.printf("%-35s \n", rs.getString("name"));
                System.out.println();
            }
            System.out.println();
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally { // ALWAYS clean up your DB resources
            closeDBResources(rs, stmt, conn);
        }
    }

    //method to handle option 4
    private static void listArtists(String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        int songId = promptInt("Please type the ID of your song: ");

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement(
                    "SELECT ARTIST.artist_id,ARTIST.name FROM SONG " +
                    "LEFT JOIN PERFORMS ON PERFORMS.song_id=SONG.song_id " +
                    "LEFT JOIN ARTIST ON ARTIST.artist_id=PERFORMS.artist_id " +
                    "WHERE SONG.song_id=?");

            stmt.setInt(1, songId);

            // Step 4: Make a query
            rs = stmt.executeQuery();

            // Step 5: Display the results
            while (rs.next()) {
                System.out.printf("%-15s", rs.getInt("artist_id"));
                System.out.printf("%-35s", rs.getString("name"));
                System.out.println();
            }
            System.out.println();
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally { // ALWAYS clean up your DB resources
            closeDBResources(rs, stmt, conn);
        }

    }

    //method to handle option 5
    private static void addSong(String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        String songName = promptString("Please type the song name: ");
        int songId = promptInt("Please type the song ID: ");
        int releaseYear = promptInt("Please type the release year: ");

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement(
                    "INSERT into SONG(song_id, release_year , name) \n VALUES(?,?,?)");

            stmt.setInt(1, songId);
            stmt.setInt(2, releaseYear);
            stmt.setString(3, songName);

            try {
                stmt.executeUpdate();
                System.out.println("Your song has been added");
                System.out.println();

            } catch (Exception SQLIntegrityConstraintViolationException) {
                System.out.println(
                        "Sorry, a song already exists with that ID number.");
                System.out.println();
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally { // ALWAYS clean up your DB resources
            closeDBResources(rs, stmt, conn);
        }
    }

    /**
     * This removes a song and all its relations.(option 6)
     *
     * @param args         are the command line arguments that allow us to
     *                     connect to our database.
     */
    private static void removeSong (String[] args){
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println("Please note: When you delete an song all of their relations get deleted as well");
        int songId = promptInt("Please enter the ID number of the song you wish to delete: ");

        // This is where we remove all relations.
        removeAllSongRelations(songId, args);

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement("DELETE SONG\n" + "FROM SONG\n"
                    + "Where song_id = ?");

            stmt.setInt(1, songId);

            try {
                stmt.executeUpdate();
                System.out.println("Song deleted");
                System.out.println();

            } catch (Exception SQLIntegrityConstraintViolationException) {
                System.out.println("Something went wrong removing that song");
                System.out.println();

            }

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally { // ALWAYS clean up your DB resources
            closeDBResources(rs, stmt, conn);
        }
    }

    /**
     * This method allows us to remove the relation between an artist and a song (option 7)
     *
     * @param args         command line arguments that allows us to make a
     *                     database connection
     */
    private static void removeArtistSong (String[] args){
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        int artistId = promptInt("Please type the artist ID number: ");
        int songId = promptInt("Please type the song Id number: ");

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement("DELETE PERFORMS\n" + "From PERFORMS\n"
                    + "Where artist_id = ? AND song_id = ?");

            stmt.setInt(1, artistId);
            stmt.setInt(2, songId);

            try {
                stmt.executeUpdate();
                System.out.println("You have removed the artist from the song");
                System.out.println();

            } catch (Exception e) {
                System.out.println(
                        "Sorry, something went wrong removing that artist from that song");
                System.out.println();

            }

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally { // ALWAYS clean up your DB resources
            closeDBResources(rs, stmt, conn);
        }
    }

    /**
     * This allows us to make a new relation between an artist and a song. If
     * the relation already exists an error message is displayed and nothing is
     * done
     * (handles option 8)
     * @param args         are the command line arguments that allow us to make
     *                     a connection
     */
    private static void addArtistSong (String[] args){
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        int artistId = promptInt("Please type the artist Id number: ");
        int songId = promptInt("Please type the song Id number: ");

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement(
                    "INSERT into PERFORMS(artist_id, song_id) \n VALUES(?,?)");

            stmt.setInt(1, artistId);
            stmt.setInt(2, songId);

            try {
                stmt.executeUpdate();
                System.out.println("The artist is now listed for this song");
                System.out.println();

            } catch (Exception SQLIntegrityConstraintViolationException) {
                System.out.println(
                        "Sorry, that artist was already listed for that song.");
                System.out.println();

            }

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally { // ALWAYS clean up your DB resources
            closeDBResources(rs, stmt, conn);
        }
    }


    /**
     * This method allows us to change the name of our song
     *  (option 9)
     * @param args         is a command line argument that allows us to connect
     *                     to our database
     */
    private static void changeSongName (String[] args){
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        int songId = promptInt("Please type the song Id number: ");
        String songName = promptString("Please type your song's new name: ");

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement(
                    "UPDATE SONG SET name = ? WHERE song_id = ?");

            stmt.setString(1, songName);
            stmt.setInt(2, songId);

            try {
                stmt.executeUpdate();
                System.out.println("You have renamed your song");
                System.out.println();

            } catch (Exception e) {
                System.out.println(
                        "Sorry, something went wrong renaming your song. I hear the name 'Bad Luck' is available");
                System.out.println();

            }

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally { // ALWAYS clean up your DB resources
            closeDBResources(rs, stmt, conn);
        }
    }

    /**
     * This is a helper function that will allow us to remove a song
     * completely from the system. This will erase all connections to artists when
     * you delete the song
     *
     * @param songId is the id of the song being removed.
     * @param args     are the command line arguments that will allow us to make
     *                 a connection.
     */
    private static void removeAllSongRelations ( int songId, String[] args){
        ResultSet rs = null;
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        Connection conn = null;

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement("DELETE PERFORMS\n" + "FROM PERFORMS\n"
                    + "Where song_id = ?");

            stmt2 = conn.prepareStatement("DELETE HAS\n" + "FROM HAS\n"
                    + "Where song_id = ?");

            stmt.setInt(1, songId);
            stmt2.setInt(1, songId);

            try {
                stmt.executeUpdate();
                stmt2.executeUpdate();

                System.out.println("All song relations deleted");

            } catch (Exception e) {
                System.out.println(
                        "Something went wrong deleting the songs and the artists associated with it.");
                System.out.println();

            }

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally { // ALWAYS clean up your DB resources
            closeDBResources(rs, stmt, conn);
        }
    }
}
