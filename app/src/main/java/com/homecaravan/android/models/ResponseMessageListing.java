package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 5/30/2016.
 */
public class ResponseMessageListing  {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private DataListingMessage dataListingMessage;
    @SerializedName("code")
    @Expose
    private int code;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public DataListingMessage getDataListingMessage() {
        return dataListingMessage;
    }

    public int getCode() {
        return code;
    }

    public class MListingMessage {
        @SerializedName("APPT_CONFIRMATION")
        @Expose
        private String apptConfirmation;
        @SerializedName("PRO_FEEDBACK")
        @Expose
        private String proFeedBack;
        @SerializedName("ORD_FEEDBACK")
        @Expose
        private String ordFeedBack;
        @SerializedName("PRO_REMINDER")
        @Expose
        private String proReminder;
        @SerializedName("ORD_REMINDER")
        @Expose
        private String ordReminder;

        public String getApptConfirmation() {
            return apptConfirmation;
        }

        public String getProFeedBack() {
            return proFeedBack;
        }

        public String getOrdFeedBack() {
            return ordFeedBack;
        }

        public String getProReminder() {
            return proReminder;
        }

        public String getOrdReminder() {
            return ordReminder;
        }
    }

    public class MListing {
        @SerializedName("__initializer__")
        @Expose
        private String initializer;
        @SerializedName("__cloner__")
        @Expose
        private String cloner;
        @SerializedName("__isInitialized__")
        @Expose
        private String isInitialized;

        public String getInitializer() {
            return initializer;
        }

        public String getCloner() {
            return cloner;
        }

        public String getIsInitialized() {
            return isInitialized;
        }
    }

    public class DataListingMessage {
        @SerializedName("LISTING_MESSAGES")
        @Expose
        private MListingMessage mListingMessage;
        @SerializedName("LISTING")
        @Expose
        private MListing mListing;

        public MListingMessage getmListingMessage() {
            return mListingMessage;
        }

        public MListing getmListing() {
            return mListing;
        }
    }
}
