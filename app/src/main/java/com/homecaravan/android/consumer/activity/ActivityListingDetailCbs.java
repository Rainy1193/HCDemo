package com.homecaravan.android.consumer.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.fragment.FragmentImage;
import com.homecaravan.android.consumer.map.TouchableWrapper;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.consumer.widget.ScrollView;
import com.homecaravan.android.ui.MapWrapperLayout;
import com.homecaravan.android.ui.MySupportMapFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ActivityListingDetailCbs extends AppCompatActivity implements
        OnMapReadyCallback,
        TouchableWrapper.EventOnMap,
        MapWrapperLayout.OnDragListener,
        View.OnTouchListener {
    private MySupportMapFragment mSupportMapFragment;
    private int mLayoutHeight;
    private float mHeightShowLayout;
    private ArrayList<String> mArr = new ArrayList<>();
    private float mdX;
    private float mPositionXVote;
    private float mPositionXMoveVote;
    private int mCurrentPage = 1;
    private int mOldPage = 1;
    private boolean mInAnimation;
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

    @Bind(R.id.tvDescription)
    TextView mTvDescription;

    @Bind(R.id.tvDetail)
    TextView mTvDetail;

    @Bind(R.id.tvVideo)
    TextView mTvVideo;

    @Bind(R.id.tvReview)
    TextView mTvReview;

    @Bind(R.id.layoutDescription)
    RelativeLayout mLayoutDescription;

    @Bind(R.id.layoutDetail)
    LinearLayout mLayoutDetail;

    @Bind(R.id.layoutVideo)
    RelativeLayout mLayoutVideo;

    @Bind(R.id.layoutReview)
    RelativeLayout mLayoutReview;

    @Bind(R.id.tvVoteDown)
    TextView mTvVoteDown;

    @Bind(R.id.tvVoteUp)
    TextView mTvVoteUp;

    @Bind(R.id.layoutVoteDown)
    LinearLayout mLayoutVoteDown;

    @Bind(R.id.layoutVoteUp)
    LinearLayout mLayoutVoteUp;

    @OnClick(R.id.tvDetail)
    public void showDetail() {
        mOldPage = mCurrentPage;
        mCurrentPage = 1;
        AnimUtils.tabSelectionListingDetail(this, mLayoutDetail, mTvDetail);
        if (mOldPage == 1) {
            return;
        }
        changeTab();
    }

    @OnClick(R.id.tvDescription)
    public void showDescription() {
        mOldPage = mCurrentPage;
        mCurrentPage = 2;
        AnimUtils.tabSelectionListingDetail(this, mLayoutDescription, mTvDescription);
        if (mOldPage == 2) {
            return;
        }
        changeTab();
    }

    @OnClick(R.id.tvVideo)
    public void showVideo() {
        mOldPage = mCurrentPage;
        mCurrentPage = 3;
        AnimUtils.tabSelectionListingDetail(this, mLayoutVideo, mTvVideo);
        if (mOldPage == 3) {
            return;
        }
        changeTab();
    }

    @OnClick(R.id.tvReview)
    public void showReview() {
        mOldPage = mCurrentPage;
        mCurrentPage = 4;
        AnimUtils.tabSelectionListingDetail(this, mLayoutReview, mTvReview);
        if (mOldPage == 4) {
            return;
        }
        changeTab();
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
    }

    @OnClick(R.id.layoutAsk)
    public void onLayoutAskClicked() {
    }

    @OnClick(R.id.layoutShare)
    public void onLayoutShareClicked() {
    }

    @OnClick(R.id.layoutFavorites)
    public void onLayoutFavoritesClicked() {
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
        likeListing();
    }

    @OnClick(R.id.layoutSuperVote)
    public void onLayoutSuperVote() {
        likeListing();
    }

    @OnClick(R.id.layoutShareListing)
    public void onLayoutShareListing() {
        likeListing();
    }

    @OnClick(R.id.layoutSchedule)
    public void onLayoutSchedule() {
        likeListing();
    }

    @OnClick(R.id.layoutPrice)
    public void onLayoutPrice() {
        disLikeListing();
    }

    @OnClick(R.id.layoutLocation)
    public void onLayoutLocation() {
        disLikeListing();
    }

    @OnClick(R.id.layoutCondition)
    public void onLayoutCondition() {
        disLikeListing();
    }

    @OnClick(R.id.layoutShare)
    public void onLayoutSize() {
        disLikeListing();
    }


    @OnClick(R.id.layoutContact)
    public void onLayoutContactClicked() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_detail_cbs);
        ButterKnife.bind(this);
        mSupportMapFragment = (MySupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapProperty);
        mSupportMapFragment.getMapAsync(this);
        mSupportMapFragment.setOnDragListener(this);
        mLayoutHeight = mLayout.getLayoutParams().height;
        mArr.add("https://cdn.houseplans.com/product/grci4jo2shllu309jtpqc7hge2/w1024.jpg");
        mArr.add("https://cdn.houseplans.com/product/fmvu1v5m41pjraococpepjd1no/w1024.jpg");
        mArr.add("https://cdn.houseplans.com/product/quvpogbon6i60i7sqr0snrrsgd/w1024.jpg");
        mScrollView.setScrollingEnabled(true);
        mLayoutButton.post(new Runnable() {
            @Override
            public void run() {
                mHeightShowLayout = mLayoutContent.getY();
                mHeightShowLayout = mHeightShowLayout + mLayoutButton.getHeight();
            }
        });
        mLayoutVoteContent.setOnTouchListener(this);
        mScrollView.setOnScrollChangedListener(
                new ScrollView.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged( int l, final int t, int oldl, int oldt) {
                        if (t > mHeightShowLayout) {
                            if (mLayoutButtonBottom.getVisibility() == View.GONE) {
                                mLayoutButtonBottom.setVisibility(View.VISIBLE);
                                AnimUtils.slideUp(ActivityListingDetailCbs.this, mLayoutButtonBottom);
                            }
                        } else {
                            if (mLayoutButtonBottom.getVisibility() == View.VISIBLE && !mInAnimation) {
                                Animation animation = AnimUtils.slideDownListener(ActivityListingDetailCbs.this, mLayoutButtonBottom);
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
                        handleScroll( t);
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
        new PerformanceBackGround().execute();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    public void likeListing() {
        mLayoutVoteUp.setVisibility(View.VISIBLE);
        mLayoutVoteDown.setVisibility(View.GONE);
        AnimUtils.moveX(mLayoutVoteContent, 0);
    }

    public void disLikeListing() {
        mLayoutVoteUp.setVisibility(View.GONE);
        mLayoutVoteDown.setVisibility(View.VISIBLE);
        AnimUtils.moveX(mLayoutVoteContent, 0);
    }

    public void changeTab() {
        if (mOldPage == 1) {
            AnimUtils.tabDisSelectionListingDetail(this, mLayoutDetail, mTvDetail);
        }
        if (mOldPage == 2) {
            AnimUtils.tabDisSelectionListingDetail(this, mLayoutDescription, mTvDescription);
        }
        if (mOldPage == 3) {
            AnimUtils.tabDisSelectionListingDetail(this, mLayoutVideo, mTvVideo);
        }
        if (mOldPage == 4) {
            AnimUtils.tabDisSelectionListingDetail(this, mLayoutReview, mTvReview);
        }
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
    public void onEventMap(String event, MotionEvent e) {

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

    private class PerformanceBackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            ImagePagerAdapter adapter = new ImagePagerAdapter(getSupportFragmentManager(), mArr.size());
            mViewPager.setAdapter(adapter);
            mViewPager.setOffscreenPageLimit(mArr.size());
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
}