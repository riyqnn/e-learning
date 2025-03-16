package ui;

import models.User;
import dao.UserDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import com.toedter.calendar.JDateChooser;

public class RegisterScreen extends JFrame {
    
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JComboBox<String> userTypeComboBox;
    private JTextField contactNumberField;
    private JTextArea addressArea;
    private JDateChooser dobChooser;
    private JButton registerButton;
    private JButton backToLoginButton;
    private JLabel profileImageLabel;
    private byte[] profileImageData;
    private UserDAO userDAO;
    
    private final Color bgColor = new Color(245, 247, 250);
    private final Color accentColor = new Color(79, 129, 242);
    private final Color textColor = new Color(60, 64, 82);
    private final Color subTextColor = new Color(114, 119, 145);
    private final Color fieldBgColor = new Color(255, 255, 255);
    private final Color buttonTextColor = Color.WHITE;
    
    public RegisterScreen() {
        userDAO = new UserDAO();
        
        setTitle("Sign Up");
        setSize(750, 550);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 750, 550, 15, 15));
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgColor);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(bgColor);
        
        JButton closeButton = createCloseButton();
        JPanel closeButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        closeButtonPanel.setBackground(bgColor);
        closeButtonPanel.add(closeButton);
        topPanel.add(closeButtonPanel, BorderLayout.EAST);
        
        JPanel contentPanel = new JPanel(new BorderLayout(20, 0));
        contentPanel.setBackground(bgColor);
        
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(bgColor);
        leftPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
        
        JLabel logoLabel = createLogoLabel();
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(bgColor);
        profilePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePanel.setBorder(new EmptyBorder(30, 0, 0, 0));
        
        profileImageLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getIcon() == null) {
                    g2.setColor(new Color(220, 220, 220));
                    g2.fill(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));
                    g2.setColor(new Color(180, 180, 180));
                    g2.drawString("Add Photo", getWidth()/2 - 30, getHeight()/2 + 5);
                } else {
                    Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, getWidth(), getHeight());
                    g2.setClip(circle);
                    super.paintComponent(g);
                }
                g2.dispose();
            }
        };
        
        profileImageLabel.setPreferredSize(new Dimension(120, 120));
        profileImageLabel.setMaximumSize(new Dimension(120, 120));
        profileImageLabel.setMinimumSize(new Dimension(120, 120));
        profileImageLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        profileImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileImageLabel.setToolTipText("Click to select profile picture");
        
        profileImageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectProfilePicture();
            }
        });
        
        JLabel photoLabel = new JLabel("Profile Picture");
        photoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        photoLabel.setForeground(textColor);
        photoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        profilePanel.add(profileImageLabel);
        profilePanel.add(Box.createVerticalStrut(10));
        profilePanel.add(photoLabel);

        leftPanel.add(logoLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(profilePanel);
        
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(bgColor);

        JLabel titleLabel = new JLabel("Sing Up");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(textColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Please fill in your information");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(subTextColor);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 15, 10));
        formPanel.setBackground(bgColor);
        formPanel.setBorder(new EmptyBorder(20, 0, 10, 0));

        formPanel.add(createFieldWithLabel("Username", usernameField = createTextField()));
        formPanel.add(createFieldWithLabel("Email", emailField = createTextField()));

        formPanel.add(createFieldWithLabel("Password", passwordField = createPasswordField()));
        formPanel.add(createFieldWithLabel("Confirm Password", confirmPasswordField = createPasswordField()));

        dobChooser = new JDateChooser();
        dobChooser.setDate(new Date());
        formPanel.add(createFieldWithLabel("Date of Birth", dobChooser));
        formPanel.add(createFieldWithLabel("Contact Number", contactNumberField = createTextField()));

        userTypeComboBox = new JComboBox<>(new String[]{"Student", "Admin"});
        userTypeComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userTypeComboBox.setBackground(fieldBgColor);
        userTypeComboBox.setPreferredSize(new Dimension(0, 40));
        
        formPanel.add(createFieldWithLabel("Register as", userTypeComboBox));
        
        JPanel addressFieldPanel = createFieldWithLabel("Address", null);
        addressArea = new JTextArea();
        addressArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addressArea.setBackground(fieldBgColor);
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);
        addressArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        JScrollPane addressScrollPane = new JScrollPane(addressArea);
        addressScrollPane.setPreferredSize(new Dimension(0, 40));
        addressScrollPane.setBorder(null);
        addressFieldPanel.add(addressScrollPane);
        formPanel.add(addressFieldPanel);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(bgColor);
        
        registerButton = createPrimaryButton("Register");
        buttonPanel.add(registerButton);

        JPanel signInPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        signInPanel.setBackground(bgColor);
        
        backToLoginButton = new JButton("Already have an account? Sign In");
        backToLoginButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backToLoginButton.setBorderPainted(false);
        backToLoginButton.setContentAreaFilled(false);
        backToLoginButton.setForeground(accentColor);
        backToLoginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        backToLoginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backToLoginButton.setForeground(accentColor.darker());
                backToLoginButton.setText("<html><u>Already have an account? Sign In</u></html>");
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backToLoginButton.setForeground(accentColor);
                backToLoginButton.setText("Already have an account? Sign In");
            }
        });
        
        signInPanel.add(backToLoginButton);

        rightPanel.add(titleLabel);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(subtitleLabel);
        rightPanel.add(formPanel);
        rightPanel.add(buttonPanel);
        rightPanel.add(signInPanel);
        
        contentPanel.add(leftPanel, BorderLayout.WEST);
        contentPanel.add(rightPanel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
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
        
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    
    private JPanel createFieldWithLabel(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(bgColor);
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(textColor);
        panel.add(label, BorderLayout.NORTH);
        
        if (field != null) {
            panel.add(field, BorderLayout.CENTER);
        }
        
        return panel;
    }
    
    private JLabel createLogoLabel() {
        JLabel logoLabel = new JLabel("LMS");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        logoLabel.setForeground(accentColor);
        
        return logoLabel;
    }
    
    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(fieldBgColor);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.setPreferredSize(new Dimension(0, 40));
        return field;
    }
    
    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(fieldBgColor);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.setPreferredSize(new Dimension(0, 40));
        return field;
    }
    
    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(accentColor);
        button.setForeground(buttonTextColor);
        button.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(accentColor.darker());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(accentColor);
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
    
    private void selectProfilePicture() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Profile Picture");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Image Files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedImage originalImage = ImageIO.read(selectedFile);
                
                int size = 120;
                BufferedImage scaledImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = scaledImage.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.drawImage(originalImage, 0, 0, size, size, null);
                g2d.dispose();
  
                profileImageLabel.setIcon(new ImageIcon(scaledImage));
                
                try (FileInputStream fis = new FileInputStream(selectedFile)) {
                    profileImageData = fis.readAllBytes();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error loading image: " + ex.getMessage(), 
                    "Image Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void registerUser() {
        if (!validateFields()) {
            return;
        }
        User user = new User();
        user.setUsername(usernameField.getText());
        user.setEmail(emailField.getText());
        user.setPassword(new String(passwordField.getPassword()));
        user.setUserType(userTypeComboBox.getSelectedItem().toString());
        user.setContactNumber(contactNumberField.getText());
        user.setAddress(addressArea.getText());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dob = sdf.format(dobChooser.getDate());
        user.setDateOfBirth(dob);

        user.setProfilePicture(profileImageData);

        try {
            if (userDAO.checkUsernameExists(user.getUsername())) {
                JOptionPane.showMessageDialog(this, 
                    "Username already exists. Please choose a different username.", 
                    "Registration Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (userDAO.checkEmailExists(user.getEmail())) {
                JOptionPane.showMessageDialog(this, 
                    "Email already exists. Please use a different email address.", 
                    "Registration Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            boolean success = userDAO.registerUser(user);
            if (success) {
                JOptionPane.showMessageDialog(this,
                    "Registration successful!",
                    "Registration Success",
                    JOptionPane.INFORMATION_MESSAGE);
                HomeScreen homeScreen = new HomeScreen(user);  
                homeScreen.setVisible(true);
                this.dispose();  
            } else {
                JOptionPane.showMessageDialog(this,
                    "Registration failed. Please try again.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error during registration: " + ex.getMessage(), 
                "Registration Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validateFields() {
        if (usernameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a username.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter an email address.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid email address.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        String password = new String(passwordField.getPassword());
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a password.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, 
                "Password must be at least 6 characters long.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        String confirmPassword = new String(confirmPasswordField.getPassword());
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, 
                "Passwords do not match.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (contactNumberField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a contact number.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (addressArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter an address.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void goToLoginScreen() {
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.setVisible(true);
        this.dispose();
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RegisterScreen().setVisible(true);
            }
        });
    }
}