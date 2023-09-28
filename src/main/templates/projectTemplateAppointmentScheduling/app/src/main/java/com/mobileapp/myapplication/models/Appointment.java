package com.mobileapp.myapplication.models;

import com.google.firebase.Timestamp;

public class Appointment {
    private String id;
    private String userId;
    private String date;
    private String time;
    private String description;
    private Timestamp timestamp;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    public Appointment() {

    }

    public Appointment(String userId, String date, String time) {
        this.userId = userId;
        this.date = date;
        this.time = time;
    }

    public Appointment(String userId, String date, String time, String description, Timestamp timestamp) {
        this.userId = userId;
        this.date = date;
        this.time = time;
        this.description = description;
        this.timestamp = timestamp;
    }



}
