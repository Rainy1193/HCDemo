package com.homecaravan.android.consumer.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.activity.BookSingleActivity;
import com.homecaravan.android.consumer.adapter.AstDaySelectAdapter;
import com.homecaravan.android.consumer.adapter.TimeBookSingleAdapter;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.fastadapter.WeeklyItem;
import com.homecaravan.android.consumer.listener.IBookSingleListener;
import com.homecaravan.android.consumer.model.AstDay;
import com.homecaravan.android.consumer.model.DayOfWeekly;
import com.homecaravan.android.consumer.model.TimeBookSingle;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.Weekly;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.consumer.widget.AstDialog;
import com.homecaravan.android.models.DaySlot;
import com.homecaravan.android.models.ListingDetailData;
import com.homecaravan.android.models.TimeSlot;
import com.homecaravan.android.ui.CustomLayoutManager;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

public class FragmentSelectTime extends BaseFragment implements TimeBookSingleAdapter.PickTimeBookSingleListener, WeeklyItem.PickTimeAst, AstDialog.AstListener {
    private TimeBookSingleAdapter mAdapter;
    private Calendar mCalendar;
    private ArrayList<TimeBookSingle> mArrTime = new ArrayList<>();
    private IBookSingleListener mListener;
    private DateFormat mDateFormat10 = new SimpleDateFormat("EEEE, MMM dd", Locale.US);
    private DateFormat mDateFormat11 = new SimpleDateFormat("hh:mm a", Locale.US);
    private DateFormat mDateFormat12 = new SimpleDateFormat("hh:mm", Locale.US);
    private int mCurrentDate = -1;
    private int mOldDate = -1;
    private AstDaySelectAdapter mAstDayAdapter;
    private FastItemAdapter<WeeklyItem> itemAdapter;
    private Calendar mMonth = Calendar.getInstance();
    private Calendar mPreviousMonth;
    private DateFormat mDateFormat = new SimpleDateFormat("EEE", Locale.US);
    private DateFormat mDateFormat1 = new SimpleDateFormat("d", Locale.US);
    private DateFormat mDateFormat3 = new SimpleDateFormat("MMM d", Locale.US);
    private DateFormat mDateFormat4 = new SimpleDateFormat("yyyy", Locale.US);
    private DateFormat mDateFormat5 = new SimpleDateFormat("MM", Locale.US);
    private DateFormat mDateFormat6 = new SimpleDateFormat("h:mm:a", Locale.US);
    private DateFormat mDateFormat7 = new SimpleDateFormat("HH:mm:ss", Locale.US);
    private DateFormat mDateFormat8 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private ArrayList<String> mArrHour = new ArrayList<>();
    private ArrayList<String> mArrTimeFull = new ArrayList<>();
    private ArrayList<String> mArrDay = new ArrayList<>();
    private Calendar mStart;
    private Calendar mEnd;
    private ArrayList<String> mArrTimeAst = new ArrayList<>();
    private ArrayList<AstDay> mArrTimeAstFull = new ArrayList<>();
    private boolean mShowAst;
    @Bind(R.id.scrollView)
    ScrollView mScrollView;
    @Bind(R.id.layoutBookNormal)
    LinearLayout mLayoutBookNormal;
    @Bind(R.id.layoutBookAst)
    LinearLayout mLayoutBookAst;
    @Bind(R.id.rvTime)
    RecyclerView mRvTime;
    @Bind(R.id.tvTime)
    TextView mTvTime;
    @Bind(R.id.ivListing)
    RoundedImageView mIvListing;
    @Bind(R.id.tvAddress1)
    TextView mTvAddress1;
    @Bind(R.id.tvAddress2)
    TextView mTvAddress2;

