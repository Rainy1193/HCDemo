package com.homecaravan.android.consumer.message.messagegetallmvp;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.model.message.ConsumerMessages;
import com.homecaravan.android.consumer.model.message.MessageItem;
import com.homecaravan.android.consumer.model.message.MessageThread;
import com.homecaravan.android.consumer.model.message.MessageThreadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;
import io.socket.client.Ack;

/**
 * Created by Anh Dao on 9/7/2017.
 */

public class MessagesActionPresenter {
    private IMessagesActionView mView;
    private final String TAG = "DaoDiDem";

    public MessagesActionPresenter(IMessagesActionView mView) {
        this.mView = mView;
    }

    public void saveMessages(Realm realm, ArrayList<ConsumerMessages> mArrConsumerMessages, String threadId) {
        deleteMessageByThreadId(realm, threadId);
        saveDataToRealm(realm, mArrConsumerMessages);
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
                        if (Constants.getInstance().MESSAGE_TYPE_IMAGE.equals(messageItem.getType())) {
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
            ArrayList<ConsumerMessages> mArrMessageItem = new ArrayList<>();
            for (ConsumerMessages mess : realm.where(ConsumerMessages.class)
                    .equalTo("messageItem.messageThread.id", threadId).findAll()) {
                mArrMessageItem.add(mess);
            }
            mView.getMessagesFromRealmSuccess(mArrMessageItem);
        }
    }

