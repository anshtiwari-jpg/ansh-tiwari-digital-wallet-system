public class PremiumWallet extends Wallet {

    public PremiumWallet(
            String ownerName,
            String password) {

        super(
                ownerName,
                password
        );
    }

    @Override
    public void transferTo(
            Wallet receiver,
            double amount) throws Exception {

        super.transferTo(
                receiver,
                amount
        );

        // Extra 3% Cashback
        deposit(amount * 0.03);
    }
}