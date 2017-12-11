package com.homecaravan.android.consumer.model;

public class EventContact {

    private String type;
    private ContactManagerData data;

    public EventContact(String type, ContactManagerData data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public ContactManagerData getData() {
        return data;
    }
}
