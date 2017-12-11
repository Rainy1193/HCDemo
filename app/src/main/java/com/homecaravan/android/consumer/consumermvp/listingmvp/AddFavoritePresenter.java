package com.homecaravan.android.consumer.consumermvp.listingmvp;

import com.homecaravan.android.consumer.api.CBSApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFavoritePresenter {
    private AddFavoriteView mView;

    public AddFavoritePresenter(AddFavoriteView mView) {
        this.mView = mView;
    }

    public void addFavoriteListing(String listingId) {
        CBSApi cbsApi = ServiceGeneratorConsumer.createService(CBSApi.class);
        cbsApi.addFavorite(listingId).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        if (response.body().getMessage().equalsIgnoreCase("The listing has been added to your favorite.")) {
                            mView.addFavoriteSuccess(true);
                        } else {
                            mView.addFavoriteSuccess(false);
                        }
                    } else {
                        mView.addFavoriteFail();
                    }
                } else {
                    mView.addFavoriteFail();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                mView.addFavoriteFail();
            }
        });
    }
}
