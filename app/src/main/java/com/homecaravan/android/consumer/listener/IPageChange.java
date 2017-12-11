package com.homecaravan.android.consumer.listener;


import java.util.ArrayList;

public interface IPageChange {
    void pageChange(int position);

    void openAllSavedSearch();

    void openSavedSearchItem(String id);

    void openAllShowing();

    void openAllAgent();

    void openAgent(int position);

    void openDiscover(String searchName);

    void submitSchedule(String day);

    void editCaravanFromShowing();

    void clearQueue(ArrayList<String> queueIds);

    void loadFragmentCompleted(int position);

    void initFragment();
}
