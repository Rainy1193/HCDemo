package com.homecaravan.android.consumer.model;


import io.realm.RealmList;
import io.realm.RealmObject;

public class SavedSearch extends RealmObject {
    private String idSearch;
    private String nameSearch;
    private String bath;
    private String bed;
    private String living;
    private String foundListing;
    private String newListing;
    private RealmList<CollaboratorDB> collaborators;
    private RealmList<ListingDB> listings;
    public String getIdSearch() {
        return idSearch;
    }

    public void setIdSearch(String idSearch) {
        this.idSearch = idSearch;
    }

    public String getNameSearch() {
        return nameSearch;
    }

    public void setNameSearch(String nameSearch) {
        this.nameSearch = nameSearch;
    }

    public String getBath() {
        return bath;
    }

    public void setBath(String bath) {
        this.bath = bath;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getLiving() {
        return living;
    }

    public void setLiving(String living) {
        this.living = living;
    }

    public String getFoundListing() {
        return foundListing;
    }

    public void setFoundListing(String foundListing) {
        this.foundListing = foundListing;
    }

    public String getNewListing() {
        return newListing;
    }

    public void setNewListing(String newListing) {
        this.newListing = newListing;
    }

    public RealmList<CollaboratorDB> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(RealmList<CollaboratorDB> collaborators) {
        this.collaborators = collaborators;
    }

    public RealmList<ListingDB> getListings() {
        return listings;
    }

    public void setListings(RealmList<ListingDB> listings) {
        this.listings = listings;
    }

    @Override
    public String toString() {
        return "SavedSearch{" +
                "idSearch='" + idSearch + '\'' +
                ", nameSearch='" + nameSearch + '\'' +
                ", bath='" + bath + '\'' +
                ", bed='" + bed + '\'' +
                ", living='" + living + '\'' +
                ", foundListing='" + foundListing + '\'' +
                ", newListing='" + newListing + '\'' +
                ", collaborators=" + collaborators +
                ", listings=" + listings +
                '}';
    }
}
