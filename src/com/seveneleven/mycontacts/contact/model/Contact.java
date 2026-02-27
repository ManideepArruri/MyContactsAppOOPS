package com.seveneleven.mycontacts.contact.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Contact {

    private String contactId;
    private String name;
    private String phoneNumber;
    private String email;
    private LocalDateTime createdAt;

    public Contact(String name, String phoneNumber, String email) {
        this.contactId = UUID.randomUUID().toString();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }

    public String getContactId() {
        return contactId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "\nContact ID: " + contactId +
               "\nName: " + name +
               "\nPhone: " + phoneNumber +
               "\nEmail: " + email +
               "\nCreated At: " + createdAt;
    }
}