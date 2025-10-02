package com.example.exempel_sd_card;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button saveBtn, loadBtn;
    private EditText textInputOutput;

    private static final int REQUEST_NOTHING = 0;
    private static final int REQUEST_WRITE = 1;
    private static final int REQUEST_READ = 2;
    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

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

        //Ifall vi vill skicka ut permissions när appen startar
        //checkPermissions(REQUEST_NOTHING);


        saveBtn = findViewById(R.id.save);
        loadBtn = findViewById(R.id.load);
        textInputOutput = findViewById(R.id.text);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermissions(REQUEST_WRITE)){
                    writeFile();
                }
            }
        });

        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermissions(REQUEST_READ)) {
                    readFile();
                }

            }
        });
    }

    private void readFile(){
        File sdCard = Environment.getExternalStorageDirectory();
        File file = new File(sdCard + "/mapp1/CoolText.txt");
        StringBuilder text = new StringBuilder();

        try{
            BufferedReader br = new BufferedReader(new FileReader((file)));
            String line;

            while((line = br.readLine()) != null){
                text.append(line);
                text.append("\n");
            }

            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        textInputOutput.setText(text);
    }

    private void writeFile(){
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + "/mapp1/");
            dir.mkdir();
            File file = new File(dir, "CoolText.txt");

            FileOutputStream fos;
            String text = textInputOutput.getText().toString();
            byte[] data = text.getBytes(StandardCharsets.UTF_8);
            fos = new FileOutputStream(file);
            fos.write(data);

            fos.flush();
            fos.close();

            textInputOutput.setText("");
        }catch (Exception e){
            Log.e("SDCARD", e.toString());
        }
    }


    private boolean checkPermissions(int requestCode){
        if(permissions != null){
            for(String permission : permissions){
                int result = ContextCompat.checkSelfPermission(this, permission);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{permission}, requestCode);
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkAllPermissions(int[] results){
        if(results == null || results.length == 0){
            return false;
        }

        for(int r: results){
            if(r != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int code, String permissions[], int[] result){
        super.onRequestPermissionsResult(code, permissions, result);

        //I vårt fall måste alla permissions vara godkända.
        if(checkAllPermissions(result) == false){
            Log.e("Permissions", "Permission denined");
            return;
        }

        //Beroende på vad vi gjorde innan
        if(code == REQUEST_WRITE){
            writeFile();
        }else if(code == REQUEST_READ){
            readFile();
        }
    }
}