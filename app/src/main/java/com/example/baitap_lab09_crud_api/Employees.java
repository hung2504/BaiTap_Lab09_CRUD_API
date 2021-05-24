package com.example.baitap_lab09_crud_api;

import java.io.Serializable;

public class Employees implements Serializable {
    private int id;
    private String name;
    private String gender;
    private int age;
    private String mail;

    public Employees(int id, String name, String gender, int age, String mail) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.mail = mail;
    }

    public Employees(int id) {
        this.id = id;
    }

    public Employees() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
