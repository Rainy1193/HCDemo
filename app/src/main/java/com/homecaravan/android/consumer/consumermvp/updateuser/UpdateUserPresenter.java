package com.homecaravan.android.consumer.consumermvp.updateuser;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.api.UsersAPI;
import com.homecaravan.android.consumer.model.responseapi.BaseResponse;
import com.homecaravan.android.consumer.model.responseapi.ResponseCompanies;
import com.homecaravan.android.consumer.model.responseapi.ResponseUploadAvatar;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anh Dao on 11/15/2017.
 */

public class UpdateUserPresenter {
    private UpdateUserView mView;

    public UpdateUserPresenter(UpdateUserView mView){
        this.mView = mView;
    }

    public void uploadAvatar(File imageFile){
        if (imageFile != null) {
            MediaType MEDIA_TYPE = MediaType.parse("image/*");
            RequestBody imageBody = RequestBody.create(MEDIA_TYPE, imageFile);
            MultipartBody.Part mPart = MultipartBody.Part.createFormData("photo", imageFile.getName(), imageBody);

            RequestBody type = RequestBody.create(MultipartBody.FORM, "avatar");
            UsersAPI usersAPI = ServiceGeneratorConsumer.createService(UsersAPI.class);
            usersAPI.uploadAvatar(type, mPart).enqueue(new Callback<ResponseUploadAvatar>() {
                @Override
                public void onResponse(Call<ResponseUploadAvatar> call, Response<ResponseUploadAvatar> response) {
                    if (response.isSuccessful()) {
                        if(response.body().getSuccess()){
                            mView.uploadAvatarSuccess(response.body().getData().getFileName());
                        }else{
                            mView.uploadAvatarFail(response.body().getMessages().get(0).getText());
                        }
                    } else {
                        mView.serverError(R.string.error_server);
                    }
                }
                @Override
                public void onFailure(Call<ResponseUploadAvatar> call, Throwable t) {
                    mView.serverError(R.string.error_server);
                }
            });
        } else {
            mView.uploadAvatarFail("You have not selected a photo yet");
        }
    }

    public void updateUser(final String firstName, final  String lastName,
                           final String phone, final  String email, final String avatar,
                           final String companyMappingId, final  String companyId,
                           final String companyName, final String companyTitle,
                           final String extraAddress, final String extraUrl,
                           final String extraVideo, final String extraFacebook,
                           final String extraAboutMe, final String extraIntro, final boolean gotCompanies){
        UsersAPI usersAPI = ServiceGeneratorConsumer.createService(UsersAPI.class);
        if(gotCompanies){
            usersAPI.updateUser(firstName, lastName, phone, email, avatar,
                    companyMappingId, companyId, companyName, companyTitle,
                    extraAddress, extraUrl, extraVideo, extraFacebook, extraIntro, extraAboutMe).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess()) {
                            mView.updateUserSuccess(firstName, lastName, phone, email, avatar,
                                    companyMappingId, companyId, companyName, companyTitle, extraAddress,
                                    extraUrl, extraVideo, extraFacebook, extraAboutMe,
                                    extraIntro);
                        } else{
                            mView.updateUserFail(response.body().getMessages().get(0).getText());
                        }
                    } else {
                        mView.serverError(R.string.error_server);
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    mView.serverError(R.string.error_server);
                }
            });
        } else{
            usersAPI.updateUser(firstName, lastName, phone, email, avatar,
                    extraAddress, extraUrl, extraVideo, extraFacebook, extraIntro, extraAboutMe).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess()) {
                            mView.updateUserSuccess(firstName, lastName, phone, email, avatar, "", "",
                                    "", "", extraAddress, extraUrl,
                                    extraVideo, extraFacebook, extraAboutMe, extraIntro);
                        } else {
                            mView.updateUserFail(response.body().getMessages().get(0).getText());
                        }
                    } else {
                        mView.serverError(R.string.error_server);
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    mView.serverError(R.string.error_server);
                }
            });
        }
    }

    public void getCompanies(String value){
        UsersAPI usersAPI = ServiceGeneratorConsumer.createService(UsersAPI.class);
        usersAPI.getCompanies(value).enqueue(new Callback<ResponseCompanies>() {
            @Override
            public void onResponse(Call<ResponseCompanies> call, Response<ResponseCompanies> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.getCompaniesSuccess(response.body().getCompanies());
                    } else {
                        mView.getCompaniesFail(response.body().getMessages().get(0).getText());
                    }
                } else {
                    mView.serverError(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<ResponseCompanies> call, Throwable t) {
                mView.serverError(R.string.error_server);
            }
        });
    }

    public void updateUserSettings(final String notifications, final String newHomes, final String emailSmsNotifications){
        UsersAPI usersAPI = ServiceGeneratorConsumer.createService(UsersAPI.class);
        usersAPI.updateUserSettings(notifications, newHomes, emailSmsNotifications).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mView.updateUserSettingsSuccess(notifications, newHomes, emailSmsNotifications);
                    } else {
                        mView.updateUserSettingsFail(response.body().getMessages().get(0).getText());
                    }
                } else {
                    mView.serverError(R.string.error_server);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                mView.serverError(R.string.error_server);
            }
        });
    }
}
