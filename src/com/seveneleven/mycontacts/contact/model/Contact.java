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
    
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
    }

    // Normal display
    public String getNormalDetails() {
        return formatDetails(name, phoneNumber, email);
    }

    // Uppercase display
    public String getUpperCaseDetails() {
        return formatDetails(
                name.toUpperCase(),
                phoneNumber.toUpperCase(),
                email.toUpperCase()
        );
    }

    // Masked email display
    public String getMaskedEmailDetails() {
        String masked = maskEmail(email);
        return formatDetails(name, phoneNumber, masked);
    }

    private String formatDetails(String name,
                                 String phone,
                                 String email) {

        return "\nContact ID: " + contactId +
               "\nName: " + name +
               "\nPhone: " + phone +
               "\nEmail: " + email +
               "\nCreated At: " + createdAt;
    }

    private String maskEmail(String email) {

        int atIndex = email.indexOf("@");

        if (atIndex <= 2) {
            return "****" + email.substring(atIndex);
        }

        String visible = email.substring(0, 2);
        String domain = email.substring(atIndex);

        return visible + "****" + domain;
    }
}