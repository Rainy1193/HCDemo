package com.homecaravan.android.consumer.consumermvp.caravanmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.CaravanParticipants;

import java.util.ArrayList;

public interface AddParticipantView {
    void addParticipantSuccess(ArrayList<CaravanParticipants> participants);
    void addParticipantFail();
    void addParticipantFail(@StringRes int message);
}
