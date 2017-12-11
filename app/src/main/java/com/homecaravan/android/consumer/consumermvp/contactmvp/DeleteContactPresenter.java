package com.homecaravan.android.consumer.consumermvp.contactmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.ContactApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteContactPresenter {
    private DeleteContactView mView;

    public DeleteContactPresenter(DeleteContactView mView) {
        this.mView = mView;
    }

    public void deleteContact(String id) {
        ContactApi contactApi = ServiceGeneratorConsumer.createService(ContactApi.class);
        contactApi.deleteContact(id).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.deleteContactSuccess(R.string.delete_contact_success);
                    } else {
                        mView.deleteContactFail(response.body().getMessage());
                    }
                } else {
                    mView.deleteContactFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                mView.deleteContactFail(R.string.error_server);
            }
        });
    }
}
