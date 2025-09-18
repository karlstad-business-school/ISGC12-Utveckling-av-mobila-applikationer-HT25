package com.example.hantera_studenter_v2;

public class Student {
    private int id;
    private String namn;
    private String pNr;

    public Student(int id, String namn, String pNr){
        this.id = id;
        this.namn = namn;
        this.pNr = pNr;
    }


    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.namn;
    }

    public String getpNr(){
        return this.pNr;
    }
}
