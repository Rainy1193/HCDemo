package com.homecaravan.android.consumer.consumermvp.loginmvp;

import android.support.annotation.NonNull;

import com.homecaravan.android.R;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.api.UsersAPI;
import com.homecaravan.android.consumer.model.responseapi.ResponseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anh Dao on 11/9/2017.
 */

public class VerifyAccountPresenter {
    private VerifyAccountView mView;
    private Call<ResponseUser> mCall;

    public VerifyAccountPresenter (VerifyAccountView mView){
        this.mView = mView;
    }

    public void verifyAccount(String userId, final String emailOrPhone, String code){
        UsersAPI usersAPI = ServiceGeneratorConsumer.createService(UsersAPI.class);
        mCall = usersAPI.verifyAccount(userId, emailOrPhone, code, Constants.DEVICE_TOKEN);
        mCall.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(@NonNull Call<ResponseUser> call, @NonNull Response<ResponseUser> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.verifySuccess(response.body().getUserData());
                    } else if(response.body().getMessages().get(0).getCode() == 11007) {
                        mView.accountActivated(R.string.error_account_activated, emailOrPhone);
                    } else{
                        mView.verifyFail(response.body().getMessages().get(0).getText());
                    }
                } else {
                    mView.serverError(R.string.error_server);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseUser> call, @NonNull Throwable t) {
                mView.serverError(R.string.error_server);
            }
        });
    }

    public void resendCode(final String emailOrPhone){
        UsersAPI usersAPI = ServiceGeneratorConsumer.createService(UsersAPI.class);
        mCall = usersAPI.resendCode(emailOrPhone);
        mCall.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(@NonNull Call<ResponseUser> call, @NonNull Response<ResponseUser> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.resendCodeSuccess(response.body().getMessages().get(0).getText());
                    } else if(response.body().getMessages().get(0).getCode() == 10006) {
                        mView.accountActivated(R.string.error_account_activated, emailOrPhone);
                    } else {
                        mView.resendCodeFail(response.body().getMessages().get(0).getText());
                    }
                } else {
                    mView.serverError(R.string.error_server);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseUser> call, @NonNull Throwable t) {
                mView.serverError(R.string.error_server);
            }
        });
    }

    public Call<ResponseUser> getCall() {
        return mCall;
    }

    public void cancelCall() {
        mCall.cancel();
    }
}
