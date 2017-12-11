package com.homecaravan.android.consumer.consumermvp.searchmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.CBSApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseSearchDetail;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveSearchPresenter {
    private SaveSearchView mView;
    private Call<ResponseSearchDetail> mCall;

    public SaveSearchPresenter(SaveSearchView mView) {
        this.mView = mView;
    }

    public void saveSearch(Map<String, RequestBody> map) {
        CBSApi searchDetail = ServiceGeneratorConsumer.createService(CBSApi.class);
        mCall = searchDetail.saveSearch(map);
        mCall.enqueue(new Callback<ResponseSearchDetail>() {
            @Override
            public void onResponse(Call<ResponseSearchDetail> call, Response<ResponseSearchDetail> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.saveSearchSuccess(response.body().getSearchDetail());
                    } else {
                        mView.saveSearchFail(response.body().getMessage());
                    }
                } else {
                    mView.saveSearchFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseSearchDetail> call, Throwable t) {
                if (!call.isCanceled()) {
                    mView.saveSearchFail(R.string.error_server);
                }
            }
        });
    }

    public Call<ResponseSearchDetail> getCall() {
        return mCall;
    }

    public void cancelGetSearchDetail() {
        mCall.cancel();
    }

}
