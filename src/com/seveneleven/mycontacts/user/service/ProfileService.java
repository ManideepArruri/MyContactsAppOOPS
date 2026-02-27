package com.seveneleven.mycontacts.user.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.seveneleven.mycontacts.user.model.User;

public class ProfileService {

    private AuthService authService;

    public ProfileService(AuthService authService) {
        this.authService = authService;
    }

    // View Profile
    public void viewProfile() {

        User user = authService.getLoggedInUser();

        if (user == null) {
            throw new IllegalStateException("No user is logged in");
        }

        System.out.println("\n=== User Profile ===");
        System.out.println("Email: " + user.getEmail());
        System.out.println("Username: " + user.getUserName());
        System.out.println("User Type: " + user.getUserType());
    }

    // Update Username
    public void updateUserName(String newUserName) {

        User user = authService.getLoggedInUser();

        if (user == null) {
            throw new IllegalStateException("Login required");
        }

        if (newUserName == null || newUserName.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        user.setUserName(newUserName);
        System.out.println("Username updated successfully!");
    }

    // Change Password
    public void changePassword(String oldPassword, String newPassword) {

        User user = authService.getLoggedInUser();

        if (user == null) {
            throw new IllegalStateException("Login required");
        }

        String oldHashed = hashPassword(oldPassword);

        if (!user.getPasswordHash().equals(oldHashed)) {
            throw new IllegalArgumentException("Old password incorrect");
        }

        if (newPassword.length() < 6) {
            throw new IllegalArgumentException("New password must be at least 6 characters");
        }

        String newHashed = hashPassword(newPassword);
        user.setPasswordHash(newHashed);

        System.out.println("Password changed successfully!");
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            return new String(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password");
        }
    }
}