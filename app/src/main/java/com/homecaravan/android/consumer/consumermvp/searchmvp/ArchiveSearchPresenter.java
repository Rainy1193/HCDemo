package com.homecaravan.android.consumer.consumermvp.searchmvp;


import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.CBSApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArchiveSearchPresenter {
    private ArchiveSearchView mView;

    public ArchiveSearchPresenter(ArchiveSearchView mView) {
        this.mView = mView;
    }

    public void archiveSearch(String searchId) {
        CBSApi cbsApi = ServiceGeneratorConsumer.createService(CBSApi.class);
        cbsApi.archiveSearch(searchId).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.archiveSearchSuccess(response.message());
                    } else {
                        mView.archiveSearchFail(response.message());
                    }
                } else {
                    mView.archiveSearchFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                mView.archiveSearchFail(R.string.error_server);
            }
        });
    }
}
