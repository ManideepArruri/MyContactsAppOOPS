package com.seveneleven.mycontacts.user.model;

public class PremiumUser extends User {

    public PremiumUser(String email, String passwordHash, String userName) {
        super(email, passwordHash, userName);
    }

    @Override
    public String getUserType() {
        return "PREMIUM";
    }
}
