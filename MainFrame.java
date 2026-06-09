import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardsContainer;
    @SuppressWarnings("unused")
    private Wallet loggedInWallet;

    public MainFrame() {
        setTitle("Digital Wallet System");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardsContainer = new JPanel(cardLayout);

        // Add screens
        cardsContainer.add(new LoginPanel(), "LOGIN");
        cardsContainer.add(new RegisterPanel(), "REGISTER");

        add(cardsContainer);
        cardLayout.show(cardsContainer, "LOGIN");
    }

    public void showScreen(String screenName) {
        cardLayout.show(cardsContainer, screenName);
    }

    public void loginUser(Wallet wallet) {
        this.loggedInWallet = wallet;
        // Re-create dashboard to populate with active user details
        cardsContainer.add(new DashboardPanel(wallet), "DASHBOARD");
        showScreen("DASHBOARD");
    }

    public void logoutUser() {
        this.loggedInWallet = null;
        showScreen("LOGIN");
    }
}