package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TopTrend {
    @SerializedName("voteDown")
    @Expose
    private String voteDown;
    @SerializedName("voteScore")
    @Expose
    private String voteScore;
    @SerializedName("usersVoteUp")
    @Expose
    private ArrayList<UserVote> usersVoteUp;
    @SerializedName("usersVoteDown")
    @Expose
    private ArrayList<UserVote> usersVoteDown;
    @SerializedName("mid")
    @Expose
    private String mid;
    @SerializedName("listingHasVoted")
    @Expose
    private String listingHasVoted;
    @SerializedName("votedNum")
    @Expose
    private String votedNum;
    @SerializedName("votedPercent")
    @Expose
    private String votedPercent;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("lkey")
    @Expose
    private String lkey;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("listingType")
    @Expose
    private String listingType;
    @SerializedName("saleType")
    @Expose
    private String saleType;
    @SerializedName("propertyType")
    @Expose
    private String propertyType;
    @SerializedName("address")
    @Expose
    private Address address;

    public String getVoteDown() {
        return voteDown;
    }

    public String getVoteScore() {
        return voteScore;
    }

    public ArrayList<UserVote> getUsersVoteUp() {
        return usersVoteUp;
    }

    public ArrayList<UserVote> getUsersVoteDown() {
        return usersVoteDown;
    }

    public String getMid() {
        return mid;
    }

    public String getListingHasVoted() {
        return listingHasVoted;
    }

    public String getVotedNum() {
        return votedNum;
    }

    public String getVotedPercent() {
        return votedPercent;
    }

    public String getId() {
        return id;
    }

    public String getLkey() {
        return lkey;
    }

    public String getStatus() {
        return status;
    }

    public String getListingType() {
        return listingType;
    }

    public String getSaleType() {
        return saleType;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "TopTrend{" +
                "voteDown='" + voteDown + '\'' +
                ", voteScore='" + voteScore + '\'' +
                ", usersVoteUp=" + usersVoteUp +
                ", usersVoteDown=" + usersVoteDown +
                ", mid='" + mid + '\'' +
                ", listingHasVoted='" + listingHasVoted + '\'' +
                ", votedNum='" + votedNum + '\'' +
                ", votedPercent='" + votedPercent + '\'' +
                ", id='" + id + '\'' +
                ", lkey='" + lkey + '\'' +
                ", status='" + status + '\'' +
                ", listingType='" + listingType + '\'' +
                ", saleType='" + saleType + '\'' +
                ", propertyType='" + propertyType + '\'' +
                ", address=" + address +
                '}';
    }
}
