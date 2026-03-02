package com.seveneleven.mycontacts.contact.service;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.seveneleven.mycontacts.contact.model.Contact;
import com.seveneleven.mycontacts.contact.model.Tag;
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
		    if (!contact.isDeleted()) {
		        System.out.println(contact);
		        System.out.println("----------------------");
		    }
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
	
	public void softDeleteContact(String contactId) {

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

	    if (contact.isDeleted()) {
	        System.out.println("Contact already deleted.");
	        return;
	    }

	    contact.setDeleted(true);

	    System.out.println("Contact soft deleted successfully.");
	}
	
	public void hardDeleteContact(String contactId) {

	    User user = authService.getLoggedInUser();

	    if (user == null) {
	        throw new IllegalStateException("Please login first");
	    }

	    Map<String, Contact> contacts = userContacts.get(user.getEmail());

	    if (contacts == null || !contacts.containsKey(contactId)) {
	        System.out.println("Contact not found.");
	        return;
	    }

	    contacts.remove(contactId);

	    System.out.println("Contact permanently deleted.");
	}
	
	public Map<String, Map<String, Contact>> getUserContacts() {
	    return userContacts;
	}
	
	public void searchByName(String searchName) {

	    User user = authService.getLoggedInUser();

	    if (user == null) {
	        throw new IllegalStateException("Please login first");
	    }

	    Map<String, Contact> contacts = userContacts.get(user.getEmail());

	    if (contacts == null || contacts.isEmpty()) {
	        System.out.println("No contacts found.");
	        return;
	    }

	    boolean found = false;

	    for (Contact contact : contacts.values()) {

	        if (!contact.isDeleted() &&
	            contact.getName().toLowerCase()
	                    .contains(searchName.toLowerCase())) {

	            System.out.println(contact);
	            System.out.println("-------------------");
	            found = true;
	        }
	    }

	    if (!found) {
	        System.out.println("No matching contacts found.");
	    }
	}
	
	public void searchByPhone(String phone) {

	    User user = authService.getLoggedInUser();
	    if (user == null) {
	        throw new IllegalStateException("Please login first");
	    }

	    Map<String, Contact> contacts = userContacts.get(user.getEmail());

	    boolean found = false;

	    for (Contact contact : contacts.values()) {

	        if (!contact.isDeleted() &&
	            contact.getPhoneNumber().contains(phone)) {

	            System.out.println(contact);
	            System.out.println("-------------------");
	            found = true;
	        }
	    }

	    if (!found) {
	        System.out.println("No matching contacts found.");
	    }
	}
	
	public void searchByEmail(String email) {

	    User user = authService.getLoggedInUser();
	    if (user == null) {
	        throw new IllegalStateException("Please login first");
	    }

	    Map<String, Contact> contacts = userContacts.get(user.getEmail());

	    boolean found = false;

	    for (Contact contact : contacts.values()) {

	        if (!contact.isDeleted() &&
	            contact.getEmail().equalsIgnoreCase(email)) {

	            System.out.println(contact);
	            System.out.println("-------------------");
	            found = true;
	        }
	    }

	    if (!found) {
	        System.out.println("No matching contacts found.");
	    }
	}
	
	public void searchByTag(String tag) {

	    User user = authService.getLoggedInUser();
	    if (user == null) {
	        throw new IllegalStateException("Please login first");
	    }

	    Map<String, Contact> contacts = userContacts.get(user.getEmail());

	    boolean found = false;

	    for (Contact contact : contacts.values()) {

	        if (!contact.isDeleted() &&
	            contact.getTags().contains(tag)) {

	            System.out.println(contact);
	            System.out.println("-------------------");
	            found = true;
	        }
	    }

	    if (!found) {
	        System.out.println("No matching contacts found.");
	    }
	}
	
	public void filterByTag(String tag) {

	    User user = authService.getLoggedInUser();
	    if (user == null) {
	        throw new IllegalStateException("Login required");
	    }

	    Map<String, Contact> contactsMap = userContacts.get(user.getEmail());

	    if (contactsMap == null) {
	        System.out.println("No contacts found.");
	        return;
	    }

	    for (Contact contact : contactsMap.values()) {

	        if (!contact.isDeleted() &&
	            contact.getTags().contains(tag)) {

	            System.out.println(contact);
	            System.out.println("-------------------");
	        }
	    }
	}
	public void filterByDateAdded() {

	    User user = authService.getLoggedInUser();
	    if (user == null) {
	        throw new IllegalStateException("Login required");
	    }

	    Map<String, Contact> contactsMap = userContacts.get(user.getEmail());

	    if (contactsMap == null) {
	        System.out.println("No contacts found.");
	        return;
	    }

	    List<Contact> contactsList = new ArrayList<>();

	    for (Contact contact : contactsMap.values()) {
	        if (!contact.isDeleted()) {
	            contactsList.add(contact);
	        }
	    }

	    Collections.sort(contactsList, new Comparator<Contact>() {
	        @Override
	        public int compare(Contact c1, Contact c2) {
	            return c2.getCreatedAt().compareTo(c1.getCreatedAt());
	        }
	    });

	    for (Contact contact : contactsList) {
	        System.out.println(contact);
	        System.out.println("-------------------");
	    }
	}
	
	public void filterByFrequentlyContacted() {

	    User user = authService.getLoggedInUser();
	    if (user == null) {
	        throw new IllegalStateException("Login required");
	    }

	    Map<String, Contact> contactsMap = userContacts.get(user.getEmail());

	    if (contactsMap == null) {
	        System.out.println("No contacts found.");
	        return;
	    }

	    List<Contact> contactsList = new ArrayList<>();

	    for (Contact contact : contactsMap.values()) {
	        if (!contact.isDeleted()) {
	            contactsList.add(contact);
	        }
	    }

	    Collections.sort(contactsList, new Comparator<Contact>() {
	        @Override
	        public int compare(Contact c1, Contact c2) {
	            return Integer.compare(c2.getContactCount(), c1.getContactCount());
	        }
	    });

	    for (Contact contact : contactsList) {
	        System.out.println(contact);
	        System.out.println("-------------------");
	    }
	}
	public void assignTagToContact(String contactId, Tag tag) {

	    User user = authService.getLoggedInUser();

	    if (user == null) {
	        throw new IllegalStateException("Login required");
	    }

	    Map<String, Contact> contacts = userContacts.get(user.getEmail());

	    if (contacts == null || !contacts.containsKey(contactId)) {
	        System.out.println("Contact not found.");
	        return;
	    }

	    contacts.get(contactId).addTag(tag);

	    System.out.println("Tag assigned to contact.");
	}
	
}