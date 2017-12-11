package com.homecaravan.android.consumer.consumershedule;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.listener.InviteEmailOrPhoneListener;

import java.io.InputStream;

import butterknife.Bind;
import butterknife.OnClick;

public class FragmentInviteEmailOrPhone extends BaseFragment {

    private InviteEmailOrPhoneListener mListener;
    @Bind(R.id.etEmailAgent)
    EditText mEmail;
    @Bind(R.id.etPhoneAgent)
    EditText mPhone;
    @Bind(R.id.tvRegion)
    TextView mTvRegion;
    @Bind(R.id.ivRegion)
    ImageView mIvRegion;

    @OnClick(R.id.layoutRegion)
    public void pickCountry() {
        mListener.pickCountryForPhone();
    }

    @OnClick(R.id.layoutInviteEmail)
    public void inviteEmail() {

    }

    @OnClick(R.id.layoutInvitePhone)
    public void invitePhone() {

    }

    @OnClick(R.id.layoutContactPhone)
    public void showContact() {
        mListener.showContact();
    }

    public void setListener(InviteEmailOrPhoneListener mListener) {
        this.mListener = mListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_invite_email_or_phone;
    }

    public void setRegion(String region, String ensign) {
        mTvRegion.setText(region);
        try {
            InputStream inputStream = getActivity().getAssets().open(ensign);
            mIvRegion.setImageBitmap(BitmapFactory.decodeStream(inputStream));
        } catch (Exception ignored) {

        }
    }

    public void setPhoneInvite(String phone) {
        mPhone.setText(phone);
    }
}
