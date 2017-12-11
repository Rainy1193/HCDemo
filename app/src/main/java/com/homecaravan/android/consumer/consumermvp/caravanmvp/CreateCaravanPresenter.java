package com.homecaravan.android.consumer.consumermvp.caravanmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.CaravanApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseCaravan;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateCaravanPresenter {
    public CreateCaravanView mView;

    public CreateCaravanPresenter(CreateCaravanView mView) {
        this.mView = mView;
    }

    public void createCaravan(String queue, String name) {
        CaravanApi caravanApi = ServiceGeneratorConsumer.createService(CaravanApi.class);
        caravanApi.createFromQueue(queue, name).enqueue(new Callback<ResponseCaravan>() {
            @Override
            public void onResponse(Call<ResponseCaravan> call, Response<ResponseCaravan> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.createCaravanSuccess(response.body());
                    } else {
                        mView.createCaravanFail(response.body().getMessage());
                    }
                } else {
                    mView.createCaravanFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseCaravan> call, Throwable t) {
                mView.createCaravanFail(R.string.error_server);
            }
        });
    }
}
