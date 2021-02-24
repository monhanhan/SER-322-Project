package ser322;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Artists {

    public static void artistMenu(Scanner inputScanner, String[] args) {
        System.out.println("Welcome to the Artist menu");
        printArtistMenuOptions();

        int inInt = Integer.parseInt(inputScanner.next());

        while (inInt != 0) {
            if (inInt == 1) {
                listArtists(args);

            } else if (inInt == 2) {
                searchArtistName(args);

            } else if (inInt == 3) {
                searchArtistId(inputScanner, args);

            } else if (inInt == 4) {
                listBands(inputScanner, args);

            } else if (inInt == 5) {
                listSongs(inputScanner, args);

            } else if (inInt == 6) {
                addArtist(inputScanner, args);

            } else if (inInt == 7) {
                removeArtist(inputScanner, args);

            } else if (inInt == 8) {
                removeArtistSong(inputScanner, args);

            } else if (inInt == 9) {
                addArtistSong(inputScanner, args);

            } else if (inInt == 10) {
                removeArtistBand(inputScanner, args);

            } else if (inInt == 11) {
                addArtistBand(inputScanner, args);

            } else if (inInt == 12) {
                changeArtistName(inputScanner, args);

            } else {
                System.out.println(
                        "Sorry, you did not select a valid menu option.");
                System.out.println();
            }

            printArtistMenuOptions();

            inInt = Integer.parseInt(inputScanner.next());
        }

    }

    private static void printArtistMenuOptions() {
        System.out.println("Please make a selection");
        System.out.println("0: Return to main menu");
        System.out.println("1: List all artists");
        System.out.println("2: Seach artists by name");
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

    private static void listArtists(String[] args) {
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
            stmt = conn.prepareStatement("SELECT *" + "FROM ARTIST");

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

    private static void searchArtistName(String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println("Please type the artist you are looking for");

        // We need a buffered reader so that we can take input that has
        // whitespace. When our scanner object gets closed it will close
        // buffered reader as well since they both use system.in. We cannot
        // close system.in without closing it for the entire program, so we
        // shouldn't close it here.
        BufferedReader stdin = new BufferedReader(
                new InputStreamReader(System.in));

        String artistName = "";

        try {
            artistName = stdin.readLine();
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
            stmt = conn
                    .prepareStatement("SELECT *" + "FROM ARTIST WHERE name=?");

            stmt.setString(1, artistName);

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

    private static void searchArtistId(Scanner inputScanner, String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println("Please type the ID of your artist");

        String artistId = inputScanner.next();
        System.out.println();

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement(
                    "SELECT *" + "FROM ARTIST WHERE artist_id=?");

            stmt.setString(1, artistId);

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

    private static void listBands(Scanner inputScanner, String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println("Please type the ID of your artist");

        String artistId = inputScanner.next();
        System.out.println();

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement("Select *\n" + "FROM BAND\n"
                    + "WHERE band_id IN \n" + "    (\n" + "    SELECT band_id\n"
                    + "    FROM FORMS\n" + "    WHERE artist_id = ?\n" + ")\n");

            stmt.setString(1, artistId);

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

    private static void listSongs(Scanner inputScanner, String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println("Please type the ID of your artist");

        String artistId = inputScanner.next();
        System.out.println();

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement("Select *\n" + "FROM SONG\n"
                    + "WHERE song_id IN \n" + "    (\n" + "    SELECT song_id\n"
                    + "    FROM PERFORMS\n" + "    WHERE artist_id = ?\n"
                    + ")\n");

            stmt.setString(1, artistId);

            // Step 4: Make a query
            rs = stmt.executeQuery();

            // Step 5: Display the results
            while (rs.next()) {
                System.out.printf("%-15s", rs.getInt("song_id"));
                System.out.printf("%-15s", rs.getInt("release_year"));
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

    private static void addArtist(Scanner inputScanner, String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println("Please type the name of the artist");

        // We need a buffered reader so that we can take input that has
        // whitespace. When our scanner object gets closed it will close
        // buffered reader as well since they both use system.in. We cannot
        // close system.in without closing it for the entire program, so we
        // shouldn't close it here.
        BufferedReader stdin = new BufferedReader(
                new InputStreamReader(System.in));

        String artistName = "";

        try {
            artistName = stdin.readLine();
            System.out.println();
        } catch (IOException e) {
            System.out.println(
                    "Something went wrong with taking your artist name. The system is going to crash now. Will I dream? Daaaisy, Daaaaisy");
        }

        System.out.println("Please type the artist Id number");
        int artistId = Integer.parseInt(inputScanner.next());

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement(
                    "INSERT into ARTIST(artist_id, name) \n VALUES(?,?)");

            stmt.setInt(1, artistId);
            stmt.setString(2, artistName);

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

    /**
     * This removes an artist and all their relations.
     *
     * @param inputScanner is a scanner for input
     * @param args         are the command line arguments that allow us to
     *                     connect to our database.
     */
    private static void removeArtist(Scanner inputScanner, String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println(
                "Please enter the ID number of the artist you wish to delete.");
        System.out.println(
                "Please note: When you delete an artist all of their relations get deleted as well");
        System.out.println();

        int artistId = Integer.parseInt(inputScanner.next());

        // This is where we remove all relations.
        removeAllArtistBand(artistId, args);
        removeAllArtistSong(artistId, args);

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement("DELETE ARTIST\n" + "FROM ARTIST\n"
                    + "Where artist_id = ?");

            stmt.setInt(1, artistId);

            try {
                stmt.executeUpdate();
                System.out.println("Artist deleted");
                System.out.println();

            } catch (Exception SQLIntegrityConstraintViolationException) {
                System.out.println("Something went wrong removing that artist");
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

    /**
     * This method allows us to remove the relation between an artist and a song
     *
     * @param inputScanner takes user input
     * @param args         command line arguments that allows us to make a
     *                     database connection
     */
    private static void removeArtistSong(Scanner inputScanner, String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println("Please type the artist Id number");
        int artistId = Integer.parseInt(inputScanner.next());
        System.out.println();

        System.out.println("please type the song Id number");
        int songId = Integer.parseInt(inputScanner.next());
        System.out.println();

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
                System.out.println("You have removed the artist from the band");
                System.out.println();

            } catch (Exception e) {
                System.out.println(
                        "Sorry, something went wrong removing that artist from that band");
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

    /**
     * This allows us to make a new relation between an artist and a song. If
     * the relation already exists an error message is displayed and nothing is
     * done
     *
     * @param inputScanner is the scanner that takes user input
     * @param args         are the command line arguments that allow us to make
     *                     a connection
     */
    private static void addArtistSong(Scanner inputScanner, String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println("Please type the artist Id number");
        int artistId = Integer.parseInt(inputScanner.next());
        System.out.println();

        System.out.println("please type the song Id number");
        int songId = Integer.parseInt(inputScanner.next());
        System.out.println();

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

    /**
     * This method removes the relation between an artist and a band
     *
     * @param inputScanner takes user input
     * @param args         is command line arguments that allows us to make a
     *                     connection
     */
    private static void removeArtistBand(Scanner inputScanner, String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println("Please type the artist Id number");
        int artistId = Integer.parseInt(inputScanner.next());
        System.out.println();

        System.out.println("please type the band Id number");
        int bandId = Integer.parseInt(inputScanner.next());
        System.out.println();

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement("DELETE FORMS\n" + "From FORMS\n"
                    + "Where artist_id = ? AND band_id = ?");

            stmt.setInt(1, artistId);
            stmt.setInt(2, bandId);

            try {
                stmt.executeUpdate();
                System.out.println("You have removed the artist from the band");
                System.out.println();

            } catch (Exception e) {
                System.out.println(
                        "Sorry, something went wrong removing that artist from that band");
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

    /**
     * This creates a relation between an artist and a band.
     *
     * @param inputScanner takes user input
     * @param args         is the command line arguments that allows a
     *                     connection to be made.
     */
    private static void addArtistBand(Scanner inputScanner, String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println("Please type the artist Id number");
        int artistId = Integer.parseInt(inputScanner.next());
        System.out.println();

        System.out.println("please type the band Id number");
        int bandId = Integer.parseInt(inputScanner.next());
        System.out.println();

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement(
                    "INSERT into FORMS(band_id, artist_id) \n VALUES(?,?)");

            stmt.setInt(1, bandId);
            stmt.setInt(2, artistId);

            try {
                stmt.executeUpdate();
                System.out.println("The artist is now listed for this band");
                System.out.println();

            } catch (Exception SQLIntegrityConstraintViolationException) {
                System.out.println(
                        "Sorry, that artist was already listed for that band.");
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

    /**
     * This method allows us to change the name of our artist
     *
     * @param inputScanner takes user input
     * @param args         is a command line argument that allows us to connect
     *                     to our database
     */
    private static void changeArtistName(Scanner inputScanner, String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println("Please type the artist Id number");
        int artistId = Integer.parseInt(inputScanner.next());
        System.out.println();

        System.out.println("please type you artist's new name");
        // We need a buffered reader so that we can take input that has
        // whitespace. When our scanner object gets closed it will close
        // buffered reader as well since they both use system.in. We cannot
        // close system.in without closing it for the entire program, so we
        // shouldn't close it here.
        BufferedReader stdin = new BufferedReader(
                new InputStreamReader(System.in));

        String artistName = "";

        try {
            artistName = stdin.readLine();
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
                    "UPDATE ARTIST\\n\" + \"SET name = ? \\n\"\n"
                            + "                    + \"WHERE artist_id = ?");

            stmt.setString(1, artistName);
            stmt.setInt(2, artistId);

            try {
                stmt.executeUpdate();
                System.out.println("You have renamed your artist");
                System.out.println();

            } catch (Exception e) {
                System.out.println(
                        "Sorry, something went wrong renaming your artist. I hear the name 'Prince' is available");
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

    /**
     * This helper function removes any association between an artist and any
     * bands they may have been in. This is a powerful tool. Use it wisely.
     *
     * @param artistId is the id of the artist being removed
     * @param args     is the list of arguments that allow us to make a
     *                 connection.
     */
    private static void removeAllArtistBand(int artistId, String[] args) {
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
                    "DELETE FORMS\n" + "FROM FORMS\n" + "Where artist_id = ?");

            stmt.setInt(1, artistId);

            try {
                stmt.executeUpdate();
                System.out.println("All band relations deleted");

            } catch (Exception e) {
                System.out.println(
                        "Something went wrong deleting the bands the artist is associated with.");
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

    /**
     * This is a helper function that will allow us to remove an artist
     * completely from the system. This will erase all connections to songs when
     * you delete the artist
     *
     * @param artistId is the id of the artist being removed.
     * @param args     are the command line arguments that will allow us to make
     *                 a connection.
     */
    private static void removeAllArtistSong(int artistId, String[] args) {
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
            stmt = conn.prepareStatement("DELETE PERFORMS\n" + "FROM PERFORMS\n"
                    + "Where artist_id = ?");

            stmt.setInt(1, artistId);

            try {
                stmt.executeUpdate();
                System.out.println("All song relations deleted");

            } catch (Exception e) {
                System.out.println(
                        "Something went wrong deleting the songs the artist is associated with.");
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
