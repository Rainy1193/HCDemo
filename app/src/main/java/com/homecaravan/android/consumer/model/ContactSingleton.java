package com.homecaravan.android.consumer.model;

import java.util.ArrayList;

public class ContactSingleton {

    public static ContactSingleton contactSingleton;
    private ArrayList<ContactManagerData> arrContact = new ArrayList<>();
    private ArrayList<String> arrId = new ArrayList<>();
    public static ContactSingleton getInstance() {
        if (contactSingleton == null) {
            contactSingleton = new ContactSingleton();
        }
        return contactSingleton;
    }

    public ArrayList<ContactManagerData> getArrContact() {
        return arrContact;
    }

    public void setArrContact(ArrayList<ContactManagerData> arrContact) {
        this.arrContact = arrContact;
    }

    public ArrayList<String> getArrId() {
        return arrId;
    }

    public void setArrId(ArrayList<String> arrId) {
        this.arrId = arrId;
    }
}
