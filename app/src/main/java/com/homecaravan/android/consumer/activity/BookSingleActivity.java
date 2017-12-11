package com.homecaravan.android.consumer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.adapter.ViewPagerAdapter;
import com.homecaravan.android.consumer.base.BaseActivity;
import com.homecaravan.android.consumer.consumerbase.ConsumerUser;
import com.homecaravan.android.consumer.consumermvp.caravanmvp.BookSinglePresenter;
import com.homecaravan.android.consumer.consumermvp.caravanmvp.BookSingleView;
import com.homecaravan.android.consumer.consumermvp.caravanmvp.GetListingDetailBookPresenter;
import com.homecaravan.android.consumer.consumermvp.caravanmvp.GetListingDetailBookView;
import com.homecaravan.android.consumer.consumermvp.caravanmvp.SubmitAstPresenter;
import com.homecaravan.android.consumer.consumermvp.caravanmvp.SubmitAstView;
import com.homecaravan.android.consumer.fragment.FragmentAddNote;
import com.homecaravan.android.consumer.fragment.FragmentConfirmation;
import com.homecaravan.android.consumer.fragment.FragmentSelectDate;
import com.homecaravan.android.consumer.fragment.FragmentSelectTime;
import com.homecaravan.android.consumer.listener.IBookSingleListener;
import com.homecaravan.android.consumer.model.AstDay;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.widget.CustomViewPager;
import com.homecaravan.android.models.ListingDetailData;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class BookSingleActivity extends BaseActivity implements IBookSingleListener, GetListingDetailBookView, BookSingleView, SubmitAstView {
    public static ListingDetailData sListingData;
    private BookSinglePresenter mBookSinglePresenter;
    private SubmitAstPresenter mSubmitAstPresenter;
    private FragmentSelectDate mFragmentSelectDate;
    private FragmentSelectTime mFragmentSelectTime;
    private FragmentAddNote mFragmentAddNote;
    private FragmentConfirmation mFragmentConfirmation;
    private ViewPagerAdapter mViewPagerAdapter;
    private GetListingDetailBookPresenter mGetListingDetail;
    private String mListingId;
    private int mCurrentPage = 0;
    private String mDateBook;
    private String mTimeBook;
    private String mTimeBookStart;
    private String mTimeBookEnd;
    private String mFullTimeBookStart;
    private String mFullTimeBookEnd;
    @Bind(R.id.tvStep)
    TextView mTvStep;
    @Bind(R.id.viewPager)
    CustomViewPager mViewPager;
    @Bind(R.id.stepOne)
    View mViewOne;
    @Bind(R.id.stepTwo)
    View mViewTwo;
    @Bind(R.id.stepThree)
    View mViewThree;
    @Bind(R.id.stepFour)
    View mViewFour;
    @Bind(R.id.layoutBack)
    RelativeLayout mLayoutBack;
    @Bind(R.id.layoutNext)
    RelativeLayout mLayoutNext;
    @Bind(R.id.tvNext)
    TextView mTvNext;
    @Bind(R.id.ivNext)
    ImageView mIvNext;
    @Bind(R.id.layoutMain)
    RelativeLayout mLayoutMain;

    @OnClick(R.id.ivClose)
    public void close() {
        onBackPressed();
    }

    @OnClick(R.id.layoutBack)
    public void back() {

        if (mCurrentPage == 1) {
            mViewPager.setCurrentItem(0);
        } else if (mCurrentPage == 2) {
            mViewPager.setCurrentItem(1);
        } else {
            mViewPager.setCurrentItem(2);
            mTvNext.setText("Next");
            mIvNext.setImageResource(R.drawable.ic_next_single_caravan);
        }
    }

    @OnClick(R.id.layoutNext)
    public void next() {
        if (mCurrentPage == 0) {
            mViewPager.setCurrentItem(1);
            mFragmentSelectTime.setDate(mFragmentSelectDate.getDate());
            mFragmentSelectTime.hideAst();
        } else if (mCurrentPage == 1) {
            if (mFragmentSelectTime.isShowAst()) {
                if (mFragmentSelectTime.getArrTimeAstFull().size() == 0) {
                    showSnackBar(mLayoutMain, TypeDialog.WARNING, "Please select timeslot", "pickDay");
                } else {
                    mViewPager.setCurrentItem(2);
                }
            } else {
                if (mFragmentSelectTime.getArrTime().size() == 0) {
                    showSnackBar(mLayoutMain, TypeDialog.WARNING, "Please select timeslot", "pickDay");
                } else {
                    mViewPager.setCurrentItem(2);
                }
            }
        } else if (mCurrentPage == 2) {
            if (mFragmentSelectTime.isShowAst()) {
                mFragmentConfirmation.setDateTimeAst(mFragmentSelectTime.getArrTimeAstFull());
            }
            mViewPager.setCurrentItem(3);
            mTvNext.setText("Submit");
            mIvNext.setImageResource(R.drawable.ic_submit_caravan);
        } else {
            if (mFragmentSelectTime.isShowAst()) {
                ArrayList<AstDay> astDays = mFragmentSelectTime.getArrTimeAstFull();
                String events = "";
                for (int i = 0; i < astDays.size(); i++) {
                    String day = astDays.get(i).getDay().substring(0, 10);
                    ArrayList<String> time = astDays.get(i).getTime();
                    for (int j = 0; j < time.size(); j++) {
                        events = events + day + " " + time.get(j).substring(11) + "$";
                    }
                }
                Log.e("ccc", events.substring(0, events.length() - 1));
                mSubmitAstPresenter.submit(mListingId, events.substring(0, events.length() - 1), mFragmentAddNote.getNote(), ConsumerUser.getInstance().getData().getToken(), "email");
            } else {
                mBookSinglePresenter.bookAppointment(mListingId, mDateBook, mFullTimeBookStart.substring(11, 16), mFullTimeBookEnd.substring(11, 16), mFragmentAddNote.getNote(), "email", "1", ConsumerUser.getInstance().getData().getToken());
                Log.e("ccc", mTimeBook + mDateBook + mFragmentAddNote.getNote() + mFullTimeBookStart.substring(11, 16) + mFullTimeBookEnd.substring(11, 16));
            }
            mLayoutNext.setEnabled(false);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBookSinglePresenter = new BookSinglePresenter(this);
        mSubmitAstPresenter = new SubmitAstPresenter(this);
        mGetListingDetail = new GetListingDetailBookPresenter(this);
        mListingId = getIntent().getExtras().getString("id");
        mGetListingDetail.getListingDetail(mListingId);
    }

    public void initViewPager() {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mFragmentSelectDate = new FragmentSelectDate();
        mFragmentSelectDate.setListener(this);
        mFragmentSelectTime = new FragmentSelectTime();
        mFragmentSelectTime.setListener(this);
        mFragmentAddNote = new FragmentAddNote();
        mFragmentAddNote.setListener(this);
        mFragmentConfirmation = new FragmentConfirmation();
        mFragmentConfirmation.setListener(this);
        mViewPagerAdapter.addFragment(mFragmentSelectDate, "date");
        mViewPagerAdapter.addFragment(mFragmentSelectTime, "time");
        mViewPagerAdapter.addFragment(mFragmentAddNote, "note");
        mViewPagerAdapter.addFragment(mFragmentConfirmation, "confirmation");
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setSwipeEnabled(false);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                if (position == 0) {
                    mLayoutBack.setVisibility(View.GONE);
                    mTvStep.setText("Select a date");
                    mViewOne.setBackground(ContextCompat.getDrawable(BookSingleActivity.this, R.drawable.bg_step_intro_show));
                    mViewTwo.setBackground(ContextCompat.getDrawable(BookSingleActivity.this, R.drawable.bg_step_intro_hide));
                    mViewThree.setBackground(ContextCompat.getDrawable(BookSingleActivity.this, R.drawable.bg_step_intro_hide));
                    mViewFour.setBackground(ContextCompat.getDrawable(BookSingleActivity.this, R.drawable.bg_step_intro_hide));
                    mTvNext.setText("Next");
                    mIvNext.setImageResource(R.drawable.ic_next_single_caravan);
                    mFragmentSelectTime.scrollTop();
                }
                if (position == 1) {
                    mLayoutBack.setVisibility(View.VISIBLE);
                    mTvStep.setText("Select a time");
                    mViewOne.setBackground(ContextCompat.getDrawable(BookSingleActivity.this, R.drawable.bg_step_intro_hide));
                    mViewTwo.setBackground(ContextCompat.getDrawable(BookSingleActivity.this, R.drawable.bg_step_intro_show));
                    mViewThree.setBackground(ContextCompat.getDrawable(BookSingleActivity.this, R.drawable.bg_step_intro_hide));
                    mViewFour.setBackground(ContextCompat.getDrawable(BookSingleActivity.this, R.drawable.bg_step_intro_hide));
                    mTvNext.setText("Next");
                    mIvNext.setImageResource(R.drawable.ic_next_single_caravan);
                }
                if (position == 2) {
                    mLayoutBack.setVisibility(View.VISIBLE);
                    mTvStep.setText("Add note");
                    mViewOne.setBackground(ContextCompat.getDrawable(BookSingleActivity.this, R.drawable.bg_step_intro_hide));
                    mViewTwo.setBackground(ContextCompat.getDrawable(BookSingleActivity.this, R.drawable.bg_step_intro_hide));
                    mViewThree.setBackground(ContextCompat.getDrawable(BookSingleActivity.this, R.drawable.bg_step_intro_show));
                    mViewFour.setBackground(ContextCompat.getDrawable(BookSingleActivity.this, R.drawable.bg_step_intro_hide));
                    mTvNext.setText("Next");
                    mIvNext.setImageResource(R.drawable.ic_next_single_caravan);
                    mFragmentAddNote.clearNote();
                }
                if (position == 3) {
                    mLayoutBack.setVisibility(View.VISIBLE);
                    mTvStep.setText("Confirmation");
                    mViewOne.setBackground(ContextCompat.getDrawable(BookSingleActivity.this, R.drawable.bg_step_intro_hide));
                    mViewTwo.setBackground(ContextCompat.getDrawable(BookSingleActivity.this, R.drawable.bg_step_intro_hide));
                    mViewThree.setBackground(ContextCompat.getDrawable(BookSingleActivity.this, R.drawable.bg_step_intro_hide));
                    mViewFour.setBackground(ContextCompat.getDrawable(BookSingleActivity.this, R.drawable.bg_step_intro_show));
                    mTvNext.setText("Submit");
                    mIvNext.setImageResource(R.drawable.ic_submit_caravan);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_book_single;
    }

    @Override
    public void pickDate(String date) {
        mDateBook = date;
        mViewPager.setCurrentItem(1);
        mFragmentSelectTime.setDate(date);
        mFragmentConfirmation.setDate(date);
    }

    @Override
    public void backDate() {
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void pickTime(String time, String timeBookStart, String timeBookEnd, String fullTimeBookStart, String fullTimeBookEnd) {
        mTimeBookStart = timeBookStart;
        mTimeBookEnd = timeBookEnd;
        mFullTimeBookStart = fullTimeBookStart;
        mFullTimeBookEnd = fullTimeBookEnd;
        mTimeBook = time;
        mFragmentConfirmation.setTime(time, mTimeBookStart, mTimeBookEnd);
    }

    @Override
    public void backTime() {
        mViewPager.setCurrentItem(1);
    }

    @Override
    public void complete(String note, String confirmation, String date, String time) {

    }

    @Override
    public void showLayoutAst() {
        mFragmentSelectTime.showAst();
    }

    @Override
    public void hideAst() {
        mFragmentSelectTime.hideAst();
    }

    @Override
    public void pickTimeAst() {
        mViewPager.setCurrentItem(1);
    }

    @Override
    public void getListingDetailSuccess(ListingDetailData listingDetailData) {
        initViewPager();
        sListingData = listingDetailData;
        mFragmentSelectDate.updateData();
        mFragmentSelectTime.updateData();
        mFragmentConfirmation.updateData();
        mFragmentSelectTime.initAst();
    }

    @Override
    public void getListingDetailFail(@StringRes int message) {
        sListingData = null;
    }

    @Override
    public void getListingDetailFail(String message) {
        sListingData = null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_close_activity_left, R.anim.anim_close_activity_right);
    }

    @Override
    public void bookSuccess(JSONObject data) {
        mLayoutNext.setEnabled(true);
        showSnackBar(mLayoutMain, TypeDialog.SUCCESS, "Book success", "book");
    }

    @Override
    public void bookNotSucceed(String message) {
        mLayoutNext.setEnabled(true);
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "book");
    }

    @Override
    public void bookNotSucceedStatus(String message) {
        mLayoutNext.setEnabled(true);
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "book");
    }

    @Override
    public void bookFail(@StringRes int message) {
        mLayoutNext.setEnabled(true);
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "book");
    }

    @Override
    public void bookFail(String message) {
        mLayoutNext.setEnabled(true);
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "book");
    }

    @Override
    public void suggestionsubmitSucceed(JSONObject bookData) {
        mLayoutNext.setEnabled(true);
        showSnackBar(mLayoutMain, TypeDialog.SUCCESS, "Book success", "book");
    }

    @Override
    public void suggestionsubmitFailed(String message) {
        mLayoutNext.setEnabled(true);
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "book");
    }

    @Override
    public void suggestionsubmitFailed(@StringRes int message) {
        mLayoutNext.setEnabled(true);
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "book");
    }

    @Override
    public void suggestionsubmitCancelled() {
        mLayoutNext.setEnabled(true);
    }
}
