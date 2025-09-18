package com.example.exempel_intent_skicka_data;

import java.io.Serializable;

public class Human implements Serializable {

    private String name;
    private int age;
    private boolean student;


    public Human(String n, int a, boolean s){
        name = n;
        age = a;
        student = s;
    }

    public String getName(){
        return name;
    }

    public int getAge(){
        return age;
    }

    public boolean isStudent(){
        return student;
    }
}