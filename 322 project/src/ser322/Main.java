package ser322;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // This is a scanner we can use for user input.
        Scanner inputScanner = new Scanner(System.in);

        // Let the user know what we want
        System.out.println("We ask for input like this");

        // Read an entire line of user input. Can be adapted with next() or
        // integer.parseint for more specific requirements.
        String inString = inputScanner.nextLine();

    }

}
