package com.example.exempel_intent_skicka_data;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
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

        Button btn_intstr = findViewById(R.id.btn_intstr);
        Button btn_obj = findViewById(R.id.btn_obj);


        btn_intstr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, data_aktivitet.class);
                intent.putExtra("text_string", "Detta är en sträng");
                intent.putExtra("text_int", 232);

                startActivity(intent);
            }
        });



        btn_obj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, objekt_aktivitet.class);

                Human human = new Human("Johan", 53, false);
                intent.putExtra("human", human); //Om ni inte har "implements Serializable" i Human.java så får ni en error här!

                startActivity(intent);
            }
        });
    }
}