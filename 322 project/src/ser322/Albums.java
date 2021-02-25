package ser322;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Albums {
    public static void albumsMenu(Scanner inputScanner, String[] args) {
        System.out.println("Welcome to the Albums menu");
        printAlbumsMenuOptions();

        int inInt = Integer.parseInt(inputScanner.next());

        while (inInt != 0) {
            switch(inInt) {
                case 1:
                    listAlbums(args);
                    break;
                case 2:
                    searchAlbumsByName(args);
                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:

                    break;
                case 7:

                    break;
                case 8:

                    break;
                default:
                    System.out.println(
                            "Sorry, you did not select a valid menu option.");
                    System.out.println();
                    break;
            }

            printAlbumsMenuOptions();
            inInt = Integer.parseInt(inputScanner.next());
        }
    }

    private static void printAlbumsMenuOptions() {
        System.out.println("Please make a selection");
        System.out.println("0: Return to main menu");
        System.out.println("1: List all albums");
        System.out.println("2: Search albums by name");

        // Unfinished
        System.out.println("3: Search artist by artist ID");
        System.out.println("4: See which bands an artist is in");
        System.out.println("5: See which songs an artist has preformed");
        System.out.println("6: Add an artist");
        System.out.println("7: Remove an artist");
        System.out.println("8: Remove an artist from a song");
        System.out.println("9: Add an artist to a song");
        System.out.println("10: Remove an artist from a band");
        System.out.println("11: Add an artist to a band");
        System.out.println("12: Change the name of an artist");
        System.out.println();
    }

    private static void printAlbumHeader() {
        System.out.printf("%-6s", "ID");
        System.out.printf("%-35s", "Album Title");
        System.out.printf("%-12s \n", "Release Year");
        System.out.printf("_________________________________________________________\n");
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
}
