package com.homecaravan.android.consumer.message.messagegetallmvp;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.message.ConsumerMessages;
import com.homecaravan.android.consumer.model.message.MessageItem;

import java.util.ArrayList;

/**
 * Created by Anh Dao on 9/7/2017.
 */

public interface IMessagesActionView {
    void getMessagesSuccess(ArrayList<MessageItem> mArrMessageItem);
    void getMessagesFromRealmSuccess(ArrayList<ConsumerMessages> mArrMessages);
    void getMessagesFailed();
    void removeMessageSuccess(int position);
    void removeMessageFail();
    void serverError(@StringRes int message);
}
