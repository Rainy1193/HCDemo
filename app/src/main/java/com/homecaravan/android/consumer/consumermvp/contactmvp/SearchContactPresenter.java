package com.homecaravan.android.consumer.consumermvp.contactmvp;


import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.ContactApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseListContact;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchContactPresenter {
    private SearchContactView mView;
    private Call<ResponseListContact> mCall;
    public SearchContactPresenter(SearchContactView mView) {
        this.mView = mView;
    }

    public void searchContact(String text) {
        ContactApi contactApi = ServiceGeneratorConsumer.createService(ContactApi.class);
        mCall=contactApi.searchContact(text);
        mCall.enqueue(new Callback<ResponseListContact>() {
            @Override
            public void onResponse(Call<ResponseListContact> call, Response<ResponseListContact> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.searchContactSuccess(response.body().getData());
                    } else {
                        mView.searchContactFail(response.body().getMessages().get(0).getText());
                    }
                } else {
                    mView.searchContactFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseListContact> call, Throwable t) {
                mView.searchContactFail(R.string.error_server);
            }
        });
    }

    public Call<ResponseListContact> getCall() {
        return mCall;
    }
}
