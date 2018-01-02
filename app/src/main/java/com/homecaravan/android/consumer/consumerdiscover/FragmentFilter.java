package com.homecaravan.android.consumer.consumerdiscover;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.listener.IFilterListener;
import com.homecaravan.android.consumer.model.SingletonFilter;
import com.homecaravan.android.consumer.model.responseapi.ConditionFull;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.consumer.widget.RangeSeekBar;
import com.homecaravan.android.models.CurrentSaveSearch;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnFocusChange;


public class FragmentFilter extends BaseFragment {

    private boolean mTypeAll = true;
    private boolean mTypeHouse;
    private boolean mTypeTownhouse;
    private boolean mTypeCondo;
    private boolean mTypeManufacturedHome;
    private boolean mTypeResident;
    private boolean mTypeResidentialIncome;
    private boolean mTypeResidentialLease;

    private float mXBedAny, mXBed1, mXBed2, mXBed3, mXBed4, mXBed5;
    private float mXBathAny, mXBath1, mXBath2, mXBath3, mXBath4, mXBath5;
    private int mBedRoom = 0;
    private int mBathRoom = 0;
    private int mBedRoomApter = 0;
    private int mBathRoomApter = 0;

    private IFilterListener mListener;
    private String mPf = "";
    private boolean mHasDataSearch;
    @Bind(R.id.scrollView)
    ScrollView mScrollView;

    @Bind(R.id.vTypeAll)
    View mVTypeAll;
    @Bind(R.id.tvTypeAll)
    TextView mTvTypeAll;
    @Bind(R.id.vTypeHouse)
    View mVTypeHouse;
    @Bind(R.id.tvTypeHouse)
    TextView mTvTypeHouse;
    @Bind(R.id.vTypeTownhouse)
    View mVTypeTownhouse;
    @Bind(R.id.tvTypeTownhouse)
    TextView mTvTypeTownhouse;
    @Bind(R.id.vTypeCondo)
    View mVTypeCondo;
    @Bind(R.id.tvTypeCondo)
    TextView mTvTypeCondo;
    @Bind(R.id.vTypeManufacturedHome)
    View mVTypeManufacturedHome;
    @Bind(R.id.tvTypeManufacturedHome)
    TextView mTvTypeManufacturedHome;
    @Bind(R.id.vTypeResident)
    View mVTypeResident;
    @Bind(R.id.tvTypeResident)
    TextView mTvTypeResident;
    @Bind(R.id.vTypeResidentialIncome)
    View mVTypeResidentialIncome;
    @Bind(R.id.tvTypeResidentialIncome)
    TextView mTvTypeResidentialIncome;
    @Bind(R.id.vTypeResidentialLease)
    View mVTypeResidentialLease;
    @Bind(R.id.tvTypeResidentialLease)
    TextView mTvTypeResidentialLease;

    @Bind(R.id.layoutMoveBed)
    RelativeLayout mLayoutMoveBed;
    @Bind(R.id.bedAny)
    TextView mBedAny;
    @Bind(R.id.bed1)
    TextView mBed1;
    @Bind(R.id.bed2)
    TextView mBed2;
    @Bind(R.id.bed3)
    TextView mBed3;
    @Bind(R.id.bed4)
    TextView mBed4;
    @Bind(R.id.bed5)
    TextView mBed5;

    @Bind(R.id.layoutMoveBath)
    RelativeLayout mLayoutMoveBath;
    @Bind(R.id.bathAny)
    TextView mBathAny;
    @Bind(R.id.bath1)
    TextView mBath1;
    @Bind(R.id.bath2)
    TextView mBath2;
    @Bind(R.id.bath3)
    TextView mBath3;
    @Bind(R.id.bath4)
    TextView mBath4;
    @Bind(R.id.bath5)
    TextView mBath5;

    @Bind(R.id.layoutBedAny)
    RelativeLayout mLayoutBedAny;
    @Bind(R.id.layoutBed1)
    RelativeLayout mLayoutBed1;
    @Bind(R.id.layoutBed2)
    RelativeLayout mLayoutBed2;
    @Bind(R.id.layoutBed3)
    RelativeLayout mLayoutBed3;
    @Bind(R.id.layoutBed4)
    RelativeLayout mLayoutBed4;
    @Bind(R.id.layoutBed5)
    RelativeLayout mLayoutBed5;
    @Bind(R.id.layoutBathAny)
    RelativeLayout mLayoutBathAny;
    @Bind(R.id.layoutBath1)
    RelativeLayout mLayoutBath1;
    @Bind(R.id.layoutBath2)
    RelativeLayout mLayoutBath2;
    @Bind(R.id.layoutBath3)
    RelativeLayout mLayoutBath3;
    @Bind(R.id.layoutBath4)
    RelativeLayout mLayoutBath4;
    @Bind(R.id.layoutBath5)
    RelativeLayout mLayoutBath5;
    @Bind(R.id.rangeSeekbar)
    RangeSeekBar mRangeSeekBar;
    @Bind(R.id.tvMinPrice)
    TextView mMinPrice;
    @Bind(R.id.tvMaxPrice)
    TextView mMaxPrice;
    @Bind(R.id.etKeyword)
    EditText mKeyword;
    @Bind(R.id.tvLotSizeMin)
    EditText mLotSizeMin;
    @Bind(R.id.tvLotSizeMax)
    EditText mLotSizeMax;
    @Bind(R.id.tvSfMin)
    EditText mSfMin;
    @Bind(R.id.tvSfMax)
    EditText mSfMax;
    @Bind(R.id.tvYearBuildMin)
    EditText mYearBuildMin;
    @Bind(R.id.tvYearBuildMax)
    EditText mYearBuildMax;
    @Bind(R.id.tvDayMax)
    EditText mDayMax;
    @Bind(R.id.tvScroll)
    TextView mTvScroll;

