import java.io.*;
import java.util.*;

class FileManager {
    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static final String TRANSACTIONS_FILE = "transactions.txt";

    public static void saveAccount(BankAccount account) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE, true))) {
            writer.write(account.getAccNumber() + "," + account.getAccHolder() + "," + account.getBalance() + "," + account.getPin());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving account.");
        }
    }

    public static BankAccount getAccount(String accNumber, String pin) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(accNumber) && parts[3].equals(pin)) {
                    return new BankAccount(parts[0], parts[1], Double.parseDouble(parts[2]), parts[3]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading accounts.");
        }
        return null;
    }

    public static BankAccount getAccount(String accNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(accNumber)) {
                    return new BankAccount(parts[0], parts[1], Double.parseDouble(parts[2]), parts[3]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading accounts.");
        }
        return null;
    }

    public static void updateAccount(BankAccount account) {
    List<String> lines = new ArrayList<>();
    boolean updated = false;
    try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts[0].equals(account.getAccNumber())) {
            
                line = account.getAccNumber() + "," + account.getAccHolder() + "," + account.getBalance() + "," + account.getPin();
                updated = true;
            }
            lines.add(line);
        }
    } catch (IOException e) {
        System.out.println("Error reading accounts.");
    }

    if (updated) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing updated account.");
        }
    }
}


    public static void displayTransactions(String accNumber) {
        boolean hasTransactions = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTIONS_FILE))) {
            String line;
            System.out.println("Transactions for account: " + accNumber);
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(accNumber)) {
                    System.out.println(line);
                    hasTransactions = true;
                }
            }
            if (!hasTransactions) {
                System.out.println("No transactions found.");
            }
        } catch (IOException e) {
            System.out.println("Error reading transactions.");
        }
    }

    public static void displayAccountHolder(String accNumber) {
        BankAccount account = getAccount(accNumber);
        if (account != null) {
            System.out.println("\nWelcome, " + account.getAccHolder() + "!\n");
        }
    }

    public static boolean deleteAccount(BankAccount account) {
    List<String> lines = new ArrayList<>();
    boolean deleted = false;

    try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (!parts[0].equals(account.getAccNumber())) {
                lines.add(line);
            } else {
                deleted = true;
            }
        }
    } catch (IOException e) {
        System.out.println("Error reading accounts.");
    }

    if (deleted) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing updated accounts.");
            return false;
        }
    }
    return deleted;
}

}
