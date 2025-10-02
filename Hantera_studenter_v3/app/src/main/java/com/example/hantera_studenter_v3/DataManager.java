package com.example.hantera_studenter_v3;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataManager {
    public static DataManager instance;
    private final String FILENAME = "students.txt";

    public DataManager(){
        if(instance == null){
            instance = this;
        }
    }

    public void writeToFile(Context context, ArrayList<Student> list){
        try{
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);

            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);

            oos.close();
            fos.close();
        } catch (Exception e) {
            Log.e("WRITE TO FILE", e.toString());
        }
    }

    public ArrayList<Student> readFromFile(Context context){
        ArrayList<Student> students = new ArrayList<Student>();

        try{
            FileInputStream fis = context.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);

            students = (ArrayList<Student>) ois.readObject();

            ois.close();
            fis.close();
        } catch (Exception e) {
            Log.e("READ FROM FILE", e.toString());
        }


        return students;
    }
}
