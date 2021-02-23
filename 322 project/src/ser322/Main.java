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

        int inInt = Integer.parseInt(inputScanner.next());
        // TODO: Add code for the next menu level.

    }

}
