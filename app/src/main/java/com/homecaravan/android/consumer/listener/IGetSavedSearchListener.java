package com.homecaravan.android.consumer.listener;

import com.homecaravan.android.consumer.model.SavedSearch;

import java.util.ArrayList;

public interface IGetSavedSearchListener {
    void getSuccess(ArrayList<SavedSearch> savedSearches);

    void getError();
}
