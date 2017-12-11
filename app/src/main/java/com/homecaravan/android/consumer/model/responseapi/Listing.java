package com.homecaravan.android.consumer.model.responseapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Listing {
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
    @SerializedName("agentId")
    @Expose
    private String agentId;
    @SerializedName("agentName")
    @Expose
    private String agentName;
    @SerializedName("agentAvatar")
    @Expose
    private String agentAvatar;


    public String getId() {
        return id;
    }

    public String getLkey() {
        return lkey;
    }

    public String getStatus() {
        return status;
    }

    public String getListingType() {
        return listingType;
    }

    public String getSaleType() {
        return saleType;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public String getUrl() {
        return url;
    }

    public String getDirectionUrl() {
        return directionUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getYearBuilt() {
        return yearBuilt;
    }

    public String getPool() {
        return pool;
    }

    public String getGarage() {
        return garage;
    }

    public Address getAddress() {
        return address;
    }

    public Price getPrice() {
        return price;
    }

    public LivingSquare getLivingSquare() {
        return livingSquare;
    }

    public LotSize getLotSize() {
        return lotSize;
    }

    public ListingImage getListingImages() {
        return listingImages;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLkey(String lkey) {
        this.lkey = lkey;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setListingType(String listingType) {
        this.listingType = listingType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDirectionUrl(String directionUrl) {
        this.directionUrl = directionUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setYearBuilt(String yearBuilt) {
        this.yearBuilt = yearBuilt;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }

    public void setGarage(String garage) {
        this.garage = garage;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public void setLivingSquare(LivingSquare livingSquare) {
        this.livingSquare = livingSquare;
    }

    public void setLotSize(LotSize lotSize) {
        this.lotSize = lotSize;
    }

    public void setListingImages(ListingImage listingImages) {
        this.listingImages = listingImages;
    }

    public String getAgentId() {
        return agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public String getAgentAvatar() {
        return agentAvatar;
    }

    @Override
    public String toString() {
        return "Listing{" +
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
                '}';
    }
}
