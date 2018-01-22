package com.homecaravan.android.consumer.model.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Anh Dao on 12/4/2017.
 */

public class Mapping extends RealmObject {
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("time")
    private String time;
    @Expose
    @SerializedName("new")
    private boolean mNew;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean ismNew() {
        return mNew;
    }

    public void setmNew(boolean mNew) {
        this.mNew = mNew;
    }
}
