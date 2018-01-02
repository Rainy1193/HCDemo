package com.homecaravan.android.consumer.consumershedule;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.homecaravan.android.R;
import com.homecaravan.android.adapter.ReviewListingAdapter;
import com.homecaravan.android.adapter.ReviewListingMapAdapter;
import com.homecaravan.android.caravan.MyDirectionConverter;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumerbase.ConsumerUser;
import com.homecaravan.android.consumer.direction.MyDirection;
import com.homecaravan.android.consumer.direction.MyDirectionCallback;
import com.homecaravan.android.consumer.direction.MyGoogleDirection;
import com.homecaravan.android.consumer.listener.IScheduleListener;
import com.homecaravan.android.consumer.model.ConsumerListingSchedule;
import com.homecaravan.android.consumer.model.ConsumerTeam;
import com.homecaravan.android.consumer.model.CurrentListingSchedule;
import com.homecaravan.android.consumer.model.ReviewMaker;
import com.homecaravan.android.consumer.model.RouteMarker;
import com.homecaravan.android.consumer.model.WayPoint;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.consumer.widget.CustomLayoutManager;
import com.homecaravan.android.consumer.widget.ScaleTransformerReviewRecyclerView;
import com.homecaravan.android.ui.CircleImageView;
import com.homecaravan.android.ui.MapWrapperLayout;
import com.homecaravan.android.ui.MySupportMapFragment;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.OnClick;


