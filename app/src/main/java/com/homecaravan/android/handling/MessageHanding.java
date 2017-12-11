package com.homecaravan.android.handling;

/**
 * Created by RAINY on 3/30/2016.
 */
public class MessageHanding {
    public static String getMessage(String rawMessage) {
        if (rawMessage.contains("\"")) {
            return rawMessage.substring(rawMessage.indexOf("\"") + 1, rawMessage.lastIndexOf("\""));
        }
        return rawMessage;
    }
}
