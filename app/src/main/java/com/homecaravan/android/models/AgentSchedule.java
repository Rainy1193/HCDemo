package com.homecaravan.android.models;


import com.homecaravan.android.consumer.model.ConsumerAgent;

public class AgentSchedule {
    private boolean isPick;
    private ConsumerAgent agent;

    public AgentSchedule(boolean isPick, ConsumerAgent agent) {
        this.isPick = isPick;
        this.agent = agent;
    }

    public ConsumerAgent getAgent() {
        return agent;
    }

    public void setAgent(ConsumerAgent agent) {
        this.agent = agent;
    }

    public boolean isPick() {
        return isPick;
    }

    public void setPick(boolean pick) {
        isPick = pick;
    }
}
