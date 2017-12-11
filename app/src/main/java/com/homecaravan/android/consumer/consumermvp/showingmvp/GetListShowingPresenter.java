package com.homecaravan.android.consumer.consumermvp.showingmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.api.ShowingApi;
import com.homecaravan.android.consumer.model.responseapi.ResponseShowingAppt;
import com.homecaravan.android.consumer.model.responseapi.ResponseShowingCaravan;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetListShowingPresenter {

    private GetListShowingView mView;

    public GetListShowingPresenter(GetListShowingView mView) {
        this.mView = mView;
    }

    public void getListShowing(String startTime, String endTime, String type) {
        if (type.equalsIgnoreCase("single")) {
            ShowingApi showingApi = ServiceGeneratorConsumer.createService(ShowingApi.class);
            showingApi.getListShowingAppointment(startTime, endTime, type).enqueue(new Callback<ResponseShowingAppt>() {
                @Override
                public void onResponse(Call<ResponseShowingAppt> call, Response<ResponseShowingAppt> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess()) {
                            mView.getShowingApptSuccess(response.body().getAppointmentShowings());
                        } else {
                            mView.getShowingApptFail(response.body().getMessage());
                        }
                    } else {
                        mView.getShowingApptFail(R.string.error_server);
                    }
                }

                @Override
                public void onFailure(Call<ResponseShowingAppt> call, Throwable t) {
                    mView.getShowingApptFail(R.string.error_server);
                }
            });
        } else {
            ShowingApi showingApi = ServiceGeneratorConsumer.createService(ShowingApi.class);
            showingApi.getListShowingCaravan(startTime, endTime, type).enqueue(new Callback<ResponseShowingCaravan>() {
                @Override
                public void onResponse(Call<ResponseShowingCaravan> call, Response<ResponseShowingCaravan> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess()) {
                            mView.getShowingCaravanSuccess(response.body().getCaravanShowings());
                        } else {
                            mView.getShowingCaravanFail(response.body().getMessage());
                        }
                    } else {
                        mView.getShowingCaravanFail(R.string.error_server);
                    }
                }

                @Override
                public void onFailure(Call<ResponseShowingCaravan> call, Throwable t) {
                    mView.getShowingCaravanFail(R.string.error_server);
                }
            });
        }
    }

}
