package com.woragis.library.user.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woragis.library.user.model.User;
import com.woragis.library.user.model.UserRole;

public class UserService {
    private final List<User> users = new ArrayList<>();
    private int nextId = 1;

    private static final String USERS_FILE_PATH = "./data/users.json";
    private final Gson gson = new Gson();

    // Constructor loads users from file on service start
    public UserService() {
        loadUsers();
        // Fix nextId after loading
        for (User u : users) {
            if (u.id >= nextId) {
                nextId = u.id + 1;
            }
        }
    }

    public User register(User user) {
        user.id = nextId++;
        user.role = UserRole.USER;
        user.createdAt = java.time.LocalDateTime.now().toString();
        users.add(user);
        saveUsers(); // save after adding
        return user;
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean deleteUser(int id) {
        boolean removed = users.removeIf(user -> user.id == id);
        if (removed) {
            saveUsers(); // save after deleting
        }
        return removed;
    }

    public boolean changeRole(int userId, UserRole newRole) {
        for (User user : users) {
            if (user.id == userId) {
                user.role = newRole;
                user.updatedAt = java.time.LocalDateTime.now().toString();
                saveUsers(); // save after changing role
                return true;
            }
        }
        return false;
    }

    public List<User> getAllUsers() {
        return users;
    }

    // Save users to JSON file
    private void saveUsers() {
        try {
            File dataDir = new File("./data");
            if (!dataDir.exists()) {
                dataDir.mkdir();
            }

            try (FileWriter writer = new FileWriter(USERS_FILE_PATH)) {
                gson.toJson(users, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load users from JSON file
    private void loadUsers() {
        try (FileReader reader = new FileReader(USERS_FILE_PATH)) {
            Type userListType = new TypeToken<ArrayList<User>>() {
            }.getType();
            List<User> loadedUsers = gson.fromJson(reader, userListType);
            if (loadedUsers != null) {
                users.addAll(loadedUsers);
            }
        } catch (IOException e) {
            // File may not exist at first run, just ignore
        }
    }
}
