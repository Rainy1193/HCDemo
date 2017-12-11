package com.homecaravan.android.consumer.model.listitem;


import com.homecaravan.android.consumer.model.responseapi.ListingFull;

public class ListingFullItem {
    private boolean isSelected;
    private boolean isFavorite;
    private boolean favorite;
    private boolean favorite1;
    private boolean inQueue;
    private boolean selected;
    private boolean superVote;
    private boolean share;
    private boolean schedule;
    private boolean price;
    private boolean location;
    private boolean condition;
    private boolean size;

    private ListingFull listing;
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public ListingFull getListing() {
        return listing;
    }

    public void setListing(ListingFull listing) {
        this.listing = listing;
    }


    public boolean isFavorite1() {
        return favorite1;
    }

    public void setFavorite1(boolean favorite1) {
        this.favorite1 = favorite1;
    }

    public boolean isInQueue() {
        return inQueue;
    }

    public void setInQueue(boolean inQueue) {
        this.inQueue = inQueue;
    }

    public boolean isSuperVote() {
        return superVote;
    }

    public void setSuperVote(boolean superVote) {
        this.superVote = superVote;
    }

    public boolean isShare() {
        return share;
    }

    public void setShare(boolean share) {
        this.share = share;
    }

    public boolean isSchedule() {
        return schedule;
    }

    public void setSchedule(boolean schedule) {
        this.schedule = schedule;
    }

    public boolean isPrice() {
        return price;
    }

    public void setPrice(boolean price) {
        this.price = price;
    }

    public boolean isLocation() {
        return location;
    }

    public void setLocation(boolean location) {
        this.location = location;
    }

    public boolean isCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public boolean isSize() {
        return size;
    }

    public void setSize(boolean size) {
        this.size = size;
    }
}
