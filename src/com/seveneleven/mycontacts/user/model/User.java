package com.seveneleven.mycontacts.user.model;


public abstract class User {

    private String email;
    private String passwordHash;
    private String userName;

    public User(String email, String passwordHash, String userName) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public abstract String getUserType();
}
