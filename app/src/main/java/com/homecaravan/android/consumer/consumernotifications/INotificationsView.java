package com.homecaravan.android.consumer.consumernotifications;

import android.support.annotation.StringRes;

import com.homecaravan.android.consumer.model.responseapi.ResponseNotification;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public interface INotificationsView {

    void getNotificationsSuccess(ResponseNotification.NotificationData data, String type, boolean isLoadMore);

    void getNotificationsFail(String message);

    void getNotificationsFail(@StringRes int message);
}
