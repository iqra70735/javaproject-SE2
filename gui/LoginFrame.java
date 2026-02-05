package learnify.gui;

import javax.swing.*;
import java.awt.*;
import learnify.database.DataManager;
import learnify.models.*;
import learnify.utilities.PasswordUtil;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton loginButton, registerButton;
    private DataManager dataManager;

    public LoginFrame() {
        dataManager = DataManager.getInstance();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Learnify - Login");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(25, 78, 132));
        headerPanel.setPreferredSize(new Dimension(500, 80));
        JLabel titleLabel = new JLabel("Learnify LMS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel roleLabel = new JLabel("Select Role:");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(roleLabel, gbc);

        roleComboBox = new JComboBox<>(new String[]{"STUDENT", "TEACHER", "ADMIN"});
        roleComboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(roleComboBox, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1; gbc.gridy = 2;
        formPanel.add(passwordField, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 248, 255));

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(25, 78, 132));
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(100, 35));
        loginButton.addActionListener(e -> handleLogin());

        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBackground(new Color(100, 150, 200));
        registerButton.setForeground(Color.WHITE);
        registerButton.setPreferredSize(new Dimension(100, 35));
        registerButton.addActionListener(e -> openRegisterDialog());

        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(registerButton);

        // Add Panels to Main
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String selectedRole = (String) roleComboBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password");
            return;
        }

        User user = dataManager.getUserByUsername(username);

        // Debug prints to trace issues
        System.out.println("=== LOGIN DEBUG ===");
        System.out.println("Input Username: " + username);
        System.out.println("Input Password: " + password);
        System.out.println("Selected Role: " + selectedRole);
        System.out.println("User Found: " + (user != null ? user.getUsername() : "null"));
        System.out.println("Stored Password: " + (user != null ? user.getPassword() : "null"));
        System.out.println("User Role: " + (user != null ? user.getRole() : "null"));

        if (user == null) {
            JOptionPane.showMessageDialog(this, "User not found");
            return;
        }

        if (!user.getRole().equals(selectedRole)) {
            JOptionPane.showMessageDialog(this, "Selected role does not match user role");
            return;
        }

        if (!PasswordUtil.verifyPassword(password, user.getPassword())) {
            JOptionPane.showMessageDialog(this, "Incorrect password");
            return;
        }

        // Successful login
        this.dispose();
        switch (selectedRole) {
            case "STUDENT" -> new StudentDashboard((Student) user).setVisible(true);
            case "TEACHER" -> new TeacherDashboard((Teacher) user).setVisible(true);
            case "ADMIN" -> new AdminDashboard((Admin) user).setVisible(true);
            default -> {}
        }
    }

    private void openRegisterDialog() {
        JDialog registerDialog = new JDialog(this, "Register", true);
        registerDialog.setSize(400, 400);
        registerDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField regUsername = new JTextField(15);
        JPasswordField regPassword = new JPasswordField(15);
        JTextField regEmail = new JTextField(15);
        JComboBox<String> regRole = new JComboBox<>(new String[]{"STUDENT", "TEACHER", "ADMIN"});

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; panel.add(regUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; panel.add(regPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; panel.add(regEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Role:"), gbc);
        gbc.gridx = 1; panel.add(regRole, gbc);

        JButton submitButton = new JButton("Register");
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;

        submitButton.addActionListener(e -> {
            String username = regUsername.getText().trim();
            String password = new String(regPassword.getPassword()).trim();
            String email = regEmail.getText().trim();
            String role = (String) regRole.getSelectedItem();

            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(registerDialog, "All fields are required");
                return;
            }

            if (dataManager.getUserByUsername(username) != null) {
                JOptionPane.showMessageDialog(registerDialog, "Username already exists");
                return;
            }

            String hashedPassword = PasswordUtil.hashPassword(password);
            String userId = role + "_" + System.currentTimeMillis();

            if (role.equals("STUDENT")) {
                Student student = new Student(userId, username, hashedPassword, email, 
                                            java.time.LocalDate.now().toString());
                dataManager.addUser(student);
            } else if (role.equals("TEACHER")) {
                Teacher teacher = new Teacher(userId, username, hashedPassword, email,
                                            java.time.LocalDate.now().toString(), "General");
                dataManager.addUser(teacher);
            } else if (role.equals("ADMIN")) {
                Admin admin = new Admin(userId, username, hashedPassword, email,
                                       java.time.LocalDate.now().toString(), "SUPER_ADMIN");
                dataManager.addUser(admin);
            }

            JOptionPane.showMessageDialog(registerDialog, "Registration successful!");
            registerDialog.dispose();
        });

        panel.add(submitButton, gbc);
        registerDialog.add(panel);
        registerDialog.setVisible(true);
    }
}
