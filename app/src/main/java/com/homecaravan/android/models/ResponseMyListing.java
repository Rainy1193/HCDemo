package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RAINY on 5/18/2016.
 */
public class ResponseMyListing {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private DataMyListing data;
    @SerializedName("code")
    @Expose
    private int code;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataMyListing getData() {
        return data;
    }

    public void setData(DataMyListing data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public class DataMyListing {
        @SerializedName("total_rows")
        @Expose
        private String totalRow;
        @SerializedName("listing")
        @Expose
        private ArrayList<MyListing> arrMyListing;

        public String getTotalRow() {
            return totalRow;
        }

        public void setTotalRow(String totalRow) {
            this.totalRow = totalRow;
        }

        public ArrayList<MyListing> getArrMyListing() {
            return arrMyListing;
        }

        public void setArrMyListing(ArrayList<MyListing> arrMyListing) {
            this.arrMyListing = arrMyListing;
        }
    }

    public class MyListing extends MyListingBase {
        @SerializedName("ID")
        @Expose
        private String id;
        @SerializedName("LKEY")
        @Expose
        private String lkey;
        @SerializedName("USER_ID")
        @Expose
        private String userId;
        @SerializedName("STATUS")
        @Expose
        private String status;
        @SerializedName("AST")
        @Expose
        private String ast;
        @SerializedName("CREATED_DATETIME")
        @Expose
        private String createdTime;
        @SerializedName("CREATED_BY")
        @Expose
        private String createBy;
        @SerializedName("MODIFIED_DATETIME")
        @Expose
        private String modifiedTime;
        @SerializedName("MODIFIED_BY")
        @Expose
        private String modifiedBy;
        @SerializedName("LISTING_ID")
        @Expose
        private String listingId;
        @SerializedName("LISTING_TYPE")
        @Expose
        private String listingType;
        @SerializedName("SALE_TYPE")
        @Expose
        private String saleType;
        @SerializedName("PROPERTY_TYPE")
        @Expose
        private String propertyType;
        @SerializedName("ADDRESS_1")
        @Expose
        private String add1;
        @SerializedName("ADDRESS_2")
        @Expose
        private String add2;
        @SerializedName("CITY")
        @Expose
        private String city;
        @SerializedName("STATE")
        @Expose
        private String state;
        @SerializedName("ZIP")
        @Expose
        private String zip;
        @SerializedName("LONGITUDE")
        @Expose
        private String longitude;
        @SerializedName("LATITUDE")
        @Expose
        private String latitude;
        @SerializedName("TIMEZONE")
        @Expose
        private String timeZone;
        @SerializedName("BEDROOMS")
        @Expose
        private String bedRooms;
        @SerializedName("BATHROOMS")
        @Expose
        private String bathRooms;
        @SerializedName("LIVING_SQUARE_FEET")
        @Expose
        private String livingSquareFeet;
        @SerializedName("LOT_SIZE")
        @Expose
        private String lotSize;
        @SerializedName("MEASURE_UNIT")
        @Expose
        private String measureUnit;
        @SerializedName("POOL")
        @Expose
        private String pool;
        @SerializedName("GARAGE")
        @Expose
        private String garage;
        @SerializedName("CONTACT_NAME")
        @Expose
        private String contactName;
        @SerializedName("MOBILE_PHONE")
        @Expose
        private String mobilePhone;
        @SerializedName("EMAIL")
        @Expose
        private String email;
        @SerializedName("PHOTO")
        @Expose
        private String photo;
        @SerializedName("DESCRIPTION")
        @Expose
        private String description;
        @SerializedName("PRICE")
        @Expose
        private String price;
        @SerializedName("PRICE_UNIT")
        @Expose
        private String priceUnit;
        @SerializedName("YEAR_BUILT")
        @Expose
        private String yearBuilt;
        @SerializedName("FOLLOWUP_TYPE")
        @Expose
        private String followupType;
        @SerializedName("REMINDER_TYPE")
        @Expose
        private String reminderType;
        @SerializedName("DISPLAY_TYPE")
        @Expose
        private String displayType;
        @SerializedName("ROLE_ID")
        @Expose
        private String roleId;
        @SerializedName("ACTION_REQUIRED")
        @Expose
        private String actionRequired;
        @SerializedName("LISTING_THUMB")
        @Expose
        private String listingThumb;
        @SerializedName("TOTAL_APPROVAL")
        @Expose
        private String totalApproval;
        @SerializedName("NEXT_APPOINTMENT")
        @Expose
        private String nextAppointment;
        @SerializedName("NUMB_APPROVED")
        @Expose
        private String numbApproved;
        @SerializedName("LIKES")
        @Expose
        private String like;

        @SerializedName("DCP_ID")
        @Expose
        private String dcpId;
        @SerializedName("DCP_FULL_NAME")
        @Expose
        private String dcpFullName;
        @SerializedName("DCP_EMAIL")
        @Expose
        private String dcpEmail;
        @SerializedName("DCP_PHOTO")
        @Expose
        private String dcpPhoto;
        @SerializedName("DCP_PHONE")
        @Expose
        private String dcpPhone;
        @SerializedName("DCP_COMPANY_NAME")
        @Expose
        private String dcpCompanyName;
        @SerializedName("DCP_COMPANY_ADDRESS_1")
        @Expose
        private String dcpAdd1;
        @SerializedName("DCP_COMPANY_ADDRESS_2")
        @Expose
        private String dcpAdd2;
        @SerializedName("THUMB")
        @Expose
        private String thumb;
        @SerializedName("IMG_NAME")
        @Expose
        private String imageName;

        @SerializedName("IMAGES")
        @Expose
        private ArrayList<ImageListing> arrImageListing;

        @SerializedName("SETTING")
        @Expose
        private Setting setting;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLkey() {
            return lkey;
        }

        public void setLkey(String lkey) {
            this.lkey = lkey;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAst() {
            return ast;
        }

        public void setAst(String ast) {
            this.ast = ast;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getModifiedTime() {
            return modifiedTime;
        }

        public void setModifiedTime(String modifiedTime) {
            this.modifiedTime = modifiedTime;
        }

        public String getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public String getListingId() {
            return listingId;
        }

        public void setListingId(String listingId) {
            this.listingId = listingId;
        }

        public String getListingType() {
            return listingType;
        }

        public void setListingType(String listingType) {
            this.listingType = listingType;
        }

        public String getSaleType() {
            return saleType;
        }

        public void setSaleType(String saleType) {
            this.saleType = saleType;
        }

        public String getPropertyType() {
            return propertyType;
        }

        public void setPropertyType(String propertyType) {
            this.propertyType = propertyType;
        }

        public String getAdd1() {
            return add1;
        }

        public void setAdd1(String add1) {
            this.add1 = add1;
        }

        public String getAdd2() {
            return add2;
        }

        public void setAdd2(String add2) {
            this.add2 = add2;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        public String getBedRooms() {
            return bedRooms;
        }

        public void setBedRooms(String bedRooms) {
            this.bedRooms = bedRooms;
        }

        public String getBathRooms() {
            return bathRooms;
        }

        public void setBathRooms(String bathRooms) {
            this.bathRooms = bathRooms;
        }

        public String getLivingSquareFeet() {
            return livingSquareFeet;
        }

        public void setLivingSquareFeet(String livingSquareFeet) {
            this.livingSquareFeet = livingSquareFeet;
        }

        public String getLotSize() {
            return lotSize;
        }

        public void setLotSize(String lotSize) {
            this.lotSize = lotSize;
        }

        public String getMeasureUnit() {
            return measureUnit;
        }

        public void setMeasureUnit(String measureUnit) {
            this.measureUnit = measureUnit;
        }

        public String getPool() {
            return pool;
        }

        public void setPool(String pool) {
            this.pool = pool;
        }

        public String getGarage() {
            return garage;
        }

        public void setGarage(String garage) {
            this.garage = garage;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public String getDcpId() {
            return dcpId;
        }

        public String getDcpFullName() {
            return dcpFullName;
        }

        public String getDcpEmail() {
            return dcpEmail;
        }

        public String getDcpPhoto() {
            return dcpPhoto;
        }

        public String getDcpPhone() {
            return dcpPhone;
        }

        public String getDcpCompanyName() {
            return dcpCompanyName;
        }

        public String getDcpAdd1() {
            return dcpAdd1;
        }

        public String getDcpAdd2() {
            return dcpAdd2;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPriceUnit() {
            return priceUnit;
        }

        public void setPriceUnit(String priceUnit) {
            this.priceUnit = priceUnit;
        }

        public String getYearBuilt() {
            return yearBuilt;
        }

        public void setYearBuilt(String yearBuilt) {
            this.yearBuilt = yearBuilt;
        }

        public String getFollowupType() {
            return followupType;
        }

        public void setFollowupType(String followupType) {
            this.followupType = followupType;
        }

        public String getReminderType() {
            return reminderType;
        }

        public void setReminderType(String reminderType) {
            this.reminderType = reminderType;
        }

        public String getDisplayType() {
            return displayType;
        }

        public void setDisplayType(String displayName) {
            this.displayType = displayName;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public String getActionRequired() {
            return actionRequired;
        }

        public void setActionRequired(String actionRequired) {
            this.actionRequired = actionRequired;
        }

        public String getListingThumb() {
            return listingThumb;
        }

        public void setListingThumb(String listingThumb) {
            this.listingThumb = listingThumb;
        }

        public ArrayList<ImageListing> getArrImageListing() {
            return arrImageListing;
        }

        public String getTotalApproval() {
            return totalApproval;
        }

        public void setTotalApproval(String totalApproval) {
            this.totalApproval = totalApproval;
        }

        public String getNextAppointment() {
            return nextAppointment;
        }

        public void setNextAppointment(String nextAppointment) {
            this.nextAppointment = nextAppointment;
        }

        public String getNumbApproved() {
            return numbApproved;
        }

        public void setNumbApproved(String numbApproved) {
            this.numbApproved = numbApproved;
        }

        public String getLike() {
            return like;
        }

        public void setLike(String like) {
            this.like = like;
        }

        public String getThumb() {
            return thumb;
        }

        public String getImageName() {
            return imageName;
        }

        public Setting getSetting() {
            return setting;
        }
    }

    public class Setting {
        @SerializedName("ID")
        @Expose
        private String id;
        @SerializedName("AVAIL_TEMPLATE_ID")
        @Expose
        private String availTemplateId;
        @SerializedName("DISPLAY_TIME_TYPE")
        @Expose
        private String displayTimeType;
        @SerializedName("SCHEDULER_START_DATETIME")
        @Expose
        private String schedulerStartDateTime;
        @SerializedName("SCHEDULER_END_DATETIME")
        @Expose
        private String schedulerEndDateTime;
        @SerializedName("APPOINTMENT_UNAVAILABLE")
        @Expose
        private String appointmentUnavailable;
        @SerializedName("BOOK_THROUGH")
        @Expose
        private String bookThrough;
        @SerializedName("CONCURRENT_APPOINTMENT")
        @Expose
        private String concurrentAppointment;
        @SerializedName("TIME_FORMAT")
        @Expose
        private String timeFormat;
        @SerializedName("APPT_MAX_PENDING_APPOINTMENT")
        @Expose
        private String apptMaxPendingAppointment;
        @SerializedName("FUTURE_AVAILABILITY_DAY")
        @Expose
        private String futureAvailabitityDay;
        @SerializedName("REQUIRED_NOTICE_HOUR")
        @Expose
        private String requiredNoticeHour;
        @SerializedName("AUTOMATIC_APPOINTMENT")
        @Expose
        private String automaticAppointment;
        @SerializedName("ADMIN_CONFIRMED_REQUIRED")
        @Expose
        private String adminConfirmedRequired;
        @SerializedName("EXPORT_CALENDAR")
        @Expose
        private String exportCalendar;
        @SerializedName("FORCE_USER_CONFIRMATION")
        @Expose
        private String forceUserConfirmation;
        @SerializedName("AUTOMATIC_FOLLOW_UP")
        @Expose
        private String automaticFollowUp;
        @SerializedName("DEFAULT_AVAILABILITY")
        @Expose
        private String defaultAvailability;
        @SerializedName("PROPERTY_TYPE")
        @Expose
        private String propertyType;
        @SerializedName("CREATED_DATETIME")
        @Expose
        private String createDateTime;
        @SerializedName("CREATED_BY")
        @Expose
        private String createBy;
        @SerializedName("MODIFIED_DATETIME")
        @Expose
        private String modifiedDatetime;
        @SerializedName("MODIFIED_BY")
        @Expose
        private String modifiedBy;
        @SerializedName("LISTING_ID")
        @Expose
        private String listingId;
        @SerializedName("SCHEDULER_START_TIME")
        @Expose
        private ListingDetailDataListing.SchedulerStartTime schedulerStartTime;
        @SerializedName("SCHEDULER_END_TIME")
        @Expose
        private ListingDetailDataListing.SchedulerEndTime schedulerEndTime;
        @SerializedName("APPOINTMENT_DURATION")
        @Expose
        private ListingDetailDataListing.AppointmentDuration appointmentDuration;
        @SerializedName("USER_CONFIRMATION_DEADLINE_HOUR")
        @Expose
        private ListingDetailDataListing.UserConfirmationDeadlineHour userConfirmationDeadlineHour;
        @SerializedName("DAY_AVAILABLE")
        @Expose
        private String dayAvailable;

        public String getAdminConfirmedRequired() {
            return adminConfirmedRequired;
        }

        public ListingDetailDataListing.AppointmentDuration getAppointmentDuration() {
            return appointmentDuration;
        }

        public String getAppointmentUnavailable() {
            return appointmentUnavailable;
        }

        public String getApptMaxPendingAppointment() {
            return apptMaxPendingAppointment;
        }

        public String getAutomaticAppointment() {
            return automaticAppointment;
        }

        public String getAutomaticFollowUp() {
            return automaticFollowUp;
        }

        public String getAvailTemplateId() {
            return availTemplateId;
        }

        public String getBookThrough() {
            return bookThrough;
        }

        public String getConcurrentAppointment() {
            return concurrentAppointment;
        }

        public String getCreateBy() {
            return createBy;
        }

        public String getCreateDateTime() {
            return createDateTime;
        }

        public String getDayAvailable() {
            return dayAvailable;
        }

        public String getDefaultAvailability() {
            return defaultAvailability;
        }

        public String getDisplayTimeType() {
            return displayTimeType;
        }

        public String getExportCalendar() {
            return exportCalendar;
        }

        public String getForceUserConfirmation() {
            return forceUserConfirmation;
        }

        public String getFutureAvailabitityDay() {
            return futureAvailabitityDay;
        }

        public String getId() {
            return id;
        }

        public String getListingId() {
            return listingId;
        }

        public String getModifiedBy() {
            return modifiedBy;
        }

        public String getModifiedDatetime() {
            return modifiedDatetime;
        }

        public String getPropertyType() {
            return propertyType;
        }

        public String getRequiredNoticeHour() {
            return requiredNoticeHour;
        }

        public String getSchedulerEndDateTime() {
            return schedulerEndDateTime;
        }

        public ListingDetailDataListing.SchedulerEndTime getSchedulerEndTime() {
            return schedulerEndTime;
        }

        public String getSchedulerStartDateTime() {
            return schedulerStartDateTime;
        }

        public ListingDetailDataListing.SchedulerStartTime getSchedulerStartTime() {
            return schedulerStartTime;
        }

        public String getTimeFormat() {
            return timeFormat;
        }

        public ListingDetailDataListing.UserConfirmationDeadlineHour getUserConfirmationDeadlineHour() {
            return userConfirmationDeadlineHour;
        }
    }
}
