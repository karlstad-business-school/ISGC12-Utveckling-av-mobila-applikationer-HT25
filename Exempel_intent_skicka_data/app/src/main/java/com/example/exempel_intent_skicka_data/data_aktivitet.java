package com.example.exempel_intent_skicka_data;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class data_aktivitet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_data_aktivitet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        TextView intTV = findViewById(R.id.tv_int);
        TextView stringTV = findViewById(R.id.tv_string);

        Bundle extras = getIntent().getExtras();
        String str = "";
        str = extras.getString("text_string");

        int number = getIntent().getIntExtra("text_int", -1);


        intTV.setText(number + "");
        stringTV.setText(str);
    }
}