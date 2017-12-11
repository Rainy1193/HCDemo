package com.homecaravan.android.consumer.consumermvp.loginmvp;

import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.api.UsersAPI;
import com.homecaravan.android.consumer.model.responseapi.ResponseAgentInfomation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoveAgentPresenter {
    private RemoveAgentView mView;

    public RemoveAgentPresenter(RemoveAgentView mView) {
        this.mView = mView;
    }

    public void removeAgent() {
        UsersAPI usersAPI = ServiceGeneratorConsumer.createService(UsersAPI.class);
        usersAPI.removeAgent().enqueue(new Callback<ResponseAgentInfomation>() {
            @Override
            public void onResponse(Call<ResponseAgentInfomation> call, Response<ResponseAgentInfomation> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.removeAgentSuccess();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseAgentInfomation> call, Throwable t) {

            }
        });
    }
}
