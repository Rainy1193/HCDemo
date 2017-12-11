package com.homecaravan.android.consumer.message.messageloginmvp;


import android.util.Log;

import com.google.gson.Gson;
import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.model.message.MessageUser;
import com.homecaravan.android.consumer.model.message.MessageUserData;

import io.socket.client.Ack;

/**
 * Created by Anh Dao on 9/7/2017.
 */

public class LoginPresenter {
    private ILoginView mView;

    public LoginPresenter(ILoginView mView) {
        this.mView = mView;
    }

    public void login(String id) {

        HomeCaravanApplication.mSocket.emit(Constants.getInstance().USER_LOGIN, id, new Ack() {
            @Override
            public void call(Object... args) {
                if(args[0] != null){
                    Log.e("DaoDiDem", "Message -> LoginPresenter: data: "+args[0].toString());
                    MessageUserData user = new Gson().fromJson(args[0].toString(), MessageUserData.class);
                    setDataUser(user);
                    if(MessageUser.getInstance().getData().getId() != null
                            || !MessageUser.getInstance().getData().getId().isEmpty()){
                        mView.loginSuccess();
                    }else{
                        mView.loginFail();
                    }
                }else{
                    Log.e("DaoDiDem", "Message -> loginFail ");
                    mView.loginFail();
                }
            }
        });
    }

    private void setDataUser(MessageUserData user) {
        MessageUser.getInstance().getData().setId(user.getId());
        MessageUser.getInstance().getData().setCreatedDatetime(user.getCreatedDatetime());
        MessageUser.getInstance().getData().setModifiedDatetime(user.getModifiedDatetime());
        MessageUser.getInstance().getData().setName(user.getName());
        MessageUser.getInstance().getData().setData(user.getData());
        MessageUser.getInstance().getData().setEmail(user.getEmail());
        MessageUser.getInstance().getData().setAvatar(user.getAvatar());
    }

}
