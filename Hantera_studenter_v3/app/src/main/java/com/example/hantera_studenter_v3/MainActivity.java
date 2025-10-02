package com.example.hantera_studenter_v3;

import android.content.Intent;
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

import java.util.ArrayList;

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

        new DataManager();
        new Database();

        ArrayList<Student> students = new ArrayList<Student>();
        students = DataManager.instance.readFromFile(this);
        Database.instance.setStudents(students);





        idET = findViewById(R.id.student_id);
        nameET = findViewById(R.id.student_name);
        pnrET = findViewById(R.id.student_pnr);
        addBtn = findViewById(R.id.add_btn);
        studentList = findViewById(R.id.student_list);


        studentList.setText(Database.instance.printStudents());

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(idET.getText().toString());
                String name = nameET.getText().toString();
                String pnr = pnrET.getText().toString();
                Student s = new Student(id, name, pnr);

                Database.instance.add(s);
                DataManager.instance.writeToFile(MainActivity.this, Database.instance.getStudents());

                studentList.setText(Database.instance.printStudents());
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        studentList.setText(Database.instance.printStudents());
    }



    public void removeStudent(View view){
        EditText rID = findViewById(R.id.remove_id);
        int id = Integer.parseInt(rID.getText().toString());
        Database.instance.remove(id);

        DataManager.instance.writeToFile(MainActivity.this, Database.instance.getStudents());

        studentList.setText(Database.instance.printStudents());
    }


    public void searchStudent(View view){
        Intent intent = new Intent(MainActivity.this, StudentView.class);

        EditText sID = findViewById(R.id.search_id);
        int id = Integer.parseInt(sID.getText().toString());

        intent.putExtra("id" , id);
        startActivity(intent);
    }

}