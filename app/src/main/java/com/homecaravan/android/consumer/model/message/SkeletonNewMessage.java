package com.homecaravan.android.consumer.model.message;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 11/27/2017.
 */

public class SkeletonNewMessage {

    public static SkeletonNewMessage object;
    public ArrayList<String> data = new ArrayList<>();

    public static SkeletonNewMessage getInstance() {
        if (object == null) {
            object = new SkeletonNewMessage();
        }
        return object;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void reset(){
        object = new SkeletonNewMessage();
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }
}
