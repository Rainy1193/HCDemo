package com.homecaravan.android.consumer.consumermvp.contactmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.ContactApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseContact;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetContactPresenter {
    private GetContactView mView;

    public GetContactPresenter(GetContactView mView) {
        this.mView = mView;
    }

    public void getContact(String id) {
        ContactApi contactApi = ServiceGeneratorConsumer.createService(ContactApi.class);
        contactApi.getContactById(id).enqueue(new Callback<ResponseContact>() {
            @Override
            public void onResponse(Call<ResponseContact> call, Response<ResponseContact> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.getContactSuccess(response.body().getData());
                    } else {
                        mView.getContactFail(response.body().getMessage());
                    }
                } else {
                    mView.getContactFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseContact> call, Throwable t) {
                mView.getContactFail(R.string.error_server);
            }
        });
    }
}
