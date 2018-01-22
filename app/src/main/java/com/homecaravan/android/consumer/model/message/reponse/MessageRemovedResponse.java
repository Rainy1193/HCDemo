package com.homecaravan.android.consumer.model.message.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.homecaravan.android.consumer.model.message.MessageThread;

/**
 * Created by Anh Dao on 11/2/2017.
 */

public class MessageRemovedResponse {
    @Expose
    @SerializedName("command")
    private String command;
    @Expose
    @SerializedName("key")
    private String key;
    @Expose
    @SerializedName("value")
    private MessageThread messageThread;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MessageThread getMessageThread() {
        return messageThread;
    }

    public void setMessageThread(MessageThread messageThread) {
        this.messageThread = messageThread;
    }
}
