package com.homecaravan.android.consumer.consumerdiscover;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.ui.IconGenerator;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumermvp.searchmvp.SaveSearchPresenter;
import com.homecaravan.android.consumer.consumermvp.searchmvp.SaveSearchView;
import com.homecaravan.android.consumer.consumermvp.searchmvp.SearchMapPresenter;
import com.homecaravan.android.consumer.consumermvp.searchmvp.SearchMapView;
import com.homecaravan.android.consumer.consumermvp.searchmvp.VoteListingPresenter;
import com.homecaravan.android.consumer.consumermvp.searchmvp.VoteListingView;
import com.homecaravan.android.consumer.fastadapter.SearchListItem;
import com.homecaravan.android.consumer.fastadapter.SearchMapItem;
import com.homecaravan.android.consumer.listener.IClusterListener;
import com.homecaravan.android.consumer.listener.ISwipedHelper;
import com.homecaravan.android.consumer.listener.IVoteListing;
import com.homecaravan.android.consumer.model.CaravanQueue;
import com.homecaravan.android.consumer.model.ClusterListingRender;
import com.homecaravan.android.consumer.model.ClusterMarker;
import com.homecaravan.android.consumer.model.CurrentCreateSavedSearch;
import com.homecaravan.android.consumer.model.DiscoverMarker;
import com.homecaravan.android.consumer.model.DiscoverMarkerFull;
import com.homecaravan.android.consumer.model.EventFavorite;
import com.homecaravan.android.consumer.model.EventQueue;
import com.homecaravan.android.consumer.model.EventRequestShowing;
import com.homecaravan.android.consumer.model.StatusMarker;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.listitem.ListingItem;
import com.homecaravan.android.consumer.model.responseapi.ClustersSearchMap;
import com.homecaravan.android.consumer.model.responseapi.ConditionFull;
import com.homecaravan.android.consumer.model.responseapi.ListingFull;
import com.homecaravan.android.consumer.model.responseapi.ListingListSearchMap;
import com.homecaravan.android.consumer.model.responseapi.ListingSearchMap;
import com.homecaravan.android.consumer.model.responseapi.ResponseSearchMap;
import com.homecaravan.android.consumer.model.responseapi.SearchDetail;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.consumer.utils.viewanimator.AnimationListener;
import com.homecaravan.android.consumer.utils.viewanimator.ViewAnimator;
import com.homecaravan.android.consumer.widget.CustomLayoutManager;
import com.homecaravan.android.consumer.widget.ItemTouchHelperCallback;
import com.homecaravan.android.consumer.widget.ItemTouchHelperExtension;
import com.homecaravan.android.consumer.widget.ScaleTransformerRecyclerView;
import com.homecaravan.android.consumer.widget.floatactionbutton.FloatingActionButton;
import com.homecaravan.android.consumer.widget.floatactionbutton.FloatingActionMenu;
import com.homecaravan.android.models.CurrentSaveSearch;
import com.homecaravan.android.ui.MapWrapperLayout;
import com.homecaravan.android.ui.MySupportMapFragment;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.RequestBody;

