package com.example.hantera_studenter_v1;

import java.util.ArrayList;

public class Database {
    private ArrayList<Student> students;

    public static Database instance;

    public Database(){
        students = new ArrayList<Student>();
        if(instance == null){
            instance = this;
        }
    }

    public void add(Student s){
        students.add(s);
    }

    public void remove(int id){
        Student s = null;
        for(int i = 0; i < students.size(); i++){
            if(students.get(i).getId() == id){
                s = students.get(i);
                break;
            }
        }
        if(s != null){
            students.remove(s);
        }
    }

    public Student get(int id){
        for(int i = 0; i < students.size(); i++){
            if(students.get(i).getId() == id){
                return students.get(i);
            }
        }

        return null;
    }

    public String printStudents(){
        String print = "";

        for(int i = 0; i < students.size(); i++){
            print += students.get(i).getId() + " ";
            print += students.get(i).getName() + " ";
            print += students.get(i).getpNr() + "\n";
        }

        return print;
    }

    public ArrayList<Student> getStudents(){
        return this.students;
    }
}