package ui;

import models.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeScreen extends JFrame {
    
    private User currentUser;
    private JLabel welcomeLabel;
    private JPanel contentPanel;
    private JPanel sidebarPanel;
    private JButton logoutButton;
    
    public HomeScreen(User user) {
        this.currentUser = user;
        
        setTitle("Student Management System - Home");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        createSidebar();
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        
        createContentPanel();
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void createSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(52, 73, 94));
        sidebarPanel.setPreferredSize(new Dimension(200, getHeight()));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(new Color(52, 73, 94));
        profilePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        profilePanel.setMaximumSize(new Dimension(180, 150));
        
        JLabel profilePicLabel = new JLabel();
        profilePicLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        if (currentUser.getProfilePicture() != null) {
            ImageIcon profileIcon = new ImageIcon(currentUser.getProfilePicture());
            profilePicLabel.setIcon(profileIcon);
        } else {
            profilePicLabel.setText("👤");
            profilePicLabel.setForeground(Color.WHITE);
            profilePicLabel.setFont(new Font("Arial", Font.PLAIN, 48));
            profilePicLabel.setHorizontalAlignment(JLabel.CENTER);
        }
        
        JLabel usernameLabel = new JLabel(currentUser.getUsername());
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel userTypeLabel = new JLabel("(" + currentUser.getUserType() + ")");
        userTypeLabel.setForeground(Color.LIGHT_GRAY);
        userTypeLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        userTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        profilePanel.add(profilePicLabel);
        profilePanel.add(Box.createVerticalStrut(10));
        profilePanel.add(usernameLabel);
        profilePanel.add(userTypeLabel);
        
        sidebarPanel.add(profilePanel);
        sidebarPanel.add(Box.createVerticalStrut(30));
        
        if ("admin".equals(currentUser.getUserType())) {
            addMenuButton("Dashboard", "dashboard");
            addMenuButton("Manage Students", "students");
            addMenuButton("Manage Courses", "courses");
            addMenuButton("Reports", "reports");
            addMenuButton("Settings", "settings");
        } else { 
            addMenuButton("My Dashboard", "dashboard");
            addMenuButton("My Courses", "courses");
            addMenuButton("My Profile", "profile");
            addMenuButton("Settings", "settings");
        }
        
        sidebarPanel.add(Box.createVerticalGlue());

        logoutButton = new JButton("Logout");
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.setFocusPainted(false);
        logoutButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoutButton.setMaximumSize(new Dimension(180, 40));
        
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        
        sidebarPanel.add(logoutButton);
    }
    
    private void addMenuButton(String text, String command) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 73, 94));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setActionCommand(command);
        button.setMaximumSize(new Dimension(180, 40));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateContent(e.getActionCommand());
            }
        });
        
        sidebarPanel.add(button);
        sidebarPanel.add(Box.createVerticalStrut(5));
    }
    
    private void createContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(236, 240, 241));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        welcomeLabel = new JLabel("Welcome, " + currentUser.getUsername() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        
        updateContent("dashboard");
    }
    
    private void updateContent(String section) {
        if (contentPanel.getComponentCount() > 1) {
            contentPanel.remove(1);
        }
        
        JPanel newContent = new JPanel();
        newContent.setLayout(new BorderLayout());
        newContent.setBackground(Color.WHITE);
        newContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel sectionTitleLabel = new JLabel(getSectionTitle(section));
        sectionTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        newContent.add(sectionTitleLabel, BorderLayout.NORTH);
        
        JPanel placeholderPanel = new JPanel(new GridBagLayout());
        placeholderPanel.setBackground(Color.WHITE);
        
        JLabel placeholderLabel = new JLabel(getPlaceholderText(section));
        placeholderLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        placeholderPanel.add(placeholderLabel);
        
        newContent.add(placeholderPanel, BorderLayout.CENTER);

        contentPanel.add(newContent, BorderLayout.CENTER);
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private String getSectionTitle(String section) {
        switch (section) {
            case "dashboard": return "Dashboard";
            case "students": return "Manage Students";
            case "courses": return "Manage Courses";
            case "reports": return "Reports";
            case "settings": return "Settings";
            case "profile": return "My Profile";
            default: return "Dashboard";
        }
    }
    
    private String getPlaceholderText(String section) {
        switch (section) {
            case "dashboard": return "This is your dashboard.";
            case "students": return "Manage your students here.";
            case "courses": return "Manage your courses here.";
            case "reports": return "View reports here.";
            case "settings": return "Adjust your settings here.";
            case "profile": return "View and edit your profile here.";
            default: return "Welcome to the Student Management System!";
        }
    }
    
    private void logout() {
        System.out.println("User logged out.");
        dispose(); 
    }
}