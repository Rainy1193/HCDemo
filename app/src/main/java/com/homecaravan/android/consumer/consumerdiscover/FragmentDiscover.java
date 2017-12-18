package com.homecaravan.android.consumer.consumerdiscover;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.homecaravan.android.consumer.activity.MainActivityConsumer;
import com.homecaravan.android.consumer.api.IGeocode;
import com.homecaravan.android.consumer.api.ServiceGeneratorConsumer;
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
import com.homecaravan.android.consumer.listener.IFilterListener;
import com.homecaravan.android.consumer.listener.IMainListener;
import com.homecaravan.android.consumer.listener.IOpenSavedSearchDetail;
import com.homecaravan.android.consumer.listener.IPageChange;
import com.homecaravan.android.consumer.listener.ISwipedHelper;
import com.homecaravan.android.consumer.listener.IVoteListing;
import com.homecaravan.android.consumer.map.MySupportMapFragment;
import com.homecaravan.android.consumer.map.TouchableWrapper;
import com.homecaravan.android.consumer.model.CaravanQueue;
import com.homecaravan.android.consumer.model.ClusterListingRender;
import com.homecaravan.android.consumer.model.ClusterMarker;
import com.homecaravan.android.consumer.model.CurrentCreateSavedSearch;
import com.homecaravan.android.consumer.model.CurrentFragment;
import com.homecaravan.android.consumer.model.DiscoverMarker;
import com.homecaravan.android.consumer.model.DiscoverMarkerFull;
import com.homecaravan.android.consumer.model.EventFavored;
import com.homecaravan.android.consumer.model.EventFavorite;
import com.homecaravan.android.consumer.model.EventQueue;
import com.homecaravan.android.consumer.model.EventQueueResponse;
import com.homecaravan.android.consumer.model.EventRequestShowing;
import com.homecaravan.android.consumer.model.EventUpdateSearch;
import com.homecaravan.android.consumer.model.StatusMarker;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.listitem.ListingItem;
import com.homecaravan.android.consumer.model.responseapi.ClustersSearchMap;
import com.homecaravan.android.consumer.model.responseapi.ConditionFull;
import com.homecaravan.android.consumer.model.responseapi.Geocode;
import com.homecaravan.android.consumer.model.responseapi.ListingFull;
import com.homecaravan.android.consumer.model.responseapi.ListingListSearchMap;
import com.homecaravan.android.consumer.model.responseapi.ListingSearchMap;
import com.homecaravan.android.consumer.model.responseapi.ResponseSearchMap;
import com.homecaravan.android.consumer.model.responseapi.SearchDetail;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.consumer.utils.Convert;
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
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;

