package com.homecaravan.android.consumer.listener;

import com.homecaravan.android.consumer.model.listitem.ListingFullItem;

public interface IPickSchedule {
    void pickSchedule(ListingFullItem listing);
    void bookSingle(String id);
}
