package bullscows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    static int len = 4;
    static List<Character> guessed = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        createGuessed();
        System.out.println("Okay, let's start a game!");
        playGame();

    }

    private static void playGame() {
        int bulls = 0;
        int cows = 0;
        int turn = 1;
        Pattern p = Pattern.compile("[0-9]{" + len + "}");
        String input;

        do {
            System.out.println("Turn " + turn++ + ":");

            while (true) {
                input = scanner.nextLine();
                if (!p.matcher(input).matches()) {
                    System.out.println("Wrong input!");
                } else break;
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

    private static void createGuessed() {
        ArrayList<Character> digits = new ArrayList<>(List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
        Random rng = new Random();

        System.out.println("Please, enter the secret code's length:");
        while (true) {
            len = scanner.nextInt();
            if (len < 1 || len > 10) {
                System.out.println("Error: can't generate a secret number with a length of " +
                        len + " because there aren't enough unique digits.");
            } else break;
        }

        int index = rng.nextInt(digits.size() - 1);
        guessed.add(digits.get(index + 1));
        digits.remove(index + 1);
        for (int i = 1; i < len; i++) {
            index = rng.nextInt(digits.size());
            guessed.add(digits.get(index));
            digits.remove(index);
        }

//        System.out.println("The random secret number is " + guessedToString() + ".");
    }

    static void printGuessed() {
        guessed.forEach(System.out::print);
    }

    static String guessedToString() {
        StringBuilder sb = new StringBuilder();
        for (char ch : guessed) sb.append(ch);
        return sb.toString();
    }
}
