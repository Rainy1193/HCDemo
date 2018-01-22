package com.homecaravan.android.consumer.message.messagegetthreadidmvp;

import android.util.Log;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.MessageApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseMessageGetThreadId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.homecaravan.android.HomeCaravanApplication.TAG;

/**
 * Created by Anh Dao on 10/24/2017.
 * Get threadId by caravanId, listingId, appoitmentId, ArrayList<String> userId
 */

public class GetThreadIdPresenter {
    private IGetThreadIdView mView;

    public GetThreadIdPresenter(IGetThreadIdView mView) {
        this.mView = mView;
    }

    public void getThreadIdAtCaravan(String caravanId, String listingId, String apptId, final String title, String teamIds, final int position) {
        Log.e(TAG, "getThreadId: caravanId:"+caravanId+" listingId: "+listingId +" apptId: "+apptId + " title: "+title +" teamIds: "+teamIds +" position: "+position );
        MessageApi messageApi = ServiceGeneratorConsumer.createService(MessageApi.class);
        messageApi.messageGetThreadId(apptId, listingId, caravanId, title, teamIds)
                .enqueue(new Callback<ResponseMessageGetThreadId>() {
                    @Override
                    public void onResponse(Call<ResponseMessageGetThreadId> call, Response<ResponseMessageGetThreadId> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isSuccess()) {
                                mView.getThreadIdAtCaravanSuccess(response.body(), position, title);
                                Log.e(TAG, "GetThreadIdPresenter: "+response.body().getThreadId());
                            } else {
                                Log.e(TAG, "GetThreadIdPresenter: FAIL threadId null");
                                mView.getThreadIdFail();
                            }
                        } else {
                            Log.e(TAG, "GetThreadIdPresenter: FAIL with message");
                            mView.getThreadIdFail(R.string.error_server);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseMessageGetThreadId> call, Throwable t) {
                        Log.e(TAG, "GetThreadIdPresenter: FAIL with message");
                        mView.getThreadIdFail(R.string.error_server);
                    }
                });
    }

    public void getThreadId(String caravanId, String listingId, String apptId, final String title, String teamIds) {
        Log.e(TAG, "getThreadId: caravanId:"+caravanId+" listingId: "+listingId +" apptId: "+apptId + " title: "+title +" teamIds: "+teamIds );
        MessageApi messageApi = ServiceGeneratorConsumer.createService(MessageApi.class);
        messageApi.messageGetThreadId(apptId, listingId, caravanId, title, teamIds)
                .enqueue(new Callback<ResponseMessageGetThreadId>() {
                    @Override
                    public void onResponse(Call<ResponseMessageGetThreadId> call, Response<ResponseMessageGetThreadId> response) {
                        if (response.isSuccessful()) {
                            if (response.body().isSuccess()) {
                                mView.getThreadIdSuccess(response.body(), title);
                                Log.e(TAG, "GetThreadIdPresenter: "+response.body().getThreadId());
                            } else {
                                Log.e(TAG, "GetThreadIdPresenter: FAIL threadId null");
                                mView.getThreadIdFail();
                            }
                        } else {
                            Log.e(TAG, "GetThreadIdPresenter: FAIL with message");
                            mView.getThreadIdFail(R.string.error_server);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseMessageGetThreadId> call, Throwable t) {
                        Log.e(TAG, "GetThreadIdPresenter: FAIL with message");
                        mView.getThreadIdFail(R.string.error_server);
                    }
                });
    }
}
