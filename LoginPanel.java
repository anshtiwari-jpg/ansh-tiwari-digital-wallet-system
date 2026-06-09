import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LoginPanel extends JPanel {
    public LoginPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 251));

        // Left Side Artistic Banner Block
        JPanel leftBanner = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(29, 78, 216), 0, getHeight(), new Color(30, 41, 59));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        leftBanner.setPreferredSize(new Dimension(450, 800));
        leftBanner.setLayout(new GridBagLayout());
        
        JLabel welcomeTxt = new JLabel("<html><body style='text-align: center;'><h2>Welcome Back!</h2><p>Login to access your Digital Wallet</p></body></html>");
        welcomeTxt.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        welcomeTxt.setForeground(Color.WHITE);
        leftBanner.add(welcomeTxt);

        // Right Side Interactive Form
        JPanel rightFormContainer = new JPanel(new GridBagLayout());
        rightFormContainer.setOpaque(false);
        
        JPanel formCard = new JPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(226, 232, 240), 1, true),
                new EmptyBorder(40, 40, 40, 40)
        ));
        formCard.setPreferredSize(new Dimension(420, 450));

        JLabel loginHeader = new JLabel("Secure Login");
        loginHeader.setFont(new Font("Segoe UI", Font.BOLD, 22));
        loginHeader.setForeground(new Color(30, 41, 59));
        loginHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextField userField = new JTextField();
        userField.setMaximumSize(new Dimension(340, 40));
        userField.setBorder(BorderFactory.createTitledBorder("Username"));

        JPasswordField passField = new JPasswordField();
        passField.setMaximumSize(new Dimension(340, 40));
        passField.setBorder(BorderFactory.createTitledBorder("Password"));

        JButton loginBtn = new JButton("Login");
        loginBtn.setMaximumSize(new Dimension(340, 45));
        loginBtn.setBackground(new Color(37, 99, 235));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setFocusPainted(false);
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton goToRegisterBtn = new JButton("Don't have an account? Register");
        goToRegisterBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        goToRegisterBtn.setForeground(new Color(37, 99, 235));
        goToRegisterBtn.setContentAreaFilled(false);
        goToRegisterBtn.setBorderPainted(false);
        goToRegisterBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        formCard.add(loginHeader); formCard.add(Box.createRigidArea(new Dimension(0, 30)));
        formCard.add(userField);    formCard.add(Box.createRigidArea(new Dimension(0, 15)));
        formCard.add(passField);    formCard.add(Box.createRigidArea(new Dimension(0, 30)));
        formCard.add(loginBtn);     formCard.add(Box.createRigidArea(new Dimension(0, 15)));
        formCard.add(goToRegisterBtn);

        rightFormContainer.add(formCard);

        add(leftBanner, BorderLayout.WEST);
        add(rightFormContainer, BorderLayout.CENTER);

        // Functional Listeners
        loginBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword());

            if(username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both Username & Password", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Wallet wallet = Main.manager.findWallet(username);
            if(wallet == null || !wallet.getPassword().equals(password)) {
                JOptionPane.showMessageDialog(this, "Authentication Failed! Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Main.getMainFrame().loginUser(wallet);
        });

        goToRegisterBtn.addActionListener(e -> Main.getMainFrame().showScreen("REGISTER"));
    }
}