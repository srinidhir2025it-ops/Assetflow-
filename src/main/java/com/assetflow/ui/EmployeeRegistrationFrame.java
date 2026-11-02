
package com.assetflow.ui;

import com.assetflow.repository.UserAccountRepository;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class EmployeeRegistrationFrame extends JFrame {

    private JTextField employeeCodeField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private final UserAccountRepository repository =
            new UserAccountRepository();

    private final Color olive = new Color(92, 107, 65);
    private final Color oliveDark = new Color(39, 66, 48);
    private final Color background = new Color(246, 247, 241);

    public EmployeeRegistrationFrame() {

        setTitle("AssetFlow - Employee Registration");
        setSize(520, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(background);
        mainPanel.setBorder(new EmptyBorder(35, 45, 35, 45));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Employee Registration");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setForeground(olive);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Create your AssetFlow employee account");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitle.setForeground(Color.DARK_GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(subtitle);
        mainPanel.add(Box.createVerticalStrut(30));

        employeeCodeField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();

        addField(mainPanel, "Employee Code", employeeCodeField);
        addField(mainPanel, "Registered Email", emailField);
        addField(mainPanel, "Password (minimum 8 characters)", passwordField);
        addField(mainPanel, "Confirm Password", confirmPasswordField);

    
JButton registerButton = new JButton("Create Account");

registerButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
registerButton.setBackground(oliveDark);
registerButton.setForeground(Color.WHITE);
registerButton.setFocusPainted(false);
registerButton.setOpaque(true);
registerButton.setContentAreaFilled(true);
registerButton.setBorderPainted(false);
registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
registerButton.setMaximumSize(new Dimension(400, 44));
registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

registerButton.addActionListener(e -> registerEmployee());

mainPanel.add(Box.createVerticalStrut(18));
mainPanel.add(registerButton);
mainPanel.add(Box.createVerticalStrut(15));

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> dispose());

        mainPanel.add(backButton);

        add(mainPanel);
        setVisible(true);
    }

    private void addField(
            JPanel panel,
            String labelText,
            JComponent field) {

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(400, 36));
        field.setMaximumSize(new Dimension(400, 36));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(6));
        panel.add(field);
        panel.add(Box.createVerticalStrut(17));
    }

    private void registerEmployee() {

        String employeeCode =
                employeeCodeField.getText().trim();

        String email =
                emailField.getText().trim();

        char[] password =
                passwordField.getPassword();

        char[] confirmPassword =
                confirmPasswordField.getPassword();

        if (employeeCode.isEmpty() || email.isEmpty()
                || password.length == 0
                || confirmPassword.length == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields."
            );
            return;
        }

        if (!new String(password).equals(new String(confirmPassword))) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match."
            );
            return;
        }

        if (password.length < 8) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password must contain at least 8 characters."
            );
            return;
        }

        try {
            repository.registerEmployee(
                    employeeCode,
                    email,
                    new String(password)
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Employee account created successfully!"
            );

            dispose();

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database error: " + e.getMessage()
            );

        } finally {
            java.util.Arrays.fill(password, '\0');
            java.util.Arrays.fill(confirmPassword, '\0');
        }
    }
}
