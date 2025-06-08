package com.woragis.library.borrowing.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.woragis.library.borrowing.model.Borrowing;
import com.woragis.library.borrowing.service.BorrowingService;
import com.woragis.library.physicalcopy.model.PhysicalCopy;
import com.woragis.library.physicalcopy.service.PhysicalCopyService;
import com.woragis.library.shared.enums.BookStatus;

public class BorrowingController {
    private final BorrowingService borrowingService = new BorrowingService();
    private final PhysicalCopyService copyService;

    public BorrowingController(PhysicalCopyService copyService) {
        this.copyService = copyService;
    }

    public void lendBook(Scanner scanner) {
        System.out.print("Enter Book ID to lend: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        PhysicalCopy availableCopy = copyService.getAvailableCopy(bookId);
        if (availableCopy == null) {
            System.out.println("No available copies.");
            return;
        }

        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();

        System.out.print("Enter Employee ID: ");
        int employeeId = scanner.nextInt();

        scanner.nextLine();
        System.out.print("Enter expected return date (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine();

        try {
            Date expectedReturn = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            Borrowing borrowing = borrowingService.borrow(availableCopy.id, userId, employeeId, expectedReturn);
            copyService.updateCopyStatus(availableCopy.id, BookStatus.BORROWED, userId);
            System.out.println("Borrowing registered. ID: " + borrowing.id);
        } catch (ParseException e) {
            System.out.println("Invalid date format.");
        }
    }

    public void returnBook(Scanner scanner) {
        System.out.print("Enter Borrowing ID to return: ");
        int borrowingId = scanner.nextInt();

        Borrowing b = borrowingService.getById(borrowingId);
        if (b == null) {
            System.out.println("Borrowing record not found.");
            return;
        }

        boolean success = borrowingService.returnBook(borrowingId);
        if (success) {
            copyService.updateCopyStatus(b.physicalCopyId, BookStatus.AVAILABLE, -1);
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Book already returned or error.");
        }
    }

    public void listBorrowings() {
        List<Borrowing> list = borrowingService.getAllBorrowings();
        if (list.isEmpty()) {
            System.out.println("No borrowings yet.");
            return;
        }

        for (Borrowing b : list) {
            System.out.println("ID: " + b.id + ", Copy: " + b.physicalCopyId +
                    ", User: " + b.userId +
                    ", Lend Date: " + b.lendingDate +
                    ", Expected Return: " + b.expectedReturnDate +
                    ", Returned: " + (b.actualReturnDate != null ? b.actualReturnDate : "Not yet"));
        }
    }
}
