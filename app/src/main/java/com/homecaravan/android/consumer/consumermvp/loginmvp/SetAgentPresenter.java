package com.homecaravan.android.consumer.consumermvp.loginmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.api.UsersAPI;
import com.homecaravan.android.consumer.model.responseapi.ResponseAgentInfomation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetAgentPresenter {
    private SetAgentView mView;

    public SetAgentPresenter(SetAgentView mView) {
        this.mView = mView;
    }

    public void setAgent(String uId, String agentId) {
        UsersAPI usersAPI = ServiceGeneratorConsumer.createService(UsersAPI.class);
        usersAPI.setAgent(uId, "", agentId).enqueue(new Callback<ResponseAgentInfomation>() {
            @Override
            public void onResponse(Call<ResponseAgentInfomation> call, Response<ResponseAgentInfomation> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.setAgentSuccess();
                    } else {
                        mView.setAgentFail(response.body().getMessage());
                    }
                } else {
                    mView.setAgentFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseAgentInfomation> call, Throwable t) {
                mView.setAgentFail(R.string.error_server);
            }
        });
    }
}
