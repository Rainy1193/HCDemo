package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.homecaravan.android.consumer.model.BaseDataRecyclerView;

import java.util.ArrayList;

public class ResponseFeatured extends BaseResponse {
    @Expose
    @SerializedName("data")
    private ArrayList<Featured> getFeatured;

    public ArrayList<Featured> getGetFeatured() {
        return getFeatured;
    }

    public class Featured extends BaseDataRecyclerView {
        private boolean select;
        @Expose
        @SerializedName("id")
        private String id;
        @Expose
        @SerializedName("fullName")
        private String fullName;
        @Expose
        @SerializedName("firstName")
        private String firstName;
        @Expose
        @SerializedName("lastName")
        private String lastName;
        @Expose
        @SerializedName("avatar")
        private String avatar;
        @Expose
        @SerializedName("pn_uid")
        private String pnUid;
        @Expose
        @SerializedName("pn_tid")
        private String pnTid;
        @Expose
        @SerializedName("businessRole")
        private String businessRole;
        @Expose
        @SerializedName("receiveNotifications")
        private String receiveNotifications;
        @Expose
        @SerializedName("newHomesNotifications")
        private String newHomesNotifications;
        @Expose
        @SerializedName("emailSmsNotifications")
        private String emailSmsNotifications;
        @Expose
        @SerializedName("company")
        private ArrayList<Company> company;
        public String getId() {
            return id;
        }

        public String getFullName() {
            return fullName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
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

        public String getBusinessRole() {
            return businessRole;
        }

        public String getReceiveNotifications() {
            return receiveNotifications;
        }

        public String getNewHomesNotifications() {
            return newHomesNotifications;
        }

        public String getEmailSmsNotifications() {
            return emailSmsNotifications;
        }

        public ArrayList<Company> getCompany() {
            return company;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }
    }
}
