package com.example.exempel_background_service_async;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MyBackgroundService extends Service {

    public int onStartCommand(Intent intent, int flags, int startId){

        MyAsyncTask mat = new MyAsyncTask(this);
        mat.execute();

        return START_NOT_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
