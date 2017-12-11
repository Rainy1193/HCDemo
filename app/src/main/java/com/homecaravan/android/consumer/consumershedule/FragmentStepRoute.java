package com.homecaravan.android.consumer.consumershedule;


import android.Manifest;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
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
import com.google.maps.android.ui.IconGenerator;
import com.homecaravan.android.R;
import com.homecaravan.android.caravan.MyDirectionConverter;
import com.homecaravan.android.consumer.adapter.ScheduleRoutePropertyAdapter;
import com.homecaravan.android.consumer.api.CaravanApi;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.direction.MyDirection;
import com.homecaravan.android.consumer.direction.MyDirectionCallback;
import com.homecaravan.android.consumer.direction.MyGoogleDirection;
import com.homecaravan.android.consumer.listener.IScheduleListener;
import com.homecaravan.android.consumer.listener.IScheduleStepTwoListener;
import com.homecaravan.android.consumer.listener.ISwapRouteListener;
import com.homecaravan.android.consumer.model.ConsumerListingSchedule;
import com.homecaravan.android.consumer.model.CurrentCaravan;
import com.homecaravan.android.consumer.model.CurrentListingSchedule;
import com.homecaravan.android.consumer.model.RouteMarker;
import com.homecaravan.android.consumer.model.WayPoint;
import com.homecaravan.android.consumer.model.responseapi.ResponseCaravan;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.consumer.widget.CustomNestedScrollView;
import com.homecaravan.android.myinterface.OnStartDragListener;
import com.homecaravan.android.ui.MapWrapperLayout;
import com.homecaravan.android.ui.MySupportMapFragment;
import com.homecaravan.android.updatelisting.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentStepRoute extends BaseFragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<LocationSettingsResult>,
        LocationListener,
        OnMapReadyCallback,
        IScheduleStepTwoListener,
        MapWrapperLayout.OnDragListener,
        OnStartDragListener,
        ISwapRouteListener,
        MyDirectionCallback {

    private static final long ACCURACY = 0;
    private static final long UPDATE_INTERVAL = 400;
    private static final long FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL / 2;


    private RecyclerView.ItemAnimator mItemAnimator = new DefaultItemAnimator();

    private int mCurrentSelect = 0;
    private int mOldSelect = -1;
    private int mPosFrom;
    private int mPosTo;
    private boolean mIsSwap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationManager mLocationManager;
    private GoogleMap mGoogleMap;
    private MySupportMapFragment mMapFragment;
    private ScheduleRoutePropertyAdapter mRouteAdapter;
    private Location mCurrentLocation;
    private ArrayList<ConsumerListingSchedule> mArrListing = new ArrayList<>();
    private ArrayList<RouteMarker> mArrMarker = new ArrayList<>();
    private ItemTouchHelper mItemTouchHelper;
    private IScheduleListener mListener;
    private int mPositionUpdateTime;
    private String mHourUpdate;
    private String mMinUpdate;
    private String mDurationUpdate;
    private String mHaftUpdate;
    private String mServerKey = "AIzaSyDZy0BfgLMN-n-BVv-6WPoRs0rGfdOd5lM";
    private ArrayList<WayPoint> mWayPoint = new ArrayList<>();
    private IconGenerator mIconFactory;
    private boolean mUpdate;

    public void setUpdate(boolean update) {
        mUpdate = update;
    }

    public void setListener(IScheduleListener mListener) {
        this.mListener = mListener;
    }

    @Bind(R.id.rvRouteProperty)
    RecyclerView mRvRoute;

    @Bind(R.id.scrollView)
    CustomNestedScrollView mScrollView;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getCaravanDetail();
        mMapFragment = (MySupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapRoute);
        mMapFragment.setOnDragListener(this);
        mMapFragment.getMapAsync(this);
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        buildGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();
        mIconFactory = new IconGenerator(getActivity());
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
    protected int getLayoutResourceId() {
        return R.layout.fragment_step_route;
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
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                    }
                });
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
                Log.e("RESOLUTION_REQUIRED", "RESOLUTION_REQUIRED");
                try {
                    status.startResolutionForResult(getActivity(), 2);
                } catch (IntentSender.SendIntentException e) {
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
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

    public void scrollListRoute(Marker marker) {
        for (int i = 0; i < mArrMarker.size(); i++) {
            if (marker.getId().equalsIgnoreCase(mArrMarker.get(i).getMarker().getId())) {
                final int finalI = i;
                mRvRoute.post(new Runnable() {
                    @Override
                    public void run() {
                        mRvRoute.smoothScrollToPosition(finalI);
                    }
                });
                break;
            }
        }
    }

    public void setUpMapAndListRoute(String hour, String min, String half) {

        mWayPoint.clear();
        mGoogleMap.clear();
        mArrMarker.clear();
        int duration = 15;
        mArrListing = CurrentListingSchedule.getInstance().getArrListing();
        mArrListing.get(0).setSelect(true);
        int iHour = Integer.parseInt(hour);
        int iMin = Integer.parseInt(min);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        if (half.equalsIgnoreCase("PM")) {
            calendar.add(Calendar.HOUR, 12);
            if (iHour - 12 > 0) {
                calendar.add(Calendar.HOUR, iHour - 12);
            }
        } else {
            calendar.add(Calendar.HOUR, iHour);
        }
        calendar.add(Calendar.MINUTE, iMin);
        Log.e("calendar", calendar.getTime().toString());

        for (int i = 0; i < mArrListing.size(); i++) {
            ConsumerListingSchedule listing = mArrListing.get(i);
            listing.setTimeStart(calendar);
            listing.setPosition(i);
            listing.setDuration(duration);
            listing.setStartHour(Utils.getSingleHour(calendar.getTime()));
            listing.setStartMin(Utils.getSingleMin(calendar.getTime()));
            listing.setStartHaft(Utils.getSingleHalf(calendar.getTime()));
            calendar.add(Calendar.MINUTE, duration);
            listing.setTimeEnd(calendar);
            listing.setEndMin(Utils.getSingleMin(calendar.getTime()));
            listing.setEndHour(Utils.getSingleHour(calendar.getTime()));
            listing.setEndHaft(Utils.getSingleHalf(calendar.getTime()));
            calendar.add(Calendar.MINUTE, duration);

            if (i + 1 < mArrListing.size()) {
                LatLng start = new LatLng(mArrListing.get(i).getListing().getAddress().getLat(), mArrListing.get(i).getListing().getAddress().getLng());
                LatLng end = new LatLng(mArrListing.get(i + 1).getListing().getAddress().getLat(), mArrListing.get(i + 1).getListing().getAddress().getLng());
                mWayPoint.add(new WayPoint(start, end));
            }
            Log.e("listing", listing.toString());
        }

        mRouteAdapter = new ScheduleRoutePropertyAdapter(mArrListing, getActivity(), this, this, this);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mRouteAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRvRoute);
        mRvRoute.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRvRoute.setHasFixedSize(true);
        mRvRoute.setAdapter(mRouteAdapter);
        mItemAnimator = mRvRoute.getItemAnimator();
        drawWay();
    }


    public void drawWay() {
        if (mArrListing.size() < 2) {
            mIconFactory.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_router));
            String position = "1";
            Marker marker = addMarker(mIconFactory, Utils.getPositionRoute(position),
                    new LatLng(mArrListing.get(0).getListing().getAddress().getLat(), mArrListing.get(0).getListing().getAddress().getLng()));
            RouteMarker routeMarker = new RouteMarker(marker, mArrListing.get(0));
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


    @Override
    public void setSelect(int position) {
        mRvRoute.setItemAnimator(null);
        mOldSelect = mCurrentSelect;
        mCurrentSelect = position;
        mArrListing.get(mOldSelect).setSelect(false);
        mArrListing.get(mCurrentSelect).setSelect(true);
        mRouteAdapter.notifyItemChanged(mOldSelect);
        mRouteAdapter.notifyItemChanged(mCurrentSelect);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(mArrMarker.get(mCurrentSelect).getMarker().getPosition()));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRvRoute.setItemAnimator(mItemAnimator);
            }
        }, 100);
    }

    @Override
    public void openSetTime(int position) {
        mPositionUpdateTime = position;
        mHourUpdate = String.valueOf(mArrListing.get(position).getStartHour());
        mMinUpdate = String.valueOf(mArrListing.get(position).getStartMin());
        mHaftUpdate = mArrListing.get(position).getStartHaft();
        mDurationUpdate = String.valueOf(mArrListing.get(position).getDuration() + " mins");
        mListener.openPickTimeRoute(mArrListing.get(position).getStartHour(), mArrListing.get(position).getStartMin(), mArrListing.get(position).getStartHaft(), String.valueOf(mArrListing.get(position).getDuration()) + " mins");
    }

    @Override
    public void onDrag(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            mScrollView.setScrollingEnabled(false);
        }
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            mScrollView.setScrollingEnabled(true);
        }
    }


    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onEndDrag(RecyclerView.ViewHolder viewHolder) {
        if (mIsSwap) {
            int posTemp = mArrListing.get(mPosFrom).getPosition();
            int posTemp1 = mArrListing.get(mPosTo).getPosition();

            int hourHourStartTemp = mArrListing.get(mPosFrom).getStartHour();
            int hourMinStartTemp = mArrListing.get(mPosFrom).getStartMin();
            String hourHaftStartTemp = mArrListing.get(mPosFrom).getStartHaft();
            int hourHourEndTemp = mArrListing.get(mPosFrom).getEndHour();
            int hourEndEndTemp = mArrListing.get(mPosFrom).getEndMin();
            String hourHaftEndTemp = mArrListing.get(mPosFrom).getEndHaft();

            int hourHourStartTemp1 = mArrListing.get(mPosTo).getStartHour();
            int hourMinStartTemp1 = mArrListing.get(mPosTo).getStartMin();
            String hourHaftStartTemp1 = mArrListing.get(mPosTo).getStartHaft();
            int hourHourEndTemp1 = mArrListing.get(mPosTo).getEndHour();
            int hourEndEndTemp1 = mArrListing.get(mPosTo).getEndMin();
            String hourHaftEndTemp1 = mArrListing.get(mPosTo).getEndHaft();

            mArrListing.get(mPosFrom).setStartHour(hourHourStartTemp1);
            mArrListing.get(mPosFrom).setStartMin(hourMinStartTemp1);
            mArrListing.get(mPosFrom).setStartHaft(hourHaftStartTemp1);
            mArrListing.get(mPosFrom).setEndHour(hourHourEndTemp1);
            mArrListing.get(mPosFrom).setEndMin(hourEndEndTemp1);
            mArrListing.get(mPosFrom).setEndHaft(hourHaftEndTemp1);

            mArrListing.get(mPosTo).setStartHour(hourHourStartTemp);
            mArrListing.get(mPosTo).setStartMin(hourMinStartTemp);
            mArrListing.get(mPosTo).setStartHaft(hourHaftStartTemp);
            mArrListing.get(mPosTo).setEndHour(hourHourEndTemp);
            mArrListing.get(mPosTo).setEndMin(hourEndEndTemp);
            mArrListing.get(mPosTo).setEndHaft(hourHaftEndTemp);


            mArrListing.get(mPosFrom).setPosition(posTemp1);
            mArrListing.get(mPosTo).setPosition(posTemp);
            mRvRoute.setItemAnimator(null);
            mRouteAdapter.notifyItemChanged(mPosFrom);
            mRouteAdapter.notifyItemChanged(mPosTo);
            for (int i = 0; i < mArrListing.size(); i++) {
                if (mArrListing.get(i).isSelect()) {
                    mCurrentSelect = i;
                }
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRvRoute.setItemAnimator(mItemAnimator);
                }
            }, 100);
            updateMarker(mPosFrom, mPosTo, posTemp, posTemp1);
            mIsSwap = false;
        }
    }

    @Override
    public void swapRoute(int posFrom, int posTo) {
        mIsSwap = !mIsSwap;
        mPosFrom = posFrom;
        mPosTo = posTo;
        Collections.swap(mArrMarker, posFrom, posTo);
        mListener.updateListAfterSwap(posFrom, posTo);
    }

    public void updateMarker(int posFrom, int posTo, int newPost, int newPost1) {
        mGoogleMap.clear();
        mArrMarker.clear();
        drawWay();
    }

    public void updateHourRoute(String hour) {
        mHourUpdate = hour;
    }

    public void updateMinRoute(String min) {
        mMinUpdate = min;
    }

    public void updateHaftRoute(String haft) {
        mHaftUpdate = haft;
    }

    public void updateDurationRoute(String duration) {
        mDurationUpdate = duration;
    }

    public void updateTimeRoute() {
        int hour = Integer.parseInt(mHourUpdate);
        int min = Integer.parseInt(mMinUpdate);
        Log.e("mHourUpdate", String.valueOf(hour));
        Log.e("mMinUpdate", String.valueOf(min));
        Log.e("mHaftUpdate", mHaftUpdate);
        Log.e("mDurationUpdate", mDurationUpdate);
        int duration = Integer.parseInt(mDurationUpdate.substring(0, mDurationUpdate.indexOf("mins")).trim());

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        if (mHaftUpdate.equalsIgnoreCase("PM")) {
            calendar.add(Calendar.HOUR, 12);
            if (hour - 12 > 0) {
                calendar.add(Calendar.HOUR, hour - 12);
            }
        } else {
            calendar.add(Calendar.HOUR, min);
        }
        calendar.add(Calendar.MINUTE, min);
        Log.e("calendar", calendar.getTime().toString());

        ConsumerListingSchedule listing = mArrListing.get(mPositionUpdateTime);
        listing.setDuration(duration);

        listing.setTimeStart(calendar);
        listing.setStartHour(Utils.getSingleHour(calendar.getTime()));
        listing.setStartMin(Utils.getSingleMin(calendar.getTime()));
        listing.setStartHaft(Utils.getSingleHalf(calendar.getTime()));

        calendar.add(Calendar.MINUTE, duration);
        listing.setTimeEnd(calendar);
        listing.setEndMin(Utils.getSingleMin(calendar.getTime()));
        listing.setEndHour(Utils.getSingleHour(calendar.getTime()));
        listing.setEndHaft(Utils.getSingleHalf(calendar.getTime()));

        Calendar calendarUpdate = mArrListing.get(mPositionUpdateTime).getTimeStart();
        if (mPositionUpdateTime == 0) {
            for (int i = 0; i < mArrListing.size(); i++) {
                ConsumerListingSchedule listingSchedule = mArrListing.get(i);
                listingSchedule.setTimeStart(calendarUpdate);
                listingSchedule.setStartHour(Utils.getSingleHour(calendar.getTime()));
                listingSchedule.setStartMin(Utils.getSingleMin(calendar.getTime()));
                listingSchedule.setStartHaft(Utils.getSingleHalf(calendar.getTime()));
                calendar.add(Calendar.MINUTE, listingSchedule.getDuration());
                listingSchedule.setTimeEnd(calendarUpdate);
                listingSchedule.setEndMin(Utils.getSingleMin(calendar.getTime()));
                listingSchedule.setEndHour(Utils.getSingleHour(calendar.getTime()));
                listingSchedule.setEndHaft(Utils.getSingleHalf(calendar.getTime()));
                calendar.add(Calendar.MINUTE, 15);
            }
        } else {
            for (int i = mPositionUpdateTime - 1; i > 0; i--) {
                ConsumerListingSchedule listingSchedule = mArrListing.get(i);
                listingSchedule.setTimeStart(calendarUpdate);
                listingSchedule.setStartHour(Utils.getSingleHour(calendar.getTime()));
                listingSchedule.setStartMin(Utils.getSingleMin(calendar.getTime()));
                listingSchedule.setStartHaft(Utils.getSingleHalf(calendar.getTime()));
                calendar.add(Calendar.MINUTE, -listingSchedule.getDuration());
                listingSchedule.setTimeEnd(calendarUpdate);
                listingSchedule.setEndMin(Utils.getSingleMin(calendar.getTime()));
                listingSchedule.setEndHour(Utils.getSingleHour(calendar.getTime()));
                listingSchedule.setEndHaft(Utils.getSingleHalf(calendar.getTime()));
                calendar.add(Calendar.MINUTE, -15);
            }
            for (int j = mPositionUpdateTime; j < mArrListing.size(); j++) {
                ConsumerListingSchedule listingSchedule = mArrListing.get(j);
                listingSchedule.setTimeStart(calendarUpdate);
                listingSchedule.setStartHour(Utils.getSingleHour(calendar.getTime()));
                listingSchedule.setStartMin(Utils.getSingleMin(calendar.getTime()));
                listingSchedule.setStartHaft(Utils.getSingleHalf(calendar.getTime()));
                calendar.add(Calendar.MINUTE, listingSchedule.getDuration());
                listingSchedule.setTimeEnd(calendarUpdate);
                listingSchedule.setEndMin(Utils.getSingleMin(calendar.getTime()));
                listingSchedule.setEndHour(Utils.getSingleHour(calendar.getTime()));
                listingSchedule.setEndHaft(Utils.getSingleHalf(calendar.getTime()));
                calendar.add(Calendar.MINUTE, 15);
            }
        }
        mRouteAdapter.notifyDataSetChanged();
    }


    public void getCaravanDetail() {
        CaravanApi caravanApi = ServiceGeneratorConsumer.createService(CaravanApi.class);
        caravanApi.getCaravanDetail("277").enqueue(new Callback<ResponseCaravan>() {
            @Override
            public void onResponse(Call<ResponseCaravan> call, Response<ResponseCaravan> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        CurrentCaravan.getInstance().setData(response.body().getData());
                    } else {
                        Log.e("e", response.body().getMessage());
                    }
                } else {
                    Log.e("e", response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseCaravan> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
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
                RouteMarker routeMarker = new RouteMarker(marker, mArrListing.get(Integer.parseInt(position) - 1));
                mArrMarker.add(routeMarker);
                mGoogleMap.addPolyline(MyDirectionConverter.createPolyline(getActivity(), directionPositionList, 4, ContextCompat.getColor(getActivity(), R.color.colorMenuConsumer)));
            }
        }
        //mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mArrListing.get(0).getListing().getAddress().getLat(), mArrListing.get(0).getListing().getAddress().getLng()), 15f));
    }

    @Override
    public void onDirectionFailure(Throwable t) {
        Log.e("onDirectionFailure", t.getMessage());
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
}
