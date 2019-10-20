package com.example.aysenur.ahbab_game.wordgame;

public class User {
    private String userName;
    private boolean userGender;

    public User(String userName, boolean userGender) {
        this.userName = userName;
        this.userGender = userGender;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isUserGender() {
        return userGender;
    }

    public void setUserGender(boolean userGender) {
        this.userGender = userGender;
    }

}