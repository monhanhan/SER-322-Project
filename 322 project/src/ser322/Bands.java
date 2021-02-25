package ser322;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Bands {
    public static void bandsMenu(Scanner inputScanner, String[] args) {
        System.out.println("Welcome to the Band menu");
        printBandMenuOptions();

        int inInt = Integer.parseInt(inputScanner.next());

        while (inInt != 0) {
            if (inInt == 1) {
                listBands(args);

            } else if (inInt == 2) {
                searchBandName(args);

            } else if (inInt == 3) {
                searchBandId(inputScanner, args);

            } else if (inInt == 4) {
                viewArtists(inputScanner, args);

            } else if (inInt == 5) {
                removeArtist(inputScanner, args);

            } else if (inInt == 6) {
                deleteBand(inputScanner, args);

            } else if (inInt == 7) {
                addBand(inputScanner, args);

            } else if (inInt == 8) {
                changeBandName(inputScanner, args);

            } else {
                System.out.println(
                        "Sorry, you did not select a valid menu option.");
                System.out.println();
            }

            printBandMenuOptions();

            inInt = Integer.parseInt(inputScanner.next());
        }

    }

    private static void printBandMenuOptions() {
        System.out.println("Please make a selection");
        System.out.println("0: Return to main menu");
        System.out.println("1: List all bands");
        System.out.println("2: Seach bands by name");
        System.out.println("3: Search bands by band ID");
        System.out.println("4: See which artists are in a band");
        System.out.println("5: Remove and artist from a band");
        System.out.println("6: Delete a band");
        System.out.println("7: Add a band");
        System.out.println("8: Change a band's name");

    }

    private static void listBands(String[] args) {
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
            stmt = conn.prepareStatement("SELECT *" + "FROM BAND");

            // Step 4: Make a query
            rs = stmt.executeQuery();

            // Step 5: Display the results
            while (rs.next()) {
                System.out.printf("%-15s", rs.getInt("band_id"));
                System.out.printf("%-35s \n", rs.getString("band_name"));
                System.out.println();

            }
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally { // ALWAYS clean up your DB resources
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

    }

    private static void searchBandName(String[] args) {

    }

    private static void searchBandId(Scanner inputScanner, String[] args) {

    }

    private static void viewArtists(Scanner inputScanner, String[] args) {

    }

    private static void removeArtist(Scanner inputScanner, String[] args) {

    }

    private static void removeAllArtist(Scanner inputScanner, String[] args) {

    }

    private static void deleteBand(Scanner inputScanner, String[] args) {

    }

    private static void addBand(Scanner inputScanner, String[] args) {

    }

    private static void changeBandName(Scanner inputScanner, String[] args) {

    }

}
