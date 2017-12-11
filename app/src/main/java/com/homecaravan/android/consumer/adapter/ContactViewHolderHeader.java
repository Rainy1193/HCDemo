package com.homecaravan.android.consumer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.homecaravan.android.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactViewHolderHeader extends RecyclerView.ViewHolder {
    @Bind(R.id.tvHeader)
    TextView mTvHeader;

    public ContactViewHolderHeader(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