    @OnClick(R.id.tvLotSizeMin)
    public void onClick() {
        scrollDown(false);
    }

    @OnFocusChange(value = R.id.tvLotSizeMin)
    public void onFocusChange(boolean b) {
        if (b) {
            scrollDown(false);
        }
    }

    @OnClick(R.id.tvLotSizeMax)
    public void onClick1() {
        scrollDown(false);
    }

    @OnFocusChange(R.id.tvLotSizeMax)
    public void onFocusEditText1(boolean b) {
        if (b) {
            scrollDown(false);
        }
    }

    @OnClick(R.id.tvSfMin)
    public void onClick2() {
        scrollDown(false);
    }

    @OnFocusChange(R.id.tvSfMin)
    public void onFocusEditText2(boolean b) {
        if (b) {
            scrollDown(false);
        }
    }

    @OnClick(R.id.tvSfMax)
    public void onClick3() {
        scrollDown(false);
    }

    @OnFocusChange(R.id.tvSfMax)
    public void onFocusEditText3(boolean b) {
        if (b) {
            scrollDown(false);
        }
    }

    @OnClick(R.id.tvYearBuildMin)
    public void onClick4() {
        scrollDown(false);
    }

    @OnFocusChange(R.id.tvYearBuildMin)
    public void onFocusEditText4(boolean b) {
        if (b) {
            scrollDown(false);
        }
    }

    @OnClick(R.id.tvYearBuildMax)
    public void onClick5() {
        scrollDown(false);
    }

    @OnFocusChange(R.id.tvYearBuildMax)
    public void onFocusEditText5(boolean b) {
        if (b) {
            scrollDown(false);
        }
    }

    @OnClick(R.id.tvDayMax)
    public void onClick6() {
        scrollDown(false);
    }

    @OnFocusChange(R.id.tvDayMax)
    public void onFocusEditText6(boolean b) {
        if (b) {
            scrollDown(false);
        }
    }

    @OnClick(R.id.etKeyword)
    public void onClick7() {
        scrollDown(true);
    }

    @OnFocusChange(R.id.etKeyword)
    public void onFocusEditText7(boolean b) {
        if (b) {
            scrollDown(true);
        }
    }

