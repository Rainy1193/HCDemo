package com.homecaravan.android.consumer.model.message;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 11/28/2017.
 */

public class SkeletonMessageThread {
    public static SkeletonMessageThread object;
    public ArrayList<ConsumerMessageAll> data = new ArrayList<>();

    public static SkeletonMessageThread getInstance() {
        if (object == null) {
            object = new SkeletonMessageThread();
        }
        return object;
    }

    public ArrayList<ConsumerMessageAll> getData() {
        return data;
    }

    public void setData(ArrayList<ConsumerMessageAll> data) {
        this.data = data;
    }
}
