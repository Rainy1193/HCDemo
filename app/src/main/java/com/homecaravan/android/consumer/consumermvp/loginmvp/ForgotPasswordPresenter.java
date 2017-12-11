package com.homecaravan.android.consumer.consumermvp.loginmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.api.UsersAPI;
import com.homecaravan.android.consumer.model.responseapi.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anh Dao on 11/13/2017.
 */

public class ForgotPasswordPresenter {
    private ForgotPasswordView mView;
    private Call<BaseResponse> mCall;

    public ForgotPasswordPresenter(ForgotPasswordView mView){
        this.mView = mView;
    }

    public void forgotPassword(final String emailOrPhone) {

        UsersAPI usersAPI = ServiceGeneratorConsumer.createService(UsersAPI.class);
        mCall = usersAPI.forgotPassword(emailOrPhone);
        mCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.forgotPasswordSuccess(response.body().getMessages().get(0).getText(), emailOrPhone);
                    } else {
                        mView.forgotPasswordFail(response.body().getMessages().get(0).getText());
                    }
                } else {
                    mView.serverError(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                if (!call.isCanceled()) {
                    mView.serverError(R.string.error_server);
                }
            }
        });
    }

    public Call<BaseResponse> getCall() {
        return mCall;
    }

    public void cancelCall() {
        mCall.cancel();
    }
}
