package ui;

import models.User;
import dao.UserDAO;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.File;

public class LoginScreen extends JFrame {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private UserDAO userDAO;
    
    private final Color bgColor = new Color(245, 247, 250);
    private final Color accentColor = new Color(79, 129, 242);
    private final Color textColor = new Color(60, 64, 82);
    private final Color subTextColor = new Color(114, 119, 145);
    private final Color fieldBgColor = new Color(255, 255, 255);
    private final Color buttonTextColor = Color.WHITE;
    
    public LoginScreen() {
        userDAO = new UserDAO();
        
        setTitle("Sign In");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 450, 500, 15, 15));
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgColor);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(bgColor);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(bgColor);
        
        JLabel logoLabel = createLogoLabel();
        logoPanel.add(logoLabel);
        
        JLabel appNameLabel = new JLabel("Learning Management System");
        appNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        appNameLabel.setForeground(textColor);
        appNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        headerPanel.add(logoPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(bgColor);
        formPanel.setBorder(new EmptyBorder(20, 0, 20, 0));

        JLabel titleLabel = new JLabel("Sign In");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(textColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Please enter your credentials");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(subTextColor);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel usernamePanel = createFieldPanel("Username");
        usernameField = createTextField();
        usernamePanel.add(usernameField);
        
        JPanel passwordPanel = createFieldPanel("Password");
        passwordField = createPasswordField();
        passwordPanel.add(passwordField);

        loginButton = createPrimaryButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        registerButton = new JButton("Need an account? Register");
        registerButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setForeground(accentColor);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        formPanel.add(titleLabel);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(subtitleLabel);
        formPanel.add(Box.createVerticalStrut(25));
        formPanel.add(usernamePanel);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(passwordPanel);
        formPanel.add(Box.createVerticalStrut(25));
        formPanel.add(loginButton);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(registerButton);

        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        topRightPanel.setBackground(bgColor);
        JButton closeButton = createCloseButton();
        topRightPanel.add(closeButton);
        
        mainPanel.add(topRightPanel, BorderLayout.NORTH);
        mainPanel.add(headerPanel, BorderLayout.CENTER);
        mainPanel.add(formPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegisterScreen();
            }
        });
        
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    
    private JLabel createLogoLabel() {
        JLabel appNameLabel = new JLabel("LMS");
        appNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        appNameLabel.setForeground(accentColor);
        
        return appNameLabel;
    }

    
    private JPanel createFieldPanel(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(bgColor);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setMaximumSize(new Dimension(400, 70));
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(textColor);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(8));
        
        return panel;
    }
    
    private JTextField createTextField() {
        JTextField field = new JTextField(15);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(fieldBgColor);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(400, 40));
        return field;
    }
    
    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField(15);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(fieldBgColor);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(400, 40));
        return field;
    }
    
    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(buttonTextColor);
        button.setBackground(accentColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(400, 40));
        button.setPreferredSize(new Dimension(400, 40));
        
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                AbstractButton b = (AbstractButton) c;
                ButtonModel model = b.getModel();
                
                int width = c.getWidth();
                int height = c.getHeight();
                
                if (model.isPressed()) {
                    g2d.setColor(accentColor.darker());
                } else if (model.isRollover()) {
                    g2d.setColor(accentColor.brighter());
                } else {
                    g2d.setColor(accentColor);
                }
                
                g2d.fillRoundRect(0, 0, width, height, 10, 10);
                
                FontMetrics fm = g2d.getFontMetrics();
                String text = b.getText();
                g2d.setColor(buttonTextColor);
                g2d.drawString(text, 
                    (width - fm.stringWidth(text)) / 2, 
                    (height + fm.getAscent() - fm.getDescent()) / 2);
                
                g2d.dispose();
            }
        });
        
        return button;
    }
    
    private JButton createCloseButton() {
    	 JButton closeButton = new JButton("×");
         closeButton.setFont(new Font("Arial", Font.BOLD, 18));
         closeButton.setForeground(subTextColor);
         closeButton.setBorderPainted(false);
         closeButton.setContentAreaFilled(false);
         closeButton.setFocusPainted(false);
         closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeButton.setForeground(Color.RED);
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeButton.setForeground(subTextColor);
            }
        });
        
        return closeButton;
    }
    
    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            showErrorMessage("Username and password cannot be empty!");
            return;
        }
        
        User user = userDAO.login(username, password);
        
        if (user != null) {
            this.dispose(); 
            new HomeScreen(user).setVisible(true);
        } else {
            showErrorMessage("Invalid username or password!");
        }
    }
    
    private void showErrorMessage(String message) {
        JDialog errorDialog = new JDialog(this, true);
        errorDialog.setUndecorated(true);
        errorDialog.setSize(350, 150);
        errorDialog.setLocationRelativeTo(this);
        errorDialog.setShape(new RoundRectangle2D.Double(0, 0, 350, 150, 15, 15));
        
        JPanel dialogPanel = new JPanel(new BorderLayout());
        dialogPanel.setBackground(Color.WHITE);
        dialogPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel errorTitle = new JLabel("Login Error");
        errorTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        errorTitle.setForeground(new Color(220, 53, 69));
        errorTitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel errorMessage = new JLabel(message);
        errorMessage.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
        
        JButton okButton = createDialogButton("OK", new Color(220, 53, 69));
        okButton.addActionListener(e -> errorDialog.dispose());
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.add(errorTitle);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(errorMessage);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(okButton);
        
        dialogPanel.add(contentPanel, BorderLayout.CENTER);
        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        errorDialog.add(dialogPanel);
        errorDialog.setVisible(true);
    }
    
    private JButton createDialogButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(80, 30));
        
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                AbstractButton b = (AbstractButton) c;
                ButtonModel model = b.getModel();
                
                int width = c.getWidth();
                int height = c.getHeight();
                
                if (model.isPressed()) {
                    g2d.setColor(bgColor.darker());
                } else if (model.isRollover()) {
                    g2d.setColor(bgColor.brighter());
                } else {
                    g2d.setColor(bgColor);
                }
                
                g2d.fillRoundRect(0, 0, width, height, 10, 10);
   
                FontMetrics fm = g2d.getFontMetrics();
                String btnText = b.getText();
                g2d.setColor(button.getForeground());
                g2d.drawString(btnText, 
                    (width - fm.stringWidth(btnText)) / 2, 
                    (height + fm.getAscent() - fm.getDescent()) / 2);
                
                g2d.dispose();
            }
        });
        
        return button;
    }
    
    private void showRegisterScreen() {
        this.dispose();
        
        RegisterScreen registerScreen = new RegisterScreen();
        registerScreen.setVisible(true);
    }
}