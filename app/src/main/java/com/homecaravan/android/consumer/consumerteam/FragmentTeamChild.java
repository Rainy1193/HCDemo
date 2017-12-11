package com.homecaravan.android.consumer.consumerteam;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.activity.TeamTabSearchActivity;
import com.homecaravan.android.consumer.adapter.FeaturedAgentAdapter;
import com.homecaravan.android.consumer.adapter.HomeInspectorFeaturedAdapter;
import com.homecaravan.android.consumer.adapter.HomeInspectorMyResourceAdapter;
import com.homecaravan.android.consumer.adapter.LenderFeaturedAdapter;
import com.homecaravan.android.consumer.adapter.LenderMyResourceAdapter;
import com.homecaravan.android.consumer.adapter.RealtorFeaturedAdapter;
import com.homecaravan.android.consumer.adapter.RealtorMyResourceAdapter;
import com.homecaravan.android.consumer.adapter.TitleInsuranceAdapter;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumerbase.ConsumerUser;
import com.homecaravan.android.consumer.consumermvp.loginmvp.GetFeaturedPresenter;
import com.homecaravan.android.consumer.consumermvp.loginmvp.GetFeaturedView;
import com.homecaravan.android.consumer.consumermvp.loginmvp.SetAgentPresenter;
import com.homecaravan.android.consumer.consumermvp.loginmvp.SetAgentView;
import com.homecaravan.android.consumer.consumerteam.homeinspectorfeaturedmvp.HomeInspectorFeaturedHelper;
import com.homecaravan.android.consumer.consumerteam.homeinspectorfeaturedmvp.HomeInspectorFeaturedPresenter;
import com.homecaravan.android.consumer.consumerteam.homeinspectorfeaturedmvp.IHomeInspectorFeaturedView;
import com.homecaravan.android.consumer.consumerteam.homeinspectormyresourcemvp.HomeInspectorMyResourceHelper;
import com.homecaravan.android.consumer.consumerteam.homeinspectormyresourcemvp.HomeInspectorMyResourcePresenter;
import com.homecaravan.android.consumer.consumerteam.homeinspectormyresourcemvp.IHomeInspectorMyResourceView;
import com.homecaravan.android.consumer.consumerteam.lenderfeaturedmvp.ILenderFeaturedView;
import com.homecaravan.android.consumer.consumerteam.lenderfeaturedmvp.LenderFeaturedHelper;
import com.homecaravan.android.consumer.consumerteam.lenderfeaturedmvp.LenderFeaturedPresenter;
import com.homecaravan.android.consumer.consumerteam.lendermyresourcemvp.ILenderMyResourceView;
import com.homecaravan.android.consumer.consumerteam.lendermyresourcemvp.LenderMyResourceHelper;
import com.homecaravan.android.consumer.consumerteam.lendermyresourcemvp.LenderMyResourcePresenter;
import com.homecaravan.android.consumer.consumerteam.realtorfeaturedmvp.IRealtorFeaturedView;
import com.homecaravan.android.consumer.consumerteam.realtorfeaturedmvp.RealtorFeaturedHelper;
import com.homecaravan.android.consumer.consumerteam.realtorfeaturedmvp.RealtorFeaturedPresenter;
import com.homecaravan.android.consumer.consumerteam.realtormyresourcemvp.IRealtorMyResourceView;
import com.homecaravan.android.consumer.consumerteam.realtormyresourcemvp.RealtorMyResourceHelper;
import com.homecaravan.android.consumer.consumerteam.realtormyresourcemvp.RealtorMyResourcePresenter;
import com.homecaravan.android.consumer.listener.IAgentListener;
import com.homecaravan.android.consumer.listener.IPageChangeMyTeam;
import com.homecaravan.android.consumer.listener.ITeamListener;
import com.homecaravan.android.consumer.listener.TeamMainListener;
import com.homecaravan.android.consumer.model.BaseDataRecyclerView;
import com.homecaravan.android.consumer.model.ConsumerTeam;
import com.homecaravan.android.consumer.model.EventAgentDetail;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.responseapi.ResponseFeatured;
import com.homecaravan.android.consumer.utils.Convert;
import com.homecaravan.android.ui.CircleImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;


