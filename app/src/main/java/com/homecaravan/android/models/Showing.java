package com.homecaravan.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RAINY on 5/11/2016.
 */
public class Showing extends ShowingBase {
    private boolean isOpen;

    @SerializedName("ID")
    @Expose
    private String idAppointment;
    @SerializedName("LISTING_ID")
    @Expose
    private String listingId;
    @SerializedName("TIMESLOT_TYPE")
    @Expose
    private String timeSlotType;
    @SerializedName("STATUS")
    @Expose
    private String status;
    @SerializedName("SA_APPROVAL_REQUIRED")
    @Expose
    private String saApprovalRequired;
    @SerializedName("ADMIN_APPROVAL_REQUIRED")
    @Expose
    private String adminApprovalRequired;
    @SerializedName("SA_ACTION")
    @Expose
    private String saAction;
    @SerializedName("ADMIN_ACTION")
    @Expose
    private String adminAction;
    @SerializedName("USER_ACTION")
    @Expose
    private String userAction;
    @SerializedName("INACTIVE_STATUS")
    @Expose
    private String inactiveStatus;
    @SerializedName("USER_ID")
    @Expose
    private String userId;
    @SerializedName("CLIENT_ID")
    @Expose
    private String clientId;
    @SerializedName("TIME_FROM")
    @Expose
    private String timeFrom;
    @SerializedName("TIME_TO")
    @Expose
    private String timeTo;
    @SerializedName("APPT_NOTE")
    @Expose
    private String apptNote;
    @SerializedName("WITHOUT_AGENT")
    @Expose
    private String withoutAgent;
    @SerializedName("COMMUNICATION_TYPE")
    @Expose
    private String communticationType;
    @SerializedName("APPOINTMENT_TYPE")
    @Expose
    private String appointmentType;
    @SerializedName("SUPER_ADMIN_APPROVE")
    @Expose
    private String superAdminApprove;
    @SerializedName("SEND_FEEDBACK")
    @Expose
    private String sendFeedBack;
    @SerializedName("SEND_REMINDER")
    @Expose
    private String sendReminder;
    @SerializedName("STATUS_REASON")
    @Expose
    private String statusReason;
    @SerializedName("AUTHENTICATION_KEY")
    @Expose
    private String authenticatiionKey;
    @SerializedName("CALENDAR_UID")
    @Expose
    private String calendarUid;
    @SerializedName("CALENDAR_UPDATE_SEQUENCE")
    @Expose
    private String calendarUpdateSequence;
    @SerializedName("CREATED_DATETIME")
    @Expose
    private String createdDateTime;
    @SerializedName("CREATED_BY")
    @Expose
    private String createdBy;
    @SerializedName("MODIFIED_DATETIME")
    @Expose
    private String modifiedDateTime;
    @SerializedName("MODIFIED_BY")
    @Expose
    private String modifiedBy;
    @SerializedName("ZIP")
    @Expose
    private String zip;
    @SerializedName("STATE")
    @Expose
    private String state;
    @SerializedName("CITY")
    @Expose
    private String city;
    @SerializedName("ADDRESS_1")
    @Expose
    private String add1;
    @SerializedName("ADDRESS_2")
    @Expose
    private String add2;
    @SerializedName("DESCRIPTION")
    @Expose
    private String description;
    @SerializedName("FIRST_NAME")
    @Expose
    private String firstName;
    @SerializedName("LAST_NAME")
    @Expose
    private String lastName;
    @SerializedName("EMAIL")
    @Expose
    private String email;
    @SerializedName("PHONE")
    @Expose
    private String phone;
    @SerializedName("AGENT_ID")
    @Expose
    private String agentId;
    @SerializedName("AGENT_NAME")
    @Expose
    private String agentName;
    @SerializedName("AGENT_FIRST_NAME")
    @Expose
    private String agentFirstName;
    @SerializedName("AGENT_LAST_NAME")
    @Expose
    private String agentLastName;
    @SerializedName("AGENT_EMAIL")
    @Expose
    private String agentEmail;
    @SerializedName("AGENT_PHONE")
    @Expose
    private String agentPhone;
    @SerializedName("BOOK_BY_ID")
    @Expose
    private String bookById;
    @SerializedName("BOOK_BY_NAME")
    @Expose
    private String bookByName;
    @SerializedName("BOOK_BY_FIRST_NAME")
    @Expose
    private String bookByFirstName;
    @SerializedName("BOOK_BY_LAST_NAME")
    @Expose
    private String bookByLastName;
    @SerializedName("BOOK_BY_EMAIL")
    @Expose
    private String bookByEmail;
    @SerializedName("BOOK_BY_PHONE")
    @Expose
    private String bookByPhone;
    @SerializedName("ACTION_BY")
    @Expose
    private String actionBy;
    @SerializedName("NEW_MESSAGE_COUNT")
    @Expose
    private String newMessageCount;
    @SerializedName("MESSAGE_COUNT")
    @Expose
    private String messageCount;
    @SerializedName("NEW_MESSAGE_COUNT_CLIENT")
    @Expose
    private String newMessageCountCilent;
    @SerializedName("MESSAGE_COUNT_CLIENT")
    @Expose
    private String messageCountCilent;
    @SerializedName("FILE_LOCATION")
    @Expose
    private String fileLocation;
    @SerializedName("LISTING_AGENT")
    @Expose
    private String listingAgent;
    @SerializedName("AGENT_PHOTO")
    @Expose
    private String agentPhoto;
    @SerializedName("NOTE_TOTAL")
    @Expose
    private String noteTotal;
    @SerializedName("SHOWING_INSTRUCTIONS")
    @Expose
    private String instructions;
    @SerializedName("AGENT_COMPANY_TITLE")
    @Expose
    private String agentCompany;

