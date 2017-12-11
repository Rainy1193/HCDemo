package com.homecaravan.android.consumer.consumermvp.searchmvp;

import com.homecaravan.android.consumer.api.CBSApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseFavorite;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetFavoriteListingPresenter {
    private GetFavoriteListingView mView;

    public GetFavoriteListingPresenter(GetFavoriteListingView mView) {
        this.mView = mView;
    }

    public void getFavorite(String pageNumber) {
        CBSApi cbsApi = ServiceGeneratorConsumer.createService(CBSApi.class);
        cbsApi.getFavoriteListing(pageNumber).enqueue(new Callback<ResponseFavorite>() {
            @Override
            public void onResponse(Call<ResponseFavorite> call, Response<ResponseFavorite> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.getFavoriteSuccess(response.body().getData().getArrListing());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseFavorite> call, Throwable t) {

            }
        });
    }
}
