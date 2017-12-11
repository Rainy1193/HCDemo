package com.homecaravan.android.consumer.listener;

import com.homecaravan.android.consumer.model.DiscoverMarker;
import com.homecaravan.android.consumer.model.DiscoverMarkerFull;

public interface IClusterListener {
    void addMarkerList(DiscoverMarker discoverMarker);

    void addMarkerFullList(DiscoverMarkerFull discoverMarkerFull);

    void onClusterItemRendered();

    void beforeClusterRendered();

    void onClustersChanged();
}
