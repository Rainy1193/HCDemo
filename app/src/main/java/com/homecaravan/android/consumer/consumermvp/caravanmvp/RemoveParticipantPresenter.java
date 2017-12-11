package com.homecaravan.android.consumer.consumermvp.caravanmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.CaravanApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseParticipants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoveParticipantPresenter {
    private RemoveParticipantView mView;

    public RemoveParticipantPresenter(RemoveParticipantView mView) {
        this.mView = mView;
    }

    public void removeParticipant(String idCaravan, String uids) {
        CaravanApi caravanApi = ServiceGeneratorConsumer.createService(CaravanApi.class);
        caravanApi.removeParticipant(idCaravan, uids).enqueue(new Callback<ResponseParticipants>() {
            @Override
            public void onResponse(Call<ResponseParticipants> call, Response<ResponseParticipants> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.removeParticipantSuccess(response.body().getData());
                    } else {
                        mView.removeParticipantFail();
                    }
                } else {
                    mView.removeParticipantFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseParticipants> call, Throwable t) {
                mView.removeParticipantFail(R.string.error_server);
            }
        });
    }
}
