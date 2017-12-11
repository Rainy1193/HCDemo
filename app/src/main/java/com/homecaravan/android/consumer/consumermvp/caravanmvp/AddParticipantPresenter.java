package com.homecaravan.android.consumer.consumermvp.caravanmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.CaravanApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseParticipants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddParticipantPresenter {
    private AddParticipantView mView;

    public AddParticipantPresenter(AddParticipantView mView) {
        this.mView = mView;
    }

    public void addParticipant(String idCaravan, String idParticipant,String email, String phone, String role, String firstName,
                               String lastName, String communication) {
        CaravanApi caravanApi = ServiceGeneratorConsumer.createService(CaravanApi.class);
        caravanApi.addParticipant(idCaravan, idParticipant,email, phone, role, firstName, lastName, communication).enqueue(new Callback<ResponseParticipants>() {
            @Override
            public void onResponse(Call<ResponseParticipants> call, Response<ResponseParticipants> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.addParticipantSuccess(response.body().getData());
                    } else {
                        mView.addParticipantFail();
                    }
                } else {
                    mView.addParticipantFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseParticipants> call, Throwable t) {
                mView.addParticipantFail(R.string.error_server);
            }
        });
    }
}
