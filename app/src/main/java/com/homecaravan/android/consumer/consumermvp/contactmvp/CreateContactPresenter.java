package com.homecaravan.android.consumer.consumermvp.contactmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.ContactApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseContact;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateContactPresenter {
    private CreateContactView mView;

    public CreateContactPresenter(CreateContactView mView) {
        this.mView = mView;
    }

    public void createContact(String name, String email, String phone, String avatar, String user) {
        ContactApi contactApi = ServiceGeneratorConsumer.createService(ContactApi.class);
        contactApi.createContact(name, email, phone, avatar, user).enqueue(new Callback<ResponseContact>() {
            @Override
            public void onResponse(Call<ResponseContact> call, Response<ResponseContact> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.createContactSuccess(response.body().getData());
                    } else {
                        mView.createContactFail(response.body().getMessage());
                    }
                } else {
                    mView.createContactFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseContact> call, Throwable t) {
                mView.createContactFail(R.string.error_server);
            }
        });

    }
}
