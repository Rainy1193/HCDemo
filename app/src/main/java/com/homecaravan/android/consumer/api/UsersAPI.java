package com.homecaravan.android.consumer.api;

import com.homecaravan.android.consumer.model.responseapi.BaseResponse;
import com.homecaravan.android.consumer.model.responseapi.ResponseAgentInfomation;
import com.homecaravan.android.consumer.model.responseapi.ResponseChangePassword;
import com.homecaravan.android.consumer.model.responseapi.ResponseCompanies;
import com.homecaravan.android.consumer.model.responseapi.ResponseFeatured;
import com.homecaravan.android.consumer.model.responseapi.ResponseRegister;
import com.homecaravan.android.consumer.model.responseapi.ResponseUploadAvatar;
import com.homecaravan.android.consumer.model.responseapi.ResponseUser;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UsersAPI {
    @FormUrlEncoded
    @POST("users/login")
    Call<ResponseUser> loginUser(@Field("username") String USERNAME, @Field("password") String PASSWORD,
                                 @Field("device_token") String DEVICE_TOKEN);

    @FormUrlEncoded
    @POST("users/change_password")
    Call<ResponseChangePassword> changePassword(@Field("password") String oldPassword, @Field("new_password") String newPassword,
                                                @Field("new_password_confirm") String ConfirmPassword);

    @FormUrlEncoded
    @POST("users/forgot_password")
    Call<BaseResponse> forgotPassword(@Field("identity") String emailOrPhone);

    @FormUrlEncoded
    @POST("users/verify")
    Call<ResponseUser> verifyAccount(@Field("uid") String userId, @Field("identity") String emailOrPhone,
                                     @Field("code") String code, @Field("device_token") String DEVICE_TOKEN);

    @FormUrlEncoded
    @POST("users/resend_active_code")
    Call<ResponseUser> resendCode(@Field("identity") String emailOrPhone);

    @FormUrlEncoded
    @POST("users/create")
    Call<ResponseRegister> register(@Field("first_name") String firstName, @Field("last_name") String lastName,
                                    @Field("email_phone") String emailPhone, @Field("password") String password);

    @FormUrlEncoded
    @POST("users/login_by_facebook")
    Call<ResponseUser> registerFacebook(@Field("name") String name, @Field("identity") String identity,
                                        @Field("avatar") String avatar, @Field("fid") String fid, @Field("device_token") String driveToken);

    @FormUrlEncoded
    @POST("users/login_by_linkedin")
    Call<ResponseUser> registerLinkedin(@Field("first_name") String firstName, @Field("last_name") String lastName,
                                        @Field("email") String emailPhone, @Field("lkid") String lkid,
                                        @Field("picture_url") String picture, @Field("device_token") String driveToken);


    @GET("users_api/featured")
    Call<ResponseFeatured> getFeatured();


    @FormUrlEncoded
    @POST("users/set_agent")
    Call<ResponseAgentInfomation> setAgent(@Field("uid") String uid, @Field("identity") String identity, @Field("agent_id") String agentId);

    @FormUrlEncoded
    @POST("users/remove_agent")
    Call<ResponseAgentInfomation> removeAgent();

    @POST("users/get_companies")
    Call<ResponseCompanies> getCompanies(@Query("value") String value);

    @FormUrlEncoded
    @POST("users/update")
    Call<BaseResponse> updateUser(@Field("first_name") String firstName, @Field("last_name") String lastName,
                                  @Field("phone") String phone, @Field("email") String email, @Field("avatar") String avatar,
                                  @Field("company[0][mapping_id]") String companyMappingId, @Field("company[0][id]") String companyId,
                                  @Field("company[0][name]") String companyName, @Field("company[0][title]") String companyTitle,
                                  @Field("extra[address]") String extraAddress, @Field("extra[url]") String extraUrl,
                                  @Field("extra[video]") String extraVideo, @Field("extra[facebook]") String extraFacebook,
                                  @Field("extra[intro]") String extraIntro, @Field("extra[about_me]") String extraAboutMe);

    @FormUrlEncoded
    @POST("users/update")
    Call<BaseResponse> updateUser(@Field("first_name") String firstName, @Field("last_name") String lastName,
                                  @Field("phone") String phone, @Field("email") String email, @Field("avatar") String avatar,
                                  @Field("extra[address]") String extraAddress, @Field("extra[url]") String extraUrl,
                                  @Field("extra[video]") String extraVideo, @Field("extra[facebook]") String extraFacebook,
                                  @Field("extra[intro]") String extraIntro, @Field("extra[about_me]") String extraAboutMe);

    @Multipart
    @POST("upload/user_img")
    Call<ResponseUploadAvatar> uploadAvatar(@Part("type") RequestBody type, @Part MultipartBody.Part avatar);

    @FormUrlEncoded
    @POST("users/update_settings")
    Call<BaseResponse> updateUserSettings(@Field("notifications") String notifications,
                                          @Field("new_homes") String newHomes,
                                          @Field("email_sms_notifications") String emailSmsNotifications);

    @FormUrlEncoded
    @POST("users_api/invite_user_by_emails")
    Call<BaseResponse> inviteEmails(@Field("emails") String emails, @Field("message") String message);

    @FormUrlEncoded
    @POST("users_api/invite_user_by_phones")
    Call<BaseResponse> invitePhones(@Field("phones") String phones, @Field("message") String message);
}
