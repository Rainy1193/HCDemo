package com.homecaravan.android.consumer.consumerintro;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.Bind;
import butterknife.OnClick;

public class FragmentIntroStepFour extends BaseFragment {
    private IntroListener mListener;

    public void setListener(IntroListener mListener) {
        this.mListener = mListener;
    }

    @Bind(R.id.iv)
    RoundedImageView mImageView;

    @Bind(R.id.tvGetStart)
    TextView mGetStart;

    @OnClick(R.id.tvGetStart)
    public void getStart() {
        mListener.cancelIntro();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(getActivity().getApplicationContext()).load(R.drawable.img_intro_4).asBitmap().fitCenter().into(mImageView);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_step_intro_four;
    }

    public void showGetStart() {
        mGetStart.setVisibility(View.VISIBLE);
        AnimUtils.fadeView(mGetStart, 1f);
    }

    public interface IntroListener {
        void cancelIntro();
    }
}