public class FragmentStepReviewAndSubmit extends BaseFragment implements
        OnMapReadyCallback,
        MapWrapperLayout.OnDragListener, MyDirectionCallback {

    private GoogleMap mGoogleMap;
    private MySupportMapFragment mMapFragment;
    private CustomLayoutManager mLayoutManager;
    private ReviewListingAdapter mAdapter;
    private ReviewListingMapAdapter mMapAdapter;
    private ArrayList<ConsumerListingSchedule> mArrListing = new ArrayList<>();
    private ArrayList<ReviewMaker> mArrMarker = new ArrayList<>();
    private boolean mIsAttack;
    private IScheduleListener mListener;
    private boolean mViewList = true;
    private String mServerKey = "AIzaSyDZy0BfgLMN-n-BVv-6WPoRs0rGfdOd5lM";
    private IconGenerator mIconFactory;

    public void setListener(IScheduleListener mListener) {
        this.mListener = mListener;
    }


    @Bind(R.id.layoutAgent)
    LinearLayout mLayoutAgent;

    @Bind(R.id.rvReviewListing)
    RecyclerView mRvListing;

    @Bind(R.id.rvListingReviewMap)
    RecyclerView mRvListingMap;

    @Bind(R.id.ivChangeView)
    ImageView mChangeView;

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
        mChangeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mViewList) {
                    mLayoutMap.setVisibility(View.VISIBLE);
                    mRvListing.setVisibility(View.INVISIBLE);
                    mViewList = false;
                } else {
                    mLayoutMap.setVisibility(View.INVISIBLE);
                    mRvListing.setVisibility(View.VISIBLE);
                    mViewList = true;
                }
            }
        });
        mIconFactory = new IconGenerator(getActivity());
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


    public void drawWay() {
        ArrayList<WayPoint> mWayPoint = new ArrayList<>();
        if (mArrListing.size() < 2) {
            mIconFactory.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_router));
            String position = "1";
            Marker marker = addMarker(mIconFactory, Utils.getPositionRoute(position),
                    new LatLng(mArrListing.get(0).getListing().getAddress().getLat(), mArrListing.get(0).getListing().getAddress().getLng()));
            ReviewMaker routeMarker = new ReviewMaker(marker, mArrListing.get(0));
            mArrMarker.add(routeMarker);
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mArrMarker.get(0).getMarker().getPosition(), 15f));
            return;
        }
        mWayPoint.clear();
        for (int i = 0; i < mArrListing.size(); i++) {
            if (i + 1 < mArrListing.size()) {
                LatLng start = new LatLng(mArrListing.get(i).getListing().getAddress().getLat(), mArrListing.get(i).getListing().getAddress().getLng());
                LatLng end = new LatLng(mArrListing.get(i + 1).getListing().getAddress().getLat(), mArrListing.get(i + 1).getListing().getAddress().getLng());
                mWayPoint.add(new WayPoint(start, end));
            }
        }
        LatLng start = new LatLng(mArrListing.get(mArrListing.size() - 1).getListing().getAddress().getLat(), mArrListing.get(mArrListing.size() - 1).getListing().getAddress().getLng());
        LatLng end = new LatLng(mArrListing.get(0).getListing().getAddress().getLat(), mArrListing.get(0).getListing().getAddress().getLng());
        mWayPoint.add(new WayPoint(start, end));

        for (int i = 0; i < mWayPoint.size(); i++) {
            MyGoogleDirection.withServerKey(mServerKey)
                    .from(mWayPoint.get(i).getStart())
                    .to(mWayPoint.get(i).getEnd())
                    .transportMode(TransportMode.DRIVING)
                    .execute(this);
        }
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(getZoomRouter(), 0), new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mGoogleMap.getCameraPosition().target, mGoogleMap.getCameraPosition().zoom - 0.5f));
            }

            @Override
            public void onCancel() {

            }
        });
    }

    public LatLngBounds getZoomRouter() {
        ArrayList<Double> arrLat = new ArrayList<>();
        ArrayList<Double> arrLng = new ArrayList<>();
        for (int i = 0; i < mArrListing.size(); i++) {
            arrLat.add(mArrListing.get(i).getListing().getAddress().getLat());
            arrLng.add(mArrListing.get(i).getListing().getAddress().getLng());
        }
        Collections.sort(arrLat, Collections.<Double>reverseOrder());
        Collections.sort(arrLng, Collections.<Double>reverseOrder());
        LatLngBounds latLngBounds = new LatLngBounds(new LatLng(arrLat.get(arrLat.size() - 1), arrLng.get(arrLng.size() - 1)),
                new LatLng(arrLat.get(0), arrLng.get(0)));
        return latLngBounds;
    }

    private Marker addMarker(IconGenerator iconGenerator, CharSequence text, LatLng position) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_router, null, false);
        TextView textView = (TextView) view.findViewById(R.id.tvNumber);
        textView.setText(text.toString());
        iconGenerator.setBackground(null);
        iconGenerator.setContentView(view);

        Bitmap bitmap = iconGenerator.makeIcon();
        MarkerOptions markerOptions = new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(bitmap)).
                position(position).
                anchor(iconGenerator.getAnchorU(), iconGenerator.getAnchorV());

        return mGoogleMap.addMarker(markerOptions);
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
        drawWay();

        mAdapter = new ReviewListingAdapter(mArrListing, getActivity());
        mRvListing.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvListing.setAdapter(mAdapter);
        mMapAdapter = new ReviewListingMapAdapter(mArrListing, getActivity());
        mRvListingMap.setAdapter(mMapAdapter);

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

    public void initAgent(String name, int avatar, boolean b) {
        if (b) {
            Glide.with(getActivity().getApplicationContext()).load(avatar).asBitmap().fitCenter().into(mIvAgent);
            mLayoutAgent.setVisibility(View.VISIBLE);
            mNameAgent.setText(name);
        } else {
            mLayoutAgent.setVisibility(View.GONE);
        }
//        if (ConsumerUser.getInstance().getData().getAgent() != null) {
//            ConsumerTeam consumerTeam = ConsumerUser.getInstance().getData().getAgent();
//            mStatusAgent.setVisibility(View.GONE);
//            mLayoutAgent.setVisibility(View.VISIBLE);
//            //Glide.with(getActivity().getApplicationContext()).load(consumerTeam.getPhoto()).asBitmap().fitCenter().into(mIvAgent);
//            mNameAgent.setText(consumerTeam.getFirstName() + " " + consumerTeam.getLastName());
//        } else {
//            mStatusAgent.setText("You are select agent");
//            mStatusAgent.setVisibility(View.VISIBLE);
//            mLayoutAgent.setVisibility(View.GONE);
//        }
    }

    @Override
    public void onDirectionSuccess(MyDirection directionResponse, String rawBody) {
        Direction direction = directionResponse.getDirection();
        LatLng latLngStart = directionResponse.getStart();
        if (direction.isOK()) {
            if (direction.getRouteList().size() != 0) {
                ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
                String position = getPosition(latLngStart);
                Marker marker = addMarker(mIconFactory, Utils.getPositionRoute(position), directionPositionList.get(0));
                ReviewMaker routeMarker = new ReviewMaker(marker, mArrListing.get(Integer.parseInt(position) - 1));
                mArrMarker.add(routeMarker);
                mGoogleMap.addPolyline(MyDirectionConverter.createPolyline(getActivity(), directionPositionList, 4, ContextCompat.getColor(getActivity(), R.color.colorMenuConsumer)));
            }
        }
    }

    public String getPosition(LatLng latLng) {
        for (int i = 0; i < mArrListing.size(); i++) {
            if (mArrListing.get(i).getListing().getAddress().getLat() == latLng.latitude &&
                    ((mArrListing.get(i).getListing().getAddress().getLng() == latLng.longitude))) {
                return String.valueOf(i + 1);
            }
        }
        return "1";
    }

    @Override
    public void onDirectionFailure(Throwable t) {

    }
}
