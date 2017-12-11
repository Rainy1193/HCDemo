package com.homecaravan.android.consumer.model.listitem;


import com.homecaravan.android.consumer.model.responseapi.Participant;

public class TeamSaveSearchItem {
    private Participant participant;
    private boolean isCollaborator;

    public TeamSaveSearchItem(Participant participant, boolean isCollaborator) {
        this.participant = participant;
        this.isCollaborator = isCollaborator;
    }

    public Participant getParticipant() {
        return participant;
    }

    public boolean isCollaborator() {
        return isCollaborator;
    }
}
