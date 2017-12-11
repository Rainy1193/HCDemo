package com.homecaravan.android.consumer.consumershedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.adapter.CalendarScheduleAdapter;
import com.homecaravan.android.adapter.ScheduleShowingPropertyAdapter;
import com.homecaravan.android.consumer.adapter.SelectAdapter;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumermvp.caravanmvp.CreateCaravanPresenter;
import com.homecaravan.android.consumer.consumermvp.caravanmvp.CreateCaravanView;
import com.homecaravan.android.consumer.listener.IPickDateListener;
import com.homecaravan.android.consumer.listener.IScheduleListener;
import com.homecaravan.android.consumer.listener.ISchedulePickListener;
import com.homecaravan.android.consumer.listener.IScheduleStepOneListener;
import com.homecaravan.android.consumer.listener.ISelectListener;
import com.homecaravan.android.consumer.model.CaravanQueue;
import com.homecaravan.android.consumer.model.ConsumerListingSchedule;
import com.homecaravan.android.consumer.model.CurrentCaravan;
import com.homecaravan.android.consumer.model.CurrentListingSchedule;
import com.homecaravan.android.consumer.model.DaySchedule;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.responseapi.ResponseCaravan;
import com.homecaravan.android.consumer.widget.CustomLayoutManager;
import com.homecaravan.android.models.ItemSelect;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.OnClick;

public class FragmentStepScheduleShowing extends BaseFragment implements IScheduleStepOneListener, IPickDateListener, ISelectListener, CreateCaravanView {

    private CustomLayoutManager mHourLayoutManager;
    private CustomLayoutManager mMinLayoutManager;
    private CustomLayoutManager mAmPmLayoutManager;
    private ScheduleShowingPropertyAdapter mAdapter;
    private CalendarScheduleAdapter mCalendarAdapter;
    private SelectAdapter mHourAdapter;
    private SelectAdapter mMinAdapter;
    private SelectAdapter mAmPmAdapter;
    private ArrayList<DaySchedule> mArrDaySchedule = new ArrayList<>();
    private ArrayList<ConsumerListingSchedule> mArrListingSchedule = new ArrayList<>();
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
    private ArrayList<ItemSelect> mArrHour = new ArrayList<>();
    private ArrayList<ItemSelect> mArrMin = new ArrayList<>();
    private ArrayList<ItemSelect> mArrAmPm = new ArrayList<>();
    private int mCurrentStart = 0;
    private int mOldStart = 0;
    private int mCurrentDate = -1;
    private int mOldDate = -1;
    private int mOldHour = 0;
    private int mCurrentHour = 0;
    private int mOldMin = 0;
    private int mCurrentMin = 0;
    private int mOldAmPm = 0;
    private int mCurrentAmPm = 0;

    private CreateCaravanPresenter mCreateCaravanPresenter;
    private ISchedulePickListener mPickListener;
    private IScheduleListener mListener;
    private boolean mCreatedCaravan;
    private boolean mUpdate;
    private boolean mPickDay;

    public boolean isPickDay() {
        return mPickDay;
    }

    public void setUpdate(boolean update) {
        mUpdate = update;
    }

    public void setPickListener(ISchedulePickListener mPickListener) {
        this.mPickListener = mPickListener;
    }

    public void setCreatedCaravan(boolean mCreatedCaravan) {
        this.mCreatedCaravan = mCreatedCaravan;
    }

    public void setListener(IScheduleListener mListener) {
        this.mListener = mListener;
    }

    @Bind(R.id.rvCalendar)
    RecyclerView mRvCalendar;
    @Bind(R.id.rvProperty)
    RecyclerView mRvProperty;
    @Bind(R.id.tvMonthYear)
    TextView mTvMonthYear;
    @Bind(R.id.rvHour)
    RecyclerView mRvHour;
    @Bind(R.id.rvMin)
    RecyclerView mRvMin;
    @Bind(R.id.rvAmPm)
    RecyclerView mRvAmPm;
    @Bind(R.id.tvMonth)
    TextView mTvMonth;
    @Bind(R.id.tvDay)
    TextView mTvDay;
    @Bind(R.id.layoutPickTime)
    LinearLayout mLayoutPickTime;
    @Bind(R.id.layoutPickDay)
    LinearLayout mLayoutPickDay;
    @Bind(R.id.nameCaravan)
    EditText mNameCaravan;
    @Bind(R.id.layoutMain)
    LinearLayout mLayoutMain;

