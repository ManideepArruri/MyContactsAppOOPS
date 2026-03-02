package com.seveneleven.mycontacts.contact.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.seveneleven.mycontacts.contact.model.Tag;
import com.seveneleven.mycontacts.user.model.User;
import com.seveneleven.mycontacts.user.service.AuthService;

public class TagService {

    private AuthService authService;

    // User email → Set of Tags
    private Map<String, Set<Tag>> userTags = new HashMap<>();

    public TagService(AuthService authService) {
        this.authService = authService;
    }

    // Create Tag
    public void createTag(String tagName) {

        User user = authService.getLoggedInUser();

        if (user == null) {
            throw new IllegalStateException("Login required");
        }

        Tag tag = new Tag(tagName);

        userTags
            .computeIfAbsent(user.getEmail(), k -> new HashSet<>())
            .add(tag);

        System.out.println("Tag created successfully.");
    }

    // View Tags
    public void viewTags() {

        User user = authService.getLoggedInUser();

        if (user == null) {
            throw new IllegalStateException("Login required");
        }

        Set<Tag> tags = userTags.get(user.getEmail());

        if (tags == null || tags.isEmpty()) {
            System.out.println("No tags created.");
            return;
        }

        for (Tag tag : tags) {
            System.out.println(tag);
        }
    }

    public Set<Tag> getUserTags(String email) {
        return userTags.get(email);
    }
}