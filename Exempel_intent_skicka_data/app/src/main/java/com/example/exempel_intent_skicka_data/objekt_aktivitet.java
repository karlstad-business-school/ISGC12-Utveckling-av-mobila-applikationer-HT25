package com.example.exempel_intent_skicka_data;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class objekt_aktivitet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_objekt_aktivitet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView ageTV = findViewById(R.id.tv_obj_age);
        TextView nameTV = findViewById(R.id.tv_obj_name);
        TextView objTV = findViewById(R.id.tv_obj_student);

        Intent intent = getIntent();
        Human human = (Human)intent.getSerializableExtra("human");


        nameTV.setText("Namn: " + human.getName());
        ageTV.setText("Ålder: " + human.getAge());

        String isStudent = "Är student";
        if(human.isStudent() == false){
            isStudent = "Är inte student";
        }
        objTV.setText(isStudent);
    }
}