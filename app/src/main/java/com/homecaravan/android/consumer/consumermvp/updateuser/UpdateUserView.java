package com.homecaravan.android.consumer.consumermvp.updateuser;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.Companies;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 11/15/2017.
 */

public interface UpdateUserView {
    void updateUserSuccess();
    void updateUserFail(String message);
    void getCompaniesSuccess(ArrayList<Companies> mArrCompanies);
    void getCompaniesFail(String message);
    void uploadAvatarSuccess(String message);
    void uploadAvatarFail(String message);
    void updateUserSettingsSuccess(String notifications, String newHomes, String emailSmsNotifications);
    void updateUserSettingsFail(String message);
    void serverError(@StringRes int message);
}
