package com.homecaravan.android.consumer.listener;

public interface IScheduleListener {
    void openPickTimeRoute(int hour, int min, String haft, String duration);

    void updateListAfterSwap(int posFrom, int posTo);

    void showNext();

    void showNextWhenSelectAgent();

    void backSelectAgent();

    void createCaravanSuccess(boolean clearData);

    void createCaravanFail();

    void disableButton();

    void enableButton();
}
