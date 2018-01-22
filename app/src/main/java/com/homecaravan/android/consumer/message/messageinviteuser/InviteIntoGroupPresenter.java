package com.homecaravan.android.consumer.message.messageinviteuser;

import android.util.Log;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.MessageApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.homecaravan.android.HomeCaravanApplication.TAG;

/**
 * Created by Anh Dao on 11/29/2017.
 */

public class InviteIntoGroupPresenter {
    private IInviteIntoGroupView mView;

    public InviteIntoGroupPresenter(IInviteIntoGroupView mView) {
        this.mView = mView;
    }

    public void inviteIntoGroup(String threadId, String usersId) {
        Log.e(TAG, "inviteIntoGroup: threadId: " + threadId + " usersId: " + usersId);
        MessageApi messageApi = ServiceGeneratorConsumer.createService(MessageApi.class);
        messageApi.messageAddParticipants(threadId, usersId)
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                                mView.invitedSuccess();
                            } else {
                                mView.invitedFail();
                            }
                        } else {
                            mView.serverError(R.string.error_server);
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        mView.serverError(R.string.error_server);
                    }
                });
    }

}
