package com.woragis.library.physicalcopy.service;

import java.util.ArrayList;
import java.util.List;

import com.woragis.library.physicalcopy.model.PhysicalCopy;
import com.woragis.library.shared.enums.BookStatus;

public class PhysicalCopyService {
    private final List<PhysicalCopy> copies = new ArrayList<>();
    private int nextId = 1;

    public PhysicalCopy addCopy(int livroId) {
        PhysicalCopy copy = new PhysicalCopy();
        copy.id = nextId++;
        copy.livroId = livroId;
        copy.status = BookStatus.AVAILABLE;
        copy.currentOwner = -1;
        copies.add(copy);
        return copy;
    }

    public List<PhysicalCopy> getCopiesByBookId(int livroId) {
        List<PhysicalCopy> result = new ArrayList<>();
        for (PhysicalCopy c : copies) {
            if (c.livroId == livroId) {
                result.add(c);
            }
        }
        return result;
    }

    public List<PhysicalCopy> getAllCopies() {
        return copies;
    }

    public PhysicalCopy getAvailableCopy(int livroId) {
        for (PhysicalCopy copy : copies) {
            if (copy.livroId == livroId && copy.status == BookStatus.AVAILABLE) {
                return copy;
            }
        }
        return null;
    }

    public boolean updateCopyStatus(int copyId, BookStatus status, int ownerId) {
        for (PhysicalCopy copy : copies) {
            if (copy.id == copyId) {
                copy.status = status;
                copy.currentOwner = ownerId;
                return true;
            }
        }
        return false;
    }
}
