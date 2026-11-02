
package com.assetflow.repository;

import com.assetflow.DatabaseConnection;
import com.assetflow.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAccountRepository {

    public boolean registerEmployee(
            String employeeCode,
            String email,
            String password) throws SQLException {

        employeeCode = employeeCode.trim();
        email = email.trim();

        if (employeeCode.isEmpty() || email.isEmpty()
                || password == null || password.length() < 8) {
            throw new IllegalArgumentException(
                    "Enter valid details and a password of at least 8 characters."
            );
        }

        String employeeSql =
                "SELECT employee_id FROM employees " +
                "WHERE employee_code = ? AND email = ? " +
                "AND employment_status = 'Active'";

        String accountSql =
                "SELECT account_id FROM user_accounts " +
                "WHERE employee_id = ? OR username = ?";

        String insertSql =
                "INSERT INTO user_accounts " +
                "(username, password_hash, account_role, employee_id, account_status) " +
                "VALUES (?, ?, 'EMPLOYEE', ?, 'ACTIVE')";

        try (Connection connection = DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {
                int employeeId;

                try (PreparedStatement statement =
                        connection.prepareStatement(employeeSql)) {

                    statement.setString(1, employeeCode);
                    statement.setString(2, email);

                    try (ResultSet result = statement.executeQuery()) {

                        if (!result.next()) {
                            throw new IllegalArgumentException(
                                    "Employee details are incorrect or employee is inactive."
                            );
                        }

                        employeeId = result.getInt("employee_id");
                    }
                }

                try (PreparedStatement statement =
                        connection.prepareStatement(accountSql)) {

                    statement.setInt(1, employeeId);
                    statement.setString(2, employeeCode);

                    try (ResultSet result = statement.executeQuery()) {

                        if (result.next()) {
                            throw new IllegalArgumentException(
                                    "An account already exists for this employee."
                            );
                        }
                    }
                }

                String passwordHash =
                        PasswordUtil.hashPassword(password);

                try (PreparedStatement statement =
                        connection.prepareStatement(insertSql)) {

                    statement.setString(1, employeeCode);
                    statement.setString(2, passwordHash);
                    statement.setInt(3, employeeId);

                    statement.executeUpdate();
                }

                connection.commit();
                return true;

            } catch (SQLException | RuntimeException e) {
                connection.rollback();
                throw e;

            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    public AuthenticatedUser authenticate(
            String username,
            String password,
            String role) throws SQLException {

        String sql =
                "SELECT account_role, employee_id, password_hash " +
                "FROM user_accounts " +
                "WHERE username = ? AND account_status = 'ACTIVE'";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username.trim());

            try (ResultSet result = statement.executeQuery()) {

                if (!result.next()) {
                    return null;
                }

                String storedRole = result.getString("account_role");
                String storedHash = result.getString("password_hash");

                if (!storedRole.equals(role)) {
                    return null;
                }

                if (!PasswordUtil.verifyPassword(password, storedHash)) {
                    return null;
                }

                int employeeId = result.getInt("employee_id");

                return new AuthenticatedUser(
                        storedRole,
                        result.wasNull() ? null : employeeId
                );
            }
        }
    }

    public static class AuthenticatedUser {

        private final String role;
        private final Integer employeeId;

        public AuthenticatedUser(String role, Integer employeeId) {
            this.role = role;
            this.employeeId = employeeId;
        }

        public String getRole() {
            return role;
        }

        public Integer getEmployeeId() {
            return employeeId;
        }
    }
}
