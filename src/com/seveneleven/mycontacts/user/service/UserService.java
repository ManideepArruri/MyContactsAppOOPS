package com.seveneleven.mycontacts.user.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Pattern;

import com.seveneleven.mycontacts.user.model.FreeUser;
import com.seveneleven.mycontacts.user.model.PremiumUser;
import com.seveneleven.mycontacts.user.model.User;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UserService {

    private List<User> users = new ArrayList<>();

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public User registerUser(String type,
                             String email,
                             String password,
                             String userName) {

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }

        String hashedPassword = hashPassword(password);

        User user;

        if (type.equalsIgnoreCase("FREE")) {
            user = new FreeUser(email, hashedPassword, userName);
        } else if (type.equalsIgnoreCase("PREMIUM")) {
            user = new PremiumUser(email, hashedPassword, userName);
        } else {
            throw new IllegalArgumentException("Invalid user type");
        }

        users.add(user);  // Store user in memory
        return user;
    }

    public List<User> getAllUsers() {
        return users;
    }

    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            return new String(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password");
        }
    }
}