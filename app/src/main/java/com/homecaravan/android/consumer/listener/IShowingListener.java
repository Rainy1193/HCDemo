package com.homecaravan.android.consumer.listener;


public interface IShowingListener {

    void showCaravanAction();

    void onMessageClicked(String apptId, String listingId, String caravanId, String title, String userId);

    void editCaravan();
}
