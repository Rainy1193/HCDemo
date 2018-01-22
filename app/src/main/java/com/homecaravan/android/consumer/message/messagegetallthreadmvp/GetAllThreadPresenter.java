package com.homecaravan.android.consumer.message.messagegetallthreadmvp;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.model.message.MessageThread;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.socket.client.Ack;

import static com.homecaravan.android.HomeCaravanApplication.TAG;

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

                Log.e(TAG, "Message -> GetAllThreadPresenter: data: "+args[0].toString());
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

    public void threadUnRead(String threadId){
        HomeCaravanApplication.mSocket.emit(Constants.getInstance().THREAD_READ, threadId);
    }
}
