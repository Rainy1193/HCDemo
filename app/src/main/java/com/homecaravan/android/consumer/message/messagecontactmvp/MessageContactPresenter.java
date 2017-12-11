package com.homecaravan.android.consumer.message.messagecontactmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.ContactApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseListContact;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageContactPresenter {

    private IMessageContactView mView;

    public MessageContactPresenter(IMessageContactView mView) {
        this.mView = mView;
    }

    public void getMessageContact() {
        ContactApi contactApi = ServiceGeneratorConsumer.createService(ContactApi.class);
        contactApi.getListContact().enqueue(new Callback<ResponseListContact>() {
            @Override
            public void onResponse(Call<ResponseListContact> call, Response<ResponseListContact> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.getMessageContactSuccess(response.body().getData());
                    } else {
                        mView.getMessageContactFail(response.body().getMessages().get(0).getText());
                    }
                } else {
                    mView.serverError(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseListContact> call, Throwable t) {
                mView.serverError(R.string.error_server);
            }
        });
    }
}
