package com.homecaravan.android.consumer.listener;


public interface IBookSingleListener {
    void pickDate(String date);

    void backDate();

    void pickTime(String time, String timeBookStart, String timeBookEnd, String fullTimeBookStart, String fullTimeBookEnd);

    void backTime();

    void complete(String note, String confirmation, String date, String time);

    void showLayoutAst();

    void hideAst();

    void pickTimeAst();
}
