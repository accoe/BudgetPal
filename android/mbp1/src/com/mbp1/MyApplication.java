package com.mbp1;

import json.Singleton;
import android.app.Application;

public class MyApplication extends Application {
	
    @Override
    public void onCreate() {
        super.onCreate();
        initSingleton(); 
    }
     
    protected void initSingleton() { 
        Singleton.initInstance(); 
    }
     
}