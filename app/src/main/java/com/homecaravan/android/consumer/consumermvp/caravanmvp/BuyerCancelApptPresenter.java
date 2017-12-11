package com.homecaravan.android.consumer.consumermvp.caravanmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.CaravanApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseShowingAppt;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anh Dao on 10/23/2017.
 */

public class BuyerCancelApptPresenter {
    private BuyerCancelApptView mView;

    public BuyerCancelApptPresenter(BuyerCancelApptView mView) {
        this.mView = mView;
    }

    public void buyerCancelAppointment(String apptId) {
        CaravanApi caravanApi = ServiceGeneratorConsumer.createService(CaravanApi.class);
        caravanApi.buyerCancelAppointment(apptId).enqueue(new Callback<ResponseShowingAppt>() {
            @Override
            public void onResponse(Call<ResponseShowingAppt> call, Response<ResponseShowingAppt> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.cancelApptSuccess(response.body().getAppointmentShowings());
                    } else {
                        mView.cancelApptFail();
                    }
                } else {
                    mView.cancelApptFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseShowingAppt> call, Throwable t) {
                mView.cancelApptFail(R.string.error_server);
            }
        });
    }
}
