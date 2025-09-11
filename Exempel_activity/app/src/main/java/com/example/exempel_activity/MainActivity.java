package com.example.exempel_activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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

        Button btnActivity1 = findViewById(R.id.btn_activity_1);
        Button btnActivity2 = findViewById(R.id.btn_activity_2);

        //Tänk på "this" i detta fall
        //"this" refererar till det vi är inne i och inte enbart huvudklassen
        //Explicit
        btnActivity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("ActivityTest", "Innan activity!");

                Intent i = new Intent(MainActivity.this, activity_test1.class);
                startActivity(i);

                Log.e("ActivityTest", "Efter activity!");
            }
        });


        btnActivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity2();
            }
        });



    }

    //Explicit
    private void startActivity2(){
        Intent i = new Intent(MainActivity.this, activity_test2.class);
        startActivity(i);
    }
}