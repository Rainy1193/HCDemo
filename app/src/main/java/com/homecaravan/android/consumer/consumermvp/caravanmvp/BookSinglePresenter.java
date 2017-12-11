package com.homecaravan.android.consumer.consumermvp.caravanmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.CBSApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumerString;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookSinglePresenter {
    private BookSingleView mView;

    public BookSinglePresenter(BookSingleView mView) {
        this.mView = mView;
    }

    public void bookAppointment(String id, String day, String startTime, String endTime, String note, String method, String pageNumber, String token) {
        CBSApi cbsApi = ServiceGeneratorConsumerString.createService(CBSApi.class);
        cbsApi.bookAppointment(id, pageNumber, day, startTime, endTime, method, note, token).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        if (jsonObject.getString("success").equalsIgnoreCase("true")) {
                            mView.bookSuccess(jsonObject);
                        } else {
                            if (jsonObject.getString("code").equalsIgnoreCase("404")) {
                                mView.bookNotSucceed(jsonObject.getString("message"));
                            } else if (jsonObject.getString("code").equalsIgnoreCase("405")) {
                                mView.bookNotSucceedStatus(jsonObject.getString("message"));
                            } else {
                                mView.bookFail(jsonObject.getString("message"));
                            }
                        }
                    } catch (Exception e) {
                        mView.bookFail(R.string.error_server);
                    }
                } else {
                    mView.bookFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mView.bookFail(R.string.error_server);
            }
        });
    }

}
