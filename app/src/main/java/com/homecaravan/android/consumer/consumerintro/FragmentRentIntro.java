package com.homecaravan.android.consumer.consumerintro;


import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.homecaravan.android.R;

import butterknife.Bind;
import butterknife.OnClick;

public class FragmentRentIntro extends FragmentIntroBase {
    private int current = 1;
    private float mHeightExpandDiscover, mHeightExpandVisit, mHeightExpandCollaborate;
    @Bind(R.id.layoutExpandDiscover)
    LinearLayout mExpandDiscover;

    @Bind(R.id.layoutExpandVisit)
    LinearLayout mExpandVisit;

    @Bind(R.id.layoutExpandCollaborate)
    LinearLayout mExpandCollaborate;

    @Bind(R.id.layoutRentDiscover)
    LinearLayout mRentDiscover;

    @Bind(R.id.layoutRentVisit)
    LinearLayout mRentVisit;

    @Bind(R.id.layoutRentCollaborate)
    LinearLayout mRentCollaborate;

    @Bind(R.id.ivRentDiscover)
    ImageView mIvDiscover;

    @Bind(R.id.ivRentVisit)
    ImageView mIvVisit;

    @Bind(R.id.ivRentCollaborate)
    ImageView mIvCollaborate;


    @OnClick(R.id.viewVideoDiscover)
    public void viewVideoDiscover() {

    }

    @OnClick(R.id.getStartedDiscover)
    public void getStartedDiscover() {

    }

    @OnClick(R.id.viewVideoVisit)
    public void viewVideoVisit() {

    }

    @OnClick(R.id.getStartedVisit)
    public void getStartedVisit() {

    }

    @OnClick(R.id.viewVideoCollaborate)
    public void viewVideoCollaborate() {

    }

    @OnClick(R.id.getStartedCollaborate)
    public void getStartedCollaborate() {

    }

    @OnClick(R.id.layoutRentDiscover)
    public void openDiscover() {
        if (current == 1) {
            closeChooseLayout();
            return;
        }
        mExpandDiscover.setVisibility(View.VISIBLE);
        expand(mExpandDiscover, 0, mHeightExpandDiscover, true);
        expand(mRentDiscover, mStartHeight, mEndHeight, true);
        if (current == 2) {
            collapse(mExpandVisit, mHeightExpandVisit, 0, true);
            collapse(mRentVisit, mEndHeight, mStartHeight, true);
        }
        if (current == 3) {
            collapse(mExpandCollaborate, mHeightExpandCollaborate, 0, true);
            collapse(mRentCollaborate, mEndHeight, mStartHeight, true);
        }
        current = 1;
    }

    @OnClick(R.id.layoutRentVisit)
    public void openVisit() {
        if (current == 2) {
            closeChooseLayout();
            return;
        }
        mExpandVisit.setVisibility(View.VISIBLE);
        expand(mExpandVisit, 0, mHeightExpandVisit, true);
        expand(mRentVisit, mStartHeight, mEndHeight, true);
        if (current == 1) {
            collapse(mExpandDiscover, mHeightExpandDiscover, 0, true);
            collapse(mRentDiscover, mEndHeight, mStartHeight, true);
        }
        if (current == 3) {
            collapse(mExpandCollaborate, mHeightExpandCollaborate, 0, true);
            collapse(mRentCollaborate, mEndHeight, mStartHeight, true);
        }
        current = 2;
    }

    @OnClick(R.id.layoutRentCollaborate)
    public void openCollaborate() {
        if (current == 3) {
            closeChooseLayout();
            return;
        }
        mExpandCollaborate.setVisibility(View.VISIBLE);
        expand(mExpandCollaborate, 0, mHeightExpandCollaborate, true);
        expand(mRentCollaborate, mStartHeight, mEndHeight, true);
        if (current == 1) {
            collapse(mExpandDiscover, mHeightExpandDiscover, 0, true);
            collapse(mRentDiscover, mEndHeight, mStartHeight, true);
        }
        if (current == 2) {
            collapse(mExpandVisit, mHeightExpandVisit, 0, true);
            collapse(mRentVisit, mEndHeight, mStartHeight, true);
        }
        current = 3;
    }


    @Override
    public int getLayoutResourceFragmentIntroId() {
        return R.layout.fragment_rent_intro;
    }

    @Override
    public void resetFragment() {
        mExpandDiscover.setVisibility(View.VISIBLE);
        expand(mExpandDiscover, 0, mHeightExpandDiscover, true);
        expand(mRentDiscover, mStartHeight, mEndHeight, true);
        if (current == 2) {
            collapse(mExpandVisit, mHeightExpandVisit, 0, true);
            collapse(mRentVisit, mEndHeight, mStartHeight, true);
        }
        if (current == 3) {
            collapse(mExpandCollaborate, mHeightExpandCollaborate, 0, true);
            collapse(mRentCollaborate, mEndHeight, mStartHeight, true);
        }
        current = 1;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void init() {

        mLoader.loadImage(getActivity(), R.drawable.rent_discover_intro, mIvDiscover);
        mLoader.loadImage(getActivity(), R.drawable.rent_visit_intro, mIvVisit);
        mLoader.loadImage(getActivity(), R.drawable.rent_collaborate_intro, mIvCollaborate);

        mExpandDiscover.post(new Runnable() {
            @Override
            public void run() {
                mHeightExpandDiscover = mExpandDiscover.getHeight();
            }
        });

        mExpandCollaborate.post(new Runnable() {
            @Override
            public void run() {
                mHeightExpandCollaborate = mExpandCollaborate.getHeight();
                mExpandCollaborate.setVisibility(View.GONE);
            }
        });

        mExpandVisit.post(new Runnable() {
            @Override
            public void run() {
                mHeightExpandVisit = mExpandVisit.getHeight();
                mExpandVisit.setVisibility(View.GONE);
            }
        });

        mRentDiscover.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (mHeightLayout * 2 + mBoundView)));
        mRentVisit.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (mHeightLayout * 2 + mBoundView)));
        mRentCollaborate.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (mHeightLayout * 2 + mBoundView)));

        collapse(mRentVisit, mHeightLayout * 2 + mBoundView, mHeightLayout, false);
        collapse(mRentCollaborate, mHeightLayout * 2 + mBoundView, mHeightLayout, false);
    }

    private ValueAnimator slideAnimator(float start, float end, final LinearLayout linearLayout, boolean withDuration) {
        ValueAnimator animator = ValueAnimator.ofFloat(start, end);
        if (withDuration) {
            animator.setDuration(300);
        } else {
            animator.setDuration(1);
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                layoutParams.height = (int) value;
                linearLayout.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    private void expand(LinearLayout relativeLayout, float startHeight, float endHeight, boolean withDuration) {
        ValueAnimator mAnimator = slideAnimator(startHeight, endHeight, relativeLayout, withDuration);
        mAnimator.start();

    }

    private void collapse(final LinearLayout relativeLayout, float startHeight, float endHeight, boolean withDuration) {
        ValueAnimator mAnimator = slideAnimator(startHeight, endHeight, relativeLayout, withDuration);
        mAnimator.start();
    }

}
