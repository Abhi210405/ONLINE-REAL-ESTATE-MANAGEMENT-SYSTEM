package com.msp.realestate;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SignupFrame extends JFrame {
    private JTextField userField, emailField, phoneField;
    private JPasswordField passField, confirmField;
    private JComboBox<String> roleBox;

    public SignupFrame() {
        setTitle("MSP Construction - Sign Up");
        setSize(420, 460);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Create New Account", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        main.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        userField = new JTextField(18);
        emailField = new JTextField(18);
        phoneField = new JTextField(18);
        passField = new JPasswordField(18);
        confirmField = new JPasswordField(18);
        roleBox = new JComboBox<>(new String[]{"GENERAL", "ADMIN"});

        JLabel[] labels = {new JLabel("Username:"), new JLabel("Email:"), new JLabel("Phone:"), new JLabel("Password:"), new JLabel("Confirm Password:"), new JLabel("Role:")};
        JComponent[] fields = {userField, emailField, phoneField, passField, confirmField, roleBox};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            form.add(labels[i], gbc);
            gbc.gridx = 1;
            form.add(fields[i], gbc);
        }

        JButton signupBtn = new JButton("Sign Up");
        gbc.gridx = 0; gbc.gridy = labels.length; gbc.gridwidth = 2;
        form.add(signupBtn, gbc);

        signupBtn.addActionListener(e -> performSignup());

        main.add(form, BorderLayout.CENTER);
        add(main);
    }

    private void performSignup() {
        String username = userField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = new String(passField.getPassword());
        String confirm = new String(confirmField.getPassword());
        String role = (String) roleBox.getSelectedItem();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill required fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Passwords don't match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseManager.getConnection()) {
            String sql = "INSERT INTO users (username, password, email, phone, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.setString(4, phone);
            pstmt.setString(5, role);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            new LoginFrame().setVisible(true);
            dispose();
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                JOptionPane.showMessageDialog(this, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
