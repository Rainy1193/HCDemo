package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 11/23/2016.
 */

public class ParticipantLocation {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Participant participant;
    @SerializedName("code")
    @Expose
    private int code;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Participant getParticipantUser() {
        return participant;
    }

    public int getCode() {
        return code;
    }

    public class Participant {
        @SerializedName("ID")
        @Expose
        private String id;
        @SerializedName("LNG")
        @Expose
        private String lng;
        @SerializedName("LAT")
        @Expose
        private String lat;
        @SerializedName("CARAVAN_ID")
        @Expose
        private String cid;
        @SerializedName("USER_ID")
        @Expose
        private String uid;

        public String getId() {
            return id;
        }

        public String getLng() {
            return lng;
        }

        public String getLat() {
            return lat;
        }

        public String getCid() {
            return cid;
        }

        public String getUid() {
            return uid;
        }

        @Override
        public String toString() {
            return "Participant{" +
                    "id='" + id + '\'' +
                    ", lng='" + lng + '\'' +
                    ", lat='" + lat + '\'' +
                    ", cid=" + cid +
                    ", uid=" + uid +
                    '}';
        }
    }
}
