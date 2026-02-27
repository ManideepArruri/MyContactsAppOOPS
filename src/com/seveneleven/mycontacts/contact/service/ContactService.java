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
}