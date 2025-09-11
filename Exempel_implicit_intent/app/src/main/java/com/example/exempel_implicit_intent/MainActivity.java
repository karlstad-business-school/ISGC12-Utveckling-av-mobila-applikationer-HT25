package com.example.exempel_implicit_intent;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

        Button btn_email = findViewById(R.id.button2);

        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("plain/text");
                startActivity(Intent.createChooser(i, "EEEEEMAILL"));
            }
        });



        //"default/res/layout/ids/
        Button btn_activity = findViewById(R.id.button3);
        btn_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ACTION = "abc.test.123";
                Intent intent = new Intent();
                intent.setAction(ACTION);

                startActivity(intent);
            }
        });

        Button tel = findViewById(R.id.button1);
        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Intent.ACTION_DIAL);
                a.setData(Uri.parse("tel:123456789"));

                if(a.resolveActivity(getPackageManager()) != null){
                    startActivity(a);
                }

            }
        });
    }
}