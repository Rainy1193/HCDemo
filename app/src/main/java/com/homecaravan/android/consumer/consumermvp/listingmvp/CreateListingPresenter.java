package com.homecaravan.android.consumer.consumermvp.listingmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.SellerApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseAddListing;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateListingPresenter {
    private CreateListingView mView;

    public CreateListingPresenter(CreateListingView mView) {
        this.mView = mView;
    }

    public void createListing(String address, String fullName, String title) {
        SellerApi sellerApi = ServiceGeneratorConsumer.createService(SellerApi.class);
        sellerApi.addListing(address, fullName, title).enqueue(new Callback<ResponseAddListing>() {
            @Override
            public void onResponse(Call<ResponseAddListing> call, Response<ResponseAddListing> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.createListingSuccess(response.body().getData());
                    } else {
                        if (response.body().getMessages().isEmpty()) {
                            mView.createListingFail(response.body().getMessage());
                        } else {
                            mView.createListingFail(response.body().getMessages().get(0).getText());
                        }
                    }
                } else {
                    mView.createListingFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseAddListing> call, Throwable t) {
                mView.createListingFail(R.string.error_server);
            }
        });
    }
}
