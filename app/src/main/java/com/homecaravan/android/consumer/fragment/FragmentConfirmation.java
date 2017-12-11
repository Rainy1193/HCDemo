package com.homecaravan.android.consumer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.activity.BookSingleActivity;
import com.homecaravan.android.consumer.adapter.AstDaySelectAdapter;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.listener.IBookSingleListener;
import com.homecaravan.android.consumer.model.AstDay;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.models.ListingDetailDataAgent;
import com.homecaravan.android.ui.CircleImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;

public class FragmentConfirmation extends BaseFragment {
    private IBookSingleListener mListener;
    private AstDaySelectAdapter mAstDayAdapter;
    private ArrayList<AstDay> mArrAst = new ArrayList<>();
    @Bind(R.id.ivListing)
    ImageView mIvListing;
    @Bind(R.id.tvAddress1)
    TextView mTvAddress1;
    @Bind(R.id.tvAddress2)
    TextView mTvAddress2;
    @Bind(R.id.ivAgent)
    CircleImageView mIvAgent;
    @Bind(R.id.tvAgent)
    TextView mTvAgent;
    @Bind(R.id.tvCompany)
    TextView mTvCompany;
    @Bind(R.id.tvDate)
    TextView mTvDate;
    @Bind(R.id.tvTime)
    TextView mTvTime;
    @Bind(R.id.layoutDateTime)
    LinearLayout mLayoutDateTime;
    @Bind(R.id.rvDateTime)
    RecyclerView mRvDateTime;

    public void setListener(IBookSingleListener mListener) {
        this.mListener = mListener;
    }

    @OnClick(R.id.layoutBackDate)
    public void backDate() {
        mListener.backDate();
    }


    @OnClick(R.id.layoutBackTime)
    public void backTime() {
        mListener.backTime();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAstDayAdapter = new AstDaySelectAdapter(mArrAst, getActivity());
        mRvDateTime.setAdapter(mAstDayAdapter);
        mRvDateTime.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_comfirmation;
    }


    public void updateData() {
        Glide.with(this).load(BookSingleActivity.sListingData.getListingDetailDataListing().getImgName())
                .asBitmap().placeholder(R.drawable.ic_placeholder_listing_consumer).fitCenter().into(mIvListing);
        mTvAddress1.setText(BookSingleActivity.sListingData.getListingDetailDataListing().getLkey());
        mTvAddress2.setText(BookSingleActivity.sListingData.getListingDetailDataListing().getCity() + ", " +
                BookSingleActivity.sListingData.getListingDetailDataListing().getState() + " " +
                BookSingleActivity.sListingData.getListingDetailDataListing().getZip());
        if (BookSingleActivity.sListingData.getListingDetailDataAgent() != null) {
            ListingDetailDataAgent agent = BookSingleActivity.sListingData.getListingDetailDataAgent();
            Glide.with(this).load(agent.getPhotoAgent())
                    .asBitmap().placeholder(R.drawable.avatar_default).fitCenter().into(mIvAgent);
            mTvAgent.setText(agent.getFirstNameAgent() + " " + agent.getLastNameAgent());
            if (agent.getTitle() != null) {
                mTvCompany.setText(agent.getTitle());
            }
        }
    }

    public void setDate(String date) {
        mLayoutDateTime.setVisibility(View.VISIBLE);
        mRvDateTime.setVisibility(View.GONE);
        DateFormat simpleDateFormat = new SimpleDateFormat("dd, MMMM", Locale.US);
        mTvDate.setText(simpleDateFormat.format(Utils.createDateFromString1(date).getTime()));
    }

    public void setTime(String time, String timeStart, String timeEnd) {
        mLayoutDateTime.setVisibility(View.VISIBLE);
        mRvDateTime.setVisibility(View.GONE);
        mTvTime.setText(time);
    }


    public void setDateTimeAst(ArrayList<AstDay> astDay) {
        Log.e("Ccc", astDay.toString());
        mLayoutDateTime.setVisibility(View.GONE);
        mRvDateTime.setVisibility(View.VISIBLE);
        mArrAst.clear();
        mArrAst.addAll(astDay);
        mRvDateTime.getAdapter().notifyDataSetChanged();
    }
}
