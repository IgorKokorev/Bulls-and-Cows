package bullscows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    static int len = 4;
    static int numSymbols = 10;
    static List<Character> guessed = new ArrayList<>();
    static List<Character> permitted;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        if (createGuessed()) {
            System.out.println("Okay, let's start a game!");
            playGame();
        }
    }

    private static void playGame() {
        int bulls = 0;
        int cows = 0;
        int turn = 1;

        String pattern;
        if (numSymbols < 11) pattern = "0-" + (numSymbols - 1);
        else pattern = "0-9a-" + permitted.get(numSymbols - 1);
        Pattern p = Pattern.compile("[" + pattern + "]{" + len + "}");
        String input;

        do {
            System.out.println("Turn " + turn++ + ":");

            input = scanner.nextLine();
            if (!p.matcher(input).matches()) {
                System.out.println("Error: Wrong input!");
                return;
            }

            bulls = 0;
            cows = 0;
            for (int i = 0; i < len; i++) {
                if (input.charAt(i) == guessed.get(i)) bulls++;
                else if (guessed.contains(input.charAt(i))) cows++;
            }

            printGrade(bulls, cows);

        } while (bulls < len);
        System.out.println("Congratulations! You guessed the secret code.");
    }

    private static void printGrade(int bulls, int cows) {

        System.out.print("Grade: ");
        if (cows == 0 && bulls == 0) System.out.println("None");
        else if (cows == 0) System.out.println(bulls + " bull(s)");
        else if (bulls == 0) System.out.println(cows + " cow(s)");
        else System.out.println(bulls + " bull(s) and " + cows + " cow(s)");
    }

    private static boolean createGuessed() {
        ArrayList<Character> digits = new ArrayList<>(List.of('0', '1', '2', '3', '4', '5',
                '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));
        Random rng = new Random();

        System.out.println("Input the length of the secret code:");
        try {
            len = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: not a number.");
            return false;
        }
        if (len < 1 || len > 36) {
            System.out.println("Error: can't generate a secret number with a length of " +
                    len + " because there aren't enough unique digits.");
            return false;
        }

        System.out.println("Input the number of possible symbols in the code:");
        try {
            numSymbols = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: not a number.");
            return false;
        }
        if (numSymbols < len || numSymbols > 36) {
            System.out.println("Error: number of symbols should be not less " +
                    "than the length of the code and not more than 36.");
            return false;
        }

        permitted = digits.subList(0, numSymbols);
        ArrayList<Character> copy = new ArrayList<>(permitted);

        int index;
        for (int i = 0; i < len; i++) {
            index = rng.nextInt(copy.size());
            guessed.add(copy.get(index));
            copy.remove(index);
        }

        System.out.print("The secret is prepared: ");
        for (int i = 0; i < len; i++) System.out.print("*");
        System.out.print(" (0-");
        if (numSymbols < 11) {
            System.out.print(numSymbols - 1);
        } else {
            System.out.print("9, a");
            if (numSymbols > 11) System.out.print("-" + permitted.get(numSymbols - 1));
        }
        System.out.println(").");
        return true;
    }
}
