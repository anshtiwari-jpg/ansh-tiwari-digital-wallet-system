import java.io.*;
import java.util.Scanner;

public class FileManager {

    // Save All Wallets
    public static void saveWallets() {

        try {

            PrintWriter writer =
                    new PrintWriter(
                            new FileWriter(
                                    "wallets.txt"
                            )
                    );

            for(Wallet w :
                    Main.manager.getWallets()) {

                String type = "Normal";

                if(w instanceof PremiumWallet) {
                    type = "Premium";
                }

                writer.println(
                        w.getOwnerName() + "," +
                        w.getPassword() + "," +
                        type + "," +
                        w.getBalance()
                );
            }

            writer.close();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    // Load All Wallets
    public static void loadUsers() {

        try {

            File file =
                    new File("wallets.txt");

            if(!file.exists()) {
                return;
            }

            Scanner sc =
                    new Scanner(file);

            while(sc.hasNextLine()) {

                String[] data =
                        sc.nextLine().split(",");

                String username =
                        data[0];

                String password =
                        data[1];

                String type =
                        data[2];

                double balance =
                        Double.parseDouble(
                                data[3]
                        );

                Wallet wallet;

                if(type.equals("Premium")) {

                    wallet =
                            new PremiumWallet(
                                    username,
                                    password
                            );

                } else {

                    wallet =
                            new Wallet(
                                    username,
                                    password
                            );
                }

                wallet.setBalance(
                        balance
                );

                Main.manager.addWallet(
                        wallet
                );
            }

            sc.close();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
}