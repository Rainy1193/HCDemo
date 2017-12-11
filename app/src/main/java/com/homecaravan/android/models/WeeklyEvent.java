package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAINY on 8/6/2016.
 */
public class WeeklyEvent {
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
    private boolean openSpanStartSu;
    private boolean openSpanEndSu;
    private boolean openSpanStartMo;
    private boolean openSpanEndMo;
    private boolean openSpanStartTu;
    private boolean openSpanEndTu;
    private boolean openSpanStartWe;
    private boolean openSpanEndWe;
    private boolean openSpanStartTh;
    private boolean openSpanEndTh;
    private boolean openSpanStartFr;
    private boolean openSpanEndFr;
    private boolean openSpanStartSa;
    private boolean openSpanEndSa;

    public boolean isOpenSpanStartSu() {
        return openSpanStartSu;
    }

    public void setOpenSpanStartSu(boolean openSpanStartSu) {
        this.openSpanStartSu = openSpanStartSu;
    }

    public boolean isOpenSpanEndSu() {
        return openSpanEndSu;
    }

    public void setOpenSpanEndSu(boolean openSpanEndSu) {
        this.openSpanEndSu = openSpanEndSu;
    }

    public boolean isOpenSpanStartMo() {
        return openSpanStartMo;
    }

    public void setOpenSpanStartMo(boolean openSpanStartMo) {
        this.openSpanStartMo = openSpanStartMo;
    }

    public boolean isOpenSpanEndMo() {
        return openSpanEndMo;
    }

    public void setOpenSpanEndMo(boolean openSpanEndMo) {
        this.openSpanEndMo = openSpanEndMo;
    }

    public boolean isOpenSpanStartTu() {
        return openSpanStartTu;
    }

    public void setOpenSpanStartTu(boolean openSpanStartTu) {
        this.openSpanStartTu = openSpanStartTu;
    }

    public boolean isOpenSpanEndTu() {
        return openSpanEndTu;
    }

    public void setOpenSpanEndTu(boolean openSpanEndTu) {
        this.openSpanEndTu = openSpanEndTu;
    }

    public boolean isOpenSpanStartWe() {
        return openSpanStartWe;
    }

    public void setOpenSpanStartWe(boolean openSpanStartWe) {
        this.openSpanStartWe = openSpanStartWe;
    }

    public boolean isOpenSpanEndWe() {
        return openSpanEndWe;
    }

    public void setOpenSpanEndWe(boolean openSpanEndWe) {
        this.openSpanEndWe = openSpanEndWe;
    }

    public boolean isOpenSpanStartTh() {
        return openSpanStartTh;
    }

    public void setOpenSpanStartTh(boolean openSpanStartTh) {
        this.openSpanStartTh = openSpanStartTh;
    }

    public boolean isOpenSpanEndTh() {
        return openSpanEndTh;
    }

    public void setOpenSpanEndTh(boolean openSpanEndTh) {
        this.openSpanEndTh = openSpanEndTh;
    }

    public boolean isOpenSpanStartFr() {
        return openSpanStartFr;
    }

    public void setOpenSpanStartFr(boolean openSpanStartFr) {
        this.openSpanStartFr = openSpanStartFr;
    }

    public boolean isOpenSpanEndFr() {
        return openSpanEndFr;
    }

    public void setOpenSpanEndFr(boolean openSpanEndFr) {
        this.openSpanEndFr = openSpanEndFr;
    }

    public boolean isOpenSpanStartSa() {
        return openSpanStartSa;
    }

    public void setOpenSpanStartSa(boolean openSpanStartSa) {
        this.openSpanStartSa = openSpanStartSa;
    }

    public boolean isOpenSpanEndSa() {
        return openSpanEndSa;
    }

    public void setOpenSpanEndSa(boolean openSpanEndSa) {
        this.openSpanEndSa = openSpanEndSa;
    }

    public String getId() {
        return id;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getOverlap() {
        return overlap;
    }

    public String getColor() {
        return color;
    }

    public String getAllDay() {
        return allDay;
    }

    public String getTitle() {
        return title;
    }

    public String getClassName() {
        return className;
    }

    public String getIndicator() {
        return indicator;
    }

    public String getIndicatorClass() {
        return indicatorClass;
    }

    public String getNoAgentIndicator() {
        return noAgentIndicator;
    }

    public String getTextColor() {
        return textColor;
    }

    public String getBadgeColor() {
        return badgeColor;
    }

    public String getStatus() {
        return status;
    }

    public String getApptCount() {
        return apptCount;
    }

    public String getAppointmentExist() {
        return appointmentExist;
    }

    public String getBookingExist() {
        return bookingExist;
    }

    public String getAppointmentIds() {
        return appointmentIds;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setOverlap(String overlap) {
        this.overlap = overlap;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setAllDay(String allDay) {
        this.allDay = allDay;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public void setIndicatorClass(String indicatorClass) {
        this.indicatorClass = indicatorClass;
    }

    public void setNoAgentIndicator(String noAgentIndicator) {
        this.noAgentIndicator = noAgentIndicator;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public void setBadgeColor(String badgeColor) {
        this.badgeColor = badgeColor;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setApptCount(String apptCount) {
        this.apptCount = apptCount;
    }

    public void setAppointmentExist(String appointmentExist) {
        this.appointmentExist = appointmentExist;
    }

    public void setBookingExist(String bookingExist) {
        this.bookingExist = bookingExist;
    }

    public void setAppointmentIds(String appointmentIds) {
        this.appointmentIds = appointmentIds;
    }

    @Override
    public String toString() {
        return "DaySlot{" +
                "id='" + id + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", overlap='" + overlap + '\'' +
                ", color='" + color + '\'' +
                ", allDay='" + allDay + '\'' +
                ", title='" + title + '\'' +
                ", className='" + className + '\'' +
                ", indicator='" + indicator + '\'' +
                ", indicatorClass='" + indicatorClass + '\'' +
                ", noAgentIndicator='" + noAgentIndicator + '\'' +
                ", textColor='" + textColor + '\'' +
                ", badgeColor='" + badgeColor + '\'' +
                ", status='" + status + '\'' +
                ", apptCount='" + apptCount + '\'' +
                ", appointmentExist='" + appointmentExist + '\'' +
                ", bookingExist='" + bookingExist + '\'' +
                ", appointmentIds='" + appointmentIds + '\'' +
                '}';
    }
}
