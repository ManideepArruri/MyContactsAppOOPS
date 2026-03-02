package com.seveneleven.mycontacts.contact.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Contact {

    private String contactId;
    private String name;
    private String phoneNumber;
    private String email;
    private LocalDateTime createdAt;
    private boolean deleted = false;
    private Set<Tag> tags = new HashSet<>();
    private int contactCount = 0;

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
               "\nCreated At: " + createdAt +
               "\nStatus: " + (deleted ? "Deleted" : "Active") + 
               "\nTags: " + tags;
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
    
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    public Set<Tag> getTags() {
        return tags;
    }
    
    public int getContactCount() {
        return contactCount;
    }

    public void increaseContactCount() {
        contactCount++;
    }
}