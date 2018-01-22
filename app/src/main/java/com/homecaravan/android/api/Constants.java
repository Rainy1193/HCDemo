package com.homecaravan.android.api;

import android.os.Environment;

import java.io.File;

public class Constants {
    private static Constants constant;
    public String API_VERSION = "v2/";
    public static final String API_VERSION_CODE = "2";
    public String URL_BASE = "http://api.homecaravan.net/";
    public String URL_BASE_CONSUMER = "http://api.consumer.homecaravan.net/";
    public String PHOTO_ACCOUNT_SMALL = URL_BASE + "uploads/account_avatar/small/";
    public String PHOTO_ACCOUNT = URL_BASE + "uploads/account_avatar/";
    public static final String AUTHORIZATION = "Basic aG9tZWNhcmF2YW46U3Ryb25nZXIyMDE1IQ==";
    public static final String API_KEY = "V4tPT5XOSssROGHa/SNXpQ==";
    public static final String FOLDER = Environment.getExternalStorageDirectory() + File.separator + "HomeCaravan";
    public String URL_LISTING_MAP = URL_BASE + "uploads/listing_small/[listing ID]/916/[image name]";
    public String URL_LISTING_NET = URL_BASE + "uploads/listing_normal/[listing ID]/169/[image name]";
    public String URL_LISTING_ORIGINAL = URL_BASE + "uploads/listing_original/";
    public String URL_LISTING_BOOK_NET = URL_BASE + "uploads/listing_normal/[listing ID]/169/[image name]";
    public String URL_LISTING_DETAIL_NET = URL_BASE + "uploads/listing_normal/[listing ID]/916/[image name]";
    public String URL_LISTING_DETAIL_VIEW_NET = URL_BASE + "uploads/listing_large/[listing ID]/916/[image name]";
    public static final String DEVICE_TOKEN = "TKE0vajiGGXU9ARa1keye7JyB2Aw8vpEJC+vmlfS7yE=";

    //Message
    public final String URL_BASE_CONSUMER_MESSAGE = "http://services.homecaravan.com:8000";
    public final String URL_BASE_CONSUMER_MESSAGE_UPLOAD_IMAGE = "http://services.homecaravan.com:8001";
    public final String USER_LOGIN = "user.login";
    public final String USER_LOAD_BY_IDS = "user.loadByIds";
    public final String THREAD = "thread";
    public final String THREAD_KEY_REMOVE_PARTICIPANTS = "removeParticipant";
    public final String THREAD_LOAD = "thread.load";
    public final String THREAD_UPDATE = "thread.update";
    public final String THREAD_ID = "threadId";
    public final String THREAD_GET_ALL = "thread.getAll";
    public final String THREAD_READ = "thread.read";
    public final String THREAD_GET_MESSAGE = "thread.getMessages";
    public final String THREAD_CREATE = "thread.create";
    public final String THREAD_CREATE_TIME = "createTime";
    public final String THREAD_REMOVE_PARTICIPANTS = "thread.removeParticipants";
    public final String THREAD_LEAVE = "thread.leave";
    public final String THREAD_ADD_PARTICIPANTS = "thread.addParticipants";
    public final String MESSAGE_SEND_MESSAGE = "message.sendToThread";
    public final String MESSAGE_THREAD_TYPING = "thread.typing";
    public final String MESSAGE_SEND_TO_THREAD = "sendToThread";
    public final String MESSAGE_MESSAGE = "message";
    public final String MESSAGE_DELETE = "message.delete";
    public final String MESSAGE_ID = "id";
    public final String MESSAGE_DATA = "data";
    public final String MESSAGE_VIEW = "view";
    public final String MESSAGE_PARTICIPANTS = "participants";
    public final String MESSAGE_PART_ID = "partId";
    public final String MESSAGE_CREATED_DATE_TIME = "createdDatetime";
    public final String MESSAGE_CREATED_BY = "createdBy";
    public final String MESSAGE_MODIFIED_DATE_TIME = "modifiedDatetime";
    public final String MESSAGE_MODIFIED_BY = "modifiedBy";
    public final String MESSAGE_EMAIL = "email";
    public final String MESSAGE_AVATAR = "avatar";
    public final String MESSAGE_NAME = "name";
    public final String MESSAGE_PHOTO = "photo";
    public final String MESSAGE_CONTENT = "content";
    public final String MESSAGE_COMMAND = "command";
    public final String MESSAGE_COMMAND_ADD = "ADD";
    public final String MESSAGE_COMMAND_UPDATE = "UPDATE";
    public final String MESSAGE_COMMAND_DELETE = "DELETE";
    public final String MESSAGE_COMMAND_TYPING = "TYPING";
    public final String MESSAGE_KEY = "key";
    public final String MESSAGE_KEY_PARTICIPANTS = "participants";
    public final String MESSAGE_VALUE = "value";

    //CIA
    public final String URL_BASE_CONSUMER_CIA = "http://service.homecaravan.com:8002";
    public final String CIA_GOOGLE_DIRECTION_API_KEY = "AIzaSyDZy0BfgLMN-n-BVv-6WPoRs0rGfdOd5lM";
    public final String CIA_LOGIN = "user.login";
    public final String CIA_USERNAME = "username";
    public final String CIA_TEAM = "team";
    public final String CIA_HOME_CARAVAN = "HomeCaravan";
    public final String CIA_ACTIVE = "user.active_caravan";
    public final String CIA_DEACTIVE = "user.deactive_caravan";
    public final String CIA_GPS_UPDATE = "gps.update";
    public final String CIA_GPS_RECEIVE = "gps";

