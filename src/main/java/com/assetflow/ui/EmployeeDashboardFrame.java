
package com.assetflow.ui;

import com.assetflow.DatabaseConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class EmployeeDashboardFrame extends JFrame {

    private final int employeeId;

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

    private final Font regularFont = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font mediumFont = new Font("Segoe UI", Font.BOLD, 13);
    private final Font headingFont = new Font("Segoe UI", Font.BOLD, 25);
    private final Font sectionFont = new Font("Segoe UI", Font.BOLD, 16);

    private JPanel contentPanel;
    private JLabel pageTitle;

    public EmployeeDashboardFrame(int employeeId) {

        this.employeeId = employeeId;

        setTitle("AssetFlow - Employee Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1050, 680));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(pageBackground);

        root.add(createSidebar(), BorderLayout.WEST);
        root.add(createMainArea(), BorderLayout.CENTER);

        setContentPane(root);

        showDashboard();

        setVisible(true);
    }

    private JPanel createSidebar() {

        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(245, 0));
        sidebar.setBackground(sidebarColor);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(25, 17, 20, 17));

        JLabel logo = new JLabel("AssetFlow");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 27));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("EMPLOYEE PORTAL");
        subtitle.setFont(new Font("Segoe UI", Font.BOLD, 10));
        subtitle.setForeground(new Color(190, 207, 190));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(5));
        sidebar.add(subtitle);
        sidebar.add(Box.createVerticalStrut(35));

        addSidebarButton(sidebar, "Dashboard", this::showDashboard);
        addSidebarButton(sidebar, "My Profile", this::showProfile);
        addSidebarButton(sidebar, "My Assets", this::showMyAssets);
        addSidebarButton(sidebar, "Allocation History", this::showAllocationHistory);
        addSidebarButton(sidebar, "Report a Defect", this::showDefectForm);
addSidebarButton(sidebar, "My Defect Reports", this::showMyDefectReports);
addSidebarButton(sidebar, "My Returns", this::showMyReturns);
        addSidebarButton(sidebar, "Exit Clearance", this::showExitClearance);

        sidebar.add(Box.createVerticalGlue());

        JLabel employeeLabel = new JLabel("EMPLOYEE ACCOUNT");
        employeeLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        employeeLabel.setForeground(new Color(190, 207, 190));
        employeeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(employeeLabel);
        sidebar.add(Box.createVerticalStrut(12));

        addSidebarButton(sidebar, "Logout", this::logout);

        return sidebar;
    }

  
