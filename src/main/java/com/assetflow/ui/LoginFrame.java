
package com.assetflow.ui;

import com.assetflow.repository.UserAccountRepository;
import com.assetflow.repository.UserAccountRepository.AuthenticatedUser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;

    private final UserAccountRepository repository =
            new UserAccountRepository();

    private final Color sidebarColor = new Color(24, 43, 40);
    private final Color sidebarHover = new Color(43, 69, 59);
    private final Color olive = new Color(76, 112, 76);
    private final Color oliveDark = new Color(39, 66, 48);
    private final Color oliveLight = new Color(229, 238, 222);
    private final Color pageBackground = new Color(243, 246, 241);
    private final Color surface = Color.WHITE;
    private final Color borderColor = new Color(218, 226, 216);
    private final Color textColor = new Color(34, 45, 37);
    private final Color mutedText = new Color(91, 105, 94);

    private final Font regularFont =
            new Font("Segoe UI", Font.PLAIN, 14);

    private final Font mediumFont =
            new Font("Segoe UI", Font.BOLD, 13);

    private final Font headingFont =
            new Font("Segoe UI", Font.BOLD, 27);

    public LoginFrame() {

        setTitle("AssetFlow | Login");
        setSize(950, 620);
        setMinimumSize(new Dimension(850, 550));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(pageBackground);

        mainPanel.add(createBrandPanel(), BorderLayout.WEST);
        mainPanel.add(createLoginPanel(), BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createBrandPanel() {

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(340, 620));
        panel.setBackground(sidebarColor);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(75, 40, 50, 40));

        JLabel brand = new JLabel("AssetFlow");
        brand.setFont(new Font("Segoe UI", Font.BOLD, 34));
        brand.setForeground(Color.WHITE);
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel smallTitle = new JLabel("IT ASSET MANAGEMENT");
        smallTitle.setFont(new Font("Segoe UI", Font.BOLD, 11));
        smallTitle.setForeground(new Color(190, 207, 186));
        smallTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel line = new JPanel();
        line.setBackground(olive);
        line.setMaximumSize(new Dimension(55, 4));
        line.setPreferredSize(new Dimension(55, 4));
        line.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel heading = new JLabel(
                "<html>Manage your<br>IT assets with<br>confidence.</html>"
        );
        heading.setFont(new Font("Segoe UI", Font.BOLD, 25));
        heading.setForeground(Color.WHITE);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel description = new JLabel(
                "<html>One place to manage employees,<br>" +
                "assets, allocations and exit clearance.</html>"
        );
        description.setFont(regularFont);
        description.setForeground(new Color(196, 207, 196));
        description.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(brand);
        panel.add(Box.createVerticalStrut(7));
        panel.add(smallTitle);
        panel.add(Box.createVerticalStrut(35));
        panel.add(line);
        panel.add(Box.createVerticalStrut(35));
        panel.add(heading);
        panel.add(Box.createVerticalStrut(18));
        panel.add(description);

        return panel;
    }

    private JPanel createLoginPanel() {

        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setBackground(pageBackground);
        outerPanel.setBorder(new EmptyBorder(35, 45, 35, 45));

        JPanel card = new JPanel();
        card.setBackground(surface);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor),
                new EmptyBorder(35, 40, 35, 40)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel("Welcome Back");
        heading.setFont(headingFont);
        heading.setForeground(textColor);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Sign in to continue to AssetFlow");
        subtitle.setFont(regularFont);
        subtitle.setForeground(mutedText);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(heading);
        card.add(Box.createVerticalStrut(7));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(30));

        JLabel roleLabel = createLabel("Login As");

        roleBox = new JComboBox<>(
                new String[]{"Admin", "Employee"}
        );

        roleBox.setFont(regularFont);
        roleBox.setBackground(Color.WHITE);
        roleBox.setForeground(textColor);
        roleBox.setPreferredSize(new Dimension(340, 42));
        roleBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        roleBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        roleBox.setSelectedIndex(0);
        roleBox.setMaximumRowCount(2);

        roleBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus
                );

                label.setFont(regularFont);
                label.setBorder(new EmptyBorder(5, 10, 5, 10));

                return label;
            }
        });

        card.add(roleLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(roleBox);
        card.add(Box.createVerticalStrut(20));

        JLabel usernameLabel = createLabel("Username");

        usernameField = new JTextField();
        styleTextField(usernameField);

        card.add(usernameLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(usernameField);
        card.add(Box.createVerticalStrut(20));

        JLabel passwordLabel = createLabel("Password");

        passwordField = new JPasswordField();
        styleTextField(passwordField);

        card.add(passwordLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(passwordField);
        card.add(Box.createVerticalStrut(28));

        JButton loginButton = new JButton("Login");

        loginButton.setFont(mediumFont);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(olive);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        loginButton.setPreferredSize(new Dimension(340, 44));

        loginButton.addActionListener(e -> login());

        card.add(loginButton);
        card.add(Box.createVerticalStrut(18));

        JButton registerButton =
                new JButton("New Employee? Register Here");

        registerButton.setFont(mediumFont);
        registerButton.setForeground(oliveDark);
        registerButton.setBackground(surface);
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerButton.addActionListener(e ->
                new EmployeeRegistrationFrame()
        );

        card.add(registerButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);

        outerPanel.add(card, gbc);

        return outerPanel;
    }

    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);
        label.setFont(mediumFont);
        label.setForeground(textColor);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        return label;
    }

    private void styleTextField(JTextField field) {

        field.setFont(regularFont);
        field.setForeground(textColor);
        field.setBackground(Color.WHITE);
        field.setCaretColor(olive);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor),
                new EmptyBorder(8, 12, 8, 12)
        ));

        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        field.setPreferredSize(new Dimension(340, 42));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void login() {

        String username = usernameField.getText().trim();
        char[] password = passwordField.getPassword();

        String selectedRole = (String) roleBox.getSelectedItem();

        String role = "Admin".equals(selectedRole)
                ? "ADMIN" : "EMPLOYEE";

        if (username.isEmpty() || password.length == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter your username and password."
            );

            Arrays.fill(password, '\0');
            return;
        }

        try {

            AuthenticatedUser user =
                    repository.authenticate(
                            username,
                            new String(password),
                            role
                    );

            if (user == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid username, password or account role."
                );

            } else if ("ADMIN".equals(user.getRole())) {

                JOptionPane.showMessageDialog(
                        this,
                        "Admin login successful!"
                );

                dispose();
                new AssetFlowUI();

            } else {

                int employeeId = user.getEmployeeId();

                dispose();
                new EmployeeDashboardFrame(employeeId);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database error: " + e.getMessage()
            );

        } finally {

            Arrays.fill(password, '\0');
            passwordField.setText("");
        }
    }
}
