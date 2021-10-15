package com.poly.dotsave;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

public class mApp  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
    }
}
