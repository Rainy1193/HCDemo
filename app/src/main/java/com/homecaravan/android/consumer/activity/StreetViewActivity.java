package com.homecaravan.android.consumer.activity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.base.BaseActivity;

import butterknife.OnClick;

public class StreetViewActivity extends BaseActivity implements OnStreetViewPanoramaReadyCallback {

    private LatLng mLatLng;

    @OnClick(R.id.layoutBack)
    public void back() {
        onBackPressed();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        mLatLng = new LatLng(getIntent().getExtras().getDouble("lat"), getIntent().getExtras().getDouble("lng"));
        SupportStreetViewPanoramaFragment streetViewPanoramaFragment = (SupportStreetViewPanoramaFragment)
                getSupportFragmentManager().findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_street_view;
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        streetViewPanorama.setStreetNamesEnabled(true);
        streetViewPanorama.setUserNavigationEnabled(true);
        streetViewPanorama.setZoomGesturesEnabled(true);
        streetViewPanorama.setPanningGesturesEnabled(true);
        streetViewPanorama.setPosition(mLatLng);

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
