package com.homecaravan.android.consumer.message.messagegetallthreadmvp;

import com.homecaravan.android.consumer.model.message.MessageThread;

/**
 * Created by Anh Dao on 10/25/2017.
 */

public interface IGetThreadView {
    void getThreadSuccess(MessageThread messageThread);
    void getThreadFail();
}
