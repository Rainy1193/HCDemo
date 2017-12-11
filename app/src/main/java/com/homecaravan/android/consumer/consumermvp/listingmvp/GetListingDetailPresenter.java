package com.homecaravan.android.consumer.consumermvp.listingmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.CBSApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseListingDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetListingDetailPresenter {

    private GetListingDetailView mView;

    public GetListingDetailPresenter(GetListingDetailView mView) {
        this.mView = mView;
    }

    public void getListingDetail(String id) {
        CBSApi cbsApi = ServiceGeneratorConsumer.createService(CBSApi.class);
        cbsApi.getListingDetail(id).enqueue(new Callback<ResponseListingDetail>() {
            @Override
            public void onResponse(Call<ResponseListingDetail> call, Response<ResponseListingDetail> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.getListingDetailSuccess(response.body().getListing());
                    } else {
                        mView.getListingDetailFail(response.body().getMessage());
                    }
                } else {
                    mView.getListingDetailFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseListingDetail> call, Throwable t) {
                mView.getListingDetailFail(R.string.error_server);
            }
        });
    }
}
