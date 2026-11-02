package com.assetflow.model;

public class Person {

    private String employeeName;
    private String email;

    public Person(String employeeName, String email) {
        this.employeeName = employeeName;
        this.email = email;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmail() {
        return email;
    }
}

