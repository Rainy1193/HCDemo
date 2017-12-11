package com.homecaravan.android.consumer.consumershedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.base.BaseFragment;

public class FragmentInviteQrCode extends BaseFragment {
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_invite_qr_code;
    }
}
