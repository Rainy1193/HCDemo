package com.homecaravan.android.consumer.model.message;

import io.realm.RealmObject;

/**
 * Created by Anh Dao on 9/7/2017.
 */


public class MessageUser extends RealmObject {
    public static MessageUser user;
    public MessageUserData data = new MessageUserData();

    public static MessageUser getInstance() {
        if (user == null) {
            user = new MessageUser();
        }
        return user;
    }

    public MessageUserData getData() {
        return data;
    }

}