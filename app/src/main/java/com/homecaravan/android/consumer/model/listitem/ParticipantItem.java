package com.homecaravan.android.consumer.model.listitem;


import com.homecaravan.android.consumer.model.responseapi.CaravanParticipants;


public class ParticipantItem {
    private CaravanParticipants participant;
    private boolean isPick;

    public ParticipantItem(CaravanParticipants participant, boolean isPick) {
        this.participant = participant;
        this.isPick = isPick;
    }

    public CaravanParticipants getParticipant() {
        return participant;
    }

    public void setParticipant(CaravanParticipants participant) {
        this.participant = participant;
    }

    public boolean isPick() {
        return isPick;
    }

    public void setPick(boolean pick) {
        isPick = pick;
    }
}
