package com.homecaravan.android.consumer.consumerdashboard.seachplacemvp;

import com.homecaravan.android.consumer.model.Predictions;

public interface SearchPlaceView {
    void showListPlace(Predictions predictions);

    void showLoading();

    void showError(String e);

    void showEmpty();
}
