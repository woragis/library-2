package com.woragis.library.user.service;

import java.util.ArrayList;
import java.util.List;

import com.woragis.library.user.model.User;
import com.woragis.library.user.model.UserRole;

public class UserService {
    private final List<User> users = new ArrayList<>();
    private int nextId = 1;

    public User register(User user) {
        user.id = nextId++;
        user.role = UserRole.USER;
        user.createdAt = java.time.LocalDateTime.now().toString();
        users.add(user);
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
        return users.removeIf(user -> user.id == id);
    }

    public boolean changeRole(int userId, UserRole newRole) {
        for (User user : users) {
            if (user.id == userId) {
                user.role = newRole;
                user.updatedAt = java.time.LocalDateTime.now().toString();
                return true;
            }
        }
        return false;
    }

    public List<User> getAllUsers() {
        return users;
    }
}
