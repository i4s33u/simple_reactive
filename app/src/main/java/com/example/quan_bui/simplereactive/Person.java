package com.example.quan_bui.simplereactive;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quan Bui on 4/12/16.
 */
public class Person {

    private int id;
    private String name;
    private int age;

    private List<Person> people = new ArrayList<>();

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> person) {
        this.people = person;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
