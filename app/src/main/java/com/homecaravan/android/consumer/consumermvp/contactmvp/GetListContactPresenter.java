package com.homecaravan.android.consumer.consumermvp.contactmvp;


import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.ContactApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseListContact;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetListContactPresenter {
    private GetListContactView mView;

    public GetListContactPresenter(GetListContactView mView) {
        this.mView = mView;
    }

    public void getListContact() {
        ContactApi contactApi = ServiceGeneratorConsumer.createService(ContactApi.class);
        contactApi.getListContact().enqueue(new Callback<ResponseListContact>() {
            @Override
            public void onResponse(Call<ResponseListContact> call, Response<ResponseListContact> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.getListContactSuccess(response.body().getData());
                    } else {
                        mView.getListContactFail(response.body().getMessage());
                    }
                } else {
                    mView.getListContactFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseListContact> call, Throwable t) {
                mView.getListContactFail(R.string.error_server);
            }
        });
    }
}
