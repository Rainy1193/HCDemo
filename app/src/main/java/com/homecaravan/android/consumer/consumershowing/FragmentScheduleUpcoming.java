package com.homecaravan.android.consumer.consumershowing;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.activity.CaravanInActionActivity;
import com.homecaravan.android.consumer.activity.ConversationActivity;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumerbase.ConsumerUser;
import com.homecaravan.android.consumer.consumermvp.showingmvp.GetListShowingPresenter;
import com.homecaravan.android.consumer.consumermvp.showingmvp.GetListShowingView;
import com.homecaravan.android.consumer.fastadapter.AppointmentCaravanItem;
import com.homecaravan.android.consumer.fastadapter.CalendarShowingItem;
import com.homecaravan.android.consumer.fastadapter.CaravanShowingItem;
import com.homecaravan.android.consumer.fastadapter.SingleAppointmentItem;
import com.homecaravan.android.consumer.listener.IPickDateListener;
import com.homecaravan.android.consumer.listener.IShowingListener;
import com.homecaravan.android.consumer.listener.ShowingMainListener;
import com.homecaravan.android.consumer.message.messagegetthreadidmvp.GetThreadIdPresenter;
import com.homecaravan.android.consumer.message.messagegetthreadidmvp.IGetThreadIdView;
import com.homecaravan.android.consumer.model.CurrentCaravan;
import com.homecaravan.android.consumer.model.DayShowing;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.responseapi.AppointmentShowing;
import com.homecaravan.android.consumer.model.responseapi.CaravanShowing;
import com.homecaravan.android.consumer.model.responseapi.ResponseCaravan;
import com.homecaravan.android.consumer.model.responseapi.ResponseMessageGetThreadId;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.consumer.widget.CustomNestedScrollView;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

