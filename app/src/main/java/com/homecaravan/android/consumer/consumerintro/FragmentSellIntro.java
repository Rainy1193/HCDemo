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

public class FragmentSellIntro extends FragmentIntroBase {
    private int current = 1;
    private float mHeightExpandYourHome, mHeightExpandShowcase, mHeightExpandFeedback;
    @Bind(R.id.layoutExpandYourHome)
    LinearLayout mExpandYourHome;

    @Bind(R.id.layoutExpandShowcase)
    LinearLayout mExpandShowcase;

    @Bind(R.id.layoutExpandFeedBack)
    LinearLayout mExpandFeedBack;

    @Bind(R.id.layoutSellYourHome)
    LinearLayout mSellYourHome;

    @Bind(R.id.layoutSellShowCase)
    LinearLayout mSellShowCase;

    @Bind(R.id.layoutSellAgentFeedBack)
    LinearLayout mSellFeedBack;

    @Bind(R.id.ivYourHome)
    ImageView mIvYourHome;

    @Bind(R.id.ivShowcase)
    ImageView mIvShowcase;

    @Bind(R.id.ivFeedback)
    ImageView mIvFeedback;

    @OnClick(R.id.getStartedYourHome)
    public void getStartedYourHome() {

    }

    @OnClick(R.id.viewVideoYourHome)
    public void viewVideoYourHome() {

    }

    @OnClick(R.id.getStartedShowCase)
    public void getStartedShowCase() {

    }

    @OnClick(R.id.viewVideoShowCase)
    public void viewVideoShowCase() {

    }

    @OnClick(R.id.viewVideoFeedBack)
    public void viewVideoFeedBack() {

    }

    @OnClick(R.id.getStartedFeedBack)
    public void getStartedFeedBack() {

    }


    @OnClick(R.id.layoutSellYourHome)
    public void openYourHome() {
        if (current == 1) {
            closeChooseLayout();
            return;
        }
        mExpandYourHome.setVisibility(View.VISIBLE);
        expand(mExpandYourHome, 0, mHeightExpandYourHome, true);
        expand(mSellYourHome, mStartHeight, mEndHeight, true);
        if (current == 2) {
            collapse(mExpandShowcase, mHeightExpandShowcase, 0, true);
            collapse(mSellShowCase, mEndHeight, mStartHeight, true);
        }
        if (current == 3) {
            collapse(mExpandFeedBack, mHeightExpandFeedback, 0, true);
            collapse(mSellFeedBack, mEndHeight, mStartHeight, true);
        }
        current = 1;
    }

    @OnClick(R.id.layoutSellShowCase)
    public void openShowCase() {
        if (current == 2) {
            closeChooseLayout();
            return;
        }
        mExpandShowcase.setVisibility(View.VISIBLE);
        expand(mExpandShowcase, 0, mHeightExpandShowcase, true);
        expand(mSellShowCase, mStartHeight, mEndHeight, true);
        if (current == 1) {
            collapse(mExpandYourHome, mHeightExpandYourHome, 0, true);
            collapse(mSellYourHome, mEndHeight, mStartHeight, true);
        }
        if (current == 3) {
            collapse(mExpandFeedBack, mHeightExpandFeedback, 0, true);
            collapse(mSellFeedBack, mEndHeight, mStartHeight, true);
        }
        current = 2;
    }

    @OnClick(R.id.layoutSellAgentFeedBack)
    public void openFeedBack() {
        if (current == 3) {
            closeChooseLayout();
            return;
        }
        mExpandFeedBack.setVisibility(View.VISIBLE);
        expand(mExpandFeedBack, 0, mHeightExpandFeedback, true);
        expand(mSellFeedBack, mStartHeight, mEndHeight, true);
        if (current == 1) {
            collapse(mExpandYourHome, mHeightExpandYourHome, 0, true);
            collapse(mSellYourHome, mEndHeight, mStartHeight, true);
        }
        if (current == 2) {
            collapse(mExpandShowcase, mHeightExpandShowcase, 0, true);
            collapse(mSellShowCase, mEndHeight, mStartHeight, true);
        }
        current = 3;
    }

    @Override
    public int getLayoutResourceFragmentIntroId() {
        return R.layout.fragment_sell_intro;
    }

    @Override
    public void resetFragment() {
        mExpandYourHome.setVisibility(View.VISIBLE);
        expand(mExpandYourHome, 0, mHeightExpandYourHome, true);
        expand(mSellYourHome, mStartHeight, mEndHeight, true);
        if (current == 2) {
            collapse(mExpandShowcase, mHeightExpandShowcase, 0, true);
            collapse(mSellShowCase, mEndHeight, mStartHeight, true);
        }
        if (current == 3) {
            collapse(mExpandFeedBack, mHeightExpandFeedback, 0, true);
            collapse(mSellFeedBack, mEndHeight, mStartHeight, true);
        }
        current = 1;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

        mLoader.loadImage(getActivity(), R.drawable.sell_your_home_intro, mIvYourHome);
        mLoader.loadImage(getActivity(), R.drawable.sell_showcase_intro, mIvShowcase);
        mLoader.loadImage(getActivity(), R.drawable.sell_receive_agent_feedback, mIvFeedback);

        mExpandYourHome.post(new Runnable() {
            @Override
            public void run() {
                mHeightExpandYourHome = mExpandYourHome.getHeight();
            }
        });

        mExpandShowcase.post(new Runnable() {
            @Override
            public void run() {
                mHeightExpandShowcase = mExpandShowcase.getHeight();
                mExpandShowcase.setVisibility(View.GONE);
            }
        });

        mExpandFeedBack.post(new Runnable() {
            @Override
            public void run() {
                mHeightExpandFeedback = mExpandFeedBack.getHeight();
                mExpandFeedBack.setVisibility(View.GONE);
            }
        });

        mSellYourHome.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (mHeightLayout * 2 + mBoundView)));
        mSellShowCase.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (mHeightLayout * 2 + mBoundView)));
        mSellFeedBack.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (mHeightLayout * 2 + mBoundView)));

        collapse(mSellShowCase, mHeightLayout * 2 + mBoundView, mHeightLayout, false);
        collapse(mSellFeedBack, mHeightLayout * 2 + mBoundView, mHeightLayout, false);
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
