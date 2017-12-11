package com.homecaravan.android.consumer.listener;


import com.homecaravan.android.consumer.model.responseapi.Listing;

public interface ISchedulePropertyListener {
    void pickSchedule(Listing id);

    void removeSchedule(Listing id, int position);
}
