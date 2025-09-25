package com.example.exempel_background_service_async;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class MyAsyncTask extends AsyncTask<Void, Void, Void> {
    private Context context;
    public MyAsyncTask(Context context){
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        for(int i = 0; i <= 10; i++){
            synchronized (this){
                try{
                    Log.e("MyAsyncTask", "Waiting: " + i);
                    wait(1000);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        super.onPostExecute(result);

        Intent intent = new Intent(context, MyBackgroundService.class);
        context.stopService(intent);
    }
}