    @OnClick(R.id.ivBackPickDay)
    public void backPickDay() {
        mPickDay = false;
        mLayoutPickDay.setVisibility(View.VISIBLE);
        mLayoutPickTime.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getListing();
        mArrListingSchedule = CurrentListingSchedule.getInstance().getArrListing();
        mArrListingSchedule.get(0).setStart(true);
        mAdapter = new ScheduleShowingPropertyAdapter(mArrListingSchedule, getActivity(), this);
        mRvProperty.setAdapter(mAdapter);
        mRvProperty.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRvProperty.hasFixedSize();
        mRvProperty.setItemAnimator(null);
        mCurrentDay = mDateFormat.format(Calendar.getInstance().getTime());
        getPropertiesDate();
        getAllDay();
        getAllDaySchedule();
        setUpDataSelect();
        setUpRvSelect();
        setUpTimeDefault();
        mCalendarAdapter = new CalendarScheduleAdapter(mArrDaySchedule, getActivity(), this);
        mCreateCaravanPresenter = new CreateCaravanPresenter(this);
        mRvCalendar.setAdapter(mCalendarAdapter);
        mRvCalendar.setLayoutManager(new GridLayoutManager(getActivity(), 7));
        mRvCalendar.setHasFixedSize(true);
        mRvCalendar.setItemAnimator(null);

    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_step_schedule_showing;
    }

    @Override
    public void pickStart(int position) {
        mOldStart = mCurrentStart;
        mCurrentStart = position;
        mArrListingSchedule.get(mOldStart).setStart(false);
        mArrListingSchedule.get(mCurrentStart).setStart(true);
        mAdapter.notifyItemChanged(mOldStart);
        mAdapter.notifyItemChanged(mCurrentStart);

    }

    public void getListing() {
        for (int i = 0; i < CurrentListingSchedule.getInstance().getArrListing().size(); i++) {
            mArrListingSchedule.add(CurrentListingSchedule.getInstance().getArrListing().get(i));
        }
    }


