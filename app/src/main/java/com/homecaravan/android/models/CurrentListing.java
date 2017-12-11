package com.homecaravan.android.models;

import java.util.ArrayList;

/**
 * Created by RAINY on 4/21/2016.
 */
public class CurrentListing {
    private static CurrentListing currentListing;
    private String companyAgent;
    private ArrayList<String> image;
    private String idAgent;
    private String lkey;
    private String id;
    private String price;
    private String type;
    private String favorite;
    private String add1;
    private String city;
    private String des;
    private String bed;
    private String bath;
    private String sq;
    private String timeZone;
    private String longitude;
    private String latitude;
    private String propertyType;
    private String yearBuilt;
    private String lotSize;
    private String pool;
    private String garage;
    private String list;
    private String contactname;
    private String phone;
    private String state;
    private String zipcode;
    private String email;
    private String imageDetail;
    private String typeListing;
    private String listingAgent;
    private String agentEmail;
    private String agentPhone;
    private String agentName;
    private String agentPhoto;
    private String roleId;
    private ListingDetailDataAgent listingDetailDataAgent;
    private ListingDetailDataSetting listingDetailDataSettings;
    private ListingDetailDataListing listingDetailDataListing;
    private ArrayList<ListingTeam> listingDetailTeam;
    private String dcpId;
    private String dcpFullName;
    private String dcpEmail;
    private String dcpPhone;
    private String dcpPhoto;
    private String dcpNameCompany;
    private String dcpAdd1;
    private String dcpAdd2;

    public ArrayList<ListingTeam> getListingDetailTeam() {
        return listingDetailTeam;
    }

    public void setListingDetailTeam(ArrayList<ListingTeam> listingDetailTeam) {
        this.listingDetailTeam = listingDetailTeam;
    }

    public ListingDetailDataListing getListingDetailDataListing() {
        return listingDetailDataListing;
    }

    public void setListingDetailDataListing(ListingDetailDataListing listingDetailDataListing) {
        this.listingDetailDataListing = listingDetailDataListing;
    }

    public ListingDetailDataSetting getListingDetailDataSettings() {
        return listingDetailDataSettings;
    }

    public void setListingDetailDataSettings(ListingDetailDataSetting listingDetailDataSettings) {
        this.listingDetailDataSettings = listingDetailDataSettings;
    }

    public String getImageDetail() {
        return imageDetail;
    }

    public void setImageDetail(String imageDetail) {
        this.imageDetail = imageDetail;
    }


    public CurrentListing() {

    }

    public static CurrentListing getInstance() {
        if (currentListing == null) {
            currentListing = new CurrentListing();
        }
        return currentListing;
    }

    public String getAgentEmail() {
        return agentEmail;
    }

    public void setAgentEmail(String agentEmail) {
        this.agentEmail = agentEmail;
    }

    public String getAgentPhoto() {
        return agentPhoto;
    }

    public void setAgentPhoto(String agentPhoto) {
        this.agentPhoto = agentPhoto;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentPhone() {
        return agentPhone;
    }

    public void setAgentPhone(String agentPhone) {
        this.agentPhone = agentPhone;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getBath() {
        return bath;
    }

    public void setBath(String bath) {
        this.bath = bath;
    }

    public String getSq() {
        return sq;
    }

    public void setSq(String sq) {
        this.sq = sq;
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

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuilt(String yearBuilt) {
        this.yearBuilt = yearBuilt;
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

    public String getIdAgent() {
        return idAgent;
    }

    public void setIdAgent(String idAgent) {
        this.idAgent = idAgent;
    }

    public String getGarage() {
        return garage;
    }


    public void setGarage(String garage) {
        this.garage = garage;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ListingDetailDataAgent getListingDetailDataAgent() {
        return listingDetailDataAgent;
    }

    public void setListingDetailDataAgent(ListingDetailDataAgent listingDetailDataAgent) {
        this.listingDetailDataAgent = listingDetailDataAgent;
    }

    public String getCompanyAgent() {
        return companyAgent;
    }

    public void setCompanyAgent(String companyAgent) {
        this.companyAgent = companyAgent;
    }

    public String getTypeListing() {
        return typeListing;
    }

    public void setTypeListing(String typeListing) {
        this.typeListing = typeListing;
    }

    public String getListingAgent() {
        return listingAgent;
    }

    public void setListingAgent(String listingAgent) {
        this.listingAgent = listingAgent;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getDcpId() {
        return dcpId;
    }

    public void setDcpId(String dcpId) {
        this.dcpId = dcpId;
    }

    public String getDcpFullName() {
        return dcpFullName;
    }

    public void setDcpFullName(String dcpFullName) {
        this.dcpFullName = dcpFullName;
    }

    public String getDcpEmail() {
        return dcpEmail;
    }

    public void setDcpEmail(String dcpEmail) {
        this.dcpEmail = dcpEmail;
    }

    public String getDcpPhone() {
        return dcpPhone;
    }

    public void setDcpPhone(String dcpPhone) {
        this.dcpPhone = dcpPhone;
    }

    public String getDcpPhoto() {
        return dcpPhoto;
    }

    public void setDcpPhoto(String dcpPhoto) {
        this.dcpPhoto = dcpPhoto;
    }

    public String getDcpNameCompany() {
        return dcpNameCompany;
    }

    public void setDcpNameCompany(String dcpNameCompany) {
        this.dcpNameCompany = dcpNameCompany;
    }

    public String getDcpAdd1() {
        return dcpAdd1;
    }

    public void setDcpAdd1(String dcpAdd1) {
        this.dcpAdd1 = dcpAdd1;
    }

    public String getDcpAdd2() {
        return dcpAdd2;
    }

    public void setDcpAdd2(String dcpAdd2) {
        this.dcpAdd2 = dcpAdd2;
    }
}
