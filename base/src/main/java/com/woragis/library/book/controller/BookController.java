package com.woragis.library.book.controller;

import java.util.Scanner;

import com.woragis.library.book.model.Book;
import com.woragis.library.book.service.BookService;

public class BookController {
    private final BookService bookService = new BookService();

    public void addBook(Scanner scanner) {
        Book book = new Book();

        System.out.print("ISBN: ");
        book.isbn = scanner.nextLine();

        System.out.print("Title: ");
        book.title = scanner.nextLine();

        System.out.print("Description: ");
        book.description = scanner.nextLine();

        System.out.print("Number of physical copies: ");
        book.physicalCopyCount = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Book added = bookService.addBook(book);
        System.out.println("Book added with ID: " + added.id);
    }

    public void listBooks() {
        var books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        for (Book book : books) {
            System.out.println("ID: " + book.id + ", ISBN: " + book.isbn + ", Title: " + book.title);
        }
    }

    public void findBookByIsbn(Scanner scanner) {
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        Book book = bookService.findByIsbn(isbn);
        if (book != null) {
            System.out.println("Title: " + book.title);
            System.out.println("Description: " + book.description);
            System.out.println("Copies: " + book.physicalCopyCount);
        } else {
            System.out.println("Book not found.");
        }
    }
}
