package com.homecaravan.android.consumer.consumermvp.listingmvp;

import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.api.CBSApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseGetListingPrePage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetListingPrePagePresenter {
    private GetListingPrePageView mView;

    public GetListingPrePagePresenter(GetListingPrePageView mView) {
        this.mView = mView;
    }

    public void getListingPrePage(String id, String page) {
        CBSApi getListingPrePage = ServiceGeneratorConsumer.createService(CBSApi.class);
        getListingPrePage.getListingPrePage(Constants.getInstance().getURL_BASE_CONSUMER() + "v2/search_api/get_listings"
                + "/" + id + "/" + page).enqueue(new Callback<ResponseGetListingPrePage>() {
            @Override
            public void onResponse(Call<ResponseGetListingPrePage> call, Response<ResponseGetListingPrePage> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.getListingPrePageSuccess(response.body().getListingData().getData());
                    } else {
                        mView.getListingPrePageFail();
                    }
                } else {
                    mView.getListingPrePageFail();
                }
            }

            @Override
            public void onFailure(Call<ResponseGetListingPrePage> call, Throwable t) {
                mView.getListingPrePageFail();
            }
        });
    }
}
