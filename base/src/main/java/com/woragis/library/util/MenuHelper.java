package com.woragis.library.util;

import java.util.Scanner;

import com.woragis.library.book.controller.BookController;
import com.woragis.library.borrowing.controller.BorrowingController;
import com.woragis.library.physicalcopy.controller.PhysicalCopyController;
import com.woragis.library.physicalcopy.service.PhysicalCopyService;
import com.woragis.library.user.controller.UserController;

public class MenuHelper {
    public static void run() {
        Scanner scanner = new Scanner(System.in);

        UserController userController = new UserController();
        BookController bookController = new BookController();
        PhysicalCopyController copyController = new PhysicalCopyController();
        PhysicalCopyService copyService = copyController.getService(); // reuse for borrowing
        BorrowingController borrowingController = new BorrowingController(copyService);

        int option;
        do {
            System.out.println("\n--- Library Menu ---");
            System.out.println("1. Register User");
            System.out.println("2. Login User");
            System.out.println("3. List Users");
            System.out.println("4. Change User Role");
            System.out.println("5. Add Book");
            System.out.println("6. List Books");
            System.out.println("7. Find Book by ISBN");
            System.out.println("8. Add Physical Copies");
            System.out.println("9. List Physical Copies by Book ID");
            System.out.println("10. Update Physical Copy Status");
            System.out.println("11. Lend Book");
            System.out.println("12. Return Book");
            System.out.println("13. List All Borrowings");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                scanner.next(); // discard invalid input
                System.out.print("Choose an option: ");
            }

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> userController.registerUser(scanner); // Register a new user, UserController.java
                case 2 -> userController.loginUser(scanner); // Login an existing user, UserController.java
                case 3 -> userController.listUsers(); // List all users, UserController.java
                case 4 -> userController.changeUserRole(scanner); // Change user role, UserController.java
                case 5 -> bookController.addBook(scanner); // Add a new book, BookController.java
                case 6 -> bookController.listBooks(); // List all books, BookController.java
                case 7 -> bookController.findBookByIsbn(scanner); // Find a book by ISBN, BookController.java
                case 8 -> copyController.addCopies(scanner); // Add physical copies of a book, PhysicalCopyController.java
                case 9 -> copyController.listCopies(scanner); // List physical copies by book ID, PhysicalCopyController.java
                case 10 -> copyController.updateCopyStatus(scanner); // Update the status of a physical copy, PhysicalCopyController.java
                case 11 -> borrowingController.lendBook(scanner); // Lend a book to a user, BorrowingController.java
                case 12 -> borrowingController.returnBook(scanner); // Return a borrowed book, BorrowingController.java
                case 13 -> borrowingController.listBorrowings(); // List all borrowings, BorrowingController.java
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid option.");
            }
        } while (option != 0);

        scanner.close();
    }
}
