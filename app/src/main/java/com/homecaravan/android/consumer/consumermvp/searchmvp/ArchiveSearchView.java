package com.homecaravan.android.consumer.consumermvp.searchmvp;

import android.support.annotation.StringRes;

public interface ArchiveSearchView {
    void archiveSearchSuccess(String message);

    void archiveSearchFail(@StringRes int message);

    void archiveSearchFail(String message);
}
