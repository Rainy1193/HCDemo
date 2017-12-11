package com.homecaravan.android.consumer.listener;


public interface IMainListener {
    String getNameSearch(String action);

    void hideBottomBar(boolean b);

    void openSaveSearchDetailFromDiscover(String id, boolean b);
}
