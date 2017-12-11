package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListingCaravan {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("lkey")
    @Expose
    private String lkey;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("listingType")
    @Expose
    private String listingType;
    @SerializedName("saleType")
    @Expose
    private String saleType;
    @SerializedName("propertyType")
    @Expose
    private String propertyType;
    @SerializedName("bedrooms")
    @Expose
    private String bedrooms;
    @SerializedName("bathrooms")
    @Expose
    private String bathrooms;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("directionUrl")
    @Expose
    private String directionUrl;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("yearBuilt")
    @Expose
    private String yearBuilt;
    @SerializedName("pool")
    @Expose
    private String pool;
    @SerializedName("garage")
    @Expose
    private String garage;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("price")
    @Expose
    private Price price;
    @SerializedName("livingSquare")
    @Expose
    private LivingSquare livingSquare;
    @SerializedName("lotSize")
    @Expose
    private LotSize lotSize;
    @SerializedName("listingImages")
    @Expose
    private ListingImage listingImages;
    @SerializedName("timezone")
    @Expose
    private String timeZone;
    @SerializedName("agentName")
    @Expose
    private String agent;
    @SerializedName("agentAvatar")
    @Expose
    private String agentAvatar;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDirectionUrl() {
        return directionUrl;
    }

    public void setDirectionUrl(String directionUrl) {
        this.directionUrl = directionUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuilt(String yearBuilt) {
        this.yearBuilt = yearBuilt;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public LivingSquare getLivingSquare() {
        return livingSquare;
    }

    public void setLivingSquare(LivingSquare livingSquare) {
        this.livingSquare = livingSquare;
    }

    public LotSize getLotSize() {
        return lotSize;
    }

    public void setLotSize(LotSize lotSize) {
        this.lotSize = lotSize;
    }

    public ListingImage getListingImages() {
        return listingImages;
    }

    public void setListingImages(ListingImage listingImages) {
        this.listingImages = listingImages;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getAgentAvatar() {
        return agentAvatar;
    }

    public void setAgentAvatar(String agentAvatar) {
        this.agentAvatar = agentAvatar;
    }

    @Override
    public String toString() {
        return "ListingCaravan{" +
                "id='" + id + '\'' +
                ", lkey='" + lkey + '\'' +
                ", status='" + status + '\'' +
                ", listingType='" + listingType + '\'' +
                ", saleType='" + saleType + '\'' +
                ", propertyType='" + propertyType + '\'' +
                ", bedrooms='" + bedrooms + '\'' +
                ", bathrooms='" + bathrooms + '\'' +
                ", url='" + url + '\'' +
                ", directionUrl='" + directionUrl + '\'' +
                ", description='" + description + '\'' +
                ", yearBuilt='" + yearBuilt + '\'' +
                ", pool='" + pool + '\'' +
                ", garage='" + garage + '\'' +
                ", address=" + address +
                ", price=" + price +
                ", livingSquare=" + livingSquare +
                ", lotSize=" + lotSize +
                ", listingImages=" + listingImages +
                ", timeZone='" + timeZone + '\'' +
                ", agent='" + agent + '\'' +
                ", agentAvatar='" + agentAvatar + '\'' +
                '}';
    }
}
