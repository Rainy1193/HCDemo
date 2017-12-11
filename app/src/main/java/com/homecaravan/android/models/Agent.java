package com.homecaravan.android.models;

/**
 * Created by RAINY on 5/21/2016.
 */
public class Agent {

    private String id;

    public static Agent agent;
    public static Agent getInstance() {
        if (agent == null) {
            agent = new Agent();
        }
        return agent;
    }

    private Agent() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
