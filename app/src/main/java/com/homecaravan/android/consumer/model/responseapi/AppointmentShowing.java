package com.homecaravan.android.consumer.model.responseapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppointmentShowing {
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("start")
    private String start;
    @Expose
    @SerializedName("end")
    private String end;
    @Expose
    @SerializedName("backgroundColor")
    private String backgroundColor;
    @Expose
    @SerializedName("borderColor")
    private String borderColor;
    @Expose
    @SerializedName("eventType")
    private String eventType;
    @Expose
    @SerializedName("eventStatus")
    private String eventStatus;
    @Expose
    @SerializedName("ref")
    private RefAppointment refAppointment;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public RefAppointment getRefAppointment() {
        return refAppointment;
    }

    public class RefAppointment {
        @Expose
        @SerializedName("showingInstructions")
        private String showingInstructions;
        @Expose
        @SerializedName("astData")
        private String astDa;
        @Expose
        @SerializedName("showRescheduleBtn")
        private String showRescheduleBtn;
        @Expose
        @SerializedName("showApproveBtn")
        private String showApproveBtn;
        @Expose
        @SerializedName("showDeferBtn")
        private String showDeferBtn;
        @Expose
        @SerializedName("showCancelBtn")
        private String showCancelBtn;
        @Expose
        @SerializedName("showRejectBtn")
        private String showRejectBtn;
        @Expose
        @SerializedName("showAwaitingAgent")
        private String showAwaitingAgent;
        @Expose
        @SerializedName("client")
        private Client client;
        @Expose
        @SerializedName("agent")
        private Agent agent;
        @Expose
        @SerializedName("id")
        private String id;
        @Expose
        @SerializedName("apptType")
        private Object apptType;
        @Expose
        @SerializedName("status")
        private Status status;
        @Expose
        @SerializedName("timeslotType")
        private String timeslotType;
        @Expose
        @SerializedName("timeFrom")
        private TimeAppt timeFrom;
        @Expose
        @SerializedName("timeTo")
        private TimeAppt timeTo;
        @Expose
        @SerializedName("bookBy")
        private BookAppt bookBy;
        @Expose
        @SerializedName("bookFor")
        private BookAppt bookFor;
        @Expose
        @SerializedName("listing")
        private Listing listing;

        public String getShowingInstructions() {
            return showingInstructions;
        }

        public String getAstDa() {
            return astDa;
        }

        public String getShowRescheduleBtn() {
            return showRescheduleBtn;
        }

        public String getShowApproveBtn() {
            return showApproveBtn;
        }

        public String getShowDeferBtn() {
            return showDeferBtn;
        }

        public String getShowCancelBtn() {
            return showCancelBtn;
        }

        public String getShowRejectBtn() {
            return showRejectBtn;
        }

        public String getShowAwaitingAgent() {
            return showAwaitingAgent;
        }

        public Client getClient() {
            return client;
        }

        public Agent getAgent() {
            return agent;
        }

        public String getId() {
            return id;
        }

        public Object getApptType() {
            return apptType;
        }
//
//        public String getStatus() {
//            return status;
//        }

        public String getTimeslotType() {
            return timeslotType;
        }

        public TimeAppt getTimeFrom() {
            return timeFrom;
        }

        public TimeAppt getTimeTo() {
            return timeTo;
        }

        public BookAppt getBookBy() {
            return bookBy;
        }

        public BookAppt getBookFor() {
            return bookFor;
        }

        public Listing getListing() {
            return listing;
        }

        public Status getStatus() {
            return status;
        }
    }

    public class Client {
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

    public class Agent {
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

    public class TimeAppt {
        @Expose
        @SerializedName("DATE")
        private String date;
        @Expose
        @SerializedName("TIMEZONE")
        private String timeZone;

        public String getDate() {
            return date;
        }

        public String getTimeZone() {
            return timeZone;
        }
    }

    public class BookAppt {
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

    public class Status {
        @Expose
        @SerializedName("STATUS")
        private String status;
        @Expose
        @SerializedName("SHOW_BUTTON")
        private String showButton;
        @Expose
        @SerializedName("EXTRA")
        private StatusExtra statusExtra;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status){
            this.status = status;
        }

        public String getShowButton() {
            return showButton;
        }

        public StatusExtra getStatusExtra() {
            return statusExtra;
        }
    }

    public class StatusExtra {
        @Expose
        @SerializedName("NAME")
        private String name;
        @Expose
        @SerializedName("CLASS")
        private String sClass;
        @Expose
        @SerializedName("NOTE")
        private String note;
        @Expose
        @SerializedName("MODIFIED_BY")
        private String modifiedBy;
        @Expose
        @SerializedName("MODIFIED_ON")
        private String modifiedOn;

        public String getName() {
            return name;
        }

        public String getsClass() {
            return sClass;
        }

        public String getNote() {
            return note;
        }

        public String getModifiedBy() {
            return modifiedBy;
        }

        public String getModifiedOn() {
            return modifiedOn;
        }
    }
}
