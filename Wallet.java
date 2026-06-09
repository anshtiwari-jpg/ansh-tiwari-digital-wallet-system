import java.util.ArrayList;

public class Wallet {

    private String ownerName;
    private String password;
    private double balance;

    // Wallet ID System
    private static int counter = 100;
    private String walletId;

    protected ArrayList<Transaction> history =
            new ArrayList<>();

    private static double cashbackRate = 0.02;

    public Wallet(
            String ownerName,
            String password) {

        this.ownerName = ownerName;
        this.password = password;
        this.balance = 0;

        counter++;
        walletId = "W" + counter;
    }

    // Getters
    public String getOwnerName() {
        return ownerName;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public String getWalletId() {
        return walletId;
    }

    public static double getCashbackRate() {
        return cashbackRate;
    }

    // Required for FileManager
    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Deposit
    public void deposit(double amount) {

        if(amount > 0) {

            balance += amount;

            history.add(
                    new Transaction(
                            "Deposit",
                            amount
                    )
            );
        }
    }

    // Transfer
    public void transferTo(
            Wallet receiver,
            double amount) throws Exception {

        if(amount <= 0) {

            throw new Exception(
                    "Invalid Amount"
            );
        }

        if(balance < amount) {

            throw new Exception(
                    "Insufficient Balance"
            );
        }

        // Deduct from sender
        balance -= amount;

        // Add to receiver
        receiver.balance += amount;

        // Cashback
        double cashback =
                amount * cashbackRate;

        balance += cashback;

        // Sender History
        history.add(
                new Transaction(
                        "Transfer Sent",
                        amount
                )
        );

        // Receiver History
        receiver.history.add(
                new Transaction(
                        "Transfer Received",
                        amount
                )
        );

        // Cashback History
        history.add(
                new Transaction(
                        "Cashback",
                        cashback
                )
        );
    }

    // Transaction History
    public ArrayList<Transaction>
    getHistory() {

        return history;
    }

    
    public double getTotalCashback() {

        double total = 0;

        for(Transaction t : history) {
            if(t.getType().equals("Cashback")) {
                total += t.getAmount();
            }
        }

        return total;
    }
}
