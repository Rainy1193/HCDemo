package com.homecaravan.android.consumer.message.leavethreadmvp;

/**
 * Created by Anh Dao on 12/7/2017.
 */

public interface ILeaveThreadView {
    void leaveThreadSuccess(String threadId, int position);
    void leaveThreadFail();
}
