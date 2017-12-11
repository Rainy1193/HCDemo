package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.listener.IShowingListener;
import com.homecaravan.android.consumer.model.responseapi.CaravanShowing;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.consumer.utils.Utils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CaravanShowingAdapter extends RecyclerView.Adapter<CaravanShowingAdapter.AppointmentHolder> {

    private Context mContext;
    private ArrayList<CaravanShowing> mArrCaravan = new ArrayList<>();
    private IShowingListener mListener;

    public CaravanShowingAdapter(Context mContext, ArrayList<CaravanShowing> mArrCaravan, IShowingListener mListener) {
        this.mContext = mContext;
        this.mArrCaravan = mArrCaravan;
        this.mListener = mListener;
    }

    @Override
    public CaravanShowingAdapter.AppointmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_item, parent, false);
        return new AppointmentHolder(v);
    }

    @Override
    public void onBindViewHolder(final CaravanShowingAdapter.AppointmentHolder holder, final int position) {
        final CaravanShowing caravanShowing = mArrCaravan.get(position);
        holder.mAddress.setText(caravanShowing.getTitle());
        holder.mDate.setText(Utils.getDateShowing(caravanShowing.getStart()));
        holder.mTime.setText(Utils.getTimeShowing(caravanShowing.getStart(), caravanShowing.getEnd()));
        holder.mRvExpandAppointment.setAdapter(new ExpandCaravanAdapter(mContext, caravanShowing.getRefCaravan().getDestinations()));
        holder.mRvExpandAppointment.setLayoutManager(createLayoutManagerHorizontal());
        holder.mEditCaravan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.editCaravan();
            }
        });

        if (caravanShowing.isExpand()) {
            holder.mRvExpandAppointment.setVisibility(View.VISIBLE);
        } else {
            holder.mRvExpandAppointment.setVisibility(View.GONE);
        }

        holder.mFrmExpandAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (caravanShowing.isExpand()) {
                    holder.mRvExpandAppointment.setVisibility(View.GONE);
                    caravanShowing.setExpand(false);
                } else {
                    holder.mRvExpandAppointment.setVisibility(View.VISIBLE);
                    caravanShowing.setExpand(true);
                }
            }
        });
        if (!Utils.checkStatusCaravan(mArrCaravan.get(position).getStart())) {
            holder.mStatus.setText("Start soon");
            holder.mStatus.setTextColor(ContextCompat.getColor(mContext, R.color.text_blue_color_caravan_showing));
        } else {
            holder.mStatus.setText("In progress");
            holder.mStatus.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed));
            AnimUtils.inProgress(holder.mStatus);
        }
        holder.mStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.showCaravanAction();
            }
        });
    }


    public LinearLayoutManager createLayoutManagerHorizontal() {
        return new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
    }

    @Override
    public int getItemCount() {
        return mArrCaravan.size();
    }


    public class AppointmentHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.frmExpandAppointment)
        FrameLayout mFrmExpandAppointment;
        @Bind(R.id.rvExpandAppointment)
        RecyclerView mRvExpandAppointment;
        @Bind(R.id.tvStatusCaravan)
        TextView mStatus;
        @Bind(R.id.tvAddress)
        TextView mAddress;
        @Bind(R.id.tvDate)
        TextView mDate;
        @Bind(R.id.tvTime)
        TextView mTime;
        @Bind(R.id.ivEditCaravan)
        ImageView mEditCaravan;

        public AppointmentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
