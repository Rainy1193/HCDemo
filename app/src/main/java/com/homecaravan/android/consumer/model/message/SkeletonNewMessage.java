package com.homecaravan.android.consumer.model.message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anh Dao on 11/27/2017.
 */

public class SkeletonNewMessage {

    public static SkeletonNewMessage object;
    public List<String> data = new ArrayList<>();

    public static SkeletonNewMessage getInstance() {
        if (object == null) {
            object = new SkeletonNewMessage();
        }
        return object;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
