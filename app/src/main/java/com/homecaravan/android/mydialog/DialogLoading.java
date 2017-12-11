package com.homecaravan.android.mydialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.homecaravan.android.R;

public class DialogLoading extends DialogFragment {
    private AlertDialog alertDialog;
    private boolean isPause;
    private boolean isShow;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.dialog_loading, null);
        alertDialog = new AlertDialog.Builder(getActivity()).setView(view).create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        return alertDialog;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
        isShow = true;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (!isPause) {
            super.onDismiss(dialog);
            isShow = false;
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        dismiss();
        isPause = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        isPause = false;
    }

    public boolean isShow() {
        return isShow;
    }
}
