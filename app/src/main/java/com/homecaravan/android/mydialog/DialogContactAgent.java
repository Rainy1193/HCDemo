package com.homecaravan.android.mydialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
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
import android.widget.TextView;

import com.homecaravan.android.R;

import com.homecaravan.android.models.CurrentListing;
import com.homecaravan.android.models.UserLoginInfo;
import com.homecaravan.android.ui.FontManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RAINY on 5/6/2016.
 */
public class DialogContactAgent extends DialogFragment {
    @Bind(R.id.tvPhone)
    TextView textViewPhone;
    @Bind(R.id.tvSms)
    TextView textViewSms;
    @Bind(R.id.tvEmail)
    TextView textViewEmail;
    private AlertDialog alertDialog;
    public static String phoneAgent;
    public static String nameContact;
    public static String emailContact;
    public static String idContact;

    public static DialogContactAgent getInstance(String phone, String name, String email, String id) {
        phoneAgent = phone;
        nameContact = name;
        emailContact = email;
        idContact = id;
        return new DialogContactAgent();
    }


    @OnClick(R.id.tvCancel)
    public void cancel(View view) {
        this.dismiss();
    }


    @OnClick(R.id.tvPhone)
    public void call(View view) {
        DialogCall.getInstance(phoneAgent).show(getActivity().getSupportFragmentManager(), "CALL");
        this.dismiss();
    }

    @OnClick(R.id.tvSms)
    public void sendSms(View view) {

    }

    @OnClick(R.id.tvEmail)
    public void sendEmail(View view) {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.dialog_contact_agent, null);
        ButterKnife.bind(this, view);
        textViewPhone.setTypeface(FontManager.getTypeface(getActivity().getBaseContext()));
        textViewSms.setTypeface(FontManager.getTypeface(getActivity().getBaseContext()));
        textViewEmail.setTypeface(FontManager.getTypeface(getActivity().getBaseContext()));
        alertDialog = new AlertDialog.Builder(getActivity()).setView(view).create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        return alertDialog;
    }
    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {

        }
    }
}
