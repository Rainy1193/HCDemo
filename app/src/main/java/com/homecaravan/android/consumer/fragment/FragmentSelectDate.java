package com.homecaravan.android.consumer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.adapter.CalendarBookSingleAdapter;
import com.homecaravan.android.consumer.activity.BookSingleActivity;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.listener.IBookSingleListener;
import com.homecaravan.android.consumer.listener.IPickDateSingleBookListener;
import com.homecaravan.android.consumer.model.DayBookSingle;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.consumer.widget.AstDialog;
import com.homecaravan.android.models.DaySlot;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

public class FragmentSelectDate extends BaseFragment implements IPickDateSingleBookListener, AstDialog.AstListener {
    private ArrayList<DayBookSingle> mArrDay = new ArrayList<>();
    private CalendarBookSingleAdapter mCalendarAdapter;
    private String mCurrentDay;
    private String mMonthString;
    private String mMonthString1;
    private String mYearString;
    private String mThString;
    private String mDayString;
    private Calendar mMonth = Calendar.getInstance();
    private Calendar mPreviousMonth;
    private DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private ArrayList<String> mDateString = new ArrayList<>();
    private int mCurrentDate = -1;
    private int mOldDate = -1;
    private DateFormat df;
    private DateFormat df1;
    private DateFormat df2;
    private DateFormat df3;
    private DateFormat df4;
    private IBookSingleListener mListener;

    public void setListener(IBookSingleListener mListener) {
        this.mListener = mListener;
    }

    @Bind(R.id.ivListing)
    RoundedImageView mIvListing;
    @Bind(R.id.tvAddress1)
    TextView mTvAddress1;
    @Bind(R.id.tvAddress2)
    TextView mTvAddress2;
    @Bind(R.id.mTvMonthYear)
    TextView mTvMonthYear;
    @Bind(R.id.rvCalendar)
    RecyclerView mRvCalendar;

    @OnClick(R.id.layoutBookAst)
    public void showAst() {
        mListener.pickTimeAst();
        mListener.showLayoutAst();
    }

    @OnClick(R.id.layoutNext)
    public void nextMonth() {
        mDateString.clear();
        for (int i = 0; i <= 20; i++) {
            String dateItem = mDateFormat.format(mPreviousMonth.getTime());
            mPreviousMonth.add(Calendar.DATE, 1);
            mDateString.add(dateItem);
        }
        getAllDaySchedule();
        updateDaySlot();
        mCalendarAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.layoutPrevious)
    public void previousMonth() {
        mDateString.clear();
        mPreviousMonth.add(Calendar.DATE, -22);
        for (int i = 0; i <= 20; i++) {
            String dateItem = mDateFormat.format(mPreviousMonth.getTime());
            mPreviousMonth.add(Calendar.DATE, -1);
            mDateString.add(0, dateItem);
        }
        getAllDaySchedule();
        updateDaySlot();
        mCalendarAdapter.notifyDataSetChanged();
        mPreviousMonth.add(Calendar.DATE, 22);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCurrentDay = mDateFormat.format(Calendar.getInstance().getTime());
        getPropertiesDate();
        getAllDay();
        getAllDaySchedule();

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_select_date;
    }

