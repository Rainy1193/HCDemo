package com.homecaravan.android.consumer.consumermvp.searchmvp;


import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.CBSApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseAllSearch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetListSearchPresenter {
    private GetListSearchView mView;
    private Call<ResponseAllSearch> mCall;

    public GetListSearchPresenter(GetListSearchView mView) {
        this.mView = mView;
    }

    public void getListSearch(String page, String prePage, String src, String uid) {
        CBSApi listSearch = ServiceGeneratorConsumer.createService(CBSApi.class);
        mCall = listSearch.getListSearches(page, prePage, src, uid);
        mCall.enqueue(new Callback<ResponseAllSearch>() {
            @Override
            public void onResponse(Call<ResponseAllSearch> call, Response<ResponseAllSearch> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.getListSearchSuccess(response.body().getData());
                    } else {
                        mView.getListSearchFail(response.body().getMessage());
                    }
                } else {
                    mView.getListSearchFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseAllSearch> call, Throwable t) {
                if (!call.isCanceled()) {
                    mView.getListSearchFail(R.string.error_server);
                }
            }
        });
    }

    public Call<ResponseAllSearch> getCall() {
        return mCall;
    }

    public void cancelGetListSearch() {
        mCall.cancel();
    }
}