private void addSidebarButton(
        JPanel sidebar, String name, Runnable action) {

    JButton button = new JButton(name);

    button.setFont(mediumFont);
    button.setForeground(Color.WHITE);
    button.setBackground(sidebarColor);
    button.setOpaque(true);
    button.setContentAreaFilled(true);
    button.setBorderPainted(false);
    button.setHorizontalAlignment(SwingConstants.LEFT);
    button.setFocusPainted(false);
    button.setBorder(new EmptyBorder(13, 15, 13, 10));
    button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
    button.setAlignmentX(Component.LEFT_ALIGNMENT);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    button.addActionListener(e -> action.run());

    button.addMouseListener(new java.awt.event.MouseAdapter() {

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            button.setBackground(sidebarHover);
            button.setForeground(Color.WHITE);
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            button.setBackground(sidebarColor);
            button.setForeground(Color.WHITE);
        }
    });

    sidebar.add(button);
    sidebar.add(Box.createVerticalStrut(5));
}


    private JPanel createMainArea() {

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(pageBackground);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(surface);
        topBar.setBorder(new EmptyBorder(18, 28, 18, 28));

        pageTitle = new JLabel("Dashboard");
        pageTitle.setFont(sectionFont);
        pageTitle.setForeground(textColor);

        JLabel roleLabel = new JLabel("ASSETFLOW  |  EMPLOYEE");
        roleLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        roleLabel.setForeground(mutedText);

        topBar.add(pageTitle, BorderLayout.WEST);
        topBar.add(roleLabel, BorderLayout.EAST);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(pageBackground);
        contentPanel.setBorder(new EmptyBorder(25, 28, 25, 28));

        main.add(topBar, BorderLayout.NORTH);
        main.add(contentPanel, BorderLayout.CENTER);

        return main;
    }

    private void setPage(String title, JPanel page) {

        pageTitle.setText(title);

        contentPanel.removeAll();
        contentPanel.add(page, BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    
private void showDashboard() {

    JPanel page = new JPanel();
    page.setBackground(pageBackground);
    page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));

    JPanel welcome = new JPanel(new BorderLayout());
    welcome.setBackground(oliveLight);
    welcome.setBorder(new EmptyBorder(25, 27, 25, 27));
    welcome.setMaximumSize(new Dimension(Integer.MAX_VALUE, 115));

    JLabel welcomeTitle = new JLabel("Welcome to AssetFlow");
    welcomeTitle.setFont(new Font("Segoe UI", Font.BOLD, 23));
    welcomeTitle.setForeground(oliveDark);

    JLabel welcomeSubtitle = new JLabel(
            "Manage your employee information and assigned IT assets."
    );
    welcomeSubtitle.setFont(regularFont);
    welcomeSubtitle.setForeground(mutedText);

    JPanel welcomeText = new JPanel();
    welcomeText.setOpaque(false);
    welcomeText.setLayout(new BoxLayout(welcomeText, BoxLayout.Y_AXIS));
    welcomeText.add(welcomeTitle);
    welcomeText.add(Box.createVerticalStrut(8));
    welcomeText.add(welcomeSubtitle);

    welcome.add(welcomeText, BorderLayout.WEST);

    page.add(welcome);
    page.add(Box.createVerticalStrut(20));

    // Employee information section
    JPanel employeeCard = createCard();
    employeeCard.setLayout(new BorderLayout(0, 15));
    employeeCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 175));

    JLabel employeeHeading = new JLabel("My Employee Details");
    employeeHeading.setFont(sectionFont);
    employeeHeading.setForeground(textColor);

    JPanel employeeDetails = new JPanel(new GridLayout(2, 3, 20, 15));
    employeeDetails.setBackground(Color.WHITE);

    String sql =
            "SELECT employee_code, employee_name, department, " +
            "designation, email, employment_status " +
            "FROM employees WHERE employee_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, employeeId);

        try (ResultSet result = statement.executeQuery()) {

            if (result.next()) {

                employeeDetails.add(createProfileDetail(
                        "Employee Code",
                        result.getString("employee_code")
                ));

                employeeDetails.add(createProfileDetail(
                        "Employee Name",
                        result.getString("employee_name")
                ));

                employeeDetails.add(createProfileDetail(
                        "Department",
                        result.getString("department")
                ));

                employeeDetails.add(createProfileDetail(
                        "Designation",
                        result.getString("designation")
                ));

                employeeDetails.add(createProfileDetail(
                        "Email",
                        result.getString("email")
                ));

                employeeDetails.add(createProfileDetail(
                        "Employment Status",
                        result.getString("employment_status")
                ));

            } else {
                employeeDetails.add(
                        new JLabel("Employee details not found.")
                );
            }
        }

    } catch (SQLException e) {
        showDatabaseError(e);
    }

    employeeCard.add(employeeHeading, BorderLayout.NORTH);
    employeeCard.add(employeeDetails, BorderLayout.CENTER);

    page.add(employeeCard);
    page.add(Box.createVerticalStrut(20));

    JLabel overview = new JLabel("My Overview");
    overview.setFont(sectionFont);
    overview.setForeground(textColor);
    overview.setAlignmentX(Component.LEFT_ALIGNMENT);

    page.add(overview);
    page.add(Box.createVerticalStrut(12));

    JPanel cards = new JPanel(new GridLayout(1, 3, 17, 0));
    cards.setBackground(pageBackground);
    cards.setMaximumSize(new Dimension(Integer.MAX_VALUE, 125));

    cards.add(createStatCard(
            "Currently Allocated",
            getCount(
                    "SELECT COUNT(*) FROM allocations " +
                    "WHERE employee_id = ? AND allocation_status = 'Allocated'"
            ),
            "Assets assigned to me"
    ));

    cards.add(createStatCard(
            "Allocation Records",
            getCount(
                    "SELECT COUNT(*) FROM allocations WHERE employee_id = ?"
            ),
            "My allocation history"
    ));

    cards.add(createStatCard(
            "Returned Assets",
            getCount(
                    "SELECT COUNT(*) FROM asset_returns ar " +
                    "JOIN allocations al ON ar.allocation_id = al.allocation_id " +
                    "WHERE al.employee_id = ?"
            ),
            "My return records"
    ));

    page.add(cards);
    page.add(Box.createVerticalStrut(20));

    JPanel information = createCard();
    information.setLayout(new BorderLayout(10, 10));

    JLabel infoTitle = new JLabel("Quick Information");
    infoTitle.setFont(sectionFont);
    infoTitle.setForeground(textColor);

    JLabel infoText = new JLabel(
        "<html><div style='line-height:1.8'>" +
        "• View your currently allocated assets.<br>" +
        "• Check your previous allocation and return records.<br>" +
        "• Report defects in your assigned IT assets.<br>" +
        "• Track the status of your submitted defect reports.<br>" +
        "• Check your exit clearance and data handover status." +
        "</div></html>"
);

    infoText.setFont(regularFont);
    infoText.setForeground(mutedText);

    information.add(infoTitle, BorderLayout.NORTH);
    information.add(infoText, BorderLayout.CENTER);

    page.add(information);

    setPage("Dashboard", page);
}

    private JPanel createStatCard(String title, int value, String description) {

        JPanel card = createCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(mediumFont);
        titleLabel.setForeground(mutedText);

        JLabel valueLabel = new JLabel(String.valueOf(value));
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        valueLabel.setForeground(oliveDark);

        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descriptionLabel.setForeground(mutedText);

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(descriptionLabel);

        return card;
    }