    @OnClick(R.id.tvApply)
    public void applyFilter() {
        int lsMin = mLotSizeMin.getText().toString().isEmpty() ? 0 : Integer.parseInt(mLotSizeMin.getText().toString());
        int lsMax = mLotSizeMax.getText().toString().isEmpty() ? 0 : Integer.parseInt(mLotSizeMax.getText().toString());
        int lsfMin = mSfMin.getText().toString().isEmpty() ? 0 : Integer.parseInt(mSfMin.getText().toString());
        int lsfMax = mSfMax.getText().toString().isEmpty() ? 0 : Integer.parseInt(mSfMax.getText().toString());
        int ybMin = mYearBuildMin.getText().toString().isEmpty() ? 0 : Integer.parseInt(mYearBuildMin.getText().toString());
        int ybMax = mYearBuildMax.getText().toString().isEmpty() ? 0 : Integer.parseInt(mYearBuildMax.getText().toString());
//        if (lsMin > lsMax) {
//            showDialog(TypeDialog.WARNING, "Data invalidate", "data error");
//            return;
//        }
//        if (lsfMin > lsfMax) {
//            showDialog(TypeDialog.WARNING, "Data invalidate", "data error");
//            return;
//        }
//        if (ybMin > ybMax) {
//            showDialog(TypeDialog.WARNING, "Data invalidate", "data error");
//            return;
//        }
        mPf = "";
        if (mTypeHouse) {
            mPf = mPf + "house,";
        }
        if (mTypeTownhouse) {
            mPf = mPf + "townhouse,";
        }
        if (mTypeCondo) {
            mPf = mPf + "condo,";
        }
        if (mTypeManufacturedHome) {
            mPf = mPf + "manufactured,";
        }
        if (mTypeResidentialIncome) {
            mPf = mPf + "residentialIncome,";
        }
        if (mTypeResidentialLease) {
            mPf = mPf + "residentialLease,";
        }
        if (mTypeResident) {
            mPf = mPf + "resident,";
        }
        if (mPf.length() > 0) {
            mPf = mPf.substring(0, mPf.length() - 1);
        }

        Log.e("mMaxPrice", mRangeSeekBar.getSelectedMinValue().toString());
        Log.e("mMinPrice", mRangeSeekBar.getSelectedMaxValue().toString());
        Log.e("mBed", String.valueOf(mBedRoomApter));
        Log.e("mBath", String.valueOf(mBathRoomApter));
        Log.e("mMinLs", mLotSizeMin.getText().toString());
        Log.e("mMaxLs", mLotSizeMax.getText().toString());
        Log.e("mMinLsf", mSfMin.getText().toString());
        Log.e("mMaxLsf", mSfMax.getText().toString());
        Log.e("mMinYb", mYearBuildMin.getText().toString());
        Log.e("mMaxYb", mYearBuildMax.getText().toString());
        Log.e("mPt", mPf);
        Log.e("mKeyword", mKeyword.getText().toString());
        Log.e("mDc", mDayMax.getText().toString());

        SingletonFilter.getInstance().setMaxPrice(mRangeSeekBar.getSelectedMaxValue().toString());
        SingletonFilter.getInstance().setMinPrice(mRangeSeekBar.getSelectedMinValue().toString());
        SingletonFilter.getInstance().setBed(String.valueOf(mBedRoomApter));
        SingletonFilter.getInstance().setBath(String.valueOf(mBathRoomApter));
        SingletonFilter.getInstance().setMinLs(mLotSizeMin.getText().toString());
        SingletonFilter.getInstance().setMaxLs(mLotSizeMax.getText().toString());
        SingletonFilter.getInstance().setMinLsf(mSfMin.getText().toString());
        SingletonFilter.getInstance().setMaxLsf(mSfMax.getText().toString());
        SingletonFilter.getInstance().setMaxYb(mYearBuildMax.getText().toString());
        SingletonFilter.getInstance().setMinYb(mYearBuildMin.getText().toString());
        SingletonFilter.getInstance().setPt(mPf);
        SingletonFilter.getInstance().setKeyword(mKeyword.getText().toString());
        SingletonFilter.getInstance().setDc(mDayMax.getText().toString());
        Log.e("SingletonFilter", SingletonFilter.getInstance().toString());
        mListener.applyFilter("sale", mRangeSeekBar.getSelectedMinValue().toString(), mRangeSeekBar.getSelectedMaxValue().toString(),
                String.valueOf(mBedRoomApter), String.valueOf(mBathRoomApter), mLotSizeMin.getText().toString(), mLotSizeMax.getText().toString(),
                mSfMin.getText().toString(), mSfMax.getText().toString(), mYearBuildMin.getText().toString(),
                mYearBuildMax.getText().toString(), mPf, mDayMax.getText().toString(), mKeyword.getText().toString());
    }

    public void cancelFilter() {

    }

    @OnClick(R.id.layoutReset)
    public void resetFilter() {
        mHasDataSearch = false;
        mBathRoom = mBathRoomApter;
        mBathRoomApter = 0;
        changeTextWithAnimation(mBathAny, mBathRoomApter, false);
        changeTextColor(mBathAny, true);
        mBedRoom = mBedRoomApter;
        mBedRoomApter = 0;
        changeTextWithAnimation(mBedAny, mBedRoomApter, true);
        changeTextColor(mBedAny, true);
        resetProperType();
        mMinPrice.setText("0");
        mMaxPrice.setText("5M");
        mSfMin.setText("");
        mSfMax.setText("");
        mLotSizeMin.setText("");
        mLotSizeMax.setText("");
        mYearBuildMin.setText("");
        mYearBuildMax.setText("");
        mDayMax.setText("");
        mKeyword.setText("");
        mRangeSeekBar.setRangeValues(0, 5000000);
        mRangeSeekBar.setSelectedMinValue(0);
        mRangeSeekBar.setSelectedMaxValue(5000000);
        mBathRoom = 0;
        mBedRoom = 0;
        mPf = "";
        mListener.resetFilter();
        SingletonFilter.getInstance().clearFilter();
    }

    @OnClick(R.id.layoutBedAny)
    public void onLayoutBedAnyClicked() {
        if (mBedRoomApter != 0) {
            mBedRoom = mBedRoomApter;
            mBedRoomApter = 0;
            changeTextWithAnimation(mBedAny, mBedRoomApter, true);
        }
    }

    @OnClick(R.id.layoutBed1)
    public void onLayoutBed1Clicked() {
        if (mBedRoomApter != 1) {
            mBedRoom = mBedRoomApter;
            mBedRoomApter = 1;
            changeTextWithAnimation(mBed1, mBedRoomApter, true);
        }
    }

    @OnClick(R.id.layoutBed2)
    public void onLayoutBed2Clicked() {
        if (mBedRoomApter != 2) {
            mBedRoom = mBedRoomApter;
            mBedRoomApter = 2;
            changeTextWithAnimation(mBed2, mBedRoomApter, true);
        }
    }

    @OnClick(R.id.layoutBed3)
    public void onLayoutBed3Clicked() {
        if (mBedRoomApter != 3) {
            mBedRoom = mBedRoomApter;
            mBedRoomApter = 3;
            changeTextWithAnimation(mBed3, mBedRoomApter, true);
        }
    }

