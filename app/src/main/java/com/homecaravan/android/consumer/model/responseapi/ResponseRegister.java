package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseRegister extends BaseResponse {
    @Expose
    @SerializedName("data")
    private RegisterData data;

    public RegisterData getData() {
        return data;
    }

    public class RegisterData {
        @Expose
        @SerializedName("id")
        private String id;
        @Expose
        @SerializedName("firstName")
        private String firstName;
        @Expose
        @SerializedName("lastName")
        private String lastName;
        @Expose
        @SerializedName("fullName")
        private String fullName;
        @Expose
        @SerializedName("avatar")
        private String avatar;
        @Expose
        @SerializedName("pn_uid")
        private String pnUid;
        @Expose
        @SerializedName("pn_tid")
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
    }
}
