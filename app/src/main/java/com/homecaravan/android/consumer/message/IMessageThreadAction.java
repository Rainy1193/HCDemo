package com.homecaravan.android.consumer.message;

/**
 * Created by Anh Dao on 11/2/2017.
 */

public interface IMessageThreadAction {
    void deleteThread(int position, String threadId);
    void unRead(int position, String threadId, String timeStamp);
}
