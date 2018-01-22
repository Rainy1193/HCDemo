package com.homecaravan.android.consumer.utils;

import com.homecaravan.android.consumer.model.message.MessageUserData;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

import io.realm.RealmList;

/**
 * Created by Anh Dao on 12/13/2017.
 */

public class TextUtils {

    public static String getThreadName(RealmList<MessageUserData> arrUserInThread) {

        int n = arrUserInThread.size();

        if (n < 2) {
            return "No Members";
        } else {
            int count = 0;
            StringBuilder names = new StringBuilder();
            for (MessageUserData member : arrUserInThread) {
                names.append(", ").append(member.getName());

                if (count++ >= 5) {
                    break;
                }
            }
            return names.delete(0, 2).toString();
        }
    }

    public static String getFirstCharacter(String name) {
        if (name == null || name.isEmpty())
            return name;
        String[] s = name.split(" ");
        StringBuilder charName = new StringBuilder();
        int i = 0;
        for (String value : s) {
            charName.append(value.charAt(0));
            if (i++ == 1) {
                break;
            }
        }
        return charName.toString().toUpperCase();
    }

    public static String getLastTime(String time, long timeStampStartOfToday) {
        try {
            long timeStamp = Long.parseLong(time);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeStamp);
//            TimeZone tz = TimeZone.getDefault();
//            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            timeStamp /= 1000;
            Date currenTimeZone = calendar.getTime();
            if (timeStamp >= timeStampStartOfToday) {
                return DateUtils.dateFormatThread().format(currenTimeZone);
            } else {
                return DateUtils.dateFormatThreadLongTimeAgo().format(currenTimeZone);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getLastTime(long timeStamp, long timeStampStartOfToday) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeStamp);
//            TimeZone tz = TimeZone.getDefault();
//            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            timeStamp /= 1000;
            Date currenTimeZone = calendar.getTime();
            if (timeStamp >= timeStampStartOfToday) {
                return DateUtils.dateFormatThread().format(currenTimeZone);
            } else {
                return DateUtils.dateFormatThreadLongTimeAgo().format(currenTimeZone);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Calculate MD5
     *
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String generateMD5(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(data.getBytes());
        byte messageDigest[] = digest.digest();

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < messageDigest.length; i++)
            hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

        return hexString.toString();
    }
}
