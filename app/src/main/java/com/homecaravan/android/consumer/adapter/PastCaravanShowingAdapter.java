package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.responseapi.CaravanShowing;
import com.homecaravan.android.consumer.utils.Utils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PastCaravanShowingAdapter extends RecyclerView.Adapter<PastCaravanShowingAdapter.PastAppointmentHolder> {

    private Context mContext;
    private ArrayList<CaravanShowing> mArrCaravan = new ArrayList<>();

    public PastCaravanShowingAdapter(Context mContext, ArrayList<CaravanShowing> mArrCaravan) {
        this.mContext = mContext;
        this.mArrCaravan = mArrCaravan;
    }

    @Override
    public PastCaravanShowingAdapter.PastAppointmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_appointment_item, parent, false);
        return new PastAppointmentHolder(v);
    }

    @Override
    public void onBindViewHolder(PastCaravanShowingAdapter.PastAppointmentHolder holder, final int position) {

        CaravanShowing caravanShowing = mArrCaravan.get(position);
        holder.mAddress.setText(caravanShowing.getTitle());
        holder.mDate.setText(Utils.getDateShowing(caravanShowing.getStart()));
        holder.mTime.setText(Utils.getTimeShowing(caravanShowing.getStart(), caravanShowing.getEnd()));
        holder.mRvExpandPastAppointment.setAdapter(new ExpandPastCaravanAdapter(mContext, caravanShowing.getRefCaravan().getJson()));
        holder.mRvExpandPastAppointment.setLayoutManager(createLayoutManagerHorizontal());

//        ConsumerUpcoming appointment = mArrAppointment.get(position);
//        final PastAppointmentHolder appointmentHolder = (PastAppointmentHolder) holder;
//
//        appointmentHolder.mRvExpandPastAppointment.setLayoutManager(createLayoutManagerHorizontal());
//        appointmentHolder.mRvExpandPastAppointment.setAdapter(mExpandPastAppointmentAdapter);
//
//
//        if (mArrPastAppointment.get(position).isExpand()) {
//            appointmentHolder.mRvExpandPastAppointment.setVisibility(View.VISIBLE);
//        } else {
//            appointmentHolder.mRvExpandPastAppointment.setVisibility(View.GONE);
//        }
//
//        appointmentHolder.mFrmExpandPastAppointment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mArrPastAppointment.get(position).isExpand()) {
//                    AnimUtils.collapseView(appointmentHolder.mRvExpandPastAppointment, null);
//                    mArrPastAppointment.get(position).setExpand(false);
//                } else {
//                    AnimUtils.expandView(appointmentHolder.mRvExpandPastAppointment);
//                    mArrPastAppointment.get(position).setExpand(true);
//                }
//            }
//        });
    }


    public LinearLayoutManager createLayoutManagerHorizontal() {
        return new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
    }

    @Override
    public int getItemCount() {
        return mArrCaravan.size();
    }


    public class PastAppointmentHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvAddress)
        TextView mAddress;
        @Bind(R.id.tvDate)
        TextView mDate;
        @Bind(R.id.tvTime)
        TextView mTime;
        @Bind(R.id.frmExpandPastAppointment)
        FrameLayout mFrmExpandPastAppointment;
        @Bind(R.id.rvExpandPastAppointment)
        RecyclerView mRvExpandPastAppointment;

        public PastAppointmentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
