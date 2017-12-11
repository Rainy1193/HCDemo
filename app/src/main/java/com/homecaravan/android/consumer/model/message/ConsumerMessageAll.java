package com.homecaravan.android.consumer.model.message;


import io.realm.RealmObject;

/**
 * Created by Anh Dao on 8/31/2017.
 */

public class ConsumerMessageAll extends RealmObject {

    private MessageThread messageThread;
    private String type;

    public MessageThread getMessageThread() {
        return messageThread;
    }

    public void setMessageThread(MessageThread messageThread) {
        this.messageThread = messageThread;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
