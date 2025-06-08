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
                case 1 -> userController.registerUser(scanner);
                case 2 -> userController.loginUser(scanner);
                case 3 -> userController.listUsers();
                case 4 -> userController.changeUserRole(scanner);
                case 5 -> bookController.addBook(scanner);
                case 6 -> bookController.listBooks();
                case 7 -> bookController.findBookByIsbn(scanner);
                case 8 -> copyController.addCopies(scanner);
                case 9 -> copyController.listCopies(scanner);
                case 10 -> copyController.updateCopyStatus(scanner);
                case 11 -> borrowingController.lendBook(scanner);
                case 12 -> borrowingController.returnBook(scanner);
                case 13 -> borrowingController.listBorrowings();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid option.");
            }
        } while (option != 0);

        scanner.close();
    }
}
