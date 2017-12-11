package com.homecaravan.android.consumer.message.messagegetallthreadmvp;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.model.message.MessageThread;
import com.homecaravan.android.consumer.model.message.MessageUserData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.socket.client.Ack;

/**
 * Created by Anh Dao on 9/7/2017.
 */

public class GetAllThreadPresenter {
    private IGetAllThreadView mView;

    public GetAllThreadPresenter(IGetAllThreadView mView) {
        this.mView = mView;
    }

    public void getAllThread(String timeStamp) {

        HomeCaravanApplication.mSocket.emit(Constants.getInstance().THREAD_GET_ALL, timeStamp, new Ack() {
            @Override
            public void call(Object... args) {

                Log.e("DaoDiDem", "Message -> GetAllThreadPresenter: data: "+args[0].toString());
                if(args[0] != null){
                    Type listType = new TypeToken<ArrayList<MessageThread>>() {}.getType();
                    ArrayList<MessageThread> thread = new Gson().fromJson(args[0].toString(), listType);
                    mView.getAllThreadSuccess(thread);
                }else{
                    mView.getAllThreadFail();
                }
            }
        });
    }

    public void getAllUserInfo(JSONArray jArrParticipants) {

        HomeCaravanApplication.mSocket.emit(Constants.getInstance().USER_LOAD_BY_IDS, jArrParticipants, new Ack() {
            @Override
            public void call(Object... args) {
                Log.d("DaoDiDem", "Message -> LoadUserByIdsPresenter: data: "+args[0].toString());
                if(args[0] != null){
                    Type listType = new TypeToken<ArrayList<MessageUserData>>() {}.getType();
                    ArrayList<MessageUserData> participants = new Gson().fromJson(args[0].toString(), listType);
                    mView.getAllUserInfoSuccess(participants);
                }else{
                    mView.getAllUserInfoFail();
                }
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
                RealmResults<MessageThread> result = realm.where(MessageThread.class).equalTo("id", threadId).findAll();
                result.deleteAllFromRealm();
            }
        });
    }

    private ArrayList<MessageUserData> getUserInfo(JSONArray dataArray){
        JSONObject resultJSONObject;
        int n;
        MessageUserData messageUserData;
        ArrayList<MessageUserData> arrMessageUserData = new ArrayList<>();
        String id, createdDatetime, modifiedDatetime, name, data = null, email, avatar;

        Log.d("DaoDiDem", "setUserDataEachThread: ---------");

        try {
            n = dataArray.length();
            for (int i = 0; i < n; i++) {

                resultJSONObject = dataArray.getJSONObject(i);
                id = resultJSONObject.getString(Constants.getInstance().MESSAGE_ID);
                createdDatetime = resultJSONObject.getString(Constants.getInstance().MESSAGE_CREATED_DATE_TIME);
                modifiedDatetime = resultJSONObject.getString(Constants.getInstance().MESSAGE_MODIFIED_DATE_TIME);
                name = resultJSONObject.getString(Constants.getInstance().MESSAGE_NAME);
                if(!resultJSONObject.isNull(Constants.getInstance().MESSAGE_DATA)){
                    data = resultJSONObject.getString(Constants.getInstance().MESSAGE_DATA);
                }
                email = resultJSONObject.getString(Constants.getInstance().MESSAGE_EMAIL);
                avatar = resultJSONObject.getString(Constants.getInstance().MESSAGE_AVATAR);

                messageUserData = new MessageUserData(id, createdDatetime, modifiedDatetime, name, data, email, avatar);
                arrMessageUserData.add(messageUserData);
            }
        } catch (JSONException e) {
            Log.d("DaoDiDem", "Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        return arrMessageUserData;
    }
}
