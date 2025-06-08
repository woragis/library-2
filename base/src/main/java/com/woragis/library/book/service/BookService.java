package com.woragis.library.book.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woragis.library.book.model.Book;

public class BookService {
    private final List<Book> books = new ArrayList<>();
    private int nextId = 1;

    private static final String BOOKS_FILE_PATH = "./data/books.json";
    private final Gson gson = new Gson();

    public BookService() {
        loadBooks();
        for (Book book : books) {
            if (book.id >= nextId) {
                nextId = book.id + 1;
            }
        }
    }

    public Book addBook(Book book) {
        book.id = nextId++;
        book.likeCount = 0;
        book.commentCount = 0;
        book.borrowCount = 0;
        books.add(book);
        saveBooks();
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
        boolean removed = books.removeIf(book -> book.id == id);
        if (removed) {
            saveBooks();
        }
        return removed;
    }

    private void saveBooks() {
        try {
            File dataDir = new File("./data");
            if (!dataDir.exists()) {
                dataDir.mkdir();
            }
            try (FileWriter writer = new FileWriter(BOOKS_FILE_PATH)) {
                gson.toJson(books, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBooks() {
        try (FileReader reader = new FileReader(BOOKS_FILE_PATH)) {
            Type bookListType = new TypeToken<ArrayList<Book>>() {
            }.getType();
            List<Book> loadedBooks = gson.fromJson(reader, bookListType);
            if (loadedBooks != null) {
                books.addAll(loadedBooks);
            }
        } catch (IOException e) {
            // Likely first run - file doesn't exist yet, so ignore
        }
    }
}
