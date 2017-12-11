package com.homecaravan.android.consumer.consumershowing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.adapter.CaravanActionAdapter;
import com.homecaravan.android.consumer.adapter.CaravanActionTimeAdapter;
import com.homecaravan.android.consumer.adapter.ViewPagerAdapter;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.listener.IPageChange;
import com.homecaravan.android.consumer.listener.IShowingListener;
import com.homecaravan.android.consumer.listener.ShowingMainListener;
import com.homecaravan.android.consumer.model.responseapi.ResponseCaravan;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.consumer.widget.CustomViewPager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class FragmentShowing extends BaseFragment implements
        IShowingListener,
        ShowingMainListener {

    private ViewPagerAdapter mViewPagerAdapter;
    private IPageChange mListener;

    private CaravanActionTimeAdapter mTimeAdapter;
    private CaravanActionAdapter mListingAdapter;
    private ArrayList<String> mArrTime = new ArrayList<>();
    private ArrayList<ResponseCaravan.CaravanDestinations> mArrListing = new ArrayList<>();
    private FragmentScheduleUpcoming mScheduleUpcoming;
    private FragmentPastReviews mPastReviews;
    private boolean mShowCaravanAction;
    private boolean mInitData;

    public void setPageChange(IPageChange mListener) {
        this.mListener = mListener;
    }


    @Bind(R.id.layoutShowing)
    RelativeLayout mLayoutShowing;

    @Bind(R.id.vpShowing)
    CustomViewPager mViewPager;

    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;

    @Bind(R.id.frmActionSort)
    RelativeLayout mFrmActionSort;

    @Bind(R.id.layoutSort)
    LinearLayout mLayoutSort;

    @Bind(R.id.layoutSortContent)
    RelativeLayout mLayoutSortContent;

    @OnClick(R.id.lnNewestToOldest)
    public void onLnNewestToOldest() {
        closeSort();
    }

    @OnClick(R.id.lnOldestToNewest)
    public void onLnOldestToNewest() {
        closeSort();
    }

    @OnClick(R.id.lnByCaravan)
    public void onLnByCaravan() {
        closeSort();
    }

    @OnClick(R.id.lnByAppointments)
    public void onLnByAppointments() {
        closeSort();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        mScheduleUpcoming = new FragmentScheduleUpcoming();
        mScheduleUpcoming.setListener(this);
        mScheduleUpcoming.setShowingMainListener(this);
        mPastReviews = new FragmentPastReviews();
        mViewPagerAdapter.addFragment(mScheduleUpcoming, "Schedule/Upcoming");
        mViewPagerAdapter.addFragment(mPastReviews, "Past/Reviews");
        mTabLayout.setupWithViewPager(mViewPager);
        if(mListener != null){
            mListener.loadFragmentCompleted(3);
        }
    }


    @OnClick(R.id.layoutSort)
    public void onLayoutSort() {
        closeSort();
    }

    @OnClick(R.id.frmActionSort)
    public void sort() {
        if (mLayoutSort.getVisibility() == View.VISIBLE) {
            AnimUtils.collapseView(mLayoutSortContent, mLayoutSort);
        } else {
            mLayoutSort.setVisibility(View.VISIBLE);
            AnimUtils.expandView(mLayoutSortContent);
        }
    }

    public void closeSort() {
        if (mLayoutSort.getVisibility() == View.VISIBLE) {
            AnimUtils.collapseView(mLayoutSortContent, mLayoutSort);
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_consumer_showing;
    }

    public void initData() {
        if (!mInitData) {
            mViewPager.setAdapter(mViewPagerAdapter);
            mInitData = true;
        }
    }


    public void onSubmitSchedule(String day) {
        mScheduleUpcoming.getNewCaravan(day);
        initData();
    }

//    public void initList() {
//        mArrListing.clear();
//        mArrTime.clear();
//        ArrayList<ResponseCaravan.CaravanDestinations> arrListing = CurrentCaravan.getCaravan().getData().getDestinations();
//        mArrListing.addAll(arrListing);
//        for (int i = 0; i < mArrListing.size() - 1; i++) {
//            mArrTime.add(String.valueOf(Integer.parseInt(mArrListing.get(i).getDestinationsDuration().getValue()) / 60) + " mins");
//        }
//        mListingAdapter = new CaravanActionAdapter(mArrListing, getActivity());
//        mTimeAdapter = new CaravanActionTimeAdapter(mArrTime, getActivity());
//        mRvTime.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        mRvTime.setAdapter(mTimeAdapter);
//        mRvListing.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        mRvListing.setAdapter(mListingAdapter);
//    }

//    public void initMap() {
//        IconGenerator iconFactory = new IconGenerator(getActivity());
//        mGoogleMap.clear();
//        for (int i = 0; i < mArrListing.size(); i++) {
//            addStopCaravan(iconFactory, mArrListing.get(i));
//        }
//    }

//    public void addStopCaravan(IconGenerator iconFactory, ResponseCaravan.CaravanDestinations listing) {
//        LatLng latLng = new LatLng(listing.getListing().getAddress().getLat(),
//                listing.getListing().getAddress().getLng());
//        iconFactory.setBackground(null);
//        View view = LayoutInflater.from(getActivity()).inflate(R.layout.marker_caravan_action, null, false);
//        CircleImageView avatarAgent = (CircleImageView) view.findViewById(R.id.ivAgent);
//        Glide.with(getActivity().getApplication()).load(R.drawable.sana).asBitmap().fitCenter().dontAnimate()
//                .placeholder(R.drawable.avatar_default).into(avatarAgent);
//        TextView nameAgent = (TextView) view.findViewById(R.id.tvNameAgent);
//        nameAgent.setText("Sana");
//        iconFactory.setContentView(view);
//        addMarker(iconFactory, latLng);
//
//    }

//    private Marker addMarker(IconGenerator iconFactory, LatLng position) {
//        Bitmap bitmap = iconFactory.makeIcon();
//        MarkerOptions markerOptions = new MarkerOptions().
//                icon(BitmapDescriptorFactory.fromBitmap(bitmap)).
//                position(position).
//                anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());
//
//        return mGoogleMap.addMarker(markerOptions);
//    }



    @Override
    public void showCaravanAction() {

    }

    @Override
    public void onMessageClicked(String apptId, String listingId, String caravanId, String title, String userId) {

    }

    @Override
    public void editCaravan() {
        mListener.editCaravanFromShowing();
    }

    public void hideCaravanAction(boolean animation) {

    }


    public void resetFragment() {
        mLayoutSort.setVisibility(View.GONE);
        mLayoutSortContent.setVisibility(View.GONE);
    }

    public boolean isShowCaravanAction() {
        return mShowCaravanAction;
    }

    @Override
    public void showLayout() {
        mLayoutShowing.setVisibility(View.VISIBLE);
    }


    public void checkToShowPopupWhenCurrentItemIsShowing(){
        mScheduleUpcoming.checkToShowPopupWhenCurrentItemIsShowing();
    }
}
