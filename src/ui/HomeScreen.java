package ui;

import models.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EmptyBorder;
import java.awt.geom.RoundRectangle2D;

public class HomeScreen extends JFrame {
    
    private User currentUser;
    private JLabel welcomeLabel;
    private JPanel contentPanel;
    private JPanel sidebarPanel;
    private JButton logoutButton;
    private Color primaryColor = new Color(41, 128, 185);
    private Color sidebarBgColor = new Color(44, 62, 80);
    private Color hoverColor = new Color(52, 73, 94);
    
    public HomeScreen(User user) {
        this.currentUser = user;
        
        setTitle("E-Learning Course - Home");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        
        createSidebar();
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        
        createContentPanel();
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void createSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(sidebarBgColor);
        sidebarPanel.setPreferredSize(new Dimension(220, getHeight()));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(25, 15, 25, 15));
        
        // App title
        JLabel appTitle = new JLabel("E-Learning");
        appTitle.setForeground(Color.WHITE);
        appTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        appTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        appTitle.setBorder(new EmptyBorder(0, 10, 20, 0));
        sidebarPanel.add(appTitle);
        
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(190, 1));
        separator.setForeground(new Color(70, 90, 110));
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebarPanel.add(separator);
        sidebarPanel.add(Box.createVerticalStrut(25));
        
        // Profile panel
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(sidebarBgColor);
        profilePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        profilePanel.setMaximumSize(new Dimension(190, 150));
        
        // Profile picture (circular)
        JPanel profilePicPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (currentUser.getProfilePicture() != null) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    int size = Math.min(80, 80);
                    ImageIcon originalIcon = new ImageIcon(currentUser.getProfilePicture());
                    Image img = originalIcon.getImage();
                    
                    // Create a circular shape
                    Shape circle = new RoundRectangle2D.Float(10, 0, size, size, size, size);
                    g2.setClip(circle);
                    
                    // Draw the image scaled to fit the circle, maintaining aspect ratio
                    float aspectRatio = (float) img.getWidth(null) / img.getHeight(null);
                    int drawWidth, drawHeight;
                    int x = 10, y = 0;
                    
                    if (aspectRatio > 1) { // wider than tall
                        drawHeight = size;
                        drawWidth = Math.round(size * aspectRatio);
                        x = 10 - (drawWidth - size) / 2;
                    } else { // taller than wide
                        drawWidth = size;
                        drawHeight = Math.round(size / aspectRatio);
                        y = (size - drawHeight) / 2;
                    }
                    
                    g2.drawImage(img, x, y, drawWidth, drawHeight, null);
                    
                    // Draw a border
                    g2.setClip(null);
                    g2.setColor(new Color(70, 90, 110));
                    g2.setStroke(new BasicStroke(2));
                    g2.draw(circle);
                    
                    g2.dispose();
                } else {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    int size = 80;
                    Shape circle = new RoundRectangle2D.Float(10, 0, size, size, size, size);
                    g2.setColor(new Color(70, 90, 110));
                    g2.fill(circle);
                    
                    // Draw the default user icon
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Segoe UI", Font.PLAIN, 40));
                    FontMetrics fm = g2.getFontMetrics();
                    String userIcon = "👤";
                    int textWidth = fm.stringWidth(userIcon);
                    int textHeight = fm.getHeight();
                    g2.drawString(userIcon, 10 + (size - textWidth) / 2, (size - textHeight) / 2 + fm.getAscent());
                    
                    g2.dispose();
                }
            }
            
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(100, 80);
            }
        };
        profilePicPanel.setBackground(sidebarBgColor);
        profilePicPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel usernameLabel = new JLabel(currentUser.getUsername());
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        usernameLabel.setBorder(new EmptyBorder(10, 10, 0, 0));
        
        JLabel userTypeLabel = new JLabel(currentUser.getUserType());
        userTypeLabel.setForeground(new Color(189, 195, 199));
        userTypeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userTypeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        userTypeLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
        
        profilePanel.add(profilePicPanel);
        profilePanel.add(usernameLabel);
        profilePanel.add(userTypeLabel);
        
        sidebarPanel.add(profilePanel);
        sidebarPanel.add(Box.createVerticalStrut(30));
        
        // Menu separator
        JLabel menuLabel = new JLabel("MENU");
        menuLabel.setForeground(new Color(189, 195, 199));
        menuLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        menuLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuLabel.setBorder(new EmptyBorder(0, 10, 10, 0));
        sidebarPanel.add(menuLabel);
        
        if ("admin".equals(currentUser.getUserType())) {
            addMenuButton("Dashboard", "dashboard", UIManager.getIcon("FileView.computerIcon"));
            addMenuButton("Manage Students", "students", UIManager.getIcon("FileView.directoryIcon"));
            addMenuButton("Manage Courses", "courses", UIManager.getIcon("FileChooser.listViewIcon"));
            addMenuButton("Reports", "reports", UIManager.getIcon("FileView.computerIcon"));
            addMenuButton("Settings", "settings", UIManager.getIcon("FileView.hardDriveIcon"));
        } else {
            addMenuButton("My Dashboard", "dashboard", UIManager.getIcon("FileView.computerIcon"));
            addMenuButton("My Courses", "courses", UIManager.getIcon("FileChooser.listViewIcon"));
            addMenuButton("My Profile", "profile", UIManager.getIcon("FileView.fileIcon"));
            addMenuButton("Settings", "settings", UIManager.getIcon("FileView.hardDriveIcon"));
        }

        
        sidebarPanel.add(Box.createVerticalGlue());
        
        // Logout button
        logoutButton = new JButton("Logout");
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoutButton.setMaximumSize(new Dimension(190, 40));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        
        sidebarPanel.add(logoutButton);
    }

    private void addMenuButton(String text, String command, Icon icon) {
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(sidebarBgColor);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setMaximumSize(new Dimension(190, 40));
        buttonPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(icon);
        iconLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
        
        JLabel textLabel = new JLabel(text);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textLabel.setBorder(new EmptyBorder(0, 8, 0, 0));
        
        buttonPanel.add(iconLabel, BorderLayout.WEST);
        buttonPanel.add(textLabel, BorderLayout.CENTER);
        
        buttonPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonPanel.setBackground(hoverColor);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                buttonPanel.setBackground(sidebarBgColor);
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                updateContent(command);
            }
        });
        
        sidebarPanel.add(buttonPanel);
        sidebarPanel.add(Box.createVerticalStrut(5));
    }
    private void createContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(245, 245, 245));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        
        welcomeLabel = new JLabel("Welcome, " + currentUser.getUsername() + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        welcomeLabel.setForeground(new Color(52, 73, 94));
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        
        JTextField searchField = new JTextField("Search...");
        searchField.setPreferredSize(new Dimension(250, 30));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        searchField.setForeground(Color.GRAY);
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(searchField);
        headerPanel.add(searchPanel, BorderLayout.EAST);
        
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        
        updateContent("dashboard");
    }
    
    private void updateContent(String section) {
        if (contentPanel.getComponentCount() > 1) {
            contentPanel.remove(1);
        }
        
        JPanel newContent = new JPanel();
        newContent.setLayout(new BorderLayout(0, 20));
        newContent.setBackground(new Color(245, 245, 245));
        newContent.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(245, 245, 245));
        
        JLabel sectionTitleLabel = new JLabel(getSectionTitle(section));
        sectionTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        sectionTitleLabel.setForeground(new Color(52, 73, 94));
        titlePanel.add(sectionTitleLabel, BorderLayout.WEST);
        
        newContent.add(titlePanel, BorderLayout.NORTH);
        
        // Content depends on the section
        JPanel sectionContent = getSectionContent(section);
        newContent.add(sectionContent, BorderLayout.CENTER);
        
        contentPanel.add(newContent, BorderLayout.CENTER);
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
	    private JPanel getSectionContent(String section) {
	        JPanel contentCards = new JPanel(new GridLayout(0, 2, 20, 20));
	        contentCards.setBackground(new Color(245, 245, 245));
	        
	        switch (section) {
	        case "dashboard":
	            if ("admin".equals(currentUser.getUserType())) {
	                contentCards.add(createCard("Total Students", "1,245", UIManager.getIcon("FileView.directoryIcon"))); 
	                contentCards.add(createCard("Total Courses", "42", UIManager.getIcon("FileView.fileIcon"))); 
	                contentCards.add(createCard("Active Users", "854", UIManager.getIcon("FileView.computerIcon"))); 
	                contentCards.add(createCard("Monthly Revenue", "$12,450", UIManager.getIcon("FileView.hardDriveIcon"))); 
	            } else {
	                contentCards.add(createCard("Enrolled Courses", "5", UIManager.getIcon("FileView.directoryIcon"))); 
	                contentCards.add(createCard("Completed Courses", "2", UIManager.getIcon("FileView.computerIcon"))); 
	                contentCards.add(createCard("In Progress", "3", UIManager.getIcon("FileView.floppyDriveIcon"))); 
	                contentCards.add(createCard("Achievements", "7", UIManager.getIcon("FileChooser.detailsViewIcon"))); 
	            }
	            break;
	        
	        case "courses":
	            if ("admin".equals(currentUser.getUserType())) {
	                contentCards.add(createCourseCard("Java Programming Fundamentals", "Programming", "89 students", "Active"));
	                contentCards.add(createCourseCard("Web Development with HTML/CSS", "Web Development", "132 students", "Active"));
	                contentCards.add(createCourseCard("Python for Data Science", "Data Science", "165 students", "Active"));
	                contentCards.add(createCourseCard("Mobile App Development", "Programming", "78 students", "Active"));
	            } else {
	                contentCards.add(createCourseCard("Java Programming Fundamentals", "Programming", "Progress: 75%", "In Progress"));
	                contentCards.add(createCourseCard("Web Development with HTML/CSS", "Web Development", "Progress: 100%", "Completed"));
	                contentCards.add(createCourseCard("Python for Data Science", "Data Science", "Progress: 42%", "In Progress"));
	                contentCards.add(createCourseCard("UI/UX Design Principles", "Design", "Progress: 100%", "Completed"));
	            }
	            break;
	        
	        case "students":
	            contentCards.setLayout(new BorderLayout());
	            JPanel studentListPanel = createStudentListPanel();
	            contentCards.add(studentListPanel, BorderLayout.CENTER);
	            break;
	        
	        case "profile":
	            contentCards.setLayout(new BorderLayout());
	            JPanel profileContentPanel = createProfilePanel();
	            contentCards.add(profileContentPanel, BorderLayout.CENTER);
	            break;
	        
	        case "settings":
	            contentCards.setLayout(new BorderLayout());
	            JPanel settingsPanel = createSettingsPanel();
	            contentCards.add(settingsPanel, BorderLayout.CENTER);
	            break;
	        
	        case "reports":
	            contentCards.setLayout(new BorderLayout());
	            JPanel reportsPanel = createReportsPanel();
	            contentCards.add(reportsPanel, BorderLayout.CENTER);
	            break;
	        
	        default:
	            JLabel noContentLabel = new JLabel("No content available for this section.");
	            noContentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
	            contentCards.add(noContentLabel);
	    }
	
	    return contentCards;

                }

                private String getSectionTitle(String section) {
                    switch (section) {
                        case "dashboard": return "Dashboard";
                        case "courses": return "admin".equals(currentUser.getUserType()) ? "Manage Courses" : "My Courses";
                        case "students": return "Manage Students";
                        case "profile": return "My Profile";
                        case "settings": return "Settings";
                        case "reports": return "Reports";
                        default: return "Unknown Section";
                    }
                }

                private JPanel createCard(String title, String value, Icon icon) {
                    JPanel card = new JPanel(new BorderLayout()) {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            Graphics2D g2d = (Graphics2D) g;
                            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            g2d.setColor(getBackground());
                            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                        }
                    };
                    card.setBackground(Color.WHITE);
                    card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                    
                    JLabel iconLabel = new JLabel(icon);
                    iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
                    
                    JLabel titleLabel = new JLabel(title);
                    titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    titleLabel.setForeground(new Color(120, 120, 120));
                    
                    JLabel valueLabel = new JLabel(value);
                    valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
                    valueLabel.setForeground(new Color(52, 73, 94));
                    
                    JPanel textPanel = new JPanel(new GridLayout(2, 1));
                    textPanel.setBackground(Color.WHITE);
                    textPanel.add(titleLabel);
                    textPanel.add(valueLabel);
                    
                    card.add(iconLabel, BorderLayout.WEST);
                    card.add(textPanel, BorderLayout.CENTER);
                    
                    return card;
                }

                private JPanel createCourseCard(String courseName, String category, String stats, String status) {
                    JPanel card = new JPanel(new BorderLayout(15, 0)) {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            Graphics2D g2d = (Graphics2D) g;
                            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            g2d.setColor(getBackground());
                            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                        }
                    };
                    card.setBackground(Color.WHITE);
                    card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                    
                    JPanel leftPanel = new JPanel(new GridLayout(3, 1));
                    leftPanel.setBackground(Color.WHITE);
                    
                    JLabel courseNameLabel = new JLabel(courseName);
                    courseNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
                    courseNameLabel.setForeground(new Color(52, 73, 94));
                    
                    JLabel categoryLabel = new JLabel(category);
                    categoryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    categoryLabel.setForeground(new Color(120, 120, 120));
                    
                    JLabel statsLabel = new JLabel(stats);
                    statsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    statsLabel.setForeground(new Color(120, 120, 120));
                    
                    leftPanel.add(courseNameLabel);
                    leftPanel.add(categoryLabel);
                    leftPanel.add(statsLabel);
                    
                    JPanel rightPanel = new JPanel(new BorderLayout());
                    rightPanel.setBackground(Color.WHITE);
                    
                    JLabel statusLabel = new JLabel(status);
                    statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
                    
                    if ("Active".equals(status) || "Completed".equals(status)) {
                        statusLabel.setForeground(new Color(46, 204, 113));
                    } else if ("In Progress".equals(status)) {
                        statusLabel.setForeground(new Color(241, 196, 15));
                    } else {
                        statusLabel.setForeground(new Color(231, 76, 60));
                    }
                    
                    rightPanel.add(statusLabel, BorderLayout.NORTH);
                    
                    JButton actionButton = new JButton("admin".equals(currentUser.getUserType()) ? "Edit" : "View");
                    actionButton.setBackground(primaryColor);
                    actionButton.setForeground(Color.WHITE);
                    actionButton.setFocusPainted(false);
                    actionButton.setBorderPainted(false);
                    actionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    
                    rightPanel.add(actionButton, BorderLayout.SOUTH);
                    
                    card.add(leftPanel, BorderLayout.CENTER);
                    card.add(rightPanel, BorderLayout.EAST);
                    
                    return card;
                }

                private JPanel createStudentListPanel() {
                    JPanel panel = new JPanel(new BorderLayout(0, 20));
                    panel.setBackground(Color.WHITE);
                    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                    
                    // Search and filter tools
                    JPanel toolsPanel = new JPanel(new BorderLayout());
                    toolsPanel.setBackground(Color.WHITE);
                    
                    JTextField searchField = new JTextField();
                    searchField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220)),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                    ));
                    searchField.setPreferredSize(new Dimension(200, 30));
                    
                    JButton addStudentBtn = new JButton("Add Student");
                    addStudentBtn.setBackground(primaryColor);
                    addStudentBtn.setForeground(Color.WHITE);
                    addStudentBtn.setFocusPainted(false);
                    addStudentBtn.setBorderPainted(false);
                    addStudentBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    
                    JPanel rightToolsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    rightToolsPanel.setBackground(Color.WHITE);
                    rightToolsPanel.add(addStudentBtn);
                    
                    toolsPanel.add(searchField, BorderLayout.WEST);
                    toolsPanel.add(rightToolsPanel, BorderLayout.EAST);
                    
                    // Student table
                    String[] columns = {"ID", "Name", "Email", "Status", "Enrolled Courses", "Actions"};
                    Object[][] data = {
                        {"001", "John Smith", "john@example.com", "Active", "3", "Edit"},
                        {"002", "Sarah Johnson", "sarah@example.com", "Active", "2", "Edit"},
                        {"003", "Michael Brown", "michael@example.com", "Inactive", "1", "Edit"},
                        {"004", "Lisa Davis", "lisa@example.com", "Active", "4", "Edit"},
                        {"005", "David Wilson", "david@example.com", "Active", "2", "Edit"}
                    };
                    
                    JTable table = new JTable(data, columns);
                    table.setRowHeight(40);
                    table.setShowVerticalLines(false);
                    table.setGridColor(new Color(240, 240, 240));
                    table.setSelectionBackground(new Color(232, 246, 252));
                    table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
                    table.getTableHeader().setBackground(new Color(245, 245, 245));
                    table.getTableHeader().setForeground(new Color(52, 73, 94));
                    
                    JScrollPane scrollPane = new JScrollPane(table);
                    scrollPane.setBorder(BorderFactory.createEmptyBorder());
                    
                    panel.add(toolsPanel, BorderLayout.NORTH);
                    panel.add(scrollPane, BorderLayout.CENTER);
                    
                    return panel;
                }

                private JPanel createProfilePanel() {
                    JPanel panel = new JPanel(new BorderLayout(20, 20));
                    panel.setBackground(Color.WHITE);
                    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                    
                    // Left panel for profile picture and basic info
                    JPanel leftPanel = new JPanel(new BorderLayout(0, 15));
                    leftPanel.setBackground(Color.WHITE);
                    leftPanel.setPreferredSize(new Dimension(250, 400));
                    
                    // Profile picture panel
                    JPanel profilePicPanel = new JPanel() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            Graphics2D g2 = (Graphics2D) g.create();
                            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            
                            int size = Math.min(getWidth(), getHeight()) - 20;
                            int x = (getWidth() - size) / 2;
                            int y = 10;
                            
                            // Draw circle background
                            g2.setColor(new Color(240, 240, 240));
                            g2.fillOval(x, y, size, size);
                            
                            // Draw user icon
                            g2.setColor(new Color(150, 150, 150));
                            g2.setFont(new Font("Segoe UI", Font.PLAIN, size / 2));
                            FontMetrics fm = g2.getFontMetrics();
                            String userIcon = "👤";
                            int textWidth = fm.stringWidth(userIcon);
                            int textHeight = fm.getHeight();
                            g2.drawString(userIcon, x + (size - textWidth) / 2, y + (size - textHeight) / 2 + fm.getAscent());
                            
                            g2.dispose();
                        }
                        
                        @Override
                        public Dimension getPreferredSize() {
                            return new Dimension(200, 200);
                        }
                    };
                    
                    JButton changePhotoBtn = new JButton("Change Photo");
                    changePhotoBtn.setBackground(primaryColor);
                    changePhotoBtn.setForeground(Color.WHITE);
                    changePhotoBtn.setFocusPainted(false);
                    changePhotoBtn.setBorderPainted(false);
                    
                    JPanel infoPanel = new JPanel(new GridLayout(3, 1, 0, 5));
                    infoPanel.setBackground(Color.WHITE);
                    
                    JLabel nameLabel = new JLabel(currentUser.getUsername());
                    nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
                    nameLabel.setHorizontalAlignment(JLabel.CENTER);
                    
                    JLabel emailLabel = new JLabel("user@example.com");
                    emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    emailLabel.setForeground(new Color(120, 120, 120));
                    emailLabel.setHorizontalAlignment(JLabel.CENTER);
                    
                    JLabel memberSinceLabel = new JLabel("Member since: Jan 2023");
                    memberSinceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    memberSinceLabel.setForeground(new Color(150, 150, 150));
                    memberSinceLabel.setHorizontalAlignment(JLabel.CENTER);
                    
                    infoPanel.add(nameLabel);
                    infoPanel.add(emailLabel);
                    infoPanel.add(memberSinceLabel);
                    
                    JPanel photoButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    photoButtonPanel.setBackground(Color.WHITE);
                    photoButtonPanel.add(changePhotoBtn);
                    
                    leftPanel.add(profilePicPanel, BorderLayout.NORTH);
                    leftPanel.add(infoPanel, BorderLayout.CENTER);
                    leftPanel.add(photoButtonPanel, BorderLayout.SOUTH);
                    
                    // Right panel for profile details
                    JPanel rightPanel = new JPanel();
                    rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
                    rightPanel.setBackground(Color.WHITE);
                    
                    JLabel detailsTitle = new JLabel("Profile Details");
                    detailsTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
                    detailsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
                    detailsTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
                    
                    String[][] fields = {
                    		{"Full Name:", ""},
                    		{"Email:", "user@example.com"},
                    		{"Phone:", "+1 (555) 123-4567"},
                    		{"Address:", "123 Main Street, City"},
                    		{"User Type:", currentUser.getUserType()},
                    		{"Bio:", ""}
                    		};

                    		JPanel formPanel = new JPanel(new GridLayout(fields.length, 1, 0, 15));
                    		formPanel.setBackground(Color.WHITE);
                    		formPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                    		for (String[] field : fields) {
                    		    JPanel fieldPanel = new JPanel(new BorderLayout(10, 0));
                    		    fieldPanel.setBackground(Color.WHITE);
                    		    
                    		    JLabel fieldLabel = new JLabel(field[0]);
                    		    fieldLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    		    fieldLabel.setPreferredSize(new Dimension(100, 25));
                    		    
                    		    JTextField fieldValue;
                    		    if (field[0].equals("Bio:")) {
                    		        fieldValue = new JTextField("Write something about yourself...");
                    		        fieldValue.setPreferredSize(new Dimension(0, 60));
                    		    } else {
                    		        fieldValue = new JTextField(field[1]);
                    		    }
                    		    
                    		    fieldValue.setBorder(BorderFactory.createCompoundBorder(
                    		        BorderFactory.createLineBorder(new Color(220, 220, 220)),
                    		        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                    		    ));
                    		    
                    		    fieldPanel.add(fieldLabel, BorderLayout.WEST);
                    		    fieldPanel.add(fieldValue, BorderLayout.CENTER);
                    		    
                    		    formPanel.add(fieldPanel);
                    		}

                    		JButton saveButton = new JButton("Save Changes");
                    		saveButton.setBackground(primaryColor);
                    		saveButton.setForeground(Color.WHITE);
                    		saveButton.setFocusPainted(false);
                    		saveButton.setBorderPainted(false);
                    		saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);
                    		saveButton.setMaximumSize(new Dimension(150, 40));
                    		saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                    		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    		buttonPanel.setBackground(Color.WHITE);
                    		buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    		buttonPanel.add(saveButton);

                    		rightPanel.add(detailsTitle);
                    		rightPanel.add(formPanel);
                    		rightPanel.add(Box.createVerticalStrut(20));
                    		rightPanel.add(buttonPanel);

                    		panel.add(leftPanel, BorderLayout.WEST);
                    		panel.add(rightPanel, BorderLayout.CENTER);

                    		return panel;
                    		}

                    		private JPanel createSettingsPanel() {
                    		    JPanel panel = new JPanel();
                    		    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    		    panel.setBackground(Color.WHITE);
                    		    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                    		    
                    		    JLabel settingsTitle = new JLabel("Account Settings");
                    		    settingsTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
                    		    settingsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
                    		    settingsTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
                    		    
                    		    // Account settings section
                    		    JPanel accountSection = createSettingsSection("Account",
                    		        new String[][]{
                    		            {"Email Notifications", "true"},
                    		            {"SMS Notifications", "false"},
                    		            {"Two-Factor Authentication", "false"}
                    		        }
                    		    );
                    		    
                    		    // Privacy settings section
                    		    JPanel privacySection = createSettingsSection("Privacy",
                    		        new String[][]{
                    		            {"Show Profile to Others", "true"},
                    		            {"Share Progress", "true"},
                    		            {"Allow Messages", "true"}
                    		        }
                    		    );
                    		    
                    		    // Theme settings section
                    		    JPanel themeSection = createSettingsSection("Appearance",
                    		        new String[][]{
                    		            {"Dark Mode", "false"},
                    		            {"High Contrast", "false"},
                    		            {"Large Text", "false"}
                    		        }
                    		    );
                    		    
                    		    JButton saveButton = new JButton("Save Settings");
                    		    saveButton.setBackground(primaryColor);
                    		    saveButton.setForeground(Color.WHITE);
                    		    saveButton.setFocusPainted(false);
                    		    saveButton.setBorderPainted(false);
                    		    saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);
                    		    saveButton.setMaximumSize(new Dimension(150, 40));
                    		    saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    		    
                    		    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    		    buttonPanel.setBackground(Color.WHITE);
                    		    buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    		    buttonPanel.add(saveButton);
                    		    
                    		    panel.add(settingsTitle);
                    		    panel.add(accountSection);
                    		    panel.add(Box.createVerticalStrut(20));
                    		    panel.add(privacySection);
                    		    panel.add(Box.createVerticalStrut(20));
                    		    panel.add(themeSection);
                    		    panel.add(Box.createVerticalStrut(20));
                    		    panel.add(buttonPanel);
                    		    
                    		    return panel;
                    		}

                    		private JPanel createSettingsSection(String title, String[][] options) {
                    		    JPanel section = new JPanel();
                    		    section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
                    		    section.setBackground(Color.WHITE);
                    		    section.setBorder(BorderFactory.createCompoundBorder(
                    		        BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(230, 230, 230)),
                    		        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                    		    ));
                    		    section.setAlignmentX(Component.LEFT_ALIGNMENT);
                    		    
                    		    JLabel sectionTitle = new JLabel(title);
                    		    sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
                    		    sectionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
                    		    sectionTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
                    		    
                    		    section.add(sectionTitle);
                    		    
                    		    for (String[] option : options) {
                    		        JPanel optionPanel = new JPanel(new BorderLayout());
                    		        optionPanel.setBackground(Color.WHITE);
                    		        optionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    		        optionPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
                    		        
                    		        JLabel optionLabel = new JLabel(option[0]);
                    		        optionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    		        
                    		        JCheckBox checkBox = new JCheckBox();
                    		        checkBox.setSelected(Boolean.parseBoolean(option[1]));
                    		        checkBox.setBackground(Color.WHITE);
                    		        
                    		        optionPanel.add(optionLabel, BorderLayout.WEST);
                    		        optionPanel.add(checkBox, BorderLayout.EAST);
                    		        
                    		        section.add(optionPanel);
                    		        section.add(Box.createVerticalStrut(10));
                    		    }
                    		    
                    		    return section;
                    		}

                    		private JPanel createReportsPanel() {
                    		    JPanel panel = new JPanel(new BorderLayout(0, 20));
                    		    panel.setBackground(Color.WHITE);
                    		    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                    		    
                    		    JPanel topPanel = new JPanel(new BorderLayout());
                    		    topPanel.setBackground(Color.WHITE);
                    		    
                    		    String[] reportTypes = {"User Activity", "Course Enrollments", "Revenue", "Student Performance"};
                    		    JComboBox<String> reportSelector = new JComboBox<>(reportTypes);
                    		    reportSelector.setPreferredSize(new Dimension(200, 30));
                    		    
                    		    JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    		    filterPanel.setBackground(Color.WHITE);
                    		    
                    		    JLabel dateRangeLabel = new JLabel("Date Range: ");
                    		    JComboBox<String> dateRangeSelector = new JComboBox<>(new String[]{"Last 7 days", "Last 30 days", "Last 90 days", "Custom"});
                    		    
                    		    filterPanel.add(dateRangeLabel);
                    		    filterPanel.add(dateRangeSelector);
                    		    
                    		    topPanel.add(reportSelector, BorderLayout.WEST);
                    		    topPanel.add(filterPanel, BorderLayout.EAST);
                    		    
                    		    // Placeholder for chart
                    		    JPanel chartPanel = new JPanel() {
                    		        @Override
                    		        protected void paintComponent(Graphics g) {
                    		            super.paintComponent(g);
                    		            Graphics2D g2 = (Graphics2D) g;
                    		            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    		            
                    		            // Draw a simple bar chart as placeholder
                    		            g2.setColor(new Color(240, 240, 240));
                    		            g2.fillRect(0, 0, getWidth(), getHeight());
                    		            
                    		            int barWidth = getWidth() / 8;
                    		            int maxBarHeight = getHeight() - 40;
                    		            
                    		            // X and Y axis
                    		            g2.setColor(Color.BLACK);
                    		            g2.drawLine(40, getHeight() - 30, getWidth() - 20, getHeight() - 30); // X axis
                    		            g2.drawLine(40, 20, 40, getHeight() - 30); // Y axis
                    		            
                    		            // Draw bars
                    		            int[] values = {65, 85, 45, 75, 55, 90, 30};
                    		            String[] labels = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
                    		            
                    		            for (int i = 0; i < values.length; i++) {
                    		                int barHeight = (int)((values[i] / 100.0) * maxBarHeight);
                    		                int x = 60 + i * (barWidth + 10);
                    		                int y = getHeight() - 30 - barHeight;
                    		                
                    		                g2.setColor(new Color(41, 128, 185, 180));
                    		                g2.fillRect(x, y, barWidth, barHeight);
                    		                
                    		                g2.setColor(new Color(41, 128, 185));
                    		                g2.drawRect(x, y, barWidth, barHeight);
                    		                
                    		                // Draw label
                    		                g2.setColor(Color.BLACK);
                    		                g2.drawString(labels[i], x + (barWidth / 2) - 10, getHeight() - 10);
                    		                
                    		                // Draw value
                    		                g2.drawString(String.valueOf(values[i]), x + (barWidth / 2) - 10, y - 5);
                    		            }
                    		        }
                    		    };
                    		    chartPanel.setPreferredSize(new Dimension(0, 300));
                    		    
                    		    // Metrics cards
                    		    JPanel metricsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
                    		    metricsPanel.setBackground(Color.WHITE);
                    		    
                    		    metricsPanel.add(createMetricCard("Total Views", "12,456", "+12%"));
                    		    metricsPanel.add(createMetricCard("New Enrollments", "543", "+8%"));
                    		    metricsPanel.add(createMetricCard("Completion Rate", "68%", "+5%"));
                    		    metricsPanel.add(createMetricCard("Avg. Rating", "4.7/5", "+0.2"));
                    		    
                    		    panel.add(topPanel, BorderLayout.NORTH);
                    		    panel.add(chartPanel, BorderLayout.CENTER);
                    		    panel.add(metricsPanel, BorderLayout.SOUTH);
                    		    
                    		    return panel;
                    		}

                    		private JPanel createMetricCard(String title, String value, String change) {
                    		    JPanel card = new JPanel(new BorderLayout(0, 5)) {
                    		        @Override
                    		        protected void paintComponent(Graphics g) {
                    		            super.paintComponent(g);
                    		            Graphics2D g2d = (Graphics2D) g;
                    		            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    		            g2d.setColor(getBackground());
                    		            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                    		        }
                    		    };
                    		    card.setBackground(Color.WHITE);
                    		    card.setBorder(BorderFactory.createCompoundBorder(
                    		        BorderFactory.createLineBorder(new Color(230, 230, 230)),
                    		        BorderFactory.createEmptyBorder(10, 15, 10, 15)
                    		    ));
                    		    
                    		    JLabel titleLabel = new JLabel(title);
                    		    titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    		    titleLabel.setForeground(new Color(120, 120, 120));
                    		    
                    		    JLabel valueLabel = new JLabel(value);
                    		    valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
                    		    
                    		    JLabel changeLabel = new JLabel(change);
                    		    changeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    		    
                    		    if (change.startsWith("+")) {
                    		        changeLabel.setForeground(new Color(46, 204, 113));
                    		    } else if (change.startsWith("-")) {
                    		        changeLabel.setForeground(new Color(231, 76, 60));
                    		    }
                    		    
                    		    card.add(titleLabel, BorderLayout.NORTH);
                    		    card.add(valueLabel, BorderLayout.CENTER);
                    		    card.add(changeLabel, BorderLayout.SOUTH);
                    		    
                    		    return card;
                    		}

                    		private void logout() {
                    		    System.out.println("User logged out.");
                    		    dispose();
                    		}
                    		}