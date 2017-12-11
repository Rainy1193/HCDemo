package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RAINY on 5/3/2016.
 */
public class ResuggestDetail {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("data")
    @Expose
    private ResuggestData data;


    public class ResuggestData{
        @SerializedName("SUGGESTION_ID")
        @Expose
        private String suggestionId;

        @SerializedName("EVENTS")
        @Expose
        private ArrayList<Event> events;

        @SerializedName("LISTING_DETAIL")
        @Expose
        private ListingDetailData dataListingDetail;

        public String getSuggestionId() {
            return suggestionId;
        }

        public void setSuggestionId(String suggestionId) {
            this.suggestionId = suggestionId;
        }

        public ArrayList<Event> getEvents() {
            return events;
        }

        public void setEvents(ArrayList<Event> events) {
            this.events = events;
        }

        public ListingDetailData getDataListingDetail() {
            return dataListingDetail;
        }

        public void setDataListingDetail(ListingDetailData dataListingDetail) {
            this.dataListingDetail = dataListingDetail;
        }

        public class Event{
            @SerializedName("ID")
            @Expose
            private String id;
            @SerializedName("START")
            @Expose
            private String start;
            @SerializedName("END")
            @Expose
            private String end;
            @SerializedName("DATE")
            @Expose
            private String date;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getStart() {
                return start;
            }

            public void setStart(String start) {
                this.start = start;
            }

            public String getEnd() {
                return end;
            }

            public void setEnd(String end) {
                this.end = end;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }
    }

    public ResuggestData getData() {
        return data;
    }

    public String getCode() {
        return code;
    }



    public String getMessage() {
        return message;
    }

    public String getSuccess() {
        return success;
    }
}