    @Bind(R.id.tvDay)
    TextView mTvDay;
    @Bind(R.id.tvSun)
    TextView mTvSun;
    @Bind(R.id.tvSun1)
    TextView mTvSun1;
    @Bind(R.id.tvMon)
    TextView mTvMon;
    @Bind(R.id.tvMon1)
    TextView mTvMon1;
    @Bind(R.id.tvTue)
    TextView mTvTue;
    @Bind(R.id.tvTue1)
    TextView mTvTue1;
    @Bind(R.id.tvWed)
    TextView mTvWed;
    @Bind(R.id.tvWed1)
    TextView mTvWed1;
    @Bind(R.id.tvThu)
    TextView mTvThu;
    @Bind(R.id.tvThu1)
    TextView mTvThu1;
    @Bind(R.id.tvFri)
    TextView mTvFri;
    @Bind(R.id.tvFri1)
    TextView mTvFri1;
    @Bind(R.id.tvSat)
    TextView mTvSat;
    @Bind(R.id.tvSat1)
    TextView mTvSat1;
    @Bind(R.id.rvCalendar)
    RecyclerView mRvCalendar;
    @Bind(R.id.rvTimeSelect)
    RecyclerView mRvTimeSelect;

    public void setListener(IBookSingleListener mListener) {
        this.mListener = mListener;
    }

    public boolean isShowAst() {
        return mShowAst;
    }

    public ArrayList<AstDay> getArrTimeAstFull() {
        return mArrTimeAstFull;
    }

    public ArrayList<TimeBookSingle> getArrTime() {
        return mArrTime;
    }

    @OnClick(R.id.layoutTime)
    public void backPickDate() {
        mListener.backDate();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new TimeBookSingleAdapter(mArrTime, getActivity(), this);
        mRvTime.setAdapter(mAdapter);
        mRvTime.setLayoutManager(new CustomLayoutManager(getActivity(), 4));
        mRvTime.setHasFixedSize(true);
        mRvTime.setItemAnimator(new ScaleInAnimator());
        getAllDay();

    }