private JPanel createCard() {

    JPanel card = new JPanel();

    card.setBackground(Color.WHITE);
    card.setForeground(new Color(34, 45, 37));
    card.setOpaque(true);

    card.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(218, 226, 216)),
        new EmptyBorder(20, 22, 20, 22)
    ));

    return card;
}



    private int getCount(String sql) {

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, employeeId);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt(1);
                }
            }

        } catch (SQLException e) {
            showDatabaseError(e);
        }

        return 0;
    }

   
private void showProfile() {

    JPanel page = new JPanel(new BorderLayout(0, 20));
    page.setBackground(pageBackground);

    JPanel heading = new JPanel();
    heading.setBackground(pageBackground);
    heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

    JLabel headingLabel = new JLabel("My Profile");
    headingLabel.setFont(headingFont);
    headingLabel.setForeground(textColor);

    JLabel subtitleLabel = new JLabel(
            "View your registered employee information"
    );
    subtitleLabel.setFont(regularFont);
    subtitleLabel.setForeground(mutedText);

    heading.add(headingLabel);
    heading.add(Box.createVerticalStrut(6));
    heading.add(subtitleLabel);

    JPanel detailsCard = createCard();
    detailsCard.setLayout(new BorderLayout(0, 20));

    JLabel detailsTitle = new JLabel("Employee Information");
    detailsTitle.setFont(sectionFont);
    detailsTitle.setForeground(textColor);

    JPanel detailsGrid = new JPanel(new GridLayout(0, 2, 18, 18));
    detailsGrid.setBackground(Color.WHITE);

    String sql =
            "SELECT employee_id, employee_code, employee_name, " +
            "department, designation, email, employment_status " +
            "FROM employees WHERE employee_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, employeeId);

        try (ResultSet result = statement.executeQuery()) {

            if (result.next()) {

                detailsGrid.add(createProfileDetail(
                        "Employee ID",
                        String.valueOf(result.getInt("employee_id"))
                ));

                detailsGrid.add(createProfileDetail(
                        "Employee Code",
                        result.getString("employee_code")
                ));

                detailsGrid.add(createProfileDetail(
                        "Employee Name",
                        result.getString("employee_name")
                ));

                detailsGrid.add(createProfileDetail(
                        "Department",
                        result.getString("department")
                ));

                detailsGrid.add(createProfileDetail(
                        "Designation",
                        result.getString("designation")
                ));

                detailsGrid.add(createProfileDetail(
                        "Email Address",
                        result.getString("email")
                ));

                detailsGrid.add(createProfileDetail(
                        "Employment Status",
                        result.getString("employment_status")
                ));

            } else {

                detailsGrid.add(new JLabel("Employee details not found."));
            }
        }

    } catch (SQLException e) {
        showDatabaseError(e);
        return;
    }

    detailsCard.add(detailsTitle, BorderLayout.NORTH);
    detailsCard.add(detailsGrid, BorderLayout.CENTER);

    page.add(heading, BorderLayout.NORTH);
    page.add(detailsCard, BorderLayout.CENTER);

    setPage("My Profile", page);
}

