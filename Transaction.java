import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private String type;
    private double amount;
    private String dateTime;

    public Transaction(
            String type,
            double amount) {

        this.type = type;
        this.amount = amount;

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "dd-MM-yyyy hh:mm a"
                );

        dateTime =
                LocalDateTime.now()
                        .format(formatter);
    }

    @Override
    public String toString() {

        return dateTime +
                "\n" +
                type +
                " : ₹" +
                amount;
    }

    public Object getType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }

    public double getAmount() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAmount'");
    }
}