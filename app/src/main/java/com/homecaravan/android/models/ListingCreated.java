package com.homecaravan.android.models;

/**
 * Created by RAINY on 5/31/2016.
 */
public class ListingCreated {
    private String id;
    private String lkey;
    private String userId;
    private String ast;
    private String createDateTime;
    private String createBy;
    private String modifiedDateTime;
    private String modifiedBy;
    private String listingId;
    private String listingType;
    private String propertyType;
    private String price;
    private String add1;
    private String add2;
    private String city;
    private String state;
    private String status;
    private String zip;
    private String longitude;
    private String latitude;
    private String timeZone;
    private String bedroom;
    private String bathroom;
    private String livingSquareFeet;
    private String lotSize;
    private String pool;
    private String garage;
    private String contactName;
    private String mobilePhone;
    private String email;
    private String photo;
    private String des;
    private String yearBuilt;
    private String followupType;
    private String reminderType;
    private String displayType;
    private String roleId;
    private String actionRequired;
    private String listingThumb;
    private String totalApproval;
    private String nextppt;
    private String numbApproved;
    private String like;
    public static ListingCreated listingCreated;

    public static ListingCreated getInstance() {
        if (listingCreated == null) {
            listingCreated = new ListingCreated();
        }
        return listingCreated;
    }

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

    public String getAst() {
        return ast;
    }

    public void setAst(String ast) {
        this.ast = ast;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getBedroom() {
        return bedroom;
    }

    public void setBedroom(String bedroom) {
        this.bedroom = bedroom;
    }

    public String getBathroom() {
        return bathroom;
    }

    public void setBathroom(String bathroom) {
        this.bathroom = bathroom;
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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
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

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
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

    public String getTotalApproval() {
        return totalApproval;
    }

    public void setTotalApproval(String totalApproval) {
        this.totalApproval = totalApproval;
    }

    public String getNextppt() {
        return nextppt;
    }

    public void setNextppt(String nextppt) {
        this.nextppt = nextppt;
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

    @Override
    public String toString() {
        return "ListingCreated{" +
                "id='" + id + '\'' +
                ", lkey='" + lkey + '\'' +
                ", userId='" + userId + '\'' +
                ", ast='" + ast + '\'' +
                ", createDateTime='" + createDateTime + '\'' +
                ", createBy='" + createBy + '\'' +
                ", modifiedDateTime='" + modifiedDateTime + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", listingId='" + listingId + '\'' +
                ", listingType='" + listingType + '\'' +
                ", propertyType='" + propertyType + '\'' +
                ", price='" + price + '\'' +
                ", add1='" + add1 + '\'' +
                ", add2='" + add2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", status='" + status + '\'' +
                ", zip='" + zip + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", bedroom='" + bedroom + '\'' +
                ", bathroom='" + bathroom + '\'' +
                ", livingSquareFeet='" + livingSquareFeet + '\'' +
                ", lotSize='" + lotSize + '\'' +
                ", pool='" + pool + '\'' +
                ", garage='" + garage + '\'' +
                ", contactName='" + contactName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                ", des='" + des + '\'' +
                ", yearBuilt='" + yearBuilt + '\'' +
                ", followupType='" + followupType + '\'' +
                ", reminderType='" + reminderType + '\'' +
                ", displayType='" + displayType + '\'' +
                ", roleId='" + roleId + '\'' +
                ", actionRequired='" + actionRequired + '\'' +
                ", listingThumb='" + listingThumb + '\'' +
                ", totalApproval='" + totalApproval + '\'' +
                ", nextppt='" + nextppt + '\'' +
                ", numbApproved='" + numbApproved + '\'' +
                ", like='" + like + '\'' +
                '}';
    }
}
