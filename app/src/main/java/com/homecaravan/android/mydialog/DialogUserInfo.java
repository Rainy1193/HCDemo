package com.homecaravan.android.mydialog;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.R;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.ui.CircleImageView;
import com.homecaravan.android.ui.FontManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vankhoadesign on 7/7/16.
 */
public class DialogUserInfo extends DialogFragment {
    @Bind(R.id.tvPhone)
    TextView textViewPhone;
    @Bind(R.id.tvSms)
    TextView textViewSms;
    @Bind(R.id.tvHome)
    TextView textHome;

    @Bind(R.id.info_full_name)
    TextView tvFullName;
    @Bind(R.id.info_company_title)
    TextView tvCompanyTitle;
    @Bind(R.id.info_company_address)
    TextView tvCompanyAddress;
    @Bind(R.id.info_company_phone)
    TextView tvCompanyPhone;
    @Bind(R.id.info_phone)
    TextView tvPhone;
    @Bind(R.id.info_appt_time)
    TextView tvApptTime;
    @Bind(R.id.ivAvatar)
    CircleImageView userAvatar;
    private Picasso picasso;

    public static JSONObject userData;
    public static String phoneAgent;
    public static String listing_id = "";
    public static String full_name = "";
    public static String email = "";
    public static String photo = "";
    public static String phone = "";
    public static String company_title = "";
    public static String company_address = "";
    public static String company_phone = "";
    public static String appt_time = "";
    private AlertDialog alertDialog;

    public static DialogUserInfo getInstance(JSONObject _userData) {
        userData = _userData;
        return new DialogUserInfo();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.dialog_user_info, null);
        picasso = HomeCaravanApplication.getInstance().buildPicasso();
        ButterKnife.bind(this, view);

        textViewPhone.setTypeface(FontManager.getTypeface(getActivity().getBaseContext()));
        textViewSms.setTypeface(FontManager.getTypeface(getActivity().getBaseContext()));
        textHome.setTypeface(FontManager.getTypeface(getActivity().getBaseContext()));
        try {
            full_name = userData.getString("FULL_NAME");
            email = userData.getString("EMAIL");
            photo = userData.getString("PHOTO");
            phone = userData.getString("PHONE");
            company_title = userData.getString("CONPANY_TITLE");
            company_address = userData.getString("COMPANY_ADDRESS");
            company_phone = userData.getString("COMPANY_PHONE");
            appt_time = userData.getString("APPT_TIME");
            tvFullName.setText(full_name);
            tvPhone.setText(phone);
            if (company_title == null || company_title.isEmpty() || company_title.trim().length() == 0 || company_title.equalsIgnoreCase("null")) {
                tvCompanyTitle.setVisibility(View.GONE);
                tvCompanyTitle.setEnabled(false);

            } else {
                tvCompanyTitle.setVisibility(View.VISIBLE);
                tvCompanyTitle.setText(company_title);
            }
            if (company_address == null || company_address.isEmpty() || company_address.trim().length() == 0 || company_address.equalsIgnoreCase("null")) {
                tvCompanyAddress.setVisibility(View.GONE);
                tvCompanyAddress.setEnabled(false);

            } else {
                tvCompanyAddress.setVisibility(View.VISIBLE);
                tvCompanyAddress.setText(company_address);
            }

            if (appt_time == null || appt_time.isEmpty()) {
                tvApptTime.setVisibility(View.GONE);
                tvApptTime.setEnabled(false);
            } else {
                tvApptTime.setVisibility(View.VISIBLE);
                tvApptTime.setText("Appointment: on " + getTime(appt_time) + ", " + appt_time.substring(11, 16));
            }
            picasso.load(Constants.getInstance().getPHOTO_ACCOUNT_SMALL() +
                    photo).placeholder(R.drawable.image_default).fit().centerCrop().into(userAvatar, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    picasso.load(Constants.getInstance().getPHOTO_ACCOUNT() +
                            photo).fit().centerCrop().placeholder(R.drawable.image_default).into(userAvatar);
                }
            });


        } catch (JSONException je) {

        }

        view.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        view.findViewById(R.id.tvPhone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    HomeCaravanApplication.askPermission(getActivity(), getActivity(), Manifest.permission.CALL_PHONE, 1);
                } else {
                    Intent call = new Intent(Intent.ACTION_CALL);
                    call.setData(Uri.parse("tel:" + phone));
                    startActivity(call);
                }

            }
        });
        view.findViewById(R.id.tvSms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phone, null)));
            }
        });

        alertDialog = new AlertDialog.Builder(getActivity()).setView(view).create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        return alertDialog;
    }

    private String getTime(String dateTime) {
        Locale.setDefault(Locale.US);
        String time = dateTime.substring(0, 10);
        Calendar calendar = Calendar.getInstance();
        int year = Integer.parseInt(time.substring(0, 4));
        int mouth = Integer.parseInt(time.substring(5, 7)) - 1;
        int day = Integer.parseInt(time.substring(8, 10));
        calendar.set(year, mouth, day);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, MMM, d");
        return dateFormatter.format(calendar.getTime());
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:" + phone));
                startActivity(call);
            } else {
                Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
