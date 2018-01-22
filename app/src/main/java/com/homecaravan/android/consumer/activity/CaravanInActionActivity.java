package com.homecaravan.android.consumer.activity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.CycleInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Route;
import com.bumptech.glide.Glide;
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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;
import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.R;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.adapter.CIAParticipantsInListingAdapter;
import com.homecaravan.android.consumer.adapter.CaravanActionAdapter;
import com.homecaravan.android.consumer.adapter.CaravanActionTimeAdapter;
import com.homecaravan.android.consumer.base.BaseActivity;
import com.homecaravan.android.consumer.consumerbase.ConsumerUser;
import com.homecaravan.android.consumer.consumermvp.caravanmvp.BuyerCancelApptPresenter;
import com.homecaravan.android.consumer.consumermvp.caravanmvp.BuyerCancelApptView;
import com.homecaravan.android.consumer.direction.MyDirection;
import com.homecaravan.android.consumer.direction.MyDirectionCallback;
import com.homecaravan.android.consumer.direction.MyGoogleDirection;
import com.homecaravan.android.consumer.listener.IBottomSheetCIAListener;
import com.homecaravan.android.consumer.message.messagegetthreadidmvp.GetThreadIdPresenter;
import com.homecaravan.android.consumer.message.messagegetthreadidmvp.IGetThreadIdView;
import com.homecaravan.android.consumer.model.CurrentCaravan;
import com.homecaravan.android.consumer.model.EventCIA;
import com.homecaravan.android.consumer.model.UserData;
import com.homecaravan.android.consumer.model.cia.BottomSheetMarker;
import com.homecaravan.android.consumer.model.cia.ListingCIAMarker;
import com.homecaravan.android.consumer.model.cia.ParticipantsMarker;
import com.homecaravan.android.consumer.model.responseapi.AppointmentShowing;
import com.homecaravan.android.consumer.model.responseapi.CaravanParticipants;
import com.homecaravan.android.consumer.model.responseapi.ResponseCaravan;
import com.homecaravan.android.consumer.model.responseapi.ResponseMessageGetThreadId;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.consumer.utils.viewanimator.AnimationListener;
import com.homecaravan.android.consumer.utils.viewanimator.ViewAnimator;
import com.homecaravan.android.consumer.widget.floatactionbutton.FloatingActionMenu;
import com.homecaravan.android.ui.CircleImageView;
import com.homecaravan.android.ui.MapWrapperLayout;
import com.homecaravan.android.ui.MySupportMapFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class CaravanInActionActivity extends BaseActivity implements
        OnMapReadyCallback,
        MapWrapperLayout.OnDragListener,
        MyDirectionCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<LocationSettingsResult>,
        LocationListener,
        FloatingActionMenu.OnMenuToggleListener,
        IBottomSheetCIAListener,
        BuyerCancelApptView,
        IGetThreadIdView {

    private final String TAG = "DaoDiDem";
    // Google client to interact with Google API
    private ArrayList<Polyline> mPolylinePaths = new ArrayList<>();
    ////////
//    private final int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationManager mLocationManager;
    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;
    private LocationRequest mLocationRequest;
    // Location updates intervals in sec
    private int UPDATE_INTERVAL;
    private int FATEST_INTERVAL;
    //    private int DISPLACEMENT = 10; // 10 meters//pattern to custom polyline
    private final int PATTERN_DASH_LENGTH_PX = 20;
    private final int PATTERN_GAP_LENGTH_PX = 20;
    //    private final PatternItem DOT = new Dot();
    private final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    private final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    private final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);

    private GoogleMap mGoogleMap;
    private IconGenerator mIconFactory;
    private LatLngBounds.Builder builder = new LatLngBounds.Builder();
    private int countBounds = 0;
    private ArrayList<String> mArrTime = new ArrayList<>();
    private ArrayList<ListingCIAMarker> mArrListingCIAMarker = new ArrayList<>();
    private ArrayList<ParticipantsMarker> mParticipantsMarker = new ArrayList<>();
    private ArrayList<ParticipantsMarker> mParticipantsGoneThroughMarker = new ArrayList<>();
    private ParticipantsMarker mCurrentMarker = new ParticipantsMarker();
    private ListingCIAMarker mCurrentListing, mPreviousListing;
    private BottomSheetMarker mBottomSheetMarker = new BottomSheetMarker();
    private ResponseCaravan.CaravanData mCaravanData;
    //    private ArrayList<LatLng> mWayPoints = new ArrayList<>();
    private Timer mUpdateTimer = new Timer();
    private boolean mIsFirstShowLocationPopup = true;
    private boolean mIsCaravanInProgress;
    private boolean mIsCaravan;
    private boolean mTimerIsCancelled;
    private AlertDialog mAlertDialog;
    private String eta = "";
    private double mLastParticipantLat = 0;
    private double mLastParticipantLng = 0;
    private long mLastParticipantUpdateTime = 0;
    private boolean mAddMoreTime;
    private boolean mySelfHasOnMap;
    private int mCurrentPosition = -1;
    private int mStatusOnMarkerClicked = -1;
    private final int ON_PARTICIPANT_CLICKED = 0;
    private final int ON_LISTING_MARKER_CLICKED = 1;
    private final int ON_LISTING_BOTTOM_CLICKED = 2;

    private CaravanActionAdapter mListingAdapter;
    private CIAParticipantsInListingAdapter mCIAParticipantsGoneThroughAdapter;
    private Handler handler;
    private Runnable runnable;
    private BuyerCancelApptPresenter mBuyerCancelApptPresenter;
    private GetThreadIdPresenter mGetThreadIdPresenter;
    private DateFormat dateFormatHHMMA = new SimpleDateFormat("hh:mm a", Locale.US);
    private DateFormat dateFormatHHMM = new SimpleDateFormat("HH:mm", Locale.US);
    private SimpleDateFormat formatYYYYMMDDHHMM = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
    private String threadIdOfMessageGroup;
    private boolean clickGroupMessageCaravan;
    private Timer mCheckCaravanTimer = new Timer();

    private Socket mSocket;

    {
        try {
            mSocket = IO.socket(Constants.getInstance().URL_BASE_CONSUMER_CIA);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Bind(R.id.rvTime)
    RecyclerView mRvTime;
    @Bind(R.id.rvListing)
    RecyclerView mRvListing;
    @Bind(R.id.scrollViewHorizontal)
    HorizontalScrollView mScrollViewHorizontal;
    @Bind(R.id.fabActionMap)
    FloatingActionMenu mFabActionMap;
    @Bind(R.id.layoutBgActionMap)
    FrameLayout mLayoutBgActionMap;

    //Bottom sheet
    @Bind(R.id.layoutAppointmentInfo)
    LinearLayout mLayoutAppointmentInfo;
    @Bind(R.id.rvParticipants)
    RecyclerView mRvParticipants;
    @Bind(R.id.rvParticipantsGoneThrough)
    RecyclerView mRvParticipantsGoneThrough;
    @Bind(R.id.tvBottomSheet)
    TextView mTvBottomSheet;
    @Bind(R.id.tvBottomSheet1)
    TextView mTvBottomSheet1;
    @Bind(R.id.tvBottomSheet2)
    TextView mTvBottomSheet2;
    @Bind(R.id.tvBottomSheet3)
    TextView mTvBottomSheet3;
    @Bind(R.id.viewLine1)
    View mViewLine1;
    @Bind(R.id.viewLine2)
    View mViewLine2;
    @Bind(R.id.viewLine3)
    View mViewLine3;
    @Bind(R.id.imgAppointmentHome)
    RoundedImageView mImgAppointmentHome;
    @Bind(R.id.tvAppointmentAddress)
    TextView mTvAppointmentAddress;
    @Bind(R.id.tvAppointmentCity)
    TextView mTvAppointmentCity;
    @Bind(R.id.tvAppointmentTime)
    TextView mTvAppointmentTime;
    @Bind(R.id.layoutBottomSheet)
    LinearLayout mLayoutBottomSheet;

    @OnClick(R.id.fabCurrentLocation)
    public void onFabCurrentLocationClicked() {
        mFabActionMap.close(true);
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (mLastLocation == null) {
                requestLocation();
            } else {
                moveToCurrentLocation();
            }
        } else {
            mLastLocation = null;
            checkLocationSettings();
        }
    }

    @OnClick(R.id.fabStartUpdateLocation)
    public void onFabStartUpdateLocationClicked() {
        mFabActionMap.close(true);
        togglePeriodicLocationUpdates();
    }

    @OnClick(R.id.fabChangeCurrentTime1)
    public void onFabChangeCurrentTime1Clicked() {
        mAddMoreTime = true;
        checkData(3000000);
    }

    @OnClick(R.id.fabChangeCurrentTime2)
    public void onFabChangeCurrentTime2Clicked() {
        mAddMoreTime = true;
        checkData(86400000);
        //nho check location
    }

    @OnClick(R.id.fabChangeCurrentTime3)
    public void onFabChangeCurrentTime3Clicked() {
        mAddMoreTime = true;
        checkData(289694000);
        //nho check location
    }

    @OnClick(R.id.fabChangeCurrentTime4)
    public void onFabChangeCurrentTime4Clicked() {
        mAddMoreTime = false;
    }

    @OnClick(R.id.layoutBgActionMap)
    public void onBgActionMapClicked() {
        mFabActionMap.close(true);
        if (mLayoutBottomSheet.getVisibility() == View.VISIBLE) {
            AnimUtils.hideViewFromBottom(mLayoutBottomSheet);
        }
    }

    @OnClick(R.id.tvBottomSheet1)
    public void onBottomSheet1Clicked() {
        Log.e(TAG, "onBottomSheet1Clicked: mCurrentPosition: " + mCurrentPosition + " mStatusOnMarkerClicked: " + mStatusOnMarkerClicked);
        if (mCurrentPosition == -1 || mStatusOnMarkerClicked == -1) {
            Log.e(TAG, "onBottomSheet2Clicked: wrong mCurrentPosition");
            return;
        }
        if (mStatusOnMarkerClicked == ON_PARTICIPANT_CLICKED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            String phoneNumber = "tel:*101#";
            callIntent.setData(Uri.parse(phoneNumber));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                askPermission(Manifest.permission.CALL_PHONE, 3);
                return;
            }
            startActivity(callIntent);
        }
        AnimUtils.hideViewFromBottom(mLayoutBottomSheet);
        mCurrentPosition = -1;
        mStatusOnMarkerClicked = -1;
    }

    @OnClick(R.id.tvBottomSheet2)
    public void onBottomSheet2Clicked() {
        Log.e(TAG, "onBottomSheet2Clicked: mCurrentPosition: " + mCurrentPosition + " mStatusOnMarkerClicked: " + mStatusOnMarkerClicked);
        if (mCurrentPosition == -1 || mStatusOnMarkerClicked == -1) {
            Log.e(TAG, "onBottomSheet2Clicked: wrong mCurrentPosition");
            return;
        }
        if (mStatusOnMarkerClicked == ON_PARTICIPANT_CLICKED) {
            // Chat voi participant
            String apptId, listingId, caravanId, title, teamIds;

            apptId = mCaravanData.getDestinations().get(0).getAppointment().getId();
            listingId = mCaravanData.getDestinations().get(0).getListing().getId();
            caravanId = mCaravanData.getId();
            title = mParticipantsMarker.get(mCurrentPosition).getParticipants().getFullName();
            teamIds = mParticipantsMarker.get(mCurrentPosition).getParticipants().getId();
            Log.e(TAG, "onBottomSheet2Clicked: apptId: " + apptId + " listingId: "
                    + listingId + " caravanId: " + caravanId + " title: " + title + " teamIds: " + teamIds);
            mGetThreadIdPresenter.getThreadIdAtCaravan(apptId, listingId, caravanId, title, teamIds, mCurrentListing.getPosition());
            showLoading();
            mTvBottomSheet2.setEnabled(false);
        } else if (mStatusOnMarkerClicked == ON_LISTING_BOTTOM_CLICKED) {
//            Send message group khi click len Bottom Listing
            int n = mParticipantsMarker.size();
            if (n == 0) {
                Log.e(TAG, "onBottomSheet2Clicked: Send message group Bottom Listing nothing");
                return;
            }

//            clickGroupMessageCaravan = true;
//            if(threadIdOfMessageGroup != null){
//                initMessage(threadIdOfMessageGroup);
//                threadIdOfMessageGroup = null;
//                return;
//            }

            String apptId, listingId, caravanId, teamIds, title;
            apptId = mCaravanData.getDestinations().get(mCurrentPosition).getAppointment().getId();
            listingId = mCaravanData.getDestinations().get(mCurrentPosition).getListing().getId();
            caravanId = mCaravanData.getId();
            title = mParticipantsMarker.get(0).getParticipants().getFullName();
            teamIds = mParticipantsMarker.get(0).getParticipants().getId();

            for (int i = 1; i < n; i++) {
                if (!mCurrentMarker.getParticipants().getId()
                        .equals(mParticipantsMarker.get(i).getParticipants().getId())) {
                    title += ", " + mParticipantsMarker.get(i).getParticipants().getFullName();
                    teamIds += "," + mParticipantsMarker.get(i).getParticipants().getId();
                }
            }
            title += ", " + mCurrentMarker.getParticipants().getFullName();
            Log.e(TAG, "onBottomSheet2Clicked: apptId: " + apptId + " listingId: "
                    + listingId + " caravanId: " + caravanId + " title: " + title + " teamIds: " + teamIds);
            mGetThreadIdPresenter.getThreadIdAtCaravan(apptId, listingId, caravanId, title, teamIds, mCurrentPosition);
            showLoading();
            mTvBottomSheet2.setEnabled(false);
        }
        mCurrentPosition = -1;
        mStatusOnMarkerClicked = -1;
        AnimUtils.hideViewFromBottom(mLayoutBottomSheet);
    }

    @OnClick(R.id.tvBottomSheet3)
    public void onBottomSheet3Clicked() {
        Log.e(TAG, "onBottomSheet3Clicked: mCurrentPosition: " + mCurrentPosition + " mStatusOnMarkerClicked: " + mStatusOnMarkerClicked);
        if (mCurrentPosition == -1 || mStatusOnMarkerClicked == -1) {
            Log.e(TAG, "onBottomSheet2Clicked: wrong mCurrentPosition");
            return;
        }
        if (mStatusOnMarkerClicked == ON_PARTICIPANT_CLICKED) {
            //View profile
            Intent intent = new Intent(this, ViewUserProfileConsumerActivity.class);
            startActivity(intent);
        } else if (mStatusOnMarkerClicked == ON_LISTING_BOTTOM_CLICKED) {
            if (!"cancelled".equals(mArrListingCIAMarker.get(mCurrentPosition).getDestinations().getAppointment().getStatus().getStatus())) {
                mBuyerCancelApptPresenter.buyerCancelAppointment(
                        mArrListingCIAMarker.get(mCurrentPosition).getDestinations().getAppointment().getId());
            }
        }
        mCurrentPosition = -1;
        mStatusOnMarkerClicked = -1;
        AnimUtils.hideViewFromBottom(mLayoutBottomSheet);
    }

    @OnClick(R.id.tvBottomSheet4)
    public void onBottomSheet4Clicked() {
        AnimUtils.hideViewFromBottom(mLayoutBottomSheet);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivityConsumer.mIsCaravanInAction = true;
        ButterKnife.bind(this);
        MySupportMapFragment mMapFragment = (MySupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapCaravan);
        mMapFragment.getMapAsync(this);
        mMapFragment.setOnDragListener(this);
        mIconFactory = new IconGenerator(this);
        mFabActionMap.setOnMenuToggleListener(this);
        mGetThreadIdPresenter = new GetThreadIdPresenter(this);

        mCaravanData = CurrentCaravan.getCaravan().getData();
        CaravanParticipants currentUser = converUserDataToCaravanParticipants(ConsumerUser.getInstance().getData());
        mCurrentMarker.setParticipants(currentUser);
        setParticipantsInfo();

        SharedPreferences sharedPreferences = getSharedPreferences("SETTINGS_TIME_UPDATE_LOCATION", Context.MODE_PRIVATE);
        UPDATE_INTERVAL = sharedPreferences.getInt("CIA_UPDATE_INTERVAL", 65000);
        FATEST_INTERVAL = sharedPreferences.getInt("CIA_FATEST_INTERVAL", 60000);
        Log.d(TAG, "FATEST_INTERVAL: " + FATEST_INTERVAL + "  UPDATE_INTERVAL: " + UPDATE_INTERVAL);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        buildGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();
        initData();
        setupBottomSheet();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_caravan_in_action;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setCompassEnabled(false);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        mGoogleMap.getUiSettings().setIndoorLevelPickerEnabled(false);
        mGoogleMap.getUiSettings().setTiltGesturesEnabled(false);

        drawListingsGoneThrough();

        checkCaravanTimer();

        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d(TAG, "onMapClick: clicked !!");
                if (mScrollViewHorizontal.getVisibility() == View.VISIBLE) {
                    AnimUtils.hideViewFromBottom(mScrollViewHorizontal);
                } else {
                    if (mLayoutBottomSheet.getVisibility() == View.VISIBLE) {
                        AnimUtils.hideViewFromBottom(mLayoutBottomSheet);
                    } else {
                        AnimUtils.showViewFromBottom(mScrollViewHorizontal);
                    }
                }
            }
        });

        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
