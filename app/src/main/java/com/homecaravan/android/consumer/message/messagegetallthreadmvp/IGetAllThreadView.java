package com.homecaravan.android.consumer.message.messagegetallthreadmvp;

import com.homecaravan.android.consumer.model.message.MessageThread;
import com.homecaravan.android.consumer.model.message.MessageUserData;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 9/7/2017.
 */

public interface IGetAllThreadView {
    void getAllThreadSuccess(ArrayList<MessageThread> data);
    void getAllThreadFail();

    void getAllUserInfoSuccess(ArrayList<MessageUserData> data);
    void getAllUserInfoFail();
}
