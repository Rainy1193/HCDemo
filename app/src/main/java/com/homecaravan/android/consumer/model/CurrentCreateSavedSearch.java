package com.homecaravan.android.consumer.model;


import java.util.ArrayList;

public class CurrentCreateSavedSearch {

    public static CurrentCreateSavedSearch mCurrentCreate;
    private String name;
    private ArrayList<ConsumerAgentInvite> mAgentNew = new ArrayList<>();
    private ArrayList<String> mListingId = new ArrayList<>();

    public static CurrentCreateSavedSearch getInstance() {
        if (mCurrentCreate == null) {
            mCurrentCreate = new CurrentCreateSavedSearch();
        }
        return mCurrentCreate;
    }

    public ArrayList<ConsumerAgentInvite> getAgentNew() {
        return mAgentNew;
    }

    public void setAgentNew(ArrayList<ConsumerAgentInvite> mAgentNew) {
        this.mAgentNew = mAgentNew;
    }

    public ArrayList<String> getListingId() {
        return mListingId;
    }

    public void setListingId(ArrayList<String> mListingId) {
        this.mListingId = mListingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
