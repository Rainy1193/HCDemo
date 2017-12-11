package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CaravanJson {
    @Expose
    @SerializedName("time_from")
    private String timeFrom;
    @Expose
    @SerializedName("time_to")
    private String timeTo;
    @Expose
    @SerializedName("duration")
    private int duration;
    @Expose
    @SerializedName("drive")
    private CaravanJsonDrive drive;
    @Expose
    @SerializedName("park")
    private Object park;
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("address")
    private String address;
    @Expose
    @SerializedName("short_address")
    private String short_address;
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("sid")
    private int sid;
    @Expose
    @SerializedName("position")
    private int position;
    @Expose
    @SerializedName("lat")
    private String lat;
    @Expose
    @SerializedName("lng")
    private String lng;
    @Expose
    @SerializedName("delete")
    private boolean delete;
    @Expose
    @SerializedName("manualAst")
    private boolean manualAst;
    @Expose
    @SerializedName("autoAst")
    private boolean autoAst;
    @Expose
    @SerializedName("photo")
    private String photo;
    @Expose
    @SerializedName("free_time_before")
    private int freeTimeBefore;
    @Expose
    @SerializedName("free_time_after")
    private int freeTimeAfter;
    @Expose
    @SerializedName("fixed_time_from")
    private String fixedTimeFrom;
    @Expose
    @SerializedName("alternate_time")
    private String alternateTime;
    @Expose
    @SerializedName("lock")
    private boolean lock;
    @Expose
    @SerializedName("note")
    private String note;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("copy")
    private String copy;
    @Expose
    @SerializedName("rsd_realtor_list")
    private String rsdRealtorList;
    @Expose
    @SerializedName("appointment")
    private Object appointment;
    @Expose
    @SerializedName("action")
    private Object action;
    @Expose
    @SerializedName("tpl")
    private Object tpl;
    @Expose
    @SerializedName("listing")
    private ListingCaravan listing;

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public CaravanJsonDrive getDrive() {
        return drive;
    }

    public void setDrive(CaravanJsonDrive drive) {
        this.drive = drive;
    }

    public Object getPark() {
        return park;
    }

    public void setPark(Object park) {
        this.park = park;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShort_address() {
        return short_address;
    }

    public void setShort_address(String short_address) {
        this.short_address = short_address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public boolean getDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean getManualAst() {
        return manualAst;
    }

    public void setManualAst(boolean manualAst) {
        this.manualAst = manualAst;
    }

    public boolean getAutoAst() {
        return autoAst;
    }

    public void setAutoAst(boolean autoAst) {
        this.autoAst = autoAst;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getFreeTimeBefore() {
        return freeTimeBefore;
    }

    public void setFreeTimeBefore(int freeTimeBefore) {
        this.freeTimeBefore = freeTimeBefore;
    }

    public int getFreeTimeAfter() {
        return freeTimeAfter;
    }

    public void setFreeTimeAfter(int freeTimeAfter) {
        this.freeTimeAfter = freeTimeAfter;
    }

    public String getFixedTimeFrom() {
        return fixedTimeFrom;
    }

    public void setFixedTimeFrom(String fixedTimeFrom) {
        this.fixedTimeFrom = fixedTimeFrom;
    }

    public String getAlternateTime() {
        return alternateTime;
    }

    public void setAlternateTime(String alternateTime) {
        this.alternateTime = alternateTime;
    }

    public boolean getLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCopy() {
        return copy;
    }

    public void setCopy(String copy) {
        this.copy = copy;
    }

    public String getRsdRealtorList() {
        return rsdRealtorList;
    }

    public void setRsdRealtorList(String rsdRealtorList) {
        this.rsdRealtorList = rsdRealtorList;
    }

    public Object getAppointment() {
        return appointment;
    }

    public void setAppointment(Object appointment) {
        this.appointment = appointment;
    }

    public Object getAction() {
        return action;
    }

    public void setAction(Object action) {
        this.action = action;
    }

    public Object getTpl() {
        return tpl;
    }

    public void setTpl(Object tpl) {
        this.tpl = tpl;
    }

    public ListingCaravan getListing() {
        return listing;
    }

    public void setListing(ListingCaravan listing) {
        this.listing = listing;
    }

    @Override
    public String toString() {
        return "CaravanJson{" +
                "timeFrom='" + timeFrom + '\'' +
                ", timeTo='" + timeTo + '\'' +
                ", duration='" + duration + '\'' +
                ", drive=" + drive +
                ", park=" + park +
                ", type='" + type + '\'' +
                ", address='" + address + '\'' +
                ", short_address='" + short_address + '\'' +
                ", id='" + id + '\'' +
                ", sid='" + sid + '\'' +
                ", position='" + position + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", delete='" + delete + '\'' +
                ", manualAst='" + manualAst + '\'' +
                ", autoAst='" + autoAst + '\'' +
                ", photo='" + photo + '\'' +
                ", freeTimeBefore='" + freeTimeBefore + '\'' +
                ", freeTimeAfter='" + freeTimeAfter + '\'' +
                ", fixedTimeFrom='" + fixedTimeFrom + '\'' +
                ", alternateTime='" + alternateTime + '\'' +
                ", lock='" + lock + '\'' +
                ", note='" + note + '\'' +
                ", description='" + description + '\'' +
                ", copy='" + copy + '\'' +
                ", rsdRealtorList='" + rsdRealtorList + '\'' +
                ", appointment=" + appointment +
                ", action=" + action +
                ", tpl=" + tpl +
                ", listing=" + listing +
                '}';
    }

    public class CaravanJsonDrive {
        @Expose
        @SerializedName("time_from")
        private String timeFrom;
        @Expose
        @SerializedName("time_to")
        private String timeTo;
        @Expose
        @SerializedName("duration")
        private int duration;

        public String getTimeFrom() {
            return timeFrom;
        }

        public void setTimeFrom(String timeFrom) {
            this.timeFrom = timeFrom;
        }

        public String getTimeTo() {
            return timeTo;
        }

        public void setTimeTo(String timeTo) {
            this.timeTo = timeTo;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        @Override
        public String toString() {
            return "CaravanJsonDrive{" +
                    "timeFrom='" + timeFrom + '\'' +
                    ", timeTo='" + timeTo + '\'' +
                    ", duration='" + duration + '\'' +
                    '}';
        }
    }
}

