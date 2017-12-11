package com.homecaravan.android.consumer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homecaravan.android.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ViewAllHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.layoutViewAll)
    RelativeLayout mLayoutViewAll;
    @Bind(R.id.tvNumViewAll)
    TextView mNumViewAll;

    public ViewAllHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
