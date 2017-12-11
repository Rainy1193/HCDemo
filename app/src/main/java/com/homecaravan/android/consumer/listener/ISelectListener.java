package com.homecaravan.android.consumer.listener;

public interface ISelectListener {
    void selectHour(int position);
    void selectMin(int position);
    void selectAmPm(int position);
    void selectDuration(int duration);

}
