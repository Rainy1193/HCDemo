package com.homecaravan.android.consumer.consumermvp.searchmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.CBSApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseSearchDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoveParticipantSearchPresenter {
    private RemoveParticipantSearchView mView;

    public RemoveParticipantSearchPresenter(RemoveParticipantSearchView mView) {
        this.mView = mView;
    }

    public void removeParticipant(String id, String uid) {
        CBSApi cbsApi = ServiceGeneratorConsumer.createService(CBSApi.class);
        cbsApi.removeUser(id, uid).enqueue(new Callback<ResponseSearchDetail>() {
            @Override
            public void onResponse(Call<ResponseSearchDetail> call, Response<ResponseSearchDetail> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.removeParticipantSuccess(response.body().getSearchDetail());
                    } else {
                        mView.removeParticipantFail(response.body().getMessage());
                    }
                } else {
                    mView.removeParticipantFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseSearchDetail> call, Throwable t) {
                mView.removeParticipantFail(R.string.error_server);
            }
        });
    }
}
