package com.homecaravan.android.consumer.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.model.message.SkeletonNewMessage;

/**
 * Created by Anh Dao on 11/24/2017.
 */

public class ApplicationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
//        HomeCaravanApplication.mSocket.disconnect();
//        HomeCaravanApplication.mSocket.close();
        HomeCaravanApplication.mLoginSocketSuccess = false;
        SharedPreferences mPrefs = getSharedPreferences(Constants.getInstance().HOME_CARAVAN_CONSUMER, Context.MODE_PRIVATE);
        if (mPrefs != null) {
            SharedPreferences.Editor edit = mPrefs.edit();
            edit.putString(Constants.getInstance().NEW_MESSAGE_THREAD_ID_LIST, TextUtils.join(",", SkeletonNewMessage.getInstance().getData()));
            edit.putInt(Constants.getInstance().NEW_MESSAGE_COUNT, SkeletonNewMessage.getInstance().getData().size());
            edit.apply();
        }

        super.onTaskRemoved(rootIntent);
        //stop service
        this.stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }
}
