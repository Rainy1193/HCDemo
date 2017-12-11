package com.homecaravan.android.consumer.consumermvp.searchmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.CBSApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseSearchDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateParticipantPresenter {
    private UpdateParticipantView mView;

    public UpdateParticipantPresenter(UpdateParticipantView mView) {
        this.mView = mView;
    }

    public void updateParticipant( String idSearch, String uid, String weight,String permission) {
        CBSApi cbsApi = ServiceGeneratorConsumer.createService(CBSApi.class);
        cbsApi.updateUser(idSearch, uid, weight, permission).enqueue(new Callback<ResponseSearchDetail>() {
            @Override
            public void onResponse(Call<ResponseSearchDetail> call, Response<ResponseSearchDetail> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.updateParticipantSuccess(response.body().getSearchDetail());
                    } else {
                        mView.updateParticipantFail(response.body().getMessage());
                    }
                } else {
                    mView.updateParticipantFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseSearchDetail> call, Throwable t) {
                mView.updateParticipantFail(R.string.error_server);
            }
        });
    }
}
