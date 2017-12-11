package com.homecaravan.android.consumer.message.messagegetthreadidmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.ResponseMessageGetThreadId;

/**
 * Created by Anh Dao on 10/24/2017.
 */

public interface GetThreadIdView {

    void getThreadIdAtCaravanSuccess(ResponseMessageGetThreadId threadId, int position, String threadName);

    void getThreadIdSuccess(ResponseMessageGetThreadId threadId, String threadName);

    void getThreadIdFail();

    void getThreadIdFail(@StringRes int message);
}
