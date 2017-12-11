package com.homecaravan.android.consumer.consumermvp.loginmvp;

import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.api.UsersAPI;
import com.homecaravan.android.consumer.model.responseapi.ResponseFeatured;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetFeaturedPresenter {
    private GetFeaturedView mView;

    public GetFeaturedPresenter(GetFeaturedView mView) {
        this.mView = mView;
    }

    public void getFeature() {
        UsersAPI usersAPI = ServiceGeneratorConsumer.createService(UsersAPI.class);
        usersAPI.getFeatured().enqueue(new Callback<ResponseFeatured>() {
            @Override
            public void onResponse(Call<ResponseFeatured> call, Response<ResponseFeatured> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.getFeaturedSuccess(response.body().getGetFeatured());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseFeatured> call, Throwable t) {

            }
        });
    }
}
