package com.homecaravan.android.consumer.listener;

import com.homecaravan.android.consumer.model.Collaborator;

public interface ICollaboratorListener {
    void addColl();

    void pickAgent(Collaborator collaborator);
}
