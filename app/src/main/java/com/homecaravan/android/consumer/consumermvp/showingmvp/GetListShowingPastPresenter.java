package com.homecaravan.android.consumer.consumermvp.showingmvp;


import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.api.ShowingApi;
import com.homecaravan.android.consumer.model.responseapi.ResponseShowingCaravan;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetListShowingPastPresenter {

    private GetListShowingPastView mView;

    public GetListShowingPastPresenter(GetListShowingPastView mView) {
        this.mView = mView;
    }

    public void getListShowingPast(String type) {
        ShowingApi showingApi = ServiceGeneratorConsumer.createService(ShowingApi.class);
        showingApi.getListShowingCaravanPast(type).enqueue(new Callback<ResponseShowingCaravan>() {
            @Override
            public void onResponse(Call<ResponseShowingCaravan> call, Response<ResponseShowingCaravan> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.getShowingCaravanSuccess(response.body().getCaravanShowings());
                    } else {
                        mView.getShowingCaravanFail(response.body().getMessage());
                    }
                } else {
                    mView.getShowingCaravanFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseShowingCaravan> call, Throwable t) {

            }
        });
    }
}
