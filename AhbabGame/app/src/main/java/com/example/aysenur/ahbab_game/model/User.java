package com.example.aysenur.ahbab_game.model;

public class User {

    private String userID;
    private String name;
    private String date;
    private String phoneNum;
    private String relativeName;
    private String relativePhoneNum;

    public User(){}

    public User(String userID, String name, String date, String phoneNum, String relativeName, String relativePhoneNum) {
        this.userID = userID;
        this.name = name;
        this.date = date;
        this.phoneNum = phoneNum;
        this.relativeName = relativeName;
        this.relativePhoneNum = relativePhoneNum;
    }

    public String getUserID() { return userID; }

    public void setUserID(String userID) { this.userID = userID; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getPhoneNum() { return phoneNum; }

    public void setPhoneNum(String phoneNum) { this.phoneNum = phoneNum; }

    public String getRelativeName() { return relativeName; }

    public void setRelativeName(String relativeName) { this.relativeName = relativeName; }

    public String getRelativePhoneNum() { return relativePhoneNum; }

    public void setRelativePhoneNum(String relativePhoneNum) { this.relativePhoneNum = relativePhoneNum; }
}