    public void setDate(String date) {
        mArrTime.clear();
        mAdapter.notifyDataSetChanged();
        mCalendar = Utils.createDateFromString1(date);
        mTvTime.setText(mDateFormat10.format(mCalendar.getTime()));
        ArrayList<DaySlot> daySlots = BookSingleActivity.sListingData.getListingDetailDataSetting().getDaySlots();
        for (int i = 0; i < daySlots.size(); i++) {
            if (daySlots.get(i).getId().equalsIgnoreCase(date)) {
                ArrayList<TimeSlot> timeSlots = daySlots.get(i).getArrTimeSlot();
                for (int j = 0; j < timeSlots.size(); j++) {
                    TimeBookSingle timeBookSingle = new TimeBookSingle();
                    timeBookSingle.setTime(mDateFormat11.format(Utils.createDateFromString(timeSlots.get(j).getDateTimeFrom())));
                    timeBookSingle.setStatus(timeSlots.get(j).getStatus());
                    timeBookSingle.setFullTimeBookStart(timeSlots.get(j).getDateTimeFrom());
                    timeBookSingle.setFullTimeBookEnd(timeSlots.get(j).getDateTimeTo());
                    timeBookSingle.setTimeBookStart(mDateFormat12.format(Utils.createDateFromString(timeSlots.get(j).getDateTimeFrom())));
                    timeBookSingle.setTimeBookEnd(mDateFormat12.format(Utils.createDateFromString(timeSlots.get(j).getDateTimeTo())));
                    mArrTime.add(timeBookSingle);
                }
            }
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_select_time;
    }

    @Override
    public void pickTime(int position, boolean b) {
        if (b) {
            mOldDate = mCurrentDate;
            mCurrentDate = position;
            if (mOldDate != -1) {
                mArrTime.get(mOldDate).setSelected(false);
                mAdapter.notifyItemChanged(mOldDate);
            }
            if (mCurrentDate != -1) {
                mArrTime.get(mCurrentDate).setSelected(true);
                mAdapter.notifyItemChanged(mCurrentDate);
            }
            mListener.pickTime(mArrTime.get(mCurrentDate).getTime(), mArrTime.get(mCurrentDate).getTimeBookStart(), mArrTime.get(mCurrentDate).getTimeBookEnd(), mArrTime.get(mCurrentDate).getFullTimeBookStart(), mArrTime.get(mCurrentDate).getFullTimeBookEnd());

        } else {
            mArrTime.get(position).setSelected(false);
            mOldDate = -1;
            mCurrentDate = -1;
            mAdapter.notifyItemChanged(position);
        }
    }

    public void updateData() {
        Glide.with(this).load(BookSingleActivity.sListingData.getListingDetailDataListing().getImgName())
                .asBitmap().placeholder(R.drawable.ic_placeholder_listing_consumer).fitCenter().into(mIvListing);
        mTvAddress1.setText(BookSingleActivity.sListingData.getListingDetailDataListing().getLkey());
        mTvAddress2.setText(BookSingleActivity.sListingData.getListingDetailDataListing().getCity() + ", " +
                BookSingleActivity.sListingData.getListingDetailDataListing().getState() + " " +
                BookSingleActivity.sListingData.getListingDetailDataListing().getZip());
    }


    public void showAst() {
        mShowAst = true;
        mLayoutBookNormal.setVisibility(View.GONE);
        mLayoutBookAst.setVisibility(View.VISIBLE);
    }

    public void hideAst() {
        mShowAst = false;
        mLayoutBookNormal.setVisibility(View.VISIBLE);
        mLayoutBookAst.setVisibility(View.GONE);
        //clearAst();
    }

    public void clearAst() {
        for (int i = 0; i < mArrTimeFull.size(); i++) {
            itemAdapter.getAdapterItem(i).getWeekly().getSun().setTimeBookStart(mArrDay.get(0) + " " + mArrTimeFull.get(i));
            itemAdapter.getAdapterItem(i).getWeekly().getSun().setStatus("unavailable");
            itemAdapter.getAdapterItem(i).getWeekly().getSun().setSelected(false);
            itemAdapter.getAdapterItem(i).getWeekly().getMon().setTimeBookStart(mArrDay.get(1) + " " + mArrTimeFull.get(i));
            itemAdapter.getAdapterItem(i).getWeekly().getMon().setStatus("unavailable");
            itemAdapter.getAdapterItem(i).getWeekly().getMon().setSelected(false);
            itemAdapter.getAdapterItem(i).getWeekly().getTue().setTimeBookStart(mArrDay.get(2) + " " + mArrTimeFull.get(i));
            itemAdapter.getAdapterItem(i).getWeekly().getTue().setStatus("unavailable");
            itemAdapter.getAdapterItem(i).getWeekly().getTue().setSelected(false);
            itemAdapter.getAdapterItem(i).getWeekly().getWed().setTimeBookStart(mArrDay.get(3) + " " + mArrTimeFull.get(i));
            itemAdapter.getAdapterItem(i).getWeekly().getWed().setStatus("unavailable");
            itemAdapter.getAdapterItem(i).getWeekly().getWed().setSelected(false);
            itemAdapter.getAdapterItem(i).getWeekly().getThu().setTimeBookStart(mArrDay.get(4) + " " + mArrTimeFull.get(i));
            itemAdapter.getAdapterItem(i).getWeekly().getThu().setStatus("unavailable");
            itemAdapter.getAdapterItem(i).getWeekly().getThu().setSelected(false);
            itemAdapter.getAdapterItem(i).getWeekly().getFri().setTimeBookStart(mArrDay.get(5) + " " + mArrTimeFull.get(i));
            itemAdapter.getAdapterItem(i).getWeekly().getFri().setStatus("unavailable");
            itemAdapter.getAdapterItem(i).getWeekly().getFri().setSelected(false);
            itemAdapter.getAdapterItem(i).getWeekly().getSat().setTimeBookStart(mArrDay.get(6) + " " + mArrTimeFull.get(i));
            itemAdapter.getAdapterItem(i).getWeekly().getSat().setStatus("unavailable");
            itemAdapter.getAdapterItem(i).getWeekly().getSat().setSelected(false);
        }
        mArrTimeAstFull.clear();
        mRvTimeSelect.getAdapter().notifyDataSetChanged();
        mArrTimeAst.clear();
    }

    @OnClick(R.id.ivNext)
    public void next() {
        mArrDay.clear();
        for (int i = 0; i <= 6; i++) {
            if (i == 0) {
                mStart = (Calendar) mPreviousMonth.clone();
                mTvSun.setText(mDateFormat.format(mPreviousMonth.getTime()));
                mTvSun1.setText(mDateFormat1.format(mPreviousMonth.getTime()));
            }
            if (i == 1) {
                mTvMon.setText(mDateFormat.format(mPreviousMonth.getTime()));
                mTvMon1.setText(mDateFormat1.format(mPreviousMonth.getTime()));
            }
            if (i == 2) {
                mTvTue.setText(mDateFormat.format(mPreviousMonth.getTime()));
                mTvTue1.setText(mDateFormat1.format(mPreviousMonth.getTime()));
            }
            if (i == 3) {
                mTvWed.setText(mDateFormat.format(mPreviousMonth.getTime()));
                mTvWed1.setText(mDateFormat1.format(mPreviousMonth.getTime()));
            }
            if (i == 4) {
                mTvThu.setText(mDateFormat.format(mPreviousMonth.getTime()));
                mTvThu1.setText(mDateFormat1.format(mPreviousMonth.getTime()));
            }
            if (i == 5) {
                mTvFri.setText(mDateFormat.format(mPreviousMonth.getTime()));
                mTvFri1.setText(mDateFormat1.format(mPreviousMonth.getTime()));
            }
            if (i == 6) {
                mEnd = (Calendar) mPreviousMonth.clone();
                mTvSat.setText(mDateFormat.format(mPreviousMonth.getTime()));
                mTvSat1.setText(mDateFormat1.format(mPreviousMonth.getTime()));
            }
            mArrDay.add(mDateFormat8.format(mPreviousMonth.getTime()));
            mPreviousMonth.add(Calendar.DATE, 1);
        }
        setDistanceDay();
        updateListAstNext();
        updateListAst();
    }

    @OnClick(R.id.ivPrevious)
    public void previous() {
        mArrDay.clear();
        mPreviousMonth.add(Calendar.DATE, -8);
        for (int i = 0; i <= 6; i++) {
            if (i == 6) {
                mStart = (Calendar) mPreviousMonth.clone();
                mTvSun.setText(mDateFormat.format(mPreviousMonth.getTime()));
                mTvSun1.setText(mDateFormat1.format(mPreviousMonth.getTime()));
            }
            if (i == 5) {
                mTvMon.setText(mDateFormat.format(mPreviousMonth.getTime()));
                mTvMon1.setText(mDateFormat1.format(mPreviousMonth.getTime()));
            }
            if (i == 4) {
                mTvTue.setText(mDateFormat.format(mPreviousMonth.getTime()));
                mTvTue1.setText(mDateFormat1.format(mPreviousMonth.getTime()));
            }
            if (i == 3) {
                mTvWed.setText(mDateFormat.format(mPreviousMonth.getTime()));
                mTvWed1.setText(mDateFormat1.format(mPreviousMonth.getTime()));
            }
            if (i == 2) {
                mTvThu.setText(mDateFormat.format(mPreviousMonth.getTime()));
                mTvThu1.setText(mDateFormat1.format(mPreviousMonth.getTime()));
            }
            if (i == 1) {
                mTvFri.setText(mDateFormat.format(mPreviousMonth.getTime()));
                mTvFri1.setText(mDateFormat1.format(mPreviousMonth.getTime()));
            }
            if (i == 0) {
                mEnd = (Calendar) mPreviousMonth.clone();
                mTvSat.setText(mDateFormat.format(mPreviousMonth.getTime()));
                mTvSat1.setText(mDateFormat1.format(mPreviousMonth.getTime()));
            }
            mArrDay.add(0, mDateFormat8.format(mPreviousMonth.getTime()));
            mPreviousMonth.add(Calendar.DATE, -1);
        }
        mPreviousMonth.add(Calendar.DATE, 8);
        setDistanceDay();
        updateListAstNext();
        updateListAst();
    }

    public void scrollTop() {
        mScrollView.post(new Runnable() {
            @Override
            public void run() {
                mScrollView.scrollTo(0, 0);
            }
        });
    }

    public void getAllDay() {
        mMonth.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DATE));
        mPreviousMonth = (Calendar) mMonth.clone();
        int firstDay = mMonth.get(Calendar.DAY_OF_WEEK);
        mPreviousMonth.add(Calendar.DATE, -(firstDay - 1));
        for (int i = 0; i <= 6; i++) {
            if (i == 0) {

                mStart = (Calendar) mPreviousMonth.clone();
                mTvSun.setText(mDateFormat.format(mPreviousMonth.getTime()));
                mTvSun1.setText(mDateFormat1.format(mPreviousMonth.getTime()));
            }
            if (i == 1) {
                mTvMon.setText(mDateFormat.format(mPreviousMonth.getTime()));
                mTvMon1.setText(mDateFormat1.format(mPreviousMonth.getTime()));
            }
            if (i == 2) {
                mTvTue.setText(mDateFormat.format(mPreviousMonth.getTime()));
                mTvTue1.setText(mDateFormat1.format(mPreviousMonth.getTime()));
            }
            if (i == 3) {
                mTvWed.setText(mDateFormat.format(mPreviousMonth.getTime()));
                mTvWed1.setText(mDateFormat1.format(mPreviousMonth.getTime()));
            }
            if (i == 4) {
                mTvThu.setText(mDateFormat.format(mPreviousMonth.getTime()));
                mTvThu1.setText(mDateFormat1.format(mPreviousMonth.getTime()));
            }
            if (i == 5) {
                mTvFri.setText(mDateFormat.format(mPreviousMonth.getTime()));
                mTvFri1.setText(mDateFormat1.format(mPreviousMonth.getTime()));
            }
            if (i == 6) {
                mEnd = (Calendar) mPreviousMonth.clone();
                mTvSat.setText(mDateFormat.format(mPreviousMonth.getTime()));
                mTvSat1.setText(mDateFormat1.format(mPreviousMonth.getTime()));
            }
            mArrDay.add(mDateFormat8.format(mPreviousMonth.getTime()));
            mPreviousMonth.add(Calendar.DATE, 1);
        }
        setDistanceDay();
    }

    public void setDistanceDay() {
        if (mDateFormat4.format(mStart.getTime()).equalsIgnoreCase(mDateFormat4.format(mEnd.getTime()))) {
            if (mDateFormat5.format(mStart.getTime()).equalsIgnoreCase(mDateFormat5.format(mEnd.getTime()))) {
                mTvDay.setText(mDateFormat3.format(mStart.getTime()) + " - " + mDateFormat1.format(mEnd.getTime()) + ", " + mDateFormat4.format(mStart.getTime()));
            } else {
                mTvDay.setText(mDateFormat3.format(mStart.getTime()) + " - " + mDateFormat3.format(mEnd.getTime()) + ", " + mDateFormat4.format(mStart.getTime()));
            }
        } else {
            mTvDay.setText(mDateFormat3.format(mStart.getTime()) + "," + mDateFormat4.format(mStart.getTime()) + " - " + mDateFormat3.format(mEnd.getTime()) + ", " + mDateFormat4.format(mEnd.getTime()));
        }
    }

    public void initAst() {
        mAstDayAdapter = new AstDaySelectAdapter(mArrTimeAstFull, getContext());
        mRvTimeSelect.setItemAnimator(null);
        mRvTimeSelect.setAdapter(mAstDayAdapter);
        mRvTimeSelect.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemAdapter = new FastItemAdapter<>();
        mRvCalendar.setAdapter(itemAdapter);
        mRvCalendar.setItemAnimator(null);
        mRvCalendar.setLayoutManager(new LinearLayoutManager(getContext()));
        ListingDetailData listingDetailData = BookSingleActivity.sListingData;
        int duration = Integer.parseInt(listingDetailData.getListingDetailDataListing().getSetting().getAppointmentDuration().getDate().substring(14, 16));
        Calendar calendar = Utils.createDateFromStringAst(listingDetailData.getListingDetailDataListing().getSetting().getSchedulerStartTime().getDate());
        Calendar calendar1 = Utils.createDateFromStringAst(listingDetailData.getListingDetailDataListing().getSetting().getSchedulerEndTime().getDate());
        while (!mDateFormat7.format(calendar.getTime()).equalsIgnoreCase(mDateFormat7.format(calendar1.getTime()))) {
            mArrHour.add(mDateFormat6.format(calendar.getTime()));
            mArrTimeFull.add(mDateFormat7.format(calendar.getTime()));
            calendar.add(Calendar.MINUTE, duration);
        }
//        calendar.add(Calendar.MINUTE, duration);
//        mArrHour.add(mDateFormat6.format(calendar.getTime()));
//        mArrTimeFull.add(mDateFormat7.format(calendar.getTime()));
        initListAst();
        updateListAst();
    }

    public void initListAst() {
        itemAdapter.clear();
        for (int i = 0; i < mArrTimeFull.size(); i++) {
            Weekly weekly = new Weekly();
            DayOfWeekly sun = new DayOfWeekly();
            sun.setTimeBookStart(mArrDay.get(0) + " " + mArrTimeFull.get(i));
            sun.setStatus("unavailable");
            weekly.setSun(sun);

            DayOfWeekly mon = new DayOfWeekly();
            mon.setTimeBookStart(mArrDay.get(1) + " " + mArrTimeFull.get(i));
            mon.setStatus("unavailable");
            weekly.setMon(mon);

            DayOfWeekly tue = new DayOfWeekly();
            tue.setTimeBookStart(mArrDay.get(2) + " " + mArrTimeFull.get(i));
            tue.setStatus("unavailable");
            weekly.setTue(tue);

            DayOfWeekly wed = new DayOfWeekly();
            wed.setTimeBookStart(mArrDay.get(3) + " " + mArrTimeFull.get(i));
            wed.setStatus("unavailable");
            weekly.setWed(wed);

            DayOfWeekly thu = new DayOfWeekly();
            thu.setTimeBookStart(mArrDay.get(4) + " " + mArrTimeFull.get(i));
            thu.setStatus("unavailable");
            weekly.setThu(thu);

            DayOfWeekly fri = new DayOfWeekly();
            fri.setTimeBookStart(mArrDay.get(5) + " " + mArrTimeFull.get(i));
            fri.setStatus("unavailable");
            weekly.setFri(fri);

            DayOfWeekly sat = new DayOfWeekly();
            sat.setTimeBookStart(mArrDay.get(6) + " " + mArrTimeFull.get(i));
            sat.setStatus("unavailable");
            weekly.setSat(sat);

            weekly.setHour(mArrHour.get(i));
            WeeklyItem weeklyItem = new WeeklyItem();
            weeklyItem.setWeekly(weekly);
            weeklyItem.setContext(getActivity());
            weeklyItem.setPickTime(this);
            weeklyItem.setPosition(i);
            itemAdapter.add(weeklyItem);
        }
    }

    public void updateListAstNext() {
        for (int i = 0; i < mArrTimeFull.size(); i++) {
            itemAdapter.getAdapterItem(i).getWeekly().getSun().setTimeBookStart(mArrDay.get(0) + " " + mArrTimeFull.get(i));
            itemAdapter.getAdapterItem(i).getWeekly().getSun().setStatus("unavailable");
            itemAdapter.getAdapterItem(i).getWeekly().getSun().setSelected(false);
            itemAdapter.getAdapterItem(i).getWeekly().getMon().setTimeBookStart(mArrDay.get(1) + " " + mArrTimeFull.get(i));
            itemAdapter.getAdapterItem(i).getWeekly().getMon().setStatus("unavailable");
            itemAdapter.getAdapterItem(i).getWeekly().getMon().setSelected(false);
            itemAdapter.getAdapterItem(i).getWeekly().getTue().setTimeBookStart(mArrDay.get(2) + " " + mArrTimeFull.get(i));
            itemAdapter.getAdapterItem(i).getWeekly().getTue().setStatus("unavailable");
            itemAdapter.getAdapterItem(i).getWeekly().getTue().setSelected(false);
            itemAdapter.getAdapterItem(i).getWeekly().getWed().setTimeBookStart(mArrDay.get(3) + " " + mArrTimeFull.get(i));
            itemAdapter.getAdapterItem(i).getWeekly().getWed().setStatus("unavailable");
            itemAdapter.getAdapterItem(i).getWeekly().getWed().setSelected(false);
            itemAdapter.getAdapterItem(i).getWeekly().getThu().setTimeBookStart(mArrDay.get(4) + " " + mArrTimeFull.get(i));
            itemAdapter.getAdapterItem(i).getWeekly().getThu().setStatus("unavailable");
            itemAdapter.getAdapterItem(i).getWeekly().getThu().setSelected(false);
            itemAdapter.getAdapterItem(i).getWeekly().getFri().setTimeBookStart(mArrDay.get(5) + " " + mArrTimeFull.get(i));
            itemAdapter.getAdapterItem(i).getWeekly().getFri().setStatus("unavailable");
            itemAdapter.getAdapterItem(i).getWeekly().getFri().setSelected(false);
            itemAdapter.getAdapterItem(i).getWeekly().getSat().setTimeBookStart(mArrDay.get(6) + " " + mArrTimeFull.get(i));
            itemAdapter.getAdapterItem(i).getWeekly().getSat().setStatus("unavailable");
            itemAdapter.getAdapterItem(i).getWeekly().getSat().setSelected(false);
            for (int j = 0; j < mArrTimeAst.size(); j++) {
                if (mArrTimeAst.get(j).equalsIgnoreCase(mArrDay.get(0) + " " + mArrTimeFull.get(i))) {
                    itemAdapter.getAdapterItem(i).getWeekly().getSun().setSelected(true);
                }
                if (mArrTimeAst.get(j).equalsIgnoreCase(mArrDay.get(1) + " " + mArrTimeFull.get(i))) {
                    itemAdapter.getAdapterItem(i).getWeekly().getMon().setSelected(true);
                }
                if (mArrTimeAst.get(j).equalsIgnoreCase(mArrDay.get(2) + " " + mArrTimeFull.get(i))) {
                    itemAdapter.getAdapterItem(i).getWeekly().getTue().setSelected(true);
                }
                if (mArrTimeAst.get(j).equalsIgnoreCase(mArrDay.get(3) + " " + mArrTimeFull.get(i))) {
                    itemAdapter.getAdapterItem(i).getWeekly().getWed().setSelected(true);
                }
                if (mArrTimeAst.get(j).equalsIgnoreCase(mArrDay.get(4) + " " + mArrTimeFull.get(i))) {
                    itemAdapter.getAdapterItem(i).getWeekly().getThu().setSelected(true);
                }
                if (mArrTimeAst.get(j).equalsIgnoreCase(mArrDay.get(5) + " " + mArrTimeFull.get(i))) {
                    itemAdapter.getAdapterItem(i).getWeekly().getFri().setSelected(true);
                }
                if (mArrTimeAst.get(j).equalsIgnoreCase(mArrDay.get(6) + " " + mArrTimeFull.get(i))) {
                    itemAdapter.getAdapterItem(i).getWeekly().getSat().setSelected(true);
                }
            }
        }
    }

    public void updateListAst() {
        Calendar calendar = Calendar.getInstance();
        ArrayList<DaySlot> daySlots = BookSingleActivity.sListingData.getListingDetailDataSetting().getDaySlots();
        for (int i = 0; i < itemAdapter.getItemCount(); i++) {
            for (int j = 0; j < daySlots.size(); j++) {
                ArrayList<TimeSlot> timeSlots = daySlots.get(j).getArrTimeSlot();
                for (int k = 0; k < timeSlots.size(); k++) {
                    Weekly weekly = itemAdapter.getAdapterItem(i).getWeekly();
                    if (weekly.getSun().getTimeBookStart().equalsIgnoreCase(timeSlots.get(k).getDateTimeFrom())) {
                        if (calendar.getTime().getTime() > Utils.createDateFromStringAst(timeSlots.get(k).getDateTimeFrom()).getTime().getTime()) {
                            weekly.getSun().setStatus("past");
                            Log.e("Cc", "Cc");
                        } else {
                            weekly.getSun().setStatus(timeSlots.get(k).getStatus());
                        }
                    }
                    if (weekly.getMon().getTimeBookStart().equalsIgnoreCase(timeSlots.get(k).getDateTimeFrom())) {
                        if (calendar.getTime().getTime() > Utils.createDateFromStringAst(timeSlots.get(k).getDateTimeFrom()).getTime().getTime()) {
                            weekly.getSun().setStatus("past");
                            Log.e("Cc", "Cc");
                        } else {
                            weekly.getMon().setStatus(timeSlots.get(k).getStatus());
                        }
                    }
                    if (weekly.getTue().getTimeBookStart().equalsIgnoreCase(timeSlots.get(k).getDateTimeFrom())) {
                        if (calendar.getTime().getTime() > Utils.createDateFromStringAst(timeSlots.get(k).getDateTimeFrom()).getTime().getTime()) {
                            weekly.getSun().setStatus("past");
                            Log.e("Cc", "Cc");
                        } else {
                            weekly.getTue().setStatus(timeSlots.get(k).getStatus());
                        }
                    }
                    if (weekly.getWed().getTimeBookStart().equalsIgnoreCase(timeSlots.get(k).getDateTimeFrom())) {
                        if (calendar.getTime().getTime() > Utils.createDateFromStringAst(timeSlots.get(k).getDateTimeFrom()).getTime().getTime()) {
                            weekly.getSun().setStatus("past");
                            Log.e("Cc", "Cc");
                        } else {
                            weekly.getWed().setStatus(timeSlots.get(k).getStatus());
                        }
                    }
                    if (weekly.getThu().getTimeBookStart().equalsIgnoreCase(timeSlots.get(k).getDateTimeFrom())) {
                        if (calendar.getTime().getTime() > Utils.createDateFromStringAst(timeSlots.get(k).getDateTimeFrom()).getTime().getTime()) {
                            weekly.getSun().setStatus("past");
                            Log.e("Cc", "Cc");
                        } else {
                            weekly.getThu().setStatus(timeSlots.get(k).getStatus());
                        }
                    }
                    if (weekly.getFri().getTimeBookStart().equalsIgnoreCase(timeSlots.get(k).getDateTimeFrom())) {
                        if (calendar.getTime().getTime() > Utils.createDateFromStringAst(timeSlots.get(k).getDateTimeFrom()).getTime().getTime()) {
                            weekly.getSun().setStatus("past");
                            Log.e("Cc", "Cc");
                        } else {
                            weekly.getFri().setStatus(timeSlots.get(k).getStatus());
                        }
                    }
                    if (weekly.getSat().getTimeBookStart().equalsIgnoreCase(timeSlots.get(k).getDateTimeFrom())) {
                        if (calendar.getTime().getTime() > Utils.createDateFromStringAst(timeSlots.get(k).getDateTimeFrom()).getTime().getTime()) {
                            weekly.getSun().setStatus("past");
                            Log.e("Cc", "Cc");
                        } else {
                            weekly.getSat().setStatus(timeSlots.get(k).getStatus());
                        }
                    }
                }
            }
        }
        mRvCalendar.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void pickTime(String time, boolean b, int position, String status) {
        if (!status.equalsIgnoreCase("unavailable") && !status.equalsIgnoreCase("past")) {
            AstDialog.getInstance(time).setListener(this).show(getChildFragmentManager(), "AstDialog");
        } else {
            Calendar calendar = Utils.createDateFromStringAst(time);
            Calendar currentCalendar = Calendar.getInstance();
            if (status.equalsIgnoreCase("past")) {
                if (calendar.getTime().getTime() < currentCalendar.getTime().getTime()) {
                    showSnackBar(getView(), TypeDialog.WARNING, "Please pickup a timeslot in the future", "pickTime");
                }
            } else {
                if (b) {
                    mArrTimeAst.add(time);
                } else {
                    mArrTimeAst.remove(time);
                }
                addAstDay(time, b);
                mRvTimeSelect.getAdapter().notifyDataSetChanged();
                itemAdapter.notifyItemChanged(position);
            }
        }
    }

    public void addAstDay(String time, boolean b) {
        if (mArrTimeAstFull.size() == 0) {
            ArrayList<String> arrTime = new ArrayList<>();
            arrTime.add(time);
            mArrTimeAstFull.add(new AstDay(time, arrTime));
            return;
        }
        int count = 0;
        for (int i = 0; i < mArrTimeAstFull.size(); i++) {
            if (mArrTimeAstFull.get(i).getDay().substring(0, 10).equalsIgnoreCase(time.substring(0, 10))) {
                if (b) {
                    mArrTimeAstFull.get(i).getTime().add(time);
                } else {
                    mArrTimeAstFull.get(i).getTime().remove(time);
                    if (mArrTimeAstFull.get(i).getTime().size() == 0) {
                        mArrTimeAstFull.remove(i);
                    }
                }
                break;
            } else {
                count++;
            }
        }
        if (count == mArrTimeAstFull.size() && b) {
            ArrayList<String> arrTime = new ArrayList<>();
            arrTime.add(time);
            mArrTimeAstFull.add(new AstDay(time, arrTime));
        }
        mRvTimeSelect.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onRightClick() {
        mListener.backDate();
    }

    @Override
    public void onLeftClick() {

    }
}
