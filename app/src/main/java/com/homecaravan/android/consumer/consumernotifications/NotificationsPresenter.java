package com.homecaravan.android.consumer.consumernotifications;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.api.NotificationApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.model.responseapi.ResponseNotification;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anh Dao on 7/27/2017.
 */

public class NotificationsPresenter {

    private INotificationsView mView;

    public NotificationsPresenter(INotificationsView mView) {
        this.mView = mView;
    }

    public void getNotification(final String type, String pageNumber, String perPage, final boolean isLoadMore){
        NotificationApi notificationApi = ServiceGeneratorConsumer.createService(NotificationApi.class);
        notificationApi.getNotifications(type, "", "", pageNumber, perPage)
                .enqueue(new Callback<ResponseNotification>() {
                    @Override
                    public void onResponse(Call<ResponseNotification> call, Response<ResponseNotification> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                                mView.getNotificationsSuccess(response.body().getNotificationData(), type, isLoadMore);
                            } else {
                                mView.getNotificationsFail(response.body().getMessages().get(0).getText());
                            }
                        } else {
                            mView.getNotificationsFail(R.string.error_server);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseNotification> call, Throwable t) {
                        mView.getNotificationsFail(R.string.error_server);
                    }
                });
    }

}
