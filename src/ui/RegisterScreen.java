package ui;

import models.User;
import dao.UserDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterScreen extends JFrame {
    
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JComboBox<String> userTypeComboBox;
    private JButton registerButton;
    private JButton backToLoginButton;
    private UserDAO userDAO;
    
    public RegisterScreen() {
        userDAO = new UserDAO();
        
        setTitle("Sign Up");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);
        
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(usernameLabel, gbc);
        
        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(usernameField, gbc);
        
        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(emailLabel, gbc);
        
        emailField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(emailField, gbc);
        
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(passwordLabel, gbc);
        
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(passwordField, gbc);
        
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(confirmPasswordLabel, gbc);
        
        confirmPasswordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 4;
        mainPanel.add(confirmPasswordField, gbc);
 
        JLabel userTypeLabel = new JLabel("Register as:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(userTypeLabel, gbc);
        
        userTypeComboBox = new JComboBox<>(new String[]{"student", "admin"});
        gbc.gridx = 1;
        gbc.gridy = 5;
        mainPanel.add(userTypeComboBox, gbc);
        
        registerButton = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(registerButton, gbc);
        
        backToLoginButton = new JButton("Already have an account? Sign In");
        backToLoginButton.setBorderPainted(false);
        backToLoginButton.setContentAreaFilled(false);
        backToLoginButton.setForeground(Color.BLUE);
        backToLoginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        mainPanel.add(backToLoginButton, gbc);
        
        add(mainPanel);
        
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        
        backToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToLoginScreen();
            }
        });
    }
    
    private void registerUser() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String userType = (String) userTypeComboBox.getSelectedItem();
        
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (userDAO.usernameExists(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists!", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
     
        if (userDAO.emailExists(email)) {
            JOptionPane.showMessageDialog(this, "Email already exists!", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        User user = new User(username, email, password, userType);
        
        boolean success = userDAO.registerUser(user);
        
        if (success) {
            JOptionPane.showMessageDialog(this, "Registration successful! Please login.", "Registration Success", JOptionPane.INFORMATION_MESSAGE);
            goToLoginScreen();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed!", "Registration Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void goToLoginScreen() {
        this.dispose(); // Close 
        new LoginScreen().setVisible(true);
    }
}
