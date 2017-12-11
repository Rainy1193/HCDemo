package com.homecaravan.android.consumer.listener;

import com.homecaravan.android.consumer.model.ContactManagerData;

public interface IContactManager {

    void editContact(ContactManagerData managerData, int position);

    void pickContact(ContactManagerData managerData, String id, int position, boolean b);

    void removeContact(String id);

    void updateContact(String id);
}
