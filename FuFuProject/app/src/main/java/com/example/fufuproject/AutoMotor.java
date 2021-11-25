package com.example.fufuproject;

import com.google.firebase.database.Exclude;

public class AutoMotor {

    String time;

    @Exclude
    public String key;
    public String date;
////////////////////
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    /////////////

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public AutoMotor() {

    }
    public AutoMotor(String time) {
        this.time = time;
    }

}
