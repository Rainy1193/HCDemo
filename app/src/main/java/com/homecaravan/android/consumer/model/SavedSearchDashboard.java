package com.homecaravan.android.consumer.model;

public class SavedSearchDashboard {
    private SavedSearch savedSearch;
    private String type;

    public SavedSearch getSavedSearch() {
        return savedSearch;
    }

    public void setSavedSearch(SavedSearch savedSearch) {
        this.savedSearch = savedSearch;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
