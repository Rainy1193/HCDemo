package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 7/5/2016.
 */
public class NoteAppt {
    @SerializedName("datetime")
    @Expose
    private String timeNote;
    @SerializedName("content")
    @Expose
    private String contentNote;

    public NoteAppt(String timeNote, String contentNote) {
        this.timeNote = timeNote;
        this.contentNote = contentNote;
    }

    public String getTimeNote() {
        return timeNote;
    }

    public void setTimeNote(String timeNote) {
        this.timeNote = timeNote;
    }

    public String getContentNote() {
        return contentNote;
    }

    public void setContentNote(String contentNote) {
        this.contentNote = contentNote;
    }
}
