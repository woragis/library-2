package com.woragis.library.physicalcopy.controller;

import java.util.List;
import java.util.Scanner;

import com.woragis.library.physicalcopy.model.PhysicalCopy;
import com.woragis.library.physicalcopy.service.PhysicalCopyService;
import com.woragis.library.shared.enums.BookStatus;

public class PhysicalCopyController {
    private final PhysicalCopyService copyService = new PhysicalCopyService();

    public void addCopies(Scanner scanner) {
        System.out.print("Enter book ID to add physical copies: ");
        int bookId = scanner.nextInt();

        System.out.print("How many copies to add? ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // consume newline

        for (int i = 0; i < quantity; i++) {
            PhysicalCopy copy = copyService.addCopy(bookId);
            System.out.println("Created copy with ID: " + copy.id);
        }
    }

    public void listCopies(Scanner scanner) {
        System.out.print("Enter book ID to list physical copies: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        List<PhysicalCopy> copies = copyService.getCopiesByBookId(bookId);
        if (copies.isEmpty()) {
            System.out.println("No copies found for this book.");
            return;
        }

        for (PhysicalCopy copy : copies) {
            System.out.println("Copy ID: " + copy.id + ", Status: " + copy.status + ", Owner ID: " + copy.currentOwner);
        }
    }

    public void updateCopyStatus(Scanner scanner) {
        System.out.print("Enter copy ID to update: ");
        int copyId = scanner.nextInt();

        System.out.println("Enter new status (AVAILABLE, RESERVED, BORROWED, PENDING, LOST): ");
        scanner.nextLine(); // consume newline
        String statusStr = scanner.nextLine().toUpperCase();

        System.out.print("Enter current owner ID (-1 if none): ");
        int ownerId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        try {
            BookStatus status = BookStatus.valueOf(statusStr);
            boolean success = copyService.updateCopyStatus(copyId, status, ownerId);
            System.out.println(success ? "Copy updated." : "Copy not found.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status.");
        }
    }

    public PhysicalCopyService getService() {
        return copyService;
    }
}
