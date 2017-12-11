package com.homecaravan.android.consumer.listener;

import com.homecaravan.android.consumer.model.responseapi.ContactData;
import com.homecaravan.android.consumer.model.responseapi.ResponseFeatured;

public interface ITeamSearchListener {
    void pickAgent(ContactData contact, boolean b);

    void pickAgent(ResponseFeatured.Featured featuredAgent, boolean b);

    void pickAgentChangeTab();
}
