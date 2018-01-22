package com.homecaravan.android.consumer.model.message;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 11/28/2017.
 */

public class SkeletonMessageThread {
    public static SkeletonMessageThread object;
    public ArrayList<MessageThread> data = new ArrayList<>();

    public static SkeletonMessageThread getInstance() {
        if (object == null) {
            object = new SkeletonMessageThread();
        }
        return object;
    }

    public ArrayList<MessageThread> getData() {
        return data;
    }

    public void setData(ArrayList<MessageThread> data) {
        this.data = data;
    }
}
