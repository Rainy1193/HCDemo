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

import com.homecaravan.android.R;

/**
 * Created by RAINY on 5/6/2016.
 */
public class DialogCall extends DialogFragment {
    public static String phoneAgent;
    private TextView textViewPhone;
    private AlertDialog alertDialog;

    public static DialogCall getInstance(String phone) {
        phoneAgent = phone;
        return new DialogCall();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        try {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.dialog_call, null);
            textViewPhone = (TextView) view.findViewById(R.id.tvPhone);
            textViewPhone.setText(phoneAgent);
            view.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            view.findViewById(R.id.tvCall).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CALL_PHONE)) {
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
                        } else {
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
                        }
                    } else {
                        dismiss();
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + phoneAgent));
                        startActivity(intent);
                    }
                }
            });

            alertDialog = new AlertDialog.Builder(getActivity()).setView(view).create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            alertDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            return alertDialog;
        } catch (Exception e) {
            return null;
        }
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
                dismiss();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneAgent));
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}