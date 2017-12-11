package com.homecaravan.android.consumer.listener;

import com.homecaravan.android.consumer.model.responseapi.User;

/**
 * Created by Anh Dao on 11/3/2017.
 */

public interface IScanOrCode {
    void openUnlockStepThree(User data);
    void sendCodeToEnterCodeFragment(String code);
    void switchFragment(int position);
}
