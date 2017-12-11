package com.homecaravan.android.consumer.consumermvp.queuemvp;

import com.homecaravan.android.consumer.api.QueueApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseQueue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddQueuePresenter {
    private AddQueueView mView;

    public AddQueuePresenter(AddQueueView mView) {
        this.mView = mView;
    }

    public void addQueue(String id) {
        QueueApi queueApi = ServiceGeneratorConsumer.createService(QueueApi.class);
        queueApi.addQueue(id).enqueue(new Callback<ResponseQueue>() {
            @Override
            public void onResponse(Call<ResponseQueue> call, Response<ResponseQueue> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.addQueueSuccess(response.body().getData().getQueueData().get(0).getListing());
                    } else {
                        mView.addQueueFail();
                    }
                } else {
                    mView.addQueueFail();
                }
            }

            @Override
            public void onFailure(Call<ResponseQueue> call, Throwable t) {
                mView.addQueueFail();
            }
        });
    }
}
