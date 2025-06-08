package com.woragis.library.user.controller;

import java.util.Scanner;

import com.woragis.library.user.model.User;
import com.woragis.library.user.model.UserRole;
import com.woragis.library.user.service.UserService;

public class UserController {
    private final UserService userService = new UserService();

    public void registerUser(Scanner scanner) {
        User user = new User();

        System.out.print("Name: ");
        user.name = scanner.nextLine();
        System.out.print("Username: ");
        user.username = scanner.nextLine();
        System.out.print("Email: ");
        user.email = scanner.nextLine();
        System.out.print("Password: ");
        user.password = scanner.nextLine();
        System.out.print("Date of Birth (YYYY-MM-DD): ");
        user.dateOfBirth = scanner.nextLine();

        User registered = userService.register(user);
        System.out.println("Registered successfully with ID: " + registered.id);
    }

    public void loginUser(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = userService.login(username, password);
        if (user != null) {
            System.out.println("Welcome, " + user.name + "! Role: " + user.role);
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    public void changeUserRole(Scanner scanner) {
        System.out.print("User ID to change role: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("New Role (USER, EMPLOYEE, MODERATOR, ADMIN): ");
        String roleInput = scanner.nextLine().toUpperCase();
        try {
            UserRole newRole = UserRole.valueOf(roleInput);
            boolean changed = userService.changeRole(id, newRole);
            System.out.println(changed ? "Role updated successfully." : "User not found.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid role.");
        }
    }

    public void listUsers() {
        for (User user : userService.getAllUsers()) {
            System.out.println("ID: " + user.id + ", Name: " + user.name + ", Role: " + user.role);
        }
    }
}
