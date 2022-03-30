package com.example.webfactory.model;

public class CalendarUser {
    private String dateID;
    private String date;

    public CalendarUser() {
    }

    public CalendarUser(String date) {
        this.date = date;
    }

    public CalendarUser(String dateID, String date) {
        this.dateID = dateID;
        this.date = date;
    }

    public String getDateID() {
        return dateID;
    }

    public void setDateID(String dateID) {
        this.dateID = dateID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
