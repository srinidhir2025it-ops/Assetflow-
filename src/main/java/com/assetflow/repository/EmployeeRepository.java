package com.assetflow.repository;

import com.assetflow.DatabaseConnection;
import com.assetflow.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    public List<Employee> getAllEmployees() throws SQLException {

        List<Employee> employees = new ArrayList<>();

        String sql = "SELECT employee_id, employee_code, employee_name, department, designation, email, employment_status FROM employees ORDER BY employee_id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Employee employee = new Employee(
                    resultSet.getInt("employee_id"),
                    resultSet.getString("employee_code"),
                    resultSet.getString("employee_name"),
                    resultSet.getString("department"),
                    resultSet.getString("designation"),
                    resultSet.getString("email"),
                    resultSet.getString("employment_status")
                );

                employees.add(employee);
            }
        }

        return employees;
    }

    public boolean addEmployee(
            String employeeCode,
            String employeeName,
            String department,
            String designation,
            String email,
            Date joiningDate) throws SQLException {

        String sql = "INSERT INTO employees (employee_code, employee_name, department, designation, email, joining_date, employment_status) VALUES (?, ?, ?, ?, ?, ?, 'Active')";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, employeeCode);
            statement.setString(2, employeeName);
            statement.setString(3, department);
            statement.setString(4, designation);
            statement.setString(5, email);
            statement.setDate(6, joiningDate);

            return statement.executeUpdate() > 0;
        }
    }
}