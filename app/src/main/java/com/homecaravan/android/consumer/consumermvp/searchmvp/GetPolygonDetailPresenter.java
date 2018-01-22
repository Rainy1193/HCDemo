package com.homecaravan.android.consumer.consumermvp.searchmvp;

import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.api.CBSApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.PolygonDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetPolygonDetailPresenter {
    private GetPolygonView mView;
    private Call<PolygonDetail> mCall;

    public GetPolygonDetailPresenter(GetPolygonView mView) {
        this.mView = mView;
    }

    public void getPolygonDetail(String id) {
        CBSApi cbsApi = ServiceGeneratorConsumer.createService(CBSApi.class);
        mCall = cbsApi.getPolygon(Constants.getInstance().getURL_BASE_CONSUMER() + "v2/search_api/polygon/" + id);
        mCall.enqueue(new Callback<PolygonDetail>() {
            @Override
            public void onResponse(Call<PolygonDetail> call, Response<PolygonDetail> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.getPolygonSuccess(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<PolygonDetail> call, Throwable t) {

            }
        });
    }


    public Call<PolygonDetail> getCall() {
        return mCall;
    }
}