private JPanel createProfileDetail(String label, String value) {

    JPanel detail = new JPanel();
    detail.setBackground(oliveLight);
    detail.setLayout(new BoxLayout(detail, BoxLayout.Y_AXIS));
    detail.setBorder(new EmptyBorder(16, 18, 16, 18));

    JLabel labelText = new JLabel(label);
    labelText.setFont(mediumFont);
    labelText.setForeground(mutedText);

    JLabel valueText = new JLabel(
            value == null || value.trim().isEmpty()
                    ? "Not Available"
                    : value
    );

    valueText.setFont(new Font("Segoe UI", Font.BOLD, 15));
    valueText.setForeground(oliveDark);

    detail.add(labelText);
    detail.add(Box.createVerticalStrut(9));
    detail.add(valueText);

    return detail;
}

    private void showMyAssets() {

        showTablePage(
                "My Assets",
                "IT assets currently allocated to you",
                "SELECT a.asset_code, a.asset_name, a.asset_type, " +
                "a.serial_number, al.allocation_date, al.allocation_status " +
                "FROM allocations al JOIN assets a ON al.asset_id = a.asset_id " +
                "WHERE al.employee_id = ? AND al.allocation_status = 'Allocated' " +
                "ORDER BY al.allocation_date DESC",
                new String[]{
                        "Asset Code", "Asset Name", "Type",
                        "Serial Number", "Allocation Date", "Status"
                }
        );
    }

    private void showAllocationHistory() {

        showTablePage(
                "Allocation History",
                "Your previous and current asset allocation records",
                "SELECT a.asset_code, a.asset_name, al.allocation_date, " +
                "COALESCE(DATE_FORMAT(al.return_date, '%Y-%m-%d'), " +
                "'Still Assigned') AS return_date, " +
                "al.allocation_status " +
                "FROM allocations al JOIN assets a ON al.asset_id = a.asset_id " +
                "WHERE al.employee_id = ? " +
                "ORDER BY al.allocation_date DESC",
                new String[]{
                        "Asset Code", "Asset Name",
                        "Allocation Date", "Return Date", "Status"
                }
        );
    }
    
private void showMyDefectReports() {

    showTablePage(
            "My Defect Reports",
            "Track the complaints you have submitted and their resolution status",

            "SELECT r.report_id, a.asset_code, a.asset_name, " +
            "r.reported_date, r.defect_description, r.severity, " +
            "r.report_status, r.admin_notes, r.resolution_date " +
            "FROM asset_defect_reports r " +
            "JOIN assets a ON r.asset_id = a.asset_id " +
            "WHERE r.employee_id = ? " +
            "ORDER BY r.report_id DESC",

            new String[]{
                    "Report ID",
                    "Asset Code",
                    "Asset Name",
                    "Reported Date",
                    "Description",
                    "Severity",
                    "Status",
                    "Admin Notes",
                    "Resolution Date"
            }
    );
}

    private void showMyReturns() {

        showTablePage(
                "My Returns",
                "Your asset return records",
                "SELECT ar.return_id, a.asset_name, ar.actual_return_date, " +
                "ar.return_condition, ar.remarks " +
                "FROM asset_returns ar " +
                "JOIN allocations al ON ar.allocation_id = al.allocation_id " +
                "JOIN assets a ON al.asset_id = a.asset_id " +
                "WHERE al.employee_id = ? " +
                "ORDER BY ar.actual_return_date DESC",
                new String[]{
                        "Return ID", "Asset Name", "Return Date",
                        "Return Condition", "Remarks"
                }
        );
    }

    private void showExitClearance() {

        showTablePage(
                "Exit Clearance",
                "Track your exit formalities and data handover status",
                "SELECT exit_date, assets_returned, data_handover, " +
                "access_revoked, clearance_status " +
                "FROM exit_clearance WHERE employee_id = ?",
                new String[]{
                        "Exit Date", "Assets Returned",
                        "Data Handover", "Access Revoked", "Status"
                }
        );
    }

  
