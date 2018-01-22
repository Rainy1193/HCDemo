package com.homecaravan.android.consumer.consumershowing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.activity.ConversationActivity;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumerbase.ConsumerUser;
import com.homecaravan.android.consumer.consumermvp.showingmvp.GetListShowingPresenter;
import com.homecaravan.android.consumer.consumermvp.showingmvp.GetListShowingView;
import com.homecaravan.android.consumer.fastadapter.AppointmentCaravanItem;
import com.homecaravan.android.consumer.fastadapter.CaravanShowingItem;
import com.homecaravan.android.consumer.fastadapter.SingleAppointmentItem;
import com.homecaravan.android.consumer.listener.IShowingListener;
import com.homecaravan.android.consumer.message.messagegetthreadidmvp.GetThreadIdPresenter;
import com.homecaravan.android.consumer.message.messagegetthreadidmvp.IGetThreadIdView;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.responseapi.AppointmentShowing;
import com.homecaravan.android.consumer.model.responseapi.CaravanShowing;
import com.homecaravan.android.consumer.model.responseapi.ResponseCaravan;
import com.homecaravan.android.consumer.model.responseapi.ResponseMessageGetThreadId;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.consumer.widget.CustomNestedScrollView;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;

import java.util.ArrayList;

import butterknife.Bind;


public class FragmentPastReviews extends BaseFragment implements GetListShowingView, IShowingListener, IGetThreadIdView {

    private FastItemAdapter<SingleAppointmentItem> mSingleAdapter;
    private FastItemAdapter<CaravanShowingItem> mCaravanAdapter;
    private FastItemAdapter<AppointmentCaravanItem> mApptCaravanAdapter;
    private GetListShowingPresenter mGetListCaravanPresenter;
    private GetListShowingPresenter mGetListApptPresenter;
    private GetThreadIdPresenter mGetThreadIdPresenter;

    @Bind(R.id.layoutMain)
    FrameLayout mLayoutMain;
    @Bind(R.id.rvCaravan)
    RecyclerView mRvCaravan;
    @Bind(R.id.rvPastSingleShowings)
    RecyclerView mRvPastSingleShowings;
    @Bind(R.id.scrollView)
    CustomNestedScrollView mScrollView;
    @Bind(R.id.layoutEmpty)
    LinearLayout mLayoutEmpty;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCaravanAdapter = new FastItemAdapter<>();
        mSingleAdapter = new FastItemAdapter<>();
        mRvCaravan.setLayoutManager(createLayoutManagerVertical());
        mRvCaravan.setAdapter(mCaravanAdapter);
        mRvPastSingleShowings.setAdapter(mSingleAdapter);
        mRvPastSingleShowings.setLayoutManager(createLayoutManagerVertical());
        mGetThreadIdPresenter = new GetThreadIdPresenter(this);
        mGetListCaravanPresenter = new GetListShowingPresenter(this);
        mGetListApptPresenter = new GetListShowingPresenter(this);
        mGetListCaravanPresenter.getListShowing(Utils.getPreviousMonth(), Utils.getCurrentMonthMinusOneDay(), "caravan");
        mGetListApptPresenter.getListShowing(Utils.getPreviousMonth(), Utils.getCurrentMonthMinusOneDay(), "single");
    }


    public LinearLayoutManager createLayoutManagerVertical() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_past_reviews;
    }


    @Override
    public void getShowingApptSuccess(ArrayList<AppointmentShowing> appointments) {
        for (int i = 0; i < appointments.size(); i++) {
            SingleAppointmentItem singleAppointmentItem = new SingleAppointmentItem();
            singleAppointmentItem.setContext(getActivity());
            singleAppointmentItem.setAppointment(appointments.get(i));
            singleAppointmentItem.setListener(this);
            mSingleAdapter.add(singleAppointmentItem);
        }
    }

    @Override
    public void getShowingCaravanSuccess(ArrayList<CaravanShowing> caravans) {
        if (caravans.isEmpty()) {
            mLayoutEmpty.setVisibility(View.VISIBLE);
            mScrollView.setVisibility(View.GONE);
        } else {
            mLayoutEmpty.setVisibility(View.GONE);
            mScrollView.setVisibility(View.VISIBLE);
        }
        int count = 0;
        for (int i = 0; i < caravans.size(); i++) {
            CaravanShowingItem caravanItem = new CaravanShowingItem();
            caravanItem.setPast(true);
            caravanItem.setContext(getActivity());
            caravanItem.setCaravan(caravans.get(i));
            caravans.get(i).setPast(true);
            caravanItem.setListener(this);
            ArrayList<ResponseCaravan.CaravanDestinations> destinations = caravans.get(i).getRefCaravan().getDestinations();
            mApptCaravanAdapter = new FastItemAdapter<>();
            for (int j = 0; j < destinations.size(); j++) {
                if(destinations.get(j).getAppointment()!=null) {
                    AppointmentCaravanItem apptCaravanItem = new AppointmentCaravanItem();
                    apptCaravanItem.setContext(getActivity());
                    apptCaravanItem.setDestinations(destinations.get(j).getAppointment());
                    apptCaravanItem.setPosition(j);
                    mApptCaravanAdapter.add(apptCaravanItem);
                }
            }
            caravanItem.setApptAdapter(mApptCaravanAdapter);
            mCaravanAdapter.add(caravanItem);
            count++;
            if (count == 20) {
                return;
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
    public void showCaravanAction() {

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
