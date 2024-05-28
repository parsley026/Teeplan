package com.example.teeplan.event;

public class EventModel {
    private String name;
    private String description;

    private String date;

    public EventModel(String name, String description, String date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}
