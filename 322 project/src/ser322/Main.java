package ser322;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // This is a scanner we use for user input.
        Scanner inputScanner = new Scanner(System.in);
        mainMenu(inputScanner, args);
    }

    public static void mainMenu(Scanner inputScanner, String[] args) {
        // Let the user know what we want
        System.out.println("Welcome to our music database!");
        printMainMenu();

        // Read the user's selection
        int inInt = Integer.parseInt(inputScanner.next());

        // Input loop
        while (inInt != 0) {
            if (inInt == 1) {
                Artists.artistMenu(inputScanner, args);
            } else if (inInt == 2) {
                Albums.albumsMenu(inputScanner, args);
            } else if (inInt == 3) {
                Bands.bandsMenu(inputScanner, args);
            } else if (inInt == 4) {
                Genres.genreMenu(inputScanner, args);
            } else if (inInt == 5) {
                Songs.songMenu(inputScanner, args);
            } else {
                System.out.println("I'm sorry, you have not selected a valid option.");
            }
            
            printMainMenu();
            inInt = Integer.parseInt(inputScanner.next());
        }

        // If we got here it is because the user has chosen to exit.
        // Close our input scanner.
        inputScanner.close();
        // Close the program.
        System.exit(0);
    }

    private static void printMainMenu() {
        System.out.println("Main Menu");
        System.out.println("Please make a selection: ");
        System.out.println();
        System.out.println("0: Exit System");
        System.out.println("1: Artists");
        System.out.println("2: Albums");
        System.out.println("3: Bands");
        System.out.println("4: Genres");
        System.out.println("5: Songs");
        System.out.println();
    }
}
