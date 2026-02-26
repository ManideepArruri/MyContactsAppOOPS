package com.seveneleven.mycontacts.user.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import com.seveneleven.mycontacts.user.model.FreeUser;
import com.seveneleven.mycontacts.user.model.PremiumUser;
import com.seveneleven.mycontacts.user.model.User;

public class UserService {

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

        if (type.equalsIgnoreCase("FREE")) {
            return new FreeUser(email, hashedPassword, userName);
        } 
        else if (type.equalsIgnoreCase("PREMIUM")) {
            return new PremiumUser(email, hashedPassword, userName);
        } 
        else {
            throw new IllegalArgumentException("Invalid user type");
        }
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
