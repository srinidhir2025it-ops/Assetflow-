package com.assetflow.model;

public class Employee extends Person {

    private int employeeId;
    private String employeeCode;
    private String department;
    private String designation;
    private String employmentStatus;

    public Employee(int employeeId, String employeeCode,
                    String employeeName, String department,
                    String designation, String email,
                    String employmentStatus) {

        super(employeeName, email);

        this.employeeId = employeeId;
        this.employeeCode = employeeCode;
        this.department = department;
        this.designation = designation;
        this.employmentStatus = employmentStatus;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public String getDepartment() {
        return department;
    }

    public String getDesignation() {
        return designation;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }
}