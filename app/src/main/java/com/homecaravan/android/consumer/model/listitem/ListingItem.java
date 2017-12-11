package com.homecaravan.android.consumer.model.listitem;

import com.homecaravan.android.consumer.model.responseapi.ListingSearchMap;

public class ListingItem {
    private boolean isSelected;

    private boolean isFavorite;
    private boolean isQueue;
    private boolean isSuperVote;
    private boolean isShare;

    private boolean isPrice;
    private boolean isLocation;
    private boolean isCondition;
    private boolean isSize;

    private ListingSearchMap listing;

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

    public ListingSearchMap getListing() {
        return listing;
    }

    public void setListing(ListingSearchMap listing) {
        this.listing = listing;
    }

    public boolean isQueue() {
        return isQueue;
    }

    public void setQueue(boolean queue) {
        isQueue = queue;
    }

    public boolean isSuperVote() {
        return isSuperVote;
    }

    public void setSuperVote(boolean superVote) {
        isSuperVote = superVote;
    }

    public boolean isShare() {
        return isShare;
    }

    public void setShare(boolean share) {
        isShare = share;
    }

    public boolean isPrice() {
        return isPrice;
    }

    public void setPrice(boolean price) {
        isPrice = price;
    }

    public boolean isLocation() {
        return isLocation;
    }

    public void setLocation(boolean location) {
        isLocation = location;
    }

    public boolean isCondition() {
        return isCondition;
    }

    public void setCondition(boolean condition) {
        isCondition = condition;
    }

    public boolean isSize() {
        return isSize;
    }

    public void setSize(boolean size) {
        isSize = size;
    }
}
