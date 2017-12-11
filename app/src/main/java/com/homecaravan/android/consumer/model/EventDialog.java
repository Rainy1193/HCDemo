package com.homecaravan.android.consumer.model;

import android.support.annotation.StringRes;

public class EventDialog {
    private TypeDialog type;
    private
    @StringRes
    int message;
    private String messageString;
    private String action;

    public EventDialog(TypeDialog type, int message, String messageString, String action) {
        this.type = type;
        this.message = message;
        this.messageString = messageString;
        this.action = action;
    }

    public TypeDialog getType() {
        return type;
    }

    public int getMessage() {
        return message;
    }

    public String getMessageString() {
        return messageString;
    }

    public String getAction() {
        return action;
    }
}
