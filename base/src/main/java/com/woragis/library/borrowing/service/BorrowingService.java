package com.woragis.library.borrowing.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.woragis.library.borrowing.model.Borrowing;

public class BorrowingService {
    private final List<Borrowing> records = new ArrayList<>();
    private int nextId = 1;

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
        return b;
    }

    public boolean returnBook(int borrowingId) {
        for (Borrowing b : records) {
            if (b.id == borrowingId && b.actualReturnDate == null) {
                b.actualReturnDate = new Date();
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
}
