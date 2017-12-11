package com.homecaravan.android.consumer.api;

import com.homecaravan.android.consumer.model.responseapi.ResponseAddListing;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SellerApi {
    @FormUrlEncoded
    @POST("listing_api/add_listing_seller")
    Call<ResponseAddListing> addListing(@Field("address") String address,
                                        @Field("full_name") String fullName,
                                        @Field("title") String title);
}
