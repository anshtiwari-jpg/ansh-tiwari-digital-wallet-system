import java.util.ArrayList;

public class WalletManager {

    private ArrayList<Wallet> wallets =
            new ArrayList<>();

    public void addWallet(
            Wallet wallet) {

        wallets.add(wallet);
    }

    public ArrayList<Wallet>
    getWallets() {

        return wallets;
    }

    public Wallet findWallet(
            String name) {

        for (Wallet w : wallets) {

            if (w.getOwnerName()
                    .equalsIgnoreCase(name)) {

                return w;
            }
        }

        return null;
    }
}