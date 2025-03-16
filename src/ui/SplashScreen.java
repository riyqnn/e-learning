package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashScreen extends JFrame {
    
    private JLabel imageLabel;
    private JLabel nameLabel;
    private JProgressBar progressBar;
    
    public SplashScreen() {
        setUndecorated(true);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(new Color(245, 245, 245));
        
        imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/resources/app.jpg"));
        if (imageIcon.getIconWidth() > 0) {
            imageLabel.setIcon(imageIcon);
        } else {
            imageLabel.setText("App Logo");
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setPreferredSize(new Dimension(200, 200));
            imageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        }
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        nameLabel = new JLabel("E-Learning Course");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(400, 20));
        progressBar.setMaximumSize(new Dimension(400, 20));
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(imageLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(nameLabel);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(progressBar);
        contentPanel.add(Box.createVerticalGlue());
        
        add(contentPanel, BorderLayout.CENTER);
    }
    
    public void loadProgressBar() {
        Timer timer = new Timer(30, new ActionListener() {
            int i = 0;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                progressBar.setValue(i++);
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
}
