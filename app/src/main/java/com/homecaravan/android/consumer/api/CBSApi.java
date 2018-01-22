package com.homecaravan.android.consumer.api;

import com.homecaravan.android.consumer.model.responseapi.BaseResponse;
import com.homecaravan.android.consumer.model.responseapi.ResponseAllSearch;
import com.homecaravan.android.consumer.model.responseapi.ResponseFavorite;
import com.homecaravan.android.consumer.model.responseapi.ResponseGetListingPrePage;
import com.homecaravan.android.consumer.model.responseapi.ResponseListingDetail;
import com.homecaravan.android.consumer.model.responseapi.ResponseListingDetailBook;
import com.homecaravan.android.consumer.model.responseapi.ResponseListings;
import com.homecaravan.android.consumer.model.responseapi.ResponseParticipant;
import com.homecaravan.android.consumer.model.responseapi.ResponseSearchDetail;
import com.homecaravan.android.consumer.model.responseapi.ResponseSearchMap;
import com.homecaravan.android.consumer.model.responseapi.ResponseVote;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface CBSApi {
    @Multipart
    @POST("search_api/save_search")
    Call<ResponseSearchDetail> saveSearch(@PartMap Map<String, RequestBody> params);

    @GET("search_api/get_searches")
    Call<ResponseAllSearch> getListSearches(@Query("p") String page, @Query("pre") String prePage, @Query("src") String src, @Query("uid") String uid);

    @Multipart
    @GET("search_api/search")
    Call<ResponseSearchMap> searchGet(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("search_api/search")
    Call<ResponseSearchMap> search(@PartMap Map<String, RequestBody> params);

    @GET("search_api/detail/{id}")
    Call<ResponseSearchDetail> getSearchDetail(@Path("id") String id);

    @GET
    Call<ResponseGetListingPrePage> getListingPrePage(@Url String url);

    @GET
    Call<ResponseParticipant> getParticipantPrePage(@Url String url);

    @FormUrlEncoded
    @POST("search_api/add_user")
    Call<ResponseSearchDetail> addUser(@Field("first_name") String firstName, @Field("last_name") String lastName,
                                       @Field("permission") String permission, @Field("weight") String weight,
                                       @Field("email") String email, @Field("id") String id,
                                       @Field("phone") String phone, @Field("uid") String uid);

    @FormUrlEncoded
    @POST("search_api/remove_user")
    Call<ResponseSearchDetail> removeUser(@Field("id") String id, @Field("user_id") String userId);

    @FormUrlEncoded
    @POST("search_api/update_user")
    Call<ResponseSearchDetail> updateUser(@Field("id") String id, @Field("user_id") String userId, @Field("weight") String weight, @Field("permission") String permission);


    @FormUrlEncoded
    @POST("search_api/trending")
    Call<ResponseListings> getTrending(@Field("limit") String limit);

    @FormUrlEncoded
    @POST("search_api/just_list")
    Call<ResponseListings> getJustList(@Field("limit") String limit);

    @FormUrlEncoded
    @POST("search_api/vote")
    Call<ResponseVote> voteListing(@Field("id") String id, @Field("lid") String lib, @Field("type") String type, @Field("reason") String reason, @Field("note") String note);

    @GET("listing/detail/{id}")
    Call<ResponseListingDetail> getListingDetail(@Path("id") String id);

    @GET("listing_api/favorite/{page}")
    Call<ResponseFavorite> getFavoriteListing(@Path("page") String page);

    @POST("listing_api/save_favorite")
    @FormUrlEncoded
    Call<BaseResponse> addFavorite(@Field("LISTING_ID") String listingId);

    @POST("listing_api/detail")
    @FormUrlEncoded
    Call<ResponseListingDetailBook> getListingDetailBook(@Field("LISTING_ID") String listingId);

    @POST("appointment_booking_api/book_appointment")
    @FormUrlEncoded
    Call<String> bookAppointment(@Field("LISTING_ID") String listingId, @Field("PAGE_NUMBER") String page,
                                 @Field("DAY_BOOKING") String dayBook, @Field("DAY_END_HOUR_MINUTE") String startTime,
                                 @Field("DAY_START_HOUR_MINUTE") String endTime, @Field("COMMUNICATION_METHOD") String method,
                                 @Field("APPT_NOTE") String note, @Field("TOKEN") String token);

    @POST("suggestion_api/submit_ast")
    @FormUrlEncoded
    Call<String> submit(@Field("LISTING_ID") String id,
                        @Field("EVENTS") String events,
                        @Field("APPT_NOTE") String appt_note,
                        @Field("TOKEN") String token,
                        @Field("COMMUNICATION_METHOD") String COMMUNICATION_METHOD);
    @POST("search_api/archive_search")
    @FormUrlEncoded
    Call<BaseResponse> archiveSearch(@Field("search_id") String id);
}
