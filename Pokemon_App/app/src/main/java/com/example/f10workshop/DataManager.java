package com.example.f10workshop;


import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataManager {

    public static DataManager instance;
    //private final String FILENAME = "pokemons.txt";
    private final String FILENAME = "data.txt";

    public DataManager(){
        if(instance == null){
            instance = this;
        }
    }

    public void writeToFile(Context context, PokemonData data){
        try{
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);

            oos.close();
            fos.close();
        }catch(Exception e){
            Log.e("Error write file", e.toString());
        }
    }

    public PokemonData readFromFile(Context context){
        PokemonData data = new PokemonData();

        try{
            FileInputStream fis = context.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);

            data = (PokemonData) ois.readObject();
            ois.close();
            fis.close();


        }catch(Exception e){
            Log.e("Error read file", e.toString());
        }

        return data;
    }
}