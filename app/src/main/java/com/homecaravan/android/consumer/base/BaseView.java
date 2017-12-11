package com.homecaravan.android.consumer.base;


import android.support.annotation.StringRes;
import android.view.View;

import com.homecaravan.android.consumer.model.TypeDialog;

public interface BaseView {
    void showLoading();

    void hideLoading();

    void onError(@StringRes int resId);

    void onError(String message);

    void showDialog(TypeDialog type, String message, String action);

    void showDialog(TypeDialog type, @StringRes int resId, String action);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();

    void showSnackBar(View view, TypeDialog type, String message, String action);

    void showSnackBar(View view, TypeDialog type, @StringRes int message, String action);

    void dismissSnackBar();
}
