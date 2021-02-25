package ser322;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
                Artists.removeArtistBand(inputScanner, args);

            } else if (inInt == 6) {
                Artists.addArtistBand(inputScanner, args);

            } else if (inInt == 7) {
                deleteBand(inputScanner, args);

            } else if (inInt == 8) {
                addBand(inputScanner, args);

            } else if (inInt == 9) {
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
        System.out.println("5: Remove an artist from a band");
        System.out.println("6: Add an artist to a band");
        System.out.println("7: Delete a band");
        System.out.println("8: Add a band");
        System.out.println("9: Change a band's name");

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
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println("Please type the band you are looking for");

        // We need a buffered reader so that we can take input that has
        // whitespace. When our scanner object gets closed it will close
        // buffered reader as well since they both use system.in. We cannot
        // close system.in without closing it for the entire program, so we
        // shouldn't close it here.
        BufferedReader stdin = new BufferedReader(
                new InputStreamReader(System.in));

        String bandName = "";

        try {
            bandName = stdin.readLine();
        } catch (IOException e) {
            System.out.println(
                    "Something went wrong with taking your band name. The system is going to crash now. Will I dream? Daaaisy, Daaaaisy");
        }

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement(
                    "SELECT *" + "FROM BAND WHERE band_name=?");

            stmt.setString(1, bandName);

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

    private static void searchBandId(Scanner inputScanner, String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println("Please type the ID of your band");

        String bandId = inputScanner.next();
        System.out.println();

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn
                    .prepareStatement("SELECT *" + "FROM BAND WHERE band_id=?");

            stmt.setString(1, bandId);

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

    private static void viewArtists(Scanner inputScanner, String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println("Please type the ID of your band");

        String bandId = inputScanner.next();
        System.out.println();

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement(
                    "SELECT * FROM ARTIST WHERE artist_id IN (SELECT artist_id FROM FORMS WHERE band_id = ?)");

            stmt.setString(1, bandId);

            // Step 4: Make a query
            rs = stmt.executeQuery();

            // Step 5: Display the results
            while (rs.next()) {
                System.out.printf("%-15s", rs.getInt("artist_id"));
                System.out.printf("%-35s \n", rs.getString("name"));
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

    private static void removeAllArtist(int bandId, String[] args) {
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
                    "DELETE FORMS\n" + "FROM FORMS\n" + "Where band_id = ?");

            stmt.setInt(1, bandId);

            try {
                stmt.executeUpdate();
                System.out.println("All artist relations deleted");

            } catch (Exception e) {
                System.out.println(
                        "Something went wrong deleting the artists the band is associated with.");
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

    private static void deleteBand(Scanner inputScanner, String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println(
                "Please enter the ID number of the band you wish to delete.");
        System.out.println(
                "Please note: When you delete an band all of their relations get deleted as well");
        System.out.println();

        int bandId = Integer.parseInt(inputScanner.next());

        // This is where we remove all relations.
        removeAllArtist(bandId, args);

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement(
                    "DELETE BAND\n" + "FROM BAND\n" + "Where band_id = ?");

            stmt.setInt(1, bandId);

            try {
                stmt.executeUpdate();
                System.out.println("Band deleted");
                System.out.println();

            } catch (Exception SQLIntegrityConstraintViolationException) {
                System.out.println("Something went wrong removing that band");
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

    private static void addBand(Scanner inputScanner, String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println("Please type the name of the band");

        // We need a buffered reader so that we can take input that has
        // whitespace. When our scanner object gets closed it will close
        // buffered reader as well since they both use system.in. We cannot
        // close system.in without closing it for the entire program, so we
        // shouldn't close it here.
        BufferedReader stdin = new BufferedReader(
                new InputStreamReader(System.in));

        String bandName = "";

        try {
            bandName = stdin.readLine();
            System.out.println();
        } catch (IOException e) {
            System.out.println(
                    "Something went wrong with taking your band name. The system is going to crash now. Will I dream? Daaaisy, Daaaaisy");
        }

        System.out.println("Please type the band Id number");
        int bandId = Integer.parseInt(inputScanner.next());

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement(
                    "INSERT into BAND(band_id, band_name) \n VALUES(?,?)");

            stmt.setInt(1, bandId);
            stmt.setString(2, bandName);

            try {
                stmt.executeUpdate();
                System.out.println("Your artist has been added");
                System.out.println();

            } catch (Exception SQLIntegrityConstraintViolationException) {
                System.out.println(
                        "Sorry, an artist already exists with that ID number.");
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

    private static void changeBandName(Scanner inputScanner, String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println("Please type the band Id number");
        int bandId = Integer.parseInt(inputScanner.next());
        System.out.println();

        System.out.println("please type you band's new name");

        // We need a buffered reader so that we can take input that has
        // whitespace. When our scanner object gets closed it will close
        // buffered reader as well since they both use system.in. We cannot
        // close system.in without closing it for the entire program, so we
        // shouldn't close it here.
        BufferedReader stdin = new BufferedReader(
                new InputStreamReader(System.in));

        String bandName = "";

        try {
            bandName = stdin.readLine();
        } catch (IOException e) {
            System.out.println(
                    "Something went wrong with taking your artist name. The system is going to crash now. Will I dream? Daaaisy, Daaaaisy");
        }

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement(
                    "UPDATE BAND\\n\" + \"SET name = ? \\n\"\n"
                            + "                    + \"WHERE band_id = ?");

            stmt.setString(1, bandName);
            stmt.setInt(2, bandId);

            try {
                stmt.executeUpdate();
                System.out.println("You have renamed your band");
                System.out.println();

            } catch (Exception e) {
                System.out.println(
                        "Sorry, something went wrong renaming your band.");
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

}