private void showTablePage(
        String title, String subtitle, String sql, String[] columns) {

    JPanel page = new JPanel(new BorderLayout(0, 15));
    page.setBackground(pageBackground);

    JPanel heading = new JPanel();
    heading.setBackground(pageBackground);
    heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

    JLabel headingLabel = new JLabel(title);
    headingLabel.setFont(headingFont);
    headingLabel.setForeground(new Color(34, 45, 37));
    headingLabel.setOpaque(false);

    JLabel subtitleLabel = new JLabel(subtitle);
    subtitleLabel.setFont(regularFont);
    subtitleLabel.setForeground(new Color(75, 90, 78));
    subtitleLabel.setOpaque(false);

    heading.add(headingLabel);
    heading.add(Box.createVerticalStrut(6));
    heading.add(subtitleLabel);

    JPanel tableCard = createCard();
    tableCard.setBackground(Color.WHITE);
    tableCard.setLayout(new BorderLayout(0, 12));

    JTable table = createTable(sql, columns);

    table.setForeground(new Color(34, 45, 37));
    table.setBackground(Color.WHITE);
    table.setSelectionBackground(new Color(213, 228, 205));
    table.setSelectionForeground(new Color(25, 45, 30));
    table.setGridColor(new Color(218, 226, 216));
    table.setOpaque(true);
    table.setFillsViewportHeight(true);

    javax.swing.table.DefaultTableCellRenderer cellRenderer =
            new javax.swing.table.DefaultTableCellRenderer() {

        @Override
        public java.awt.Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {

            super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column
            );

            setForeground(new Color(34, 45, 37));

            if (isSelected) {
                setBackground(new Color(213, 228, 205));
            } else {
                setBackground(Color.WHITE);
            }

            setFont(regularFont);
            setOpaque(true);

            return this;
        }
    };

    for (int i = 0; i < table.getColumnCount(); i++) {
        table.getColumnModel().getColumn(i)
                .setCellRenderer(cellRenderer);
    }

    table.getTableHeader().setBackground(new Color(220, 233, 211));
    table.getTableHeader().setForeground(new Color(30, 55, 35));
    table.getTableHeader().setFont(mediumFont);
    table.getTableHeader().setOpaque(true);

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBorder(BorderFactory.createEmptyBorder());
    scrollPane.getViewport().setBackground(Color.WHITE);

    JButton refreshButton = createActionButton("Refresh");

    refreshButton.addActionListener(e ->
            showTablePage(title, subtitle, sql, columns)
    );

    JPanel tableHeader = new JPanel(new BorderLayout());
    tableHeader.setBackground(Color.WHITE);

    JLabel recordsLabel = new JLabel("Records");
    recordsLabel.setFont(sectionFont);
    recordsLabel.setForeground(new Color(34, 45, 37));
    recordsLabel.setOpaque(false);

    tableHeader.add(recordsLabel, BorderLayout.WEST);
    tableHeader.add(refreshButton, BorderLayout.EAST);

    tableCard.add(tableHeader, BorderLayout.NORTH);
    tableCard.add(scrollPane, BorderLayout.CENTER);

    page.add(heading, BorderLayout.NORTH);
    page.add(tableCard, BorderLayout.CENTER);

    setPage(title, page);
}

   
private JTable createTable(String sql, String[] columns) {

    DefaultTableModel model = new DefaultTableModel(columns, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, employeeId);

        try (ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Object[] row = new Object[columns.length];

                for (int i = 0; i < columns.length; i++) {
                    row[i] = result.getObject(i + 1);
                }

                model.addRow(row);
            }
        }

    } catch (SQLException e) {
        showDatabaseError(e);
    }

    JTable table = new JTable(model);
    
