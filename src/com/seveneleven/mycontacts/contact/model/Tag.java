package com.seveneleven.mycontacts.contact.model;

import java.util.Objects;

public class Tag {

    private String name;

    public Tag(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tag name cannot be empty");
        }
        this.name = name.trim().toLowerCase();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Tag tag = (Tag) obj;

        return name.equals(tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}