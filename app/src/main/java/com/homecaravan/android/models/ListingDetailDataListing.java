package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RAINY on 5/3/2016.
 */
public class ListingDetailDataListing {
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
    private String displayName;
    @SerializedName("NEXT_OPEN_HOUSE")
    @Expose
    private NextOpenHouse nextOpenHouse;
    @SerializedName("FILE_LOCATION")
    @Expose
    private String fileLocation;
    @SerializedName("IN_FAVORITE")
    @Expose
    private String inFavorite;
    @SerializedName("THUMB")
    @Expose
    private String thumb;
    @SerializedName("IMG_NAME")
    @Expose
    private String imgName;
    @SerializedName("STATUS_COMMENT")
    @Expose
    private String statusComment;
    @SerializedName("META_KEYWORDS")
    @Expose
    private String metaKeywords;
    @SerializedName("META_DESC")
    @Expose
    private String metaDesc;
    @SerializedName("IMAGES")
    @Expose
    private ArrayList<ImageListing> arrImageListing;
    @SerializedName("MAP_ADDRESS")
    @Expose
    private String mapAddress;
    @SerializedName("LASTEST_STATUS")
    @Expose
    private String lastestStatus;
    @SerializedName("SETTING")
    @Expose
    private Setting setting;
//    @SerializedName("LISTING_MESSAGES")
//    @Expose
//    private ListingMessages listingMessages;
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


    public String getAdd1() {
        return add1;
    }

    public String getAdd2() {
        return add2;
    }

    public ArrayList<ImageListing> getArrImageListing() {
        return arrImageListing;
    }

    public String getAst() {
        return ast;
    }

    public String getBathRooms() {
        return bathRooms;
    }

    public String getBedRooms() {
        return bedRooms;
    }

    public String getCity() {
        return city;
    }

    public String getContactName() {
        return contactName;
    }

