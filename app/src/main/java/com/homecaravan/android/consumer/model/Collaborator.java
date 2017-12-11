package com.homecaravan.android.consumer.model;


public class Collaborator {
    private ConsumerAgent agent;
    private boolean isCollaborator;

    public Collaborator(ConsumerAgent agent, boolean isCollaborator) {
        this.agent = agent;
        this.isCollaborator = isCollaborator;
    }

    public ConsumerAgent getAgent() {
        return agent;
    }

    public boolean isCollaborator() {
        return isCollaborator;
    }

}