//                if (mCurrentMarker.getMarker() != null) { //Test thoi, co the remove
//                    if (marker.getId().equals(mCurrentMarker.getMarker().getId())) {
//                        mStatusOnMarkerClicked = ON_PARTICIPANT_CLICKED;
//                        mBottomSheetMarker.setMarker(mCurrentMarker.getMarker());
//                        mBottomSheetMarker.setParticipants(mCurrentMarker.getParticipants());
//                        showBottomSheetMenu(mStatusOnMarkerClicked, 0);
//                        return true;
//                    }
//                }

                int i;
                int n = mArrListingCIAMarker.size();
                for (i = 0; i < n; i++) {
                    if (mArrListingCIAMarker.get(i).getMarker() != null
                            && marker.getId().equals(mArrListingCIAMarker.get(i).getMarker().getId())) {
                        mStatusOnMarkerClicked = ON_LISTING_MARKER_CLICKED;
                        mBottomSheetMarker.setMarker(mArrListingCIAMarker.get(i).getMarker());
                        mBottomSheetMarker.setDestinations(mArrListingCIAMarker.get(i).getDestinations());
                        showBottomSheetMenu(mStatusOnMarkerClicked, i);
                        return true;
                    }
                }

                n = mParticipantsMarker.size();
                for (i = 0; i < n; i++) {
                    if (mParticipantsMarker.get(i).getMarker() != null
                            && marker.getId().equals(mParticipantsMarker.get(i).getMarker().getId())) {
                        mStatusOnMarkerClicked = ON_PARTICIPANT_CLICKED;
                        mBottomSheetMarker.setMarker(mParticipantsMarker.get(i).getMarker());
                        mBottomSheetMarker.setParticipants(mParticipantsMarker.get(i).getParticipants());
                        showBottomSheetMenu(mStatusOnMarkerClicked, i);
                        return true;
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onDrag(MotionEvent motionEvent) {
    }

    @Override
    public void onDirectionSuccess(MyDirection direction, String rawBody) {

        if (mCurrentListing == null || mLastLocation == null) {
            return;
        }

        if (mPolylinePaths != null) {
            for (Polyline polyline : mPolylinePaths) {
                polyline.remove();
            }
            mPolylinePaths.clear();
        }

        double distance = getDistance(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        if (distance < 300) {
            if (mArrListingCIAMarker.get(mCurrentListing.getPosition()).getParticipantsGoneThrough().size() != 0) {
                for (ParticipantsMarker participant : mArrListingCIAMarker.get(mCurrentListing.getPosition()).getParticipantsGoneThrough()) {
                    if (!mCurrentMarker.getParticipants().getId().equals(participant.getParticipants().getId())) {
                        mArrListingCIAMarker.get(mCurrentListing.getPosition()).getParticipantsGoneThrough().add(mCurrentMarker);
                        break;
                    }
                }
            } else {
                mArrListingCIAMarker.get(mCurrentListing.getPosition()).getParticipantsGoneThrough().add(mCurrentMarker);
            }
            if (mCurrentMarker.getMarker() != null) {
                mCurrentMarker.getMarker().remove();
                mySelfHasOnMap = false;
            }
            return;
        }

        PolylineOptions polylineOptions = new PolylineOptions()
                .geodesic(true)
                .color(Color.GREEN) //ORANGE - Color.rgb(255, 192, 0)
                .width(8)
                .pattern(PATTERN_POLYGON_ALPHA);

        for (Route route : direction.getDirection().getRouteList()) {
            polylineOptions.addAll(route.getOverviewPolyline().getPointList());
        }

        if (direction.getDirection().getStatus().equals("OK")) {
            if (direction.getDirection().getRouteList().get(0).getLegList().size() != 0) {
                eta = direction.getDirection().getRouteList().get(0).getLegList().get(0).getDuration().getText();
                if (!mCurrentListing.isYouBeenLate() && mIsCaravan) {
                    long etaSeconds = Long.parseLong(direction.getDirection().getRouteList().get(0).getLegList().get(0).getDuration().getValue());
                    checkInTardy(etaSeconds, mCurrentListing.getDestinations().getTimeFrom().getData());
                    mCurrentListing.setYouBeenLate(true);
                }
            }
        }

        mPolylinePaths.add(mGoogleMap.addPolyline(polylineOptions));
        drawMySelf();
    }

    private void checkInTardy(long etaSeconds, String timeStartListing) {
        Calendar currentCalendar = Calendar.getInstance();
        long currentTime = currentCalendar.getTimeInMillis() / 1000;
//        currentTime = currentTimeTest / 1000;
        Calendar caravanCalendar = Calendar.getInstance();
        long caravanStartTime;
        Date date;
        try {
            date = formatYYYYMMDDHHMM.parse(timeStartListing);
            caravanCalendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long estimateTime = currentTime + etaSeconds;

        caravanStartTime = caravanCalendar.getTimeInMillis() / 1000;
        if (estimateTime > caravanStartTime + 300) {//bi tre qua 5 phut
            String timeIsLate;
            long minutes = (estimateTime - caravanStartTime) / 60;
            long hours;
            if (minutes < 60) {
                timeIsLate = minutes + " minutes";
            } else {
                hours = minutes / 60;
                minutes = minutes - hours * 60;
                if (minutes == 0) {
                    timeIsLate = hours + " hours";
                } else {
                    timeIsLate = hours + " hours " + minutes + " minutes";
                }
            }
            String imageUrl = mCurrentListing.getDestinations().getListing().getListingImages().getImage();
            String address = mCurrentListing.getDestinations().getListing().getAddress().getAddress1();
            String time = Utils.getTimeShowing(mCurrentListing.getDestinations().getTimeFrom().getData(),
                    mCurrentListing.getDestinations().getTimeTo().getData());
            String message = "\"Your next appointment for <b>" + address + "</b> is \"<b>estimated to be late for " + timeIsLate + "</b>\", please take action now to inform other parties";
            boolean isAdmin = "admin".equals(mCurrentMarker.getParticipants().getRole());
            ciaAppointmentWarning(isAdmin, message, imageUrl, address, time);
        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (mCurrentListing != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                askPermission(Manifest.permission.ACCESS_FINE_LOCATION, 1);
            } else {
                if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    mLastLocation = LocationServices.FusedLocationApi
                            .getLastLocation(mGoogleApiClient);
                    if (mLastLocation != null) {
                        drawMySelf();
//                        mGoogleMap.animateCamera(CameraUpdateFactory.
//                                newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 10f));
                        countBounds++;
                        builder.include(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
                        cameraZoom();
                    }
                }
            }

            if (mRequestingLocationUpdates) {
                startLocationUpdates();
            }

            if (!mTimerIsCancelled) {
                checkTimeCaravanInProgress();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location == null)
            return;
        mLastLocation = location;
        requestGoogleDirectionAPI();
        socketGPSUpdate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
        checkLocationSettings();
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventListingDetail(EventCIA event) {
        Log.e(TAG, "Eventbus onEventListingDetail: ");
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    public void onDestroy() {
        if (mUpdateTimer != null) {
            mUpdateTimer.cancel();
            mUpdateTimer.purge();
        }
        if (mCheckCaravanTimer != null) {
            mCheckCaravanTimer.cancel();
            mCheckCaravanTimer.purge();
        }
        mSocket.off(Constants.getInstance().CIA_GPS_RECEIVE);
        super.onDestroy();
        Log.e(TAG, "CIA onDestroy: ");
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                if (mIsCaravanInProgress) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            requestLocation();
                            if (mLastLocation != null) {
                                socketGPSUpdate();
                                requestGoogleDirectionAPI();
//                                drawMySelf();
//                                mGoogleMap.animateCamera(CameraUpdateFactory.
//                                        newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 10f));
                                countBounds++;
                                builder.include(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
                                cameraZoom();
                            }
                        }
                    }, 1000);
                    mUpdateTimer.cancel();
                    mUpdateTimer.purge();
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            requestLocation();
                            if (mLastLocation != null) {
                                drawMySelf();
//                                mGoogleMap.animateCamera(CameraUpdateFactory.
//                                        newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 10f));
                                countBounds++;
                                builder.include(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
                                cameraZoom();
                            }
                        }
                    }, 1000);
                }
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                if (mIsFirstShowLocationPopup || mIsCaravanInProgress) {
                    try {
                        status.startResolutionForResult(this, 2);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                    mIsFirstShowLocationPopup = false;
                } else {
//                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                            new LatLng(mArrListingCIAMarker.get(0).getDestinations().getListing().getAddress().getLat(),
//                                    mArrListingCIAMarker.get(0).getDestinations().getListing().getAddress().getLng()), 10f));
                    cameraZoom();
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1:
                if (permissions.length == 1 && grantResults.length == 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("DaoDiDem", "onRequestPermissionsResult: PERMISSION_GRANTED" + requestCode);
                } else {
                    askPermission(Manifest.permission.ACCESS_FINE_LOCATION, 1);
                }
                break;
            default:
        }
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

    @Override
    public void onItemListingClick(int position) {
        showBottomSheetMenu(ON_LISTING_BOTTOM_CLICKED, position);
    }

    @Override
    public void onItemParticipantsClick(int position) {
        showBottomSheetMenu(ON_PARTICIPANT_CLICKED, position);
    }

//    private void requestGoogleDirectionAPI() {
//        int n = mArrListingCIAMarker.size();
//        if (n == 0) {
//            return;
//        }
//
//        if (n == 1) {
//            LatLng start = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
//            LatLng end = new LatLng(mArrListingCIAMarker.get(0).getDestinations().getListing().getAddress().getLat(),
//                    mArrListingCIAMarker.get(0).getDestinations().getListing().getAddress().getLng());
//
//            MyGoogleDirection.withServerKey(Constants.getInstance().CIA_GOOGLE_DIRECTION_API_KEY)
//                    .from(start)
//                    .to(end)
//                    .transportMode(TransportMode.DRIVING)
//                    .alternativeRoute(false)
//                    .execute(this);
//        } else {
//            mWayPoints.clear();
//            LatLng start = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
//            for (int i = 0; i < n; i++) {
//                LatLng wayPoint = new LatLng(mArrListingCIAMarker.get(i).getDestinations().getListing().getAddress().getLat(),
//                        mArrListingCIAMarker.get(i).getDestinations().getListing().getAddress().getLng());
//                mWayPoints.add(wayPoint);
//            }
//
//            MyGoogleDirection.withServerKey(Constants.getInstance().CIA_GOOGLE_DIRECTION_API_KEY)
//                    .from(start)
//                    .to(start)
//                    .waypoints(mWayPoints)
//                    .transportMode(TransportMode.DRIVING)
//                    .alternativeRoute(false)
//                    .execute(this);
//        }
//    }

    private void requestGoogleDirectionAPI() {
        if (mCurrentListing != null && mLastLocation != null) {
            LatLng start = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            LatLng end = new LatLng(mCurrentListing.getLatLng().latitude,
                    mCurrentListing.getLatLng().longitude);

            MyGoogleDirection.withServerKey(Constants.getInstance().CIA_GOOGLE_DIRECTION_API_KEY)
                    .from(start)
                    .to(end)
                    .transportMode(TransportMode.DRIVING)
                    .alternativeRoute(false)
                    .execute(this);
        }
    }

    private void initData() {
        int n = mCaravanData.getDestinations().size();
        if (n == 0) {
            Log.d(TAG, "initListingsRecyclerview: Not Found Listing !!!");
            finish();
            return;
        }
        boolean checkTime;
        Calendar currentCalendar = Calendar.getInstance();
        long currentTime = currentCalendar.getTimeInMillis();
        for (int i = 0; i < n; i++) {
            ListingCIAMarker listing = new ListingCIAMarker();
            listing.setDestinations(mCaravanData.getDestinations().get(i));
            checkTime = mCaravanData.getDestinations().get(i).getTimeTo() != null && checkTimeListing(mCaravanData.getDestinations().get(i).getTimeTo().getData(), currentTime);
            listing.setStatus(checkTime);
            mArrListingCIAMarker.add(listing);
            if (listing.getDestinations().getAppointment() != null) {
                if (!checkTime && mCurrentListing == null
                        && !"cancelled".equals(listing.getDestinations().getAppointment().getStatus().getStatus())) {
                    mCurrentListing = mArrListingCIAMarker.get(i);
                    mCurrentListing.setPosition(i);
                    LatLng latLng = new LatLng(Double.parseDouble(mCurrentListing.getDestinations().getLat()),
                            Double.parseDouble(mCurrentListing.getDestinations().getLng()));
                    mCurrentListing.setLatLng(latLng);
                }
            } else {
                if (!checkTime && mCurrentListing == null) {
                    mCurrentListing = mArrListingCIAMarker.get(i);
                    mCurrentListing.setPosition(i);
                    LatLng latLng = new LatLng(Double.parseDouble(mCurrentListing.getDestinations().getLat()),
                            Double.parseDouble(mCurrentListing.getDestinations().getLng()));
                    mCurrentListing.setLatLng(latLng);
                }
            }
        }

        if (mArrListingCIAMarker.size() == 0) {
            return;
        }

        int minutes, hours;
        for (int i = 0; i < n - 1; i++) {
            minutes = Integer.parseInt(mArrListingCIAMarker.get(i).getDestinations().getDestinationsDrive().getValue()) / 60;
            if (minutes < 60) {
                mArrTime.add(minutes + " mins");
            } else {
                hours = minutes / 60;
                minutes = minutes - hours * 60;
                if (minutes == 0) {
                    mArrTime.add(hours + "h");
                } else {
                    mArrTime.add(hours + "h" + minutes + "m");
                }
            }
        }
        mListingAdapter = new CaravanActionAdapter(mArrListingCIAMarker, this, this);
        CaravanActionTimeAdapter mTimeAdapter = new CaravanActionTimeAdapter(mArrTime, this);
        mRvTime.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRvTime.setAdapter(mTimeAdapter);
        mRvListing.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRvListing.setAdapter(mListingAdapter);

        if (mCurrentListing == null) {
            Log.d(TAG, "initListingsRecyclerview: Caravan DONE !!!");
        } else {
            socketConnect();
            mBuyerCancelApptPresenter = new BuyerCancelApptPresenter(this);
        }
    }

    //remove if needn't test
    private Calendar currentCalendarTest = Calendar.getInstance();
    private long currentTimeTest = currentCalendarTest.getTimeInMillis();

    private void checkCaravanTimer() {
        mCheckCaravanTimer.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        checkData();
                    }
                });
            }
        }, 0, 10000);
    }

    private void checkData() {
        Log.e(TAG, "-----------checkData: timer----------");
        boolean checkTime;
        if (mCurrentListing != null) {
            mPreviousListing = mCurrentListing;
        }
        mCurrentListing = null;
        Calendar currentCalendar = Calendar.getInstance();
        long currentTime = currentCalendar.getTimeInMillis();
        int n = mArrListingCIAMarker.size();
        for (int i = 0; i < n; i++) {
            if (!mArrListingCIAMarker.get(i).isStatus()) {
                checkTime = checkTimeListing(mArrListingCIAMarker.get(i).getDestinations().getTimeTo().getData(), currentTime);
                mArrListingCIAMarker.get(i).setStatus(checkTime);
                if (!checkTime && mCurrentListing == null
                        && !"cancelled".equals(mArrListingCIAMarker.get(i).getDestinations().getAppointment().getStatus().getStatus())) {
                    mCurrentListing = mArrListingCIAMarker.get(i);
                    mCurrentListing.setPosition(i);
                    LatLng latLng = new LatLng(Double.parseDouble(mCurrentListing.getDestinations().getLat()),
                            Double.parseDouble(mCurrentListing.getDestinations().getLng()));
                    mCurrentListing.setLatLng(latLng);
                }
            }
        }

        if (!(mPreviousListing != null && mPreviousListing.getDestinations().getListing().getId()
                .equals(mCurrentListing.getDestinations().getListing().getId()))) {
            mListingAdapter.notifyDataSetChanged();
            drawListingsGoneThrough();
            requestGoogleDirectionAPI();
        }

//        if (mIsCaravanInProgress) {
//            requestGoogleDirectionAPI();
//        }

        if (mCurrentListing == null) {
            Log.d(TAG, "checkData: Caravan DONE!!!");
            socketDeactive();
            if (mPolylinePaths != null) {
                for (Polyline polyline : mPolylinePaths) {
                    polyline.remove();
                }
                mPolylinePaths.clear();
            }

            if (mCurrentMarker.getMarker() != null) {
                mCurrentMarker.getMarker().remove();
            }

            for (ParticipantsMarker participant : mParticipantsMarker) {
                if (participant.getMarker() != null)
                    participant.getMarker().remove();
            }

            mRequestingLocationUpdates = false;
            stopLocationUpdates();
            mSocket.off(Constants.getInstance().CIA_GPS_RECEIVE);
            mCheckCaravanTimer.cancel();
            mCheckCaravanTimer.purge();
        }
    }

    private void checkData(long moreTime) {
        boolean checkTime;
        if (mAddMoreTime) {
            currentTimeTest += moreTime;
        }
        mCurrentListing = null;
        int n = mArrListingCIAMarker.size();
        for (int i = 0; i < n; i++) {
            if (!mArrListingCIAMarker.get(i).isStatus()) {
                checkTime = checkTimeListing(mArrListingCIAMarker.get(i).getDestinations().getTimeTo().getData(), currentTimeTest);
                mArrListingCIAMarker.get(i).setStatus(checkTime);
                if (!checkTime && mCurrentListing == null
                        && !"cancelled".equals(mArrListingCIAMarker.get(i).getDestinations().getAppointment().getStatus().getStatus())) {
                    mCurrentListing = mArrListingCIAMarker.get(i);
                    mCurrentListing.setPosition(i);
                    LatLng latLng = new LatLng(Double.parseDouble(mCurrentListing.getDestinations().getLat()),
                            Double.parseDouble(mCurrentListing.getDestinations().getLng()));
                    mCurrentListing.setLatLng(latLng);
                }
            }
        }

//        if (mIsCaravanInProgress) {
//            requestGoogleDirectionAPI();
//        }
        if (mCurrentListing == null) {
            Log.d(TAG, "checkData: Caravan DONE!!!");
            socketDeactive();
            if (mPolylinePaths != null) {
                for (Polyline polyline : mPolylinePaths) {
                    polyline.remove();
                }
                mPolylinePaths.clear();
            }

            if (mCurrentMarker.getMarker() != null) {
                mCurrentMarker.getMarker().remove();
            }

            for (ParticipantsMarker participant : mParticipantsMarker) {
                if (participant.getMarker() != null)
                    participant.getMarker().remove();
            }

            mRequestingLocationUpdates = false;
            stopLocationUpdates();
            mSocket.off(Constants.getInstance().CIA_GPS_RECEIVE);
        }

        mListingAdapter.notifyDataSetChanged();

        drawListingsGoneThrough();
    }

    private void drawListingsGoneThrough() {
        if (handler != null && runnable != null)
            handler.removeCallbacks(runnable);
        for (ListingCIAMarker listing : mArrListingCIAMarker) {
            if (!listing.isStatus()) {
                if (!"cancelled".equals(listing.getDestinations().getAppointment().getStatus().getStatus())) {
                    if (listing.getMarker() != null) {
                        listing.getMarker().remove();
                    }
                    listing.setMarker(addStopCaravan(mIconFactory, listing.getDestinations(), false));
                    countBounds++;
                    builder.include(listing.getMarker().getPosition());
                    break;
                } else {
                    if (listing.getMarker() != null) {
                        listing.getMarker().remove();
                    }
                    listing.setMarker(addStopCaravan(mIconFactory, listing.getDestinations(), true));
                    countBounds++;
                    builder.include(listing.getMarker().getPosition());
                }
            } else {
                if (listing.getMarker() != null) {
                    listing.getMarker().remove();
                }
                listing.setMarker(addStopCaravan(mIconFactory, listing.getDestinations(), true));
                countBounds++;
                builder.include(listing.getMarker().getPosition());
            }
        }
    }

    private boolean checkTimeListing(String timeToListing, long currentTime) {
        if (timeToListing == null) {
            return false;
        }

        Calendar caravanCalendar = Calendar.getInstance();
        long listingEndTime;
        Date date;
        try {
            date = formatYYYYMMDDHHMM.parse(timeToListing);
            caravanCalendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        listingEndTime = caravanCalendar.getTimeInMillis();
        return listingEndTime < currentTime;
    }

    private void drawMySelf() {
        if (mCurrentMarker.getMarker() == null) {
            mySelfHasOnMap = false;
        }
        if (!mySelfHasOnMap) {
            if (mCurrentMarker.getMarker() != null) {
                mCurrentMarker.getMarker().remove();
            }

            addMySelfInCaravan(mIconFactory, new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
            mySelfHasOnMap = true;
        } else {
            mIconFactory.setBackground(null);
            View view = LayoutInflater.from(this).inflate(R.layout.marker_caravan_action_1, null, false);
            CircleImageView avatarAgent = (CircleImageView) view.findViewById(R.id.ivAgent);
            Glide.with(getApplication()).load(ConsumerUser.getInstance().getData().getPhoto()).asBitmap().fitCenter()
                    .dontAnimate().placeholder(R.drawable.avatar_default).into(avatarAgent);
            TextView nameAgent = (TextView) view.findViewById(R.id.tvNameAgent);
            nameAgent.setText(ConsumerUser.getInstance().getData().getFirstName() + " " + ConsumerUser.getInstance().getData().getLastName());
            TextView tvETA = (TextView) view.findViewById(R.id.tv2);
            if (!"".equals(eta)) {
                tvETA.setText("ETA: " + eta);
            } else {
                tvETA.setText("Coming ...");
            }
            mIconFactory.setContentView(view);
            Bitmap bitmap = mIconFactory.makeIcon();
            mCurrentMarker.getMarker().setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
            animateMarker(mLastLocation, mCurrentMarker.getMarker());
        }
    }

    public static void animateMarker(final Location destination, final Marker marker) {
        if (marker != null) {
            final LatLng startPosition = marker.getPosition();
            final LatLng endPosition = new LatLng(destination.getLatitude(), destination.getLongitude());

//            final float startRotation = marker.getRotation();

            final LatLngInterpolator latLngInterpolator = new LatLngInterpolator.LinearFixed();
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(700); // duration 1 second
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    try {
                        float v = animation.getAnimatedFraction();
                        LatLng newPosition = latLngInterpolator.interpolate(v, startPosition, endPosition);
                        marker.setPosition(newPosition);
//                        huong quay cua marker
//                        marker.setRotation(computeRotation(v, startRotation, destination.getBearing()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            valueAnimator.start();
        }
    }

    public static void animateMarker(final LatLng endPosition, final Marker marker) {
        if (marker != null) {
            final LatLng startPosition = marker.getPosition();

//            final float startRotation = marker.getRotation();

            final LatLngInterpolator latLngInterpolator = new LatLngInterpolator.LinearFixed();
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(700); // duration 1 second
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    try {
                        float v = animation.getAnimatedFraction();
                        LatLng newPosition = latLngInterpolator.interpolate(v, startPosition, endPosition);
                        marker.setPosition(newPosition);
//                        huong quay cua marker
//                        marker.setRotation(computeRotation(v, startRotation, destination.getBearing()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            valueAnimator.start();
        }
    }

    private static float computeRotation(float fraction, float start, float end) {
        float normalizeEnd = end - start; // rotate start to 0
        float normalizedEndAbs = (normalizeEnd + 360) % 360;

        float direction = (normalizedEndAbs > 180) ? -1 : 1; // -1 = anticlockwise, 1 = clockwise
        float rotation;
        if (direction > 0) {
            rotation = normalizedEndAbs;
        } else {
            rotation = normalizedEndAbs - 360;
        }

        float result = fraction * rotation + start;
        return (result + 360) % 360;
    }

    private interface LatLngInterpolator {
        LatLng interpolate(float fraction, LatLng a, LatLng b);

        class LinearFixed implements LatLngInterpolator {
            @Override
            public LatLng interpolate(float fraction, LatLng a, LatLng b) {
                double lat = (b.latitude - a.latitude) * fraction + a.latitude;
                double lngDelta = b.longitude - a.longitude;
                // Take the shortest path across the 180th meridian.
                if (Math.abs(lngDelta) > 180) {
                    lngDelta -= Math.signum(lngDelta) * 360;
                }
                double lng = lngDelta * fraction + a.longitude;
                return new LatLng(lat, lng);
            }
        }
    }

    private CaravanParticipants converUserDataToCaravanParticipants(UserData userData) {
        CaravanParticipants caravanParticipants = new CaravanParticipants();
//        caravanParticipants.setLat(mLastLocation.getLatitude() + "");
//        caravanParticipants.setLng(mLastLocation.getLongitude() + "");
        caravanParticipants.setId(userData.getId());
        caravanParticipants.setFirstName(userData.getFirstName());
        caravanParticipants.setLastName(userData.getLastName());
        caravanParticipants.setFullName(userData.getFirstName() + " " + userData.getLastName());
        caravanParticipants.setAvatar(userData.getPhoto());
        caravanParticipants.setPnUid(userData.getPnUID());
        return caravanParticipants;
    }

    private Marker addStopCaravan(IconGenerator iconFactory, ResponseCaravan.CaravanDestinations listing, boolean goneThrough) {
        LatLng latLng = new LatLng(listing.getListing().getAddress().getLat(),
                listing.getListing().getAddress().getLng());
        iconFactory.setBackground(null);
        View view;
        if (goneThrough) {
            view = LayoutInflater.from(this).inflate(R.layout.marker_caravan_action_gone, null, false);
            iconFactory.setContentView(view);
            return addMarker(iconFactory, latLng);
        } else {
            MarkerOptions markerOptions = new MarkerOptions().
                    position(latLng);
            Marker marker = mGoogleMap.addMarker(markerOptions);
            Bitmap markerIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_consumer_caravan_flag_1);
            pulseMarker(markerIcon, marker, 1000);
            return marker;
        }
//        ImageView imageView = (ImageView) view.findViewById(R.id.imgFlag);
//        if(goneThrough){
//            Glide.with(getApplication()).load(R.drawable.ic_caravan_action_1).asBitmap().dontAnimate().into(imageView);
//        }else{
//            Glide.with(getApplication()).load(R.drawable.ic_consumer_caravan_flag).asBitmap().dontAnimate().into(imageView);
//        }
//        CircleImageView avatarAgent = (CircleImageView) view.findViewById(R.id.ivAgent);
//        Glide.with(getApplication()).load(listing.getListing().getListingImages().getImage()).asBitmap().fitCenter()
//                .dontAnimate().placeholder(R.drawable.no_image_b).into(avatarAgent);
//        TextView nameAgent = (TextView) view.findViewById(R.id.tvNameAgent);
//        nameAgent.setText("Listing " + (Integer.parseInt(listing.getPosition()) + 1) + " - " + getTimeMetting(listing.getTimeFrom().getData()));
//        TextView tvAddress = (TextView) view.findViewById(R.id.tv2);
//        tvAddress.setText(listing.getListing().getAddress().getAddress1());

    }

    private void pulseMarker(final Bitmap markerIcon, final Marker marker, final long onePulseDuration) {
        handler = new Handler();
        final long startTime = System.currentTimeMillis();
        final Interpolator interpolator = new CycleInterpolator(1f);

        runnable = new Runnable() {
            @Override
            public void run() {
                long elapsed = System.currentTimeMillis() - startTime;
                float t = interpolator.getInterpolation((float) elapsed / onePulseDuration);
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(scaleBitmap(markerIcon, 1f + 0.05f * t)));
                handler.postDelayed(this, 50);
            }
        };
        handler.post(runnable);

    }

    public Bitmap scaleBitmap(Bitmap bitmap, float scaleFactor) {
        final int sizeX = Math.round(bitmap.getWidth() * scaleFactor);
        final int sizeY = Math.round(bitmap.getHeight() * scaleFactor);
        return Bitmap.createScaledBitmap(bitmap, sizeX, sizeY, false);
    }

    private void addParticipantsInCaravan(final IconGenerator iconFactory, final CaravanParticipants participant,
                                          final LatLng latLng, final int position) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                iconFactory.setBackground(null);
                View view = LayoutInflater.from(CaravanInActionActivity.this).inflate(R.layout.marker_caravan_action_2, null, false);
                CircleImageView avatarAgent = (CircleImageView) view.findViewById(R.id.ivAgent);
                Glide.with(getApplication()).load(participant.getAvatar()).asBitmap().fitCenter()
                        .dontAnimate().placeholder(R.drawable.avatar_default).into(avatarAgent);
                TextView nameAgent = (TextView) view.findViewById(R.id.tvNameAgent);
                nameAgent.setText(participant.getFullName());
                TextView tvETA = (TextView) view.findViewById(R.id.tv2);
                tvETA.setText("Coming..");
                iconFactory.setContentView(view);
                Marker marker = addMarker(iconFactory, latLng);
                Log.d(TAG, "-+++-addParticipant----: " + participant.getFullName() + " - Lat: " + latLng.latitude + " Lng: " + latLng.longitude);
                mParticipantsMarker.get(position).setMarker(marker);
            }
        });

    }

    private void addMySelfInCaravan(IconGenerator iconFactory, LatLng latLng) {
        iconFactory.setBackground(null);
        View view = LayoutInflater.from(this).inflate(R.layout.marker_caravan_action_1, null, false);

        CircleImageView avatarAgent = (CircleImageView) view.findViewById(R.id.ivAgent);
        Glide.with(getApplication()).load(ConsumerUser.getInstance().getData().getPhoto()).asBitmap().fitCenter()
                .dontAnimate().placeholder(R.drawable.avatar_default).into(avatarAgent);
        TextView nameAgent = (TextView) view.findViewById(R.id.tvNameAgent);
        nameAgent.setText(ConsumerUser.getInstance().getData().getFirstName() + " " + ConsumerUser.getInstance().getData().getLastName());
        TextView tvETA = (TextView) view.findViewById(R.id.tv2);
        if (!"".equals(eta)) {
            tvETA.setText("ETA: " + eta);
        } else {
            tvETA.setText("Coming ...");
        }
        iconFactory.setContentView(view);
        Marker marker = addMarker(iconFactory, latLng);
        mCurrentMarker.setMarker(marker);
    }

    private Marker addMarker(IconGenerator iconFactory, LatLng position) {
        Bitmap bitmap = iconFactory.makeIcon();
        MarkerOptions markerOptions = new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(bitmap)).
                position(position).
                anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

        return mGoogleMap.addMarker(markerOptions);
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    protected void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

    }

    protected void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        int DISPLACEMENT = 10;
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    protected void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    private void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(this);
    }

    private void togglePeriodicLocationUpdates() {
        if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true;

            // Starting the location updates
            startLocationUpdates();

            Log.d(TAG, "Periodic location updates started!");

        } else {
            mRequestingLocationUpdates = false;

            // Stopping the location updates
            stopLocationUpdates();

            Log.d(TAG, "Periodic location updates stopped!");
        }
    }

    private void askPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            }
        }
    }

    private void checkTimeCaravanInProgress() {
        mUpdateTimer.schedule(new TimerTask() {
            public void run() {
                Calendar currentCalendar = Calendar.getInstance();
                long currentTime = currentCalendar.getTimeInMillis();
//                currentTime = currentTimeTest;
                Calendar caravanCalendar = Calendar.getInstance();
                long caravanStartTime;
                long caravanEndTime;
                Date date;
                try {
                    date = formatYYYYMMDDHHMM.parse(mCaravanData.getStartingTime().getData());
                    caravanCalendar.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                caravanStartTime = caravanCalendar.getTimeInMillis() - 1800000; //before 30 minutes

                if (mCaravanData.getEndingTime() != null) {
                    try {
                        date = formatYYYYMMDDHHMM.parse(mCaravanData.getEndingTime().getData());
                        caravanCalendar.setTime(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    caravanEndTime = caravanCalendar.getTimeInMillis();
                    if (caravanStartTime < currentTime && currentTime < caravanEndTime) {
                        int listings = mCaravanData.getDestinations().size();
                        String startTime = dateFormatHHMM.format(Utils.createDateFromString(mCaravanData.getStartingTime().getData()));
                        String message = "\"Your Caravan at " + startTime + " for " + listings + " listings is about to start, you can activate \"<b>in action mode</b>\" now to share your location, your readiness and your ETA to other parties now\"";
                        ciaShowPopupCaravan(message);
                    }
                } else {
                    if (caravanStartTime < currentTime && currentTime < caravanStartTime + 1800000) {
                        int listings = mCaravanData.getDestinations().size();
                        String startTime = dateFormatHHMMA.format(Utils.createDateFromString(mCaravanData.getStartingTime().getData()));
                        String message = "\"Your Caravan at " + startTime + " for " + listings + " listings is about to start, you can activate \"<b>in action mode</b>\" now to share your location, your readiness and your ETA to other parties now\"";
                        ciaShowPopupCaravan(message);
                    }
                }
            }
        }, 0, 30000);
    }

    private void ciaShowPopupCaravan(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mAlertDialog == null) {
                    LayoutInflater layoutInflater1 = LayoutInflater.from(CaravanInActionActivity.this);
                    View view1 = layoutInflater1.inflate(R.layout.dialog_consumer_caravan_popup_show_popup_caravan, null);
                    FrameLayout rlButton1 = (FrameLayout) view1.findViewById(R.id.rlButton1);
                    FrameLayout rlButton2 = (FrameLayout) view1.findViewById(R.id.rlButton2);
                    TextView tvPopupMessage = (TextView) view1.findViewById(R.id.tvPopupMessage);
                    tvPopupMessage.setText(fromHtml(message));
                    mAlertDialog = new AlertDialog.Builder(CaravanInActionActivity.this).setView(view1).create();
                    mAlertDialog.getWindow().getAttributes().windowAnimations = R.style.TeamTabSearchDialog;
                    mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    WindowManager.LayoutParams wmlp = mAlertDialog.getWindow().getAttributes();
                    wmlp.gravity = Gravity.CENTER;
                    wmlp.y = -200;   //y position

                    rlButton1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //share location
                            if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                checkLocationSettings();
                                mIsCaravanInProgress = true;
                            } else {
                                requestLocation();
                                if (mLastLocation != null) {
                                    socketGPSUpdate();
                                    requestGoogleDirectionAPI();
                                    countBounds++;
                                    builder.include(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
                                    cameraZoom();
                                    togglePeriodicLocationUpdates();
                                    mTimerIsCancelled = true;
                                    mIsCaravan = true;
                                    mUpdateTimer.cancel();
                                    mUpdateTimer.purge();
                                }
                            }
                            mAlertDialog.dismiss();
                        }
                    });
                }
                mAlertDialog.show();
            }
        });
    }

    private void ciaAppointmentWarning(boolean isAdmin, String message, String imageUrl, String address, String time) {
        LayoutInflater layoutInflater1 = LayoutInflater.from(this);
        View view1 = layoutInflater1.inflate(R.layout.dialog_consumer_caravan_popup_appointment_warning, null);
        FrameLayout frmButton1 = (FrameLayout) view1.findViewById(R.id.frmButton1);
        FrameLayout frmButton2 = (FrameLayout) view1.findViewById(R.id.frmButton2);
        FrameLayout frmButton3 = (FrameLayout) view1.findViewById(R.id.frmButton3);
        if (isAdmin) {
            frmButton1.setVisibility(View.VISIBLE);
            frmButton3.setVisibility(View.VISIBLE);
        } else {
            frmButton1.setVisibility(View.GONE);
            frmButton3.setVisibility(View.GONE);
        }
        TextView tvPopupMessage = (TextView) view1.findViewById(R.id.tvPopupMessage);
        TextView tvAppointmentAddress = (TextView) view1.findViewById(R.id.tvAppointmentAddress);
        TextView tvAppointmentTime = (TextView) view1.findViewById(R.id.tvAppointmentTime);
        RoundedImageView imgAppointmentHome = (RoundedImageView) view1.findViewById(R.id.imgAppointmentHome);
        Glide.with(this).load(imageUrl).asBitmap().fitCenter().dontAnimate().
                placeholder(R.drawable.no_image_b).into(imgAppointmentHome);
        tvPopupMessage.setText(fromHtml(message));
        tvAppointmentAddress.setText(address);
        tvAppointmentTime.setText(time);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view1).create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.TeamTabSearchDialog;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        wmlp.y = -200;   //y position

        frmButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        frmButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStatusOnMarkerClicked = ON_LISTING_BOTTOM_CLICKED;
                mCurrentPosition = mCurrentListing.getPosition();
                onBottomSheet2Clicked();
                alertDialog.dismiss();
            }
        });

        frmButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!"cancelled".equals(mCurrentListing.getDestinations().getAppointment().getStatus().getStatus())) {
                    mBuyerCancelApptPresenter.buyerCancelAppointment(
                            mCurrentListing.getDestinations().getAppointment().getId());
                }
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    @SuppressWarnings("deprecation")
    public Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    private void setParticipantsInfo() {
        int n = mCaravanData.getParticipants().size();
        for (int i = 0; i < n; i++) {
            if (mCurrentMarker.getParticipants().getId().equals(mCaravanData.getParticipants().get(i).getId())) {
                mCurrentMarker.getParticipants().setRole(mCaravanData.getParticipants().get(i).getRole());
            } else {
                ParticipantsMarker participantsMarker = new ParticipantsMarker();
                participantsMarker.setParticipants(mCaravanData.getParticipants().get(i));
                mParticipantsMarker.add(participantsMarker);
            }
        }
    }

    private void moveToCurrentLocation() {
        if (mLastLocation != null) {
            if (mCurrentListing.getMarker() == null) {
                drawMySelf();
            }
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())));
        }
    }

    private void socketConnect() {
        mSocket.connect();

        socketLogin();

        mSocket.on(Constants.getInstance().CIA_GPS_RECEIVE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    JSONObject data = (JSONObject) args[0];
                    parseJSon(data);
                    Log.d(TAG, "----CIA_GPS_RECEIVE: " + args[0].toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void socketLogin() {
        Log.d(TAG, "LOGIN");
        JSONObject json = new JSONObject();
        try {
            json.put(Constants.getInstance().CIA_USERNAME, ConsumerUser.getInstance().getData().getId());
            json.put(Constants.getInstance().CIA_TEAM, Constants.getInstance().CIA_HOME_CARAVAN);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mSocket.emit(Constants.getInstance().CIA_LOGIN, json, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                Log.d(TAG, "Response login: " + data.toString());
                socketActive();
            }
        });
    }

    private void socketActive() {
        Log.d(TAG, "ACTIVE");
        Log.e(TAG, "socketActive: mCaravanData.getCiaId(): " + mCaravanData.getCiaId());
        mSocket.emit(Constants.getInstance().CIA_ACTIVE, mCaravanData.getCiaId(), new Ack() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "Response active: " + args[0].toString());
            }
        });
    }

    private void socketDeactive() {
        Log.d(TAG, "DEACTIVE");
        mSocket.emit(Constants.getInstance().CIA_DEACTIVE);
    }

    private void socketGPSUpdate() {
        Log.d(TAG, "GPS_UPDATE");
        JSONObject json = new JSONObject();
        try {
            json.put("longitude", mLastLocation.getLongitude());
            json.put("latitude", mLastLocation.getLatitude());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "json: " + json.toString());

        mSocket.emit(Constants.getInstance().CIA_GPS_UPDATE, json);
    }

    private void parseJSon(JSONObject data) throws JSONException {
        if (data == null || mCurrentListing == null)
            return;

        JSONObject map = data.getJSONObject("map");
        JSONObject user = map.getJSONObject("user");
        JSONObject userMap = user.getJSONObject("map");
        String userName = userMap.getString("username");
        if (userName.equals(mCurrentMarker.getParticipants().getId())) {
            return;
        }

        final double latitude = map.getDouble("latitude");
        final double longitude = map.getDouble("longitude");
        long updated_time = map.getLong("updated_time");

        //Fixes getting too many data from the server as it engages in multiple rooms
        if (mLastParticipantLat == latitude && mLastParticipantLng == longitude && mLastParticipantUpdateTime == updated_time) {
            return;
        }

        mLastParticipantLat = latitude;
        mLastParticipantLng = longitude;
        mLastParticipantUpdateTime = updated_time;

        double distance = getDistance(latitude, longitude);

        Log.d(TAG, "parseJSon: distance: " + distance);
        Log.d(TAG, "parseJSon: userName: " + userName + " - latitude: " + latitude + " - longitude: " + longitude + " - updated_time: " + updated_time);
        int n = mParticipantsMarker.size();
        for (int i = 0; i < n; i++) {
            if (mParticipantsMarker.get(i).getParticipants().getId().equals(userName)) {
                final int position = i;
                if (distance < 300) {
                    //Them nguoi da den neu o trong ban kinh 300m
                    if (mArrListingCIAMarker.get(mCurrentListing.getPosition()).getParticipantsGoneThrough().size() != 0) {
                        for (ParticipantsMarker participant : mArrListingCIAMarker.get(mCurrentListing.getPosition()).getParticipantsGoneThrough()) {
                            if (!mParticipantsMarker.get(i).getParticipants().getId().equals(participant.getParticipants().getId())) {
                                mArrListingCIAMarker.get(mCurrentListing.getPosition()).getParticipantsGoneThrough().add(mParticipantsMarker.get(i));
                            }
                        }
                    } else {
                        mArrListingCIAMarker.get(mCurrentListing.getPosition()).getParticipantsGoneThrough().add(mParticipantsMarker.get(i));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mParticipantsMarker.get(position).getMarker() != null) {
                                mParticipantsMarker.get(position).getMarker().remove();
                                mParticipantsMarker.get(position).setHasOnMap(false);
                            }
                        }
                    });
                    return;
                } else {
                    //Ve marker nguoi do len ban do neu ra khoi 300m
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            drawParticipants(mParticipantsMarker.get(position).getParticipants(), new LatLng(latitude, longitude), position);
                        }
                    });
                }
            }
        }
    }

    private void drawParticipants(final CaravanParticipants participant, final LatLng latLng, final int position) {
        if (mParticipantsMarker.get(position).getMarker() == null) {
            mParticipantsMarker.get(position).setHasOnMap(false);
        }
        if (!mParticipantsMarker.get(position).isHasOnMap()) {
            if (mParticipantsMarker.get(position).getMarker() != null) {
                mParticipantsMarker.get(position).getMarker().remove();
            }

            addParticipantsInCaravan(mIconFactory, participant, latLng, position);
            mParticipantsMarker.get(position).setHasOnMap(true);
        } else {
            animateMarker(latLng, mParticipantsMarker.get(position).getMarker());
        }
    }

    private double getDistance(double latitude, double longitude) {
        Location participantLocation = new Location("participant");
        participantLocation.setLatitude(latitude);
        participantLocation.setLongitude(longitude);
        Location listingLocation = new Location("listing");
        listingLocation.setLatitude(mCurrentListing.getLatLng().latitude);
        listingLocation.setLongitude(mCurrentListing.getLatLng().longitude);
        return listingLocation.distanceTo(participantLocation);
    }

    private void setupBottomSheet() {
        CIAParticipantsInListingAdapter mCIAParticipantsInListingAdapter = new CIAParticipantsInListingAdapter(this, mParticipantsMarker, this, false);
        mRvParticipants.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRvParticipants.setAdapter(mCIAParticipantsInListingAdapter);
        mCIAParticipantsGoneThroughAdapter = new CIAParticipantsInListingAdapter(this, mParticipantsGoneThroughMarker, this, true);
        mRvParticipantsGoneThrough.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRvParticipantsGoneThrough.setAdapter(mCIAParticipantsGoneThroughAdapter);
    }

    private void showBottomSheetMenu(int statusOnMarkerClicked, int position) {
        mCurrentPosition = position;
        mStatusOnMarkerClicked = statusOnMarkerClicked;
        if (mLayoutBottomSheet.getVisibility() == View.VISIBLE) {
            mLayoutBottomSheet.setVisibility(View.GONE);
        }
        mFabActionMap.close(true);
        if (mScrollViewHorizontal.getVisibility() == View.VISIBLE) {
            mScrollViewHorizontal.setVisibility(View.GONE);
        }

        if (statusOnMarkerClicked == ON_PARTICIPANT_CLICKED) {
            mLayoutAppointmentInfo.setVisibility(View.GONE);
            mRvParticipants.setVisibility(View.GONE);
            mRvParticipantsGoneThrough.setVisibility(View.GONE);
            mViewLine1.setVisibility(View.GONE);
            mViewLine2.setVisibility(View.VISIBLE);
            mViewLine3.setVisibility(View.VISIBLE);
            mTvBottomSheet.setVisibility(View.GONE);
//            mTvBottomSheet1.setVisibility(View.VISIBLE);
            mTvBottomSheet2.setVisibility(View.VISIBLE);
            mTvBottomSheet3.setVisibility(View.VISIBLE);
//            String callName = "Call " + mParticipantsMarker.get(mCurrentPosition).getParticipants().getFirstName();
//            mTvBottomSheet1.setText(callName);
            mTvBottomSheet2.setText(getString(R.string.caravan_send_message));
            mTvBottomSheet3.setText(getString(R.string.caravan_view_profile));
        } else if (statusOnMarkerClicked == ON_LISTING_MARKER_CLICKED) {
            mParticipantsGoneThroughMarker.clear();
            mParticipantsGoneThroughMarker.addAll(mArrListingCIAMarker.get(position).getParticipantsGoneThrough());
            if (mParticipantsGoneThroughMarker.size() != 0) {
                mTvBottomSheet.setVisibility(View.VISIBLE);
                mTvBottomSheet.setText(getString(R.string.caravan_who_have_come_here));
            } else {
                mTvBottomSheet.setVisibility(View.GONE);
            }
            mCIAParticipantsGoneThroughAdapter.notifyDataSetChanged();
            mLayoutAppointmentInfo.setVisibility(View.VISIBLE);
            mRvParticipants.setVisibility(View.GONE);
            mRvParticipantsGoneThrough.setVisibility(View.VISIBLE);
            mViewLine1.setVisibility(View.GONE);
            mViewLine2.setVisibility(View.GONE);
            mViewLine3.setVisibility(View.GONE);
            mTvBottomSheet1.setVisibility(View.GONE);
            mTvBottomSheet2.setVisibility(View.GONE);
            mTvBottomSheet3.setVisibility(View.GONE);
            mTvAppointmentAddress.setText(mBottomSheetMarker.getDestinations().getListing().getAddress().getAddress1());
            String city = mBottomSheetMarker.getDestinations().getListing().getAddress().getCity() + ", "
                    + mBottomSheetMarker.getDestinations().getListing().getAddress().getState() + " "
                    + mBottomSheetMarker.getDestinations().getListing().getAddress().getZip();
            mTvAppointmentCity.setText(city);
            mTvAppointmentTime.setText(Utils.getTimeShowing(mBottomSheetMarker.getDestinations().getTimeFrom().getData(),
                    mBottomSheetMarker.getDestinations().getTimeTo().getData()));
            Glide.with(getApplication()).load(mBottomSheetMarker.getDestinations().getListing().getListingImages().getImage()).asBitmap().fitCenter()
                    .dontAnimate().placeholder(R.drawable.no_image_b).into(mImgAppointmentHome);
        } else {
            mLayoutAppointmentInfo.setVisibility(View.GONE);
            mRvParticipants.setVisibility(View.VISIBLE);
            mRvParticipantsGoneThrough.setVisibility(View.GONE);
            mViewLine1.setVisibility(View.GONE);
            mViewLine2.setVisibility(View.VISIBLE);
            mTvBottomSheet.setVisibility(View.VISIBLE);
            mTvBottomSheet1.setVisibility(View.GONE);
            mTvBottomSheet2.setVisibility(View.VISIBLE);
            mTvBottomSheet.setText(getString(R.string.caravan_who_see_the_house));
            mTvBottomSheet2.setText(getString(R.string.caravan_send_group_messages));
            if ("admin".equals(mCurrentMarker.getParticipants().getRole())) {
                mTvBottomSheet3.setVisibility(View.VISIBLE);
                mTvBottomSheet3.setText(getString(R.string.caravan_cancel_this_appointment));
                mViewLine3.setVisibility(View.VISIBLE);
            } else {
                mTvBottomSheet3.setVisibility(View.GONE);
                mViewLine3.setVisibility(View.GONE);
            }
        }
        AnimUtils.showViewFromBottom(mLayoutBottomSheet);
    }

    private void cameraZoom() {
        if (countBounds != 0) {
            LatLngBounds bounds = builder.build();
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.15); // offset from edges of the map 15% of screen
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
            mGoogleMap.animateCamera(cu);
            countBounds = 0;
        }
    }

    @Override
    public void cancelApptSuccess(ArrayList<AppointmentShowing> appointmentShowing) {
        Log.e(TAG, "-----------Response----------");
        for (ListingCIAMarker listing : mArrListingCIAMarker) {
            if (appointmentShowing.get(0).getRefAppointment().getId()
                    .equals(listing.getDestinations().getAppointment().getId())) {
                listing.getDestinations().getAppointment().getStatus().setStatus("cancelled");
                mListingAdapter.notifyDataSetChanged();
                break;
            }
        }
        for (AppointmentShowing listing : appointmentShowing) {
            Log.e(TAG, "id: " + listing.getRefAppointment().getId() + " Status: " + listing.getRefAppointment().getStatus().getStatus());
        }
    }

    @Override
    public void cancelApptFail() {
        Log.e(TAG, "cancelApptFail: FAIL");
    }

    @Override
    public void cancelApptFail(@StringRes int message) {
        Log.e(TAG, "cancelApptFail: " + getString(message));
    }

    @Override
    public void getThreadIdAtCaravanSuccess(ResponseMessageGetThreadId responeGetThreadId, int position, String threadName) {
//        if(clickGroupMessageCaravan){
//            threadIdOfMessageGroup = responeGetThreadId.getThreadId();
//            clickGroupMessageCaravan = false;
//        }
        Log.e(TAG, "getThreadIdSuccess: threadId: " + responeGetThreadId.getThreadId());
        initMessage(responeGetThreadId.getThreadId(), position, threadName);
    }

    @Override
    public void getThreadIdSuccess(ResponseMessageGetThreadId threadId, String threadName) {

    }

    @Override
    public void getThreadIdFail() {
        mTvBottomSheet2.setEnabled(true);
    }

    @Override
    public void getThreadIdFail(@StringRes int message) {
        mTvBottomSheet2.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (mLayoutBottomSheet.getVisibility() == View.VISIBLE) {
            AnimUtils.hideViewFromBottom(mLayoutBottomSheet);
            return;
        }
        if (mScrollViewHorizontal.getVisibility() == View.VISIBLE) {
            AnimUtils.hideViewFromBottom(mScrollViewHorizontal);
            return;
        }
        super.onBackPressed();
        MainActivityConsumer.mIsCaravanInAction = false;
    }

    private void initMessage(String threadId, int position, String threadName) {

        if(!HomeCaravanApplication.mLoginSocketSuccess){
            return;
        }
        Intent intent = new Intent(this, ConversationActivity.class);
        intent.putExtra("THREAD_ID", threadId);
        String responseMessage1 = "I’m driving right now";
        if (mCurrentListing != null) {
            responseMessage1 += ", see you at: " + mArrListingCIAMarker.get(position).getDestinations().getListing().getAddress().getAddress1();
            if (!"".equals(eta)) {
                responseMessage1 += "in " + eta;
            }
        }
        Log.e(TAG, "getThreadIdSuccess: threadId: " + threadId + " responseMessage1: " + responseMessage1);
        intent.putExtra("RESPONSE_MESSAGE_1", responseMessage1);
        intent.putExtra("MESSAGE_THREAD_NAME", threadName);
        hideLoading();
        startActivity(intent);
        mTvBottomSheet2.setEnabled(true);


    }
}
