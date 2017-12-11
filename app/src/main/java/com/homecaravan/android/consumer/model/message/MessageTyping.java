package com.homecaravan.android.consumer.model.message;

/**
 * Created by Anh Dao on 11/27/2017.
 */

public class MessageTyping {
    private String idTyping;
    private String nameTyping;

    public MessageTyping(String idTyping, String nameTyping) {
        this.idTyping = idTyping;
        this.nameTyping = nameTyping;
    }

    public String getIdTyping() {
        return idTyping;
    }

    public void setIdTyping(String idTyping) {
        this.idTyping = idTyping;
    }

    public String getNameTyping() {
        return nameTyping;
    }

    public void setNameTyping(String nameTyping) {
        this.nameTyping = nameTyping;
    }

}
