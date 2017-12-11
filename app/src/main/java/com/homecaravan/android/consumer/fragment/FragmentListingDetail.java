package com.homecaravan.android.consumer.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.activity.ConversationActivity;
import com.homecaravan.android.consumer.activity.StreetViewActivity;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumerbase.ConsumerUser;
import com.homecaravan.android.consumer.consumermvp.listingmvp.GetListingDetailPresenter;
import com.homecaravan.android.consumer.consumermvp.listingmvp.GetListingDetailView;
import com.homecaravan.android.consumer.consumermvp.searchmvp.VoteListingPresenter;
import com.homecaravan.android.consumer.consumermvp.searchmvp.VoteListingView;
import com.homecaravan.android.consumer.message.messagegetthreadidmvp.GetThreadIdPresenter;
import com.homecaravan.android.consumer.message.messagegetthreadidmvp.GetThreadIdView;
import com.homecaravan.android.consumer.model.CaravanQueue;
import com.homecaravan.android.consumer.model.CurrentConsumerListing;
import com.homecaravan.android.consumer.model.EventQueueRequest;
import com.homecaravan.android.consumer.model.EventQueueResponse;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.responseapi.ImageListingDetail;
import com.homecaravan.android.consumer.model.responseapi.Listing;
import com.homecaravan.android.consumer.model.responseapi.ListingFull;
import com.homecaravan.android.consumer.model.responseapi.ResponseMessageGetThreadId;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.consumer.widget.ScrollView;
import com.homecaravan.android.ui.CircleImageView;
import com.homecaravan.android.ui.MapWrapperLayout;
import com.homecaravan.android.ui.MySupportMapFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class FragmentListingDetail extends BaseFragment implements
        OnMapReadyCallback,
        MapWrapperLayout.OnDragListener,
        View.OnTouchListener,
        VoteListingView,
        GetListingDetailView,
        GetThreadIdView {

    private MySupportMapFragment mSupportMapFragment;
    private int mLayoutHeight;
    private float mHeightShowLayout;
    private ArrayList<String> mArr = new ArrayList<>();
    private VoteListingPresenter mVoteListingPresenter;
    private GetListingDetailPresenter mGetListingDetailPresenter;
    private float mdX;
    private float mPositionXVote;
    private float mPositionXMoveVote;
    private boolean mInAnimation;
    private GoogleMap mGoogleMap;
    private String mFullAddress = "";
    @Bind(R.id.tvAddress1)
    TextView mAddress1;
    @Bind(R.id.tvAddress2)
    TextView mAddress2;
    @Bind(R.id.tvListed)
    TextView mListed;
    @Bind(R.id.tvPrice)
    TextView mPrice;
    @Bind(R.id.tvBed)
    TextView mBed;
    @Bind(R.id.tvBath)
    TextView mBath;
    @Bind(R.id.tvSquare)
    TextView mSquare;
    @Bind(R.id.tvLiving)
    TextView mLiving;
    @Bind(R.id.tvPropertyType)
    TextView mPropertyType;
    @Bind(R.id.ivAgent)
    CircleImageView mIvAgent;
    @Bind(R.id.tvAgentName)
    TextView mAgentName;
    @Bind(R.id.layoutLike)
    LinearLayout mLayoutLike;
    @Bind(R.id.tvCountImage)
    TextView mCountImage;
    @Bind(R.id.layoutDislike)
    LinearLayout mLayoutDislike;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    @Bind(R.id.layoutContent)
    LinearLayout mLayoutContent;
    @Bind(R.id.layoutButtonBottom)
    RelativeLayout mLayoutButtonBottom;
    @Bind(R.id.scrollView)
    ScrollView mScrollView;
    @Bind(R.id.layout1)
    RelativeLayout mLayout;
    @Bind(R.id.layoutStatus)
    RelativeLayout mLayoutStatus;
    @Bind(R.id.layoutButton)
    RelativeLayout mLayoutButton;
    @Bind(R.id.layoutViewPager)
    LinearLayout mLayoutViewPager;
    @Bind(R.id.layoutVoteContent)
    LinearLayout mLayoutVoteContent;
    @Bind(R.id.layoutDetail)
    LinearLayout mLayoutDetail;
    @Bind(R.id.tvVoteDown)
    TextView mTvVoteDown;
    @Bind(R.id.tvVoteUp)
    TextView mTvVoteUp;
    @Bind(R.id.layoutVoteDown)
    LinearLayout mLayoutVoteDown;
    @Bind(R.id.layoutVoteUp)
    LinearLayout mLayoutVoteUp;
    @Bind(R.id.layoutBook)
    LinearLayout mLayoutBook;
    @Bind(R.id.layoutBookBottom)
    LinearLayout mLayoutBookBottom;
    @Bind(R.id.layoutListed)
    LinearLayout mLayoutListed;

    @Bind(R.id.layoutFavoriteLike)
    RelativeLayout mLayoutFavoriteLike;
    @Bind(R.id.ivFavoriteLike)
    ImageView mIvFavoriteLike;
    @Bind(R.id.tvFavoriteLike)
    TextView mTvFavoriteLike;

    @Bind(R.id.layoutSuperVote)
    RelativeLayout mLayoutSuperVote;
    @Bind(R.id.ivSuperVote)
    ImageView mIvSuperVote;
    @Bind(R.id.tvSuperVote)
    TextView mTvSuperVote;

    @Bind(R.id.layoutShareListing)
    RelativeLayout mLayoutShare;
    @Bind(R.id.ivShare)
    ImageView mIvShare;
    @Bind(R.id.tvShare)
    TextView mTvShare;

    @Bind(R.id.layoutSchedule)
    RelativeLayout mLayoutSchedule;
    @Bind(R.id.ivSchedule)
    ImageView mIvSchedule;
    @Bind(R.id.tvSchedule)
    TextView mTvSchedule;

    @Bind(R.id.layoutPrice)
    RelativeLayout mLayoutPrice;
    @Bind(R.id.ivDislikePrice)
    ImageView mIvDislikePrice;
    @Bind(R.id.tvDislikePrice)
    TextView mTvDislikePrice;


    @Bind(R.id.layoutCondition)
    RelativeLayout mLayoutCondition;
    @Bind(R.id.ivCondition)
    ImageView mIvCondition;
    @Bind(R.id.tvCondition)
    TextView mTvCondition;

    @Bind(R.id.layoutLocation)
    RelativeLayout mLayoutLocation;
    @Bind(R.id.ivLocation)
    ImageView mIvLocation;
    @Bind(R.id.tvLocation)
    TextView mTvLocation;

    @Bind(R.id.layoutSize)
    RelativeLayout mLayoutSize;
    @Bind(R.id.ivSize)
    ImageView mIvSize;
    @Bind(R.id.tvSize)
    TextView mTvSize;

    @Bind(R.id.layoutVote)
    RelativeLayout mLayoutVote;
    @Bind(R.id.layoutBottom)
    LinearLayout mLayoutBottom;

    @OnClick(R.id.layoutViewStreet)
    public void viewStreet() {
        Intent intent = new Intent(getActivity(), StreetViewActivity.class);
        intent.putExtra("lat", CurrentConsumerListing.getInstance().getListingData().getAddress().getLat());
        intent.putExtra("lng", CurrentConsumerListing.getInstance().getListingData().getAddress().getLng());
        startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.layoutBackLike)
    public void backViewVote() {
        AnimUtils.moveX(mLayoutVoteContent, 0);
    }

    @OnClick(R.id.layoutBackDislike)
    public void backViewVote1() {
        AnimUtils.moveX(mLayoutVoteContent, 0);
    }

    @OnClick(R.id.layoutBook)
    public void onLayoutBookClicked() {
        EventBus.getDefault().post(new EventQueueRequest(CurrentConsumerListing.getInstance().getListingData().getId(), true));
        mLayoutBook.setAlpha(0.7f);
        mLayoutBook.setEnabled(false);
        mLayoutBookBottom.setAlpha(0.7f);
        mLayoutBookBottom.setEnabled(false);
    }

    @OnClick(R.id.layoutAsk)
    public void onLayoutAskClicked() {
    }

    @OnClick(R.id.layoutShare)
    public void onLayoutShareClicked() {
    }

    @OnClick(R.id.layoutFavorites)
    public void onLayoutFavoritesClicked() {
        //EventBus.getDefault().post(new EventQueueResponse(CurrentConsumerListing.getInstance().getListingData().getId()));
    }

    @OnClick(R.id.voteUp)
    public void onVoteUpClicked() {
        mLayoutLike.setVisibility(View.VISIBLE);
        mLayoutDislike.setVisibility(View.GONE);
        AnimUtils.moveX(mLayoutVoteContent, -1500);

    }

    @OnClick(R.id.voteDown)
    public void onVoteDownClicked() {
        AnimUtils.moveX(mLayoutVoteContent, 1500);
        mLayoutLike.setVisibility(View.GONE);
        mLayoutDislike.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.layoutBackLike)
    public void backLike() {
        AnimUtils.moveX(mLayoutVoteContent, 0);
    }

    @OnClick(R.id.layoutBackDislike)
    public void backDisLike() {
        AnimUtils.moveX(mLayoutVoteContent, 0);
    }


    @OnClick(R.id.layoutFavoriteLike)
    public void onLayoutFavoriteLike() {
        likeListing("favorite");
    }

    @OnClick(R.id.layoutSuperVote)
    public void onLayoutSuperVote() {
        likeListing("superVote");
    }

    @OnClick(R.id.layoutShareListing)
    public void onLayoutShareListing() {
        likeListing("share");
    }

    @OnClick(R.id.layoutSchedule)
    public void onLayoutSchedule() {
        likeListing("schedule");
    }

    @OnClick(R.id.layoutPrice)
    public void onLayoutPrice() {
        disLikeListing("private");
    }

    @OnClick(R.id.layoutLocation)
    public void onLayoutLocation() {
        disLikeListing("location");
    }

    @OnClick(R.id.layoutCondition)
    public void onLayoutCondition() {
        disLikeListing("condition");
    }

    @OnClick(R.id.layoutSize)
    public void onLayoutSize() {
        disLikeListing("size");
    }


    @OnClick(R.id.layoutContact)
    public void onLayoutContactClicked() {
        if (!ConsumerUser.getInstance().getData().getId().equals(CurrentConsumerListing.getInstance().getListingData().getAgentId())) {
            GetThreadIdPresenter mGetThreadIdPresenter = new GetThreadIdPresenter(this);
            mGetThreadIdPresenter.getThreadId("", "", CurrentConsumerListing.getInstance().getListingData().getId(),
                    CurrentConsumerListing.getInstance().getListingData().getAgentName(), CurrentConsumerListing.getInstance().getListingData().getAgentId());
            showLoading();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSupportMapFragment = (MySupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapProperty);
        mSupportMapFragment.getMapAsync(this);
        mSupportMapFragment.setOnDragListener(this);
        mLayoutHeight = mLayout.getLayoutParams().height;
        mVoteListingPresenter = new VoteListingPresenter(this);
        mGetListingDetailPresenter = new GetListingDetailPresenter(this);
        mScrollView.setScrollingEnabled(true);
        mLayoutButton.post(new Runnable() {
            @Override
            public void run() {
                mHeightShowLayout = mLayoutContent.getY();
                mHeightShowLayout = mHeightShowLayout + mLayoutButton.getHeight();
            }
        });
        mAddress1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                copyAddress(mFullAddress);
                return false;
            }
        });

        mAddress2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                copyAddress(mFullAddress);
                return false;
            }
        });
        mLayoutVoteContent.setOnTouchListener(this);
        mScrollView.setOnScrollChangedListener(
                new ScrollView.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged(int l, final int t, int oldl, int oldt) {
                        if (t > mHeightShowLayout) {
                            if (mLayoutButtonBottom.getVisibility() == View.GONE) {
                                mLayoutButtonBottom.setVisibility(View.VISIBLE);
                                AnimUtils.slideUp(getActivity(), mLayoutButtonBottom);
                            }
                        } else {
                            if (mLayoutButtonBottom.getVisibility() == View.VISIBLE && !mInAnimation) {
                                Animation animation = AnimUtils.slideDownListener(getActivity(), mLayoutButtonBottom);
                                animation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                        mInAnimation = true;
                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        mInAnimation = false;
                                        mLayoutButtonBottom.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                            }
                        }
                        mLayoutViewPager.setAlpha((1 / mHeightShowLayout) * t);
                        //handleScroll(t);
                    }
                }
        );
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCountImage.setText(String.valueOf(position + 1) + "/" + String.valueOf(mArr.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    public void copyAddress(String address) {
        ClipboardManager myClipboard;
        myClipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText("text", address);
        myClipboard.setPrimaryClip(myClip);
        showSnackBar(mScrollView, TypeDialog.SUCCESS, "Copy address success", "copyLink");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_listing_detail_cbs;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }

    public void likeListing(String type) {
        mLayoutVoteUp.setVisibility(View.VISIBLE);
        mLayoutVoteDown.setVisibility(View.GONE);
        AnimUtils.moveX(mLayoutVoteContent, 0);
    }

    public void disLikeListing(String type) {
        mLayoutVoteUp.setVisibility(View.GONE);
        mLayoutVoteDown.setVisibility(View.VISIBLE);
        AnimUtils.moveX(mLayoutVoteContent, 0);
    }


    private void handleScroll(int top) {
        int scrolledImageHeight = Math.min(mLayoutHeight, Math.max(0, top));
        ViewGroup.MarginLayoutParams imageParams = (ViewGroup.MarginLayoutParams) mLayout.getLayoutParams();
        int newImageHeight = mLayoutHeight - scrolledImageHeight;
        if (imageParams.height != newImageHeight) {
            imageParams.height = newImageHeight;
            imageParams.topMargin = scrolledImageHeight;
            mLayout.setLayoutParams(imageParams);
        }
    }

    public void hideLayout() {
        mScrollView.setVisibility(View.GONE);
    }

    public void showLayout(String listingId) {
        mGetListingDetailPresenter.getListingDetail(listingId);
    }

    public void initData() {
        mLayoutBook.setAlpha(1f);
        mLayoutBook.setEnabled(true);
        mLayoutBookBottom.setAlpha(1f);
        mLayoutBookBottom.setEnabled(true);
        ArrayList<String> queueIds = CaravanQueue.getInstance().getIds();
        for (int i = 0; i < queueIds.size(); i++) {
            if (queueIds.get(i).equalsIgnoreCase(CurrentConsumerListing.getInstance().getListingData().getId())) {
                mLayoutBook.setAlpha(0.7f);
                mLayoutBook.setEnabled(false);
                mLayoutBookBottom.setAlpha(0.7f);
                mLayoutBookBottom.setEnabled(false);
            }
        }
        mViewPager.setVisibility(View.INVISIBLE);
        mArr.clear();
        ArrayList<ImageListingDetail> images = CurrentConsumerListing.getInstance().getListingData().getListingImages().getArrImg();
        Listing listing = CurrentConsumerListing.getInstance().getListingData();
        for (int i = 0; i < images.size(); i++) {
            mArr.add(images.get(i).getImage());
        }
        if (mArr.size() != 0) {
            mCountImage.setText(String.valueOf(1) + "/" + String.valueOf(mArr.size()));
        } else {
            mCountImage.setText("0/0");
        }
        mFullAddress = listing.getAddress().getFullAddress().getOneLine();
        mAddress1.setText(listing.getAddress().getAddress1());
        mAddress2.setText(listing.getAddress().getCity());
        if (listing.getYearBuilt() != null) {
            mListed.setText(listing.getYearBuilt());
            mListed.setVisibility(View.VISIBLE);
        } else {
            mListed.setVisibility(View.INVISIBLE);
        }
        mPrice.setText(Utils.getPrice(listing.getPrice().getValue()));
        mBath.setText(listing.getBathrooms());
        mBed.setText(listing.getBedrooms());
        mSquare.setText(listing.getLivingSquare().getValue());
        mLiving.setText(listing.getLivingSquare().getValue());
        mPropertyType.setText(listing.getDescription());
        LatLng latLng = new LatLng(listing.getAddress().getLat(), listing.getAddress().getLng());
        View viewMarker = getActivity().getLayoutInflater().inflate(R.layout.layout_cluster, null, false);
        IconGenerator iconGenerator = new IconGenerator(getActivity());
        iconGenerator.setBackground(null);
        iconGenerator.setContentView(viewMarker);
        mGoogleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon())));
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f));
        Glide.with(getActivity().getApplicationContext()).load(listing.getAgentAvatar()).asBitmap().centerCrop().placeholder(R.drawable.avatar_default).into(mIvAgent);
        mAgentName.setText(listing.getAgentName());
        mScrollView.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLayoutVote.setVisibility(View.VISIBLE);
                mLayoutBottom.setVisibility(View.VISIBLE);
            }
        }, 500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new PerformanceBackGround().execute();
            }
        }, 700);
        mLayoutButton.post(new Runnable() {
            @Override
            public void run() {
                mHeightShowLayout = mLayoutContent.getY();
                mHeightShowLayout = mHeightShowLayout + mLayoutButton.getHeight();
            }
        });
    }

    private void centerViewVertically(View view) {
        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.setTranslationY(-v.getHeight() / 2);
                v.removeOnLayoutChangeListener(this);
            }
        });
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
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mScrollView.setScrollingEnabled(false);
                mdX = view.getX() - motionEvent.getRawX();
                mPositionXVote = motionEvent.getRawX();
                break;

            case MotionEvent.ACTION_MOVE:
                mPositionXMoveVote = motionEvent.getRawX() + mdX;
                if (mPositionXVote < motionEvent.getRawX()) {
                    mLayoutLike.setVisibility(View.VISIBLE);
                    mLayoutDislike.setVisibility(View.GONE);
                } else {
                    mLayoutLike.setVisibility(View.GONE);
                    mLayoutDislike.setVisibility(View.VISIBLE);
                }
                view.animate()
                        .x(motionEvent.getRawX() + mdX)
                        .y(view.getY())
                        .setDuration(0)
                        .start();
                break;

            case MotionEvent.ACTION_UP:
                if (mPositionXMoveVote > mPositionXVote) {
                    if (view.getWidth() / 5 < mPositionXMoveVote) {
                        AnimUtils.moveX(mLayoutVoteContent, 1500);
                    } else {
                        AnimUtils.moveX(mLayoutVoteContent, 0);
                    }
                } else {
                    if (-view.getWidth() / 5 > mPositionXMoveVote) {
                        AnimUtils.moveX(mLayoutVoteContent, -1500);
                    } else {
                        AnimUtils.moveX(mLayoutVoteContent, 0);
                    }

                }
                mScrollView.setScrollingEnabled(true);
                break;

        }
        return true;
    }

    @Override
    public void voteSuccess(ListingFull listingFull) {

    }

    @Override
    public void voteFail(String message) {

    }

    @Override
    public void voteFail(@StringRes int message) {

    }

    @Override
    public void getListingDetailSuccess(Listing listing) {
        CurrentConsumerListing.getInstance().setListingData(listing);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mScrollView.getVisibility() == View.GONE) {
                    mScrollView.smoothScrollTo(0, 0);
                    initData();
//                    new PerformanceBackGround().execute();
                }
            }
        }, 200);
    }

    @Override
    public void getListingDetailFail(String message) {
        showSnackBar(getView(), TypeDialog.WARNING, message, "getListingDetail");
    }

    @Override
    public void getListingDetailFail(@StringRes int message) {
        showSnackBar(getView(), TypeDialog.ERROR, message, "getListingDetail");
    }

    private class PerformanceBackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            ImagePagerAdapter adapter = new ImagePagerAdapter(getChildFragmentManager(), mArr.size());
            mViewPager.setAdapter(adapter);
            //mViewPager.setOffscreenPageLimit(mArr.size());
            mViewPager.setVisibility(View.VISIBLE);
            super.onPostExecute(result);
        }
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {
        private final int mSize;

        public ImagePagerAdapter(FragmentManager fm, int size) {
            super(fm);
            mSize = size;
        }

        @Override
        public int getCount() {
            return mSize;
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentImage.newInstance(mArr.get(position));
        }
    }

    public void resetLike() {

        mLayoutSchedule.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
        mIvSchedule.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        mTvSchedule.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));

        mLayoutShare.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        mTvShare.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        mIvShare.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorTextMain));

        mLayoutSuperVote.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        mTvSuperVote.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        mIvSuperVote.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorTextMain));

        mLayoutFavoriteLike.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        mTvFavoriteLike.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        mIvFavoriteLike.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorTextMain));

    }

    public void resetDislike() {
        mLayoutPrice.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        mTvDislikePrice.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        mIvDislikePrice.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorTextMain));

        mLayoutCondition.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        mTvCondition.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        mIvCondition.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorTextMain));

        mLayoutLocation.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        mTvLocation.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        mIvLocation.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorTextMain));

        mLayoutSize.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        mTvSize.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        mIvSize.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorTextMain));

    }

    @Override
    public void getThreadIdAtCaravanSuccess(ResponseMessageGetThreadId messageGetThreadId, int position, String threadName) {

    }

    @Override
    public void getThreadIdSuccess(ResponseMessageGetThreadId messageGetThreadId, String threadName) {
        Log.e("DaoDiDem", "getThreadIdSuccess: threadId: " + messageGetThreadId.getThreadId());
        if (!HomeCaravanApplication.mLoginSocketSuccess) {
            return;
        }
        Intent intent = new Intent(getActivity(), ConversationActivity.class);
        intent.putExtra("THREAD_ID", messageGetThreadId.getThreadId());
        String responseMessage1 = "I’m driving right now";
        intent.putExtra("RESPONSE_MESSAGE_1", responseMessage1);
        intent.putExtra("MESSAGE_THREAD_NAME", threadName);
        hideLoading();
        startActivity(intent);
    }

    @Override
    public void getThreadIdFail() {

    }

    @Override
    public void getThreadIdFail(@StringRes int message) {

    }
}
