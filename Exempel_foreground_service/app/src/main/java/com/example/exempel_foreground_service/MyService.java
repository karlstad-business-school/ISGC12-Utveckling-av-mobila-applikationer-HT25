package com.example.exempel_foreground_service;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyService extends Service {

    private static final String ID = "counting_service";
    private static final int NOTIFICATION_ID = 97531;

    private boolean running;
    private Thread thread;

    @Override
    public void onCreate(){
        super.onCreate();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(ID, "Counting", NotificationManager.IMPORTANCE_LOW);
            channel.setDescription("I am counting from 0 to 10!");
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        if(running){
            return START_NOT_STICKY;
        }

        running = true;

        startForeground(NOTIFICATION_ID, buildNotification("Count strarting", 0));

        thread = new Thread(() -> {
            try{

                for(int i = 0; i <= 10; i++){
                    if(running){
                        Notification n = buildNotification("Counter at : " + i, i);
                        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        manager.notify(NOTIFICATION_ID, n);
                        Thread.sleep(1000);
                    }
                }

            }catch(InterruptedException exception){
                Log.e("Service", exception.toString());
            }finally{
                running = false;
                stopForeground(true);
                stopSelf();
            }
        });

        thread.start();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){
        running = false;
        if(thread != null){
            thread.interrupt();
        }
        super.onDestroy();

    }


    private Notification buildNotification(String text, int progress){
        Intent app = new Intent(this, MainActivity.class);
        int flags = PendingIntent.FLAG_IMMUTABLE;
        if(Build.VERSION.SDK_INT < 23){
            flags = 0;
        }
        PendingIntent pending = PendingIntent.getActivity(this, 0, app, flags);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ID);
        builder.setSmallIcon(android.R.drawable.ic_media_play);
        builder.setContentTitle("This is a counter!");
        builder.setContentText(text);
        builder.setOnlyAlertOnce(true);
        builder.setOngoing(true);
        builder.setContentIntent(pending);
        builder.setProgress(10, Math.min(progress, 10), false);

        return  builder.build();

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
