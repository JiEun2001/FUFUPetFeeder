package com.example.fufuproject;

import com.google.firebase.database.Exclude;

public class UserProfile {
    public String userAge;
    public String userEmail;
    public String userName;

    @Exclude
    public String Key;
    public String storageKey;
    //getter setter key and storage key
    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getStorageKey() {
        return storageKey;
    }

    public void setStorageKey(String storageKey) {
        this.storageKey = storageKey;
    }
    /////////////////////////////////////////////////////////
    public  UserProfile() {
    }

    public UserProfile(String userAge, String userEmail, String userName) {
        this.userAge = userAge;
        this.userEmail = userEmail;
        this.userName = userName;
    }

    public String getUserAge() {
        if(userAge != null){

            return userAge;
        }else{
            return "error";
        }
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserEmail() {
        if(userEmail != null){

            return userEmail;
        }else{
            return "error";
        }
    }

    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUserName() {
        if(userName != null){

            return userName;
        }else{
            return "error";
        }
    }

    public void setUserName(String userName) { this.userName = userName; }
}
