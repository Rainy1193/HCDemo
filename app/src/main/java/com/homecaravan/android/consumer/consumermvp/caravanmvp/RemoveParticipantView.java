package com.homecaravan.android.consumer.consumermvp.caravanmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.CaravanParticipants;

import java.util.ArrayList;

public interface RemoveParticipantView {
    void removeParticipantSuccess(ArrayList<CaravanParticipants> participants);

    void removeParticipantFail();

    void removeParticipantFail(@StringRes int message);
}
