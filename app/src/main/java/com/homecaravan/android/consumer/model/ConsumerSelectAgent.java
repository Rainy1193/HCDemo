package com.homecaravan.android.consumer.model;


public class ConsumerSelectAgent  {
    private ConsumerTeam agent;
    private boolean isPick;

    public ConsumerTeam getAgent() {
        return agent;
    }

    public void setAgent(ConsumerTeam agent) {
        this.agent = agent;
    }

    public boolean isPick() {
        return isPick;
    }

    public void setPick(boolean pick) {
        isPick = pick;
    }
}
