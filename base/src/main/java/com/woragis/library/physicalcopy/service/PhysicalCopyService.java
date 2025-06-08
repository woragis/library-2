package com.woragis.library.physicalcopy.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woragis.library.physicalcopy.model.PhysicalCopy;
import com.woragis.library.shared.enums.BookStatus;

public class PhysicalCopyService {
    private final List<PhysicalCopy> copies = new ArrayList<>();
    private int nextId = 1;

    private static final String COPIES_FILE_PATH = "./data/physicalcopies.json";
    private final Gson gson = new Gson();

    public PhysicalCopyService() {
        loadCopies();
        for (PhysicalCopy copy : copies) {
            if (copy.id >= nextId) {
                nextId = copy.id + 1;
            }
        }
    }

    public PhysicalCopy addCopy(int livroId) {
        PhysicalCopy copy = new PhysicalCopy();
        copy.id = nextId++;
        copy.livroId = livroId;
        copy.status = BookStatus.AVAILABLE;
        copy.currentOwner = -1;
        copies.add(copy);
        saveCopies();
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
                saveCopies();
                return true;
            }
        }
        return false;
    }

    private void saveCopies() {
        try {
            File dataDir = new File("./data");
            if (!dataDir.exists()) {
                dataDir.mkdir();
            }
            try (FileWriter writer = new FileWriter(COPIES_FILE_PATH)) {
                gson.toJson(copies, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCopies() {
        try (FileReader reader = new FileReader(COPIES_FILE_PATH)) {
            Type copyListType = new TypeToken<ArrayList<PhysicalCopy>>() {
            }.getType();
            List<PhysicalCopy> loadedCopies = gson.fromJson(reader, copyListType);
            if (loadedCopies != null) {
                copies.addAll(loadedCopies);
            }
        } catch (IOException e) {
            // Probably first run and file does not exist yet, ignore
        }
    }
}
