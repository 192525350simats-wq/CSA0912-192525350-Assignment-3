import java.io.*;
import java.util.*;

class InvalidWithdrawalException extends Exception {
    InvalidWithdrawalException(String message) { super(message); }
}
class InsufficientFundsException extends Exception {
    InsufficientFundsException(String message) { super(message); }
}

public class ATMWithdrawal {
    static int withdraw(String account, String amountText, double balance, boolean cardFileExists)
            throws InvalidWithdrawalException, InsufficientFundsException, IOException {
        if (!cardFileExists) throw new FileNotFoundException("card-reader.dat is missing");
        int amount;
        try { amount = Integer.parseInt(amountText); }
        catch (NumberFormatException e) { throw e; }
        if (amount <= 0) throw new InvalidWithdrawalException("Withdrawal amount must be positive");
        if (amount > balance) throw new InsufficientFundsException("Insufficient account balance");
        System.out.println("Cash dispensed: Rs." + amount + " for account " + account);
        return (int)balance - amount;
    }
    public static void main(String[] args) {
        String[][] tests = {
            {"A100", "500", "2000", "true"},
            {"A101", "abc", "2000", "true"},
            {"A102", "-50", "2000", "true"},
            {"A103", "2500", "2000", "true"},
            {"A104", "100", "2000", "false"}
        };
        for (String[] t : tests) {
            System.out.println("\nAccount="+t[0]+", amount="+t[1]+", cardFile="+t[3]);
            try {
                int remaining = withdraw(t[0], t[1], Double.parseDouble(t[2]), Boolean.parseBoolean(t[3]));
                System.out.println("Remaining balance: Rs." + remaining);
            } catch (NumberFormatException e) {
                System.out.println("Input error: amount must be numeric");
            } catch (InvalidWithdrawalException | InsufficientFundsException e) {
                System.out.println("Transaction rejected: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("File error: " + e.getMessage());
            } finally {
                System.out.println("Transaction session closed.");
            }
        }
    }
}
