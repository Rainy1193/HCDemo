package com.homecaravan.android.consumer.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.homecaravan.android.api.Constants;

/**
 * Created by Anh Dao on 12/13/2017.
 * PredferenceUtils for Message
 */

public class PreferenceUtils {

    public static final String PREFERENCE_KEY_USER_ID = "userId";

    // Prevent instantiation
    private PreferenceUtils() {

    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(Constants.getInstance().HOME_CARAVAN_CONSUMER, Context.MODE_PRIVATE);
    }

    public static boolean getSettingsReceiverNewMessageNoti(Context context){
        return getSharedPreferences(context).getBoolean(Constants.getInstance().RECEIVER_NEW_MESSAGE_NOTIFICATION, true);
    }

    public static void setSettingsReceiverNewMessageNoti(Context context, boolean data){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(Constants.getInstance().RECEIVER_NEW_MESSAGE_NOTIFICATION, data).apply();
    }
}
