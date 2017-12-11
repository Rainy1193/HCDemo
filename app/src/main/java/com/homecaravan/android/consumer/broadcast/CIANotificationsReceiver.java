package com.homecaravan.android.consumer.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.activity.MainActivityConsumer;

/**
 * Created by Anh Dao on 10/19/2017.
 */

public class CIANotificationsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(),
                R.mipmap.ic_launcher);

        String caravanID = intent.getStringExtra("CaravanID");
        String caravanName = intent.getStringExtra("CaravanName");
        String caravanStartTime = intent.getStringExtra("CaravanStartTime");
        String notificationContent = "Your caravan will start at " + caravanStartTime;
        int uniqueID = (int) System.currentTimeMillis();
        String[] split = caravanID.split("-");
        if(split.length > 1){
            uniqueID = Integer.parseInt(split[1]);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(largeIcon)
                .setContentTitle(caravanName)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentText(notificationContent);

        Intent notificationIntent = new Intent(context, MainActivityConsumer.class);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notificationIntent.putExtra("CARAVAN_IN_ACTION", true);
        notificationIntent.putExtra("CARAVAN_IN_ACTION_ID", caravanID);
        PendingIntent contentIntent = PendingIntent.getActivity(context, uniqueID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setContentIntent(contentIntent);
        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(caravanID ,uniqueID, notification);
    }
}
