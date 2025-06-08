package com.woragis.library.util;

import java.util.Scanner;

import com.woragis.library.book.controller.BookController;
import com.woragis.library.user.controller.UserController;

public class MenuHelper {
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        UserController userController = new UserController();
        BookController bookController = new BookController();

        int option;
        do {
            System.out.println("\n--- Library Menu ---");
            System.out.println("1. Register User");
            System.out.println("2. List Users");
            System.out.println("3. Add Book");
            System.out.println("4. List Books");
            System.out.println("5. Find Book by ISBN");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (option) {
                case 1:
                    userController.registerUser(scanner);
                    break;
                case 2:
                    userController.listUsers();
                    break;
                case 3:
                    bookController.addBook(scanner);
                    break;
                case 4:
                    bookController.listBooks();
                    break;
                case 5:
                    bookController.findBookByIsbn(scanner);
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