public class FragmentListSavedSearch extends BaseFragment implements
        ISwipedHelper,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<LocationSettingsResult>,
        LocationListener,
        OnMapReadyCallback,
        View.OnTouchListener,
        FloatingActionMenu.OnMenuToggleListener,
        MapWrapperLayout.OnDragListener,
        GoogleMap.OnCameraIdleListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveCanceledListener,
        SearchMapView,
        IClusterListener,
        IVoteListing,
        SaveSearchView,
        VoteListingView {


    private FastItemAdapter<SearchMapItem> mMapAdapterSearch;
    private FastItemAdapter<SearchListItem> mListAdapterSearch;
    private ArrayList<DiscoverMarker> mArrMarker = new ArrayList<>();
    private VoteListingPresenter mVotePresenter;
    private SaveSearchPresenter mSaveSearchPresenter;
    private ItemTouchHelperExtension mItemTouchHelper;
    private ClusterManager<DiscoverMarker> mClusterManager;
    private ArrayList<ListingItem> mArrListingSearch = new ArrayList<>();
    private ArrayList<ListingItem> mArrListingSearchBase = new ArrayList<>();
    private ArrayList<ClusterMarker> mArrClusterMarker = new ArrayList<>();
    private ArrayList<ListingListSearchMap> mArrListingList = new ArrayList<>();
    private LatLngBounds mLatLngBounds;
    private Point mPointSw;
    private Point mPointNe;
    private boolean mMapReady;
    private boolean mDataReady;
    private static final long ACCURACY = 0;
    private static final long UPDATE_INTERVAL = 400;
    private static final long FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL / 2;
    private Marker mCurrentMarker;
    private Marker mOldMarker;
    private int mCurrentPosition = -1;
    private int mOldPosition = -1;
    private boolean mFirstLoad;
    private int mPosition;
    private String mFt = "sale";
    private String mMaxPrice = "";
    private String mMinPrice = "";
    private String mBed = "";
    private String mBath = "";
    private String mMinLs = "";
    private String mMaxLs = "";
    private String mMinLsf = "";
    private String mMaxLsf = "";
    private String mMinYb = "";
    private String mMaxYb = "";
    private String mPt = "";
    private String mKeyword = "";
    private String mDc = "";
    private String mNameSavedSearch = "New Search";
    private String mNe;
    private String mSw;
    private boolean mSaveBeforeVote;
    private boolean mMoreNotCallApi;
    private SearchMapPresenter mSearchMapPresenter;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationManager mLocationManager;
    private GoogleMap mGoogleMap;
    private MySupportMapFragment mMapFragment;
    private ArrayList<Polyline> mArrPolyline = new ArrayList<>();
    private CustomLayoutManager mCustomLayoutManager;
    private ItemTouchHelperCallback mItemTouchHelperCallback;
    private int mStroke = 5;
    private ArrayList<LatLng> mArrLatLng = new ArrayList<>();
    private LatLng mBeginArea;
    private PolygonOptions mRectOptions;
    private Polygon mPolyGon;
    private Location mCurrentLocation;
    private boolean mIsAttack;
    private boolean mCallApiDev;
    private boolean mShowMap;
    @Bind(R.id.rvListing)
    RecyclerView mRvListing;
    @Bind(R.id.layoutEmpty)
    LinearLayout mLayoutEmpty;
    @Bind(R.id.layoutMapSavedSearch)
    RelativeLayout mLayoutMap;
    @Bind(R.id.layoutMapSavedSearchContainer)
    RelativeLayout mLayoutMapContainer;
    @Bind(R.id.rvListingMini)
    RecyclerView mRvListingMap;
    @Bind(R.id.layoutRvListing)
    RelativeLayout mLayoutRvListing;
    @Bind(R.id.layoutListingList)
    RelativeLayout mLayoutListingList;
    @Bind(R.id.layoutDraw)
    FrameLayout mLayoutDraw;
    @Bind(R.id.layoutBgActionMap)
    RelativeLayout mLayoutBgActionMap;
    @Bind(R.id.fabActionMap)
    FloatingActionMenu mFabActionMap;
    @Bind(R.id.fabRefresh)
    FloatingActionButton mFabRefresh;
    @Bind(R.id.fabCurrentLocation)
    FloatingActionButton mFabCurrentLocation;
    @Bind(R.id.fabDrawArea)
    FloatingActionButton mFabDrawArea;
    @Bind(R.id.fabSatelliteView)
    FloatingActionButton mFabSatelliteView;
    @Bind(R.id.layoutLoading)
    LinearLayout mLayoutLoading;
    @Bind(R.id.layoutErrorZoom)
    RelativeLayout mLayoutErrorZoom;
    @Bind(R.id.layoutMain)
    RelativeLayout mLayoutMain;

    @OnClick(R.id.fabRefresh)
    public void onFabRefreshClicked() {
        mFabActionMap.close(true);
        mIsAttack = false;
        refreshMap();
    }

    @OnClick(R.id.fabCurrentLocation)
    public void onFabCurrentLocationClicked() {
        mFabActionMap.close(true);
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (mCurrentLocation == null) {
                startLocationUpdates();
            } else {
                moveToCurrentLocation();
            }
        } else {
            mCurrentLocation = null;
            checkLocationSettings();
        }

    }

    @OnClick(R.id.fabDrawArea)
    public void onFabDrawAreaClicked() {
        mFabActionMap.close(true);
        AnimUtils.hideViewFromBottom(mLayoutRvListing);
        mLayoutDraw.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.fabSatelliteView)
    public void onFabSatelliteViewClicked() {
        if (mGoogleMap.getMapType() == 1) {
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            mFabSatelliteView.setLabelText("Classic View");
        } else {
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mFabSatelliteView.setLabelText("Satellite View");
        }
        mFabActionMap.close(true);
    }


    @OnClick(R.id.layoutBgActionMap)
    public void closeActionButton() {
        mLayoutBgActionMap.setVisibility(View.GONE);
        mFabActionMap.close(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapFragment = (MySupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapSaveSearchDetail);
        mMapFragment.setOnDragListener(this);
        mMapFragment.getMapAsync(this);
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        buildGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();
        mMapAdapterSearch = new FastItemAdapter<>();
        mListAdapterSearch = new FastItemAdapter<>();
        mListAdapterSearch.withSelectable(true);
        mRvListing.setAdapter(mListAdapterSearch);
        mRvListing.setHasFixedSize(true);
        mRvListingMap.setAdapter(mMapAdapterSearch);
        mLayoutDraw.setOnTouchListener(this);
        mFabActionMap.setOnMenuToggleListener(this);
//        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    mLayoutMap.setVisibility(View.VISIBLE);
//                    mLayoutListingList.setVisibility(View.GONE);
//                } else {
//                    mLayoutMap.setVisibility(View.GONE);
//                    mLayoutListingList.setVisibility(View.VISIBLE);
//                }
//            }
//        });
        mVotePresenter = new VoteListingPresenter(this);
        mSaveSearchPresenter = new SaveSearchPresenter(this);
        mSearchMapPresenter = new SearchMapPresenter(this);
        mItemTouchHelperCallback = new ItemTouchHelperCallback();
        mItemTouchHelperCallback.setHelper(this);
        mItemTouchHelper = new ItemTouchHelperExtension(mItemTouchHelperCallback);
        mItemTouchHelper.attachToRecyclerView(mRvListing);
        mRvListing.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCustomLayoutManager = new CustomLayoutManager(CustomLayoutManager.HORIZONTAL);
        mCustomLayoutManager.attach(mRvListingMap);
        mCustomLayoutManager.setItemTransformer(new ScaleTransformerRecyclerView());
        mCustomLayoutManager.setOnItemSelectedListener(new CustomLayoutManager.OnItemSelectedListener() {
            @Override
            public void onItemSelected(RecyclerView recyclerView, View item, int position) {
                String id = mArrListingSearch.get(position).getListing().getId();
                if (mIsAttack) {
                    mOldPosition = mCurrentPosition;
                    mCurrentPosition = position;
                    moveToMarkerWhenScrollList(id);
                } else {
                    mIsAttack = true;
                }
            }
        });


    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventRequestShowing(EventRequestShowing requestShowing) {
        if (mListAdapterSearch != null) {
            for (int i = 0; i < mListAdapterSearch.getItemCount(); i++) {
                if (mListAdapterSearch.getAdapterItem(i).getListing().getListing().getId().equalsIgnoreCase(requestShowing.getId())) {
                    mListAdapterSearch.getAdapterItem(i).getListing().setQueue(true);
                }
            }
            for (int i = 0; i < mMapAdapterSearch.getItemCount(); i++) {
                if (mMapAdapterSearch.getAdapterItem(i).getListing().getListing().getId().equalsIgnoreCase(requestShowing.getId())) {
                    mMapAdapterSearch.getAdapterItem(i).getListing().setQueue(true);
                }
            }
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_list_saved_search;
    }

    @Override
    public void mGetMovementFlags(int position) {

    }

    @Override
    public void mOnMove() {

    }

    @Override
    public void mOnSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public void mOnChildDraw() {

    }

    @Override
    public void onMenuToggle(boolean opened) {
        if (opened) {
            mLayoutBgActionMap.setVisibility(View.VISIBLE);
            ViewAnimator.animate(mLayoutBgActionMap).duration(100).alpha(0, 1).start();
            mFabActionMap.setMenuButtonColorNormalResId(R.color.colorFabClick);
            mFabActionMap.setMenuButtonColorPressedResId(R.color.colorFabClick);
            mFabActionMap.setMenuButtonColorRippleResId(R.color.colorFabClick);
        } else {
            ViewAnimator.animate(mLayoutBgActionMap).duration(100).alpha(1, 0).onStop(new AnimationListener.Stop() {
                @Override
                public void onStop() {
                    mLayoutBgActionMap.setVisibility(View.GONE);
                }
            }).start();
            mFabActionMap.setMenuButtonColorNormalResId(R.color.colorMenuConsumer);
            mFabActionMap.setMenuButtonColorPressedResId(R.color.colorMenuConsumer);
            mFabActionMap.setMenuButtonColorRippleResId(R.color.colorMenuConsumer);
        }
    }

    public synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    /**
     * Create location request.
     */
    public void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setSmallestDisplacement(ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Build location settings request.
     */
    public void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int x_co = Math.round(x);
        int y_co = Math.round(y);
        Point x_y_points = new Point(x_co, y_co);
        LatLng latLng = mGoogleMap.getProjection().fromScreenLocation(x_y_points);
        Double latitude = latLng.latitude;
        Double longitude = latLng.longitude;
        int action = motionEvent.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                removeArea();
                mArrLatLng.clear();
                mArrLatLng.add(new LatLng(latitude, longitude));
                mBeginArea = new LatLng(latitude, longitude);
                break;
            case MotionEvent.ACTION_MOVE:
                mArrLatLng.add(new LatLng(latitude, longitude));
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.add(mBeginArea, new LatLng(latitude, longitude));
                polylineOptions.width(mStroke);
                polylineOptions.color(ContextCompat.getColor(getActivity(), R.color.colorMenuConsumer));
                mArrPolyline.add(mGoogleMap.addPolyline(polylineOptions));
                mBeginArea = new LatLng(latitude, longitude);
                break;
            case MotionEvent.ACTION_UP:
                drawMap();
                break;
            default:
        }
        return true;
    }


    public void removeArea() {
        if (mPolyGon != null) {
            mPolyGon.remove();
        }
        for (int i = 0; i < mArrPolyline.size(); i++) {
            mArrPolyline.get(i).remove();
        }
    }


    public void clearMap() {
        removeArea();
        for (int i = 0; i < mArrMarker.size(); i++) {
            DiscoverMarker discoverMarker = mArrMarker.get(i);
            discoverMarker.getMarker().remove();
        }
    }

    /**
     * Move to current location
     */
    public void moveToCurrentLocation() {
        if (mCurrentLocation != null) {
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude())));
        }
    }


    public void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient,
                this
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
            }
        });
    }

    public void startLocationUpdates() {
//        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
//                .setResultCallback(new ResultCallback<Status>() {
//                    @Override
//                    public void onResult(@NonNull Status status) {
//                    }
//                });
    }

    public void updatePager() {
        mDataReady = true;
        if (mMapReady) {
            mCallApiDev = true;
            mItemTouchHelperCallback.setDraw(true);
            ConditionFull conditionFull = CurrentSaveSearch.getInstance().getSearchDetail().getConditions().get(0);
            Log.e("conditionFull", conditionFull.toString());
            mFt = conditionFull.getFt() == null ? "" : conditionFull.getFt().toString();
            mMaxPrice = conditionFull.getMaxPrice() == null ? "" : conditionFull.getMaxPrice();
            mMinPrice = conditionFull.getMinPrice() == null ? "" : conditionFull.getMinPrice();
            mBed = conditionFull.getBr() == null ? "" : conditionFull.getBr();
            mBath = conditionFull.getAr() == null ? "" : conditionFull.getAr();
            mMinLs = conditionFull.getMinLs() == null ? "" : conditionFull.getMinLs();
            mMaxLs = conditionFull.getMaxLs() == null ? "" : conditionFull.getMaxLs();
            mMinLsf = conditionFull.getMinLsf() == null ? "" : conditionFull.getMinLsf();
            mMaxLsf = conditionFull.getMaxLsf() == null ? "" : conditionFull.getMaxLsf();
            mMinYb = conditionFull.getMinYb() == null ? "" : conditionFull.getMinYb();
            mMaxYb = conditionFull.getMaxYb() == null ? "" : conditionFull.getMaxYb();
            mPt = conditionFull.getPt() == null ? "" : conditionFull.getPt();
            mKeyword = conditionFull.getKeyword() == null ? "" : conditionFull.getKeyword();
            mDc = conditionFull.getDc() == null ? "" : conditionFull.getDc();

            if (CurrentSaveSearch.getInstance().getSearchDetail().getConditions().get(0).getSw() != null) {
                mLatLngBounds = new LatLngBounds(
                        Utils.getPositionFromLocation(CurrentSaveSearch.getInstance().getSearchDetail().getConditions().get(0).getSw()),
                        Utils.getPositionFromLocation(CurrentSaveSearch.getInstance().getSearchDetail().getConditions().get(0).getNe()));
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(mLatLngBounds, 0));
                Log.e("latLngBounds", mLatLngBounds.toString());
                Log.e("cc", CurrentSaveSearch.getInstance().getSearchDetail().getConditions().get(0).getSw());
                Log.e("cc", CurrentSaveSearch.getInstance().getSearchDetail().getConditions().get(0).getNe());
                Log.e("sana", Utils.getPositionFromLocation(CurrentSaveSearch.getInstance().getSearchDetail().getConditions().get(0).getSw()).toString());
                Log.e("sana", Utils.getPositionFromLocation(CurrentSaveSearch.getInstance().getSearchDetail().getConditions().get(0).getNe()).toString());

            }
        }
    }

    public void clearPager() {
        mRvListing.removeAllViews();
        mRvListingMap.removeAllViews();
        mLayoutEmpty.setVisibility(View.GONE);
        mListAdapterSearch.clear();
        mMapAdapterSearch.clear();
        mRvListing.setVisibility(View.VISIBLE);
        mLayoutMap.setVisibility(View.GONE);
//        mSwitch.setChecked(false);
//        mTvTotalListing.setText("");
        mArrListingSearch.clear();
        mArrMarker.clear();
        mArrListingSearchBase.clear();
        mGoogleMap.clear();
        mCurrentMarker = null;
        mOldMarker = null;
        mCurrentPosition = -1;
        mOldPosition = -1;
        mIsAttack = false;
        clearMap();
    }

    public void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        } else {
            if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                startLocationUpdates();
                mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (mCurrentLocation != null) {

                } else {
                    startLocationUpdates();
                }
            } else {
                checkLocationSettings();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:
                startLocationUpdates();
                break;
            default:
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                Log.e("SUCCESS", "SUCCESS");
                startLocationUpdates();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                Log.e("RESOLUTION_REQUIRED", "RESOLUTION_REQUIRED");
//                try {
//                    status.startResolutionForResult(getActivity(), 2);
//                } catch (IntentSender.SendIntentException e) {
//                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (mCurrentLocation == null) {
            mCurrentLocation = location;
            moveToCurrentLocation();
        } else {
            mCurrentLocation = location;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnCameraIdleListener(this);
        mGoogleMap.setOnCameraMoveStartedListener(this);
        mGoogleMap.setOnCameraMoveListener(this);
        mGoogleMap.setOnCameraMoveCanceledListener(this);
        mGoogleMap.getUiSettings().setCompassEnabled(false);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        mGoogleMap.getUiSettings().setIndoorLevelPickerEnabled(false);
        mGoogleMap.getUiSettings().setTiltGesturesEnabled(false);
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                AnimUtils.hideViewFromBottom(mLayoutRvListing);
            }
        });
        mGoogleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMapReady = true;
                if (mDataReady) {
                    updatePager();
                }
            }
        });

        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for (int i = 0; i < mArrClusterMarker.size(); i++) {
                    if (mArrClusterMarker.get(i).getMarker().getId().equalsIgnoreCase(marker.getId())) {
                        mCallApiDev = true;
                        if (mArrClusterMarker.get(i).getCluster().getDocCount() < 10) {
                            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mArrClusterMarker.get(i).getMarker().getPosition(), 13f));
                        } else if (mArrClusterMarker.get(i).getCluster().getDocCount() > 10 && mArrClusterMarker.get(i).getCluster().getDocCount() < 100) {
                            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mArrClusterMarker.get(i).getMarker().getPosition(), 12f));
                        } else {
                            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mArrClusterMarker.get(i).getMarker().getPosition(), 11f));
                        }
                        return true;
                    }
                }

                if (mSearchMapPresenter.getCall() != null) {
                    mSearchMapPresenter.cancelSearch();
                }
                mOldMarker = mCurrentMarker;
                mCurrentMarker = marker;
                updateMarker(mOldMarker, mCurrentMarker);
                return false;
            }
        });
        mClusterManager = new ClusterManager<>(getActivity(), mGoogleMap);
        mClusterManager.setRenderer(new ClusterListingRender(getActivity(), mGoogleMap, mClusterManager, this));
    }


    @Override
    public void onDrag(MotionEvent motionEvent) {

    }


    @Override
    public void onCameraIdle() {
        Log.e("mPointNe", mPointNe.toString());
        Log.e("mPointSw", mPointSw.toString());
        mNe = Utils.pointToLocationString(mPointNe, mGoogleMap);
        mSw = Utils.pointToLocationString(mPointSw, mGoogleMap);
        if (!mFirstLoad && mLatLngBounds != null) {
            mNe = Utils.locationString(mLatLngBounds.northeast);
            mSw = Utils.locationString(mLatLngBounds.southwest);
            mFirstLoad = true;
        }
        Log.e("mNe", mNe);
        Log.e("mSw", mSw);
        if (!mMoreNotCallApi) {
            mLayoutLoading.setVisibility(View.VISIBLE);
            mSearchMapPresenter.searchMap(setRequestParams(null, null, mNe, mSw, mMinPrice, mMaxPrice, mBed, mBath, "", "", mKeyword, mFt,
                    "", "", "", "", mMinLs, mMaxLs, mMinLsf,
                    mMaxLsf, mMinYb, mMaxYb, mDc, mPt));
            mClusterManager.onCameraIdle();
        } else {
            if (mCallApiDev) {
                mLayoutLoading.setVisibility(View.VISIBLE);
                mSearchMapPresenter.searchMap(setRequestParams(null, null, mNe, mSw, mMinPrice, mMaxPrice, mBed, mBath, "", "", mKeyword, mFt,
                        "", "", "", "", mMinLs, mMaxLs, mMinLsf,
                        mMaxLsf, mMinYb, mMaxYb, mDc, mPt));
                mClusterManager.onCameraIdle();
                mCallApiDev = false;
            }
        }
    }

    @Override
    public void onCameraMove() {

    }

    @Override
    public void onCameraMoveStarted(int i) {

        mLayoutErrorZoom.setVisibility(View.GONE);
        mSaveBeforeVote = true;
        if (i == REASON_GESTURE) {
            mMoreNotCallApi = false;
            if (mLayoutRvListing.getVisibility() == View.VISIBLE) {
                AnimUtils.hideViewFromBottom(mLayoutRvListing);
            }
        }
        if (i == REASON_API_ANIMATION) {
            mMoreNotCallApi = true;
        }
        if (i == REASON_DEVELOPER_ANIMATION) {
            mMoreNotCallApi = true;
        }

        if (mSearchMapPresenter.getCall() != null) {
            mSearchMapPresenter.cancelSearch();
            mLayoutLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCameraMoveCanceled() {

    }

    @Override
    public void searchMapSuccess(ResponseSearchMap responseSearchMap) {

        if (responseSearchMap.getData().getTotal() == 0) {
            mLayoutEmpty.setVisibility(View.VISIBLE);
            mRvListing.setVisibility(View.GONE);
            showSnackBar(mLayoutBgActionMap, TypeDialog.MESSAGES, responseSearchMap.getMessage(), "searchEmpty");
            mLayoutLoading.setVisibility(View.GONE);
            return;
        }
        mLayoutEmpty.setVisibility(View.GONE);
        mRvListing.setVisibility(View.VISIBLE);
        handlerDataSearch(responseSearchMap);
        mLayoutLoading.setVisibility(View.GONE);
    }

    @Override
    public void searchMapFail(String message) {
        mLayoutLoading.setVisibility(View.GONE);
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "searchFail");
    }

    @Override
    public void searchMapFail(@StringRes int message) {
        mLayoutLoading.setVisibility(View.GONE);
        showSnackBar(mLayoutMain, TypeDialog.ERROR, message, "searchFail");
    }

    @Override
    public void yesAction(String action) {
        super.yesAction(action);
        mItemTouchHelper.closeOpenedPreItem();
        mSaveSearchPresenter.saveSearch(setRequestParams(CurrentSaveSearch.getInstance().getId(), CurrentSaveSearch.getInstance().getName(),
                mNe, mSw, mMinPrice, mMaxPrice, mBed, mBath, "", "", mKeyword, mFt,
                "", "", "", "", mMinLs, mMaxLs, mMinLsf,
                mMaxLsf, mMinYb, mMaxYb, mDc, mPt));
        showLoading();
    }

    @Override
    public void noAction(String action) {
        super.noAction(action);
        mItemTouchHelper.closeOpenedPreItem();
    }


    @Override
    public void saveSearchSuccess(SearchDetail searchDetail) {
        mSaveBeforeVote = false;
        mItemTouchHelperCallback.setDraw(true);
        hideLoading();
        showSnackBar(mLayoutMain, TypeDialog.SUCCESS, R.string.update_conditions_success, "saveSearchSuccess");
    }

    @Override
    public void saveSearchFail(String message) {
        hideLoading();
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "saveSearchFail");
    }

    @Override
    public void saveSearchFail(@StringRes int message) {
        hideLoading();
        showSnackBar(mLayoutMain, TypeDialog.ERROR, R.string.error_server, "saveSearchFail");
    }

    @Override
    public void voteSuccess(ListingFull listingFull) {
        //mListAdapterSearch.notifyItemChanged(mPosition);
    }

    @Override
    public void voteFail(String message) {

    }

    @Override
    public void voteFail(@StringRes int message) {

    }

    public Map<String, RequestBody> setRequestParams(String id, String searchName, String ne, String sw, String minPrice, String maxPrice, String minBedRoom, String minBathRoom, String squareFeet,
                                                     String lotSize, String textSearch, String filterType, String softBy, String sortMode,
                                                     String source, String status, String minLostSize, String maxLotSize, String minSquareFeet,
                                                     String maxSquareFeet, String minYear, String maxYear, String dayCaravan, String properType) {
        Map<String, RequestBody> map = new HashMap<>();
        if (searchName != null) {
            map.put("NAME", Utils.creteRbSearchMap(searchName));
        }
        if (id != null) {
            map.put("id", Utils.creteRbSearchMap(CurrentSaveSearch.getInstance().getId()));
        }
        map.put("ne", Utils.creteRbSearchMap(ne));
        map.put("sw", Utils.creteRbSearchMap(sw));
        map.put("min_pr", Utils.creteRbSearchMap(minPrice));
        map.put("max_pr", Utils.creteRbSearchMap(maxPrice));
        map.put("br", Utils.creteRbSearchMap(minBedRoom));
        map.put("ar", Utils.creteRbSearchMap(minBathRoom));
        map.put("lsf", Utils.creteRbSearchMap(squareFeet));
        map.put("ls", Utils.creteRbSearchMap(lotSize));
        map.put("k", Utils.creteRbSearchMap(textSearch));
        map.put("ft", Utils.creteRbSearchMap(filterType));
        map.put("sb", Utils.creteRbSearchMap(softBy));
        map.put("sm", Utils.creteRbSearchMap(sortMode));
        map.put("src", Utils.creteRbSearchMap(source));
        map.put("st", Utils.creteRbSearchMap(status));
        map.put("min_ls", Utils.creteRbSearchMap(minLostSize));
        map.put("max_ls", Utils.creteRbSearchMap(maxLotSize));
        map.put("min_lsf", Utils.creteRbSearchMap(minSquareFeet));
        map.put("max_lsf", Utils.creteRbSearchMap(maxSquareFeet));
        map.put("min_yb", Utils.creteRbSearchMap(minYear));
        map.put("max_yb", Utils.creteRbSearchMap(maxYear));
        map.put("dc", Utils.creteRbSearchMap(dayCaravan));
        map.put("pt", Utils.creteRbSearchMap(properType));
        map.put("zm", Utils.creteRbSearchMap(String.valueOf((int) mGoogleMap.getCameraPosition().zoom)));
        return map;
    }


    public void resetFilter() {
        mFt = "sale";
        mMaxPrice = "";
        mMinPrice = "";
        mBed = "";
        mBath = "";
        mMinLs = "";
        mMaxLs = "";
        mMinLsf = "";
        mMaxLsf = "";
        mMinYb = "";
        mMaxYb = "";
        mPt = "";
        mKeyword = "";
        mDc = "";
    }

    public void applyFilter(String ft, String minPrice, String maxPrice, String bed, String bath, String minLs, String maxLs,
                            String minSf, String maxSf, String minYb, String maxYb, String pt, String dayHc, String k) {

        mFt = ft;
        mMaxPrice = maxPrice;
        mMinPrice = minPrice;
        mBed = bed;
        mBath = bath;
        mMinLs = minLs;
        mMaxLs = maxLs;
        mMinLsf = minSf;
        mMaxLsf = maxSf;
        mMinYb = minYb;
        mMaxYb = maxYb;
        mPt = pt;
        mKeyword = k;
        mDc = dayHc;
        mLayoutLoading.setVisibility(View.VISIBLE);
        mSearchMapPresenter.searchMap(setRequestParams(null, null, mNe, mSw, mMinPrice, mMaxPrice, mBed, mBath, "", "", mKeyword, mFt,
                "", "", "", "", mMinLs, mMaxLs, mMinLsf,
                mMaxLsf, mMinYb, mMaxYb, mDc, mPt));
        mClusterManager.onCameraIdle();

    }

    public void initData() {
        mLayoutMap.post(new Runnable() {
            @Override
            public void run() {
                mPointNe = new Point(Utils.widthScreen, mLayoutMap.getTop());
                mPointSw = new Point(0, mLayoutMap.getBottom());
            }
        });
    }

    public void changeView(boolean b) {
        mShowMap = b;
        if (b) {
            mLayoutMap.setVisibility(View.VISIBLE);
            mLayoutListingList.setVisibility(View.GONE);
        } else {
            mLayoutMap.setVisibility(View.GONE);
            mLayoutListingList.setVisibility(View.VISIBLE);
        }
    }

    public boolean isViewMap() {
        return mShowMap;
    }

    public void handlerDataSearch(ResponseSearchMap responseSearchMap) {
        mRvListingMap.swapAdapter(mMapAdapterSearch, true);
        mRvListing.swapAdapter(mListAdapterSearch, true);
        mArrListingList.clear();
        mArrListingList.addAll(responseSearchMap.getData().getArrListingList());
        mListAdapterSearch.clear();
        mMapAdapterSearch.clear();
        mArrListingSearch.clear();
        mArrListingSearchBase.clear();
        mArrMarker.clear();
        mGoogleMap.clear();
        mArrClusterMarker.clear();

        Log.e("mCurrentPosition", String.valueOf(mCurrentPosition));
        Log.e("mOldPosition", String.valueOf(mOldPosition));

        mCurrentMarker = null;
        mOldMarker = null;
        mCurrentPosition = -1;
        mOldPosition = -1;
        mIsAttack = false;

//        ArrayList<ListingSearchMap> listingSearchMap = new ArrayList<>();
//        listingSearchMap.addAll(responseSearchMap.getData().getArrListing());
//        ArrayList<ListingSearchMap> listingSearchMap1 = new ArrayList<>();
//        listingSearchMap1.addAll(responseSearchMap.getData().getArrListing());
//        Log.e("listingSearchMap", String.valueOf(listingSearchMap.size()));
//        Log.e("mArrListingSearch", String.valueOf(mArrListingSearch.size()));
//        Log.e("mArrListingSearchBase", String.valueOf(mArrListingSearchBase.size()));
//        for (int i = 0; i < listingSearchMap.size(); i++) {
//            for (int j = 0; j < mArrListingSearch.size(); j++) {
//                if (listingSearchMap.get(i).getId().equalsIgnoreCase(mArrListingSearch.get(j).getListing().getId())) {
//                    Log.e("Cc", "Cc");
//                    listingSearchMap.remove(i);
//                    i--;
//                }
//            }
//        }
//
//        ArrayList<ListingItem> listingItem = new ArrayList<>();
//        int count = 0;
//        for (int i = 0; i < mArrListingSearch.size(); i++) {
//            for (int j = 0; j < listingSearchMap1.size(); j++) {
//                if (!mArrListingSearch.get(i).getListing().getId().equalsIgnoreCase(listingSearchMap1.get(j).getId())) {
//                    count++;
//                }
//            }
//            if (count == listingSearchMap1.size()) {
//                listingItem.add(mArrListingSearch.get(i));
//                mArrListingSearch.remove(i);
//                i--;
//            }
//            count = 0;
//        }
//
//        for (int i = 0; i < listingItem.size(); i++) {
//            for (int j = 0; j < mArrMarker.size(); j++) {
//                if (listingItem.get(i).getListing().getId().equalsIgnoreCase(mArrMarker.get(j).getData().getListing().getId())) {
//                    mArrMarker.get(j).getMarker().remove();
//                    mArrMarker.remove(j);
//                    j--;
//                }
//            }
//        }
//
//        for (int i = 0; i < listingItem.size(); i++) {
//            for (int j = 0; j < mMapAdapterSearch.getItemCount(); j++) {
//                if (listingItem.get(i).getListing().getId().equalsIgnoreCase(mMapAdapterSearch.getAdapterItem(j).getListing().getListing().getId())) {
//                    mMapAdapterSearch.remove(j);
//                    j--;
//                }
//            }
//        }

        createCluster(responseSearchMap.getData().getArrCluster());
        createMarker(responseSearchMap.getData().getArrListing());
        createList();
    }

    public void createList() {
        if (mArrListingList.size() == 0) {
            mLayoutEmpty.setVisibility(View.VISIBLE);
            mRvListing.setVisibility(View.GONE);
        } else {
            mLayoutEmpty.setVisibility(View.GONE);
            mRvListing.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < mArrListingList.size(); i++) {
            ListingItem listingItem = new ListingItem();
            ListingSearchMap listingSearchMap = new ListingSearchMap();
            ListingListSearchMap listingListSearchMap = mArrListingList.get(i);
            listingSearchMap.setAddress1(listingListSearchMap.getAddress1());
            listingSearchMap.setAddress2(listingListSearchMap.getAddress2());
            listingSearchMap.setBaths(listingListSearchMap.getBaths());
            listingSearchMap.setBeds(listingListSearchMap.getBeds());
            listingSearchMap.setFavorite(listingListSearchMap.getFavorite());
            listingSearchMap.setId(listingListSearchMap.getId());
            listingSearchMap.setLat(listingListSearchMap.getLat());
            listingSearchMap.setLng(listingListSearchMap.getLng());
            listingSearchMap.setPool(listingListSearchMap.getPool());
            listingSearchMap.setThumbnail(listingListSearchMap.getThumbnail());
            listingSearchMap.setPrice(listingListSearchMap.getPrice());
            listingSearchMap.setLivingSquare(listingListSearchMap.getLivingSquare());
            listingItem.setListing(listingSearchMap);
            for (int j = 0; j < CaravanQueue.getInstance().getIds().size(); j++) {
                if (listingItem.getListing().getId().equalsIgnoreCase(CaravanQueue.getInstance().getIds().get(j))) {
                    listingItem.setQueue(true);
                }
            }
            listingItem.setFavorite(listingListSearchMap.getFavorite());
            SearchListItem searchListItem = new SearchListItem();
            searchListItem.setContext(getActivity());
            searchListItem.setPosition(mArrMarker.size() - 1);
            searchListItem.setListing(listingItem);
            searchListItem.setListener(this);
            mListAdapterSearch.add(searchListItem);
        }

    }

    public void createMarker(ArrayList<ListingSearchMap> listingsSearch) {
        mClusterManager.clearItems();
        for (int i = 0; i < listingsSearch.size(); i++) {
            ListingItem listingItem = new ListingItem();
            listingItem.setListing(listingsSearch.get(i));
            listingItem.setFavorite(listingsSearch.get(i).getFavorite());
            DiscoverMarker discoverMarker = new DiscoverMarker(null, StatusMarker.HAVE_NOT_BEEN_VIEWED, listingItem);
            CurrentCreateSavedSearch.getInstance().getListingId().add(listingsSearch.get(i).getId());
            mClusterManager.addItem(discoverMarker);
        }
        mClusterManager.cluster();
    }

    public void createCluster(ArrayList<ClustersSearchMap> clusterSearch) {

        IconGenerator iconGenerator = new IconGenerator(getContext());
        for (int i = 0; i < clusterSearch.size(); i++) {
            ClustersSearchMap clusters = clusterSearch.get(i);
            if (clusters.getBounds().getBottomRight().getLat() == clusters.getBounds().getTopLeft().getLat() &&
                    clusters.getBounds().getBottomRight().getLon() == clusters.getBounds().getTopLeft().getLon()) {
                clusters.setTwoListing(true);
            }
            MarkerOptions markerOptions = new MarkerOptions();
            if (clusters.isTwoListing()) {
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(Utils.getIconGeneratorClusterTwoListing(iconGenerator, getContext()).makeIcon()));
            } else {
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(Utils.getIconGeneratorCluster(iconGenerator, clusters, getContext()).makeIcon()));
            }
            markerOptions.position(new LatLng(clusters.getLocation().getLat(), clusters.getLocation().getLon()));
            Marker marker = mGoogleMap.addMarker(markerOptions);
            ClusterMarker clusterMarker = new ClusterMarker(marker, clusters);
            mArrClusterMarker.add(clusterMarker);
        }
    }

    public void moveToMarkerWhenScrollList(String id) {
        for (int i = 0; i < mArrMarker.size(); i++) {
            ListingItem consumerMapSearch = mArrMarker.get(i).getData();
            if (consumerMapSearch.getListing().getId().equalsIgnoreCase(id)) {
                LatLng latLng = new LatLng(Double.parseDouble(consumerMapSearch.getListing().getLat()),
                        Double.parseDouble(consumerMapSearch.getListing().getLng()));
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                if (mOldPosition == -1) {
                    updateMarker(null, mArrMarker.get(i).getMarker());
                } else {
                    updateMarker(mArrMarker.get(mOldPosition).getMarker(), mArrMarker.get(i).getMarker());
                }
            }
        }
    }

    public void updateMarker(Marker oldMarker, Marker currentMarker) {
        IconGenerator iconFactory = new IconGenerator(getActivity());
        ArrayList<DiscoverMarker> discoverMarkers = new ArrayList<>();
        for (int i = 0; i < mArrMarker.size(); i++) {
            if (mArrMarker.get(i).getMarker().isVisible()) {
                discoverMarkers.add(mArrMarker.get(i));

            }
        }

        for (int i = 0; i < discoverMarkers.size(); i++) {
            Marker marker1 = discoverMarkers.get(i).getMarker();
            if (currentMarker.getId().equalsIgnoreCase(marker1.getId())) {
                marker1.setIcon(BitmapDescriptorFactory.fromBitmap(Utils.getIconGeneratorRound(iconFactory, StatusMarker.SELECTED,
                        getActivity(), discoverMarkers.get(i).getData().getListing().getPrice())
                        .makeIcon()));
                discoverMarkers.get(i).setStatus(StatusMarker.SELECTED);
                openListWithMarkerSelected(i);
            }
            if (oldMarker != null) {
                if (oldMarker.getId().equalsIgnoreCase(marker1.getId()) && !oldMarker.getId().equalsIgnoreCase(currentMarker.getId())) {
                    marker1.setIcon(BitmapDescriptorFactory.fromBitmap(Utils.getIconGeneratorRound(iconFactory, StatusMarker.HAVE_BEEN_VIEWED, getActivity(), discoverMarkers.get(i).getData().getListing().getPrice())
                            .makeIcon()));
                    discoverMarkers.get(i).setStatus(StatusMarker.HAVE_BEEN_VIEWED);
                }
            }
        }
        for (int i = 0; i < mArrMarker.size(); i++) {
            Marker marker1 = mArrMarker.get(i).getMarker();
            if (oldMarker != null) {
                if (oldMarker.getId().equalsIgnoreCase(marker1.getId()) && !oldMarker.getId().equalsIgnoreCase(currentMarker.getId())) {
                    marker1.setIcon(BitmapDescriptorFactory.fromBitmap(Utils.getIconGeneratorRound(iconFactory, StatusMarker.HAVE_BEEN_VIEWED, getActivity(), mArrMarker.get(i).getData().getListing().getPrice())
                            .makeIcon()));
                    mArrMarker.get(i).setStatus(StatusMarker.HAVE_BEEN_VIEWED);
                }
            }
        }
    }

    public void openListWithMarkerSelected(final int position) {
        AnimUtils.showViewFromBottom(mLayoutRvListing);
        mRvListingMap.post(new Runnable() {
            @Override
            public void run() {
                mRvListingMap.smoothScrollToPosition(position);
            }
        });
    }

    private void drawMap() {
        mIsAttack = false;
        CurrentCreateSavedSearch.getInstance().getListingId().clear();
        mArrListingSearch.clear();
        mRectOptions = new PolygonOptions();
        mRectOptions.addAll(mArrLatLng);
        mRectOptions.strokeWidth(mStroke);
        mRectOptions.strokeColor(ContextCompat.getColor(getActivity(), R.color.colorMenuConsumer));
        mPolyGon = mGoogleMap.addPolygon(mRectOptions);
        mPolyGon.setFillColor(ContextCompat.getColor(getActivity(), R.color.colorFillArea));
        for (int i = 0; i < mArrMarker.size(); i++) {
            DiscoverMarker discoverMarker = mArrMarker.get(i);
            if (PolyUtil.containsLocation(new LatLng(Double.parseDouble(discoverMarker.getData().getListing().getLat()),
                    Double.parseDouble(discoverMarker.getData().getListing().getLng())), mArrLatLng, true)) {
                discoverMarker.getMarker().setVisible(true);
                mArrListingSearch.add(discoverMarker.getData());
                CurrentCreateSavedSearch.getInstance().getListingId().add(discoverMarker.getData().getListing().getId());
            } else {
                discoverMarker.getMarker().setVisible(false);
            }
        }
        ArrayList<Marker> markers = new ArrayList<>();
        markers.addAll(mClusterManager.getClusterMarkerCollection().getMarkers());
        for (int i = 0; i < markers.size(); i++) {
            if (PolyUtil.containsLocation(markers.get(i).getPosition(), mArrLatLng, true)) {
                markers.get(i).setVisible(true);
            } else {
                markers.get(i).setVisible(false);
            }
        }
        for (int i = 0; i < mArrClusterMarker.size(); i++) {
            if (PolyUtil.containsLocation(mArrClusterMarker.get(i).getMarker().getPosition(), mArrLatLng, true)) {
                mArrClusterMarker.get(i).getMarker().setVisible(true);
            } else {
                mArrClusterMarker.get(i).getMarker().setVisible(false);
            }
        }
        mListAdapterSearch.clear();
        mMapAdapterSearch.clear();
        for (int i = 0; i < mArrListingSearch.size(); i++) {
            ListingItem listingItem = new ListingItem();
            listingItem.setListing(mArrListingSearch.get(i).getListing());

            SearchMapItem searchMapItem = new SearchMapItem();
            listingItem.setFavorite(mArrListingSearch.get(i).getListing().getFavorite());
            searchMapItem.setListing(listingItem);
            searchMapItem.setContext(getActivity());
            searchMapItem.setPosition(i);
            searchMapItem.setListener(this);

            SearchListItem searchListItem = new SearchListItem();
            searchListItem.setContext(getActivity());
            listingItem.setFavorite(mArrListingSearch.get(i).getListing().getFavorite());
            searchListItem.setListing(listingItem);
            searchListItem.setPosition(i);
            searchListItem.setListener(this);
            mMapAdapterSearch.add(searchMapItem);
            mListAdapterSearch.add(searchListItem);
        }
        mRvListing.getAdapter().notifyDataSetChanged();
        mLayoutDraw.setVisibility(View.GONE);
    }

    public void refreshMap() {
        mArrListingSearch.clear();
        removeArea();
        for (int i = 0; i < mArrMarker.size(); i++) {
            DiscoverMarker discoverMarker = mArrMarker.get(i);
            discoverMarker.getMarker().setVisible(true);
            mArrListingSearch.add(mArrMarker.get(i).getData());
        }
        for (int i = 0; i < mArrClusterMarker.size(); i++) {
            mArrClusterMarker.get(i).getMarker().setVisible(true);
        }
        mListAdapterSearch.clear();
        mMapAdapterSearch.clear();
        for (int i = 0; i < mArrListingSearch.size(); i++) {
            ListingItem listingItem = new ListingItem();
            listingItem.setListing(mArrListingSearch.get(i).getListing());

            SearchMapItem searchMapItem = new SearchMapItem();
            searchMapItem.setPosition(i);
            listingItem.setFavorite(mArrListingSearch.get(i).getListing().getFavorite());
            searchMapItem.setListing(listingItem);
            searchMapItem.setContext(getActivity());
            searchMapItem.setListener(this);
            mMapAdapterSearch.add(searchMapItem);
        }
        for (int i = 0; i < mArrListingList.size(); i++) {
            ListingItem listingItem = new ListingItem();
            ListingSearchMap listingSearchMap = new ListingSearchMap();
            ListingListSearchMap listingListSearchMap = mArrListingList.get(i);
            listingSearchMap.setAddress1(listingListSearchMap.getAddress1());
            listingSearchMap.setAddress2(listingListSearchMap.getAddress2());
            listingSearchMap.setBaths(listingListSearchMap.getBaths());
            listingSearchMap.setBeds(listingListSearchMap.getBeds());
            listingSearchMap.setFavorite(listingListSearchMap.getFavorite());
            listingSearchMap.setId(listingListSearchMap.getId());
            listingSearchMap.setLat(listingListSearchMap.getLat());
            listingSearchMap.setLng(listingListSearchMap.getLng());
            listingSearchMap.setPool(listingListSearchMap.getPool());
            listingSearchMap.setThumbnail(listingListSearchMap.getThumbnail());
            listingSearchMap.setPrice(listingListSearchMap.getPrice());
            listingSearchMap.setLivingSquare(listingListSearchMap.getLivingSquare());
            listingItem.setListing(listingSearchMap);
            for (int j = 0; j < CaravanQueue.getInstance().getIds().size(); j++) {
                if (listingItem.getListing().getId().equalsIgnoreCase(CaravanQueue.getInstance().getIds().get(j))) {
                    listingItem.setQueue(true);
                }
            }
            listingItem.setFavorite(listingListSearchMap.getFavorite());
            SearchListItem searchListItem = new SearchListItem();
            searchListItem.setContext(getActivity());
            searchListItem.setPosition(mArrMarker.size() - 1);
            searchListItem.setListing(listingItem);
            searchListItem.setListener(this);
            mListAdapterSearch.add(searchListItem);
        }
        AnimUtils.hideViewFromBottom(mLayoutRvListing);
    }

    public void clearSearch() {
        mArrListingSearch.clear();
        mCurrentMarker = null;
        mOldMarker = null;
        mCurrentPosition = -1;
        mOldPosition = -1;
        mIsAttack = false;
        removeArea();
        IconGenerator iconFactory = new IconGenerator(getActivity());
        for (int i = 0; i < mArrMarker.size(); i++) {
            Marker marker1 = mArrMarker.get(i).getMarker();
            marker1.setVisible(true);
            marker1.setIcon(BitmapDescriptorFactory.fromBitmap(Utils.getIconGeneratorRound(iconFactory, StatusMarker.HAVE_NOT_BEEN_VIEWED,
                    getActivity(), mArrMarker.get(i).getData().getListing().getPrice()).makeIcon()));
            mArrMarker.get(i).setStatus(StatusMarker.HAVE_NOT_BEEN_VIEWED);
            mArrListingSearch.add(mArrMarker.get(i).getData());
        }
        AnimUtils.hideViewFromBottom(mLayoutRvListing);
    }


    @Override
    public void addMarkerList(DiscoverMarker discoverMarker) {

        for (int i = 0; i < mArrMarker.size(); i++) {
            if (discoverMarker.getData().getListing().getId().equalsIgnoreCase(mArrMarker.get(i).getData().getListing().getId())) {
                return;
            }
        }
        for (int j = 0; j < CaravanQueue.getInstance().getIds().size(); j++) {
            if (discoverMarker.getData().getListing().getId().equalsIgnoreCase(CaravanQueue.getInstance().getIds().get(j))) {
                discoverMarker.getData().setQueue(true);
            }
        }

        mArrMarker.add(discoverMarker);
        SearchMapItem searchMapItem = new SearchMapItem();
        searchMapItem.setListing(discoverMarker.getData());
        searchMapItem.setPosition(mArrMarker.size() - 1);
        searchMapItem.setContext(getActivity());
        searchMapItem.setListener(this);
        mMapAdapterSearch.add(searchMapItem);
    }

    @Override
    public void onClusterItemRendered() {

        mArrListingSearch.clear();
        mArrListingSearchBase.clear();
        for (int i = 0; i < mArrMarker.size(); i++) {
            mArrListingSearch.add(mArrMarker.get(i).getData());
            mArrListingSearchBase.add(mArrMarker.get(i).getData());
        }
    }


    @Override
    public void beforeClusterRendered() {

    }

    @Override
    public void onClustersChanged() {

    }

    @Override
    public void addMarkerFullList(DiscoverMarkerFull discoverMarkerFull) {

    }

    @Override
    public void voteListing(int position, String lid, String type, String reason) {

        if (mSaveBeforeVote) {
            if (type.equalsIgnoreCase("map")) {
                SearchMapItem searchMapItem = null;
                int mPosition = 0;
                for (int i = 0; i < mMapAdapterSearch.getItemCount(); i++) {
                    if (mMapAdapterSearch.getAdapterItem(i).getListing().getListing().getId().equalsIgnoreCase(lid)) {
                        searchMapItem = mMapAdapterSearch.getAdapterItem(i);
                        mPosition = i;
                    }
                }
                if (reason.equalsIgnoreCase("scheduleMap")) {
                    if (searchMapItem.getListing().isQueue()) {
                        searchMapItem.getListing().setQueue(false);
                        EventBus.getDefault().post(new EventQueue(false, searchMapItem.getListing().getListing().getId(),
                                null, null, searchMapItem.getListing().getListing()));
                    } else {
                        searchMapItem.getListing().setQueue(true);
                        EventBus.getDefault().post(new EventQueue(true, searchMapItem.getListing().getListing().getId(),
                                null, null, searchMapItem.getListing().getListing()));
                    }
                }
                if (reason.equalsIgnoreCase("favoriteMap")) {
                    if (searchMapItem.getListing().isFavorite()) {
                        searchMapItem.getListing().setFavorite(false);
                        EventBus.getDefault().post(new EventFavorite(searchMapItem.getListing().getListing().getId(), false));
                    } else {
                        searchMapItem.getListing().setFavorite(true);
                        EventBus.getDefault().post(new EventFavorite(searchMapItem.getListing().getListing().getId(), true));
                    }
                }
                mMapAdapterSearch.notifyItemChanged(mPosition);
                return;
            }

            if (type.equalsIgnoreCase("up")) {
                int mPosition = 0;
                SearchListItem searchListItem = null;
                for (int i = 0; i < mListAdapterSearch.getItemCount(); i++) {
                    if (mListAdapterSearch.getAdapterItem(i).getListing().getListing().getId().equalsIgnoreCase(lid)) {
                        searchListItem = mListAdapterSearch.getAdapterItem(i);
                        mPosition = i;
                    }
                }
                if (reason.equalsIgnoreCase("schedule")) {
                    if (searchListItem.getListing().isQueue()) {
                        searchListItem.getListing().setQueue(false);
                        EventBus.getDefault().post(new EventQueue(false, searchListItem.getListing().getListing().getId(), null, null, searchListItem.getListing().getListing()));
                    } else {
                        searchListItem.getListing().setQueue(true);
                        EventBus.getDefault().post(new EventQueue(true, searchListItem.getListing().getListing().getId(), null, null, searchListItem.getListing().getListing()));
                    }
                }
                if (reason.equalsIgnoreCase("favorite")) {

                    if (searchListItem.getListing().isFavorite()) {
                        searchListItem.getListing().setFavorite(false);
                        EventBus.getDefault().post(new EventFavorite(searchListItem.getListing().getListing().getId(), false));
                    } else {
                        searchListItem.getListing().setFavorite(true);
                        EventBus.getDefault().post(new EventFavorite(searchListItem.getListing().getListing().getId(), true));
                    }
                }
                mListAdapterSearch.notifyItemChanged(mPosition);
                return;
            }
            showSnackBar(mLayoutMain, TypeDialog.MESSAGES, R.string.error_vote, "vote");
        } else {
            if (type.equalsIgnoreCase("map")) {
                SearchMapItem searchMapItem = null;
                int mPosition = 0;
                for (int i = 0; i < mMapAdapterSearch.getItemCount(); i++) {
                    if (mMapAdapterSearch.getAdapterItem(i).getListing().getListing().getId().equalsIgnoreCase(lid)) {
                        searchMapItem = mMapAdapterSearch.getAdapterItem(i);
                        mPosition = i;
                    }
                }
                if (reason.equalsIgnoreCase("scheduleMap")) {
                    if (searchMapItem.getListing().isQueue()) {
                        searchMapItem.getListing().setQueue(false);
                        EventBus.getDefault().post(new EventQueue(false, searchMapItem.getListing().getListing().getId(),
                                null, null, searchMapItem.getListing().getListing()));
                    } else {
                        searchMapItem.getListing().setQueue(true);
                        EventBus.getDefault().post(new EventQueue(true, searchMapItem.getListing().getListing().getId(),
                                null, null, searchMapItem.getListing().getListing()));
                    }
                }
                if (reason.equalsIgnoreCase("favoriteMap")) {
                    if (searchMapItem.getListing().isFavorite()) {
                        searchMapItem.getListing().setFavorite(false);
                        EventBus.getDefault().post(new EventFavorite(searchMapItem.getListing().getListing().getId(), false));
                    } else {
                        searchMapItem.getListing().setFavorite(true);
                        EventBus.getDefault().post(new EventFavorite(searchMapItem.getListing().getListing().getId(), true));
                    }
                }
                mMapAdapterSearch.notifyItemChanged(mPosition);
            } else {
                int mPosition = 0;
                SearchListItem searchListItem = null;
                for (int i = 0; i < mListAdapterSearch.getItemCount(); i++) {
                    if (mListAdapterSearch.getAdapterItem(i).getListing().getListing().getId().equalsIgnoreCase(lid)) {
                        searchListItem = mListAdapterSearch.getAdapterItem(i);
                        mPosition = i;
                    }
                }
                if (type.equalsIgnoreCase("up")) {

                    if (reason.equalsIgnoreCase("schedule")) {
                        if (searchListItem.getListing().isQueue()) {
                            searchListItem.getListing().setQueue(false);
                            EventBus.getDefault().post(new EventQueue(false, searchListItem.getListing().getListing().getId(), null, null, searchListItem.getListing().getListing()));
                        } else {
                            searchListItem.getListing().setQueue(true);
                            EventBus.getDefault().post(new EventQueue(true, searchListItem.getListing().getListing().getId(), null, null, searchListItem.getListing().getListing()));
                        }
                    }
                    if (reason.equalsIgnoreCase("favorite")) {

                        if (searchListItem.getListing().isFavorite()) {
                            searchListItem.getListing().setFavorite(false);
                            EventBus.getDefault().post(new EventFavorite(searchListItem.getListing().getListing().getId(), false));
                        } else {
                            searchListItem.getListing().setFavorite(true);
                            EventBus.getDefault().post(new EventFavorite(searchListItem.getListing().getListing().getId(), true));
                        }
                    }
                    if (reason.equalsIgnoreCase("super_vote")) {
                        if (searchListItem.getListing().isSuperVote()) {
                            searchListItem.getListing().setSuperVote(false);
                        } else {
                            searchListItem.getListing().setSuperVote(true);
                        }
                    }
                    if (reason.equalsIgnoreCase("share")) {
                        if (searchListItem.getListing().isShare()) {
                            searchListItem.getListing().setShare(false);
                        } else {
                            searchListItem.getListing().setShare(true);
                        }
                    }

                } else {
                    if (reason.equalsIgnoreCase("size")) {
                        if (searchListItem.getListing().isSize()) {
                            searchListItem.getListing().setSize(false);
                        } else {
                            searchListItem.getListing().setSize(true);
                        }
                    }
                    if (reason.equalsIgnoreCase("condition")) {
                        if (searchListItem.getListing().isCondition()) {
                            searchListItem.getListing().setCondition(false);
                        } else {
                            searchListItem.getListing().setCondition(true);
                        }
                    }
                    if (reason.equalsIgnoreCase("location")) {
                        if (searchListItem.getListing().isLocation()) {
                            searchListItem.getListing().setLocation(false);
                        } else {
                            searchListItem.getListing().setLocation(true);
                        }
                    }
                    if (reason.equalsIgnoreCase("price")) {
                        if (searchListItem.getListing().isPrice()) {
                            searchListItem.getListing().setPrice(false);
                        } else {
                            searchListItem.getListing().setPrice(true);
                        }
                    }
                }
                mListAdapterSearch.notifyItemChanged(mPosition);
            }
            mVotePresenter.voteListing(CurrentSaveSearch.getInstance().getId(), lid, type, reason, "note");

        }

    }
}
