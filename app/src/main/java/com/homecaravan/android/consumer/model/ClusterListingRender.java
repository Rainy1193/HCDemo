package com.homecaravan.android.consumer.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.IClusterListener;
import com.homecaravan.android.consumer.utils.Utils;

import java.util.Set;


public class ClusterListingRender extends DefaultClusterRenderer<DiscoverMarker> {
    private final IconGenerator mIconGenerator;
    private final IconGenerator mClusterIconGenerator;
    private TextView mTvNumber;
    private Context mContext;
    private IClusterListener mListener;

    public ClusterListingRender(Activity activity, GoogleMap googleMap, ClusterManager<DiscoverMarker> mClusterManager, IClusterListener mListener) {
        super(activity, googleMap, mClusterManager);
        this.mContext = activity.getBaseContext();
        this.mListener = mListener;
        mIconGenerator = new IconGenerator(mContext);
        mClusterIconGenerator = new IconGenerator(mContext);
        View viewCluster = activity.getLayoutInflater().inflate(R.layout.layout_cluster, null, false);
        mTvNumber = (TextView) viewCluster.findViewById(R.id.tvNumber);
        mClusterIconGenerator.setBackground(null);
        mClusterIconGenerator.setContentView(viewCluster);
    }

    @Override
    protected void onClusterItemRendered(DiscoverMarker clusterItem, Marker marker) {
        marker.setZIndex(-10);
        clusterItem.setMarker(marker);
        mListener.addMarkerList(clusterItem);
        mListener.onClusterItemRendered();
    }


    @Override
    protected void onClusterRendered(Cluster<DiscoverMarker> cluster, Marker marker) {
        super.onClusterRendered(cluster, marker);
    }


    @Override
    public void onClustersChanged(Set<? extends Cluster<DiscoverMarker>> clusters) {
        super.onClustersChanged(clusters);
        mListener.onClustersChanged();
    }


    @Override
    protected void onBeforeClusterItemRendered(DiscoverMarker marker, MarkerOptions markerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(Utils.getIconGeneratorRound(mIconGenerator, marker.getStatus(),
                mContext, marker.getData().getListing().getPrice()).makeIcon()));
    }


    @Override
    protected void onBeforeClusterRendered(Cluster<DiscoverMarker> cluster, MarkerOptions markerOptions) {
        mTvNumber.setText(String.valueOf(cluster.getSize()));
        Bitmap icon = mClusterIconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        mListener.beforeClusterRendered();
    }


    @Override
    protected boolean shouldRenderAsCluster(Cluster cluster) {
        return cluster.getSize() > 1000000;
    }
}