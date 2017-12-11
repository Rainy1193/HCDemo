package com.homecaravan.android.consumer.consumershedule;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.homecaravan.android.R;
import com.homecaravan.android.adapter.ReviewListingAdapter;
import com.homecaravan.android.adapter.ReviewListingMapAdapter;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumerbase.ConsumerUser;
import com.homecaravan.android.consumer.listener.IScheduleListener;
import com.homecaravan.android.consumer.model.ConsumerListingSchedule;
import com.homecaravan.android.consumer.model.ConsumerTeam;
import com.homecaravan.android.consumer.model.CurrentListingSchedule;
import com.homecaravan.android.consumer.model.ReviewMaker;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.consumer.widget.CustomLayoutManager;
import com.homecaravan.android.consumer.widget.ScaleTransformerReviewRecyclerView;
import com.homecaravan.android.ui.CircleImageView;
import com.homecaravan.android.ui.MapWrapperLayout;
import com.homecaravan.android.ui.MySupportMapFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;


public class FragmentStepReviewAndSubmit extends BaseFragment implements
        OnMapReadyCallback,
        MapWrapperLayout.OnDragListener {


    private GoogleMap mGoogleMap;
    private MySupportMapFragment mMapFragment;
    private CustomLayoutManager mLayoutManager;
    private ReviewListingAdapter mAdapter;
    private ReviewListingMapAdapter mMapAdapter;
    private ArrayList<ConsumerListingSchedule> mArrListing = new ArrayList<>();
    private ArrayList<ReviewMaker> mArrMarker = new ArrayList<>();
    private boolean mIsAttack;
    private IScheduleListener mListener;

    public void setListener(IScheduleListener mListener) {
        this.mListener = mListener;
    }

    @Bind(R.id.tvStatusAgent)
    TextView mStatusAgent;

    @Bind(R.id.layoutAgent)
    RelativeLayout mLayoutAgent;

    @Bind(R.id.rvReviewListing)
    RecyclerView mRvListing;

    @Bind(R.id.rvListingReviewMap)
    RecyclerView mRvListingMap;

    @Bind(R.id.switchView)
    Switch mSwitch;

    @Bind(R.id.layoutMap)
    RelativeLayout mLayoutMap;

    @Bind(R.id.ivAgent)
    CircleImageView mIvAgent;

    @Bind(R.id.tvNameAgent)
    TextView mNameAgent;

    @OnClick(R.id.tvStatusAgent)
    public void onTvStatusAgent() {
        mListener.backSelectAgent();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapFragment = (MySupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapReview);
        mMapFragment.setOnDragListener(this);
        mMapFragment.getMapAsync(this);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mLayoutMap.setVisibility(View.VISIBLE);
                    mRvListing.setVisibility(View.INVISIBLE);
                } else {
                    mLayoutMap.setVisibility(View.INVISIBLE);
                    mRvListing.setVisibility(View.VISIBLE);
                }
            }
        });
        mLayoutManager = new CustomLayoutManager(CustomLayoutManager.HORIZONTAL);
        mLayoutManager.attach(mRvListingMap);
        mLayoutManager.setItemTransformer(new ScaleTransformerReviewRecyclerView());
        mLayoutManager.setOnItemSelectedListener(new CustomLayoutManager.OnItemSelectedListener() {
            @Override
            public void onItemSelected(RecyclerView recyclerView, View item, int position) {
                String id = mArrListing.get(position).getListing().getId();
                if (mIsAttack) {
                    moveToMarkerWhenScrollList(id);
                } else {
                    mIsAttack = true;
                }
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_step_review_and_submit;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setCompassEnabled(false);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        mGoogleMap.getUiSettings().setIndoorLevelPickerEnabled(false);
        mGoogleMap.getUiSettings().setTiltGesturesEnabled(false);

        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                scrollListRoute(marker);
                return false;
            }
        });
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(33.669355, -118.012377), 17f));
    }


    @Override
    public void onDrag(MotionEvent motionEvent) {

    }

    public void setUpListAndMap() {
        mRvListing.removeAllViews();
        mRvListingMap.removeAllViews();
        mArrListing.clear();
        mArrMarker.clear();
        mGoogleMap.clear();
        for (int i = 0; i < CurrentListingSchedule.getInstance().getArrListing().size(); i++) {
            mArrListing.add(CurrentListingSchedule.getInstance().getArrListing().get(i));
        }
        IconGenerator iconFactory = new IconGenerator(getActivity());
        for (int i = 0; i < mArrListing.size(); i++) {
            LatLng latLng = new LatLng(mArrListing.get(i).getListing().getAddress().getLat(),
                    mArrListing.get(i).getListing().getAddress().getLng());
            iconFactory.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_router));
            Marker marker = addMarker(iconFactory, Utils.getPositionRoute(String.valueOf(i + 1)), latLng);
            ReviewMaker routeMarker = new ReviewMaker(marker, mArrListing.get(i));
            mArrMarker.add(routeMarker);

        }

        mAdapter = new ReviewListingAdapter(mArrListing, getActivity());
        mRvListing.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvListing.setAdapter(mAdapter);
        mMapAdapter = new ReviewListingMapAdapter(mArrListing, getActivity());
        mRvListingMap.setAdapter(mMapAdapter);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mArrMarker.get(0).getMarker().getPosition(), 17f));
    }

    public void moveToMarkerWhenScrollList(String id) {
        for (int i = 0; i < mArrMarker.size(); i++) {
            ConsumerListingSchedule listing = mArrListing.get(i);
            if (listing.getListing().getId().equalsIgnoreCase(id)) {
                LatLng latLng = new LatLng(listing.getListing().getAddress().getLat(),
                       listing.getListing().getAddress().getLng());
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }
    }

    private Marker addMarker(IconGenerator iconFactory, CharSequence text, LatLng position) {
        Bitmap bitmap = iconFactory.makeIcon(text);
        MarkerOptions markerOptions = new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(bitmap)).
                position(position).
                anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

        return mGoogleMap.addMarker(markerOptions);
    }

    public void scrollListRoute(Marker marker) {
        for (int i = 0; i < mArrMarker.size(); i++) {
            if (marker.getId().equalsIgnoreCase(mArrMarker.get(i).getMarker().getId())) {
                final int finalI = i;
                mRvListingMap.post(new Runnable() {
                    @Override
                    public void run() {
                        mRvListingMap.smoothScrollToPosition(finalI);
                    }
                });
                break;
            }
        }
    }

    public void initAgent(int statusSelectAgent) {
        if (ConsumerUser.getInstance().getData().getAgent() != null) {
            ConsumerTeam consumerTeam = ConsumerUser.getInstance().getData().getAgent();
            mStatusAgent.setVisibility(View.GONE);
            mLayoutAgent.setVisibility(View.VISIBLE);
            //Glide.with(getActivity().getApplicationContext()).load(consumerTeam.getPhoto()).asBitmap().fitCenter().into(mIvAgent);
            mNameAgent.setText(consumerTeam.getFirstName() + " " + consumerTeam.getLastName());
        } else {
            mStatusAgent.setText("You are select agent");
            mStatusAgent.setVisibility(View.VISIBLE);
            mLayoutAgent.setVisibility(View.GONE);
        }
    }
}
