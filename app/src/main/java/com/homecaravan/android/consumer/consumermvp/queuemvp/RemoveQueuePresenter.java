package com.homecaravan.android.consumer.consumermvp.queuemvp;

import com.homecaravan.android.consumer.api.QueueApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoveQueuePresenter {
    private RemoveQueueView mView;

    public RemoveQueuePresenter(RemoveQueueView mView) {
        this.mView = mView;
    }

    public void removeQueue(String ids) {
        QueueApi queueApi = ServiceGeneratorConsumer.createService(QueueApi.class);
        queueApi.removeQueue(ids).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.removeQueueSuccess();
                    } else {
                        mView.removeQueueFail();
                    }
                } else {
                    mView.removeQueueFail();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                mView.removeQueueFail();
            }
        });
    }
}
