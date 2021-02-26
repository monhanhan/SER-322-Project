package ser322;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author Sam Rondinelli
 *
 *         This class gives us the functionality to do all required database
 *         operations relating to albums.
 *
 */
public class Albums {
    private static Scanner scanner;

    /**
     * This is the menu for this part of the program. The program will return
     * here after every operation until the user inputs the int 0, at which
     * point the program will exit the while loop and return to the main menu.
     *
     * @param inputScanner is our scanner to take input from the user
     * @param args         is a list of command line arguments split on spaces.
     *                     These allows us to make our database connection.
     */
    public static void albumsMenu(Scanner inputScanner, String[] args) {
        scanner = inputScanner;

        System.out.println("Welcome to the Albums menu");
        printAlbumsMenuOptions();

        int inInt = Integer.parseInt(scanner.next());

        while (inInt != 0) {
            switch(inInt) {
                case 1:
                    listAlbums(args);
                    break;
                case 2:
                    searchAlbumsByName(args);
                    break;
                case 3:
                    listAlbumsByReleaseYear(args);
                    break;
                case 4:
                    listSongsOnAlbum(args);
                    break;
                case 5:
                    addSongToAlbum(args);
                    break;
                case 6:
                    changeAlbumName(args);
                    break;
                case 7:
                    changeReleaseYear(args);
                    break;
                case 8:
                    addAlbum(args);
                    break;
                case 9:
                    removeAlbum(args);
                    break;
                default:
                    System.out.println("Sorry, you did not select a valid menu option.");
                    System.out.println();
                    break;
            }

            printAlbumsMenuOptions();
            inInt = Integer.parseInt(scanner.next());
        }
    }

    /**
     * Prints all the menu options
     */
    private static void printAlbumsMenuOptions() {
        System.out.println("Please make a selection");
        System.out.println("0: Return to main menu");
        System.out.println("1: List all albums");
        System.out.println("2: Search albums by name");
        System.out.println("3: Search albums by release year");
        System.out.println("4: List all songs on an album");
        System.out.println("5: Add a song to an album");
        System.out.println("6: Change the name of an album");
        System.out.println("7: Change the release year of an album");
        System.out.println("8: Create an album");
        System.out.println("9: Delete an album");
        System.out.println();
    }

    /**
     * Prints out the column headers for a list of albums
     */
    private static void printAlbumHeader() {
        System.out.printf("%-6s", "ID");
        System.out.printf("%-35s", "Album Title");
        System.out.printf("%-12s \n", "Release Year");
        System.out.print("_________________________________________________________\n");
    }

    /**
     * Prints out a prompt and then reads a string from the user
     *
     * @param prompt The string to prompt the user with
     */
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

    /**
     * Prints out a prompt and then reads an int from the user
     *
     * @param prompt The string to prompt the user with
     */
    private static int promptInt(String prompt) {
        System.out.println(prompt);
        return scanner.nextInt();
    }

    /**
     * Ensures that all database resources are properly cleaned up
     *
     * @param rs ResultSet
     * @param stmt PreparedStatement
     * @param conn Connection
     */
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

