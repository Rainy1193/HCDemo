package com.homecaravan.android.consumer.message.getuserinthreadmvp;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.model.message.MessageUserData;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.socket.client.Ack;

import static com.homecaravan.android.HomeCaravanApplication.TAG;

/**
 * Created by Anh Dao on 12/5/2017.
 */

public class GetUserInThreadPresenter {

    private IGetUserInThreadView mView;

    public GetUserInThreadPresenter(IGetUserInThreadView mView){
        this.mView = mView;
    }

    public void getAllUserInfo(JSONArray jArrParticipants) {

        HomeCaravanApplication.mSocket.emit(Constants.getInstance().USER_LOAD_BY_IDS, jArrParticipants, new Ack() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "Message -> GetUserInThreadPresenter data: " + args[0].toString());
                if (args[0] != null) {
                    Type listType = new TypeToken<ArrayList<MessageUserData>>() {
                    }.getType();
                    ArrayList<MessageUserData> participants = new Gson().fromJson(args[0].toString(), listType);
                    mView.getAllUserInfoSuccess(participants);
                } else {
                    mView.getAllUserInfoFail();
                }
            }
        });
    }
}
