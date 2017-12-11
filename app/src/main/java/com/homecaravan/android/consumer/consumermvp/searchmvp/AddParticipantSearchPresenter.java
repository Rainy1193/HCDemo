package com.homecaravan.android.consumer.consumermvp.searchmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.CBSApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseSearchDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddParticipantSearchPresenter {
    private AddParticipantSearchView mView;

    public AddParticipantSearchPresenter(AddParticipantSearchView mView) {
        this.mView = mView;
    }

    public void addParticipant(String firstName, String lastName, String email, String phone,
                               String permission, String weight, String idSearch, String uid) {
        CBSApi cbsApi = ServiceGeneratorConsumer.createService(CBSApi.class);
        cbsApi.addUser(firstName, lastName, permission, weight, email, idSearch, phone, uid).enqueue(new Callback<ResponseSearchDetail>() {
            @Override
            public void onResponse(Call<ResponseSearchDetail> call, Response<ResponseSearchDetail> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.addParticipantSuccess(response.body().getSearchDetail());
                    } else {
                        mView.addParticipantFail(response.body().getMessage());
                    }
                } else {
                    mView.addParticipantFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseSearchDetail> call, Throwable t) {
                mView.addParticipantFail(R.string.error_server);
            }
        });
    }
}
