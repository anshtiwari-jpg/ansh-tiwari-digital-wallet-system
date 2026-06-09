import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class RegisterPanel extends JPanel {
    public RegisterPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 251));

        JPanel leftBanner = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(16, 185, 129), 0, getHeight(), new Color(30, 41, 59));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        leftBanner.setPreferredSize(new Dimension(450, 800));
        leftBanner.setLayout(new GridBagLayout());
        
        JLabel registerTxt = new JLabel("<html><body style='text-align: center;'><h2>Join Us!</h2><p>Create an account to quickly manage transactions.</p></body></html>");
        registerTxt.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        registerTxt.setForeground(Color.WHITE);
        leftBanner.add(registerTxt);

        JPanel rightFormContainer = new JPanel(new GridBagLayout());
        rightFormContainer.setOpaque(false);

        JPanel formCard = new JPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(226, 232, 240), 1, true),
                new EmptyBorder(30, 40, 30, 40)
        ));
        formCard.setPreferredSize(new Dimension(420, 520));

        JLabel registerHeader = new JLabel("Create New Account");
        registerHeader.setFont(new Font("Segoe UI", Font.BOLD, 22));
        registerHeader.setForeground(new Color(30, 41, 59));
        registerHeader.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField userField = new JTextField();
        userField.setMaximumSize(new Dimension(340, 40));
        userField.setBorder(BorderFactory.createTitledBorder("Username / Full Name"));

        JPasswordField passField = new JPasswordField();
        passField.setMaximumSize(new Dimension(340, 40));
        passField.setBorder(BorderFactory.createTitledBorder("Password"));

        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Normal Wallet", "Premium Wallet"});
        typeBox.setMaximumSize(new Dimension(340, 40));
        typeBox.setBorder(BorderFactory.createTitledBorder("Account Tier Strategy"));

        JButton registerBtn = new JButton("Register");
        registerBtn.setMaximumSize(new Dimension(340, 45));
        registerBtn.setBackground(new Color(16, 185, 129));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerBtn.setFocusPainted(false);
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton goToLoginBtn = new JButton("Already have an account? Login");
        goToLoginBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        goToLoginBtn.setForeground(new Color(37, 99, 235));
        goToLoginBtn.setContentAreaFilled(false);
        goToLoginBtn.setBorderPainted(false);
        goToLoginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        formCard.add(registerHeader);  formCard.add(Box.createRigidArea(new Dimension(0, 25)));
        formCard.add(userField);       formCard.add(Box.createRigidArea(new Dimension(0, 15)));
        formCard.add(passField);       formCard.add(Box.createRigidArea(new Dimension(0, 15)));
        formCard.add(typeBox);         formCard.add(Box.createRigidArea(new Dimension(0, 30)));
        formCard.add(registerBtn);     formCard.add(Box.createRigidArea(new Dimension(0, 15)));
        formCard.add(goToLoginBtn);

        rightFormContainer.add(formCard);

        add(leftBanner, BorderLayout.WEST);
        add(rightFormContainer, BorderLayout.CENTER);

        registerBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            if(username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be blank!", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if(Main.manager.findWallet(username) != null) {
                JOptionPane.showMessageDialog(this, "User already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(typeBox.getSelectedItem().toString().equals("Premium Wallet")) {
                Main.manager.addWallet(new PremiumWallet(username, password));
            } else {
                Main.manager.addWallet(new Wallet(username, password));
            }

            FileManager.saveWallets();
            JOptionPane.showMessageDialog(this, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            Main.getMainFrame().showScreen("LOGIN");
        });

        goToLoginBtn.addActionListener(e -> Main.getMainFrame().showScreen("LOGIN"));
    }
}