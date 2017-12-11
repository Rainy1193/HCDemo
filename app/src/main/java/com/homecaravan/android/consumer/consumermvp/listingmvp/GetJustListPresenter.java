package com.homecaravan.android.consumer.consumermvp.listingmvp;


import com.homecaravan.android.consumer.api.CBSApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseListings;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetJustListPresenter {
    private GetJustListView mView;

    public GetJustListPresenter(GetJustListView mView) {
        this.mView = mView;
    }

    public void getJustList(String limit) {
        CBSApi cbsApi = ServiceGeneratorConsumer.createService(CBSApi.class);
        cbsApi.getJustList(limit).enqueue(new Callback<ResponseListings>() {
            @Override
            public void onResponse(Call<ResponseListings> call, Response<ResponseListings> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.getJustListSuccess(response.body().getData().getListingFulls());
                    } else {
                        mView.getJustListFail();
                    }
                } else {
                    mView.getJustListFail();
                }
            }

            @Override
            public void onFailure(Call<ResponseListings> call, Throwable t) {
                mView.getJustListFail();
            }
        });
    }
}
