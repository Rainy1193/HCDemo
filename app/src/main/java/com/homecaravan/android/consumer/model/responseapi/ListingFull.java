package com.homecaravan.android.consumer.model.responseapi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListingFull {
    @SerializedName("voteUp")
    @Expose
    private String voteUp;
    @SerializedName("voteDown")
    @Expose
    private String voteDown;
    @SerializedName("voteScore")
    @Expose
    private String voteScore;
    @SerializedName("usersVoteUp")
    @Expose
    private ArrayList<UserVote> usersVoteUp;
    @SerializedName("usersVoteDown")
    @Expose
    private ArrayList<UserVote> usersVoteDown;
    @SerializedName("mid")
    @Expose
    private String mid;
    @SerializedName("listingHasVoted")
    @Expose
    private String listingHasVoted;
    @SerializedName("votedNum")
    @Expose
    private String votedNum;
    @SerializedName("votedPercent")
    @Expose
    private String votedPercent;
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

    public String getVoteUp() {
        return voteUp;
    }

    public String getVoteDown() {
        return voteDown;
    }

    public String getVoteScore() {
        return voteScore;
    }

    public ArrayList<UserVote> getUsersVoteUp() {
        return usersVoteUp;
    }

    public ArrayList<UserVote> getUsersVoteDown() {
        return usersVoteDown;
    }

    public String getMid() {
        return mid;
    }

    public String getListingHasVoted() {
        return listingHasVoted;
    }

    public String getVotedNum() {
        return votedNum;
    }

    public String getVotedPercent() {
        return votedPercent;
    }

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

    @Override
    public String toString() {
        return "ListingFull{" +
                "voteUp='" + voteUp + '\'' +
                ", voteDown='" + voteDown + '\'' +
                ", voteScore='" + voteScore + '\'' +
                ", usersVoteUp=" + usersVoteUp +
                ", usersVoteDown=" + usersVoteDown +
                ", mid='" + mid + '\'' +
                ", listingHasVoted='" + listingHasVoted + '\'' +
                ", votedNum='" + votedNum + '\'' +
                ", votedPercent='" + votedPercent + '\'' +
                ", id='" + id + '\'' +
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

    public void setVoteUp(String voteUp) {
        this.voteUp = voteUp;
    }

    public void setVoteDown(String voteDown) {
        this.voteDown = voteDown;
    }

    public void setVoteScore(String voteScore) {
        this.voteScore = voteScore;
    }

    public void setUsersVoteUp(ArrayList<UserVote> usersVoteUp) {
        this.usersVoteUp = usersVoteUp;
    }

    public void setUsersVoteDown(ArrayList<UserVote> usersVoteDown) {
        this.usersVoteDown = usersVoteDown;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setListingHasVoted(String listingHasVoted) {
        this.listingHasVoted = listingHasVoted;
    }

    public void setVotedNum(String votedNum) {
        this.votedNum = votedNum;
    }

    public void setVotedPercent(String votedPercent) {
        this.votedPercent = votedPercent;
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
}
