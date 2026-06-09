import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Digital Wallet System");
        setSize(550, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Main Background Panel
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(248, 250, 252)); // Ultra-clean modern light grey

        // Premium Top Header Banner
        JPanel header = new JPanel();
        header.setLayout(null);
        header.setBackground(new Color(79, 70, 229)); // Modern Indigo Accent
        header.setBounds(0, 0, 550, 110);

        JLabel title = new JLabel("DIGITAL WALLET");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setBounds(175, 25, 300, 35);

        JLabel subtitle = new JLabel("Secure Digital Payment System");
        subtitle.setForeground(new Color(224, 231, 255));
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setBounds(178, 60, 260, 20);

        header.add(title);
        header.add(subtitle);

        // Form Container / Card View
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(null);
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBounds(65, 150, 420, 350);
        cardPanel.setBorder(new LineBorder(new Color(226, 232, 240), 1, true));

        // Username Input field
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(new Color(71, 85, 105));
        userLabel.setBounds(40, 30, 100, 25);

        JTextField userField = new JTextField();
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userField.setBounds(40, 60, 340, 40);
        userField.setBorder(new LineBorder(new Color(203, 213, 225), 1));

        // Password Input field
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passLabel.setForeground(new Color(71, 85, 105));
        passLabel.setBounds(40, 115, 100, 25);

        JPasswordField passField = new JPasswordField();
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passField.setBounds(40, 145, 340, 40);
        passField.setBorder(new LineBorder(new Color(203, 213, 225), 1));

        // Modern Login Button
        JButton loginBtn = new JButton("Login to Account");
        loginBtn.setBounds(40, 215, 340, 45);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setBackground(new Color(79, 70, 229)); // Indigo
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);

        // Modern Register Button
        JButton registerBtn = new JButton("Create New Wallet");
        registerBtn.setBounds(40, 275, 340, 45);
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerBtn.setBackground(new Color(16, 185, 129)); // Emerald Green
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);
        registerBtn.setBorderPainted(false);

        // Adding components to Card Panel
        cardPanel.add(userLabel);
        cardPanel.add(userField);
        cardPanel.add(passLabel);
        cardPanel.add(passField);
        cardPanel.add(loginBtn);
        cardPanel.add(registerBtn);

        // Adding main segments
        panel.add(header);
        panel.add(cardPanel);
        add(panel);

        // --- ACTION LISTENERS ---

        // LOGIN ACTION
        loginBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword());

            if(username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both Username & Password", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Wallet wallet = Main.manager.findWallet(username);

            if(wallet == null) {
                JOptionPane.showMessageDialog(this, "User Not Found!", "Authentication Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(!wallet.getPassword().equals(password)) {
                JOptionPane.showMessageDialog(this, "Incorrect Password!", "Authentication Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DashboardFrame dashboard = new DashboardFrame(wallet);
            dashboard.setVisible(true);
            dispose();
        });

        // REGISTER ACTION
        registerBtn.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(this, "Enter New Username:");
            if(username == null || username.trim().isEmpty()) return;

            Wallet existing = Main.manager.findWallet(username.trim());
            if(existing != null) {
                JOptionPane.showMessageDialog(this, "User Already Exists!", "Registration Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String password = JOptionPane.showInputDialog(this, "Enter New Password:");
            if(password == null || password.trim().isEmpty()) return;

            String[] options = {"Normal Wallet", "Premium Wallet"};
            String type = (String) JOptionPane.showInputDialog(
                    this, "Select Account Type", "Wallet Type",
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]
            );

            if(type == null) return;

            if(type.equals("Premium Wallet")) {
                Main.manager.addWallet(new PremiumWallet(username.trim(), password));
            } else {
                Main.manager.addWallet(new Wallet(username.trim(), password));
            }

            // Save data to text file immediately after registration
            FileManager.saveWallets();

            JOptionPane.showMessageDialog(this, "Registration Successful!\nLog in now.", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}