    @OnClick(R.id.layoutBed4)
    public void onLayoutBed4Clicked() {
        if (mBedRoomApter != 4) {
            mBedRoom = mBedRoomApter;
            mBedRoomApter = 4;
            changeTextWithAnimation(mBed4, mBedRoomApter, true);
        }
    }

    @OnClick(R.id.layoutBed5)
    public void onLayoutBed5Clicked() {
        if (mBedRoomApter != 5) {
            mBedRoom = mBedRoomApter;
            mBedRoomApter = 5;
            changeTextWithAnimation(mBed5, mBedRoomApter, true);
        }
    }

    @OnClick(R.id.layoutBathAny)
    public void onLayoutBathAnyClicked() {
        if (mBathRoomApter != 0) {
            mBathRoom = mBathRoomApter;
            mBathRoomApter = 0;
            changeTextWithAnimation(mBathAny, mBathRoomApter, false);
        }
    }

    @OnClick(R.id.layoutBath1)
    public void onLayoutBath1Clicked() {
        if (mBathRoomApter != 1) {
            mBathRoom = mBathRoomApter;
            mBathRoomApter = 1;
            changeTextWithAnimation(mBath1, mBathRoomApter, false);
        }
    }

    @OnClick(R.id.layoutBath2)
    public void onLayoutBath2Clicked() {
        if (mBathRoomApter != 2) {
            mBathRoom = mBathRoomApter;
            mBathRoomApter = 2;
            changeTextWithAnimation(mBath2, mBathRoomApter, false);
        }
    }

    @OnClick(R.id.layoutBath3)
    public void onLayoutBath3Clicked() {
        if (mBathRoomApter != 3) {
            mBathRoom = mBathRoomApter;
            mBathRoomApter = 3;
            changeTextWithAnimation(mBath3, mBathRoomApter, false);
        }
    }

    @OnClick(R.id.layoutBath4)
    public void onLayoutBath4Clicked() {
        if (mBathRoomApter != 4) {
            mBathRoom = mBathRoomApter;
            mBathRoomApter = 4;
            changeTextWithAnimation(mBath4, mBathRoomApter, false);
        }
    }

    @OnClick(R.id.layoutBath5)
    public void onLayoutBath5Clicked() {
        if (mBathRoomApter != 5) {
            mBathRoom = mBathRoomApter;
            mBathRoomApter = 5;
            changeTextWithAnimation(mBath5, mBathRoomApter, false);
        }
    }


    @OnClick(R.id.layoutTypeAll)
    public void onLayoutTypeAllClicked() {
        if (!mTypeAll) {
            changeButtonWithAnimation(mVTypeAll, mTvTypeAll, mTypeAll);
            mTypeAll = true;
            resetProperType();
        }
    }

    @OnClick(R.id.layoutTypeHouse)
    public void onLayoutTypeHouseClicked() {
        Log.e("mTypeHouse", String.valueOf(mTypeHouse));
        Log.e("mTypeAll", String.valueOf(mTypeAll));
        changeButtonWithAnimation(mVTypeHouse, mTvTypeHouse, mTypeHouse);
        mTypeHouse = !mTypeHouse;
        if (mTypeAll) {
            changeButtonWithAnimation(mVTypeAll, mTvTypeAll, mTypeAll);
            mTypeAll = false;
        }
        if (!mTypeHouse && !mTypeCondo && !mTypeTownhouse) {
            resetProperType();
        }
    }

    @OnClick(R.id.layoutTypeTownhouse)
    public void onLayoutTypeTownhouseClicked() {
        changeButtonWithAnimation(mVTypeTownhouse, mTvTypeTownhouse, mTypeTownhouse);
        mTypeTownhouse = !mTypeTownhouse;
        if (mTypeAll) {
            changeButtonWithAnimation(mVTypeAll, mTvTypeAll, mTypeAll);
            mTypeAll = false;
        }
        if (!mTypeHouse && !mTypeCondo && !mTypeTownhouse) {
            resetProperType();
        }
    }

    @OnClick(R.id.layoutTypeCondo)
    public void onLayoutTypeCondoClicked() {
        changeButtonWithAnimation(mVTypeCondo, mTvTypeCondo, mTypeCondo);
        mTypeCondo = !mTypeCondo;
        if (mTypeAll) {
            changeButtonWithAnimation(mVTypeAll, mTvTypeAll, mTypeAll);
            mTypeAll = false;
        }
        if (!mTypeHouse && !mTypeCondo && !mTypeTownhouse) {
            resetProperType();
        }
    }

    @OnClick(R.id.layoutTypeManufacturedHome)
    public void onLayoutTypeManufacturedHomeClicked() {
        changeButtonWithAnimation(mVTypeManufacturedHome, mTvTypeManufacturedHome, mTypeManufacturedHome);
        mTypeManufacturedHome = !mTypeManufacturedHome;

        if (mTypeAll) {
            changeButtonWithAnimation(mVTypeAll, mTvTypeAll, mTypeAll);
            mTypeAll = !mTypeAll;
        }
    }

