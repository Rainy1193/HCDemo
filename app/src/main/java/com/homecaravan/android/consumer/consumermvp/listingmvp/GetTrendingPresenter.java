package com.homecaravan.android.consumer.consumermvp.listingmvp;


import com.homecaravan.android.consumer.api.CBSApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseListings;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetTrendingPresenter {
    private GetTrendingView mView;

    public GetTrendingPresenter(GetTrendingView mView) {
        this.mView = mView;
    }

    public void getTrending(String limit) {
        CBSApi cbsApi = ServiceGeneratorConsumer.createService(CBSApi.class);
        cbsApi.getTrending(limit).enqueue(new Callback<ResponseListings>() {
            @Override
            public void onResponse(Call<ResponseListings> call, Response<ResponseListings> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.getTrendingSuccess(response.body().getData().getListingFulls());
                    } else {
                        mView.getTrendingFail();
                    }
                } else {
                    mView.getTrendingFail();
                }
            }

            @Override
            public void onFailure(Call<ResponseListings> call, Throwable t) {
                mView.getTrendingFail();
            }
        });
    }
}
