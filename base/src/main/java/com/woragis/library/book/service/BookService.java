package com.woragis.library.book.service;

import java.util.ArrayList;
import java.util.List;

import com.woragis.library.book.model.Book;

public class BookService {
    private final List<Book> books = new ArrayList<>();
    private int nextId = 1;

    public Book addBook(Book book) {
        book.id = nextId++;
        book.likeCount = 0;
        book.commentCount = 0;
        book.borrowCount = 0;
        books.add(book);
        return book;
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Book findByIsbn(String isbn) {
        for (Book book : books) {
            if (book.isbn.equalsIgnoreCase(isbn)) {
                return book;
            }
        }
        return null;
    }

    public boolean deleteBook(int id) {
        return books.removeIf(book -> book.id == id);
    }
}
