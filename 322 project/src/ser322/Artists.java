package ser322;

import java.util.Scanner;

public class Artists {

    public static void artistMenu(Scanner inputScanner, String[] args) {
        System.out.println("Welcome to the Artist menu");
        printArtistMenuOptions();

        int inInt = Integer.parseInt(inputScanner.next());

        while (inInt != 0) {
            if (inInt == 1) {

            } else if (inInt == 2) {

            } else if (inInt == 3) {

            } else if (inInt == 4) {

            } else if (inInt == 5) {

            } else if (inInt == 6) {

            } else if (inInt == 7) {

            } else if (inInt == 8) {

            } else if (inInt == 9) {

            } else if (inInt == 10) {

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
        System.out.println("9: Remove an artist from a band");
        System.out.println("10: Change the name of an artist");
        System.out.println();

    }

}
