package com.homecaravan.android.consumer.message.messagegetallmvp;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.model.message.MessageItem;
import com.homecaravan.android.consumer.model.message.MessageThread;
import com.homecaravan.android.consumer.model.message.MessageUserData;
import com.homecaravan.android.consumer.utils.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import io.socket.client.Ack;

import static com.homecaravan.android.HomeCaravanApplication.TAG;

/**
 * Created by Anh Dao on 9/7/2017.
 */

public class MessagesActionPresenter {
    private IMessagesActionView mView;

    public MessagesActionPresenter(IMessagesActionView mView) {
        this.mView = mView;
    }

    public void saveMessages(Realm realm, ArrayList<MessageItem> mArrMessageItem, String threadId) {
        deleteMessageByThreadId(realm, threadId);
        saveDataToRealm(realm, mArrMessageItem);
    }

    public void saveUserData(Realm realm, ArrayList<MessageUserData> mArrGroupUser, String threadId) {
        deleteGroupUserByThreadId(realm, threadId);
        saveGroupUserToRealm(realm, mArrGroupUser);
    }

    public void getMessages(String id) {
        Log.e(TAG, "getMessages: threadId:" + id);

        Long tsLong1000 = System.currentTimeMillis() / 1000;
        JSONObject json = new JSONObject();
        try {
            json.put(Constants.getInstance().THREAD_ID, id);
            json.put(Constants.getInstance().THREAD_CREATE_TIME, tsLong1000);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HomeCaravanApplication.mSocket.emit(Constants.getInstance().THREAD_GET_MESSAGE, json, new Ack() {
            @Override
            public void call(Object... args) {
                Log.e(TAG, "Message -> GetMessagesPresenter: data: " + args[0].toString());
                if (args[0] != null) {
                    Type listType = new TypeToken<ArrayList<MessageItem>>() {}.getType();
                    ArrayList<MessageItem> mArrMessageItem = new Gson().fromJson(args[0].toString(), listType);
                    for (MessageItem messageItem : mArrMessageItem) {
                        messageItem.setDateFormat(getDate(messageItem.getCreatedDatetime()));
                        if (MessageItem.TYPE_IMAGE.equals(messageItem.getType())) {
                            try {
                                JSONObject data = new JSONObject(messageItem.getData());
                                String image = data.getString("fileUrl");
                                messageItem.setImage(image);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    mView.getMessagesSuccess(mArrMessageItem);
                }else{
                    mView.getMessagesFailed();
                }
            }
        });
    }

    public void getMessagesFromRealm(String threadId, Realm realm) {
        if (checkRealmIsEmpty(threadId, realm)) {
            Log.e(TAG, "getMessagesFromRealm: checkRealmIsEmpty = true");
            mView.getMessagesFailed();
        } else {
            Log.e(TAG, "getMessagesFromRealm: checkRealmIsEmpty = false");
            ArrayList<MessageItem> mArrMessageItem = new ArrayList<>();
            mArrMessageItem.addAll(realm.where(MessageItem.class)
                    .equalTo("messageItem.messageThread.id", threadId).findAll());
            mView.getMessagesFromRealmSuccess(mArrMessageItem);
        }
    }

    public void getGroupUserFromRealm(String threadId, Realm realm) {
        if (checkRealmIsEmpty(threadId, realm)) {
            Log.e(TAG, "getGroupUserFromRealm: checkRealmIsEmpty = true");
            mView.getGroupUserFailed();
        } else {
            Log.e(TAG, "getGroupUserFromRealm: checkRealmIsEmpty = false");
            ArrayList<MessageUserData> mArrGroupUser = new ArrayList<>();
            mArrGroupUser.addAll(realm.where(MessageUserData.class)
                    .equalTo("threadId", threadId).findAll());
            mView.getGroupUserFromRealmSuccess(mArrGroupUser);
        }
    }

    public void removeMessage(String id, final int position) {
        Log.e(TAG, "removeMessage: id: " + id);
        HomeCaravanApplication.mSocket.emit(Constants.getInstance().MESSAGE_DELETE, id, new Ack() {
            @Override
            public void call(Object... args) {
                if(args[0] != null){
                    MessageItem messageItem = new Gson().fromJson(args[0].toString(), MessageItem.class);
                    if(messageItem.getStatus().equals(MessageItem.STATUS_DELETED)){
                        mView.removeMessageSuccess(position);
                    }else{
                        mView.removeMessageFail();
                    }
                }else{
                    mView.removeMessageFail();
                }
            }
        });
    }

    private String getDate(String time) {
        try {
            long timeStamp = Long.parseLong(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeStamp);
            Date currenTimeZone = calendar.getTime();
            return DateUtils.dateFormatThreadLongTimeAgo().format(currenTimeZone);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean checkRealmIsEmpty(String threadId, Realm realm) {
        return realm.where(MessageThread.class)
                .equalTo("messageItem.messageThread.id", threadId).findAll().size() == 0;
    }

    private void deleteMessageByThreadId(Realm realm, final String threadId) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                RealmResults<MessageThread> result = realm.where(MessageThread.class).equalTo("messageItem.messageThread.id", threadId).findAll();
                result.deleteAllFromRealm();
            }
        });
    }

    private void deleteGroupUserByThreadId(Realm realm, final String threadId) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                RealmResults<MessageUserData> result = realm.where(MessageUserData.class).equalTo("threadId", threadId).findAll();
                result.deleteAllFromRealm();
            }
        });
    }

    private void saveDataToRealm(Realm realm, final ArrayList<MessageItem> mArrMessageItem) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.insert(mArrMessageItem);
            }
        });
    }

    private void saveGroupUserToRealm(Realm realm, final ArrayList<MessageUserData> mArrGroupUser) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.insert(mArrGroupUser);
            }
        });
    }

}
