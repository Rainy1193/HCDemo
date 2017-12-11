package com.homecaravan.android.consumer.consumermvp.caravanmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.CaravanApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseSaveCaravan;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveCaravanPresenter {
    private SaveCaravanView mView;

    public SaveCaravanPresenter(SaveCaravanView mView) {
        this.mView = mView;
    }

    public void saveCaravan(String id, String destination) {
        CaravanApi caravanApi = ServiceGeneratorConsumer.createService(CaravanApi.class);
        caravanApi.saveCaravan(id, destination).enqueue(new Callback<ResponseSaveCaravan>() {
            @Override
            public void onResponse(Call<ResponseSaveCaravan> call, Response<ResponseSaveCaravan> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.saveCaravanSuccess(response.body());
                    } else {
                        mView.saveCaravanFail(response.body().getMessage());
                    }
                } else {
                    mView.saveCaravanFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseSaveCaravan> call, Throwable t) {
                if (!call.isCanceled()) {
                    mView.saveCaravanFail(R.string.error_server);
                }
            }
        });
    }
}
