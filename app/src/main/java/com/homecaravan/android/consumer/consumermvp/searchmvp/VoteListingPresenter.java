package com.homecaravan.android.consumer.consumermvp.searchmvp;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.CBSApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseVote;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoteListingPresenter {
    private VoteListingView mView;

    public VoteListingPresenter(VoteListingView mView) {
        this.mView = mView;
    }

    public void voteListing(String id, String lib, String type, String reason, String note) {
        CBSApi cbsApi = ServiceGeneratorConsumer.createService(CBSApi.class);
        cbsApi.voteListing(id, lib, type, reason, note).enqueue(new Callback<ResponseVote>() {
            @Override
            public void onResponse(Call<ResponseVote> call, Response<ResponseVote> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.voteSuccess(response.body().getListingFull());
                    } else {
                        mView.voteFail(response.body().getMessages().get(0).getText());
                    }
                } else {
                    mView.voteFail(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseVote> call, Throwable t) {
                mView.voteFail(R.string.error_server);
            }
        });
    }
}
