package com.homecaravan.android.consumer.consumermvp.caravanmvp;

import com.homecaravan.android.consumer.api.CaravanApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseParticipants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRecentPresenter {
    private GetRecentView mView;

    public GetRecentPresenter(GetRecentView mView) {
        this.mView = mView;
    }

    public void getRecent() {
        CaravanApi caravanApi = ServiceGeneratorConsumer.createService(CaravanApi.class);
        caravanApi.getRecent().enqueue(new Callback<ResponseParticipants>() {
            @Override
            public void onResponse(Call<ResponseParticipants> call, Response<ResponseParticipants> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.getRecentSuccess(response.body().getData());
                    } else {
                        mView.getRecentFail();
                    }
                } else {
                    mView.getRecentFail();
                }
            }

            @Override
            public void onFailure(Call<ResponseParticipants> call, Throwable t) {
                mView.getRecentFail();
            }
        });
    }
}
