package com.homecaravan.android.consumer.message.messagegetallthreadmvp;

import com.google.gson.Gson;
import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.model.message.MessageThread;
import com.homecaravan.android.consumer.model.message.MessageThreadView;

import org.json.JSONArray;
import org.json.JSONObject;

import io.realm.RealmList;
import io.socket.client.Ack;

/**
 * Created by Anh Dao on 10/25/2017.
 * Get thread info by id
 */

public class GetThreadPresenter {
    private IGetThreadView mView;

    public GetThreadPresenter(IGetThreadView mView) {
        this.mView = mView;
    }

    public void getThread(String id) {
        HomeCaravanApplication.mSocket.emit(Constants.getInstance().THREAD_LOAD, id, new Ack() {
            @Override
            public void call(Object... args) {
                if (args[0] != null) {
                    MessageThread thread = new Gson().fromJson(args[0].toString(), MessageThread.class);
                    mView.getThreadSuccess(thread);
                } else {
                    mView.getThreadFail();
                }
            }
        });
    }

    private MessageThread setThreadData(JSONObject data) {
        MessageThread messageThread = new MessageThread();
        try {
            messageThread.setId(data.getString(Constants.getInstance().MESSAGE_ID));
            messageThread.setCreatedDatetime(data.getString(Constants.getInstance().MESSAGE_CREATED_DATE_TIME));
            messageThread.setModifiedDatetime(data.getString(Constants.getInstance().MESSAGE_MODIFIED_DATE_TIME));
            messageThread.setCreatedBy(data.getString(Constants.getInstance().MESSAGE_CREATED_BY));
            if (!data.isNull(Constants.getInstance().MESSAGE_MODIFIED_BY)){
                messageThread.setModifiedBy(data.getString(Constants.getInstance().MESSAGE_MODIFIED_BY));
            }
            if (!data.isNull(Constants.getInstance().MESSAGE_DATA)) {
                messageThread.setData(data.getString(Constants.getInstance().MESSAGE_DATA));
            }
            if(!data.isNull(Constants.getInstance().MESSAGE_NAME)){
                messageThread.setName(data.getString(Constants.getInstance().MESSAGE_NAME));
            }
            JSONArray participants = data.getJSONArray(Constants.getInstance().MESSAGE_PARTICIPANTS);
            int n = participants.length();
            RealmList<String> arrParticipants = new RealmList<>();
            for (int i = 0; i < n; i++) {
                arrParticipants.add(participants.getString(i));
            }
            messageThread.setParticipants(arrParticipants);
            if(!data.isNull(Constants.getInstance().MESSAGE_VIEW)){
                JSONObject view = data.getJSONObject(Constants.getInstance().MESSAGE_VIEW);
                MessageThreadView messageView = new MessageThreadView();
//                if(!view.isNull(Constants.getInstance().MESSAGE_ID)){
//                    messageView.setId(view.getString(Constants.getInstance().MESSAGE_ID));
//                }
                messageView.setName(view.getString(Constants.getInstance().MESSAGE_NAME));
                messageView.setPhoto(view.getString(Constants.getInstance().MESSAGE_PHOTO));
                messageView.setContent(view.getString(Constants.getInstance().MESSAGE_CONTENT));
                messageView.setCreatedDatetime(view.getString(Constants.getInstance().MESSAGE_CREATED_DATE_TIME));
                messageThread.setMessageThreadView(messageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return messageThread;
        }
        return messageThread;
    }

}
