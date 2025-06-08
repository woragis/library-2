package com.woragis.library.util;

import java.util.Scanner;

public class MenuHelper {
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("--- Library Menu ---");
            System.out.println("1. Register User");
            System.out.println("2. Add Book");
            System.out.println("3. Lend Book");
            System.out.println("4. Return Book");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (option) {
                case 1:
                    System.out.println("[Register User feature here]");
                    break;
                case 2:
                    System.out.println("[Add Book feature here]");
                    break;
                case 3:
                    System.out.println("[Lend Book feature here]");
                    break;
                case 4:
                    System.out.println("[Return Book feature here]");
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (option != 0);
        scanner.close();
    }
}
