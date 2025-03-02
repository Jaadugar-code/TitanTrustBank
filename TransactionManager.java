import java.io.*;
import java.util.*;

class TransactionManager {
    private static final Scanner scanner = new Scanner(System.in);

    public static void deposit(BankAccount account) {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        account.setBalance(account.getBalance() + amount);
        FileManager.updateAccount(account);
        logTransaction(account.getAccNumber(), "Deposit", amount);
        System.out.println("Deposit successful.");
    }

    public static void withdraw(BankAccount account) {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();

        if (amount <= 0 || amount > account.getBalance()) {
            System.out.println("Invalid amount or insufficient balance.");
            return;
        }

        if (account.getDailyWithdrawal() + amount > 100000) {
            System.out.println("Withdrawal limit reached, come back tomorrow.");
            return;
        }

        account.setBalance(account.getBalance() - amount);
        account.setDailyWithdrawal(account.getDailyWithdrawal() + amount);
        FileManager.updateAccount(account);
        logTransaction(account.getAccNumber(), "Withdraw", amount);
        System.out.println("Withdrawal successful.");
    }

    public static void transferMoney(BankAccount sender) {
        System.out.print("Enter recipient account number: ");
        String recipientAcc = scanner.next();

        BankAccount recipient = FileManager.getAccount(recipientAcc);
        if (recipient == null) {
            System.out.println("User does not exist.");
            return;
        }

        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        if (amount <= 0 || amount > sender.getBalance()) {
            System.out.println("Invalid amount or insufficient balance.");
            return;
        }

        System.out.print("Are you sure you want to transfer? (Y/N): ");
        String confirm = scanner.next();
        if (!confirm.equalsIgnoreCase("Y")) {
            System.out.println("Transfer cancelled.");
            return;
        }

        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);
        
        FileManager.updateAccount(sender);
        FileManager.updateAccount(recipient);
        
        logTransaction(sender.getAccNumber(), "Transfer to " + recipient.getAccNumber(), amount);
        logTransaction(recipient.getAccNumber(), "Received from " + sender.getAccNumber(), amount);
        System.out.println("Transfer successful.");
    }

    public static void changePin(BankAccount account) {
        System.out.print("Enter current PIN: ");
        String currentPin = scanner.next();
        if (!currentPin.equals(account.getPin())) {
            System.out.println("Incorrect PIN.");
            return;
        }

        System.out.print("Enter new PIN: ");
        String newPin = scanner.next();
        System.out.print("Confirm new PIN: ");
        String confirmPin = scanner.next();

        if (!newPin.equals(confirmPin)) {
            System.out.println("PINs do not match.");
            return;
        }

        account.setPin(newPin);
        FileManager.updateAccount(account);
        System.out.println("PIN changed successfully.");
    }

    private static void logTransaction(String accNumber, String type, double amount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt", true))) {
            writer.write(accNumber + " - " + type + ": " + amount);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error logging transaction.");
        }
    }
    
    public static void deleteAccount(BankAccount account) {
    System.out.print("Enter your PIN to confirm deletion: ");
    String pin = scanner.next();
    
    if (!pin.equals(account.getPin())) {
        System.out.println("Incorrect PIN. Account deletion cancelled.");
        return;
    }

    System.out.print("Are you sure you want to delete your account? (Y/N): ");
    String confirm = scanner.next();
    if (!confirm.equalsIgnoreCase("Y")) {
        System.out.println("Account deletion cancelled.");
        return;
    }

    if (FileManager.deleteAccount(account)) {
        System.out.println("Your account has been deleted successfully.");
        System.exit(0); // Exit system after deletion
    } else {
        System.out.println("Error deleting account. Try again.");
    }
}

}
