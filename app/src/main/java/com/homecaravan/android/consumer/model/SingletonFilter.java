package com.homecaravan.android.consumer.model;

public class SingletonFilter {
    public static SingletonFilter mSingletonFilter;
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
    private String mNe = "";
    private String mSw = "";

    public static SingletonFilter getInstance() {
        if (mSingletonFilter == null) {
            mSingletonFilter = new SingletonFilter();
        }
        return mSingletonFilter;
    }

    public String getFt() {
        return mFt;
    }

    public void setFt(String mFt) {
        this.mFt = mFt;
    }

    public String getMaxPrice() {
        return mMaxPrice;
    }

    public void setMaxPrice(String mMaxPrice) {
        this.mMaxPrice = mMaxPrice;
    }

    public String getMinPrice() {
        return mMinPrice;
    }

    public void setMinPrice(String mMinPrice) {
        this.mMinPrice = mMinPrice;
    }

    public String getBed() {
        return mBed;
    }

    public void setBed(String mBed) {
        this.mBed = mBed;
    }

    public String getBath() {
        return mBath;
    }

    public void setBath(String mBath) {
        this.mBath = mBath;
    }

    public String getMinLs() {
        return mMinLs;
    }

    public void setMinLs(String mMinLs) {
        this.mMinLs = mMinLs;
    }

    public String getMaxLs() {
        return mMaxLs;
    }

    public void setMaxLs(String mMaxLs) {
        this.mMaxLs = mMaxLs;
    }

    public String getMinLsf() {
        return mMinLsf;
    }

    public void setMinLsf(String mMinLsf) {
        this.mMinLsf = mMinLsf;
    }

    public String getMaxLsf() {
        return mMaxLsf;
    }

    public void setMaxLsf(String mMaxLsf) {
        this.mMaxLsf = mMaxLsf;
    }

    public String getMinYb() {
        return mMinYb;
    }

    public void setMinYb(String mMinYb) {
        this.mMinYb = mMinYb;
    }

    public String getMaxYb() {
        return mMaxYb;
    }

    public void setMaxYb(String mMaxYb) {
        this.mMaxYb = mMaxYb;
    }

    public String getPt() {
        return mPt;
    }

    public void setPt(String mPt) {
        this.mPt = mPt;
    }

    public String getKeyword() {
        return mKeyword;
    }

    public void setKeyword(String mKeyword) {
        this.mKeyword = mKeyword;
    }

    public String getDc() {
        return mDc;
    }

    public void setDc(String mDc) {
        this.mDc = mDc;
    }

    public String getNe() {
        return mNe;
    }

    public void setNe(String mNe) {
        this.mNe = mNe;
    }

    public String getSw() {
        return mSw;
    }

    public void setSw(String mSw) {
        this.mSw = mSw;
    }

    @Override
    public String toString() {
        return "SingletonFilter{" +
                "mFt='" + mFt + '\'' +
                ", mMaxPrice='" + mMaxPrice + '\'' +
                ", mMinPrice='" + mMinPrice + '\'' +
                ", mBed='" + mBed + '\'' +
                ", mBath='" + mBath + '\'' +
                ", mMinLs='" + mMinLs + '\'' +
                ", mMaxLs='" + mMaxLs + '\'' +
                ", mMinLsf='" + mMinLsf + '\'' +
                ", mMaxLsf='" + mMaxLsf + '\'' +
                ", mMinYb='" + mMinYb + '\'' +
                ", mMaxYb='" + mMaxYb + '\'' +
                ", mPt='" + mPt + '\'' +
                ", mKeyword='" + mKeyword + '\'' +
                ", mDc='" + mDc + '\'' +
                ", mNe='" + mNe + '\'' +
                ", mSw='" + mSw + '\'' +
                '}';
    }

    public void clearFilter() {
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
        mNe = "";
        mSw = "";
    }
}
