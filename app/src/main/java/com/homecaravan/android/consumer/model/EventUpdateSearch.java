package com.homecaravan.android.consumer.model;


import com.homecaravan.android.consumer.model.responseapi.Participant;

public class EventUpdateSearch {
    public boolean delete;
    public boolean changeName;
    public String name;
    public String idSearch;
    public String idParticipant;
    public Participant participant;
    public EventUpdateSearch() {
    }

    public String getIdSearch() {
        return idSearch;
    }

    public void setIdSearch(String idSearch) {
        this.idSearch = idSearch;
    }

    public String getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(String idParticipant) {
        this.idParticipant = idParticipant;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChangeName() {
        return changeName;
    }

    public void setChangeName(boolean changeName) {
        this.changeName = changeName;
    }
}