    public void removeMessage(String id, final int position) {
        Log.e(TAG, "removeMessage: id: " + id);
        HomeCaravanApplication.mSocket.emit(Constants.getInstance().MESSAGE_DELETE, id, new Ack() {
            @Override
            public void call(Object... args) {
                if(args[0] != null){
                    MessageItem messageItem = new Gson().fromJson(args[0].toString(), MessageItem.class);
                    if(messageItem.getStatus().equals(Constants.getInstance().MESSAGE_STATUS_DELETED)){
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

    private ArrayList<MessageItem> setMesssages(JSONArray messages) {
        JSONObject resultJSONObject;
        JSONObject view;
        JSONObject thread;
        int n;
        ArrayList<MessageItem> mArrMessageItem = new ArrayList<>();
        MessageItem messageItem;
        MessageThreadView messageThreadView = null;
        MessageThread messageThread;
        String id, createdDatetime, modifiedDatetime, content, type, status,
                idView, nameView, photoView, contentView, createdDatetimeView, viewType,
                idThread, createdDatetimeThread, modifiedDatetimeThread, dateFormat;
        try {
            n = messages.length();
            for (int i = 0; i < n; i++) {
                resultJSONObject = messages.getJSONObject(i);
                id = resultJSONObject.getString(Constants.getInstance().MESSAGE_ID);
                createdDatetime = resultJSONObject.getString("createdDatetime");
                dateFormat = getDate(createdDatetime);
                modifiedDatetime = resultJSONObject.getString("modifiedDatetime");
                content = resultJSONObject.getString("content");
                type = resultJSONObject.getString("type");
                status = resultJSONObject.getString("status");
                if (Constants.getInstance().MESSAGE_STATUS_DELETED.equals(status)) {
                    continue;
                }
                if (!resultJSONObject.isNull("view")) {
                    view = resultJSONObject.getJSONObject("view");
                    if (view.isNull("id")) {
                        //If id == null then ignore
                        continue;
                    }
                    idView = view.getString(Constants.getInstance().MESSAGE_ID);
                    nameView = view.getString("name");
                    photoView = view.getString("photo");
                    contentView = view.getString("content");
                    createdDatetimeView = view.getString("createdDatetime");
                    viewType = view.getString("type");
                    messageThreadView = new MessageThreadView(idView, nameView, photoView, contentView, createdDatetimeView, viewType);
                }

                thread = resultJSONObject.getJSONObject("thread");
                idThread = thread.getString(Constants.getInstance().MESSAGE_ID);
                createdDatetimeThread = thread.getString("createdDatetime");
                modifiedDatetimeThread = thread.getString("modifiedDatetime");
                messageThread = new MessageThread(idThread, createdDatetimeThread, modifiedDatetimeThread);
                messageItem = new MessageItem(id, createdDatetime, modifiedDatetime, content,
                        messageThreadView, type, status, messageThread, dateFormat);

                if (Constants.getInstance().MESSAGE_TYPE_IMAGE.equals(type)) {
                    JSONObject data = new JSONObject(resultJSONObject.getString("data"));
                    String image = data.getString("fileUrl");
                    messageItem.setImage(image);
                }
                mArrMessageItem.add(messageItem);
            }
            return mArrMessageItem;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getDate(String time) {
        try {
            long timeStamp = Long.parseLong(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeStamp);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM", Locale.US);
            Date currenTimeZone = calendar.getTime();
            return sdf.format(currenTimeZone);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean checkRealmIsEmpty(String threadId, Realm realm) {
        return realm.where(ConsumerMessages.class)
                .equalTo("messageItem.messageThread.id", threadId).findAll().size() == 0;
    }

    private void deleteMessageByThreadId(Realm realm, final String threadId) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                RealmResults<ConsumerMessages> result = realm.where(ConsumerMessages.class).equalTo("messageItem.messageThread.id", threadId).findAll();
                result.deleteAllFromRealm();
            }
        });
    }

    private void saveDataToRealm(Realm realm, final ArrayList<ConsumerMessages> mArrMessage) {
        realm.executeTransaction(new Realm.Transaction() {
            ConsumerMessages message;
            MessageItem messageItem;
            MessageThreadView messageThreadView;
            MessageThread messageThread;

            @Override
            public void execute(@NonNull Realm realm) {
                for (ConsumerMessages mess : mArrMessage) {

                    if (mess.getMessageItem() == null || Constants.getInstance().MESSAGE_STATUS_DELETED.equals(mess.getMessageItem().getStatus())) {
                        continue;
                    }
                    if (mess.getMessageItem().getMessageThreadView() != null) {
                        if (mess.getMessageItem().getMessageThreadView().getId() == null) {
                            continue;
                        }
                        messageThreadView = realm.createObject(MessageThreadView.class);
                        messageThreadView.setId(mess.getMessageItem().getMessageThreadView().getId());
                        messageThreadView.setName(mess.getMessageItem().getMessageThreadView().getName());
                        messageThreadView.setPhoto(mess.getMessageItem().getMessageThreadView().getPhoto());
                        messageThreadView.setContent(mess.getMessageItem().getMessageThreadView().getContent());
                        messageThreadView.setCreatedDatetime(mess.getMessageItem().getMessageThreadView().getCreatedDatetime());
                    }
                    messageThread = realm.createObject(MessageThread.class);
                    messageThread.setId(mess.getMessageItem().getMessageThread().getId());
                    messageThread.setCreatedDatetime(mess.getMessageItem().getMessageThread().getCreatedDatetime());
                    messageThread.setModifiedDatetime(mess.getMessageItem().getMessageThread().getModifiedDatetime());

                    messageItem = realm.createObject(MessageItem.class);
                    messageItem.setId(mess.getMessageItem().getId());
                    messageItem.setCreatedDatetime(mess.getMessageItem().getCreatedDatetime());
                    messageItem.setDateFormat(mess.getMessageItem().getDateFormat());
                    messageItem.setModifiedDatetime(mess.getMessageItem().getModifiedDatetime());
                    messageItem.setContent(mess.getMessageItem().getContent());
                    messageItem.setType(mess.getMessageItem().getType());
                    messageItem.setStatus(mess.getMessageItem().getStatus());
                    messageItem.setMessageThreadView(messageThreadView);
                    messageItem.setMessageThread(messageThread);
                    if (Constants.getInstance().MESSAGE_TYPE_IMAGE.equals(mess.getMessageItem().getType())) {
                        messageItem.setImage(mess.getMessageItem().getImage());
                    }
                    message = realm.createObject(ConsumerMessages.class);
                    message.setMessageItem(messageItem);
                    message.setType(mess.getType());
                    message.setDate(mess.getDate());

                    Log.e(TAG, "Save(conversation) Id: " + mess.getMessageItem().getId() + " content: " + mess.getMessageItem().getContent());
                }
            }
        });
    }

}
