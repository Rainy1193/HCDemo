package com.homecaravan.android.consumer.consumermvp.searchmvp;


import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.CBSApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumerString;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAddressPresenter {
    private SearchAddressView mView;
    private Call<String> mCall;

    public SearchAddressPresenter(SearchAddressView mView) {
        this.mView = mView;
    }

    public void searchAddress(String address) {
        CBSApi cbsApi = ServiceGeneratorConsumerString.createService(CBSApi.class);
        mCall = cbsApi.searchAddress(address);
        mCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    if (jsonObject.getBoolean("success")) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        mView.searchAddressSuccess(data);
                    } else {
                        mView.searchAddressFail(jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                mView.searchAddressFail(R.string.error_server);
            }
        });
    }

    public Call<String> getCall() {
        return mCall;
    }
}
