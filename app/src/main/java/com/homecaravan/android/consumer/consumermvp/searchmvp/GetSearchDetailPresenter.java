package com.homecaravan.android.consumer.consumermvp.searchmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.CBSApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseSearchDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetSearchDetailPresenter {
    private GetSearchDetailView mView;
    private Call<ResponseSearchDetail> mCall;

    public GetSearchDetailPresenter(GetSearchDetailView mView) {
        this.mView = mView;
    }

    public void getSearchDetail(String searchId) {

        CBSApi searchDetail = ServiceGeneratorConsumer.createService(CBSApi.class);
        mCall = searchDetail.getSearchDetail(searchId);
        mCall.enqueue(new Callback<ResponseSearchDetail>() {
            @Override
            public void onResponse(Call<ResponseSearchDetail> call, Response<ResponseSearchDetail> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.getSearchDetailSuccess(response.body().getSearchDetail());
                    } else {
                        if (response.body().getMessages().size() != 0) {
                            mView.getSearchDetailFail(response.body().getMessages().get(0).getText());
                        }
                    }
                } else {
                    mView.getSearchDetailFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseSearchDetail> call, Throwable t) {
                if (!call.isCanceled()) {
                    mView.getSearchDetailFail(R.string.error_server);
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
