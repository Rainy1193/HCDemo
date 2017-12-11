package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RAINY on 6/3/2016.
 */
public class ResponseChangeMemberStatus {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("hc_error_message")
    @Expose
    private String message;
    @SerializedName("hc_error_title")
    @Expose
    private String title;
    @SerializedName("data")
    @Expose
    private MemberList memberList;
    @SerializedName("code")
    @Expose
    private int code;

    public String getTitle() {
        return title;
    }

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public MemberList getMemberList() {
        return memberList;
    }

    public int getCode() {
        return code;
    }

    public class MemberList {
        @SerializedName("MEMBER_LIST")
        @Expose
        private ArrayList<ListingMember> memberLists;

        public ArrayList<ListingMember> getMemberLists() {
            return memberLists;
        }
    }

}
