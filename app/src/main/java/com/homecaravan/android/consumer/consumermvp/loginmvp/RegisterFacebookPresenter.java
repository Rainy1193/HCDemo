package com.homecaravan.android.consumer.consumermvp.loginmvp;


import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.api.UsersAPI;
import com.homecaravan.android.consumer.model.responseapi.ResponseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFacebookPresenter {

    private RegisterFacebookView mView;

    public RegisterFacebookPresenter(RegisterFacebookView mView) {
        this.mView = mView;
    }

    public void register(String name, String identity, String avatar, String fid, String driveToken) {
        UsersAPI usersAPI = ServiceGeneratorConsumer.createService(UsersAPI.class);
        usersAPI.registerFacebook(name, identity, avatar, fid, driveToken).enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.registerFacebookSuccess(response.body());
                    } else {
                        if (response.body().getMessages().size() != 0) {
                            mView.registerFacebookFail(response.body().getMessages().get(0).getText());
                        } else {
                            mView.registerFacebookFail(response.body().getMessage());
                        }
                    }
                } else {
                    mView.serverError(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                mView.serverError(R.string.error_server);
            }
        });
    }
}
