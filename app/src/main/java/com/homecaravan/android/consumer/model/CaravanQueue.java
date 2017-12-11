package com.homecaravan.android.consumer.model;

import android.util.Log;

import com.homecaravan.android.consumer.model.responseapi.Listing;

import java.util.ArrayList;

public class CaravanQueue {
    public static CaravanQueue caravanQueue;
    public ArrayList<Listing> listings = new ArrayList<>();
    public ArrayList<String> ids = new ArrayList<>();
    public String idRemove = "0";
    public ArrayList<ListingInQueue> listingQueue = new ArrayList<>();

    public static CaravanQueue getInstance() {
        if (caravanQueue == null) {
            caravanQueue = new CaravanQueue();
        }
        return caravanQueue;
    }

    public ArrayList<Listing> getListings() {
        return listings;
    }

    public void setListings(ArrayList<Listing> listings) {
        this.listings = listings;
    }


    public ArrayList<String> getIds() {
        return ids;
    }

    public void setIds(ArrayList<String> ids) {
        this.ids = ids;
    }

    public String getIdRemove() {
        return idRemove;
    }

    public void setIdRemove(String idRemove) {
        this.idRemove = idRemove;
    }

    public void removeQueueInSingleton() {
        Log.e("idRemove",idRemove);
        for (int i = 0; i < listings.size(); i++) {
            if (listings.get(i).getId().equalsIgnoreCase(idRemove)) {
                listings.remove(i);
                break;
            }
        }
        for (int i = 0; i < ids.size(); i++) {
            if (ids.get(i).equalsIgnoreCase(idRemove)) {
                ids.remove(i);
                break;
            }
        }
    }

    public ArrayList<ListingInQueue> getListingQueue() {
        return listingQueue;
    }

    public void setListingQueue(ArrayList<ListingInQueue> listingQueue) {
        this.listingQueue = listingQueue;
    }


}
