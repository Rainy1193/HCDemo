package com.homecaravan.android.consumer.consumermvp.searchmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.CBSApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.PolygonSearch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavePolygonPresenter {
    private SavePolygonView mView;

    public SavePolygonPresenter(SavePolygonView mView) {
        this.mView = mView;
    }

    public void savePolygon(final String data) {
        CBSApi cbsApi = ServiceGeneratorConsumer.createService(CBSApi.class);
        cbsApi.savePolygon(data).enqueue(new Callback<PolygonSearch>() {
            @Override
            public void onResponse(Call<PolygonSearch> call, Response<PolygonSearch> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.savePolygonSuccess(response.body().data);
                    } else {
                        mView.savePolygonFail(response.body().getMessage());
                    }
                } else {
                    mView.savePolygonFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<PolygonSearch> call, Throwable t) {
                mView.savePolygonFail(R.string.error_server);
            }
        });
    }
}
