package com.homecaravan.android.consumer.model;

import io.realm.RealmObject;

public class ListingDB extends RealmObject {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
