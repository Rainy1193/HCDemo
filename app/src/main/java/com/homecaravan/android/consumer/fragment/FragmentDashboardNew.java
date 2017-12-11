package com.homecaravan.android.consumer.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.base.BaseFragment;

import butterknife.Bind;

public class FragmentDashboardNew extends BaseFragment {

    private int mTitle;
    private int mSrc;
    private int mNew;
    @Bind(R.id.iv)
    ImageView mImageView;
    @Bind(R.id.tvTitle)
    TextView mTvTitle;
    @Bind(R.id.tvNew)
    TextView mTvNew;

    public static FragmentDashboardNew getInstance(int mNew, int src, int title) {
        final FragmentDashboardNew f = new FragmentDashboardNew();
        final Bundle args = new Bundle();
        args.putInt("src", src);
        args.putInt("title", title);
        args.putInt("new", mNew);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(getActivity()).load(mSrc).asBitmap().fitCenter().into(mImageView);
        mTvTitle.setText(mTitle);
        mTvNew.setText(mNew);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSrc == R.drawable.dashboard_2) {
                    Intent terms = new Intent(Intent.ACTION_VIEW);
                    terms.setData(Uri.parse("https://www.firstam.com"));
                    startActivity(terms);
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments() != null ? getArguments().getInt("title") : 0;
        mSrc = getArguments() != null ? getArguments().getInt("src") : 0;
        mNew = getArguments() != null ? getArguments().getInt("new") : 0;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_dashboard_new;
    }
}
