package com.homecaravan.android.consumer.consumermvp.caravanmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.CBSApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumerString;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitAstPresenter {
    private SubmitAstView mView;

    public SubmitAstPresenter(SubmitAstView mView) {
        this.mView = mView;
    }

    public void submit(String listing_id, String events, String appt_note, String token, String method) {
        CBSApi suggestionBook = ServiceGeneratorConsumerString.createService(CBSApi.class);
        suggestionBook.submit(listing_id, events, appt_note, token, method).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        if (jsonObject.getString("success").equalsIgnoreCase("true")) {
                            mView.suggestionsubmitSucceed(jsonObject);
                        } else {
                            if (jsonObject.getString("code").equalsIgnoreCase("404")) {
                                mView.suggestionsubmitFailed(jsonObject.getString("message"));
                            } else {
                                mView.suggestionsubmitFailed(jsonObject.getString("message"));
                            }
                        }
                    } catch (Exception e) {
                        mView.suggestionsubmitFailed(R.string.error_server);

                    }
                } else {
                    mView.suggestionsubmitFailed(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mView.suggestionsubmitFailed(R.string.error_server);
            }
        });

    }
}
