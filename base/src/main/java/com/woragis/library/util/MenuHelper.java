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
            System.out.println("2. List Users");
            System.out.println("3. Add Book");
            System.out.println("4. List Books");
            System.out.println("5. Find Book by ISBN");
            System.out.println("6. Add Physical Copies");
            System.out.println("7. List Physical Copies by Book ID");
            System.out.println("8. Update Physical Copy Status");
            System.out.println("9. Lend Book");
            System.out.println("10. Return Book");
            System.out.println("11. List All Borrowings");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (option) {
                case 1 -> userController.registerUser(scanner);
                case 2 -> userController.listUsers();
                case 3 -> bookController.addBook(scanner);
                case 4 -> bookController.listBooks();
                case 5 -> bookController.findBookByIsbn(scanner);
                case 6 -> copyController.addCopies(scanner);
                case 7 -> copyController.listCopies(scanner);
                case 8 -> copyController.updateCopyStatus(scanner);
                case 9 -> borrowingController.lendBook(scanner);
                case 10 -> borrowingController.returnBook(scanner);
                case 11 -> borrowingController.listBorrowings();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid option.");
            }
        } while (option != 0);

        scanner.close();
    }
}
