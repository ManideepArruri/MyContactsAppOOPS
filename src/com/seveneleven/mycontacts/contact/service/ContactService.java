package com.seveneleven.mycontacts.contact.service;

import java.util.HashMap;
import java.util.Map;

import com.seveneleven.mycontacts.contact.model.Contact;
import com.seveneleven.mycontacts.user.model.User;
import com.seveneleven.mycontacts.user.service.AuthService;

public class ContactService {

	private AuthService authService;

	// Stores contacts per user
	private Map<String, Map<String, Contact>> userContacts = new HashMap<>();

	public ContactService(AuthService authService) {
		this.authService = authService;
	}

	// Add Contact
	public void addContact(String name, String phone, String email) {

		User user = authService.getLoggedInUser();

		if (user == null) {
			throw new IllegalStateException("Please login first");
		}

		Contact contact = new Contact(name, phone, email);

		userContacts
		.computeIfAbsent(user.getEmail(), k -> new HashMap<>())
		.put(contact.getContactId(), contact);

		System.out.println("Contact added successfully!");
		System.out.println("Contact ID: " + contact.getContactId());
	}

	// View All Contacts
	public void viewAllContacts() {

		User user = authService.getLoggedInUser();

		if (user == null) {
			throw new IllegalStateException("Please login first");
		}

		Map<String, Contact> contacts = userContacts.get(user.getEmail());

		if (contacts == null || contacts.isEmpty()) {
			System.out.println("No contacts found.");
			return;
		}

		for (Contact contact : contacts.values()) {
			System.out.println(contact);
			System.out.println("----------------------");
		}
	}

	// View Specific Contact
	public void viewContactById(String contactId) {

		User user = authService.getLoggedInUser();

		if (user == null) {
			throw new IllegalStateException("Please login first");
		}

		Map<String, Contact> contacts = userContacts.get(user.getEmail());

		if (contacts == null || !contacts.containsKey(contactId)) {
			System.out.println("Contact not found.");
			return;
		}

		System.out.println(contacts.get(contactId));
	}

	public void viewContactWithFormat(String contactId, int formatOption) {

		User user = authService.getLoggedInUser();

		if (user == null) {
			throw new IllegalStateException("Please login first");
		}

		Map<String, Contact> contacts = userContacts.get(user.getEmail());

		if (contacts == null || !contacts.containsKey(contactId)) {
			System.out.println("Contact not found.");
			return;
		}

		Contact contact = contacts.get(contactId);

		switch (formatOption) {

		case 1:
			System.out.println(contact.getNormalDetails());
			break;

		case 2:
			System.out.println(contact.getUpperCaseDetails());
			break;

		case 3:
			System.out.println(contact.getMaskedEmailDetails());
			break;

		default:
			System.out.println("Invalid format option");
		}
	}

	public void editContact(String contactId,
			String newName,
			String newPhone,
			String newEmail) {

		User user = authService.getLoggedInUser();

		if (user == null) {
			throw new IllegalStateException("Please login first");
		}

		Map<String, Contact> contacts = userContacts.get(user.getEmail());

		if (contacts == null || !contacts.containsKey(contactId)) {
			System.out.println("Contact not found.");
			return;
		}

		Contact contact = contacts.get(contactId);

		if (newName != null && !newName.trim().isEmpty()) {
			contact.setName(newName);
		}

		if (newPhone != null && !newPhone.trim().isEmpty()) {
			contact.setPhoneNumber(newPhone);
		}

		if (newEmail != null && !newEmail.trim().isEmpty()) {
			contact.setEmail(newEmail);
		}

		System.out.println("Contact updated successfully!");
	}
}