    public String getCreateBy() {
        return createBy;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public String getFollowupType() {
        return followupType;
    }

    public String getGarage() {
        return garage;
    }

    public String getId() {
        return id;
    }

    public String getImgName() {
        return imgName;
    }

    public String getInFavorite() {
        return inFavorite;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getListingId() {
        return listingId;
    }

//    public ListingMessages getListingMessages() {
//        return listingMessages;
//    }

    public String getListingType() {
        return listingType;
    }

    public String getLivingSquareFeet() {
        return livingSquareFeet;
    }

    public String getLkey() {
        return lkey;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLotSize() {
        return lotSize;
    }

    public String getMapAddress() {
        return mapAddress;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public String getMetaDesc() {
        return metaDesc;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public NextOpenHouse getNextOpenHouse() {
        return nextOpenHouse;
    }

    public String getPhoto() {
        return photo;
    }

    public String getLastestStatus() {
        return lastestStatus;
    }

    public String getPool() {
        return pool;
    }

    public String getPrice() {
        return price;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public String getReminderType() {
        return reminderType;
    }

    public String getSaleType() {
        return saleType;
    }

    public Setting getSetting() {
        return setting;
    }

    public String getState() {
        return state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getDcpCompanyName() {
        return dcpCompanyName;
    }

    public String getDcpAdd1() {
        return dcpAdd1;
    }

    public String getDcpAdd2() {
        return dcpAdd2;
    }

    public String getStatusComment() {
        return statusComment;
    }

    public String getDcpPhone() {
        return dcpPhone;
    }

    public String getThumb() {
        return thumb;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getUserId() {
        return userId;
    }

    public String getYearBuilt() {
        return yearBuilt;
    }

    public String getZip() {
        return zip;
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
        private SchedulerStartTime schedulerStartTime;
        @SerializedName("SCHEDULER_END_TIME")
        @Expose
        private SchedulerEndTime schedulerEndTime;
        @SerializedName("APPOINTMENT_DURATION")
        @Expose
        private AppointmentDuration appointmentDuration;
        @SerializedName("USER_CONFIRMATION_DEADLINE_HOUR")
        @Expose
        private UserConfirmationDeadlineHour userConfirmationDeadlineHour;
        @SerializedName("DAY_AVAILABLE")
        @Expose
        private String dayAvailable;

        public String getAdminConfirmedRequired() {
            return adminConfirmedRequired;
        }

        public AppointmentDuration getAppointmentDuration() {
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

        public SchedulerEndTime getSchedulerEndTime() {
            return schedulerEndTime;
        }

        public String getSchedulerStartDateTime() {
            return schedulerStartDateTime;
        }

        public SchedulerStartTime getSchedulerStartTime() {
            return schedulerStartTime;
        }

        public String getTimeFormat() {
            return timeFormat;
        }

        public UserConfirmationDeadlineHour getUserConfirmationDeadlineHour() {
            return userConfirmationDeadlineHour;
        }


    }

    public class SchedulerStartTime {
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("timezone_type")
        @Expose
        private String timezoneType;
        @SerializedName("timezone")
        @Expose
        private String timeZone;

        public String getDate() {
            return date;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public String getTimezoneType() {
            return timezoneType;
        }
    }

    public class NextOpenHouse {
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("timezone_type")
        @Expose
        private String timezoneType;
        @SerializedName("timezone")
        @Expose
        private String timeZone;

        public String getDate() {
            return date;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public String getTimezoneType() {
            return timezoneType;
        }
    }

    public class SchedulerEndTime {
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("timezone_type")
        @Expose
        private String timezoneType;
        @SerializedName("timezone")
        @Expose
        private String timeZone;

        public String getDate() {
            return date;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public String getTimezoneType() {
            return timezoneType;
        }
    }

    public class AppointmentDuration {
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("timezone_type")
        @Expose
        private String timezoneType;
        @SerializedName("timezone")
        @Expose
        private String timeZone;

        public String getDate() {
            return date;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public String getTimezoneType() {
            return timezoneType;
        }
    }

    public class UserConfirmationDeadlineHour {
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("timezone_type")
        @Expose
        private String timezoneType;
        @SerializedName("timezone")
        @Expose
        private String timeZone;

        public String getDate() {
            return date;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public String getTimezoneType() {
            return timezoneType;
        }
    }

    public class DayAvailable {
        @SerializedName("Sun")
        @Expose
        private String sun;
        @SerializedName("Mon")
        @Expose
        private String mon;
        @SerializedName("Tue")
        @Expose
        private String tue;
        @SerializedName("Wed")
        @Expose
        private String wed;
        @SerializedName("Thu")
        @Expose
        private String thu;
        @SerializedName("Fri")
        @Expose
        private String fri;
        @SerializedName("Sat")
        @Expose
        private String sat;

        public String getFri() {
            return fri;
        }

        public String getMon() {
            return mon;
        }

        public String getSat() {
            return sat;
        }

        public String getSun() {
            return sun;
        }

        public String getThu() {
            return thu;
        }

        public String getTue() {
            return tue;
        }

        public String getWed() {
            return wed;
        }
    }

    public class DayAvailability {
        @SerializedName("Sun")
        @Expose
        private String Sun;
        @SerializedName("Mon")
        @Expose
        private String Mon;
        @SerializedName("Tue")
        @Expose
        private String Tue;
        @SerializedName("Wed")
        @Expose
        private String Wed;
        @SerializedName("Thu")
        @Expose
        private String Thu;
        @SerializedName("Fri")
        @Expose
        private String Fri;
        @SerializedName("Sat")
        @Expose
        private String Sat;

        public String getSun() {
            return Sun;
        }

        public String getMon() {
            return Mon;
        }

        public String getTue() {
            return Tue;
        }

        public String getWed() {
            return Wed;
        }

        public String getThu() {
            return Thu;
        }

        public String getFri() {
            return Fri;
        }

        public String getSat() {
            return Sat;
        }
    }

    public class ListingMessages {
        @SerializedName("APPT_CONFIRMATION")
        @Expose
        private String apptConfirmation;
        @SerializedName("PRO_FEEDBACK")
        @Expose
        private String proFeedback;
        @SerializedName("ORD_FEEDBACK")
        @Expose
        private String ordFeedback;
        @SerializedName("PRO_REMINDER")
        @Expose
        private String proReminder;
        @SerializedName("ORD_REMINDER")
        @Expose
        private String ordReminder;

        @SerializedName("APPT_CONFIRMATION_STATUS")
        @Expose
        private String apptConfirmationstatus;
        @SerializedName("PRO_FEEDBACK_STATUS")
        @Expose
        private String proFeedbackstatus;
        @SerializedName("ORD_FEEDBACK_STATUS")
        @Expose
        private String ordFeedbackstatus;
        @SerializedName("PRO_REMINDER_STATUS")
        @Expose
        private String proReminderstatus;
        @SerializedName("ORD_REMINDER_STATUS")
        @Expose
        private String ordReminderstatus;

        public String getApptConfirmationstatus() {
            return apptConfirmationstatus;
        }

        public String getProFeedbackstatus() {
            return proFeedbackstatus;
        }

        public String getOrdFeedbackstatus() {
            return ordFeedbackstatus;
        }

        public String getProReminderstatus() {
            return proReminderstatus;
        }

        public String getOrdReminderstatus() {
            return ordReminderstatus;
        }

        public String getApptConfirmation() {
            return apptConfirmation;
        }

        public String getOrdFeedback() {
            return ordFeedback;
        }

        public String getOrdReminder() {
            return ordReminder;
        }

        public String getProFeedback() {
            return proFeedback;
        }

        public String getProReminder() {
            return proReminder;
        }
    }

}
