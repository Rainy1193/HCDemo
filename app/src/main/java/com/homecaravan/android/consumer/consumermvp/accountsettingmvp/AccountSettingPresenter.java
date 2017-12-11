package com.homecaravan.android.consumer.consumermvp.accountsettingmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.api.UsersAPI;
import com.homecaravan.android.consumer.model.responseapi.ResponseChangePassword;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anh Dao on 11/8/2017.
 */

public class AccountSettingPresenter {
    private IAccountSettingView mView;

    public AccountSettingPresenter(IAccountSettingView mView) {
        this.mView = mView;
    }

    public void changePassword(String oldPassword, String newPassword, String confirmPassword){
        UsersAPI usersAPI = ServiceGeneratorConsumer.createService(UsersAPI.class);
        usersAPI.changePassword(oldPassword, newPassword, confirmPassword)
                .enqueue(new Callback<ResponseChangePassword>() {
                    @Override
                    public void onResponse(Call<ResponseChangePassword> call, Response<ResponseChangePassword> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                                String token = response.body().getData().getToken();
                                mView.changePasswordSuccess(token);
                            } else {
                                mView.changePasswordFail(response.body().getMessages().get(0).getText());
                            }
                        } else {
                            mView.changePasswordFail(R.string.error_server);
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseChangePassword> call, Throwable t) {
                        mView.changePasswordFail(R.string.error_server);
                    }
                });
    }
}
