
class BankAccount {
    private String accNumber;
    private String accHolder;
    private double balance;
    private String pin;
    private double dailyWithdrawal;

    public BankAccount(String accNumber, String accHolder, double balance, String pin) {
        this.accNumber = accNumber;
        this.accHolder = accHolder;
        this.balance = balance;
        this.pin = pin;
        this.dailyWithdrawal = 0;
    }

    public String getAccNumber() { return accNumber; }
    public String getAccHolder() { return accHolder; }
    public double getBalance() { return balance; }
    public String getPin() { return pin; }
    public double getDailyWithdrawal() { return dailyWithdrawal; }
    public void setBalance(double balance) { this.balance = balance; }
    public void setPin(String pin) { this.pin = pin; }
    public void setDailyWithdrawal(double amount) { this.dailyWithdrawal = amount; }

    public void deposit(double amount) {
    if (amount > 0) {
        balance += amount;
        FileManager.updateAccount(this); // ✅ Updates accounts.txt
        System.out.println("Deposit successful!");
    } else {
        System.out.println("Invalid deposit amount.");
    }
}

public boolean withdraw(double amount) {
    if (amount > 0 && amount <= balance) {
        if (dailyWithdrawal + amount > 100000) {
            System.out.println("Withdrawal limit reached, come back tomorrow.");
            return false;
        }
        balance -= amount;
        dailyWithdrawal += amount;
        FileManager.updateAccount(this); // ✅ Updates accounts.txt
        System.out.println("Withdrawal successful!");
        return true;
    } else {
        System.out.println("Insufficient balance or invalid amount.");
        return false;
    }
}


}
