package com.homecaravan.android.consumer.model;


import com.google.android.gms.maps.model.Marker;
import com.homecaravan.android.consumer.model.responseapi.ClustersSearchMap;

public class ClusterMarker {
    private Marker mMarker;
    private ClustersSearchMap mCluster;

    public ClusterMarker(Marker mMarker, ClustersSearchMap mCluster) {
        this.mMarker = mMarker;
        this.mCluster = mCluster;
    }

    public Marker getMarker() {
        return mMarker;
    }

    public void setMarker(Marker mMarker) {
        this.mMarker = mMarker;
    }

    public ClustersSearchMap getCluster() {
        return mCluster;
    }

    public void setCluster(ClustersSearchMap mCluster) {
        this.mCluster = mCluster;
    }
}
