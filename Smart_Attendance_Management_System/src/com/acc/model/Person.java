package com.acc.model;

public abstract class Person {
    protected int id;
    protected String name;

    public abstract String getRole();

    public int getId() { return id; }
    public String getName() { return name; }
}
