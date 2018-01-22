package com.homecaravan.android.consumer.message.leavethreadmvp;

import android.support.annotation.NonNull;
import android.util.Log;

import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.model.message.MessageThread;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;
import io.socket.client.Ack;

import static com.homecaravan.android.HomeCaravanApplication.TAG;

/**
 * Created by Anh Dao on 12/7/2017.
 */

public class LeaveThreadPresenter {
    private ILeaveThreadView mView;

    public LeaveThreadPresenter(ILeaveThreadView mView){
        this.mView = mView;
    }

    public void leaveThread(final String threadId, final int position){
        HomeCaravanApplication.mSocket.emit(Constants.getInstance().THREAD_LEAVE, threadId, new Ack() {
            @Override
            public void call(Object... args) {
                Log.e(TAG, "leaveThread: "+args[0].toString());
                if(args[0] != null){
                    if(args[0].toString().equals("true")){ //server trả về "true"
                        mView.leaveThreadSuccess(threadId, position);
                    }else
                        mView.leaveThreadFail();
                }else
                    mView.leaveThreadFail();
            }
        });
    }

    public void deleteThread(String threadId, String userId){

        JSONObject json = new JSONObject();
        try {
            json.put(Constants.getInstance().THREAD_ID, threadId);
            json.put(Constants.getInstance().MESSAGE_PART_ID, userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HomeCaravanApplication.mSocket.emit(Constants.getInstance().THREAD_REMOVE_PARTICIPANTS, json);
    }

    public void deleteThreadFromRealm(Realm realm, final String threadId){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                RealmResults<MessageThread> result = realm.where(MessageThread.class)
                        .equalTo("id", threadId).findAll();
                result.deleteAllFromRealm();
            }
        });
    }
}