    @SerializedName("ACTION_STATUS")
    @Expose
    private ActionStatus actionStatus;
    @SerializedName("IS_MYLISTING")
    @Expose
    private String isMylisting;
    @SerializedName("LISTING")
    @Expose
    private ListingShowing listing;



    @SerializedName("AST")
    @Expose
    private String ast;

    @SerializedName("AST_DATA")
    @Expose
    private AstData astData;

    public String getAst() {
        return ast;
    }

    public void setAst(String ast) {
        this.ast = ast;
    }

    public AstData getAstData() {
        return astData;
    }

    public void setAstData(AstData astData) {
        this.astData = astData;
    }

    public class AstData{
        @SerializedName("SUGGESTION_ID")
        @Expose
        private String suggestionId;

        @SerializedName("USER_STATUS")
        @Expose
        private String userStatus;
        @SerializedName("STATUS_ALOW")
        @Expose
        private String statusAlow;
        @SerializedName("HAS_AGENT")
        @Expose
        private String hasAgent;
        @SerializedName("SUGGESTION_STATUS")
        @Expose
        private String suggestionStatus;

        public String getHasAgent() {
            return hasAgent;
        }

        public void setHasAgent(String hasAgent) {
            this.hasAgent = hasAgent;
        }

        public String getSuggestionStatus() {
            return suggestionStatus;
        }

        public void setSuggestionStatus(String suggestionStatus) {
            this.suggestionStatus = suggestionStatus;
        }

        public String getSuggestionId() {
            return suggestionId;
        }

