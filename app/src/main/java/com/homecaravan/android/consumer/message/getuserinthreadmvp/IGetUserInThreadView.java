package com.homecaravan.android.consumer.message.getuserinthreadmvp;

import com.homecaravan.android.consumer.model.message.MessageUserData;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 12/5/2017.
 */

public interface IGetUserInThreadView {
    void getAllUserInfoSuccess(ArrayList<MessageUserData> data);

    void getAllUserInfoFail();
}
