package com.homecaravan.android.consumer.listener;

public interface IDialogListener {
    void yesAction(String action);
    void noAction(String action);
    void otherAction(String action);
}
