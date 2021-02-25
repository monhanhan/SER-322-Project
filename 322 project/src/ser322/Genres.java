package ser322;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Genres {
    public static void genreMenu(Scanner inputScanner, String[] args) {
        System.out.println("Welcome to the Genera menu");
        printGenreMenuOptions();

        int inInt = Integer.parseInt(inputScanner.next());

        while (inInt != 0) {
            if (inInt == 1) {
                listGenre(args);

            } else if (inInt == 2) {
                viewSongs(args);

            } else if (inInt == 3) {
                addSong(inputScanner, args);

            } else if (inInt == 4) {
                removeSong(inputScanner, args);

            } else if (inInt == 5) {
                addGenre(args);

            } else if (inInt == 6) {
                deleteGenre(args);

            } else {
                System.out.println(
                        "Sorry, you did not select a valid menu option.");
                System.out.println();
            }

            printGenreMenuOptions();

            inInt = Integer.parseInt(inputScanner.next());
        }

    }

    private static void printGenreMenuOptions() {
        System.out.println("Please make a selection");
        System.out.println("0: Return to main menu");
        System.out.println("1: List all genres");
        System.out.println("2: See which songs are in a genre");
        System.out.println("3: Add a song to a genre");
        System.out.println("4: Remove a song from a genre");
        System.out.println("5: Add a genre");
        System.out.println("6: Delete a genre");

    }

    private static void listGenre(String[] args) {
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
            stmt = conn.prepareStatement("SELECT *" + "FROM GENRE");

            // Step 4: Make a query
            rs = stmt.executeQuery();

            // Step 5: Display the results
            while (rs.next()) {
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

    private static void viewSongs(String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println("Please type the name of your genre");

        // We need a buffered reader so that we can take input that has
        // whitespace. When our scanner object gets closed it will close
        // buffered reader as well since they both use system.in. We cannot
        // close system.in without closing it for the entire program, so we
        // shouldn't close it here.
        BufferedReader stdin = new BufferedReader(
                new InputStreamReader(System.in));

        String name = "";

        try {
            name = stdin.readLine();
        } catch (IOException e) {
            System.out.println(
                    "Something went wrong with taking your genre name. The system is going to crash now. Will I dream? Daaaisy, Daaaaisy");
        }

        System.out.println();

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement(
                    "SELECT * FROM SONG WHERE song_id IN (SELECT song_id FROM IS_GENRE WHERE GENRE_NAME = ?)");

            stmt.setString(1, name);

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

    protected static void addSong(Scanner inputScanner, String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println("Please type the genre name");

        // We need a buffered reader so that we can take input that has
        // whitespace. When our scanner object gets closed it will close
        // buffered reader as well since they both use system.in. We cannot
        // close system.in without closing it for the entire program, so we
        // shouldn't close it here.
        BufferedReader stdin = new BufferedReader(
                new InputStreamReader(System.in));

        String name = "";

        try {
            name = stdin.readLine();
        } catch (IOException e) {
            System.out.println(
                    "Something went wrong with taking your genre name. The system is going to crash now. Will I dream? Daaaisy, Daaaaisy");
        }

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
                    "INSERT into IS_GENRE(genre_name, song_id) \n VALUES(?,?)");

            stmt.setString(1, name);
            stmt.setInt(2, songId);

            try {
                stmt.executeUpdate();
                System.out.println("The song is now listed for this genre");
                System.out.println();

            } catch (Exception SQLIntegrityConstraintViolationException) {
                System.out.println(
                        "Sorry, that song was already listed for that genre.");
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

    private static void removeSong(Scanner inputScanner, String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println("Please type the genre name");

        // We need a buffered reader so that we can take input that has
        // whitespace. When our scanner object gets closed it will close
        // buffered reader as well since they both use system.in. We cannot
        // close system.in without closing it for the entire program, so we
        // shouldn't close it here.
        BufferedReader stdin = new BufferedReader(
                new InputStreamReader(System.in));

        String name = "";

        try {
            name = stdin.readLine();
        } catch (IOException e) {
            System.out.println(
                    "Something went wrong with taking your genre name. The system is going to crash now. Will I dream? Daaaisy, Daaaaisy");
        }

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
            stmt = conn.prepareStatement("DELETE IS_GENRE\n" + "From IS_GENRE\n"
                    + "Where genre_name = ? AND song_id = ?");

            stmt.setString(1, name);
            stmt.setInt(2, songId);

            try {
                stmt.executeUpdate();
                System.out.println("You have removed the song from the genera");
                System.out.println();

            } catch (Exception e) {
                System.out.println(
                        "Sorry, something went wrong removing that song from that genera");
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

    private static void removeAllSongs(String generaName, String[] args) {
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
            stmt = conn.prepareStatement("DELETE IS_GENRE\n" + "From IS_GENRE\n"
                    + "Where genre_name = ?");

            stmt.setString(1, generaName);

            try {
                stmt.executeUpdate();
                System.out.println("All genre/song relations removed");
                System.out.println();

            } catch (Exception e) {
                System.out.println(
                        "Sorry, something went wrong removing the song relations from that genre");
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

    private static void addGenre(String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println("Please type the name of the genre");

        // We need a buffered reader so that we can take input that has
        // whitespace. When our scanner object gets closed it will close
        // buffered reader as well since they both use system.in. We cannot
        // close system.in without closing it for the entire program, so we
        // shouldn't close it here.
        BufferedReader stdin = new BufferedReader(
                new InputStreamReader(System.in));

        String name = "";

        try {
            name = stdin.readLine();
            System.out.println();
        } catch (IOException e) {
            System.out.println(
                    "Something went wrong with taking your genre name. The system is going to crash now. Will I dream? Daaaisy, Daaaaisy");
        }

        System.out.println();

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn.prepareStatement("INSERT into GENRE(name) VALUES(?)");

            stmt.setString(1, name);

            try {
                stmt.executeUpdate();
                System.out.println("Your genre has been added");
                System.out.println();

            } catch (Exception SQLIntegrityConstraintViolationException) {
                System.out.println(
                        "Sorry, an artist already exists with that name.");
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

    private static void deleteGenre(String[] args) {
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection conn = null;

        System.out.println("Please type the name of the genre");

        // We need a buffered reader so that we can take input that has
        // whitespace. When our scanner object gets closed it will close
        // buffered reader as well since they both use system.in. We cannot
        // close system.in without closing it for the entire program, so we
        // shouldn't close it here.
        BufferedReader stdin = new BufferedReader(
                new InputStreamReader(System.in));

        String name = "";

        try {
            name = stdin.readLine();
            System.out.println();
        } catch (IOException e) {
            System.out.println(
                    "Something went wrong with taking your genre name. The system is going to crash now. Will I dream? Daaaisy, Daaaaisy");
        }

        System.out.println();

        removeAllSongs(name, args);

        String _url = args[0];
        try {
            // Step 1: Load the JDBC driver
            Class.forName(args[3]);

            // Step 2: make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

            // Step 3: Create a statement
            stmt = conn
                    .prepareStatement("DELETE GENRE FROM GENRE Where name = ?");

            stmt.setString(1, name);

            try {
                stmt.executeUpdate();
                System.out.println("Genre deleted");
                System.out.println();

            } catch (Exception SQLIntegrityConstraintViolationException) {
                System.out.println("Something went wrong removing that Genre");
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
