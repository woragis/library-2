package com.woragis.library.borrowing.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.woragis.library.borrowing.model.Borrowing;

public class BorrowingService {
    private final List<Borrowing> records = new ArrayList<>();
    private int nextId = 1;

    private static final String BORROWINGS_FILE_PATH = "./data/borrowings.json";
    private final Gson gson;

    public BorrowingService() {
        // Gson with date formatting for Date fields
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .create();

        loadBorrowings();
        for (Borrowing b : records) {
            if (b.id >= nextId) {
                nextId = b.id + 1;
            }
        }
    }

    public Borrowing borrow(int physicalCopyId, int userId, int employeeId, Date expectedReturnDate) {
        Borrowing b = new Borrowing();
        b.id = nextId++;
        b.physicalCopyId = physicalCopyId;
        b.userId = userId;
        b.employeeId = employeeId;
        b.lendingDate = new Date();
        b.expectedReturnDate = expectedReturnDate;
        b.actualReturnDate = null;
        records.add(b);
        saveBorrowings();
        return b;
    }

    public boolean returnBook(int borrowingId) {
        for (Borrowing b : records) {
            if (b.id == borrowingId && b.actualReturnDate == null) {
                b.actualReturnDate = new Date();
                saveBorrowings();
                return true;
            }
        }
        return false;
    }

    public List<Borrowing> getAllBorrowings() {
        return records;
    }

    public Borrowing getById(int id) {
        for (Borrowing b : records) {
            if (b.id == id) {
                return b;
            }
        }
        return null;
    }

    private void saveBorrowings() {
        try {
            File dataDir = new File("./data");
            if (!dataDir.exists()) {
                dataDir.mkdir();
            }
            try (FileWriter writer = new FileWriter(BORROWINGS_FILE_PATH)) {
                gson.toJson(records, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBorrowings() {
        try (FileReader reader = new FileReader(BORROWINGS_FILE_PATH)) {
            Type borrowingListType = new TypeToken<ArrayList<Borrowing>>() {
            }.getType();
            List<Borrowing> loadedRecords = gson.fromJson(reader, borrowingListType);
            if (loadedRecords != null) {
                records.addAll(loadedRecords);
            }
        } catch (IOException e) {
            // Ignore if file does not exist yet (first run)
        }
    }
}
