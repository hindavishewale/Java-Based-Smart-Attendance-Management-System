package com.acc.model;

public class Student extends Person {

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getRole() {
        return "Student";
    }
}
