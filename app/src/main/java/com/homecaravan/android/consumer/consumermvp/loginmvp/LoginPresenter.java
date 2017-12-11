package com.homecaravan.android.consumer.consumermvp.loginmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.api.UsersAPI;
import com.homecaravan.android.consumer.model.responseapi.ResponseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {
    private LoginView mView;
    private Call<ResponseUser> mCall;

    public LoginPresenter(LoginView mView) {
        this.mView = mView;
    }

    public void login(final String emailOrPhone, String password) {

        UsersAPI usersAPI = ServiceGeneratorConsumer.createService(UsersAPI.class);
        mCall = usersAPI.loginUser(emailOrPhone, password, Constants.DEVICE_TOKEN);
        mCall.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.loginSuccess(response.body());
                    } else {
                        if (response.body().getMessages().get(0).getCode() == 11009) {
                            mView.accountNotYetActivated(R.string.error_account_not_yet_activated, emailOrPhone);
                        } else {
                            if (response.body().getMessages().size() != 0) {
                                mView.loginFail(response.body().getMessages().get(0).getText());
                            } else {
                                mView.loginFail(response.body().getMessage());
                            }
                        }
                    }
                } else {
                    mView.serverError(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                if (!call.isCanceled()) {
                    mView.serverError(R.string.error_server);
                }
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