    //Name of SharedPreferences
    public final String HOME_CARAVAN_CONSUMER = "homecaravanConsumer";
    public final String RECEIVER_NEW_MESSAGE_NOTIFICATION = "RECEIVER_NEW_MESSAGE_NOTIFICATION";
    public final String THREAD_ID_TURN_OFF_NOTIFICATION_LIST = "THREAD_ID_TURN_OFF_NOTIFICATION_LIST";
    public final String NEW_MESSAGE_THREAD_ID_LIST = "NEW_MESSAGE_THREAD_ID_LIST";
    public final String NEW_MESSAGE_COUNT = "NEW_MESSAGE_COUNT";
    public final String CIA_FATEST_INTERVAL = "CIA_FATEST_INTERVAL";
    public final String CIA_UPDATE_INTERVAL = "CIA_UPDATE_INTERVAL";
    public final String CONTACT_LIST_INVITED = "CONTACT_LIST_INVITED";
    public final String MESSAGE_HAS_GET_ALL_THREAD = "MESSAGE_HAS_GET_ALL_THREAD";
    public final String MESSAGE_LAST_TIME_STAMP_GET_THREAD = "MESSAGE_LAST_TIME_STAMP_GET_THREAD";



    public static Constants getInstance() {
        if (constant == null) {
            constant = new Constants();
        }
        return constant;
    }

    public String getAPI_VERSION() {
        return API_VERSION;
    }

    public String getURL_BASE() {
        return URL_BASE;
    }

    public String getURL_AVATAR() {
        return URL_BASE + "users/avatar/";
    }

    public void setURL_BASE(String URL_BASE) {
        this.URL_BASE = URL_BASE;
        PHOTO_ACCOUNT_SMALL = this.URL_BASE + "uploads/account_avatar/small/";
        PHOTO_ACCOUNT = this.URL_BASE + "uploads/account_avatar/";
        URL_LISTING_MAP = this.URL_BASE + "uploads/listing_small/[listing ID]/916/[image name]";
        URL_LISTING_NET = this.URL_BASE + "uploads/listing_normal/[listing ID]/169/[image name]";
        URL_LISTING_ORIGINAL = this.URL_BASE + "uploads/listing_original/";
        URL_LISTING_BOOK_NET = this.URL_BASE + "uploads/listing_normal/[listing ID]/169/[image name]";
        URL_LISTING_DETAIL_NET = this.URL_BASE + "uploads/listing_normal/[listing ID]/916/[image name]";
        URL_LISTING_DETAIL_VIEW_NET = this.URL_BASE + "uploads/listing_large/[listing ID]/916/[image name]";
    }

    public String getPHOTO_ACCOUNT_SMALL() {
        return PHOTO_ACCOUNT_SMALL;
    }

    public void setPHOTO_ACCOUNT_SMALL(String PHOTO_ACCOUNT_SMALL) {
        this.PHOTO_ACCOUNT_SMALL = PHOTO_ACCOUNT_SMALL;
    }

    public String getPHOTO_ACCOUNT() {
        return PHOTO_ACCOUNT;
    }

    public void setPHOTO_ACCOUNT(String PHOTO_ACCOUNT) {
        this.PHOTO_ACCOUNT = PHOTO_ACCOUNT;
    }


    public String getURL_LISTING_MAP() {
        return URL_LISTING_MAP;
    }

    public void setURL_LISTING_MAP(String URL_LISTING_MAP) {
        this.URL_LISTING_MAP = URL_LISTING_MAP;
    }

    public String getURL_LISTING_NET() {
        return URL_LISTING_NET;
    }

    public void setURL_LISTING_NET(String URL_LISTING_NET) {
        this.URL_LISTING_NET = URL_LISTING_NET;
    }

    public String getURL_LISTING_ORIGINAL() {
        return URL_LISTING_ORIGINAL;
    }

    public void setURL_LISTING_ORIGINAL(String URL_LISTING_ORIGINAL) {
        this.URL_LISTING_ORIGINAL = URL_LISTING_ORIGINAL;
    }

    public String getURL_LISTING_BOOK_NET() {
        return URL_LISTING_BOOK_NET;
    }

    public void setURL_LISTING_BOOK_NET(String URL_LISTING_BOOK_NET) {
        this.URL_LISTING_BOOK_NET = URL_LISTING_BOOK_NET;
    }

    public String getURL_LISTING_DETAIL_NET() {
        return URL_LISTING_DETAIL_NET;
    }

    public void setURL_LISTING_DETAIL_NET(String URL_LISTING_DETAIL_NET) {
        this.URL_LISTING_DETAIL_NET = URL_LISTING_DETAIL_NET;
    }

    public String getURL_LISTING_DETAIL_VIEW_NET() {
        return URL_LISTING_DETAIL_VIEW_NET;
    }

    public void setURL_LISTING_DETAIL_VIEW_NET(String URL_LISTING_DETAIL_VIEW_NET) {
        this.URL_LISTING_DETAIL_VIEW_NET = URL_LISTING_DETAIL_VIEW_NET;
    }

    public String getURL_BASE_CONSUMER() {
        return URL_BASE_CONSUMER;
    }
}
