import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class DashboardPanel extends JPanel {
    private Wallet currentWallet;
    private JLabel balanceLabel;
    private JLabel cashbackLabel;
    private DefaultTableModel tableModel;
    private JPanel dynamicContentArea;
    private CardLayout dynamicCardLayout;

    public DashboardPanel(Wallet wallet) {
        this.currentWallet = wallet;
        setLayout(new BorderLayout());

        // ==========================================
        // 1. LEFT SIDEBAR COMPONENT
        // ==========================================
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(11, 29, 58));
        sidebar.setPreferredSize(new Dimension(240, 800));
        sidebar.setBorder(new EmptyBorder(30, 20, 30, 20));

        JLabel appLogo = new JLabel("  Digital Wallet");
        appLogo.setForeground(Color.WHITE);
        appLogo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        appLogo.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(appLogo);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));

        JButton dashMenuBtn = createSidebarButton("📊  Dashboard", true);
        JButton addMoneyMenuBtn = createSidebarButton("📥  Add Money", false);
        JButton sendMoneyMenuBtn = createSidebarButton("📤  Send Money", false);
        JButton logoutBtn = createSidebarButton("🚪  Logout", false);
        logoutBtn.setForeground(new Color(248, 113, 113));

        sidebar.add(dashMenuBtn); sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(addMoneyMenuBtn); sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(sendMoneyMenuBtn); sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(logoutBtn);

        // ==========================================
        // 2. DYNAMIC WORKSPACE (CardLayout Layout)
        // ==========================================
        dynamicCardLayout = new CardLayout();
        dynamicContentArea = new JPanel(dynamicCardLayout);
        dynamicContentArea.setBackground(new Color(245, 247, 251));

        // Add modular dynamic workflows subviews
        dynamicContentArea.add(buildMainDashboardView(), "MAIN_VIEW");
        dynamicContentArea.add(buildDepositView(), "DEPOSIT_VIEW");
        dynamicContentArea.add(buildTransferView(), "TRANSFER_VIEW");

        add(sidebar, BorderLayout.WEST);
        add(dynamicContentArea, BorderLayout.CENTER);

        // Structural Side Nav Triggers Setup
        dashMenuBtn.addActionListener(e -> {
            updateDashboardMetrics();
            dynamicCardLayout.show(dynamicContentArea, "MAIN_VIEW");
        });
        addMoneyMenuBtn.addActionListener(e -> dynamicCardLayout.show(dynamicContentArea, "DEPOSIT_VIEW"));
        sendMoneyMenuBtn.addActionListener(e -> dynamicCardLayout.show(dynamicContentArea, "TRANSFER_VIEW"));
        logoutBtn.addActionListener(e -> Main.getMainFrame().logoutUser());
    }

    // --- Sub-View Panel Builders ---
    
    private JPanel buildMainDashboardView() {
        JPanel mainWorkspace = new JPanel(new BorderLayout());
        mainWorkspace.setBackground(new Color(245, 247, 251));
        mainWorkspace.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Header section 
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel mainHeading = new JLabel("Wallet Dashboard");
        mainHeading.setFont(new Font("Segoe UI", Font.BOLD, 28));
        mainHeading.setForeground(new Color(24, 37, 58));
        
        JLabel userBadge = new JLabel("Welcome, " + currentWallet.getOwnerName() + " 👤");
        userBadge.setFont(new Font("Segoe UI", Font.BOLD, 14));

        headerPanel.add(mainHeading, BorderLayout.WEST);
        headerPanel.add(userBadge, BorderLayout.EAST);
        mainWorkspace.add(headerPanel, BorderLayout.NORTH);

        // Central Body
        JPanel centerBody = new JPanel(new BorderLayout(0, 30));
        centerBody.setOpaque(false);
        centerBody.setBorder(new EmptyBorder(25, 0, 0, 0));

        // Row of Metric Cards
        JPanel metricsRow = new JPanel(new GridLayout(1, 2, 25, 0));
        metricsRow.setOpaque(false);
        metricsRow.setPreferredSize(new Dimension(0, 130));

        // Total Balance Display Card
        JPanel balanceCard = createCardContainer();
        balanceCard.setLayout(new BorderLayout());
        balanceCard.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel balTitle = new JLabel("Wallet Balance");
        balTitle.setForeground(Color.GRAY);
        balanceLabel = new JLabel("₹ " + String.format("%.2f", currentWallet.getBalance()));
        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        balanceLabel.setForeground(new Color(37, 99, 235));
        balanceCard.add(balTitle, BorderLayout.NORTH);
        balanceCard.add(balanceLabel, BorderLayout.CENTER);
        metricsRow.add(balanceCard);

        // Cashback Status Monitor Card
        JPanel cashbackCard = createCardContainer();
        cashbackCard.setLayout(new BorderLayout());
        cashbackCard.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel cashTitle = new JLabel("Total Cashback Tier");
        cashTitle.setForeground(Color.GRAY);
        cashbackLabel = new JLabel(currentWallet instanceof PremiumWallet ? "3.0% Active Reward" : "₹ 0.00 (Standard)");
        cashbackLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        cashbackLabel.setForeground(new Color(22, 163, 74));
        cashbackCard.add(cashTitle, BorderLayout.NORTH);
        cashbackCard.add(cashbackLabel, BorderLayout.CENTER);
        metricsRow.add(cashbackCard);

        centerBody.add(metricsRow, BorderLayout.NORTH);

        // History Table Architecture Setup
        JPanel tableWrapper = new JPanel(new BorderLayout(0, 15));
        tableWrapper.setOpaque(false);
        JLabel tableTitle = new JLabel("Recent Transactions & History Logs");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        tableModel = new DefaultTableModel(new String[]{"Index ID", "Transaction Narrative Trace Log"}, 0);
        JTable historyTable = new JTable(tableModel);
        historyTable.setRowHeight(35);
        JScrollPane tableScroll = new JScrollPane(historyTable);
        tableScroll.setBorder(new LineBorder(new Color(230, 235, 242)));
        
        tableWrapper.add(tableTitle, BorderLayout.NORTH);
        tableWrapper.add(tableScroll, BorderLayout.CENTER);
        centerBody.add(tableWrapper, BorderLayout.CENTER);

        mainWorkspace.add(centerBody, BorderLayout.CENTER);
        populateHistoryLogs();
        return mainWorkspace;
    }

    private JPanel buildDepositView() {
        JPanel view = new JPanel(new GridBagLayout());
        view.setBackground(Color.WHITE);

        JPanel formBox = new JPanel();
        formBox.setLayout(new BoxLayout(formBox, BoxLayout.Y_AXIS));
        formBox.setOpaque(false);

        JLabel title = new JLabel("Deposit Money");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField amountField = new JTextField();
        amountField.setMaximumSize(new Dimension(300, 45));
        amountField.setBorder(BorderFactory.createTitledBorder("Enter Amount (₹)"));

        JButton depositActionBtn = new JButton("Deposit Now");
        depositActionBtn.setMaximumSize(new Dimension(300, 45));
        depositActionBtn.setBackground(new Color(37, 99, 235));
        depositActionBtn.setForeground(Color.WHITE);
        depositActionBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        formBox.add(title); formBox.add(Box.createRigidArea(new Dimension(0, 25)));
        formBox.add(amountField); formBox.add(Box.createRigidArea(new Dimension(0, 25)));
        formBox.add(depositActionBtn);

        view.add(formBox);

        depositActionBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText().trim());
                if(amount <= 0) throw new Exception();
                currentWallet.deposit(amount);
                FileManager.saveWallets();
                JOptionPane.showMessageDialog(this, "Deposited ₹" + amount + " successfully!");
                amountField.setText("");
                updateDashboardMetrics();
                dynamicCardLayout.show(dynamicContentArea, "MAIN_VIEW");
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid deposit amount!", "Execution Failure", JOptionPane.ERROR_MESSAGE);
            }
        });

        return view;
    }

    private JPanel buildTransferView() {
        JPanel view = new JPanel(new GridBagLayout());
        view.setBackground(Color.WHITE);

        JPanel formBox = new JPanel();
        formBox.setLayout(new BoxLayout(formBox, BoxLayout.Y_AXIS));
        formBox.setOpaque(false);

        JLabel title = new JLabel("Fund Transfer System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField recipientField = new JTextField();
        recipientField.setMaximumSize(new Dimension(300, 45));
        recipientField.setBorder(BorderFactory.createTitledBorder("Receiver Username"));

        JTextField amountField = new JTextField();
        amountField.setMaximumSize(new Dimension(300, 45));
        amountField.setBorder(BorderFactory.createTitledBorder("Amount (₹)"));

        JButton transferActionBtn = new JButton("Transfer Now");
        transferActionBtn.setMaximumSize(new Dimension(300, 45));
        transferActionBtn.setBackground(new Color(30, 41, 59));
        transferActionBtn.setForeground(Color.WHITE);
        transferActionBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        formBox.add(title); formBox.add(Box.createRigidArea(new Dimension(0, 25)));
        formBox.add(recipientField); formBox.add(Box.createRigidArea(new Dimension(0, 15)));
        formBox.add(amountField); formBox.add(Box.createRigidArea(new Dimension(0, 25)));
        formBox.add(transferActionBtn);

        view.add(formBox);

        transferActionBtn.addActionListener(e -> {
            String receiverName = recipientField.getText().trim();
            String amountStr = amountField.getText().trim();

            Wallet receiver = Main.manager.findWallet(receiverName);
            if(receiver == null || receiver == currentWallet) {
                JOptionPane.showMessageDialog(this, "Invalid Target Account Mapping Rule Error!", "Routing Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double amount = Double.parseDouble(amountStr);
                currentWallet.transferTo(receiver, amount);
                FileManager.saveWallets();
                JOptionPane.showMessageDialog(this, "Transferred ₹" + amount + " safely to " + receiverName);
                recipientField.setText(""); amountField.setText("");
                updateDashboardMetrics();
                dynamicCardLayout.show(dynamicContentArea, "MAIN_VIEW");
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Transfer Execution Refused", JOptionPane.ERROR_MESSAGE);
            }
        });

        return view;
    }

    // --- Helpers / Render Customizers ---

    private void updateDashboardMetrics() {
        balanceLabel.setText("₹ " + String.format("%.2f", currentWallet.getBalance()));
        populateHistoryLogs();
    }

    private void populateHistoryLogs() {
        tableModel.setRowCount(0);
        int counter = 1;
        for(Transaction t : currentWallet.getHistory()) {
            tableModel.addRow(new Object[]{ "TXN-" + (counter++), t.toString() });
        }
    }

    private JPanel createCardContainer() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, 12, 12));
                g2.setColor(new Color(226, 232, 240));
                g2.draw(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, 12, 12));
                g2.dispose();
            }
        };
    }

    private JButton createSidebarButton(String text, boolean isActive) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", isActive ? Font.BOLD : Font.PLAIN, 14));
        btn.setMaximumSize(new Dimension(200, 45));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if(isActive) {
            btn.setBackground(new Color(37, 99, 235));
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(new Color(11, 29, 58));
            btn.setForeground(new Color(148, 163, 184));
        }
        return btn;
    }
}