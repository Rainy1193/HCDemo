package com.homecaravan.android.consumer.consumerbase;


import com.homecaravan.android.consumer.model.UserData;

public class ConsumerUser {
    public static ConsumerUser user;
    public UserData data = new UserData();

    public static ConsumerUser getInstance() {
        if (user == null) {
            user = new ConsumerUser();
        }
        return user;
    }

    public UserData getData() {
        return data;
    }

}
