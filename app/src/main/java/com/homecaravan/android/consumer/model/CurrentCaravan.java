package com.homecaravan.android.consumer.model;


import com.homecaravan.android.consumer.model.responseapi.ResponseCaravan;


public class CurrentCaravan {
    private static CurrentCaravan caravan;
    private ResponseCaravan.CaravanData data;

    public static CurrentCaravan getInstance() {
        if (caravan == null) {
            caravan = new CurrentCaravan();
        }
        return caravan;
    }

    public static CurrentCaravan getCaravan() {
        return caravan;
    }

    public static void setCaravan(CurrentCaravan caravan) {
        CurrentCaravan.caravan = caravan;
    }

    public ResponseCaravan.CaravanData getData() {
        return data;
    }

    public void setData(ResponseCaravan.CaravanData data) {
        this.data = data;
    }
}
