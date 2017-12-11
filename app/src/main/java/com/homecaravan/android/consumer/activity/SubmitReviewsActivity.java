package com.homecaravan.android.consumer.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.consumerdata.ConsumerListingFullStatus;
import com.homecaravan.android.consumer.consumerdata.ConsumerSingletonListing;
import com.homecaravan.android.consumer.fragment.FragmentImage;
import com.homecaravan.android.consumer.model.ImageListingConsumer;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.consumer.widget.ScrollView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class SubmitReviewsActivity extends AppCompatActivity {

    private float mHeightShowLayout;
    private ArrayList<String> mArr = new ArrayList<>();
    @Bind(R.id.scrollView)
    ScrollView mScrollView;
//    @Bind(R.id.sbPrice)
//    BubbleSeekBar mSbPrice;
    @Bind(R.id.imgInterior1)
    ImageView mImgInterior1;
    @Bind(R.id.frmInterior1)
    FrameLayout mFrmInterior1;
    @Bind(R.id.tvInterior1)
    TextView mTvInterior1;
    @Bind(R.id.imgInterior2)
    ImageView mImgInterior2;
    @Bind(R.id.frmInterior2)
    FrameLayout mFrmInterior2;
    @Bind(R.id.tvInterior2)
    TextView mTvInterior2;
    @Bind(R.id.imgInterior3)
    ImageView mImgInterior3;
    @Bind(R.id.frmInterior3)
    FrameLayout mFrmInterior3;
    @Bind(R.id.tvInterior3)
    TextView mTvInterior3;
    @Bind(R.id.imgInterior4)
    ImageView mImgInterior4;
    @Bind(R.id.frmInterior4)
    FrameLayout mFrmInterior4;
    @Bind(R.id.tvInterior4)
    TextView mTvInterior4;
    @Bind(R.id.imgInterior5)
    ImageView mImgInterior5;
    @Bind(R.id.frmInterior5)
    FrameLayout mFrmInterior5;
    @Bind(R.id.tvInterior5)
    TextView mTvInterior5;
    @Bind(R.id.imgExterior1)
    ImageView mImgExterior1;
    @Bind(R.id.frmExterior1)
    FrameLayout mFrmExterior1;
    @Bind(R.id.tvExterior1)
    TextView mTvExterior1;
    @Bind(R.id.imgExterior2)
    ImageView mImgExterior2;
    @Bind(R.id.frmExterior2)
    FrameLayout mFrmExterior2;
    @Bind(R.id.tvExterior2)
    TextView mTvExterior2;
    @Bind(R.id.imgExterior3)
    ImageView mImgExterior3;
    @Bind(R.id.frmExterior3)
    FrameLayout mFrmExterior3;
    @Bind(R.id.tvExterior3)
    TextView mTvExterior3;
    @Bind(R.id.imgExterior4)
    ImageView mImgExterior4;
    @Bind(R.id.frmExterior4)
    FrameLayout mFrmExterior4;
    @Bind(R.id.tvExterior4)
    TextView mTvExterior4;
    @Bind(R.id.imgExterior5)
    ImageView mImgExterior5;
    @Bind(R.id.frmExterior5)
    FrameLayout mFrmExterior5;
    @Bind(R.id.tvExterior5)
    TextView mTvExterior5;
    @Bind(R.id.imgLocation1)
    ImageView mImgLocation1;
    @Bind(R.id.tvLocation1)
    TextView mTvLocation1;
    @Bind(R.id.imgLocation2)
    ImageView mImgLocation2;
    @Bind(R.id.tvLocation2)
    TextView mTvLocation2;
    @Bind(R.id.imgLocation3)
    ImageView mImgLocation3;
    @Bind(R.id.tvLocation3)
    TextView mTvLocation3;
    @Bind(R.id.imgLocation4)
    ImageView mImgLocation4;
    @Bind(R.id.tvLocation4)
    TextView mTvLocation4;
    @Bind(R.id.imgLocation5)
    ImageView mImgLocation5;
    @Bind(R.id.tvLocation5)
    TextView mTvLocation5;
    @Bind(R.id.edtComment)
    EditText mEdtComment;
    @Bind(R.id.edtPrivateNote)
    EditText mEdtPrivateNote;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    @Bind(R.id.tvCountImage)
    TextView mTvCountImage;
    @Bind(R.id.layoutStatus)
    RelativeLayout mLayoutStatus;
    @Bind(R.id.layoutViewPager)
    LinearLayout mLayoutViewPager;
    @Bind(R.id.layoutSlide)
    RelativeLayout mLayoutSlide;
    @Bind(R.id.layoutContent)
    RelativeLayout mLayoutContent;
    @Bind(R.id.layoutReviewsResult)
    RelativeLayout mLayoutReviewsResult;
    @Bind(R.id.layoutButton)
    FrameLayout mLayoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_reviews);
        ButterKnife.bind(this);
        mLayoutButton.post(new Runnable() {
            @Override
            public void run() {
                mHeightShowLayout = mLayoutContent.getY();
                mHeightShowLayout = mHeightShowLayout + mLayoutButton.getHeight();
            }
        });
        mScrollView.setScrollingEnabled(true);
        //custom bubble of seekbar like this [$500K - $600K]
        //mSbPrice.customBubbleSeekBar(true);
        mScrollView.setOnScrollChangedListener(
                new ScrollView.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged(int l, final int t, int oldl, int oldt) {
                        //mSbPrice.correctOffsetWhenContainerOnScrolling();
                        mLayoutViewPager.setAlpha((1 / mHeightShowLayout) * t);
                    }
                }
        );

        showLayout();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTvCountImage.setText(String.valueOf(position + 1) + "/" + String.valueOf(mArr.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void initData() {
        mArr.clear();
        ArrayList<ConsumerListingFullStatus> justListed = ConsumerSingletonListing.getInstance().arrListing;
        ConsumerListingFullStatus listing=justListed.get(0);

        ArrayList<ImageListingConsumer> images = listing.getListing().getArrImageListing();
        for (int i = 0; i < images.size(); i++) {
            mArr.add(images.get(i).getDemoImage());
        }

        new Runnable() {
            @Override
            public void run() {
                mHeightShowLayout = mLayoutContent.getY();
            }
        };
    }

    public void showLayout() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                new PerformanceBackGround().execute();
            }
        }, 200);
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
            mViewPager.setVisibility(View.VISIBLE);
            super.onPostExecute(result);
        }
    }

    private void hideAllInterior() {
        mImgInterior1.setVisibility(View.GONE);
        mFrmInterior1.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior);
        mTvInterior1.setVisibility(View.INVISIBLE);

        mImgInterior2.setVisibility(View.GONE);
        mFrmInterior2.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior);
        mTvInterior2.setVisibility(View.INVISIBLE);

        mImgInterior3.setVisibility(View.GONE);
        mFrmInterior3.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior);
        mTvInterior3.setVisibility(View.INVISIBLE);

        mImgInterior4.setVisibility(View.GONE);
        mFrmInterior4.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior);
        mTvInterior4.setVisibility(View.INVISIBLE);

        mImgInterior5.setVisibility(View.GONE);
        mFrmInterior5.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior);
        mTvInterior5.setVisibility(View.INVISIBLE);
    }

    private void hideAllExterior() {
        mImgExterior1.setVisibility(View.GONE);
        mFrmExterior1.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior);
        mTvExterior1.setVisibility(View.INVISIBLE);

        mImgExterior2.setVisibility(View.GONE);
        mFrmExterior2.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior);
        mTvExterior2.setVisibility(View.INVISIBLE);

        mImgExterior3.setVisibility(View.GONE);
        mFrmExterior3.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior);
        mTvExterior3.setVisibility(View.INVISIBLE);

        mImgExterior4.setVisibility(View.GONE);
        mFrmExterior4.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior);
        mTvExterior4.setVisibility(View.INVISIBLE);

        mImgExterior5.setVisibility(View.GONE);
        mFrmExterior5.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior);
        mTvExterior5.setVisibility(View.INVISIBLE);
    }

    private void hideAllLocation() {
        Glide.with(this).load(R.drawable.ic_consumer_submit_reviews_location).fitCenter().into(mImgLocation1);
        mTvLocation1.setVisibility(View.INVISIBLE);

        Glide.with(this).load(R.drawable.ic_consumer_submit_reviews_location).fitCenter().into(mImgLocation2);
        mTvLocation2.setVisibility(View.INVISIBLE);

        Glide.with(this).load(R.drawable.ic_consumer_submit_reviews_location).fitCenter().into(mImgLocation3);
        mTvLocation3.setVisibility(View.INVISIBLE);

        Glide.with(this).load(R.drawable.ic_consumer_submit_reviews_location).fitCenter().into(mImgLocation4);
        mTvLocation4.setVisibility(View.INVISIBLE);

        Glide.with(this).load(R.drawable.ic_consumer_submit_reviews_location).fitCenter().into(mImgLocation5);
        mTvLocation5.setVisibility(View.INVISIBLE);
    }


    @OnClick({R.id.lnInterior1, R.id.lnInterior2, R.id.lnInterior3, R.id.lnInterior4,
            R.id.lnInterior5, R.id.lnExterior1, R.id.lnExterior2, R.id.lnExterior3,
            R.id.lnExterior4, R.id.lnExterior5, R.id.lnLocation1, R.id.lnLocation2,
            R.id.lnLocation3, R.id.lnLocation4, R.id.lnLocation5, R.id.rlSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lnInterior1:
                if (mTvInterior1.getVisibility() == View.INVISIBLE) {
                    hideAllInterior();
                    mImgInterior1.setVisibility(View.VISIBLE);
                    mFrmInterior1.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_1);
                    mTvInterior1.setVisibility(View.VISIBLE);
                } else {
                    hideAllInterior();
                }
                break;
            case R.id.lnInterior2:
                if (mTvInterior2.getVisibility() == View.INVISIBLE) {
                    hideAllInterior();
                    mImgInterior2.setVisibility(View.VISIBLE);
                    mFrmInterior2.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_2);
                    mTvInterior2.setVisibility(View.VISIBLE);

                    mImgInterior1.setVisibility(View.VISIBLE);
                    mFrmInterior1.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_1);
                } else {
                    hideAllInterior();
                }
                break;
            case R.id.lnInterior3:
                if (mTvInterior3.getVisibility() == View.INVISIBLE) {
                    hideAllInterior();
                    mImgInterior3.setVisibility(View.VISIBLE);
                    mFrmInterior3.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_3);
                    mTvInterior3.setVisibility(View.VISIBLE);

                    mImgInterior1.setVisibility(View.VISIBLE);
                    mFrmInterior1.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_1);
                    mImgInterior2.setVisibility(View.VISIBLE);
                    mFrmInterior2.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_2);
                } else {
                    hideAllInterior();
                }
                break;
            case R.id.lnInterior4:
                if (mTvInterior4.getVisibility() == View.INVISIBLE) {
                    hideAllInterior();
                    mImgInterior4.setVisibility(View.VISIBLE);
                    mFrmInterior4.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_4);
                    mTvInterior4.setVisibility(View.VISIBLE);

                    mImgInterior1.setVisibility(View.VISIBLE);
                    mFrmInterior1.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_1);
                    mImgInterior2.setVisibility(View.VISIBLE);
                    mFrmInterior2.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_2);
                    mImgInterior3.setVisibility(View.VISIBLE);
                    mFrmInterior3.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_3);
                } else {
                    hideAllInterior();
                }
                break;
            case R.id.lnInterior5:
                if (mTvInterior5.getVisibility() == View.INVISIBLE) {
                    hideAllInterior();
                    mImgInterior5.setVisibility(View.VISIBLE);
                    mFrmInterior5.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_5);
                    mTvInterior5.setVisibility(View.VISIBLE);

                    mImgInterior1.setVisibility(View.VISIBLE);
                    mFrmInterior1.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_1);
                    mImgInterior2.setVisibility(View.VISIBLE);
                    mFrmInterior2.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_2);
                    mImgInterior3.setVisibility(View.VISIBLE);
                    mFrmInterior3.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_3);
                    mImgInterior4.setVisibility(View.VISIBLE);
                    mFrmInterior4.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_4);
                } else {
                    hideAllInterior();
                }
                break;
            case R.id.lnExterior1:
                if (mTvExterior1.getVisibility() == View.INVISIBLE) {
                    hideAllExterior();
                    mImgExterior1.setVisibility(View.VISIBLE);
                    mFrmExterior1.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_1);
                    mTvExterior1.setVisibility(View.VISIBLE);
                } else {
                    hideAllExterior();
                }
                break;
            case R.id.lnExterior2:
                if (mTvExterior2.getVisibility() == View.INVISIBLE) {
                    hideAllExterior();
                    mImgExterior2.setVisibility(View.VISIBLE);
                    mFrmExterior2.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_2);
                    mTvExterior2.setVisibility(View.VISIBLE);

                    mImgExterior1.setVisibility(View.VISIBLE);
                    mFrmExterior1.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_1);
                } else {
                    hideAllExterior();
                }
                break;
            case R.id.lnExterior3:
                if (mTvExterior3.getVisibility() == View.INVISIBLE) {
                    hideAllExterior();
                    mImgExterior3.setVisibility(View.VISIBLE);
                    mFrmExterior3.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_3);
                    mTvExterior3.setVisibility(View.VISIBLE);

                    mImgExterior1.setVisibility(View.VISIBLE);
                    mFrmExterior1.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_1);
                    mImgExterior2.setVisibility(View.VISIBLE);
                    mFrmExterior2.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_2);
                } else {
                    hideAllExterior();
                }
                break;
            case R.id.lnExterior4:
                if (mTvExterior4.getVisibility() == View.INVISIBLE) {
                    hideAllExterior();
                    mImgExterior4.setVisibility(View.VISIBLE);
                    mFrmExterior4.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_4);
                    mTvExterior4.setVisibility(View.VISIBLE);

                    mImgExterior1.setVisibility(View.VISIBLE);
                    mFrmExterior1.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_1);
                    mImgExterior2.setVisibility(View.VISIBLE);
                    mFrmExterior2.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_2);
                    mImgExterior3.setVisibility(View.VISIBLE);
                    mFrmExterior3.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_3);
                } else {
                    hideAllExterior();
                }
                break;
            case R.id.lnExterior5:
                if (mTvExterior5.getVisibility() == View.INVISIBLE) {
                    hideAllExterior();
                    mImgExterior5.setVisibility(View.VISIBLE);
                    mFrmExterior5.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_5);
                    mTvExterior5.setVisibility(View.VISIBLE);

                    mImgExterior1.setVisibility(View.VISIBLE);
                    mFrmExterior1.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_1);
                    mImgExterior2.setVisibility(View.VISIBLE);
                    mFrmExterior2.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_2);
                    mImgExterior3.setVisibility(View.VISIBLE);
                    mFrmExterior3.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_3);
                    mImgExterior4.setVisibility(View.VISIBLE);
                    mFrmExterior4.setBackgroundResource(R.drawable.bg_consumer_submit_reviews_interior_4);
                } else {
                    hideAllExterior();
                }
                break;
            case R.id.lnLocation1:
                if (mTvLocation1.getVisibility() == View.INVISIBLE) {
                    hideAllLocation();
                    Glide.with(this).load(R.drawable.ic_consumer_submit_reviews_location_color).fitCenter().into(mImgLocation1);
                    mTvLocation1.setVisibility(View.VISIBLE);
                } else {
                    hideAllLocation();
                }
                break;
            case R.id.lnLocation2:
                if (mTvLocation2.getVisibility() == View.INVISIBLE) {
                    hideAllLocation();
                    Glide.with(this).load(R.drawable.ic_consumer_submit_reviews_location_color).fitCenter().into(mImgLocation2);
                    mTvLocation2.setVisibility(View.VISIBLE);

                    Glide.with(this).load(R.drawable.ic_consumer_submit_reviews_location_color).fitCenter().into(mImgLocation1);
                } else {
                    hideAllLocation();
                }
                break;
            case R.id.lnLocation3:
                if (mTvLocation3.getVisibility() == View.INVISIBLE) {
                    hideAllLocation();
                    Glide.with(this).load(R.drawable.ic_consumer_submit_reviews_location_color).fitCenter().into(mImgLocation3);
                    mTvLocation3.setVisibility(View.VISIBLE);

                    Glide.with(this).load(R.drawable.ic_consumer_submit_reviews_location_color).fitCenter().into(mImgLocation1);
                    Glide.with(this).load(R.drawable.ic_consumer_submit_reviews_location_color).fitCenter().into(mImgLocation2);
                } else {
                    hideAllLocation();
                }
                break;
            case R.id.lnLocation4:
                if (mTvLocation4.getVisibility() == View.INVISIBLE) {
                    hideAllLocation();
                    Glide.with(this).load(R.drawable.ic_consumer_submit_reviews_location_color).fitCenter().into(mImgLocation4);
                    mTvLocation4.setVisibility(View.VISIBLE);

                    Glide.with(this).load(R.drawable.ic_consumer_submit_reviews_location_color).fitCenter().into(mImgLocation1);
                    Glide.with(this).load(R.drawable.ic_consumer_submit_reviews_location_color).fitCenter().into(mImgLocation2);
                    Glide.with(this).load(R.drawable.ic_consumer_submit_reviews_location_color).fitCenter().into(mImgLocation3);
                } else {
                    hideAllLocation();
                }
                break;
            case R.id.lnLocation5:
                if (mTvLocation5.getVisibility() == View.INVISIBLE) {
                    hideAllLocation();
                    Glide.with(this).load(R.drawable.ic_consumer_submit_reviews_location_color).fitCenter().into(mImgLocation5);
                    mTvLocation5.setVisibility(View.VISIBLE);

                    Glide.with(this).load(R.drawable.ic_consumer_submit_reviews_location_color).fitCenter().into(mImgLocation1);
                    Glide.with(this).load(R.drawable.ic_consumer_submit_reviews_location_color).fitCenter().into(mImgLocation2);
                    Glide.with(this).load(R.drawable.ic_consumer_submit_reviews_location_color).fitCenter().into(mImgLocation3);
                    Glide.with(this).load(R.drawable.ic_consumer_submit_reviews_location_color).fitCenter().into(mImgLocation4);
                } else {
                    hideAllLocation();
                }
                break;
        }
    }

    @OnTextChanged(value = R.id.edtComment, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onCommentChange(Editable editable) {
        if (editable.length() != 0) {
            mEdtComment.setTextSize(14);
        } else {
            mEdtComment.setTextSize(11);
        }
    }

    @OnTextChanged(value = R.id.edtPrivateNote, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onNoteChange(Editable editable) {
        if (editable.length() != 0) {
            mEdtPrivateNote.setTextSize(14);
        } else {
            mEdtPrivateNote.setTextSize(11);
        }
    }

//    @OnTouch(R.id.sbPrice)
//    public boolean touchPrice(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mScrollView.setScrollingEnabled(false);
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                mScrollView.setScrollingEnabled(true);
//                break;
//        }
//        return false;
//    }

    @OnClick(R.id.rlSubmit)
    void onSubmitClicked() {
        mLayoutContent.setVisibility(View.GONE);
        ObjectAnimator objectAnimator = AnimUtils.animationButtonMenuWithOpenViewWithListener(MainActivityConsumer.mDrawerArrowDrawable, mLayoutReviewsResult, true);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mLayoutReviewsResult.setVisibility(View.VISIBLE);
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
