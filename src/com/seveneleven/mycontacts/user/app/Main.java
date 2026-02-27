package com.seveneleven.mycontacts.user.app;

import java.util.Scanner;

import com.seveneleven.mycontacts.user.model.User;
import com.seveneleven.mycontacts.user.service.*;

public class Main {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		UserService userService = new UserService();
		AuthService authService = new AuthService(userService);
		ProfileService profileService = new ProfileService(authService);

		while (true) {

			System.out.println("\n=== MyContacts App ===");
			System.out.println("1. Register");
			System.out.println("2. Login");
			System.out.println("3. Logout");
			System.out.println("4. View Profile");
			System.out.println("5. Update Username");
			System.out.println("6. Change Password");
			System.out.println("7. Exit");
			System.out.print("Choose option: ");

			int choice = scanner.nextInt();
			scanner.nextLine();

			try {

				switch (choice) {

				case 1:
					System.out.print("User Type (FREE/PREMIUM): ");
					String type = scanner.nextLine();

					System.out.print("Email: ");
					String email = scanner.nextLine();

					System.out.print("Password: ");
					String password = scanner.nextLine();

					System.out.print("User Name: ");
					String userName = scanner.nextLine();

					User user = userService.registerUser(type, email, password, userName);

					System.out.println("Registered Successfully!");
					break;

				case 2:
					System.out.print("Email: ");
					String loginEmail = scanner.nextLine();

					System.out.print("Password: ");
					String loginPassword = scanner.nextLine();

					User loggedUser = authService.login(loginEmail, loginPassword);

					System.out.println("Login Successful!");
					System.out.println("Welcome " + loggedUser.getUserName());
					break;

				case 3:
					authService.logout();
					break;

				case 4:
					profileService.viewProfile();
					break;

				case 5:
					System.out.print("Enter new username: ");
					String newName = scanner.nextLine();
					profileService.updateUserName(newName);
					break;

				case 6:
					System.out.print("Enter old password: ");
					String oldPass = scanner.nextLine();

					System.out.print("Enter new password: ");
					String newPass = scanner.nextLine();

					profileService.changePassword(oldPass, newPass);
					break;

				case 7:
					System.out.println("Exiting...");
					return;
				default:
					System.out.println("Invalid choice");
				}

			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}
}