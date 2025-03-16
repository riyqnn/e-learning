package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.File;

public class SplashScreen extends JFrame {
    
    private JLabel imageLabel;
    private JLabel titleLabel;
    private JProgressBar progressBar;
    private JLabel statusLabel;
    
    public SplashScreen() {
        setUndecorated(true);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        Color bgColor = new Color(175,198,240,255);
        Color accentColor = new Color(79, 129, 242);
        Color textColor = new Color(60, 64, 82);
        Color subTextColor = new Color(114, 119, 145);
        
        setShape(new RoundRectangle2D.Double(0, 0, 700, 500, 15, 15));
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(bgColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(24, 24, 24, 24)
        ));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(bgColor);
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        JLabel appNameLabel = new JLabel("LMS");
        appNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        appNameLabel.setForeground(accentColor);
        
        JLabel versionLabel = new JLabel("v2.0");
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        versionLabel.setForeground(subTextColor);
        
        headerPanel.add(appNameLabel, BorderLayout.WEST);
        headerPanel.add(versionLabel, BorderLayout.EAST);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(bgColor);
        
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(bgColor);
        
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/resources/app.jpg"));
            
            if (icon.getIconWidth() <= 0) {
                File file = new File("resources/app.jpg");
                if (file.exists()) {
                    icon = new ImageIcon(file.getAbsolutePath());
                }
            }
            
            if (icon.getIconWidth() > 0) {
                Image img = icon.getImage();
                Image scaledImg = img.getScaledInstance(400, 240, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImg));
            } else {
                imageLabel.setText("E-Learning");
                imageLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
                imageLabel.setForeground(accentColor);
            }
        } catch (Exception e) {
            System.out.println("Image loading error: " + e.getMessage());
            imageLabel.setText("E-Learning");
            imageLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
            imageLabel.setForeground(accentColor);
        }
        
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        
        titleLabel = new JLabel("Learning Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(textColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Your comprehensive education solution");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(subTextColor);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
       
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout(10, 10));
        bottomPanel.setBackground(bgColor);
        bottomPanel.setBorder(new EmptyBorder(20, 0, 5, 0));
        
        statusLabel = new JLabel("Preparing your learning environment...");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusLabel.setForeground(subTextColor);
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(false); 
        progressBar.setBackground(new Color(230, 230, 235));
        progressBar.setForeground(accentColor);
        progressBar.setPreferredSize(new Dimension(progressBar.getPreferredSize().width, 8)); 
        progressBar.setBorderPainted(false); 
        
        progressBar.setUI(new javax.swing.plaf.basic.BasicProgressBarUI() {
            @Override
            protected void paintDeterminate(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int w = progressBar.getWidth();
                int h = progressBar.getHeight();
                
                g2d.setColor(progressBar.getBackground());
                g2d.fillRoundRect(0, 0, w, h, h, h);
                
                // Draw progress
                g2d.setColor(progressBar.getForeground());
                int x = (int) (progressBar.getPercentComplete() * w);
                g2d.fillRoundRect(0, 0, x, h, h, h);
            }
        });
        
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        statusPanel.setBackground(bgColor);
        statusPanel.add(statusLabel);
        statusPanel.setBorder(new EmptyBorder(0, 0, 8, 0));
        
        bottomPanel.setLayout(new BorderLayout(10, 5));
        bottomPanel.add(statusPanel, BorderLayout.NORTH);
        bottomPanel.add(progressBar, BorderLayout.CENTER);
        
        // Add all components to the center panel
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(imagePanel);
        centerPanel.add(Box.createVerticalStrut(25));
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(subtitleLabel);
        centerPanel.add(Box.createVerticalGlue());
        
        // Add all panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    public void loadProgressBar() {
        // Modern loading messages
        String[] loadingMessages = {
            "Connecting to secure servers...",
            "Loading your personalized content...",
            "Preparing learning materials...",
            "Setting up your virtual classroom...",
            "Finalizing your learning environment..."
        };
        
        Timer timer = new Timer(40, new ActionListener() {
            int i = 0;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                progressBar.setValue(i);
                
                int messageIndex = (i / 20) % loadingMessages.length;
                if (messageIndex < loadingMessages.length) {
                    statusLabel.setText(loadingMessages[messageIndex]);
                }
                
                i++;
                
                if (i > 100) {
                    ((Timer) e.getSource()).stop();
                    SplashScreen.this.dispose();
                    showLoginScreen();
                }
            }
        });
        timer.start();
    }
    
    private void showLoginScreen() {
        SwingUtilities.invokeLater(() -> {
            new LoginScreen().setVisible(true);
        });
    }
    
    private static boolean isRunning = false;
    
    public static void main(String[] args) {
        if (isRunning) {
            JOptionPane.showMessageDialog(null, "Aplikasi sudah berjalan!");
            return;
        }
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            isRunning = true;
            SplashScreen splash = new SplashScreen();
            splash.setVisible(true);
            splash.loadProgressBar();
            
            splash.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    isRunning = false;
                }
            });
        });
    }
}