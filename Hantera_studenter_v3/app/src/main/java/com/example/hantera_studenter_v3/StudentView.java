package com.example.hantera_studenter_v3;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StudentView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        TextView idText = findViewById(R.id.student_id);
        TextView nameText = findViewById(R.id.student_name);
        TextView pnrText = findViewById(R.id.student_pnr);
        Button removeBtn = findViewById(R.id.remove_btn);


        Intent intent = getIntent();
        if(!intent.hasExtra("id")){
            Log.e("Intent StudentView", "Det finns inget ID vid start av aktivitet");
            return;
        }
        int id = intent.getIntExtra("id", -1);

        if(id != -1){
            Student student = Database.instance.get(id);

            if(student != null){
                idText.setText(student.getId() + "");
                nameText.setText(student.getName());
                pnrText.setText(student.getpNr());
            }
        }




        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database.instance.remove(id);
                finish();
            }
        });
    }
}