        public void setSuggestionId(String suggestionId) {
            this.suggestionId = suggestionId;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

        public String getStatusAlow() {
            return statusAlow;
        }

        public void setStatusAlow(String statusAlow) {
            this.statusAlow = statusAlow;
        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getNoteTotal() {
        return noteTotal;
    }

    public void setNoteTotal(String noteTotal) {
        this.noteTotal = noteTotal;
    }

    public String getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(String idAppointment) {
        this.idAppointment = idAppointment;
    }

    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public String getTimeSlotType() {
        return timeSlotType;
    }

    public void setTimeSlotType(String timeSlotType) {
        this.timeSlotType = timeSlotType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSaApprovalRequired() {
        return saApprovalRequired;
    }

    public void setSaApprovalRequired(String saApprovalRequired) {
        this.saApprovalRequired = saApprovalRequired;
    }

    public String getAdminApprovalRequired() {
        return adminApprovalRequired;
    }

    public void setAdminApprovalRequired(String adminApprovalRequired) {
        this.adminApprovalRequired = adminApprovalRequired;
    }

    public ActionStatus getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(ActionStatus actionStatus) {
        this.actionStatus = actionStatus;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getAgentPhoto() {
        return agentPhoto;
    }

    public void setAgentPhoto(String agentPhoto) {
        this.agentPhoto = agentPhoto;
    }

    public String getSaAction() {
        return saAction;
    }

    public void setSaAction(String saAction) {
        this.saAction = saAction;
    }

    public String getAdminAction() {
        return adminAction;
    }

    public void setAdminAction(String adminAction) {
        this.adminAction = adminAction;
    }

    public String getUserAction() {
        return userAction;
    }

    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }

    public String getInactiveStatus() {
        return inactiveStatus;
    }

    public void setInactiveStatus(String inactiveStatus) {
        this.inactiveStatus = inactiveStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

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

    public String getApptNote() {
        return apptNote;
    }

    public void setApptNote(String apptNote) {
        this.apptNote = apptNote;
    }

    public String getWithoutAgent() {
        return withoutAgent;
    }

    public void setWithoutAgent(String withoutAgent) {
        this.withoutAgent = withoutAgent;
    }

    public String getCommunticationType() {
        return communticationType;
    }

    public void setCommunticationType(String communticationType) {
        this.communticationType = communticationType;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getSuperAdminApprove() {
        return superAdminApprove;
    }

    public void setSuperAdminApprove(String superAdminApprove) {
        this.superAdminApprove = superAdminApprove;
    }

    public String getSendFeedBack() {
        return sendFeedBack;
    }

    public void setSendFeedBack(String sendFeedBack) {
        this.sendFeedBack = sendFeedBack;
    }

    public String getSendReminder() {
        return sendReminder;
    }

    public void setSendReminder(String sendReminder) {
        this.sendReminder = sendReminder;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public String getAuthenticatiionKey() {
        return authenticatiionKey;
    }

    public void setAuthenticatiionKey(String authenticatiionKey) {
        this.authenticatiionKey = authenticatiionKey;
    }

    public String getCalendarUid() {
        return calendarUid;
    }

    public void setCalendarUid(String calendarUid) {
        this.calendarUid = calendarUid;
    }

    public String getCalendarUpdateSequence() {
        return calendarUpdateSequence;
    }

    public void setCalendarUpdateSequence(String calendarUpdateSequence) {
        this.calendarUpdateSequence = calendarUpdateSequence;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(String modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdd1() {
        return add1;
    }

    public void setIsMylisting(String isMylisting) {
        this.isMylisting = isMylisting;
    }

    public String getIsMylisting() {
        return isMylisting;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentFirstName() {
        return agentFirstName;
    }

    public void setAgentFirstName(String agentFirstName) {
        this.agentFirstName = agentFirstName;
    }

    public String getAgentLastName() {
        return agentLastName;
    }

    public void setAgentLastName(String agentLastName) {
        this.agentLastName = agentLastName;
    }

    public String getAgentEmail() {
        return agentEmail;
    }

    public void setAgentEmail(String agentEmail) {
        this.agentEmail = agentEmail;
    }

    public String getAgentPhone() {
        return agentPhone;
    }

    public void setAgentPhone(String agentPhone) {
        this.agentPhone = agentPhone;
    }

    public String getBookById() {
        return bookById;
    }

    public void setBookById(String bookById) {
        this.bookById = bookById;
    }

    public String getBookByName() {
        return bookByName;
    }

    public void setBookByName(String bookByName) {
        this.bookByName = bookByName;
    }

    public String getBookByFirstName() {
        return bookByFirstName;
    }

    public void setBookByFirstName(String bookByFirstName) {
        this.bookByFirstName = bookByFirstName;
    }

    public String getBookByLastName() {
        return bookByLastName;
    }

    public void setBookByLastName(String bookByLastName) {
        this.bookByLastName = bookByLastName;
    }

    public String getBookByEmail() {
        return bookByEmail;
    }

    public void setBookByEmail(String bookByEmail) {
        this.bookByEmail = bookByEmail;
    }

    public String getBookByPhone() {
        return bookByPhone;
    }

    public void setBookByPhone(String bookByPhone) {
        this.bookByPhone = bookByPhone;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getNewMessageCount() {
        return newMessageCount;
    }

    public void setNewMessageCount(String newMessageCount) {
        this.newMessageCount = newMessageCount;
    }

    public String getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(String messageCount) {
        this.messageCount = messageCount;
    }

    public String getNewMessageCountCilent() {
        return newMessageCountCilent;
    }

    public void setNewMessageCountCilent(String newMessageCountCilent) {
        this.newMessageCountCilent = newMessageCountCilent;
    }

    public String getMessageCountCilent() {
        return messageCountCilent;
    }

    public void setMessageCountCilent(String messageCountCilent) {
        this.messageCountCilent = messageCountCilent;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getListingAgent() {
        return listingAgent;
    }

    public void setListingAgent(String listingAgent) {
        this.listingAgent = listingAgent;
    }

    public ListingShowing getListing() {
        return listing;
    }

    public void setListing(ListingShowing listing) {
        this.listing = listing;
    }

    public String getAgentCompany() {
        return agentCompany;
    }

    public void setAgentCompany(String agentCompany) {
        this.agentCompany = agentCompany;
    }

    public class ListingShowing {
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

        @SerializedName("IMAGES")
        @Expose
        private ArrayList<ImageListing> arrImageListing;

        @SerializedName("DCP_ID")
        @Expose
        private String dcpId;
        @SerializedName("DCP_FULL_NAME")
        @Expose
        private String dcpFullName;
        @SerializedName("DCP_EMAIL")
        @Expose
        private String dcpEmail;
        @SerializedName("DCP_PHONE")
        @Expose
        private String dcpPhone;
        @SerializedName("DCP_PHOTO")
        @Expose
        private String dcpPhoto;
        @SerializedName("DCP_COMPANY_NAME")
        @Expose
        private String dcpCompanyName;
        @SerializedName("DCP_COMPANY_ADDRESS_1")
        @Expose
        private String dcpAdd1;
        @SerializedName("DCP_COMPANY_ADDRESS_2")
        @Expose
        private String dcpAdd2;

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

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public NextOpenHouse getNextOpenHouse() {
            return nextOpenHouse;
        }

        public void setNextOpenHouse(NextOpenHouse nextOpenHouse) {
            this.nextOpenHouse = nextOpenHouse;
        }

        public String getFileLocation() {
            return fileLocation;
        }

        public void setFileLocation(String fileLocation) {
            this.fileLocation = fileLocation;
        }

        public String getInFavorite() {
            return inFavorite;
        }

        public void setInFavorite(String inFavorite) {
            this.inFavorite = inFavorite;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getImgName() {
            return imgName;
        }

        public void setImgName(String imgName) {
            this.imgName = imgName;
        }

        public ArrayList<ImageListing> getArrImageListing() {
            return arrImageListing;
        }

        public void setArrImageListing(ArrayList<ImageListing> arrImageListing) {
            this.arrImageListing = arrImageListing;
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

        public String getDcpPhone() {
            return dcpPhone;
        }
    }

    public class NextOpenHouse {
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("timezone_type")
        @Expose
        private String timeZoneType;
        @SerializedName("timezone")
        @Expose
        private String timeZone;

        public String getDate() {
            return date;
        }

        public String getTimeZoneType() {
            return timeZoneType;
        }

        public String getTimeZone() {
            return timeZone;
        }

    }

    public class ActionStatus {
        @SerializedName("STATUS")
        @Expose
        private String status;
        @SerializedName("SHOW_BUTTON")
        @Expose
        private String showButton;
        @SerializedName("EXTRA")
        @Expose
        private Extra extra;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getShowButton() {
            return showButton;
        }

        public void setShowButton(String showButton) {
            this.showButton = showButton;
        }

        public Extra getExtra() {
            return extra;
        }

        public void setExtra(Extra extra) {
            this.extra = extra;
        }
    }

    public class Extra {
        @SerializedName("LISTING_ADDRESS")
        @Expose
        private String add;
        @SerializedName("DAYS_AGO_TEXT")
        @Expose
        private String dayAgoNext;
        @SerializedName("APPOINTMENT_ID")
        @Expose
        private String apptId;
        @SerializedName("LISTING_ID")
        @Expose
        private String lid;
        @SerializedName("CONTACT_PERSON_ID")
        @Expose
        private String contactId;
        @SerializedName("DAYS_AGO")
        @Expose
        private String dayAgo;
        @SerializedName("APPOINTMENT_STATUS")
        @Expose
        private String apptStatus;

        public String getAdd() {
            return add;
        }

        public void setAdd(String add) {
            this.add = add;
        }

        public String getDayAgoNext() {
            return dayAgoNext;
        }

        public void setDayAgoNext(String dayAgoNext) {
            this.dayAgoNext = dayAgoNext;
        }

        public String getApptId() {
            return apptId;
        }

        public void setApptId(String apptId) {
            this.apptId = apptId;
        }

        public String getLid() {
            return lid;
        }

        public void setLid(String lid) {
            this.lid = lid;
        }

        public String getContactId() {
            return contactId;
        }

        public void setContactId(String contactId) {
            this.contactId = contactId;
        }

        public String getDayAgo() {
            return dayAgo;
        }

        public void setDayAgo(String dayAgo) {
            this.dayAgo = dayAgo;
        }

        public String getApptStatus() {
            return apptStatus;
        }

        public void setApptStatus(String apptStatus) {
            this.apptStatus = apptStatus;
        }
    }
}
