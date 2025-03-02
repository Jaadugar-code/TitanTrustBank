import java.util.*;

class BankSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static BankAccount loggedInAccount;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nWelcome to TitanTrust Bank");
            System.out.println("You can trust us.");
            System.out.println("1. Login\n2. Create Account\n3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    createAccount();
                    break;
                case 3:
                    System.out.println("Thank you for using TitanTrust Bank.");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void login() {
        System.out.print("Enter Account Number: ");
        String accNumber = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();
        
        loggedInAccount = FileManager.getAccount(accNumber, pin);
        if (loggedInAccount != null) {
            System.out.println("Login successful.");
            FileManager.displayAccountHolder(accNumber);

            showMenu();
        } else {
            System.out.println("Invalid account number or PIN.");
        }
    }

    private static void createAccount() {
        System.out.print("Enter Account Holder Name: ");
        String accHolder = scanner.nextLine();
        System.out.print("Set a 4-digit PIN: ");
        String pin = scanner.nextLine();
        
        String accNumber = UUID.randomUUID().toString().substring(0, 6);
        BankAccount newAccount = new BankAccount(accNumber, accHolder, 0, pin);
        FileManager.saveAccount(newAccount);
        
        System.out.println("Account created successfully! Your Account Number: " + accNumber);
    }



    private static void showMenu() {
        while (true) {
            System.out.println("\n1. Deposit\n2. Withdraw\n3. Check Balance\n4. Transfer Money\n5. Change PIN\n6. Check Transactions\n7. Delete Account\n8. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    TransactionManager.deposit(loggedInAccount);
                    break;
                case 2:
                    TransactionManager.withdraw(loggedInAccount);
                    break;
                case 3:
                    System.out.println("Current Balance: " + loggedInAccount.getBalance());
                    break;
                case 4:
                    TransactionManager.transferMoney(loggedInAccount);
                    break;
                case 5:
                    TransactionManager.changePin(loggedInAccount);
                    break;
                case 6:
                    FileManager.displayTransactions(loggedInAccount.getAccNumber());
                    break;
                case 7:
                TransactionManager.deleteAccount(loggedInAccount);
                return;

                case 8:
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
