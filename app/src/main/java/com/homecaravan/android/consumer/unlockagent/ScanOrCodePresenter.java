package com.homecaravan.android.consumer.unlockagent;

import android.util.Log;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.api.UnlockAgentApi;
import com.homecaravan.android.consumer.consumerbase.ConsumerUser;
import com.homecaravan.android.consumer.model.responseapi.ResponseAgentInfomation;
import com.homecaravan.android.consumer.model.responseapi.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.homecaravan.android.HomeCaravanApplication.TAG;

/**
 * Created by Anh Dao on 11/3/2017.
 */

public class ScanOrCodePresenter {
    private IUnlockApi mView;

    public ScanOrCodePresenter(IUnlockApi mView) {
        this.mView = mView;
    }

    public ScanOrCodePresenter() {
    }

    public void findAgent(String code) {
        UnlockAgentApi api = ServiceGeneratorConsumer.createService(UnlockAgentApi.class);
        api.findAgent(code).enqueue(new Callback<ResponseAgentInfomation>() {
            @Override
            public void onResponse(Call<ResponseAgentInfomation> call, Response<ResponseAgentInfomation> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.findAgentSuccess(response.body().getData());
                    } else {
                        mView.findAgentFail(response.body().getMessages().get(0).getText());
                    }
                } else {
                    mView.serverError(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseAgentInfomation> call, Throwable t) {
                mView.serverError(R.string.error_server);
            }
        });
    }

    public void setAgent(String myUid, final User agent){

        UnlockAgentApi api = ServiceGeneratorConsumer.createService(UnlockAgentApi.class);
        api.setAgent(myUid, "", agent.getId()).enqueue(new Callback<ResponseAgentInfomation>() {
            @Override
            public void onResponse(Call<ResponseAgentInfomation> call, Response<ResponseAgentInfomation> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        Log.e(TAG, "setAgent: "+response.body().getData().toString());
                        if(agent != null){
                            ConsumerUser.getInstance().getData().setHasAgent("yes");
                            ConsumerUser.getInstance().getData().setAgentId(agent.getAgent().getId());
                            ConsumerUser.getInstance().getData().setAgentFirstName(agent.getAgent().getFirstName());
                            ConsumerUser.getInstance().getData().setAgentLastName(agent.getAgent().getLastName());
                            ConsumerUser.getInstance().getData().setAgentFullName(agent.getAgent().getFullName());
                            ConsumerUser.getInstance().getData().setAgentPnUid(agent.getAgent().getPnUid());
                            ConsumerUser.getInstance().getData().setAgentPhoto(agent.getAgent().getAvatar());
                        }
                    } else {
                        Log.e(TAG, "setAgent: Fail");
                    }
                } else {
                    Log.e(TAG, "setAgent: isSuccessful fail");
                }
            }

            @Override
            public void onFailure(Call<ResponseAgentInfomation> call, Throwable t) {
                Log.e(TAG, "setAgent: server fail");
            }
        });
    }
}
