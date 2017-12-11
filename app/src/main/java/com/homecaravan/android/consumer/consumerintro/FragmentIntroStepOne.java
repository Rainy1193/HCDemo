package com.homecaravan.android.consumer.consumerintro;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.Bind;

public class FragmentIntroStepOne extends BaseFragment {

    @Bind(R.id.iv)
    RoundedImageView mImageView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(getActivity().getApplicationContext()).load(R.drawable.img_intro_1).asBitmap().fitCenter().into(mImageView);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_step_intro_one;
    }
}
