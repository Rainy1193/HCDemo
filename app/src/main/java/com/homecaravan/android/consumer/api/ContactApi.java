package com.homecaravan.android.consumer.api;


import com.homecaravan.android.consumer.model.responseapi.BaseResponse;
import com.homecaravan.android.consumer.model.responseapi.ResponseContact;
import com.homecaravan.android.consumer.model.responseapi.ResponseListContact;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;


public interface ContactApi {

    @FormUrlEncoded
    @POST("contact_api/create")
    Call<ResponseContact> createContact(@Field("name") String name,
                                        @Field("phone") String phone,
                                        @Field("email") String email,
                                        @Field("avatar") String avatar,
                                        @Field("user") String user);

    @GET("contact_api/get/{id}")
    Call<ResponseContact> getContactById(@Path("id") String id);

    @FormUrlEncoded
    @POST("contact_api/search")
    Call<ResponseListContact> searchContact(@Field("text") String text);

    @FormUrlEncoded
    @POST
    Call<ResponseContact> updateContact(@Url String url, @Field("name") String name,
                                        @Field("phone") String phone,
                                        @Field("email") String email,
                                        @Field("avatar") String avatar);

    @GET("contact_api/delete/{id}")
    Call<BaseResponse> deleteContact(@Path("id") String id);

    @GET("contact_api/list")
    Call<ResponseListContact> getListContact();
}
