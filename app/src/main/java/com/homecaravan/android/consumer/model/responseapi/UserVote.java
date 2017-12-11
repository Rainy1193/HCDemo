package com.homecaravan.android.consumer.model.responseapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserVote {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("pn_uid")
    @Expose
    private String pnUid;
    @SerializedName("pn_tid")
    @Expose
    private String pnTid;

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getPnUid() {
        return pnUid;
    }

    public String getPnTid() {
        return pnTid;
    }

    @Override
    public String toString() {
        return "UserVote{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", pnUid='" + pnUid + '\'' +
                ", pnTid='" + pnTid + '\'' +
                '}';
    }
}
