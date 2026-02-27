package com.seveneleven.mycontacts.user.service;

import java.util.List;

import com.seveneleven.mycontacts.user.model.User;

public class AuthService {

    private UserService userService;
    private User loggedInUser;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public User login(String email, String password) {

        List<User> users = userService.getAllUsers();

        for (User user : users) {

            if (user.getEmail().equalsIgnoreCase(email)) {

                String hashedInputPassword =
                        userService.hashPassword(password);

                if (user.getPasswordHash().equals(hashedInputPassword)) {
                    loggedInUser = user;
                    return user;
                } else {
                    throw new IllegalArgumentException("Incorrect password");
                }
            }
        }

        throw new IllegalArgumentException("User not found");
    }

    public void logout() {
        if (loggedInUser == null) {
            throw new IllegalStateException("No user is currently logged in");
        }

        System.out.println("User " + loggedInUser.getEmail() + " logged out successfully.");
        loggedInUser = null;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}