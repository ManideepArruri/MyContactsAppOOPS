package com.seveneleven.mycontacts.contact.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.seveneleven.mycontacts.contact.model.Contact;
import com.seveneleven.mycontacts.contact.model.Tag;
import com.seveneleven.mycontacts.user.model.User;
import com.seveneleven.mycontacts.user.service.AuthService;

public class BulkContactService {

    private AuthService authService;
    private Map<String, Map<String, Contact>> userContacts;

    public BulkContactService(AuthService authService,
                              Map<String, Map<String, Contact>> userContacts) {
        this.authService = authService;
        this.userContacts = userContacts;
    }

    //  Bulk Soft Delete
    public void bulkSoftDelete(List<String> contactIds) {

        User user = authService.getLoggedInUser();
        if (user == null) {
            throw new IllegalStateException("Login required");
        }

        Map<String, Contact> contacts = userContacts.get(user.getEmail());

        for (String id : contactIds) {
            if (contacts.containsKey(id)) {
                contacts.get(id).setDeleted(true);
            }
        }

        System.out.println("Bulk soft delete completed.");
    }

    //  Bulk Hard Delete
    public void bulkHardDelete(List<String> contactIds) {

        User user = authService.getLoggedInUser();
        if (user == null) {
            throw new IllegalStateException("Login required");
        }

        Map<String, Contact> contacts = userContacts.get(user.getEmail());

        for (String id : contactIds) {
            contacts.remove(id);
        }

        System.out.println("Bulk hard delete completed.");
    }

    //  Bulk Add Tag
    public void bulkAddTag(List<String> contactIds, Tag tag) {

        User user = authService.getLoggedInUser();
        if (user == null) {
            throw new IllegalStateException("Login required");
        }

        Map<String, Contact> contacts = userContacts.get(user.getEmail());

        for (String id : contactIds) {
            if (contacts.containsKey(id)) {
                contacts.get(id).addTag(tag);
            }
        }

        System.out.println("Tag added to selected contacts.");
    }

    //  Bulk Export
    public void bulkExport(List<String> contactIds, String fileName) {

        User user = authService.getLoggedInUser();
        if (user == null) {
            throw new IllegalStateException("Login required");
        }

        Map<String, Contact> contacts = userContacts.get(user.getEmail());

        try (FileWriter writer = new FileWriter(fileName)) {

            for (String id : contactIds) {
                if (contacts.containsKey(id)) {
                    writer.write(contacts.get(id).toString());
                    writer.write("\n-----------------\n");
                }
            }

            System.out.println("Contacts exported to " + fileName);

        } catch (IOException e) {
            System.out.println("Error exporting contacts.");
        }
    }
}