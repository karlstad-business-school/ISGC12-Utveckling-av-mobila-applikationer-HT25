package com.example.exempel_background_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyBackgroundService extends Service {


    public int onStartCommand(Intent intent, int flags, int startId){
        for(int i = 0; i <= 10; i++){
            synchronized (this){
                try{
                    Log.e("MyBackgroundService", "Waiting: " + i);
                    wait(1000);
                }catch(Exception e){
                    Log.e("MyBackgroundService", e.toString());
                }
            }
        }


        return START_NOT_STICKY;
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