    public void getPropertiesDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        Date date = calendar.getTime();
        df = new SimpleDateFormat("MMM", Locale.US);
        df1 = new SimpleDateFormat("MMMM", Locale.US);
        df2 = new SimpleDateFormat("yyyy", Locale.US);
        df3 = new SimpleDateFormat("EEE", Locale.US);
        df4 = new SimpleDateFormat("d", Locale.US);
        mMonthString = df.format(date);
        mMonthString1 = df1.format(date);
        mYearString = df2.format(date);
        mThString = df3.format(date);
        mDayString = df4.format(date);
        mTvMonthYear.setText(mMonthString + " " + mYearString);

    }

    public void getAllDaySchedule() {
        mArrDay.clear();
        int positionCurrentDay;
        ArrayList<String> listDate = new ArrayList<>();
        for (int i = 0; i < mDateString.size(); i++) {
            listDate.add(mDateString.get(i));
        }
        mCurrentDate = listDate.indexOf(mCurrentDay);
        positionCurrentDay = listDate.indexOf(mCurrentDay);

        int currentMonth = Integer.parseInt(mCurrentDay.substring(5, 7));
        int endDay = positionCurrentDay + 20;
        Date date = Utils.createDateFromString1(listDate.get(0)).getTime();
        mMonthString = df.format(date);
        mYearString = df2.format(date);
        mTvMonthYear.setText(mMonthString + " " + mYearString);
        for (int i = 0; i < listDate.size(); i++) {
            DayBookSingle day = new DayBookSingle();
            day.setDay(listDate.get(i).substring(8, 10));
            day.setFullDay(listDate.get(i));
            if (mCurrentDay.equalsIgnoreCase(listDate.get(i))) {
                day.setSelect(true);
            }
            mArrDay.add(day);
        }
    }

    public void getAllDay() {
        mMonth.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DATE));
        mDateString.clear();
        mPreviousMonth = (Calendar) mMonth.clone();
        int firstDay = mMonth.get(Calendar.DAY_OF_WEEK);
        mPreviousMonth.add(Calendar.DATE, -(firstDay - 1));
        for (int i = 0; i <= 20; i++) {
            String dateItem = mDateFormat.format(mPreviousMonth.getTime());
            mPreviousMonth.add(Calendar.DATE, 1);
            mDateString.add(dateItem);
        }
    }


    @Override
    public void pickDate(int position, boolean b) {
        if (b) {
            mCurrentDay = mDateString.get(position);
            mListener.pickDate(mCurrentDay);
            mListener.hideAst();
            mOldDate = mCurrentDate;
            mCurrentDate = position;
            if (mOldDate != -1) {
                mArrDay.get(mOldDate).setSelect(false);
            }
            if (mCurrentDate != -1) {
                mArrDay.get(mCurrentDate).setSelect(true);
            }
            mCalendarAdapter.notifyDataSetChanged();
        } else {
            Calendar calendar = Utils.createDateFromString1(mArrDay.get(position).getFullDay());
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
            currentCalendar.set(Calendar.MINUTE, 0);
            currentCalendar.set(Calendar.SECOND, 0);
            if (calendar.getTime().getTime() < currentCalendar.getTime().getTime()) {
                showSnackBar(getView(), TypeDialog.WARNING, "This availability is already in the past, you do not need to modify it anymore.", "pickTime");
            } else {
                AstDialog.getInstance("past").setListener(this).show(getChildFragmentManager(), "AstDialog");
            }
        }
    }

    public String getDate() {
        return mCurrentDay;
    }

    @Override
    public void pickDate(String fullDate) {

    }

    public void updateData() {
        Glide.with(this).load(BookSingleActivity.sListingData.getListingDetailDataListing().getImgName())
                .asBitmap().placeholder(R.drawable.ic_placeholder_listing_consumer).fitCenter().into(mIvListing);
        mTvAddress1.setText(BookSingleActivity.sListingData.getListingDetailDataListing().getLkey());
        mTvAddress2.setText(BookSingleActivity.sListingData.getListingDetailDataListing().getCity() + ", " +
                BookSingleActivity.sListingData.getListingDetailDataListing().getState() + " " +
                BookSingleActivity.sListingData.getListingDetailDataListing().getZip());
        if (BookSingleActivity.sListingData != null) {
            ArrayList<DaySlot> daySlots = new ArrayList<>();
            daySlots.addAll(BookSingleActivity.sListingData.getListingDetailDataSetting().getDaySlots());
            for (int i = 0; i < daySlots.size(); i++) {
                for (int j = 0; j < mDateString.size(); j++) {
                    if (daySlots.get(i).getId().equalsIgnoreCase(mDateString.get(j))) {
                        mArrDay.get(j).setStatus(daySlots.get(i).getStatus());
                    }
                }
            }
            for (int i = 0; i < mArrDay.size(); i++) {
                if (mArrDay.get(i).getStatus() == null) {
                    mArrDay.get(i).setStatus("unavailable");
                }
            }
        }
        mCalendarAdapter = new CalendarBookSingleAdapter(mArrDay, getActivity(), this);
        mRvCalendar.setAdapter(mCalendarAdapter);
        mRvCalendar.setLayoutManager(new GridLayoutManager(getActivity(), 7));
        mRvCalendar.setHasFixedSize(true);
        mRvCalendar.setItemAnimator(new ScaleInAnimator());
    }

    public void updateDaySlot() {
        if (BookSingleActivity.sListingData != null) {
            ArrayList<DaySlot> daySlots = new ArrayList<>();
            daySlots.addAll(BookSingleActivity.sListingData.getListingDetailDataSetting().getDaySlots());
            for (int i = 0; i < daySlots.size(); i++) {
                for (int j = 0; j < mDateString.size(); j++) {
                    if (daySlots.get(i).getId().equalsIgnoreCase(mDateString.get(j))) {
                        mArrDay.get(j).setStatus(daySlots.get(i).getStatus());
                    }
                }
            }
            for (int i = 0; i < mArrDay.size(); i++) {
                if (mArrDay.get(i).getStatus() == null) {
                    mArrDay.get(i).setStatus("unavailable");
                }
            }
        }
    }

    @Override
    public void onRightClick() {
        showAst();
    }

    @Override
    public void onLeftClick() {

    }
}