public class FragmentScheduleUpcoming extends BaseFragment implements
        IShowingListener, GetListShowingView, IPickDateListener, IGetThreadIdView {

    private FastItemAdapter<SingleAppointmentItem> mSingleAdapter;
    private FastItemAdapter<CaravanShowingItem> mCaravanAdapter;
    private FastItemAdapter<AppointmentCaravanItem> mApptCaravanAdapter;
    private FastItemAdapter<CalendarShowingItem> mCalendarAdapter;
    private FastItemAdapter<CalendarShowingItem> mCalendarAdapterWeek;
    private FastItemAdapter<CalendarShowingItem> mCalendarAdapterClone;
    private FastItemAdapter<CalendarShowingItem> mCalendarAdapterWeekClone;

    private GetListShowingPresenter mGetListCaravanPresenter;
    private GetListShowingPresenter mGetListApptPresenter;
    private GetThreadIdPresenter mGetThreadIdPresenter;
    private IShowingListener mListener;
    private ShowingMainListener mShowingMainListener;
    private Calendar mMonth = Calendar.getInstance();
    private Calendar mPreviousMonth;
    private DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private DateFormat mDateFormat1 = new SimpleDateFormat("MMMM", Locale.US);
    private ArrayList<String> mDateString = new ArrayList<>();
    private String mCurrentDay;
    private boolean mExpand;
    private int mCountMonth = 0;
    private int mOldPosition = -1;
    private int mCurrentPosition = -1;
    private int mFDay;
    private int mLDay;
    private int mFullHeight;
    private float mInitialX = 0;
    private float mInitialY = 0;
    private int mCountAppt = 0;
    private int mCountCaravan = 0;
    private ArrayList<CaravanShowing> mArrCaravan = new ArrayList<>();
    private AlarmManager mAlarmManager;
    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.US);
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
    private AlertDialog mAlertDialog;
    private NotificationManager notificationManager;
    private ItemTouchHelper mItemTouchHelper;
    private ItemTouchHelper.SimpleCallback mSimpleItemTouchCallback;

    @Bind(R.id.layoutMain)
    FrameLayout mLayoutMain;
    @Bind(R.id.rvCaravan)
    RecyclerView mRvCaravan;
    @Bind(R.id.rvSingleShowings)
    RecyclerView mRvSingleShowings;
    @Bind(R.id.rvCalendar)
    RecyclerView mRvCalendar;
    @Bind(R.id.tvMonth)
    TextSwitcher mTvMonth;
    @Bind(R.id.tvSu)
    TextView mTvSu;
    @Bind(R.id.tvMo)
    TextView mTvMo;
    @Bind(R.id.tvTu)
    TextView mTvTu;
    @Bind(R.id.tvWe)
    TextView mTvWe;
    @Bind(R.id.tvTh)
    TextView mTvTh;
    @Bind(R.id.tvFr)
    TextView mTvFr;
    @Bind(R.id.tvSa)
    TextView mTvSa;
    @Bind(R.id.ivPrevious)
    ImageView mIvPrevious;
    @Bind(R.id.ivNext)
    ImageView mIvNext;

    @Bind(R.id.layoutPrevious)
    RelativeLayout mLayoutPrevious;
    @Bind(R.id.layoutNext)
    RelativeLayout mLayoutNext;
    @Bind(R.id.layoutEmpty)
    ScrollView mLayoutEmpty;
    @Bind(R.id.scrollView)
    CustomNestedScrollView mScrollView;

    @OnClick(R.id.layoutPrevious)
    public void previousMonth() {
        if (mCountMonth > 0) {
            mCountMonth--;
            loadAnimations(true);
            setPreviousMonth();
            getAllDay();
            getAllDayCaravan();
            if (mCountMonth == 0) {
                mLayoutPrevious.setEnabled(false);
                mIvPrevious.setColorFilter(ContextCompat.getColor(getActivity(), R.color.color_arrow_caravan1));
            }
        }
    }

    @OnClick(R.id.layoutNext)
    public void previousNext() {
        loadAnimations(false);
        mLayoutPrevious.setEnabled(true);
        mIvPrevious.setColorFilter(ContextCompat.getColor(getActivity(), R.color.color_arrow_caravan));
        mCountMonth++;
        setNextMonth();
        getAllDay();
        getAllDayCaravan();

    }

    @OnClick(R.id.ivShowFullCalendar)
    public void showFullCalendar() {
        if (mExpand) {
            ValueAnimator animator = AnimUtils.resizeCalendar(mRvCalendar, mFullHeight / 6, mFullHeight);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    mExpand = false;
                    mCalendarAdapterWeekClone.clear();
                    mCalendarAdapterWeekClone.add(mCalendarAdapterWeek.getAdapterItems());
                    mCalendarAdapterWeek.clear();
                    mCalendarAdapter.add(mCalendarAdapterClone.getAdapterItems());
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    mRvCalendar.swapAdapter(mCalendarAdapter, true);

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            animator.start();
        }
    }

    public void setShowingMainListener(ShowingMainListener mShowingMainListener) {
        this.mShowingMainListener = mShowingMainListener;
    }

    public void setListener(IShowingListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCaravanAdapter = new FastItemAdapter<>();
        mSingleAdapter = new FastItemAdapter<>();
        mRvCaravan.setLayoutManager(createLayoutManagerVertical());
        mRvCaravan.setAdapter(mCaravanAdapter);
        mRvSingleShowings.setAdapter(mSingleAdapter);
        mRvSingleShowings.setLayoutManager(createLayoutManagerVertical());
        mCurrentDay = mDateFormat.format(Calendar.getInstance().getTime());
        mGetThreadIdPresenter = new GetThreadIdPresenter(this);
        mGetListCaravanPresenter = new GetListShowingPresenter(this);
        mGetListApptPresenter = new GetListShowingPresenter(this);
        mCalendarAdapter = new FastItemAdapter<>();
        mCalendarAdapterClone = new FastItemAdapter<>();
        mCalendarAdapterWeek = new FastItemAdapter<>();
        mCalendarAdapterWeekClone = new FastItemAdapter<>();
        setFactory();
        getAllDay();
        getAllDayCaravan();
        mRvCalendar.setLayoutManager(new GridLayoutManager(getActivity(), 7));
        mRvCalendar.setHasFixedSize(true);
        mRvCalendar.setItemAnimator(new ScaleInAnimator());
        mRvCalendar.setAdapter(mCalendarAdapter);
        mGetListCaravanPresenter.getListShowing(mCurrentDay, mCurrentDay, "caravan");
        mGetListApptPresenter.getListShowing(mCurrentDay, mCurrentDay, "single");
        mSimpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    previousNext();
                } else {
                    previousMonth();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            }
        };
        mItemTouchHelper = new ItemTouchHelper(mSimpleItemTouchCallback);
        mItemTouchHelper.attachToRecyclerView(mRvCalendar);
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > 0 && !mExpand) {
                    ValueAnimator animator = AnimUtils.resizeCalendar(mRvCalendar, mFullHeight, mFullHeight / 6);
                    animator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                            mExpand = true;
                            mCalendarAdapterClone.clear();
                            mCalendarAdapterClone.add(mCalendarAdapter.getAdapterItems());
                            mCalendarAdapter.clear();
                            mCalendarAdapterWeek.add(mCalendarAdapterWeekClone.getAdapterItems());
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            mRvCalendar.swapAdapter(mCalendarAdapterWeek, true);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                    animator.start();
                }
            }
        });
        mAlarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public LinearLayoutManager createLayoutManagerVertical() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }

    public void getAllDayCaravan() {
        mCalendarAdapter.clear();
        mCalendarAdapterWeek.clear();
        int positionCurrentDay;
        ArrayList<String> listDate = new ArrayList<>();
        for (int i = 0; i < mDateString.size(); i++) {
            listDate.add(mDateString.get(i));
        }
        positionCurrentDay = listDate.indexOf(mCurrentDay);
        mCurrentPosition = positionCurrentDay;
        int currentMonth = Integer.parseInt(mCurrentDay.substring(5, 7));
        getDayInWeekOfCurrentDay();
        for (int i = 0; i < listDate.size(); i++) {
            DayShowing day = new DayShowing();
            day.setFullDay(listDate.get(i));
            day.setDay(listDate.get(i).substring(8, 10));
            if (Integer.parseInt(listDate.get(i).substring(5, 7)) != currentMonth) {
                day.setInMonth(false);
            } else {
                day.setInMonth(true);
            }
            if (i == positionCurrentDay) {
                day.setSelect(true);
            }
            CalendarShowingItem item = new CalendarShowingItem();
            item.setPosition(i);
            item.setContext(getActivity());
            item.setDayShowing(day);
            item.setListener(this);

            mCalendarAdapter.add(item);
            if (i >= mFDay && i <= mLDay) {
                mCalendarAdapterWeek.add(item);
            }
        }
        mRvCalendar.post(new Runnable() {
            @Override
            public void run() {
                mFullHeight = mRvCalendar.getHeight();
            }
        });
    }

    public void getDayInWeekOfCurrentDay() {
        mCalendarAdapterWeek.clear();
        int d = mCurrentPosition / 7;
        if (d == 5) {
            mFDay = 35;
            mLDay = 41;
        }
        if (d == 4) {
            mFDay = 28;
            mLDay = 34;
        }
        if (d == 3) {
            mFDay = 21;
            mLDay = 27;
        }
        if (d == 2) {
            mFDay = 14;
            mLDay = 20;
        }
        if (d == 1) {
            mFDay = 7;
            mLDay = 13;
        }
        if (d == 0) {
            mFDay = 0;
            mLDay = 6;
        }
    }

    public void getAllDay() {
        mTvMonth.setText(mDateFormat1.format(mMonth.getTime()));
        mMonth.set(Calendar.DAY_OF_MONTH, 1);
        mDateString.clear();
        mPreviousMonth = (Calendar) mMonth.clone();
        int firstDay = mMonth.get(Calendar.DAY_OF_WEEK);
        int maxWeekNumber = mMonth.getActualMaximum(Calendar.WEEK_OF_MONTH);
        if (maxWeekNumber == 5) {
            maxWeekNumber++;
        }
        int monthLength;
        monthLength = maxWeekNumber * 7;
        int maxDayMonthPrevious = getMaxDayMonthPrevious();
        int calMaxPrevious = maxDayMonthPrevious - (firstDay - 1);
        Calendar previousMonthMaxSet = (Calendar) mPreviousMonth.clone();
        previousMonthMaxSet.set(Calendar.DAY_OF_MONTH, calMaxPrevious + 1);
        for (int i = 0; i < monthLength; i++) {
            String dateItem = mDateFormat.format(previousMonthMaxSet.getTime());
            previousMonthMaxSet.add(Calendar.DATE, 1);
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

    private void setNextMonth() {

        if (mMonth.get(Calendar.MONTH) == mMonth.getActualMaximum(Calendar.MONTH)) {
            mMonth.set((mMonth.get(Calendar.YEAR) + 1),
                    mMonth.getActualMinimum(Calendar.MONTH), 1);
        } else {
            mMonth.set(Calendar.MONTH,
                    mMonth.get(Calendar.MONTH) + 1);
        }


    }

    private void setPreviousMonth() {
        if (mMonth.get(Calendar.MONTH) == mMonth.getActualMinimum(Calendar.MONTH)) {
            mMonth.set((mMonth.get(Calendar.YEAR) - 1),
                    mMonth.getActualMaximum(Calendar.MONTH), 1);
        } else {
            mMonth.set(Calendar.MONTH,
                    mMonth.get(Calendar.MONTH) - 1);
        }


    }

    @Override
    public void showCaravanAction() {
        //CurrentCaravan.getInstance().setData(mArrCaravan.get(position).getRefCaravan());
        mListener.showCaravanAction();
    }

    @Override
    public void onMessageClicked(String apptId, String listingId, String caravanId, String title, String userId) {
        if(!ConsumerUser.getInstance().getData().getId().equals(userId)){
            mGetThreadIdPresenter.getThreadId(apptId, listingId, caravanId, title, userId);
            showLoading();
        }
    }

    @Override
    public void editCaravan() {
        //CurrentCaravan.getInstance().setData(mArrCaravan.get(position).getRefCaravan());
        mListener.editCaravan();
    }

    protected int getLayoutResourceId() {
        return R.layout.fragment_schedule_upcoming;
    }


    @Override
    public void getShowingApptSuccess(ArrayList<AppointmentShowing> appointments) {
        boolean pending = false, canceled = false;
        mCountAppt = appointments.size();
        if (mCountCaravan + mCountAppt == 0) {
            mLayoutEmpty.post(new Runnable() {
                @Override
                public void run() {
                    mLayoutEmpty.scrollTo(0, 0);
                }
            });
            mLayoutEmpty.setVisibility(View.VISIBLE);
        } else {
            mLayoutEmpty.setVisibility(View.GONE);
        }
        for (int i = 0; i < appointments.size(); i++) {
            SingleAppointmentItem singleAppointmentItem = new SingleAppointmentItem();
            singleAppointmentItem.setContext(getActivity());
            singleAppointmentItem.setAppointment(appointments.get(i));
            singleAppointmentItem.setListener(this);
            mSingleAdapter.add(singleAppointmentItem);
            if (appointments.get(i).getEventStatus().equalsIgnoreCase("pending")) {
                pending = true;
            }
            if (appointments.get(i).getEventStatus().equalsIgnoreCase("canceled")) {
                canceled = true;
            }
        }
        for (int i = 0; i < mCalendarAdapter.getItemCount(); i++) {
            if (mCalendarAdapter.getAdapterItem(i).getDayShowing().getFullDay().equalsIgnoreCase(mCurrentDay)) {
                mCalendarAdapter.getAdapterItem(i).getDayShowing().setShowCancelled(canceled);
                mCalendarAdapter.getAdapterItem(i).getDayShowing().setShowPending(pending);
                mCalendarAdapter.getAdapterItem(i).updateCancelled(canceled);
                mCalendarAdapter.getAdapterItem(i).updatePending(pending);
            }

        }

        for (int i = 0; i < mCalendarAdapterWeek.getItemCount(); i++) {
            if (mCalendarAdapterWeek.getAdapterItem(i).getDayShowing().getFullDay().equalsIgnoreCase(mCurrentDay)) {
                mCalendarAdapterWeek.getAdapterItem(i).getDayShowing().setShowCancelled(canceled);
                mCalendarAdapterWeek.getAdapterItem(i).getDayShowing().setShowPending(pending);
                mCalendarAdapterWeek.getAdapterItem(i).updateCancelled(canceled);
                mCalendarAdapterWeek.getAdapterItem(i).updatePending(pending);
            }
        }
    }

    @Override
    public void getShowingCaravanSuccess(ArrayList<CaravanShowing> caravans) {
        boolean hasCaravan = false;
        mCountCaravan = caravans.size();
        if (mCountCaravan != 0) {
            hasCaravan = true;
        }
        if (mCountCaravan + mCountAppt == 0) {
            mLayoutEmpty.post(new Runnable() {
                @Override
                public void run() {
                    mLayoutEmpty.scrollTo(0, 0);
                }
            });
            mLayoutEmpty.setVisibility(View.VISIBLE);
        } else {
            mLayoutEmpty.setVisibility(View.GONE);
            mArrCaravan.clear();
            for (int i = 0; i < mCountCaravan; i++) {
                CaravanShowingItem caravanItem = new CaravanShowingItem();
                caravanItem.setContext(getActivity());
                caravanItem.setCaravan(caravans.get(i));
                caravanItem.setListener(this);
                ArrayList<ResponseCaravan.CaravanDestinations> destinations = caravans.get(i).getRefCaravan().getDestinations();
                int m = destinations.size();
                mApptCaravanAdapter = new FastItemAdapter<>();
                for (int j = 0; j < m; j++) {
                    if (destinations.get(j).getAppointment() != null) {
                        AppointmentCaravanItem apptCaravanItem = new AppointmentCaravanItem();
                        apptCaravanItem.setContext(getActivity());
                        apptCaravanItem.setDestinations(destinations.get(j).getAppointment());
                        mApptCaravanAdapter.add(apptCaravanItem);
                    }
                }
                caravanItem.setApptAdapter(mApptCaravanAdapter);
                mCaravanAdapter.add(caravanItem);
                mArrCaravan.add(caravans.get(i));

                createDataForNotification(caravans.get(i));
            }
            mLayoutEmpty.setVisibility(View.GONE);
            checkNotificationToShowPopup();
        }
        mShowingMainListener.showLayout();
        for (int i = 0; i < mCalendarAdapter.getItemCount(); i++) {
            if (mCalendarAdapter.getAdapterItem(i).getDayShowing().getFullDay().equalsIgnoreCase(mCurrentDay)) {
                mCalendarAdapter.getAdapterItem(i).getDayShowing().setShowApproved(hasCaravan);
                mCalendarAdapter.getAdapterItem(i).updateApproved(hasCaravan);
            }
        }

        for (int i = 0; i < mCalendarAdapterWeek.getItemCount(); i++) {
            if (mCalendarAdapterWeek.getAdapterItem(i).getDayShowing().getFullDay().equalsIgnoreCase(mCurrentDay)) {
                mCalendarAdapterWeek.getAdapterItem(i).getDayShowing().setShowApproved(hasCaravan);
                mCalendarAdapterWeek.getAdapterItem(i).updateApproved(hasCaravan);
            }
        }
    }

    @Override
    public void getShowingApptFail(String message) {

    }


    @Override
    public void getShowingCaravanFail(String message) {
        Log.e("getShowingCaravanFail", message);
    }

    @Override
    public void getShowingApptFail(@StringRes int message) {

    }


    @Override
    public void getShowingCaravanFail(@StringRes int message) {

    }

    @Override
    public void pickDate(int position) {

    }

    @Override
    public void pickDate(String fullDay) {
        if (mExpand) {
            int position = 0;
            for (int i = 0; i < mCalendarAdapterWeek.getItemCount(); i++) {
                if (mCalendarAdapterWeek.getAdapterItem(i).getDayShowing().getFullDay().equalsIgnoreCase(fullDay)) {
                    position = i;
                }
                if (mCalendarAdapterWeek.getAdapterItem(i).getDayShowing().isSelect()) {
                    mCurrentPosition = i;
                }
            }

            for (int i = 0; i < mCalendarAdapter.getItemCount(); i++) {
                if (mCalendarAdapter.getAdapterItem(i).getDayShowing().getFullDay().equalsIgnoreCase(fullDay)) {
                    mCalendarAdapter.getAdapterItem(i).getDayShowing().setSelect(true);
                } else {
                    mCalendarAdapter.getAdapterItem(i).getDayShowing().setSelect(false);
                }
            }

            mCurrentDay = mCalendarAdapterWeek.getAdapterItem(position).getDayShowing().getFullDay();
            mOldPosition = mCurrentPosition;
            mCurrentPosition = position;
            if (mOldPosition != -1) {
                mCalendarAdapterWeek.getAdapterItem(mOldPosition).getDayShowing().setSelect(false);
                mCalendarAdapterWeek.getAdapterItem(mOldPosition).updateApproved(false);
                mCalendarAdapterWeek.getAdapterItem(mOldPosition).updateCancelled(false);
                mCalendarAdapterWeek.getAdapterItem(mOldPosition).updatePending(false);
                mCalendarAdapterWeek.notifyItemChanged(mOldPosition);
            }
            mCalendarAdapterWeek.getAdapterItem(mCurrentPosition).getDayShowing().setSelect(true);
            mCalendarAdapterWeek.notifyItemChanged(mCurrentPosition);

            mLayoutEmpty.setVisibility(View.GONE);
            mCaravanAdapter.clear();
            mSingleAdapter.clear();
            mGetListCaravanPresenter.getListShowing(mCurrentDay, mCurrentDay, "caravan");
            mGetListApptPresenter.getListShowing(mCurrentDay, mCurrentDay, "single");
        } else {

            int position = 0;
            for (int i = 0; i < mCalendarAdapter.getItemCount(); i++) {
                if (mCalendarAdapter.getAdapterItem(i).getDayShowing().getFullDay().equalsIgnoreCase(fullDay)) {
                    position = i;
                }
                if (mCalendarAdapter.getAdapterItem(i).getDayShowing().isSelect()) {
                    mCurrentPosition = i;
                }
            }

            for (int i = 0; i < mCalendarAdapterWeek.getItemCount(); i++) {
                if (mCalendarAdapterWeek.getAdapterItem(i).getDayShowing().getFullDay().equalsIgnoreCase(fullDay)) {
                    mCalendarAdapterWeek.getAdapterItem(i).getDayShowing().setSelect(true);
                } else {
                    mCalendarAdapterWeek.getAdapterItem(i).getDayShowing().setSelect(false);
                }
            }

            mCurrentDay = mCalendarAdapter.getAdapterItem(position).getDayShowing().getFullDay();
            mOldPosition = mCurrentPosition;
            mCurrentPosition = position;
            if (mOldPosition != -1) {
                mCalendarAdapter.getAdapterItem(mOldPosition).getDayShowing().setSelect(false);
                mCalendarAdapter.getAdapterItem(mOldPosition).updateApproved(false);
                mCalendarAdapter.getAdapterItem(mOldPosition).updateCancelled(false);
                mCalendarAdapter.getAdapterItem(mOldPosition).updatePending(false);
                mCalendarAdapter.notifyItemChanged(mOldPosition);
            }
            mCalendarAdapter.getAdapterItem(mCurrentPosition).getDayShowing().setSelect(true);
            mCalendarAdapter.notifyItemChanged(mCurrentPosition);

            getDayInWeekOfCurrentDay();
            for (int i = 0; i < mCalendarAdapter.getItemCount(); i++) {
                if (i >= mFDay && i <= mLDay) {
                    mCalendarAdapterWeek.add(mCalendarAdapter.getAdapterItem(i));
                }
            }
            mLayoutEmpty.setVisibility(View.GONE);
            mCaravanAdapter.clear();
            mSingleAdapter.clear();
            mGetListCaravanPresenter.getListShowing(mCurrentDay, mCurrentDay, "caravan");
            mGetListApptPresenter.getListShowing(mCurrentDay, mCurrentDay, "single");
        }
    }

    public void loadAnimations(boolean b) {
        Animation in = AnimationUtils.loadAnimation(getActivity(),
                android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getActivity(),
                android.R.anim.slide_out_right);


        mTvMonth.setInAnimation(in);
        mTvMonth.setOutAnimation(out);
    }

    public void setFactory() {
        mTvMonth.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                TextView textView = new TextView(getActivity());
                textView.setGravity(Gravity.CENTER);
                return textView;

            }
        });
    }

    public void getNewCaravan(String day) {

    }

    private void createDataForNotification(CaravanShowing caravans) {
        Calendar caravanCalendar = Calendar.getInstance();
        long currentTime = caravanCalendar.getTimeInMillis();
        long timeShowNotification;
        Date date;
        try {
            date = simpleDateFormat.parse(caravans.getStart());
            caravanCalendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timeShowNotification = caravanCalendar.getTimeInMillis() - 1800000; //before 30 minutes

        if (currentTime < timeShowNotification) {
            String caravanStartTime = dateFormat.format(Utils.createDateFromString(caravans.getStart()));
            createAlarmManager(timeShowNotification, caravans.getId(), caravans.getTitle(), caravanStartTime);
        }
    }

    private void createAlarmManager(long timeShowNotification, String caravanID, String caravanName, String caravanStartTime) {

        int notificationID = 0;
        String[] split = caravanID.split("-");
        if (split.length > 1) {
            notificationID = Integer.parseInt(split[1]);
        }

        if (notificationManager != null) {
            notificationManager.cancel(caravanID, notificationID);
        }

        Intent notificationIntent = new Intent("android.media.action.CARAVAN_DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");
        notificationIntent.putExtra("CaravanID", caravanID);
        notificationIntent.putExtra("CaravanName", caravanName);
        notificationIntent.putExtra("CaravanStartTime", caravanStartTime);
        //If requestCode is the same, then the new alarm will overwrite the old one.
        int uniqueID = (int) System.currentTimeMillis();
        PendingIntent broadcast = PendingIntent.getBroadcast(getActivity(), uniqueID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//        test
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.SECOND, i++ * 3 + 3);
//        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, timeShowNotification, broadcast);
    }

    private void checkNotificationToShowPopup() {
        Log.d("DaoDiDem", "checkNotificationToShowPopup: mIsCaravanInAction: " + HomeCaravanApplication.mIsCaravanInAction
                + " mCaravanID: " + HomeCaravanApplication.mCaravanID);
        if (HomeCaravanApplication.mIsCaravanInAction) {
            for (CaravanShowing caravan : mArrCaravan) {
                Log.d("DaoDiDem", "mArrCaravan: " + caravan.getId());
                if (caravan.getId().equals(HomeCaravanApplication.mCaravanID)) {
                    checkTimeCaravanInProgress(caravan);
                    HomeCaravanApplication.mIsCaravanInAction = false;
                    HomeCaravanApplication.mCaravanID = "";
                }
            }
        }
    }

    private void checkTimeCaravanInProgress(CaravanShowing caravan) {

        Calendar currentCalendar = Calendar.getInstance();
        long currentTime = currentCalendar.getTimeInMillis();
        Calendar caravanCalendar = Calendar.getInstance();
        long caravanStartTime;
        long caravanEndTime;
        Date date;
        try {
            date = simpleDateFormat.parse(caravan.getStart());
            caravanCalendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        caravanStartTime = caravanCalendar.getTimeInMillis() - 1800000; //before 30 minutes

        if (caravan.getEnd() != null) {
            try {
                date = simpleDateFormat.parse(caravan.getEnd());
                caravanCalendar.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            caravanEndTime = caravanCalendar.getTimeInMillis();
            if (caravanStartTime < currentTime && currentTime < caravanEndTime) {
                int listings = caravan.getRefCaravan().getDestinations().size();
                DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.US);
                String startTime = dateFormat.format(Utils.createDateFromString(caravan.getStart()));
                String message = "\"Your Caravan at " + startTime + " for " + listings + " listings is about to start, you can activate \"<b>in action mode</b>\" now to share your location, your readiness and your ETA to other parties now\"";
                ciaShowPopupCaravan(message, caravan);
            }
        } else {
            if (caravanStartTime < currentTime && currentTime < caravanStartTime + 1800000) {
                int listings = caravan.getRefCaravan().getDestinations().size();
                DateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
                String startTime = dateFormat.format(Utils.createDateFromString(caravan.getStart()));
                String message = "\"Your Caravan at " + startTime + " for " + listings + " listings is about to start, you can activate \"<b>in action mode</b>\" now to share your location, your readiness and your ETA to other parties now\"";
                ciaShowPopupCaravan(message, caravan);
            }
        }
    }

    private void ciaShowPopupCaravan(String message, final CaravanShowing caravan) {
        if (mAlertDialog == null) {
            LayoutInflater layoutInflater1 = LayoutInflater.from(getActivity());
            View view1 = layoutInflater1.inflate(R.layout.dialog_consumer_caravan_popup_show_popup_caravan, null);
            FrameLayout rlButton1 = (FrameLayout) view1.findViewById(R.id.rlButton1);
            FrameLayout rlButton2 = (FrameLayout) view1.findViewById(R.id.rlButton2);
            TextView tvPopupMessage = (TextView) view1.findViewById(R.id.tvPopupMessage);
            tvPopupMessage.setText(fromHtml(message));
            mAlertDialog = new AlertDialog.Builder(getActivity()).setView(view1).create();
            mAlertDialog.getWindow().getAttributes().windowAnimations = R.style.TeamTabSearchDialog;
            mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams wmlp = mAlertDialog.getWindow().getAttributes();
            wmlp.gravity = Gravity.CENTER;
            wmlp.y = -200;   //y position

            rlButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CurrentCaravan.getInstance().setData(caravan.getRefCaravan());
                    getActivity().startActivity(new Intent(getActivity(), CaravanInActionActivity.class));
                    mAlertDialog.dismiss();
                }
            });
        }
        mAlertDialog.show();
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    public void checkToShowPopupWhenCurrentItemIsShowing() {
        Log.d("DaoDiDem", "checkToShowPopupWhenCurrentItemIsShowing: clicked !!");
        checkNotificationToShowPopup();
    }
    
    @Override
    public void getThreadIdAtCaravanSuccess(ResponseMessageGetThreadId threadId, int position, String threadName) {
        
    }

    @Override
    public void getThreadIdSuccess(ResponseMessageGetThreadId threadId, String threadName) {
        Log.e("DaoDiDem", "getThreadIdSuccess: threadId: " + threadId.getThreadId());
        if(!HomeCaravanApplication.mLoginSocketSuccess){
            return;
        }
        Intent intent = new Intent(getActivity(), ConversationActivity.class);
        intent.putExtra("THREAD_ID", threadId.getThreadId());
        String responseMessage1 = "I’m driving right now";
        intent.putExtra("RESPONSE_MESSAGE_1", responseMessage1);
        intent.putExtra("MESSAGE_THREAD_NAME", threadName);
        startActivity(intent);
        hideLoading();
    }

    @Override
    public void getThreadIdFail() {
        hideLoading();
        showSnackBar(mLayoutMain, TypeDialog.ERROR, "Failed", "getThreadIdFail");
    }

    @Override
    public void getThreadIdFail(@StringRes int message) {
        hideLoading();
        showSnackBar(mLayoutMain, TypeDialog.ERROR, message, "getThreadIdFail");
    }
}
