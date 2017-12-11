package com.homecaravan.android.consumer.consumermvp.queuemvp;

import android.util.Log;

import com.homecaravan.android.consumer.api.QueueApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseQueue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetQueuePresenter {
    private GetQueueView mView;

    public GetQueuePresenter(GetQueueView mView) {
        this.mView = mView;
    }

    public void getQueue() {
        QueueApi queueApi = ServiceGeneratorConsumer.createService(QueueApi.class);
        queueApi.getQueue().enqueue(new Callback<ResponseQueue>() {
            @Override
            public void onResponse(Call<ResponseQueue> call, Response<ResponseQueue> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.getQueueSuccess(response.body().getData().getQueueData());
                    } else {
                        mView.getQueueFail();
                    }
                } else {
                    Log.e("fail",response.message());
                    mView.getQueueFail();
                }
            }

            @Override
            public void onFailure(Call<ResponseQueue> call, Throwable t) {
                mView.getQueueFail();
            }
        });
    }
}
