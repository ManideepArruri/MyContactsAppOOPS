package com.seveneleven.mycontacts.user.app;

import java.util.Scanner;

import com.seveneleven.mycontacts.user.model.User;
import com.seveneleven.mycontacts.user.service.UserService;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();

        System.out.println("=== User Registration ===");

        System.out.print("User Type (FREE/PREMIUM): ");
        String type = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("User Name: ");
        String userName = scanner.nextLine();

        try {
            User user = userService.registerUser(type, email, password, userName);

            System.out.println("\nUser Registered Successfully!");
            System.out.println("Type: " + user.getUserType());
            System.out.println("Name: " + user.getUserName());

        } catch (Exception e) {
            System.out.println("Registration Failed: " + e.getMessage());
        }

        scanner.close();
    }
}