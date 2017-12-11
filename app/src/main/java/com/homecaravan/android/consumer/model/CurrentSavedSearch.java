package com.homecaravan.android.consumer.model;


import java.util.ArrayList;

public class CurrentSavedSearch {

    public static CurrentSavedSearch mCurrentSearch;
    private SavedSearch mSavedSearch;
    private ArrayList<ConsumerAgentInvite> mAgentNew = new ArrayList<>();

    public static CurrentSavedSearch getInstance() {
        if (mCurrentSearch == null) {
            mCurrentSearch = new CurrentSavedSearch();
        }
        return mCurrentSearch;
    }

    public ArrayList<ConsumerAgentInvite> getAgentNew() {
        return mAgentNew;
    }

    public void setAgentNew(ArrayList<ConsumerAgentInvite> mAgentNew) {
        this.mAgentNew = mAgentNew;
    }

    public SavedSearch getSavedSearch() {
        return mSavedSearch;
    }

    public void setSavedSearch(SavedSearch mSavedSearch) {
        this.mSavedSearch = mSavedSearch;
    }
}
