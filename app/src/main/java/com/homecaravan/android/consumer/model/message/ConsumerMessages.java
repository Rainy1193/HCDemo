package com.homecaravan.android.consumer.model.message;

import io.realm.RealmObject;

/**
 * Created by Anh Dao on 9/11/2017.
 */

public class ConsumerMessages extends RealmObject {
    private MessageItem messageItem;
    private String type;
    private String date;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public MessageItem getMessageItem() {
        return messageItem;
    }

    public void setMessageItem(MessageItem messageItem) {
        this.messageItem = messageItem;
    }
}
