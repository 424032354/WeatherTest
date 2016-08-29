package com.example.victor.weathertest3;

import android.app.Application;

import com.baidu.apistore.sdk.ApiStoreSDK;

/**
 * Created by victor on 2016/8/19.
 */
public class WeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ApiStoreSDK.init(this, "41cd4bd59934b4ef88c2d2c76a7e2a5b");
    }
}