public class FragmentTeamChild extends BaseFragment implements IRealtorFeaturedView, IRealtorMyResourceView,
        ILenderFeaturedView, ILenderMyResourceView,
        IHomeInspectorFeaturedView, IHomeInspectorMyResourceView,
        GetFeaturedView, IAgentListener, SetAgentView {

    private ITeamListener mHistoryListener;
    private TeamMainListener mTeamMainListener;

    public void setTeamMainListener(TeamMainListener mTeamMainListener) {
        this.mTeamMainListener = mTeamMainListener;
    }

    public void setHistoryListener(ITeamListener mHistoryListener) {
        this.mHistoryListener = mHistoryListener;
    }

    private ResponseFeatured.Featured mFeaturedAgent;
    private SetAgentPresenter mSetAgentPresenter;
    //Adapter
    private RealtorFeaturedAdapter mRealtorFeaturedAdapter;
    private RealtorMyResourceAdapter mRealtorMyResourceAdapter;
    private LenderFeaturedAdapter mLenderFeaturedAdapter;
    private LenderMyResourceAdapter mLenderMyResourceAdapter;
    private HomeInspectorFeaturedAdapter mHomeInspectorFeaturedAdapter;
    private HomeInspectorMyResourceAdapter mHomeInspectorMyResourceAdapter;
    private TitleInsuranceAdapter mTitleInsuranceAdapter;
    private TitleInsuranceAdapter mEscrowCompanyAdapter;
    private FeaturedAgentAdapter mFeaturedAgentAdapter;

    //List Consumer Team
    private ArrayList<ConsumerTeam> mArrTeamRealtorFeatured = new ArrayList<>();
    private ArrayList<ConsumerTeam> mArrTeamRealtorMyResource = new ArrayList<>();
    private ArrayList<ConsumerTeam> mArrTeamLenderMyResource = new ArrayList<>();
    private ArrayList<ConsumerTeam> mArrTeamLenderFeatured = new ArrayList<>();
    private ArrayList<ConsumerTeam> mArrTeamHomeInspectorMyResource = new ArrayList<>();
    private ArrayList<ConsumerTeam> mArrTeamHomeInspectorFeatured = new ArrayList<>();

    private ConsumerTeam selectedRealtor, selectedLender, selectedHomeInspector;
    private IPageChangeMyTeam mPageChange;
    private GetFeaturedPresenter mGetFeaturedPresenter;

    @Bind(R.id.layoutMain)
    RelativeLayout mLayoutMain;
    //Realtor
    @Bind(R.id.rvRealtorFeatured)
    RecyclerView mRvRealtorFeatured;

    @Bind(R.id.lnSelectedRealtor)
    LinearLayout mLnSelectedRealtor;
    @Bind(R.id.lnRealtorList)
    LinearLayout mLnRealtorList;
    @Bind(R.id.imgAvatarRealtor)
    CircleImageView mImgAvatarRealtor;

    @Bind(R.id.tvRealtorName)
    TextView mTvRealtorName;
    @Bind(R.id.tvRealtorJob)
    TextView mTvRealtorJob;
    @Bind(R.id.lnHistoryRealtor)
    LinearLayout mLnHistoryRealtor;
    @Bind(R.id.imgRealtorLogo)
    ImageView mImgRealtorLogo;

    @Bind(R.id.vHeaderRealtor)
    View mVHeaderRealtor;
    @Bind(R.id.frmHeaderRealtor)
    FrameLayout mFrmHeaderRealtor;
    @Bind(R.id.tvHeaderRealtor)
    TextView mTvHeaderRealtor;

    //Lender
    @Bind(R.id.vHeaderLender)
    View mVHeaderLender;
    @Bind(R.id.imgAvatarLender)
    CircleImageView mImgAvatarLender;
    @Bind(R.id.tvHeaderLender)
    TextView mTvHeaderLender;
    @Bind(R.id.frmHeaderLender)
    FrameLayout mFrmHeaderLender;
    @Bind(R.id.tvLenderName)
    TextView mTvLenderName;
    @Bind(R.id.tvLenderJob)
    TextView mTvLenderJob;
    @Bind(R.id.imgLenderLogo)
    ImageView mImgLenderLogo;
    @Bind(R.id.itemTopLender)
    LinearLayout mItemTopLender;
    @Bind(R.id.imgHistoryLender)
    ImageView mImgHistoryLender;
    @Bind(R.id.lnHistoryLender)
    LinearLayout mLnHistoryLender;
    @Bind(R.id.imgCallLender)
    ImageView mImgCallLender;
    @Bind(R.id.imgMessageLender)
    ImageView mImgMessageLender;
    @Bind(R.id.lnSelectedLender)
    LinearLayout mLnSelectedLender;

    @Bind(R.id.rvLenderFeatured)
    RecyclerView mRvLenderFeatured;

    @Bind(R.id.lnLenderList)
    LinearLayout mLnLenderList;
    @Bind(R.id.itemLender)
    RelativeLayout mItemLender;

    @Bind(R.id.vHeaderHomeInspector)
    View mVHeaderHomeInspector;
    @Bind(R.id.tvHeaderHomeInspector)
    TextView mTvHeaderHomeInspector;
    @Bind(R.id.frmHeaderHomeInspector)
    FrameLayout mFrmHeaderHomeInspector;
    @Bind(R.id.imgAvatarHomeInspector)
    CircleImageView mImgAvatarHomeInspector;
    @Bind(R.id.tvHomeInspectorName)
    TextView mTvHomeInspectorName;
    @Bind(R.id.tvHomeInspectorJob)
    TextView mTvHomeInspectorJob;
    @Bind(R.id.imgHomeInspectorLogo)
    ImageView mImgHomeInspectorLogo;
    @Bind(R.id.itemTopHomeInspector)
    LinearLayout mItemTopHomeInspector;
    @Bind(R.id.imgHistoryHomeInspector)
    ImageView mImgHistoryHomeInspector;
    @Bind(R.id.lnHistoryHomeInspector)
    LinearLayout mLnHistoryHomeInspector;
    @Bind(R.id.imgCallHomeInspector)
    ImageView mImgCallHomeInspector;
    @Bind(R.id.imgMessageHomeInspector)
    ImageView mImgMessageHomeInspector;
    @Bind(R.id.lnSelectedHomeInspector)
    LinearLayout mLnSelectedHomeInspector;

    @Bind(R.id.rvHomeInspectorFeatured)
    RecyclerView mRvHomeInspectorFeatured;

    @Bind(R.id.lnHomeInspectorList)
    LinearLayout mLnHomeInspectorList;
    @Bind(R.id.itemHomeInspector)
    RelativeLayout mItemHomeInspector;

    //Title Insurance
    @Bind(R.id.rvTitleInsurance)
    RecyclerView mRvTitleInsurance;

    //Escrow company
    @Bind(R.id.rvEscrowCompany)
    RecyclerView mRvEscrowCompany;


    @OnClick(R.id.ivSearchRealtor)
    public void openSearchRealtor() {
        getContext().startActivity(new Intent(getContext(), TeamTabSearchActivity.class));
        getActivity().overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }

    @OnClick(R.id.lnHistoryRealtor)
    public void moveToHistoryOfSelectedRealtor() {
//        mPageChange.openHistory();
//        mHistoryListener.filterHistory(selectedRealtor.getId());
    }

    @OnClick(R.id.lnHistoryLender)
    public void moveToHistoryOfSelectedLender() {
//        mPageChange.openHistory();
//        mHistoryListener.filterHistory(selectedLender.getId());
    }

    @OnClick(R.id.lnHistoryHomeInspector)
    public void moveToHistoryOfSelectedHomeInspector() {
//        mPageChange.openHistory();
//        mHistoryListener.filterHistory(selectedHomeInspector.getId());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        initRealtorFeatured();
        initLenderFeatured();
        initHomeInspectorFeatured();
        initTitleInsurance();
        initEscrowCompany();

        setupMvp();
    }

    private void initAdapter() {
        mRealtorFeaturedAdapter = new RealtorFeaturedAdapter(getActivity(), true, this, mArrTeamRealtorFeatured);
        mRealtorMyResourceAdapter = new RealtorMyResourceAdapter(getActivity(), true, this, mArrTeamRealtorMyResource);
        mLenderFeaturedAdapter = new LenderFeaturedAdapter(getActivity(), true, this, mArrTeamLenderFeatured);
        mLenderMyResourceAdapter = new LenderMyResourceAdapter(getActivity(), true, this, mArrTeamLenderMyResource);
        mHomeInspectorFeaturedAdapter = new HomeInspectorFeaturedAdapter(getActivity(), true, this, mArrTeamHomeInspectorFeatured);
        mHomeInspectorMyResourceAdapter = new HomeInspectorMyResourceAdapter(getActivity(), true, this, mArrTeamHomeInspectorMyResource);
        mTitleInsuranceAdapter = new TitleInsuranceAdapter(getActivity());
        mEscrowCompanyAdapter = new TitleInsuranceAdapter(getActivity());
    }

    private void setupMvp() {
        //mvp
        RealtorFeaturedPresenter mRealtorFeaturedPresenter;
        RealtorFeaturedHelper mRealtorFeaturedHelper;
        RealtorMyResourcePresenter mRealtorMyResourcePresenter;
        RealtorMyResourceHelper mRealtorMyResourceHelper;
        LenderFeaturedPresenter mLenderFeaturedPresenter;
        LenderFeaturedHelper mLenderFeaturedHelper;
        LenderMyResourcePresenter mLenderMyResourcePresenter;
        LenderMyResourceHelper mLenderMyResourceHelper;
        HomeInspectorFeaturedPresenter mHomeInspectorFeaturedPresenter;
        HomeInspectorFeaturedHelper mHomeInspectorFeaturedHelper;
        HomeInspectorMyResourcePresenter mHomeInspectorMyResourcePresenter;
        HomeInspectorMyResourceHelper mHomeInspectorMyResourceHelper;

        mRealtorFeaturedHelper = new RealtorFeaturedHelper();
        mRealtorFeaturedPresenter = new RealtorFeaturedPresenter(mRealtorFeaturedHelper, this);
        mRealtorFeaturedPresenter.getRealtorFeatured();

        mRealtorMyResourceHelper = new RealtorMyResourceHelper();
        mRealtorMyResourcePresenter = new RealtorMyResourcePresenter(mRealtorMyResourceHelper, this);
        mRealtorMyResourcePresenter.getRealtorMyResource();

        mLenderFeaturedHelper = new LenderFeaturedHelper();
        mLenderFeaturedPresenter = new LenderFeaturedPresenter(mLenderFeaturedHelper, this);
        mLenderFeaturedPresenter.getLenderFeatured();

        mLenderMyResourceHelper = new LenderMyResourceHelper();
        mLenderMyResourcePresenter = new LenderMyResourcePresenter(mLenderMyResourceHelper, this);
        mLenderMyResourcePresenter.getLenderMyResource();

        mHomeInspectorFeaturedHelper = new HomeInspectorFeaturedHelper();
        mHomeInspectorFeaturedPresenter = new HomeInspectorFeaturedPresenter(mHomeInspectorFeaturedHelper, this);
        mHomeInspectorFeaturedPresenter.getHomeInspectorFeatured();

        mHomeInspectorMyResourceHelper = new HomeInspectorMyResourceHelper();
        mHomeInspectorMyResourcePresenter = new HomeInspectorMyResourcePresenter(mHomeInspectorMyResourceHelper, this);
        mHomeInspectorMyResourcePresenter.getHomeInspectorMyResource();
    }


    private void initRealtorFeatured() {
        mGetFeaturedPresenter = new GetFeaturedPresenter(this);
        mGetFeaturedPresenter.getFeature();
        mSetAgentPresenter = new SetAgentPresenter(this);
        if (ConsumerUser.getInstance().getData().getHasAgent().equalsIgnoreCase("yes")) {
            mVHeaderRealtor.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreen40));
            mLnSelectedRealtor.setVisibility(View.VISIBLE);
            mLnRealtorList.setVisibility(View.GONE);
            Glide.with(getContext()).load(ConsumerUser.getInstance().getData().getAgentPhoto())
                    .asBitmap().fitCenter().into(mImgAvatarRealtor);
            if (ConsumerUser.getInstance().getData().getAgentCompanyTitle() != null) {
                mTvRealtorJob.setText(ConsumerUser.getInstance().getData().getAgentCompanyTitle());
            }
            mTvRealtorName.setText(ConsumerUser.getInstance().getData().getAgentFirstName() + " " +
                    ConsumerUser.getInstance().getData().getAgentLastName());
        }
    }

    private void initLenderFeatured() {
        mRvLenderFeatured.setLayoutManager(createLayoutManager());
        mRvLenderFeatured.setAdapter(mLenderFeaturedAdapter);
    }


    private void initHomeInspectorFeatured() {
        mRvHomeInspectorFeatured.setLayoutManager(createLayoutManager());
        mRvHomeInspectorFeatured.setAdapter(mHomeInspectorFeaturedAdapter);
    }

    private void initTitleInsurance() {
        mRvTitleInsurance.setLayoutManager(createLayoutManager());
        mRvTitleInsurance.setAdapter(mTitleInsuranceAdapter);
    }

    private void initEscrowCompany() {
        mRvEscrowCompany.setLayoutManager(createLayoutManager());
        mRvEscrowCompany.setAdapter(mEscrowCompanyAdapter);
    }

    private LinearLayoutManager createLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
    }

    public void showSelectedRealtor(final ConsumerTeam selectedRealtor) {
        setSelectedRealtor(selectedRealtor);
        initSelectedRealtor();
        mVHeaderRealtor.setBackgroundColor(getResources().getColor(R.color.colorGreen40));

        int height166 = Convert.dpToPx(166, getActivity());
        int height22 = Convert.dpToPx(22, getActivity());
        ViewGroup.LayoutParams viewHeaderRealtor = mVHeaderRealtor.getLayoutParams();
        viewHeaderRealtor.height = height166;
        mVHeaderRealtor.setLayoutParams(viewHeaderRealtor);

        ViewGroup.LayoutParams frmHeaderRealtor = mFrmHeaderRealtor.getLayoutParams();

        frmHeaderRealtor.height = height166;
        mFrmHeaderRealtor.setLayoutParams(frmHeaderRealtor);

        ViewGroup.LayoutParams tvHeaderRealtor = mTvHeaderRealtor.getLayoutParams();
        tvHeaderRealtor.height = height166;
        mTvHeaderRealtor.setLayoutParams(tvHeaderRealtor);
        mTvHeaderRealtor.setPadding(0, height22, 0, 0);

        mLnSelectedRealtor.setVisibility(View.VISIBLE);
        mLnRealtorList.setVisibility(View.GONE);
    }

    public void showSelectedLender(final ConsumerTeam selectedLender) {
        setSelectedLender(selectedLender);
        initSelectedLender();
        mVHeaderLender.setBackgroundColor(getResources().getColor(R.color.colorGreen40));

        int height166 = Convert.dpToPx(166, getActivity());
        int height22 = Convert.dpToPx(22, getActivity());
        ViewGroup.LayoutParams viewHeaderLender = mVHeaderLender.getLayoutParams();
        viewHeaderLender.height = height166;
        mVHeaderLender.setLayoutParams(viewHeaderLender);

        ViewGroup.LayoutParams frmHeaderLender = mFrmHeaderLender.getLayoutParams();
        frmHeaderLender.height = height166;
        mFrmHeaderLender.setLayoutParams(frmHeaderLender);

        ViewGroup.LayoutParams tvHeaderLender = mTvHeaderLender.getLayoutParams();
        tvHeaderLender.height = height166;
        mTvHeaderLender.setLayoutParams(tvHeaderLender);
        mTvHeaderLender.setPadding(0, height22, 0, 0);

        mLnSelectedLender.setVisibility(View.VISIBLE);
        mLnLenderList.setVisibility(View.GONE);
    }

    public void showSelectedHomeInspector(final ConsumerTeam selectedHomeInspector) {
        setSelectedHomeInspector(selectedHomeInspector);
        initSelectedHomeInspector();
        mVHeaderHomeInspector.setBackgroundColor(getResources().getColor(R.color.colorGreen40));

        int height166 = Convert.dpToPx(166, getActivity());
        int height22 = Convert.dpToPx(22, getActivity());
        ViewGroup.LayoutParams viewHeaderHomeInspector = mVHeaderHomeInspector.getLayoutParams();
        viewHeaderHomeInspector.height = height166;
        mVHeaderHomeInspector.setLayoutParams(viewHeaderHomeInspector);

        ViewGroup.LayoutParams frmHeaderHomeInspector = mFrmHeaderHomeInspector.getLayoutParams();
        frmHeaderHomeInspector.height = height166;
        mFrmHeaderHomeInspector.setLayoutParams(frmHeaderHomeInspector);

        ViewGroup.LayoutParams tvHeaderHomeInspector = mTvHeaderHomeInspector.getLayoutParams();
        tvHeaderHomeInspector.height = height166;
        mTvHeaderHomeInspector.setLayoutParams(tvHeaderHomeInspector);
        mTvHeaderHomeInspector.setPadding(0, height22, 0, 0);

        mLnSelectedHomeInspector.setVisibility(View.VISIBLE);
        mLnHomeInspectorList.setVisibility(View.GONE);
    }

    public void hideSelectedRealtor() {
        mVHeaderRealtor.setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
        mLnSelectedRealtor.setVisibility(View.GONE);
        mLnRealtorList.setVisibility(View.VISIBLE);

        int height208 = Convert.dpToPx(208, getActivity());
        int height42 = Convert.dpToPx(42, getActivity());
        ViewGroup.LayoutParams viewHeaderRealtor = mVHeaderRealtor.getLayoutParams();
        viewHeaderRealtor.height = height208;
        mVHeaderRealtor.setLayoutParams(viewHeaderRealtor);

        ViewGroup.LayoutParams frmHeaderRealtor = mFrmHeaderRealtor.getLayoutParams();

        frmHeaderRealtor.height = height208;
        mFrmHeaderRealtor.setLayoutParams(frmHeaderRealtor);

        ViewGroup.LayoutParams tvHeaderRealtor = mTvHeaderRealtor.getLayoutParams();
        tvHeaderRealtor.height = height208;
        mTvHeaderRealtor.setLayoutParams(tvHeaderRealtor);
        mTvHeaderRealtor.setPadding(0, height42, 0, 0);
    }

    public void hideSelectedLender() {
        mVHeaderLender.setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
        mLnSelectedLender.setVisibility(View.GONE);
        mLnLenderList.setVisibility(View.VISIBLE);

        int height208 = Convert.dpToPx(208, getActivity());
        int height42 = Convert.dpToPx(42, getActivity());
        ViewGroup.LayoutParams viewHeaderLender = mVHeaderLender.getLayoutParams();
        viewHeaderLender.height = height208;
        mVHeaderLender.setLayoutParams(viewHeaderLender);

        ViewGroup.LayoutParams frmHeaderLender = mFrmHeaderLender.getLayoutParams();
        frmHeaderLender.height = height208;
        mFrmHeaderLender.setLayoutParams(frmHeaderLender);

        ViewGroup.LayoutParams tvHeaderLender = mTvHeaderLender.getLayoutParams();
        tvHeaderLender.height = height208;
        mTvHeaderLender.setLayoutParams(tvHeaderLender);
        mTvHeaderLender.setPadding(0, height42, 0, 0);
    }

    public void hideSelectedHomeInspector() {
        mVHeaderHomeInspector.setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
        mLnSelectedHomeInspector.setVisibility(View.GONE);
        mLnHomeInspectorList.setVisibility(View.VISIBLE);

        int height208 = Convert.dpToPx(208, getActivity());
        int height42 = Convert.dpToPx(42, getActivity());
        ViewGroup.LayoutParams viewHeaderHomeInspector = mVHeaderHomeInspector.getLayoutParams();
        viewHeaderHomeInspector.height = height208;
        mVHeaderHomeInspector.setLayoutParams(viewHeaderHomeInspector);

        ViewGroup.LayoutParams frmHeaderHomeInspector = mFrmHeaderHomeInspector.getLayoutParams();
        frmHeaderHomeInspector.height = height208;
        mFrmHeaderHomeInspector.setLayoutParams(frmHeaderHomeInspector);

        ViewGroup.LayoutParams tvHeaderHomeInspector = mTvHeaderHomeInspector.getLayoutParams();
        tvHeaderHomeInspector.height = height208;
        mTvHeaderHomeInspector.setLayoutParams(tvHeaderHomeInspector);
        mTvHeaderHomeInspector.setPadding(0, height42, 0, 0);
    }

    private void setSelectedRealtor(ConsumerTeam selectedRealtor) {
        this.selectedRealtor = selectedRealtor;
    }

    private void setSelectedLender(ConsumerTeam selectedLender) {
        this.selectedLender = selectedLender;
    }

    private void setSelectedHomeInspector(ConsumerTeam selectedHomeInspector) {
        this.selectedHomeInspector = selectedHomeInspector;
    }

    private void initSelectedRealtor() {
        Glide.with(getActivity().getApplicationContext()).load(selectedRealtor.getPhoto()).asBitmap().fitCenter()
                .placeholder(R.drawable.avatar_default)
                .dontAnimate()
                .into(mImgAvatarRealtor);

        mTvRealtorName.setText(selectedRealtor.getFirstName() + " " + selectedRealtor.getLastName());

        mTvRealtorJob.setText(selectedRealtor.getCompany());

        Glide.with(getActivity().getApplicationContext()).load(selectedRealtor.getLogo()).asBitmap().fitCenter()
                .placeholder(R.drawable.no_image)
                .dontAnimate()
                .into(mImgRealtorLogo);
    }

    private void initSelectedLender() {
        Glide.with(getActivity().getApplicationContext()).load(selectedLender.getPhoto()).asBitmap().fitCenter()
                .placeholder(R.drawable.avatar_default)
                .dontAnimate()
                .into(mImgAvatarLender);

        mTvLenderName.setText(selectedLender.getFirstName() + " " + selectedLender.getLastName());

        mTvLenderJob.setText(selectedLender.getCompany());

        Glide.with(getActivity().getApplicationContext()).load(selectedLender.getLogo()).asBitmap().fitCenter()
                .placeholder(R.drawable.no_image)
                .dontAnimate()
                .into(mImgLenderLogo);
    }

    private void initSelectedHomeInspector() {
        Glide.with(getActivity().getApplicationContext()).load(selectedHomeInspector.getPhoto()).asBitmap().fitCenter()
                .placeholder(R.drawable.avatar_default)
                .dontAnimate()
                .into(mImgAvatarHomeInspector);

        mTvHomeInspectorName.setText(selectedHomeInspector.getFirstName() + " " + selectedHomeInspector.getLastName());

        mTvHomeInspectorJob.setText(selectedHomeInspector.getCompany());

        Glide.with(getActivity().getApplicationContext()).load(selectedHomeInspector.getLogo()).asBitmap().fitCenter()
                .placeholder(R.drawable.no_image)
                .dontAnimate()
                .into(mImgHomeInspectorLogo);
    }

    public void setPageChange(IPageChangeMyTeam mPageChange) {
        this.mPageChange = mPageChange;
    }

    @Override
    public void showRealtorFeatured(ArrayList<ConsumerTeam> teams) {
        mArrTeamRealtorFeatured.clear();

        for (int i = 0; i < 8; i++) {
            mArrTeamRealtorFeatured.add(teams.get(i));
        }
        mRealtorFeaturedAdapter.notifyDataSetChanged();
    }

    @Override
    public void showRealtorMyResource(ArrayList<ConsumerTeam> teams) {
        mArrTeamRealtorMyResource.clear();

        for (int i = 8; i < 16; i++) {
            mArrTeamRealtorMyResource.add(teams.get(i));
        }
        mRealtorMyResourceAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLenderFeatured(ArrayList<ConsumerTeam> teams) {
        mArrTeamLenderFeatured.clear();

        for (int i = 0; i < 8; i++) {
            mArrTeamLenderFeatured.add(teams.get(i));
        }
        mLenderFeaturedAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLenderMyResource(ArrayList<ConsumerTeam> teams) {
        mArrTeamLenderMyResource.clear();

        for (int i = 8; i < 16; i++) {
            mArrTeamLenderMyResource.add(teams.get(i));
        }
        mLenderMyResourceAdapter.notifyDataSetChanged();
    }

    @Override
    public void showHomeInspectorFeatured(ArrayList<ConsumerTeam> teams) {
        mArrTeamHomeInspectorFeatured.clear();

        for (int i = 0; i < 8; i++) {
            mArrTeamHomeInspectorFeatured.add(teams.get(i));
        }
        mHomeInspectorFeaturedAdapter.notifyDataSetChanged();
    }

    @Override
    public void showHomeInspectorMyResource(ArrayList<ConsumerTeam> teams) {
        mArrTeamHomeInspectorMyResource.clear();

        for (int i = 8; i < 16; i++) {
            mArrTeamHomeInspectorMyResource.add(teams.get(i));
        }
        mHomeInspectorMyResourceAdapter.notifyDataSetChanged();
        mTeamMainListener.showLayout();
    }

    @Override
    public void showError(String message) {

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_team_child;
    }

    @Override
    public void getFeaturedSuccess(ArrayList<ResponseFeatured.Featured> arrFeatured) {
        ArrayList<BaseDataRecyclerView> arrayList = new ArrayList<>();
        arrayList.addAll(arrFeatured);
        mFeaturedAgentAdapter = new FeaturedAgentAdapter(getActivity(), arrayList, this);
        mRvRealtorFeatured.setLayoutManager(createLayoutManager());
        mRvRealtorFeatured.setAdapter(mFeaturedAgentAdapter);
    }


    @Override
    public void openAgent(final ResponseFeatured.Featured featuredAgent) {
        mFeaturedAgent = featuredAgent;
        android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setNegativeButton("View Information", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EventBus.getDefault().post(new EventAgentDetail(featuredAgent.getId()));
                        dialogInterface.dismiss();
                    }
                })
                .setNeutralButton("Set Exclusive", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mSetAgentPresenter.setAgent(ConsumerUser.getInstance().getData().getId(), featuredAgent.getId());
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

    @Override
    public void openMyTeam() {

    }

    @Override
    public void setAgentSuccess() {
        ConsumerUser.getInstance().getData().setHasAgent("yes");
        ConsumerUser.getInstance().getData().setAgentId(mFeaturedAgent.getId());
        ConsumerUser.getInstance().getData().setAgentFirstName(mFeaturedAgent.getFirstName());
        ConsumerUser.getInstance().getData().setAgentLastName(mFeaturedAgent.getLastName());
        ConsumerUser.getInstance().getData().setAgentPnUid(mFeaturedAgent.getPnUid());
        ConsumerUser.getInstance().getData().setAgentPhoto(mFeaturedAgent.getAvatar());
        mLnSelectedRealtor.setVisibility(View.VISIBLE);
        mLnRealtorList.setVisibility(View.GONE);
        Glide.with(getContext()).load(mFeaturedAgent.getAvatar())
                .asBitmap().fitCenter().into(mImgAvatarRealtor);
        if (!mFeaturedAgent.getCompany().isEmpty()) {
            mTvRealtorJob.setText(mFeaturedAgent.getCompany().get(0).getName());
        }
        mTvRealtorName.setText(mFeaturedAgent.getFullName());
    }

    @Override
    public void setAgentFail(String message) {
        showSnackBar(mLayoutMain, TypeDialog.WARNING, message, "setAgent");

    }

    @Override
    public void setAgentFail(@StringRes int message) {
        showSnackBar(mLayoutMain, TypeDialog.ERROR, message, "setAgent");

    }
}
