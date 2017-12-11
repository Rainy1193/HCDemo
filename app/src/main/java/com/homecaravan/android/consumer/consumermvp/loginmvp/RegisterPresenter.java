package com.homecaravan.android.consumer.consumermvp.loginmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.api.UsersAPI;
import com.homecaravan.android.consumer.model.responseapi.ResponseRegister;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter {
    private RegisterView mView;

    public RegisterPresenter(RegisterView mView) {
        this.mView = mView;
    }

    public void register(String firstName, String lastName, String emailOrPhone, String password) {
        UsersAPI usersAPI = ServiceGeneratorConsumer.createService(UsersAPI.class);
        usersAPI.register(firstName, lastName, emailOrPhone, password).enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.registerSuccess(R.string.register_response, response.body());
                    } else {
                        if (response.body().getMessages().size() != 0) {
                            mView.registerFail(response.body().getMessages().get(0).getText());
                        } else {
                            mView.registerFail(response.body().getMessage());
                        }
                    }
                } else {
                    mView.serverError(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                mView.serverError(R.string.error_server);
            }
        });
    }
}