table.setDefaultRenderer(Object.class,
    new javax.swing.table.DefaultTableCellRenderer() {

        @Override
        public java.awt.Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {

            super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column
            );

            setForeground(new Color(34, 45, 37));

            if (isSelected) {
                setBackground(new Color(213, 228, 205));
            } else {
                setBackground(Color.WHITE);
            }

            setFont(regularFont);
            setOpaque(true);

            return this;
        }
    }
);


    table.setFont(regularFont);
    table.setRowHeight(38);
    table.setForeground(new Color(34, 45, 37));
    table.setBackground(Color.WHITE);
    table.setSelectionBackground(new Color(213, 228, 205));
    table.setSelectionForeground(new Color(25, 45, 30));
    table.setGridColor(new Color(218, 226, 216));
    table.setShowVerticalLines(false);
    table.setOpaque(true);

    table.setFillsViewportHeight(true);

    table.getTableHeader().setFont(mediumFont);
    table.getTableHeader().setBackground(new Color(220, 233, 211));
    table.getTableHeader().setForeground(new Color(30, 55, 35));
    table.getTableHeader().setPreferredSize(new Dimension(0, 42));
    table.getTableHeader().setReorderingAllowed(false);
    table.getTableHeader().setOpaque(true);

    return table;
}

    private void showDefectForm() {

        JPanel page = new JPanel(new BorderLayout(0, 18));
        page.setBackground(pageBackground);

        JLabel heading = new JLabel("Report a Defect");
        heading.setFont(headingFont);
        heading.setForeground(textColor);

        JLabel subtitle = new JLabel(
                "Report a problem with an IT asset currently allocated to you."
        );
        subtitle.setFont(regularFont);
        subtitle.setForeground(mutedText);

        JPanel headingPanel = new JPanel();
        headingPanel.setBackground(pageBackground);
        headingPanel.setLayout(new BoxLayout(headingPanel, BoxLayout.Y_AXIS));
        headingPanel.add(heading);
        headingPanel.add(Box.createVerticalStrut(6));
        headingPanel.add(subtitle);

        JPanel form = createCard();
        form.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(9, 8, 9, 8);

        JTextField assetCode = new JTextField();
        assetCode.setFont(regularFont);
        assetCode.setPreferredSize(new Dimension(350, 38));

        JTextArea description = new JTextArea(5, 30);
        description.setFont(regularFont);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);

        JScrollPane descriptionScroll = new JScrollPane(description);
        descriptionScroll.setPreferredSize(new Dimension(350, 120));

        JComboBox<String> severity = new JComboBox<>(
                new String[]{"Low", "Medium", "High", "Critical"}
        );
        severity.setFont(regularFont);
        severity.setPreferredSize(new Dimension(350, 38));

        addFormField(form, gbc, "Asset Code", assetCode);
        addFormField(form, gbc, "Defect Description", descriptionScroll);
        addFormField(form, gbc, "Severity", severity);

        JButton submit = createActionButton("Submit Report");
        submit.addActionListener(e -> {

            String code = assetCode.getText().trim();
            String details = description.getText().trim();
            String level = (String) severity.getSelectedItem();

            if (code.isEmpty() || details.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, "Please enter the asset code and defect description."
                );
                return;
            }

            reportDefect(code, details, level);
        });

        gbc.gridy++;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        form.add(submit, gbc);

        page.add(headingPanel, BorderLayout.NORTH);
        page.add(form, BorderLayout.CENTER);

        setPage("Report a Defect", page);
    }

    private void addFormField(
            JPanel form, GridBagConstraints gbc,
            String label, JComponent field) {

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        gbc.gridwidth = 1;

        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(mediumFont);
        fieldLabel.setForeground(textColor);

        form.add(fieldLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;

        form.add(field, gbc);
    }

    private void reportDefect(
            String assetCode, String description, String severity) {

        String sql =
                "INSERT INTO asset_defect_reports " +
                "(employee_id, asset_id, reported_date, defect_description, " +
                "severity, report_status) " +
                "SELECT ?, a.asset_id, CURRENT_DATE, ?, ?, 'OPEN' " +
                "FROM allocations al " +
                "JOIN assets a ON al.asset_id = a.asset_id " +
                "WHERE al.employee_id = ? " +
                "AND a.asset_code = ? " +
                "AND al.allocation_status = 'Allocated'";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, employeeId);
            statement.setString(2, description);
            statement.setString(3, severity);
            statement.setInt(4, employeeId);
            statement.setString(5, assetCode);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(
                        this, "Defect report submitted successfully."
                );
                showDefectForm();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "This asset code is not currently allocated to you."
                );
            }

        } catch (SQLException e) {
            showDatabaseError(e);
        }
    }

   
private JButton createActionButton(String text) {

    JButton button = new JButton(text);
    button.setFont(mediumFont);
    button.setForeground(Color.WHITE);
    button.setBackground(oliveDark);
    button.setFocusPainted(false);
    button.setOpaque(true);
    button.setContentAreaFilled(true);
    button.setBorder(new EmptyBorder(10, 18, 10, 18));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    return button;
}


    private void logout() {

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Logout",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            dispose();
            new LoginFrame();
        }
    }

    private void showDatabaseError(SQLException e) {

        JOptionPane.showMessageDialog(
                this,
                "Unable to load dashboard data:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
    
private void styleLabel(JLabel label) {

    label.setForeground(new Color(34, 45, 37));
    label.setFont(regularFont);
    label.setOpaque(false);
}

}
