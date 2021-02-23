package ser322;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // This is a scanner we can use for user input.
        Scanner inputScanner = new Scanner(System.in);

        mainMenu(inputScanner);

    }

    public static void mainMenu(Scanner inputScanner) {
        // Let the user know what we want
        System.out.println("Welcome to our music database.");
        System.out.println("Please make a selection");
        System.out.println();
        System.out.println("0: Exit System");
        System.out.println("1: Artists");

        System.out.println();

        int inInt = Integer.parseInt(inputScanner.next());
        // TODO: Add code for the next menu level.

        while (inInt != 0) {
            // TODO: update this as more menus are added.

            if (inInt == 1) {
                Artists.artistMenu(inputScanner);

            } else {
                System.out.println(
                        "I'm sorry, you have not selected a valid option.");
                System.out.println("Please make a selection");
                System.out.println();
                System.out.println("0: Exit System");
                System.out.println("1: Artists");
                inInt = Integer.parseInt(inputScanner.next());

            }
        }

        // If we got here it is because the user has chosen to exit.
        // Close our input scanner.
        inputScanner.close();
        // Close the program.
        System.exit(0);

    }

}