    public void getPropertiesDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        if (mUpdate) {
            mNameCaravan.setText(CurrentCaravan.getInstance().getData().getTitle());
            calendar.set(Calendar.YEAR, CurrentListingSchedule.getInstance().getYear());
            calendar.set(Calendar.MONTH, CurrentListingSchedule.getInstance().getMonth() - 1);
            calendar.set(Calendar.DAY_OF_MONTH, CurrentListingSchedule.getInstance().getDay());
            calendar.set(Calendar.HOUR_OF_DAY, CurrentListingSchedule.getInstance().getHour());
            calendar.set(Calendar.MINUTE, CurrentListingSchedule.getInstance().getMin());
        }
        Date date = calendar.getTime();
        DateFormat df = new SimpleDateFormat("MMM", Locale.US);
        DateFormat df1 = new SimpleDateFormat("MMMM", Locale.US);
        DateFormat df2 = new SimpleDateFormat("yyyy", Locale.US);
        DateFormat df3 = new SimpleDateFormat("EEE", Locale.US);
        DateFormat df4 = new SimpleDateFormat("d", Locale.US);
        mMonthString = df.format(date);
        mMonthString1 = df1.format(date);
        mYearString = df2.format(date);
        mThString = df3.format(date);
        mDayString = df4.format(date);
        mTvMonthYear.setText(mMonthString1 + " " + mYearString);
        mTvMonth.setText(mMonthString);
        mTvDay.setText(mDayString);
    }

    public void getAllDaySchedule() {
        int positionCurrentDay;
        ArrayList<String> listDate = new ArrayList<>();
        for (int i = 0; i < mDateString.size(); i++) {
            listDate.add(mDateString.get(i));
        }
        positionCurrentDay = listDate.indexOf(mCurrentDay);
        int currentMonth = Integer.parseInt(mCurrentDay.substring(5, 7));
        int endDay = positionCurrentDay + 20;
        for (int i = 0; i < listDate.size(); i++) {
            DaySchedule day = new DaySchedule();
            day.setDay(listDate.get(i).substring(8, 10));
            if (i < positionCurrentDay || i > endDay) {
                day.setVisible(false);
            } else {
                if (Integer.parseInt(listDate.get(i).substring(5, 7)) != currentMonth) {
                    day.setInMonth(false);
                } else {
                    day.setInMonth(true);
                }
                day.setVisible(true);
            }
            if (mUpdate) {
                if (Integer.parseInt(listDate.get(i).substring(8, 10)) == CurrentListingSchedule.getInstance().getDay()) {
                    day.setSelect(true);
                }
            }
            mArrDaySchedule.add(day);
        }
    }

    public void getAllDay() {
        mMonth.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DATE));
        mDateString.clear();
        mPreviousMonth = (Calendar) mMonth.clone();
        int firstDay = mMonth.get(Calendar.DAY_OF_WEEK);
        mPreviousMonth.add(Calendar.DATE, -(firstDay - 1));
        for (int i = 0; i < firstDay + 20; i++) {
            String dateItem = mDateFormat.format(mPreviousMonth.getTime());
            mPreviousMonth.add(Calendar.DATE, 1);
            mDateString.add(dateItem);
        }
    }


    public int getMaxDayMonthPrevious() {
        int maxP;
        if (mMonth.get(Calendar.MONTH) == mMonth.getActualMinimum(Calendar.MONTH)) {
            mPreviousMonth.set((mMonth.get(Calendar.YEAR) - 1),
                    mMonth.getActualMaximum(Calendar.MONTH), 1);
        } else {
            mPreviousMonth.set(Calendar.MONTH,
                    mMonth.get(Calendar.MONTH) - 1);
        }
        maxP = mPreviousMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
        return maxP;
    }

    @Override
    public void pickDate(int position) {
        mPickDay = true;
        hideKeyboard();
        mOldDate = mCurrentDate;
        mCurrentDate = position;
        if (mOldDate != -1) {
            mArrDaySchedule.get(mOldDate).setSelect(false);
        }
        mArrDaySchedule.get(mCurrentDate).setSelect(true);
        if (mOldDate != -1) {
            mCalendarAdapter.notifyItemChanged(mOldDate);
        }
        mCalendarAdapter.notifyItemChanged(mCurrentDate);
        mLayoutPickTime.setVisibility(View.VISIBLE);
        mLayoutPickDay.setVisibility(View.INVISIBLE);
        mListener.showNext();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        CurrentListingSchedule.getInstance().setYear(calendar.get(Calendar.YEAR));
        CurrentListingSchedule.getInstance().setMonth(calendar.get(Calendar.MONTH));
        CurrentListingSchedule.getInstance().setDay(Integer.parseInt(mArrDaySchedule.get(mCurrentDate).getDay()));
        mTvDay.setText(mArrDaySchedule.get(mCurrentDate).getDay());
    }

    @Override
    public void pickDate(String fullDate) {

    }

    public void setUpDataSelect() {
        for (int i = 1; i <= 12; i++) {
            ItemSelect itemSelect = new ItemSelect();
            itemSelect.setPosition(0);
            if (i == 1) {
                itemSelect.setShowViewTop(true);
                itemSelect.setSelect(true);
            }
            if (i < 10) {
                itemSelect.setValue("0" + String.valueOf(i));
            } else {
                itemSelect.setValue(String.valueOf(i));
            }
            mArrHour.add(itemSelect);
        }

        for (int i = 0; i < 12; i++) {
            ItemSelect itemSelect = new ItemSelect();
            itemSelect.setPosition(1);
            if (i == 0) {
                itemSelect.setShowViewTop(true);
                itemSelect.setSelect(true);
            }
            if (i < 2) {
                itemSelect.setValue("0" + String.valueOf(i * 5));
            } else {
                itemSelect.setValue(String.valueOf(i * 5));
            }
            mArrMin.add(itemSelect);
        }

        ItemSelect itemSelect = new ItemSelect();
        itemSelect.setPosition(2);
        itemSelect.setSelect(true);
        itemSelect.setShowViewTop(true);
        itemSelect.setValue("AM");
        mArrAmPm.add(itemSelect);

        ItemSelect itemSelect1 = new ItemSelect();
        itemSelect1.setPosition(2);
        itemSelect1.setSelect(false);
        itemSelect1.setValue("PM");
        mArrAmPm.add(itemSelect1);
    }

    public void setUpRvSelect() {
        mHourAdapter = new SelectAdapter(mArrHour, getActivity(), this);
        mMinAdapter = new SelectAdapter(mArrMin, getActivity(), this);
        mAmPmAdapter = new SelectAdapter(mArrAmPm, getActivity(), this);
        mHourLayoutManager = new CustomLayoutManager(CustomLayoutManager.VERTICAL);
        mMinLayoutManager = new CustomLayoutManager(CustomLayoutManager.VERTICAL);
        mAmPmLayoutManager = new CustomLayoutManager(CustomLayoutManager.VERTICAL);
        mHourLayoutManager.attach(mRvHour);
        mHourLayoutManager.setCallbackInFling(true);
        mMinLayoutManager.attach(mRvMin);
        mMinLayoutManager.setCallbackInFling(true);
        mAmPmLayoutManager.attach(mRvAmPm);
        mAmPmLayoutManager.setCallbackInFling(true);

        mHourLayoutManager.setOnItemSelectedListener(new CustomLayoutManager.OnItemSelectedListener() {
            @Override
            public void onItemSelected(RecyclerView recyclerView, View item, int position) {
                mPickListener.hourPick(mArrHour.get(position).getValue());
                CurrentListingSchedule.getInstance().setHour(Integer.parseInt(mArrHour.get(position).getValue()));
                mCurrentHour = position;
            }
        });
        mMinLayoutManager.setOnItemSelectedListener(new CustomLayoutManager.OnItemSelectedListener() {
            @Override
            public void onItemSelected(RecyclerView recyclerView, View item, int position) {
                mPickListener.minPick(mArrMin.get(position).getValue());
                CurrentListingSchedule.getInstance().setHour(Integer.parseInt(mArrMin.get(position).getValue()));
                mCurrentMin = position;
            }
        });
        mAmPmLayoutManager.setOnItemSelectedListener(new CustomLayoutManager.OnItemSelectedListener() {
            @Override
            public void onItemSelected(RecyclerView recyclerView, View item, int position) {
                mPickListener.halfPick(mArrAmPm.get(position).getValue());
                CurrentListingSchedule.getInstance().setHalf(mArrAmPm.get(position).getValue());
                mCurrentAmPm = position;
            }
        });

        mRvHour.setHasFixedSize(true);
        mRvMin.setHasFixedSize(true);
        mRvAmPm.setHasFixedSize(true);
        mRvHour.setAdapter(mHourAdapter);
        mRvMin.setAdapter(mMinAdapter);
        mRvAmPm.setAdapter(mAmPmAdapter);
    }


    public void setUpTimeDefault() {
        Calendar calendar = Calendar.getInstance();
        if (mUpdate) {
            calendar.set(Calendar.YEAR, CurrentListingSchedule.getInstance().getYear());
            calendar.set(Calendar.MONTH, CurrentListingSchedule.getInstance().getMonth() - 1);
            calendar.set(Calendar.DAY_OF_MONTH, CurrentListingSchedule.getInstance().getDay());
            calendar.set(Calendar.HOUR_OF_DAY, CurrentListingSchedule.getInstance().getHour());
            calendar.set(Calendar.MINUTE, CurrentListingSchedule.getInstance().getMin());
        }
        Date date = calendar.getTime();
        DateFormat simpleDateFormat = new SimpleDateFormat("hh", Locale.US);
        DateFormat simpleDateFormat1 = new SimpleDateFormat("mm", Locale.US);
        DateFormat simpleDateFormat2 = new SimpleDateFormat("a", Locale.US);
        String currentHour = simpleDateFormat.format(date);
        String currentMin = simpleDateFormat1.format(date);
        String current = simpleDateFormat2.format(date);
        for (int i = 0; i < mArrHour.size(); i++) {
            if (Integer.parseInt(mArrHour.get(i).getValue()) == Integer.parseInt(currentHour)) {
                final int finalI = i;
                mRvHour.post(new Runnable() {
                    @Override
                    public void run() {
                        mRvHour.smoothScrollToPosition(finalI);
                        mPickListener.hourPick(mArrHour.get(finalI).getValue());
                    }
                });
                break;
            }
        }
        for (int i = 0; i < mArrMin.size(); i++) {
            if (Integer.parseInt(mArrMin.get(i).getValue()) > (Integer.parseInt(currentMin) - 5)) {
                final int finalI = i;
                mRvMin.post(new Runnable() {
                    @Override
                    public void run() {
                        mRvMin.smoothScrollToPosition(finalI);
                        mPickListener.minPick(mArrMin.get(finalI).getValue());
                    }
                });
                break;
            }
        }
        for (int i = 0; i < mArrAmPm.size(); i++) {
            if (mArrAmPm.get(i).getValue().equalsIgnoreCase(current)) {
                final int finalI = i;
                mRvAmPm.post(new Runnable() {
                    @Override
                    public void run() {
                        mRvAmPm.smoothScrollToPosition(finalI);
                        mPickListener.halfPick(mArrAmPm.get(finalI).getValue());
                    }
                });
                break;
            }
        }
    }

    public void createCaravanFromQueue() {
//        mCreatedCaravan = true;
        if (mCreatedCaravan) {
            mListener.createCaravanSuccess(false);
            return;
        }
        Log.e("getIdFromQueue()", getIdFromQueue());
        hideKeyboard();
        if (mNameCaravan.getText().toString().trim().isEmpty()) {
            showSnackBar(mLayoutMain, TypeDialog.WARNING, R.string.error_caravan_name, "caravan name");
            backPickDay();
            mNameCaravan.requestFocus();
            mListener.createCaravanFail();
            return;
        }
        showLoading();
        mListener.disableButton();
        mCreateCaravanPresenter.createCaravan(getIdFromQueue(), mNameCaravan.getText().toString());
    }

    public String getIdFromQueue() {
        String queue = "";
        ArrayList<String> id = CaravanQueue.getInstance().getIds();
        for (int i = 0; i < id.size(); i++) {
            Log.e("id", id.get(i));
            queue = queue + id.get(i) + ",";
        }
        if (queue.length() == 0) {
            return queue;
        }
        return queue.substring(0, queue.length() - 1);
    }


    @Override
    public void selectHour(int position) {

    }

    @Override
    public void selectMin(int position) {

    }

    @Override
    public void selectAmPm(int position) {

    }

    @Override
    public void selectDuration(int duration) {

    }

    public void updateListAfterSwap(int posFrom, int posTo) {
        mAdapter.notifyItemChanged(posFrom);
        mAdapter.notifyItemChanged(posTo);
    }

    @Override
    public void createCaravanSuccess(ResponseCaravan caravan) {
        mCreatedCaravan = true;
        mListener.enableButton();
        hideLoading();
        CurrentCaravan.getInstance().setData(caravan.getData());
        mListener.createCaravanSuccess(true);
    }

    @Override
    public void createCaravanFail(String message) {
        mListener.enableButton();
        hideLoading();
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "createCaravanFail");
        mListener.createCaravanFail();
    }

    @Override
    public void createCaravanFail(@StringRes int message) {
        mListener.enableButton();
        hideLoading();
        showSnackBar(mLayoutMain, TypeDialog.ERROR, message, "createCaravanFail");
        mListener.createCaravanFail();
    }


}