    @OnClick(R.id.layoutTypeResident)
    public void onLayoutTypeResidentClicked() {
        changeButtonWithAnimation(mVTypeResident, mTvTypeResident, mTypeResident);
        mTypeResident = !mTypeResident;

        if (mTypeAll) {
            changeButtonWithAnimation(mVTypeAll, mTvTypeAll, mTypeAll);
            mTypeAll = !mTypeAll;
        }
    }

    @OnClick(R.id.layoutTypeResidentialIncome)
    public void onLayoutTypeResidentialIncomeClicked() {
        changeButtonWithAnimation(mVTypeResidentialIncome, mTvTypeResidentialIncome, mTypeResidentialIncome);
        mTypeResidentialIncome = !mTypeResidentialIncome;

        if (mTypeAll) {
            changeButtonWithAnimation(mVTypeAll, mTvTypeAll, mTypeAll);
            mTypeAll = !mTypeAll;
        }
    }

    @OnClick(R.id.layoutTypeResidentialLease)
    public void onLayoutTypeResidentialLeaseClicked() {
        changeButtonWithAnimation(mVTypeResidentialLease, mTvTypeResidentialLease, mTypeResidentialLease);
        mTypeResidentialLease = !mTypeResidentialLease;

        if (mTypeAll) {
            changeButtonWithAnimation(mVTypeAll, mTvTypeAll, mTypeAll);
            mTypeAll = !mTypeAll;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupLayoutDefault();
        mRangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                mMaxPrice.setText(Utils.getPriceFilter(Integer.parseInt(maxValue.toString())));
                mMinPrice.setText(Utils.getPriceFilter(Integer.parseInt(minValue.toString())));
            }
        });
        mRangeSeekBar.setNotifyWhileDragging(true);
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_filter;
    }

    public void setListener(IFilterListener mListener) {
        this.mListener = mListener;
    }

    private void resetProperType() {
        if (!mTypeAll) {
            changeButtonWithAnimation(mVTypeAll, mTvTypeAll, mTypeAll);
            mTypeAll = !mTypeAll;
        }

        if (mTypeHouse) {
            changeButtonWithAnimation(mVTypeHouse, mTvTypeHouse, mTypeHouse);
            mTypeHouse = !mTypeHouse;
        }
        if (mTypeTownhouse) {
            changeButtonWithAnimation(mVTypeTownhouse, mTvTypeTownhouse, mTypeTownhouse);
            mTypeTownhouse = !mTypeTownhouse;
        }
        if (mTypeCondo) {
            changeButtonWithAnimation(mVTypeCondo, mTvTypeCondo, mTypeCondo);
            mTypeCondo = !mTypeCondo;
        }
        if (mTypeManufacturedHome) {
            changeButtonWithAnimation(mVTypeManufacturedHome, mTvTypeManufacturedHome, mTypeManufacturedHome);
            mTypeManufacturedHome = !mTypeManufacturedHome;
        }
        if (mTypeResident) {
            changeButtonWithAnimation(mVTypeResident, mTvTypeResident, mTypeResident);
            mTypeResident = !mTypeResident;
        }
        if (mTypeResidentialIncome) {
            changeButtonWithAnimation(mVTypeResidentialIncome, mTvTypeResidentialIncome, mTypeResidentialIncome);
            mTypeResidentialIncome = !mTypeResidentialIncome;
        }
        if (mTypeResidentialLease) {
            changeButtonWithAnimation(mVTypeResidentialLease, mTvTypeResidentialLease, mTypeResidentialLease);
            mTypeResidentialLease = !mTypeResidentialLease;
        }
    }

    public void setupLayoutDefault() {
        mBedAny.setTextColor(getColor(R.color.colorWhite));
        mBathAny.setTextColor(getColor(R.color.colorWhite));
    }

    public int getColor(int color) {
        return ContextCompat.getColor(getActivity(), color);
    }

    public void changeStatusAndDisableClick(final View view, final boolean b) {
        view.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                view.setEnabled(true);
            }
        }, 200);
    }

    public void changeStatusAndDisableClick(final View view) {
        view.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
            }
        }, 200);
    }

    public void changeButton(View view, boolean b) {

        changeStatusAndDisableClick(view, b);
    }

    public void changeTextWithAnimation(TextView tv, int position, boolean bed) {
        changeTextColor(tv, true);
        if (bed) {
            if (mBedRoom == 0) {
                changeTextColor(mBedAny, false);
            }
            if (mBedRoom == 1) {
                changeTextColor(mBed1, false);
            }
            if (mBedRoom == 2) {
                changeTextColor(mBed2, false);
            }
            if (mBedRoom == 3) {
                changeTextColor(mBed3, false);
            }
            if (mBedRoom == 4) {
                changeTextColor(mBed4, false);
            }
            if (mBedRoom == 5) {
                changeTextColor(mBed5, false);
            }
            if (position == 0) {
                AnimUtils.moveView(mLayoutMoveBed, mXBedAny);
            }
            if (position == 1) {
                AnimUtils.moveView(mLayoutMoveBed, mXBed1);
            }
            if (position == 2) {
                AnimUtils.moveView(mLayoutMoveBed, mXBed2);
            }
            if (position == 3) {
                AnimUtils.moveView(mLayoutMoveBed, mXBed3);
            }
            if (position == 4) {
                AnimUtils.moveView(mLayoutMoveBed, mXBed4);
            }
            if (position == 5) {
                AnimUtils.moveView(mLayoutMoveBed, mXBed5);
            }
        } else {
            if (mBathRoom == 0) {
                changeTextColor(mBathAny, false);
            }
            if (mBathRoom == 1) {
                changeTextColor(mBath1, false);
            }
            if (mBathRoom == 2) {
                changeTextColor(mBath2, false);
            }
            if (mBathRoom == 3) {
                changeTextColor(mBath3, false);
            }
            if (mBathRoom == 4) {
                changeTextColor(mBath4, false);
            }
            if (mBathRoom == 5) {
                changeTextColor(mBath5, false);
            }
            if (position == 0) {
                AnimUtils.moveView(mLayoutMoveBath, mXBathAny);
            }
            if (position == 1) {
                AnimUtils.moveView(mLayoutMoveBath, mXBath1);
            }
            if (position == 2) {
                AnimUtils.moveView(mLayoutMoveBath, mXBath2);
            }
            if (position == 3) {
                AnimUtils.moveView(mLayoutMoveBath, mXBath3);
            }
            if (position == 4) {
                AnimUtils.moveView(mLayoutMoveBath, mXBath4);
            }
            if (position == 5) {
                AnimUtils.moveView(mLayoutMoveBath, mXBath5);
            }
        }

    }

    public void changeTextColor(TextView tv, boolean b) {
        Log.e("Cc", "Cc");
        if (b) {
            Log.e("Cccc", "Aa");
            AnimUtils.changeTextColor(getActivity(), R.color.colorTextMain, R.color.colorWhite, tv);
        } else {
            Log.e("Aeae", "AEae");
            AnimUtils.changeTextColor(getActivity(), R.color.colorWhite, R.color.colorTextMain, tv);
        }
    }

    public void changeButtonWithAnimation(View layout, ImageView img, TextView tv, final boolean isClick) {
        int fromColor;
        int toColor;
        int fromBg;
        int toBg;

        if (isClick) {
            fromColor = R.color.colorWhite;
            toColor = R.color.colorTextMain;
            fromBg = R.drawable.bg_button_filter;
            toBg = R.drawable.bg_button_filter1;
        } else {
            fromColor = R.color.colorTextMain;
            toColor = R.color.colorWhite;
            fromBg = R.drawable.bg_button_filter1;
            toBg = R.drawable.bg_button_filter;
        }
        AnimUtils.changeTextColor(getActivity(), fromColor, toColor, tv);
        AnimUtils.changeColorFilter(getActivity(), fromColor, toColor, img);
        AnimUtils.changeBackgroundDrawable(getActivity(), fromBg, toBg, layout);
    }

    public void changeButtonWithAnimation(View layout, TextView tv, final boolean isClick) {

        int fromColor;
        int toColor;
        int fromBg;
        int toBg;

        if (isClick) {
            fromColor = R.color.color_text_light_blue_filter;
            toColor = R.color.colorTextMain;
            fromBg = R.drawable.bg_button_filter;
            toBg = R.drawable.bg_button_filter2;
        } else {
            fromColor = R.color.colorTextMain;
            toColor = R.color.color_text_light_blue_filter;
            fromBg = R.drawable.bg_button_filter2;
            toBg = R.drawable.bg_button_filter;
        }
        AnimUtils.changeTextColor(getActivity(), fromColor, toColor, tv);
        AnimUtils.changeBackgroundDrawable(getActivity(), fromBg, toBg, layout);
    }

    public void getPositionAnimation(final boolean hasData) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mXBedAny = mLayoutBedAny.getX();
                mXBed1 = mLayoutBed1.getX();
                mXBed2 = mLayoutBed2.getX();
                mXBed3 = mLayoutBed3.getX();
                mXBed4 = mLayoutBed4.getX();
                mXBed5 = mLayoutBed5.getX();
                mXBathAny = mLayoutBathAny.getX();
                mXBath1 = mLayoutBath1.getX();
                mXBath2 = mLayoutBath2.getX();
                mXBath3 = mLayoutBath3.getX();
                mXBath4 = mLayoutBath4.getX();
                mXBath5 = mLayoutBath5.getX();
                Log.e("hasData", String.valueOf(hasData));
                Log.e("mHasDataSearch", String.valueOf(mHasDataSearch));
                if (hasData && !mHasDataSearch) {
                    handlerConditionSaveSearchDetail();
                }
            }
        });
    }

    public void closeLayout() {
        //resetFilter();
        loadFilterData();
        mScrollView.post(new Runnable() {
            @Override
            public void run() {
                mScrollView.scrollTo(0, 0);
            }
        });
        hideKeyboard();
    }

    public void showLayout(final boolean hasData) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mScrollView.setVisibility(View.VISIBLE);
                getPositionAnimation(hasData);
            }
        }, 100);

    }

    public void setPt(String rawPt) {
        onLayoutTypeAllClicked();
        String[] arr = rawPt.split(",");
        for (String s : arr) {
            if (s.contains("house")) {
                onLayoutTypeHouseClicked();
                Log.e("house", "house");
            } else if (s.contains("townhouse")) {
                onLayoutTypeTownhouseClicked();
                Log.e("townhouse", "townhouse");
            } else if (s.contains("condo")) {
                onLayoutTypeCondoClicked();
                Log.e("condo", "condo");
            } else if (s.contains("manufactured")) {
                onLayoutTypeManufacturedHomeClicked();
                Log.e("manufactured", "manufactured");
            } else if (s.contains("resident")) {
                onLayoutTypeResidentClicked();
                Log.e("resident", "resident");
            } else if (s.contains("residentialIncome")) {
                onLayoutTypeResidentialIncomeClicked();
                Log.e("residentialIncome", "residentialIncome");
            } else if (s.contains("residentialLease")) {
                onLayoutTypeResidentialLeaseClicked();
                Log.e("residentialLease", "residentialLease");
            } else {
                Log.e("cc", "onLayoutTypeAllClicked");
                onLayoutTypeAllClicked();
            }
        }

    }

    public void loadFilterData() {

        if (SingletonFilter.getInstance().getPt() != null) {
            if (!SingletonFilter.getInstance().getPt().isEmpty()) {
                setPt(SingletonFilter.getInstance().getPt());
            }
        } else {
            Log.e("loadFilterData", "loadFilterData");
            onLayoutTypeAllClicked();
        }
        if (SingletonFilter.getInstance().getMaxPrice() != null) {
            if (!SingletonFilter.getInstance().getMaxPrice().isEmpty()) {
                mRangeSeekBar.setSelectedMaxValue((int) (Double.parseDouble(SingletonFilter.getInstance().getMaxPrice())));
                mMaxPrice.setText(Utils.getPriceFilter((int) (Double.parseDouble(SingletonFilter.getInstance().getMaxPrice()))));
            }
        }
        if (SingletonFilter.getInstance().getMinPrice() != null) {
            if (!SingletonFilter.getInstance().getMinPrice().isEmpty()) {
                mRangeSeekBar.setSelectedMinValue((int) (Double.parseDouble(SingletonFilter.getInstance().getMinPrice())));
                mMinPrice.setText(Utils.getPriceFilter((int) (Double.parseDouble(SingletonFilter.getInstance().getMinPrice()))));
            }
        }

        if (SingletonFilter.getInstance().getBed() != null) {
            if (!SingletonFilter.getInstance().getBed().isEmpty()) {
                switch (Integer.parseInt(SingletonFilter.getInstance().getBed())) {
                    case 1:
                        onLayoutBed1Clicked();
                        Log.e("onLayoutBed1Clicked", "onLayoutBed1Clicked");
                        break;
                    case 2:
                        onLayoutBed2Clicked();
                        Log.e("onLayoutBed2Clicked", "onLayoutBed2Clicked");
                        break;
                    case 3:
                        onLayoutBed3Clicked();
                        Log.e("onLayoutBed3Clicked", "onLayoutBed3Clicked");
                        break;
                    case 4:
                        onLayoutBed4Clicked();
                        Log.e("onLayoutBed4Clicked", "onLayoutBed4Clicked");
                        break;
                    case 5:
                        onLayoutBed5Clicked();
                        Log.e("onLayoutBed5Clicked", "onLayoutBed5Clicked");
                        break;

                }
            }
        }
        if (SingletonFilter.getInstance().getBath() != null) {
            if (!SingletonFilter.getInstance().getBath().isEmpty()) {
                switch (Integer.parseInt(SingletonFilter.getInstance().getBath())) {

                    case 1:
                        onLayoutBath1Clicked();
                        break;
                    case 2:
                        onLayoutBath2Clicked();
                        break;
                    case 3:
                        onLayoutBath3Clicked();
                        break;
                    case 4:
                        onLayoutBath4Clicked();
                        break;
                    case 5:
                        onLayoutBath5Clicked();
                        break;

                }
            }
        }

        if (SingletonFilter.getInstance().getMaxLs() != null) {
            if (!SingletonFilter.getInstance().getMaxLs().isEmpty()) {
                mLotSizeMax.setText(SingletonFilter.getInstance().getMaxLs());
            }
        }
        if (SingletonFilter.getInstance().getMinLs() != null) {
            if (!SingletonFilter.getInstance().getMinLs().isEmpty()) {
                mLotSizeMin.setText(SingletonFilter.getInstance().getMinLs());
            }
        }
        if (SingletonFilter.getInstance().getMaxYb() != null) {
            if (!SingletonFilter.getInstance().getMaxYb().isEmpty()) {
                mYearBuildMax.setText(SingletonFilter.getInstance().getMaxYb());
            }
        }
        if (SingletonFilter.getInstance().getMinYb() != null) {
            if (!SingletonFilter.getInstance().getMinYb().isEmpty()) {
                mYearBuildMin.setText(SingletonFilter.getInstance().getMinYb());
            }
        }
        if (SingletonFilter.getInstance().getMaxLsf() != null) {
            if (!SingletonFilter.getInstance().getMaxLsf().isEmpty()) {
                mSfMax.setText(SingletonFilter.getInstance().getMaxLsf());
            }
        }
        if (SingletonFilter.getInstance().getMinLsf() != null) {
            if (!SingletonFilter.getInstance().getMinLsf().isEmpty()) {
                mSfMin.setText(SingletonFilter.getInstance().getMinLsf());
            }
        }
        if (SingletonFilter.getInstance().getKeyword() != null) {
            if (!SingletonFilter.getInstance().getKeyword().isEmpty()) {
                mKeyword.setText(SingletonFilter.getInstance().getKeyword());
            }
        }
        if (SingletonFilter.getInstance().getDc() != null) {
            if (!SingletonFilter.getInstance().getDc().isEmpty()) {
                mDayMax.setText(SingletonFilter.getInstance().getDc());
            }
        }


        mLotSizeMax.clearFocus();
        mLotSizeMin.clearFocus();
        mKeyword.clearFocus();
        mYearBuildMax.clearFocus();
        mYearBuildMin.clearFocus();
        mDayMax.clearFocus();
        mSfMin.clearFocus();
        mSfMax.clearFocus();
    }

    public void handlerConditionSaveSearchDetail() {
        mHasDataSearch = true;
        ConditionFull conditionFull = CurrentSaveSearch.getInstance().getSearchDetail().getConditions().get(0);

        Log.e("ConditionFull", conditionFull.toString());
        if (conditionFull.getPt() != null) {
            if (!conditionFull.getPt().isEmpty()) {
                SingletonFilter.getInstance().setPt(mPf);
            }
        } else {
            onLayoutTypeAllClicked();
        }
        if (conditionFull.getMaxPrice() != null) {
            if (!conditionFull.getMaxPrice().isEmpty()) {
                SingletonFilter.getInstance().setMaxPrice(conditionFull.getMaxPrice());
            }
        }
        if (conditionFull.getMinPrice() != null) {
            if (!conditionFull.getMinPrice().isEmpty()) {
                SingletonFilter.getInstance().setMinPrice(conditionFull.getMinPrice());
            }
        }

        if (conditionFull.getBr() != null) {
            if (!conditionFull.getBr().isEmpty()) {
                SingletonFilter.getInstance().setBed(conditionFull.getBr());
            }
        }
        if (conditionFull.getAr() != null) {
            if (!conditionFull.getAr().isEmpty()) {
                SingletonFilter.getInstance().setBath(conditionFull.getAr());
            }
        }

        if (conditionFull.getMaxLs() != null) {
            if (!conditionFull.getMaxLs().isEmpty()) {
                SingletonFilter.getInstance().setMinLs(conditionFull.getMinLs());
            }
        }
        if (conditionFull.getMinLs() != null) {
            if (!conditionFull.getMinLs().isEmpty()) {
                SingletonFilter.getInstance().setMaxLs(conditionFull.getMaxLs());
            }
        }
        if (conditionFull.getMaxYb() != null) {
            if (!conditionFull.getMaxYb().isEmpty()) {
                SingletonFilter.getInstance().setMaxYb(conditionFull.getMaxYb());
            }
        }
        if (conditionFull.getMinYb() != null) {
            if (!conditionFull.getMinYb().isEmpty()) {
                SingletonFilter.getInstance().setMinYb(conditionFull.getMinYb());
            }
        }
        if (conditionFull.getMaxLsf() != null) {
            if (!conditionFull.getMaxLsf().isEmpty()) {
                SingletonFilter.getInstance().setMaxLsf(conditionFull.getMaxLsf());
            }
        }
        if (conditionFull.getMinLsf() != null) {
            if (!conditionFull.getMinLsf().isEmpty()) {
                SingletonFilter.getInstance().setMinLsf(conditionFull.getMinLsf());
            }
        }
        if (conditionFull.getKeyword() != null) {
            if (!conditionFull.getKeyword().isEmpty()) {
                SingletonFilter.getInstance().setKeyword(conditionFull.getKeyword());
            }
        }
        if (conditionFull.getDc() != null) {
            if (!conditionFull.getDc().isEmpty()) {
                SingletonFilter.getInstance().setDc(conditionFull.getDc());
            }
        }


        mLotSizeMax.clearFocus();
        mLotSizeMin.clearFocus();
        mKeyword.clearFocus();
        mYearBuildMax.clearFocus();
        mYearBuildMin.clearFocus();
        mDayMax.clearFocus();
        mSfMin.clearFocus();
        mSfMax.clearFocus();
        loadFilterData();
    }

    public void scrollDown(final boolean b) {
        mScrollView.post(new Runnable() {
            @Override
            public void run() {
                if (!b) {
                    AnimUtils.scrolling(mScrollView, (int) mTvScroll.getY());
                } else {
                    AnimUtils.scrolling(mScrollView, (int) mScrollView.getHeight());
                }
            }
        });
    }

}
