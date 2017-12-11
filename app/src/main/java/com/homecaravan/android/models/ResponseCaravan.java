package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RAINY on 10/5/2016.
 */

public class ResponseCaravan {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private DataCaravan dataCaravan;
    @SerializedName("code")
    @Expose
    private String code;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public DataCaravan getDataCaravan() {
        return dataCaravan;
    }

    public String getCode() {
        return code;
    }

    public class DataCaravan {
        @SerializedName("TOTAL")
        @Expose
        private String total;
        @SerializedName("CARAVANS")
        @Expose
        private ArrayList<Caravans> caravans;

        public String getTotal() {
            return total;
        }

        public ArrayList<Caravans> getCaravans() {
            return caravans;
        }
    }

    public class Caravans {
        @SerializedName("ID")
        @Expose
        private String id;
        @SerializedName("USER_ID")
        @Expose
        private String uid;
        @SerializedName("TITLE")
        @Expose
        private String title;
        @SerializedName("STARTING_TIME")
        @Expose
        private StartTime startTime;
        @SerializedName("ENDING_TIME")
        @Expose
        private EndTime endTime;
        @SerializedName("DISTANCE")
        @Expose
        private String distance;
        @SerializedName("STARTING_LOCATION")
        @Expose
        private String startLocation;
        @SerializedName("ENDING_LOCATION")
        @Expose
        private String endLocation;
        @SerializedName("DESTINATIONS")
        @Expose
        private ArrayList<Destinations> arrDestination;
        @SerializedName("PARTICIPANTS")
        @Expose
        private ArrayList<Participant> arrParticipant;

        public String getId() {
            return id;
        }

        public String getUid() {
            return uid;
        }

        public String getTitle() {
            return title;
        }

        public StartTime getStartTime() {
            return startTime;
        }

        public EndTime getEndTime() {
            return endTime;
        }

        public String getDistance() {
            return distance;
        }

        public String getStartLocation() {
            return startLocation;
        }

        public String getEndLocation() {
            return endLocation;
        }

        public ArrayList<Destinations> getArrDestination() {
            return arrDestination;
        }

        public ArrayList<Participant> getArrParticipant() {
            return arrParticipant;
        }
    }

    public class StartTime {
        @SerializedName("DATE")
        @Expose
        private String date;
        @SerializedName("TIMEZONE")
        @Expose
        private String timeZone;

        public String getDate() {
            return date;
        }

        public String getTimeZone() {
            return timeZone;
        }
    }

    public class EndTime {
        @SerializedName("DATE")
        @Expose
        private String date;
        @SerializedName("TIMEZONE")
        @Expose
        private String timeZone;

        public String getDate() {
            return date;
        }

        public String getTimeZone() {
            return timeZone;
        }
    }

    public class Destinations {
        @SerializedName("TYPE")
        @Expose
        private String type;
        @SerializedName("POSITION")
        @Expose
        private String position;
        @SerializedName("DRIVE")
        @Expose
        private Drive drive;
        @SerializedName("DURATION")
        @Expose
        private Duration duration;
        @SerializedName("TIME_FROM")
        @Expose
        private TimeFrom timeFrom;
        @SerializedName("TIME_TO")
        @Expose
        private TimeTo timeTo;
        @SerializedName("ADDRESS")
        @Expose
        private String address;
        @SerializedName("APPOINTMENT")
        @Expose
        private String apptId;
        @SerializedName("LISTING")
        @Expose
        private String listingId;

        public String getType() {
            return type;
        }

        public String getPosition() {
            return position;
        }

        public Drive getDrive() {
            return drive;
        }

        public Duration getDuration() {
            return duration;
        }

        public TimeFrom getTimeFrom() {
            return timeFrom;
        }

        public TimeTo getTimeTo() {
            return timeTo;
        }

        public String getAddress() {
            return address;
        }

        public String getApptId() {
            return apptId;
        }

        public String getListingId() {
            return listingId;
        }
    }

    public class Drive {
        @SerializedName("VALUE")
        @Expose
        private String value;
        @SerializedName("TEXT")
        @Expose
        private String text;

        public String getValue() {
            return value;
        }

        public String getText() {
            return text;
        }
    }

    public class Duration {
        @SerializedName("VALUE")
        @Expose
        private String value;
        @SerializedName("TEXT")
        @Expose
        private String text;

        public String getValue() {
            return value;
        }

        public String getText() {
            return text;
        }
    }

    public class TimeFrom {
        @SerializedName("DATE")
        @Expose
        private String date;
        @SerializedName("TIMEZONE")
        @Expose
        private String timeZone;

        public String getDate() {
            return date;
        }

        public String getTimeZone() {
            return timeZone;
        }
    }

    public class TimeTo {
        @SerializedName("DATE")
        @Expose
        private String date;
        @SerializedName("TIMEZONE")
        @Expose
        private String timeZone;

        public String getDate() {
            return date;
        }

        public String getTimeZone() {
            return timeZone;
        }
    }

    public class Participant {
        @SerializedName("USER_ID")
        @Expose
        private String uid;
        @SerializedName("FULL_NAME")
        @Expose
        private String fullName;

        public String getUid() {
            return uid;
        }

        public String getFullName() {
            return fullName;
        }
    }
}
