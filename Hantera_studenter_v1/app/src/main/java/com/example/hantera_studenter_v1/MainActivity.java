package com.example.hantera_studenter_v1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText idET, nameET, pnrET;
    private Button addBtn;
    private TextView studentList;

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

        new Database();

        idET = findViewById(R.id.student_id);
        nameET = findViewById(R.id.student_name);
        pnrET = findViewById(R.id.student_pnr);
        addBtn = findViewById(R.id.add_btn);
        studentList = findViewById(R.id.student_list);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(idET.getText().toString());
                String name = nameET.getText().toString();
                String pnr = pnrET.getText().toString();

                Student s = new Student(id, name, pnr);
                Database.instance.add(s);
                studentList.setText(Database.instance.printStudents());
            }
        });


    }

    public void removeStudent(View view) {
        EditText rID = findViewById(R.id.remove_id);
        int id = Integer.parseInt(rID.getText().toString());

        Database.instance.remove(id);
        studentList.setText(Database.instance.printStudents());
    }
}