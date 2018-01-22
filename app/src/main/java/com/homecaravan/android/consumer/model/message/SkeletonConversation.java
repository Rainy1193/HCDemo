package com.homecaravan.android.consumer.model.message;

/**
 * Created by Anh Dao on 1/3/2018.
 */

public class SkeletonConversation {
    public static SkeletonConversation object;
    public MessageThread data = new MessageThread();

    public static SkeletonConversation getInstance() {
        if (object == null) {
            object = new SkeletonConversation();
        }
        return object;
    }

    public MessageThread getData() {
        return data;
    }

    public void setData(MessageThread data) {
        this.data = data;
    }

    public void clear(){
        data = new MessageThread();
    }
}
