package com.homecaravan.android.consumer.consumermvp.searchmvp;


import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.CBSApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseSearchMap;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMapPresenter {
    private SearchMapView mView;
    private Call<ResponseSearchMap> mCall;

    public SearchMapPresenter(SearchMapView mView) {
        this.mView = mView;
    }

    public void searchMap(Map<String, RequestBody> map) {
        CBSApi searchMap = ServiceGeneratorConsumer.createService(CBSApi.class);
        mCall = searchMap.search(map);
        mCall.enqueue(new Callback<ResponseSearchMap>() {

            @Override
            public void onResponse(Call<ResponseSearchMap> call, Response<ResponseSearchMap> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.searchMapSuccess(response.body());
                    } else {
                        mView.searchMapFail(response.body().getMessages().get(0).getText());
                    }
                } else {
                    mView.searchMapFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseSearchMap> call, Throwable t) {
                if (!call.isCanceled()) {
                    mView.searchMapFail(R.string.error_server);
                }
            }
        });
    }

    public Call<ResponseSearchMap> getCall() {
        return mCall;
    }

    public void cancelSearch() {
        mCall.cancel();
    }
}
