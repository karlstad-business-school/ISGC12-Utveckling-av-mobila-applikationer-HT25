package com.example.exempel_broadcast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private String BROADCAST_ACTION = "com.test.exempel_r5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void send(View view){
        Intent intent = new Intent();
        intent.setAction(BROADCAST_ACTION);
        intent.putExtra("msg", "Hello world!");

        //Nytt för API 26+. Vi måste veta vad appen heter
        intent.setPackage("com.example.exempel_broadcast_receiver");
        sendBroadcast(intent);
    }
}