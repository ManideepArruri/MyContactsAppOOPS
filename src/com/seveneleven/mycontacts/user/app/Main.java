/* 
 * Version : 8
 * author : Developer
 *  */

package com.seveneleven.mycontacts.user.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.seveneleven.mycontacts.contact.model.Tag;
import com.seveneleven.mycontacts.contact.service.BulkContactService;
import com.seveneleven.mycontacts.contact.service.ContactService;
import com.seveneleven.mycontacts.contact.service.TagService;
import com.seveneleven.mycontacts.user.model.User;
import com.seveneleven.mycontacts.user.service.*;

public class Main {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		UserService userService = new UserService();
		AuthService authService = new AuthService(userService);
		ProfileService profileService = new ProfileService(authService);
		ContactService contactService = new ContactService(authService);
		BulkContactService bulkService =
		    new BulkContactService(authService, contactService.getUserContacts());
		TagService tagService = new TagService(authService);

		while (true) {

			System.out.println("\n=== MyContacts App ===");
			System.out.println("1. Register");
			System.out.println("2. Login");
			System.out.println("3. Logout");
			System.out.println("4. View Profile");
			System.out.println("5. Update Username");
			System.out.println("6. Change Password");
			System.out.println("7. Add Contact");
			System.out.println("8. View All Contacts");
			System.out.println("9. View Contact By ID");
			System.out.println("10. View Contact With Format");
			System.out.println("11. Edit Contact");
			System.out.println("12. Soft Delete Contact");
			System.out.println("13. Hard Delete Contact");
			System.out.println("14. Bulk Soft Delete");
			System.out.println("15. Bulk Hard Delete");
			System.out.println("16. Bulk Add Tag");
			System.out.println("17. Bulk Export");
			System.out.println("18. Search By Name");
			System.out.println("19. Search By Phone");
			System.out.println("20. Search By Email");
			System.out.println("21. Search By Tag");
			System.out.println("22. Filter By Tag");
			System.out.println("23. Filter By Date Added");
			System.out.println("24. Filter By Frequently Contacted");
			System.out.println("25. Create Tag");
			System.out.println("26. View Tags");
			System.out.println("27. Assign Tag To Contact");
			System.out.println("28. Apply Single Tag to Contact");
			System.out.println("29. Apply Multiple Tags to Contact");
			System.out.println("30. Remove Tag from Contact");
			System.out.println("31. Exit");
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
					System.out.print("Name: ");
					String name = scanner.nextLine();

					System.out.print("Phone: ");
					String phone = scanner.nextLine();

					System.out.print("Email: ");
					String contactEmail = scanner.nextLine();

					contactService.addContact(name, phone, contactEmail);
					break;

				case 8:
					contactService.viewAllContacts();
					break;

				case 9:
					System.out.print("Enter Contact ID: ");
					String id = scanner.nextLine();
					contactService.viewContactById(id);
					break;

				case 10:

					System.out.print("Enter Contact ID: ");
					String contactId = scanner.nextLine();

					System.out.println("Choose Format:");
					System.out.println("1. Normal");
					System.out.println("2. Uppercase");
					System.out.println("3. Masked Email");

					int format = scanner.nextInt();
					scanner.nextLine();

					contactService.viewContactWithFormat(contactId, format);
					break;
				case 11:

					System.out.print("Enter Contact ID to edit: ");
					String editId = scanner.nextLine();

					System.out.println("Leave field blank if no change.");

					System.out.print("New Name: ");
					String newName2 = scanner.nextLine();

					System.out.print("New Phone: ");
					String newPhone = scanner.nextLine();

					System.out.print("New Email: ");
					String newEmail = scanner.nextLine();

					contactService.editContact(editId, newName2, newPhone, newEmail);
					break;
				case 12:

					System.out.print("Enter Contact ID to soft delete: ");
					String softId = scanner.nextLine();

					contactService.softDeleteContact(softId);
					break;

				case 13:

					System.out.print("Enter Contact ID to hard delete: ");
					String hardId = scanner.nextLine();

					contactService.hardDeleteContact(hardId);
					break;
					
				case 14:

				    System.out.println("Enter Contact IDs separated by comma:");
				    String softInput = scanner.nextLine();

				    String[] softArray = softInput.split(",");
				    List<String> softIds = new ArrayList<>();

				    for (String id1 : softArray) {
				        softIds.add(id1.trim());
				    }

				    bulkService.bulkSoftDelete(softIds);
				    break;


				case 15:

				    System.out.println("Enter Contact IDs separated by comma:");
				    String hardInput = scanner.nextLine();

				    String[] hardArray = hardInput.split(",");
				    List<String> hardIds = new ArrayList<>();

				    for (String id1 : hardArray) {
				        hardIds.add(id1.trim());
				    }

				    bulkService.bulkHardDelete(hardIds);
				    break;


				case 16:

				    System.out.println("Enter Contact IDs separated by comma:");
				    String tagInput = scanner.nextLine();

				    String[] tagArray = tagInput.split(",");
				    List<String> tagIds = new ArrayList<>();

				    for (String id1 : tagArray) {
				        tagIds.add(id1.trim());
				    }

				    System.out.print("Enter tag to add: ");
				    Tag tag = new Tag("");
				    bulkService.bulkAddTag(tagIds, tag);
				    break;


				case 17:

				    System.out.println("Enter Contact IDs separated by comma:");
				    String exportInput = scanner.nextLine();

				    String[] exportArray = exportInput.split(",");
				    List<String> exportIds = new ArrayList<>();

				    for (String id1 : exportArray) {
				        exportIds.add(id1.trim());
				    }

				    System.out.print("Enter file name (example: contacts.txt): ");
				    String fileName = scanner.nextLine();

				    bulkService.bulkExport(exportIds, fileName);
				    break;
				case 18:

				    System.out.print("Enter name to search: ");
				    String nameSearch = scanner.nextLine();
				    contactService.searchByName(nameSearch);
				    break;

				case 19:

				    System.out.print("Enter phone to search: ");
				    String phoneSearch = scanner.nextLine();
				    contactService.searchByPhone(phoneSearch);
				    break;

				case 20:

				    System.out.print("Enter email to search: ");
				    String emailSearch = scanner.nextLine();
				    contactService.searchByEmail(emailSearch);
				    break;

				case 21:

				    System.out.print("Enter tag to search: ");
				    String tagSearch = scanner.nextLine();
				    contactService.searchByTag(tagSearch);
				    break;
				case 22:

				    System.out.print("Enter tag: ");
				    String filterTag = scanner.nextLine();
				    contactService.filterByTag(filterTag);
				    break;

				case 23:

				    contactService.filterByDateAdded();
				    break;

				case 24:

				    contactService.filterByFrequentlyContacted();
				    break;
				case 25:

				    System.out.print("Enter tag name: ");
				    String newTag = scanner.nextLine();
				    ((TagService) tagService).createTag(newTag);
				    break;

				case 26:

				    ((TagService) tagService).viewTags();
				    break;

				case 27:

				    System.out.print("Enter Contact ID: ");
				    String contactId1 = scanner.nextLine();

				    System.out.print("Enter Tag name: ");
				    String tagName = scanner.nextLine();

				    Tag tag1 = new Tag(tagName);
				    contactService.assignTagToContact(contactId1, tag1);
				    break;
				case 28:

				    System.out.print("Enter Contact ID: ");
				    String contactId11 = scanner.nextLine();

				    System.out.print("Enter Tag name: ");
				    String tagName1 = scanner.nextLine();

				    contactService.applyTagToContact(contactId11, tagName1);
				    break;


				case 29:

				    System.out.print("Enter Contact ID: ");
				    String contactId2 = scanner.nextLine();

				    System.out.print("Enter tags separated by comma: ");
				    String tagInput1 = scanner.nextLine();

				    contactService.applyMultipleTags(contactId2, tagInput1);
				    break;


				case 30:

				    System.out.print("Enter Contact ID: ");
				    String contactId3 = scanner.nextLine();

				    System.out.print("Enter Tag name to remove: ");
				    String removeTag = scanner.nextLine();

				    contactService.removeTagFromContact(contactId3, removeTag);
				    break;
				case 31:
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