package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseCaravan extends BaseResponse {
    @Expose
    @SerializedName("data")
    private CaravanData data;

    public CaravanData getData() {
        return data;
    }

    public class CaravanData {
        @Expose
        @SerializedName("id")
        private String id;
        @Expose
        @SerializedName("ciaId")
        private String ciaId;
        @Expose
        @SerializedName("title")
        private String title;
        @Expose
        @SerializedName("startingTime")
        private CaravanTime startingTime;
        @Expose
        @SerializedName("endingTime")
        private CaravanTime endingTime;
        // distance
        @Expose
        @SerializedName("startingLocation")
        private String startingLocation;
        @Expose
        @SerializedName("endingLocation")
        private String endingLocation;
        //        @Expose
//        @SerializedName("status")
//        private CaravanStatus status;
        @Expose
        @SerializedName("scheduleStatus")
        private CaravanScheduleStatus scheduleStatus;
        @Expose
        @SerializedName("destinations")
        private ArrayList<CaravanDestinations> destinations;
        @Expose
        @SerializedName("participants")
        private ArrayList<CaravanParticipants> participants;
        @Expose
        @SerializedName("json")
        private ArrayList<CaravanJson> json;

        public String getId() {
            return id;
        }

        public String getCiaId() {
            return ciaId;
        }

        public CaravanTime getStartingTime() {
            return startingTime;
        }

        public CaravanTime getEndingTime() {
            return endingTime;
        }

        public String getStartingLocation() {
            return startingLocation;
        }

        public String getEndingLocation() {
            return endingLocation;
        }

//        public CaravanStatus getStatus() {
//            return status;
//        }

        public CaravanScheduleStatus getScheduleStatus() {
            return scheduleStatus;
        }

        public ArrayList<CaravanDestinations> getDestinations() {
            return destinations;
        }

        public ArrayList<CaravanParticipants> getParticipants() {
            return participants;
        }

        public ArrayList<CaravanJson> getJson() {
            return json;
        }

        public void setJson(ArrayList<CaravanJson> json) {
            this.json = json;
        }

        public String getTitle() {
            return title;
        }
    }

    public class CaravanTime {
        @Expose
        @SerializedName("DATE")
        private String data;
        @Expose
        @SerializedName("TIMEZONE")
        private String timeZone;

        public String getData() {
            return data;
        }

        public String getTimeZone() {
            return timeZone;
        }
    }

    public class CaravanStatus {
        @Expose
        @SerializedName("NAME")
        private String nameStatus;
        @Expose
        @SerializedName("CLASS")
        private String classStatus;

        public String getNameStatus() {
            return nameStatus;
        }

        public String getClassStatus() {
            return classStatus;
        }
    }

    public class CaravanScheduleStatus {
        @Expose
        @SerializedName("approved")
        private int approved;
        @Expose
        @SerializedName("pending")
        private int pending;
        @Expose
        @SerializedName("cancelled")
        private int cancelled;
        @Expose
        @SerializedName("undefined")
        private int undefined;

        public int getApproved() {
            return approved;
        }

        public int getPending() {
            return pending;
        }

        public int getCancelled() {
            return cancelled;
        }

        public int getUndefined() {
            return undefined;
        }
    }

    public class CaravanDestinations {
        @Expose
        @SerializedName("type")
        private String type;
        @Expose
        @SerializedName("position")
        private String position;
        @Expose
        @SerializedName("drive")
        private DestinationsDrive DestinationsDrive;
        @Expose
        @SerializedName("duration")
        private DestinationsDuration DestinationsDuration;
        @Expose
        @SerializedName("timeFrom")
        private CaravanTime timeFrom;
        @Expose
        @SerializedName("timeTo")
        private CaravanTime timeTo;
        @Expose
        @SerializedName("address")
        private String address;
        @Expose
        @SerializedName("lat")
        private String lat;
        @Expose
        @SerializedName("lng")
        private String lng;
        @Expose
        @SerializedName("freeTimeBefore")
        private String freeTimeBefore;
        @Expose
        @SerializedName("freeTimeAfter")
        private String freeTimeAfter;
        @Expose
        @SerializedName("isFirst")
        private String isFirst;
        @Expose
        @SerializedName("isLast")
        private String isLast;
        @Expose
        @SerializedName("appointment")
        private Appointment appointment;
        @Expose
        @SerializedName("listing")
        private Listing listing;

        public String getType() {
            return type;
        }

        public String getPosition() {
            return position;
        }

        public DestinationsDrive getDestinationsDrive() {
            return DestinationsDrive;
        }

        public DestinationsDuration getDestinationsDuration() {
            return DestinationsDuration;
        }

        public String getAddress() {
            return address;
        }

        public String getLat() {
            return lat;
        }

        public String getLng() {
            return lng;
        }

        public String getFreeTimeBefore() {
            return freeTimeBefore;
        }

        public String getFreeTimeAfter() {
            return freeTimeAfter;
        }

        public String getIsFirst() {
            return isFirst;
        }

        public String getIsLast() {
            return isLast;
        }

        public Listing getListing() {
            return listing;
        }

        public CaravanTime getTimeFrom(){
            return timeFrom;
        }

        public CaravanTime getTimeTo(){
            return timeTo;
        }

        public Appointment getAppointment() {
            return appointment;
        }
    }

    public class DestinationsDrive {
        @Expose
        @SerializedName("value")
        private String value;
        @Expose
        @SerializedName("text")
        private String text;

        public String getValue() {
            return value;
        }

        public String getText() {
            return text;
        }
    }

    public class DestinationsDuration {
        @Expose
        @SerializedName("value")
        private String value;
        @Expose
        @SerializedName("text")
        private String text;

        public String getValue() {
            return value;
        }

        public String getText() {
            return text;
        }
    }

    public class Appointment{
        @Expose
        @SerializedName("id")
        private String id;
        @Expose
        @SerializedName("status")
        private AppointmentShowing.Status status;
        @Expose
        @SerializedName("timeFrom")
        private AppointmentShowing.TimeAppt timeFrom;
        @Expose
        @SerializedName("timeTo")
        private AppointmentShowing.TimeAppt timeTo;
        @Expose
        @SerializedName("listing")
        private Listing listing;

        public AppointmentShowing.TimeAppt getTimeFrom() {
            return timeFrom;
        }

        public AppointmentShowing.TimeAppt getTimeTo() {
            return timeTo;
        }

        public Listing getListing() {
            return listing;
        }

        public String getId() {
            return id;
        }

        public AppointmentShowing.Status getStatus() {
            return status;
        }
    }

}
