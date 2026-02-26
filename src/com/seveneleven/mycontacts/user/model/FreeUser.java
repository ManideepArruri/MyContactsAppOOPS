package com.seveneleven.mycontacts.user.model;

public class FreeUser extends User {

    public FreeUser(String email, String passwordHash, String userName) {
        super(email, passwordHash, userName);
    }

    @Override
    public String getUserType() {
        return "FREE";
    }
}