public class Main {
    public static WalletManager manager = new WalletManager();
    private static MainFrame mainFrame;

    public static void main(String[] args) {
        FileManager.loadUsers();

        javax.swing.SwingUtilities.invokeLater(() -> {
            mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }

    public static MainFrame getMainFrame() {
        return mainFrame;
    }
}