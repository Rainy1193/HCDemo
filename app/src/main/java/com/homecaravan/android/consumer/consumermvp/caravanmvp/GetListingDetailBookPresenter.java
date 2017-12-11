package com.homecaravan.android.consumer.consumermvp.caravanmvp;

import android.util.Log;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.CBSApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseListingDetailBook;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetListingDetailBookPresenter {
    private GetListingDetailBookView mView;

    public GetListingDetailBookPresenter(GetListingDetailBookView mView) {
        this.mView = mView;
    }

    public void getListingDetail(String listingId) {
        CBSApi cbsApi = ServiceGeneratorConsumer.createService(CBSApi.class);
        cbsApi.getListingDetailBook(listingId).enqueue(new Callback<ResponseListingDetailBook>() {
            @Override
            public void onResponse(Call<ResponseListingDetailBook> call, Response<ResponseListingDetailBook> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.getListingDetailSuccess(response.body().getData());
                    } else {
                        mView.getListingDetailFail(response.body().getMessage());
                    }
                } else {
                    Log.e("t", response.message());
                    mView.getListingDetailFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseListingDetailBook> call, Throwable t) {
                Log.e("t", t.getMessage());
                mView.getListingDetailFail(R.string.error_server);
            }
        });
    }
}
