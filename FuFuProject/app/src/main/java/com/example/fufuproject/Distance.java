package com.example.fufuproject;

import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Distance {

    public String historydistance;

    @Exclude
    public String key;
    public String date;
////////////////////
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        String formatstring = "yyyy-MM-dd HH:mm:ss";

        SimpleDateFormat format = new SimpleDateFormat(formatstring);
        date = format.format(new Date(Long.parseLong(key)*1000 + 28800000));
        //date = "0";
    }

    public String getDate() {
        return date;
    }

    public void setDate(String dare) {
        this.date = dare;
    }

    /////////////////////////
    public Distance(){
    }
    public Distance(String historydistance) {
        this.historydistance = historydistance;
    }

    public String getHistorydistance() {
        return historydistance;
    }

    public void setHistorydistance(String historydistance) {
        this.historydistance = historydistance;
    }
    ////////////////////////////
}
