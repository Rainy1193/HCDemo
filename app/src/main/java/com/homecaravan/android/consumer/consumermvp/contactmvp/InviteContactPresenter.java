package com.homecaravan.android.consumer.consumermvp.contactmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.api.UsersAPI;
import com.homecaravan.android.consumer.model.responseapi.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InviteContactPresenter {
    private InviteContactView mView;

    public InviteContactPresenter(InviteContactView mView) {
        this.mView = mView;
    }

    public void inviteContact(String data, boolean email, String message) {
        UsersAPI usersAPI = ServiceGeneratorConsumer.createService(UsersAPI.class);
        if (email) {
            usersAPI.inviteEmails(data, message).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess()) {
                            mView.inviteSuccess();
                        } else {
                            mView.inviteFail(response.message());
                        }
                    } else {
                        mView.inviteFail(R.string.error_server);
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    mView.inviteFail(R.string.error_server);
                }
            });
        } else {
            usersAPI.invitePhones(data, message).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess()) {
                            mView.inviteSuccess();
                        } else {
                            mView.inviteFail(response.message());
                        }
                    } else {
                        mView.inviteFail(R.string.error_server);
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    mView.inviteFail(R.string.error_server);
                }
            });
        }
    }
}