public class FragmentDiscover extends BaseFragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<LocationSettingsResult>,
        LocationListener,
        OnMapReadyCallback,
        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraIdleListener,
        View.OnTouchListener,
        TouchableWrapper.EventOnMap,
        FloatingActionMenu.OnMenuToggleListener,
        IFilterListener,
        ISwipedHelper,
        IOpenSavedSearchDetail,
        SearchMapView,
        SaveSearchView,
        IClusterListener,
        IVoteListing,
        VoteListingView {

    private static final String TAG = FragmentDiscover.class.getSimpleName();
    private static final long ACCURACY = 0;
    private static final long UPDATE_INTERVAL = 400;
    private static final long FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL / 2;
    private Marker mCurrentMarker;
    private Marker mOldMarker;
    public static float sZoom = 0f;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationManager mLocationManager;
    private GoogleMap mGoogleMap;
    private MySupportMapFragment mMapFragment;
    private ArrayList<Polyline> mArrPolyline = new ArrayList<>();
    private ArrayList<LatLng> mArrLatLng = new ArrayList<>();

    private ArrayList<ListingItem> mArrListingSearch = new ArrayList<>();
    private ArrayList<ListingItem> mArrListingSearchBase = new ArrayList<>();
    private ArrayList<DiscoverMarker> mArrMarker = new ArrayList<>();
    private FastItemAdapter<SearchMapItem> mMapAdapterSearch;
    private FastItemAdapter<SearchListItem> mListAdapterSearch;
    private ClusterManager<DiscoverMarker> mClusterManager;
    private ArrayList<ClusterMarker> mArrClusterMarker = new ArrayList<>();
    private VoteListingPresenter mVotePresenter;
    private ItemTouchHelperCallback mItemTouchHelperCallback;
    private SaveSearchPresenter mSaveSearchPresenter;
    private ArrayList<ListingListSearchMap> mArrListingList = new ArrayList<>();
    private int mCurrentPosition = -1;
    private int mOldPosition = -1;
    private int mStroke = 5;

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
    private CustomLayoutManager mCustomLayoutManager;

    private LatLng mBeginArea;
    private PolygonOptions mRectOptions;
    private Polygon mPolyGon;
    private Location mCurrentLocation;

    private SearchMapPresenter mSearchMapPresenter;
    private ItemTouchHelperExtension mItemTouchHelper;
    private Point mPointSw;
    private Point mPointNe;
    private IPageChange mPageChange;
    private IMainListener mMainListener;
    private boolean mShowMap = true;
    private boolean mIsAttack;
    private boolean mShowFilter;
    private boolean mOpenSaveSearch;
    private boolean mInitData;
    private boolean mMoreNotCallApi;
    private boolean mShowSaveSearchDetail;
    private boolean mCallApiDev;
    private boolean mSaveBeforeVote;
    private EditText mEtSearch;
    @Bind(R.id.layoutMain)
    RelativeLayout mLayoutMain;
    @Bind(R.id.rvDiscover)
    RecyclerView mRvListingList;
    @Bind(R.id.layoutFragment)
    RelativeLayout mLayoutFragment;
    @Bind(R.id.layoutFragmentSavedSearch)
    RelativeLayout mLayoutFragmentSavedSearch;
    @Bind(R.id.layoutFragmentSavedSearchDetail)
    RelativeLayout mLayoutFragmentSavedSearchDetail;
    @Bind(R.id.layoutMapDiscover)
    RelativeLayout mLayoutMap;
    @Bind(R.id.layoutViewMap)
    LinearLayout mViewMap;
    @Bind(R.id.layoutViewList)
    LinearLayout mViewList;
    @Bind(R.id.layoutMenuContent)
    LinearLayout mLayoutMenuContent;
    @Bind(R.id.layoutMenu)
    LinearLayout mLayoutMenu;
    @Bind(R.id.rvListingMap)
    RecyclerView mRvListingMap;
    @Bind(R.id.layoutRvListing)
    RelativeLayout mLayoutRvListing;
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
    @Bind(R.id.layoutDiscoverContent)
    RelativeLayout mLayoutDiscoverContent;
    @Bind(R.id.layoutUpdateSearch)
    LinearLayout mLayoutUpdateSearch;
    @Bind(R.id.tvUpdateSearch)
    TextView mTvUpdateSearch;
    @Bind(R.id.layoutLoading)
    LinearLayout mLayoutLoading;
    @Bind(R.id.layoutEmpty)
    LinearLayout mLayoutEmpty;
    @Bind(R.id.layoutListingList)
    RelativeLayout mLayoutListingList;
    @Bind(R.id.layoutErrorZoom)
    RelativeLayout mLayoutErrorZoom;

    @OnClick(R.id.layoutMenu)
    public void onLayoutMenu() {
        closeSearchAction();
    }

    @OnClick(R.id.layoutSaveCurrentSearch)
    public void onLayoutSaveCurrentSearchClicked() {
        closeSearchAction();
        AnimUtils.hideViewFromBottom(mLayoutRvListing);
        mNameSavedSearch = mMainListener.getNameSearch("");
        if (mNameSavedSearch.length() == 0) {
            mNameSavedSearch = "New Search";
        }
        Intent intent = new Intent(getActivity(), SaveSearchActivity.class);
        intent.putExtra("sw", Utils.pointToLocationString(mPointSw, mGoogleMap));
        intent.putExtra("ne", Utils.pointToLocationString(mPointNe, mGoogleMap));

        intent.putExtra("mFt", mFt);
        intent.putExtra("mMaxPrice", mMaxPrice);
        intent.putExtra("mMinPrice", mMinPrice);
        intent.putExtra("mBed", mBed);
        intent.putExtra("mBath", mBath);
        intent.putExtra("mMinLs", mMinLs);
        intent.putExtra("mMaxLs", mMaxLs);
        intent.putExtra("mMinLsf", mMinLsf);
        intent.putExtra("mMaxLsf", mMaxLsf);
        intent.putExtra("mMinYb", mMinYb);
        intent.putExtra("mMaxYb", mMaxYb);
        intent.putExtra("mPt", mPt);
        intent.putExtra("mKeyword", mKeyword);
        intent.putExtra("mDc", mDc);
        intent.putExtra("name", mNameSavedSearch);
        startActivityForResult(intent, 3);
        getActivity().overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }

    @OnClick(R.id.layoutEditSearch)
    public void onLayoutEditSearchClicked() {
        closeSearchAction();
        AnimUtils.hideViewFromBottom(mLayoutRvListing);
        ObjectAnimator objectAnimator = AnimUtils.animationButtonMenuWithOpenViewWithListener(MainActivityConsumer.mDrawerArrowDrawable, mLayoutFragment, true);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (mShowSaveSearchDetail) {
                    getFragmentFilter().showLayout(true);
                } else {
                    getFragmentFilter().showLayout(false);
                }
                mShowFilter = true;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        objectAnimator.start();
    }

    @OnClick(R.id.layoutOpenSavedSearch)
    public void onLayoutOpenSavedSearchClicked() {
        closeSearchAction();
        AnimUtils.hideViewFromBottom(mLayoutRvListing);
        mOpenSaveSearch = true;
        ObjectAnimator objectAnimator = AnimUtils.animationButtonMenuWithOpenViewWithListener(MainActivityConsumer.mDrawerArrowDrawable, mLayoutFragmentSavedSearch, true);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getFragmentOpenSavedSearch().initData();
                    }
                }, 100);
                mEtSearch.setText("");
                mEtSearch.setFocusable(true);
                mEtSearch.setFocusableInTouchMode(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        objectAnimator.start();
    }

    @OnClick(R.id.layoutUpdateSearch)
    public void onLayoutUpdateSearch() {
        closeSearchAction();
        openSavedSearchDetailWithAnimationButton(CurrentSaveSearch.getInstance().getSearchDetail().getId());
    }

    @OnClick(R.id.layoutDrawArea)
    public void onLayoutDrawAreaClicked() {
        closeSearchAction();
        AnimUtils.hideViewFromBottom(mLayoutRvListing);
        mLayoutDraw.setVisibility(View.VISIBLE);
        mShowMap = true;
        mLayoutMap.setVisibility(View.VISIBLE);
        mLayoutListingList.setVisibility(View.GONE);
        mViewMap.setVisibility(View.GONE);
        mViewList.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.layoutClearSearch)
    public void onLayoutClearSearchClicked() {
        closeSearchAction();
        clearSearch();
        refreshMap();
        if (mLayoutUpdateSearch.getVisibility() == View.VISIBLE) {
            mLayoutUpdateSearch.setVisibility(View.GONE);
            if (mShowSaveSearchDetail) {
                mMapAdapterSearch.clear();
                mListAdapterSearch.clear();
                mRvListingMap.swapAdapter(mMapAdapterSearch, true);
                mRvListingList.swapAdapter(mListAdapterSearch, true);
                mShowSaveSearchDetail = false;
                mItemTouchHelperCallback.setDraw(false);
                mGoogleMap.clear();
                mEtSearch.setText("");
                mEtSearch.setFocusable(true);
                mEtSearch.setFocusableInTouchMode(true);
            }
        }
    }


    @OnClick(R.id.layoutFilter)
    public void onLayoutFilterClicked() {
        mLayoutDraw.setVisibility(View.GONE);
        closeSearchAction();
        AnimUtils.hideViewFromBottom(mLayoutRvListing);
        mShowFilter = true;
        ObjectAnimator objectAnimator = AnimUtils.animationButtonMenuWithOpenViewWithListener(MainActivityConsumer.mDrawerArrowDrawable, mLayoutFragment, true);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (mShowSaveSearchDetail) {
                    getFragmentFilter().showLayout(true);
                } else {
                    getFragmentFilter().showLayout(false);
                }
                mShowFilter = true;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        objectAnimator.start();
    }

    @OnClick(R.id.layoutSearchAction)
    public void onLayoutSearchActionClicked() {
        if (mLayoutMenu.getVisibility() == View.VISIBLE) {
            AnimUtils.collapseView(mLayoutMenuContent, mLayoutMenu);
        } else {
            mLayoutMenu.setVisibility(View.VISIBLE);
            AnimUtils.expandView(mLayoutMenuContent);
        }
    }

    @OnClick(R.id.layoutView)
    public void onLayoutViewClicked() {
        AnimUtils.collapseView(mLayoutMenuContent, mLayoutMenu);
        if (mShowMap) {
            mShowMap = false;
            mLayoutMap.setVisibility(View.GONE);
            mLayoutListingList.setVisibility(View.VISIBLE);
            mViewMap.setVisibility(View.VISIBLE);
            mViewList.setVisibility(View.GONE);
        } else {
            mShowMap = true;
            mLayoutMap.setVisibility(View.VISIBLE);
            mLayoutListingList.setVisibility(View.GONE);
            mViewMap.setVisibility(View.GONE);
            mViewList.setVisibility(View.VISIBLE);
        }

    }

    @OnClick(R.id.fabRefresh)
    public void onFabRefreshClicked() {
        mFabActionMap.close(true);
        mIsAttack = false;
        refreshMap();
        mLayoutDraw.setVisibility(View.GONE);
    }


    @OnClick(R.id.fabCurrentLocation)
    public void onFabCurrentLocationClicked() {
        mFabActionMap.close(true);
        mLayoutDraw.setVisibility(View.GONE);
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
        mLayoutDraw.setVisibility(View.GONE);
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
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager childFragmentManager = getChildFragmentManager();
        mMapFragment = (MySupportMapFragment) childFragmentManager.findFragmentById(R.id.mapDiscover);
        mEtSearch = (EditText) getActivity().findViewById(R.id.etSearch);
        mMapFragment.setOnEventMap(this);
        mMapFragment.getMapAsync(this);
        mLocationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        buildGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();
//        createFragmentFilter();
//        createFragmentOpenSavedSearch();

//        createFragmentSavedSearchDetail();
        mItemTouchHelperCallback = new ItemTouchHelperCallback();
        mItemTouchHelperCallback.setHelper(this);
        mItemTouchHelper = new ItemTouchHelperExtension(mItemTouchHelperCallback);
        mItemTouchHelper.attachToRecyclerView(mRvListingList);
        mSearchMapPresenter = new SearchMapPresenter(this);
        mVotePresenter = new VoteListingPresenter(this);
        mSaveSearchPresenter = new SaveSearchPresenter(this);
        mLayoutDraw.setOnTouchListener(this);
        mFabActionMap.setOnMenuToggleListener(this);
        mCustomLayoutManager = new CustomLayoutManager(CustomLayoutManager.HORIZONTAL);
        mCustomLayoutManager.setNoAnimationScroll(true);
        mCustomLayoutManager.attach(mRvListingMap);
        mCustomLayoutManager.setCallbackInFling(true);
        mCustomLayoutManager.setItemTransformer(new ScaleTransformerRecyclerView());
        mCustomLayoutManager.setOnItemSelectedListener(new CustomLayoutManager.OnItemSelectedListener() {
            @Override
            public void onItemSelected(RecyclerView recyclerView, View item, int position) {
                if (position != mPositionScroll) {
                    return;
                }
                Log.e("onItemSelected", String.valueOf(position));
                String id = mArrListingSearch.get(position).getListing().getId();
                if (mIsAttack) {
                    Log.e("mIsAttack", "mIsAttack");
                    mOldPosition = mCurrentPosition;
                    mCurrentPosition = position;
                    moveToMarkerWhenScrollList(id);
                } else {
//                    mOldPosition = -1;
                    mCurrentPosition = position;
                    Log.e("mIsAttackNO", "mIsAttackNO");
                    mIsAttack = true;
                }
                Log.e("mCurrentPosition", String.valueOf(mCurrentPosition));
                Log.e("mOldPosition", String.valueOf(mOldPosition));
            }
        });
//        mLayoutDiscoverContent.post(new Runnable() {
//            @Override
//            public void run() {
//                int height = mLayoutDiscoverContent.getHeight();
//                mLayoutDiscoverContent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height + Convert.dpToPx(50, getActivity())));
//            }
//        });
//        mLayoutMap.post(new Runnable() {
//            @Override
//            public void run() {
//                mPointNe = new Point(Utils.widthScreen, mLayoutMap.getTop());
//                mPointSw = new Point(0, mLayoutMap.getBottom() + Convert.dpToPx(50, getActivity()));
//            }
//        });

        mMapAdapterSearch = new FastItemAdapter<>();
        mListAdapterSearch = new FastItemAdapter<>();
        mRvListingList.setLayoutManager(new LinearLayoutManager(getActivity()));
        EventBus.getDefault().register(this);
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventRequestShowing(EventRequestShowing requestShowing) {
        if (mListAdapterSearch != null) {
            for (int i = 0; i < mListAdapterSearch.getItemCount(); i++) {
                if (mListAdapterSearch.getAdapterItem(i).getListing().getListing().getId().equalsIgnoreCase(requestShowing.getId())) {
                    mListAdapterSearch.getAdapterItem(i).getListing().setQueue(true);
                    mListAdapterSearch.notifyAdapterItemChanged(i);
                }
            }
            for (int i = 0; i < mMapAdapterSearch.getItemCount(); i++) {
                if (mMapAdapterSearch.getAdapterItem(i).getListing().getListing().getId().equalsIgnoreCase(requestShowing.getId())) {
                    mMapAdapterSearch.getAdapterItem(i).getListing().setQueue(true);
                    mMapAdapterSearch.notifyAdapterItemChanged(i);
                }
            }
        }
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onQueueEventRequest(EventQueueResponse eventQueue) {
        for (int i = 0; i < mMapAdapterSearch.getItemCount(); i++) {
            if (mMapAdapterSearch.getAdapterItem(i).getListing().getListing().getId().equalsIgnoreCase(eventQueue.id)) {
                if (eventQueue.b) {
                    mMapAdapterSearch.getAdapterItem(i).getListing().setQueue(true);
                    mMapAdapterSearch.notifyAdapterItemChanged(i);
                } else {
                    mMapAdapterSearch.getAdapterItem(i).getListing().setQueue(false);
                    mMapAdapterSearch.notifyAdapterItemChanged(i);
                }
            }
        }
        for (int i = 0; i < mListAdapterSearch.getItemCount(); i++) {
            if (mListAdapterSearch.getAdapterItem(i).getListing().getListing().getId().equalsIgnoreCase(eventQueue.id)) {
                if (eventQueue.b) {
                    mListAdapterSearch.getAdapterItem(i).getListing().setQueue(true);
                    mListAdapterSearch.notifyAdapterItemChanged(i);
                } else {
                    mListAdapterSearch.getAdapterItem(i).getListing().setQueue(false);
                    mListAdapterSearch.notifyAdapterItemChanged(i);
                }
            }
        }
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onQueueEventRequest(EventFavored eventFavored) {
        for (int i = 0; i < mMapAdapterSearch.getItemCount(); i++) {
            if (mMapAdapterSearch.getAdapterItem(i).getListing().getListing().getId().equalsIgnoreCase(eventFavored.id)) {
                if (eventFavored.add) {
                    mMapAdapterSearch.getAdapterItem(i).getListing().setFavorite(true);
                } else {
                    mMapAdapterSearch.getAdapterItem(i).getListing().setFavorite(false);
                }
                mMapAdapterSearch.notifyAdapterItemChanged(i);
            }
        }
        for (int i = 0; i < mListAdapterSearch.getItemCount(); i++) {
            if (mListAdapterSearch.getAdapterItem(i).getListing().getListing().getId().equalsIgnoreCase(eventFavored.id)) {
                if (eventFavored.add) {
                    mListAdapterSearch.getAdapterItem(i).getListing().setFavorite(true);
                } else {
                    mListAdapterSearch.getAdapterItem(i).getListing().setFavorite(false);
                }
                mListAdapterSearch.notifyAdapterItemChanged(i);
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void updateNameSearch(EventUpdateSearch updateSearch) {
        if (updateSearch.isChangeName()) {
            updateSearch.getName();
            mEtSearch.setText(Utils.getNameSavedSearch(CurrentSaveSearch.getInstance().getName(), getActivity()));
            if (mShowSaveSearchDetail) {
                mTvUpdateSearch.setText("Update search: " + CurrentSaveSearch.getInstance().getName());
            }
        }
    }


    public void initData() {
        if (!mInitData) {
            if (mGoogleMap != null) {
                if (mCurrentLocation != null) {
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), 15f));
                } else {
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(33.669355, -118.012377), 15f));
                }
            }
            mInitData = true;
            mCallApiDev = true;
        }
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

        Log.e("??", "??");
        Log.e("mMaxPrice", maxPrice);
        Log.e("mMinPrice", minPrice);
        Log.e("mBed", minBedRoom);
        Log.e("mBath", minBathRoom);
        Log.e("mMinLs", mMinLs);
        Log.e("mMaxLs", mMaxLs);
        Log.e("mMinLsf", mMinLsf);
        Log.e("mMinYb", mMinYb);
        Log.e("mPt", mPt);
        Log.e("mKeyword", mKeyword);
        Log.e("mDc", mDc);
        Log.e("??", "??");
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

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_discover;
    }

    public void setPageChange(IPageChange mPageChange) {
        this.mPageChange = mPageChange;
    }

    public void setMainListener(IMainListener mMainListener) {
        this.mMainListener = mMainListener;
    }

    public boolean isOpenSavedSearch() {
        return mOpenSaveSearch;
    }

    public void closeSearchAction() {
        mLayoutDraw.setVisibility(View.GONE);
        if (mLayoutMenu.getVisibility() == View.VISIBLE) {
            AnimUtils.collapseView(mLayoutMenuContent, mLayoutMenu);
        }
    }

    public void resetFragment(boolean clearSearchName) {
        if (mShowFilter) {
            cancelFilter();
            mShowFilter = false;
        }
        mShowSaveSearchDetail = false;
        mItemTouchHelperCallback.setDraw(false);
        cancelOpenSearch();
        mShowMap = true;
        mFabActionMap.close(true);
        mLayoutMenu.setVisibility(View.GONE);
        mLayoutMenuContent.setVisibility(View.GONE);
        mLayoutListingList.setVisibility(View.GONE);
        mLayoutMap.setVisibility(View.VISIBLE);
        mViewMap.setVisibility(View.GONE);
        mViewList.setVisibility(View.VISIBLE);
        mLayoutUpdateSearch.setVisibility(View.GONE);
        if (mLayoutRvListing.getVisibility() == View.VISIBLE) {
            AnimUtils.hideViewFromBottom(mLayoutRvListing);
        }
        mEtSearch.setFocusable(true);
        mEtSearch.setFocusableInTouchMode(true);
        if (clearSearchName) {
            mEtSearch.setText("");
        }
    }

    public void resetFragmentAfterSearch(boolean clearSearchName) {
        if (mShowFilter) {
            cancelFilter();
            mShowFilter = false;
        }
        mShowSaveSearchDetail = false;
        mItemTouchHelperCallback.setDraw(false);
        cancelOpenSearch();
        mShowMap = true;
        mGoogleMap.clear();
        mFabActionMap.close(true);
        mLayoutMenu.setVisibility(View.GONE);
        mLayoutMenuContent.setVisibility(View.GONE);
        mLayoutListingList.setVisibility(View.GONE);
        mLayoutMap.setVisibility(View.VISIBLE);
        mLayoutUpdateSearch.setVisibility(View.GONE);
        mListAdapterSearch.clear();
        mMapAdapterSearch.clear();
        mRvListingList.removeAllViews();
        mRvListingMap.removeAllViews();
        if (mLayoutRvListing.getVisibility() == View.VISIBLE) {
            AnimUtils.hideViewFromBottom(mLayoutRvListing);
        }
        mEtSearch.setFocusable(true);
        mEtSearch.setFocusableInTouchMode(true);
        if (clearSearchName) {
            mEtSearch.setText("");
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

    @Override
    public void onEventMap(String event, MotionEvent e) {

    }

    public void removeArea() {
        if (mPolyGon != null) {
            mPolyGon.remove();
        }
        for (int i = 0; i < mArrPolyline.size(); i++) {
            mArrPolyline.get(i).remove();
        }
    }

    public void moveToCurrentLocation() {
        if (mCurrentLocation != null) {
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude())));
        }
    }

    public void openDiscoverAfterSearch(String searchResult, boolean fromResult) {
        getAddressFromSearch(searchResult);
        //showLayout();
        resetFragmentAfterSearch(false);
        mCallApiDev = true;
    }

    public void createFragmentFilter() {
        FragmentFilter fragmentFilter = new FragmentFilter();
        fragmentFilter.setListener(this);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(mLayoutFragment.getId(), fragmentFilter, "filter");
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void createFragmentOpenSavedSearch() {
        FragmentOpenSavedSearch openSavedSearch = new FragmentOpenSavedSearch();
        openSavedSearch.setListener(this);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(mLayoutFragmentSavedSearch.getId(), openSavedSearch, "openSavedSearch");
        fragmentTransaction.commitAllowingStateLoss();
    }

//    public void createFragmentSavedSearchDetail() {
//        FragmentSavedSearchDetail savedSearchDetail = new FragmentSavedSearchDetail();
//        FragmentManager fragmentManager = getChildFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(mLayoutFragmentSavedSearchDetail.getId(), savedSearchDetail, "savedSearchDetail");
//        fragmentTransaction.commit();
//    }

    public CurrentFragment getCurrentStepInDiscover() {
        if (mShowFilter) {
            return CurrentFragment.IN_FIlTER;
        }
        if (mOpenSaveSearch) {
            return CurrentFragment.IN_OPEN_SEARCH;
        }
        return CurrentFragment.NONE;
    }

    public void cancelFilter() {
        getFragmentFilter().closeLayout();
        AnimUtils.animationButtonMenuWithCloseView(MainActivityConsumer.mDrawerArrowDrawable, mLayoutFragment, true);
        mShowFilter = false;
    }

    public void cancelOpenSearch() {
        if (mOpenSaveSearch) {
            AnimUtils.animationButtonMenuWithCloseView(MainActivityConsumer.mDrawerArrowDrawable, mLayoutFragmentSavedSearch, true);
            mOpenSaveSearch = false;
            getFragmentOpenSavedSearch().removeData();
            if (mShowSaveSearchDetail) {
                mEtSearch.setText(Utils.getNameSavedSearch(CurrentSaveSearch.getInstance().getName(), getActivity()));
                mEtSearch.setFocusable(false);
                mEtSearch.setFocusableInTouchMode(false);
            }
        }
    }

    @Override
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
        Log.e("mFt", mFt);
        Log.e("mMaxPrice", mMaxPrice);
        Log.e("mMinPrice", mMinPrice);
        Log.e("mBed", mBed);
        Log.e("mBath", mBath);
        Log.e("mMinLs", mMinLs);
        Log.e("mMaxLs", mMaxLs);
        Log.e("mMinLsf", mMinLsf);
        Log.e("mMinYb", mMinYb);
        Log.e("mPt", mPt);
        Log.e("mKeyword", mKeyword);
        Log.e("mDc", mDc);
        cancelFilter();


        mLayoutLoading.setVisibility(View.VISIBLE);
        mSearchMapPresenter.searchMap(setRequestParams(null, null, mNe, mSw, mMinPrice, mMaxPrice, mBed, mBath, "", "", mKeyword, mFt,
                "", "", "", "", mMinLs, mMaxLs, mMinLsf,
                mMaxLsf, mMinYb, mMaxYb, mDc, mPt));
        mClusterManager.onCameraIdle();
    }

    @Override
    public void resetFilter() {
        Log.e("resetFilter", "resetFilter");
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

    public FragmentOpenSavedSearch getFragmentOpenSavedSearch() {
        return (FragmentOpenSavedSearch) getChildFragmentManager().findFragmentByTag("openSavedSearch");
    }

    public FragmentFilter getFragmentFilter() {
        return (FragmentFilter) getChildFragmentManager().findFragmentByTag("filter");
    }

    public void openSavedSearchDetailWithAnimationButton(final String searchId) {
        mMainListener.openSaveSearchDetailFromDiscover(searchId, true);

    }

    @Override
    public void openSavedSearchDetail(final String searchId) {
        mMainListener.openSaveSearchDetailFromDiscover(searchId, false);

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

    //// Set up google api client and map //////////

    public synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setSmallestDisplacement(ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askPermission(Manifest.permission.ACCESS_FINE_LOCATION, 1);
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                        }
                    });
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

    @Override
    public void onStart() {
        super.onStart();
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

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

    private void askPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkLocationSettings();
            } else {
                Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askPermission(Manifest.permission.ACCESS_FINE_LOCATION, 1);
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
                startLocationUpdates();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 2:
                startLocationUpdates();
                break;
            case 3:
                if (resultCode == Activity.RESULT_OK) {
                    openSavedSearchDetailWithAnimationButton(CurrentSaveSearch.getInstance().getSearchDetail().getId());
                }
                break;
            default:
        }
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
    public void onMapReady(GoogleMap googleMap) {
        createFragmentFilter();
        createFragmentOpenSavedSearch();
        mPageChange.initFragment();
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
                Log.e("updateMarker", "updateMarker");
                updateMarker(mOldMarker, mCurrentMarker);
                return false;
            }
        });
        mClusterManager = new ClusterManager<>(getActivity(), mGoogleMap);
        mClusterManager.setRenderer(new ClusterListingRender(getActivity(), mGoogleMap, mClusterManager, this));
    }

    @Override
    public void onCameraMoveCanceled() {
    }

    @Override
    public void onCameraMove() {
    }

    @Override
    public void onCameraMoveStarted(int i) {
        dismissSnackBar();
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
    public void onCameraIdle() {
//        if (mGoogleMap.getCameraPosition().zoom < 10f) {
//            mLayoutErrorZoom.setVisibility(View.VISIBLE);
//            return;
//        }
        sZoom = mGoogleMap.getCameraPosition().zoom;
        Log.e("zoom", String.valueOf(mGoogleMap.getCameraPosition().zoom));
        mNe = Utils.pointToLocationString(mPointNe, mGoogleMap);
        mSw = Utils.pointToLocationString(mPointSw, mGoogleMap);
        if (mInitData && !mMoreNotCallApi) {
            mLayoutLoading.setVisibility(View.VISIBLE);
            mSearchMapPresenter.searchMap(setRequestParams(null, null, mNe, mSw, mMinPrice, mMaxPrice, mBed, mBath, "", "", mKeyword, mFt,
                    "", "", "", "", mMinLs, mMaxLs, mMinLsf,
                    mMaxLsf, mMinYb, mMaxYb, mDc, mPt));
            mClusterManager.onCameraIdle();
            mCallApiDev = false;
        } else {
            if (mInitData && mCallApiDev) {
                mLayoutLoading.setVisibility(View.VISIBLE);
                mSearchMapPresenter.searchMap(setRequestParams(null, null, mNe, mSw, mMinPrice, mMaxPrice, mBed, mBath, "", "", mKeyword, mFt,
                        "", "", "", "", mMinLs, mMaxLs, mMinLsf,
                        mMaxLsf, mMinYb, mMaxYb, mDc, mPt));
                mClusterManager.onCameraIdle();
                mCallApiDev = false;
            }
        }
    }

    public void setShowListingDetail(boolean mIsShowListingDetail) {
        this.mShowSaveSearchDetail = mIsShowListingDetail;
    }

    @Override
    public void searchMapSuccess(ResponseSearchMap responseSearchMap) {
        if (responseSearchMap.getData().getTotal() == 0) {
            mLayoutEmpty.setVisibility(View.VISIBLE);
            mRvListingList.setVisibility(View.GONE);
            if (!mOpenSaveSearch) {
                showSnackBar(mLayoutBgActionMap, TypeDialog.MESSAGES, responseSearchMap.getMessage(), "searchEmpty");
            }
            mLayoutLoading.setVisibility(View.GONE);
            return;
        }
        mLayoutEmpty.setVisibility(View.GONE);
        mRvListingList.setVisibility(View.VISIBLE);
        handlerDataSearch(responseSearchMap);
        mLayoutLoading.setVisibility(View.GONE);
    }

    @Override
    public void searchMapFail(String message) {
        mLayoutLoading.setVisibility(View.GONE);
        if (!mOpenSaveSearch) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "searchFail");
        }
    }

    @Override
    public void searchMapFail(@StringRes int message) {
        mLayoutLoading.setVisibility(View.GONE);
        if (!mOpenSaveSearch) {
            showSnackBar(mLayoutMain, TypeDialog.ERROR, message, "searchFail");
        }
    }


    public void getAddressFromSearch(String address) {

        IGeocode iGeocode = ServiceGeneratorConsumer.createService(IGeocode.class);
        iGeocode.getLocation(address, "true").enqueue(new Callback<Geocode>() {
            @Override
            public void onResponse(Call<Geocode> call, Response<Geocode> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("OK")) {
                        String type = response.body().getAddress().get(0).getAddComponents().get(0).getTypes().get(0);
                        Double latitude = Double.parseDouble(response.body().getAddress().get(0).getGeometry().getLocation().getLat());
                        Double longitude = Double.parseDouble(response.body().getAddress().get(0).getGeometry().getLocation().getLng());
                        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), Utils.getZoomByAddress(type)));
                    }
                }
            }

            @Override
            public void onFailure(Call<Geocode> call, Throwable t) {

            }
        });
    }

    // Handler data when search map

    public void handlerDataSearch(ResponseSearchMap responseSearchMap) {
        mRvListingMap.swapAdapter(mMapAdapterSearch, true);
        mRvListingList.swapAdapter(mListAdapterSearch, true);
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
            mRvListingList.setVisibility(View.GONE);
        } else {
            mLayoutEmpty.setVisibility(View.GONE);
            mRvListingList.setVisibility(View.VISIBLE);
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
                    Log.e("tuday", "tuday");
                    updateMarker(null, mArrMarker.get(i).getMarker());
                } else {
                    Log.e("tuday1", "tuday1");
                    updateMarker(mArrMarker.get(mOldPosition).getMarker(), mArrMarker.get(i).getMarker());
                }
            }
        }
    }

    public void updateMarker(Marker oldMarker, Marker currentMarker) {
        if (oldMarker != null) {
            Log.e("oldMarker", "!oldMarker");
        } else {
            Log.e("oldMarker", "oldMarker");
        }
        if (currentMarker != null) {
            Log.e("currentMarker", "!currentMarker");
        } else {
            Log.e("currentMarker", "currentMarker");
        }
        IconGenerator iconFactory = new IconGenerator(getActivity());
        ArrayList<DiscoverMarker> discoverMarkers = new ArrayList<>();
        for (int i = 0; i < mArrMarker.size(); i++) {
            if (mArrMarker.get(i).getMarker().isVisible()) {
                discoverMarkers.add(mArrMarker.get(i));
            }
        }

        for (int i = 0; i < discoverMarkers.size(); i++) {
            Log.e("discoverMarkers", discoverMarkers.get(i).getData().getListing().toString());
            Marker marker1 = discoverMarkers.get(i).getMarker();
            if (currentMarker.getId().equalsIgnoreCase(marker1.getId())) {
                marker1.setIcon(BitmapDescriptorFactory.fromBitmap(Utils.getIconGeneratorRound(iconFactory, StatusMarker.SELECTED,
                        getActivity(), discoverMarkers.get(i).getData().getListing().getPrice())
                        .makeIcon()));
                discoverMarkers.get(i).setStatus(StatusMarker.SELECTED);
                Log.e("vaoday", "vaoday");
                Log.e("i", String.valueOf(i));
                openListWithMarkerSelected(i);
            }
            if (oldMarker != null) {
                Log.e("null", "null");
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
                    Log.e("Sana", "sana");
                }
            }
        }
    }

    private int mPositionScroll = -1;

    public void openListWithMarkerSelected(final int position) {
        mPositionScroll = position;
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
        mRvListingList.getAdapter().notifyDataSetChanged();
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

    // Handler data search detail in discover tab

    public void openSaveSearchDetailInDiscover() {
        if (mOpenSaveSearch) {
            cancelOpenSearch();
        }
        mGoogleMap.clear();
        getActivity().findViewById(R.id.layoutBarBottom).setVisibility(View.VISIBLE);
        mLayoutUpdateSearch.setVisibility(View.VISIBLE);
        mTvUpdateSearch.setText("Update search: " + CurrentSaveSearch.getInstance().getName());
        mArrMarker.clear();
        mArrClusterMarker.clear();
        mClusterManager.clearItems();
        mArrListingSearch.clear();
        mArrListingSearchBase.clear();
        if (!mShowMap) {
            mShowMap = true;
            mLayoutMap.setVisibility(View.VISIBLE);
            mLayoutListingList.setVisibility(View.GONE);
            mViewMap.setVisibility(View.VISIBLE);
            mViewList.setVisibility(View.GONE);
        }

        mShowSaveSearchDetail = true;
        mItemTouchHelperCallback.setDraw(true);
        ConditionFull conditionFull = CurrentSaveSearch.getInstance().getSearchDetail().getConditions().get(0);
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


        if (CurrentSaveSearch.getInstance().getSearchDetail().getConditions().size() != 0) {
            if (CurrentSaveSearch.getInstance().getSearchDetail().getConditions().get(0).getSw() != null) {
                LatLngBounds latLngBounds = new LatLngBounds(
                        Utils.getPositionFromLocation(CurrentSaveSearch.getInstance().getSearchDetail().getConditions().get(0).getSw()),
                        Utils.getPositionFromLocation(CurrentSaveSearch.getInstance().getSearchDetail().getConditions().get(0).getNe()));
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 0));
                mCallApiDev = true;
            }
        }
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
    public void voteSuccess(ListingFull listingFull) {
//        mListAdapterSearch.notifyItemChanged(mPosition);
    }

    @Override
    public void voteFail(String message) {

    }

    @Override
    public void voteFail(@StringRes int message) {

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
        showSnackBar(mLayoutMain, TypeDialog.ERROR, message, "saveSearchFail");
    }

    public void showLayout() {
        if (mLayoutMain.getVisibility() == View.GONE) {
            mLayoutMain.setVisibility(View.INVISIBLE);
            mLayoutDiscoverContent.post(new Runnable() {
                @Override
                public void run() {
                    int height = mLayoutDiscoverContent.getHeight();
                    mLayoutDiscoverContent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height + Convert.dpToPx(50, getActivity())));
                }
            });
            mLayoutMap.post(new Runnable() {
                @Override
                public void run() {
                    mPointNe = new Point(Utils.widthScreen, mLayoutMap.getTop());
                    mPointSw = new Point(0, mLayoutMap.getBottom() + Convert.dpToPx(50, getActivity()));
                }
            });
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLayoutMain.setVisibility(View.VISIBLE);
                }
            }, 200);
        }
    }
}
