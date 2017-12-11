package com.homecaravan.android.consumer.listener;


import com.homecaravan.android.consumer.model.responseapi.ResponseFeatured;

public interface IAgentListener {
    void openAgent(ResponseFeatured.Featured featuredAgent);
    void openMyTeam();
}
