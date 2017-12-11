package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RAINY on 8/5/2016.
 */
public class ResponseGetDayEvent {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private DataDayEvent data;
    @SerializedName("code")
    @Expose
    private String code;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public DataDayEvent getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public class DataDayEvent {
        @SerializedName("dayslot")
        @Expose
        private ArrayList<DayEvent> dayEvents;

        public ArrayList<DayEvent> getDaySlots() {
            return dayEvents;
        }

    }
    public class DayEvent {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("start")
        @Expose
        private String start;
        @SerializedName("end")
        @Expose
        private String end;
        @SerializedName("overlap")
        @Expose
        private String overlap;
        @SerializedName("color")
        @Expose
        private String color;
        @SerializedName("allDay")
        @Expose
        private String allDay;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("className")
        @Expose
        private String className;
        @SerializedName("indicator")
        @Expose
        private String indicator;
        @SerializedName("indicatorClass")
        @Expose
        private String indicatorClass;
        @SerializedName("noAgentIndicator")
        @Expose
        private String noAgentIndicator;
        @SerializedName("textColor")
        @Expose
        private String textColor;
        @SerializedName("badgeColor")
        @Expose
        private String badgeColor;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("apptCount")
        @Expose
        private String apptCount;
        @SerializedName("appointmentExist")
        @Expose
        private String appointmentExist;
        @SerializedName("bookingExist")
        @Expose
        private String bookingExist;
        @SerializedName("appointmentIds")
        @Expose
        private String appointmentIds;

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

        public String getOverlap() {
            return overlap;
        }

        public void setOverlap(String overlap) {
            this.overlap = overlap;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getAllDay() {
            return allDay;
        }

        public void setAllDay(String allDay) {
            this.allDay = allDay;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getIndicator() {
            return indicator;
        }

        public void setIndicator(String indicator) {
            this.indicator = indicator;
        }

        public String getIndicatorClass() {
            return indicatorClass;
        }

        public void setIndicatorClass(String indicatorClass) {
            this.indicatorClass = indicatorClass;
        }

        public String getNoAgentIndicator() {
            return noAgentIndicator;
        }

        public void setNoAgentIndicator(String noAgentIndicator) {
            this.noAgentIndicator = noAgentIndicator;
        }

        public String getTextColor() {
            return textColor;
        }

        public void setTextColor(String textColor) {
            this.textColor = textColor;
        }

        public String getBadgeColor() {
            return badgeColor;
        }

        public void setBadgeColor(String badgeColor) {
            this.badgeColor = badgeColor;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getApptCount() {
            return apptCount;
        }

        public void setApptCount(String apptCount) {
            this.apptCount = apptCount;
        }

        public String getAppointmentExist() {
            return appointmentExist;
        }

        public void setAppointmentExist(String appointmentExist) {
            this.appointmentExist = appointmentExist;
        }

        public String getBookingExist() {
            return bookingExist;
        }

        public void setBookingExist(String bookingExist) {
            this.bookingExist = bookingExist;
        }

        public String getAppointmentIds() {
            return appointmentIds;
        }

        public void setAppointmentIds(String appointmentIds) {
            this.appointmentIds = appointmentIds;
        }
    }
}
