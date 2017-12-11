package com.homecaravan.android.consumer.consumermvp.contactmvp;


import com.homecaravan.android.R;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.api.ContactApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseContact;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateContactPresenter {
    private UpdateContactView mView;

    public UpdateContactPresenter(UpdateContactView mView) {
        this.mView = mView;
    }

    public void updateContact(String id, String name, String email, String phone, String avatar) {
        ContactApi contactApi = ServiceGeneratorConsumer.createService(ContactApi.class);
        contactApi.updateContact(Constants.getInstance().getURL_BASE_CONSUMER() + "v2/contact_api/" + id, name, email, phone, avatar).enqueue(new Callback<ResponseContact>() {
            @Override
            public void onResponse(Call<ResponseContact> call, Response<ResponseContact> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.updateContactSuccess(response.body().getData());
                    } else {
                        mView.updateContactFail(response.message());
                    }
                } else {
                    mView.updateContactFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseContact> call, Throwable t) {
                mView.updateContactFail(R.string.error_server);
            }
        });
    }
}
