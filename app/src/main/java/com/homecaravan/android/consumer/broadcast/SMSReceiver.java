package com.homecaravan.android.consumer.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.homecaravan.android.consumer.listener.SMSListener;

import java.util.regex.Pattern;

/**
 * Created by Anh Dao on 11/10/2017.
 */

public class SMSReceiver extends BroadcastReceiver {

    private static SMSListener mListener;

    public static void setListener(SMSListener listener) {
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle myBundle = intent.getExtras();
        SmsMessage[] messages;
        String strMessage = "";

        if (myBundle != null) {
            Object[] pdus = (Object[]) myBundle.get("pdus");

            messages = new SmsMessage[pdus.length];

            for (int i = 0; i < messages.length; i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String format = myBundle.getString("format");
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                strMessage += messages[i].getMessageBody();


                if (messages[i].getOriginatingAddress().equals("Verify")) {
                    String code;
                    String arrSmsMessage[] = strMessage.split(" ");
                    code = arrSmsMessage[arrSmsMessage.length - 1];
                    if(code.length() == 5 && arrSmsMessage[0].equals("Thank")
                            && Pattern.compile("[0-9]*", Pattern.CASE_INSENSITIVE).matcher(code).matches()){
                        mListener.messageReceived(code);
                    }
                }

            }
        }
    }
}
