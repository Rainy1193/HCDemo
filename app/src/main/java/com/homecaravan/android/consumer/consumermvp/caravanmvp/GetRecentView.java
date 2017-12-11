package com.homecaravan.android.consumer.consumermvp.caravanmvp;

import com.homecaravan.android.consumer.model.responseapi.CaravanParticipants;

import java.util.ArrayList;

public interface GetRecentView {
    void getRecentSuccess(ArrayList<CaravanParticipants> participants);

    void getRecentFail();
}
