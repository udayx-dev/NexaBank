package com.bank.model;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountNumber;
    private String holderName;
    private String pin;
    private double balance;
    private List<Transaction> transactions;

    public Account(String accountNumber, String holderName, String pin, double initialBalance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        if (initialBalance > 0) {
            transactions.add(new Transaction("DEPOSIT", initialBalance, initialBalance, "Account Opening Balance"));
        }
    }

    public boolean validatePin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public void deposit(double amount, String note) {
        this.balance += amount;
        transactions.add(new Transaction("DEPOSIT", amount, balance,
            note == null || note.isEmpty() ? "Cash Deposit" : note));
    }

    public boolean withdraw(double amount, String note) {
        if (amount > balance) return false;
        this.balance -= amount;
        transactions.add(new Transaction("WITHDRAW", amount, balance,
            note == null || note.isEmpty() ? "Cash Withdrawal" : note));
        return true;
    }

    public void addTransferTransaction(String type, double amount, double newBalance, String desc) {
        this.balance = newBalance;
        transactions.add(new Transaction(type, amount, newBalance, desc));
    }

    // Getters
    public String getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }
    public List<Transaction> getTransactions() { return transactions; }

    // For display (mask account number)
    public String getMaskedAccount() {
        return "****" + accountNumber.substring(accountNumber.length() - 4);
    }
}
