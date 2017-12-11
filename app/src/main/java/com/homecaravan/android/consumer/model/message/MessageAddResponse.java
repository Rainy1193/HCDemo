package com.homecaravan.android.consumer.model.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anh Dao on 11/3/2017.
 */

public class MessageAddResponse {
    @Expose
    @SerializedName("command")
    private String command;
    @Expose
    @SerializedName("key")
    private String key;
    @Expose
    @SerializedName("value")
    private MessageItem messageItem;
    private String mValue;

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

    public MessageItem getMessageItem() {
        return messageItem;
    }

    public void setMessageItem(MessageItem messageItem) {
        this.messageItem = messageItem;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String mValue) {
        this.mValue = mValue;
    }
}