    /**
     * Lists out all the albums
     *
     * @param args a list of command line arguments split on spaces,
     *             used to make the database connection
     */
    private static void listAlbums(String[] args) {
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
            stmt = conn.prepareStatement("SELECT *" + "FROM ALBUM");

            // Step 4: Make a query
            rs = stmt.executeQuery();

            printAlbumHeader();

            // Step 5: Display the results
            while (rs.next()) {
                System.out.printf("%-6s", rs.getInt("album_id"));
                System.out.printf("%-35s", rs.getString("title"));
                System.out.printf("%-6s \n", rs.getInt("release_year"));
                System.out.println();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally { // ALWAYS clean up your DB resources
            closeDBResources(rs, stmt, conn);
        }
    }

    /**
     * Searches for an album by name
     *
     * @param args a list of command line arguments split on spaces,
     *             used to make the database connection
     */
    private static void searchAlbumsByName(String[] args) {
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
            stmt = conn.prepareStatement("SELECT *" + "FROM ALBUM WHERE title LIKE ?");

            // Step 4: Prompt for album name
            String albumName = promptString("Please type the name of the album you are looking for: ");
            stmt.setString(1, albumName);

            // Step 4: Make a query
            rs = stmt.executeQuery();

            printAlbumHeader();

            // Step 5: Display the results
            while (rs.next()) {
                System.out.printf("%-6s", rs.getInt("album_id"));
                System.out.printf("%-35s", rs.getString("title"));
                System.out.printf("%-6s \n", rs.getInt("release_year"));
                System.out.println();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally { // ALWAYS clean up your DB resources
            closeDBResources(rs, stmt, conn);
        }
    }

    /**
     * Lists all albums released in a specified year
     *
     * @param args a list of command line arguments split on spaces,
     *             used to make the database connection
     */
    private static void listAlbumsByReleaseYear(String[] args) {
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
            stmt = conn.prepareStatement("SELECT *" + "FROM ALBUM WHERE release_year=?");

            // Step 4: Prompt for release year
            int releaseYear = promptInt("Please type the release year: ");
            stmt.setInt(1, releaseYear);

            // Step 4: Make a query
            rs = stmt.executeQuery();

            printAlbumHeader();

            // Step 5: Display the results
            while (rs.next()) {
                System.out.printf("%-6s", rs.getInt("album_id"));
                System.out.printf("%-35s", rs.getString("title"));
                System.out.printf("%-6s \n", rs.getInt("release_year"));
                System.out.println();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally { // ALWAYS clean up your DB resources
            closeDBResources(rs, stmt, conn);
        }
    }

    /**
     * Lists all songs on a specified album
     *
     * @param args a list of command line arguments split on spaces,
     *             used to make the database connection
     */
    private static void listSongsOnAlbum(String[] args) {
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
            stmt = conn.prepareStatement(
                    "SELECT SONG.song_id,name" + " FROM ALBUM " +
                    "LEFT JOIN HAS ON HAS.album_id=ALBUM.album_id " +
                    "LEFT JOIN SONG ON SONG.song_id=HAS.song_id " +
                    "WHERE ALBUM.album_id=?");

            // Step 4: Prompt for album ID
            int albumId = promptInt("Please type the album ID: ");
            stmt.setInt(1, albumId);

            // Step 4: Make a query
            rs = stmt.executeQuery();

            System.out.println();
            System.out.printf("%-10s", "Song ID");
            System.out.printf("%-35s \n", "Song Name");
            System.out.print("______________________________________\n");

            // Step 5: Display the results
            while (rs.next()) {
                System.out.printf("%-10s", rs.getInt("song_id"));
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

    /**
     * Adds a song to an album
     *
     * @param args a list of command line arguments split on spaces,
     *             used to make the database connection
     */
    private static void addSongToAlbum(String[] args) {
        PreparedStatement stmt = null;
        Connection conn = null;

        int albumId = promptInt("Please type the album ID: ");
        int songId = promptInt("Please type the song ID: ");

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement(
                    "INSERT into HAS(album_id, song_id) \n VALUES(?,?)");

            stmt.setInt(1, albumId);
            stmt.setInt(2, songId);

            try {
                stmt.executeUpdate();
                System.out.println("The song is now added to the album");
                System.out.println();

            } catch (Exception SQLIntegrityConstraintViolationException) {
                System.out.println(
                        "Sorry, that song already exists on this album.");
                System.out.println();

            }

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally { // ALWAYS clean up your DB resources
            closeDBResources(null, stmt, conn);
        }
    }

    /**
     * Changes an album's name
     *
     * @param args a list of command line arguments split on spaces,
     *             used to make the database connection
     */
    private static void changeAlbumName(String[] args) {
        PreparedStatement stmt = null;
        Connection conn = null;

        int albumId = promptInt("Please type the album ID: ");
        String albumName = promptString("Please type the album's new name: ");

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement(
                    "UPDATE ALBUM SET title = ? WHERE album_id = ?");

            stmt.setString(1, albumName);
            stmt.setInt(2, albumId);

            try {
                stmt.executeUpdate();
                System.out.println("You have renamed your album");
                System.out.println();

            } catch (Exception e) {
                System.out.println(
                        "Sorry, something went wrong renaming your album.");
                System.out.println();

            }

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally { // ALWAYS clean up your DB resources
            closeDBResources(null, stmt, conn);
        }
    }

    /**
     * Changes the release year of an album
     *
     * @param args a list of command line arguments split on spaces,
     *             used to make the database connection
     */
    private static void changeReleaseYear(String[] args) {
        PreparedStatement stmt = null;
        Connection conn = null;

        int albumId = promptInt("Please type the album ID: ");
        int releaseYear = promptInt("Please type the album's new release year: ");

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement(
                    "UPDATE ALBUM SET release_year = ? WHERE album_id = ?");

            stmt.setInt(1, releaseYear);
            stmt.setInt(2, albumId);

            try {
                stmt.executeUpdate();
                System.out.println("You have changed the album's release year");
                System.out.println();

            } catch (Exception e) {
                System.out.println(
                        "Sorry, something went wrong changing the release year.");
                System.out.println();

            }

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally { // ALWAYS clean up your DB resources
            closeDBResources(null, stmt, conn);
        }
    }

    /**
     * Creates a new album
     *
     * @param args a list of command line arguments split on spaces,
     *             used to make the database connection
     */
    private static void addAlbum(String[] args) {
        PreparedStatement stmt = null;
        Connection conn = null;

        String albumName = promptString("Please type album name: ");
        int albumId = promptInt("Please type the album ID: ");
        int releaseYear = promptInt("Please type the release year: ");

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement(
                    "INSERT into ALBUM(album_id, title, release_year) \n VALUES(?,?,?)");

            stmt.setInt(1, albumId);
            stmt.setString(2, albumName);
            stmt.setInt(3, releaseYear);

            try {
                stmt.executeUpdate();
                System.out.println("Your album has been created");
                System.out.println();

            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println(
                        "Sorry, an album already exists with that ID number.");
                System.out.println();
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally { // ALWAYS clean up your DB resources
            closeDBResources(null, stmt, conn);
        }
    }

    /**
     * Deletes an album
     *
     * @param args a list of command line arguments split on spaces,
     *             used to make the database connection
     */
    private static void removeAlbum(String[] args) {
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println("Please note: When you delete an album all of its relations get deleted as well");
        int albumId = promptInt("Please enter the ID number of the album you wish to delete: ");

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement("DELETE ALBUM FROM ALBUM WHERE album_id = ?");

            stmt.setInt(1, albumId);

            try {
                stmt.executeUpdate();
                System.out.println("Album deleted");
                System.out.println();

            } catch (Exception SQLIntegrityConstraintViolationException) {
                System.out.println("Something went wrong removing that album");
                System.out.println();
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally { // ALWAYS clean up your DB resources
            closeDBResources(null, stmt, conn);
        }

    }
}
