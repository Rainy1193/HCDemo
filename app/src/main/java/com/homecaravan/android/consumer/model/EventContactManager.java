package com.homecaravan.android.consumer.model;


public class EventContactManager {
    public String status;
    public ContactManagerData contactManagerData;

    public EventContactManager(String status,ContactManagerData contactManagerData) {
        this.contactManagerData = contactManagerData;
        this.status=status;
    }
}
