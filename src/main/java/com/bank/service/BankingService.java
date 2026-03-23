package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BankingService {

    // In-memory storage (HashMap acts as our database)
    private final Map<String, Account> accounts = new HashMap<>();
    private int accountCounter = 1001;

    // Pre-load demo accounts on startup
    public BankingService() {
        accounts.put("10010001", new Account("10010001", "Uday Sharma", "1234", 50000.00));
        accounts.put("10010002", new Account("10010002", "Priya Patel",  "5678", 25000.00));
        accounts.put("10010003", new Account("10010003", "Rohan Mehta",  "9999", 10000.00));
    }

    // ── CREATE ACCOUNT ──────────────────────────────────────────────────────────
    public Map<String, Object> createAccount(String name, String pin, double initialDeposit) {
        Map<String, Object> result = new HashMap<>();

        if (name == null || name.trim().isEmpty()) {
            result.put("success", false);
            result.put("message", "Name cannot be empty.");
            return result;
        }
        if (pin == null || pin.length() != 4 || !pin.matches("\\d{4}")) {
            result.put("success", false);
            result.put("message", "PIN must be exactly 4 digits.");
            return result;
        }
        if (initialDeposit < 500) {
            result.put("success", false);
            result.put("message", "Minimum opening balance is ₹500.");
            return result;
        }

        String accNo = "1001" + (++accountCounter);
        Account account = new Account(accNo, name.trim(), pin, initialDeposit);
        accounts.put(accNo, account);

        result.put("success", true);
        result.put("message", "Account created successfully!");
        result.put("accountNumber", accNo);
        result.put("holderName", name.trim());
        result.put("balance", initialDeposit);
        return result;
    }

    // ── LOGIN ────────────────────────────────────────────────────────────────────
    public Map<String, Object> login(String accountNumber, String pin) {
        Map<String, Object> result = new HashMap<>();
        Account account = accounts.get(accountNumber);

        if (account == null) {
            result.put("success", false);
            result.put("message", "Account not found.");
            return result;
        }
        if (!account.validatePin(pin)) {
            result.put("success", false);
            result.put("message", "Incorrect PIN. Please try again.");
            return result;
        }

        result.put("success", true);
        result.put("accountNumber", account.getAccountNumber());
        result.put("holderName", account.getHolderName());
        result.put("maskedAccount", account.getMaskedAccount());
        result.put("balance", account.getBalance());
        return result;
    }

    // ── GET BALANCE ──────────────────────────────────────────────────────────────
    public Map<String, Object> getBalance(String accountNumber) {
        Map<String, Object> result = new HashMap<>();
        Account account = accounts.get(accountNumber);
        if (account == null) {
            result.put("success", false);
            result.put("message", "Account not found.");
            return result;
        }
        result.put("success", true);
        result.put("balance", account.getBalance());
        result.put("holderName", account.getHolderName());
        result.put("maskedAccount", account.getMaskedAccount());
        return result;
    }

    // ── DEPOSIT ──────────────────────────────────────────────────────────────────
    public Map<String, Object> deposit(String accountNumber, double amount, String note) {
        Map<String, Object> result = new HashMap<>();
        Account account = accounts.get(accountNumber);

        if (account == null) {
            result.put("success", false); result.put("message", "Account not found."); return result;
        }
        if (amount <= 0) {
            result.put("success", false); result.put("message", "Amount must be greater than 0."); return result;
        }
        if (amount > 200000) {
            result.put("success", false); result.put("message", "Maximum single deposit is ₹2,00,000."); return result;
        }

        account.deposit(amount, note);
        result.put("success", true);
        result.put("message", String.format("₹%.2f deposited successfully!", amount));
        result.put("newBalance", account.getBalance());
        return result;
    }

    // ── WITHDRAW ─────────────────────────────────────────────────────────────────
    public Map<String, Object> withdraw(String accountNumber, double amount, String note) {
        Map<String, Object> result = new HashMap<>();
        Account account = accounts.get(accountNumber);

        if (account == null) {
            result.put("success", false); result.put("message", "Account not found."); return result;
        }
        if (amount <= 0) {
            result.put("success", false); result.put("message", "Amount must be greater than 0."); return result;
        }
        if (amount > 50000) {
            result.put("success", false); result.put("message", "Maximum single withdrawal is ₹50,000."); return result;
        }
        if (!account.withdraw(amount, note)) {
            result.put("success", false);
            result.put("message", String.format("Insufficient balance! Available: ₹%.2f", account.getBalance()));
            return result;
        }

        result.put("success", true);
        result.put("message", String.format("₹%.2f withdrawn successfully!", amount));
        result.put("newBalance", account.getBalance());
        return result;
    }

    // ── TRANSFER ─────────────────────────────────────────────────────────────────
    public Map<String, Object> transfer(String fromAcc, String toAcc, double amount) {
        Map<String, Object> result = new HashMap<>();

        Account from = accounts.get(fromAcc);
        Account to   = accounts.get(toAcc);

        if (from == null) {
            result.put("success", false); result.put("message", "Your account not found."); return result;
        }
        if (to == null) {
            result.put("success", false); result.put("message", "Recipient account not found."); return result;
        }
        if (fromAcc.equals(toAcc)) {
            result.put("success", false); result.put("message", "Cannot transfer to same account."); return result;
        }
        if (amount <= 0) {
            result.put("success", false); result.put("message", "Transfer amount must be greater than 0."); return result;
        }
        if (from.getBalance() < amount) {
            result.put("success", false);
            result.put("message", String.format("Insufficient balance! Available: ₹%.2f", from.getBalance()));
            return result;
        }

        from.addTransferTransaction("TRANSFER_OUT", amount, from.getBalance() - amount,
            "Transfer to " + to.getMaskedAccount() + " - " + to.getHolderName());
        to.addTransferTransaction("TRANSFER_IN", amount, to.getBalance() + amount,
            "Transfer from " + from.getMaskedAccount() + " - " + from.getHolderName());

        result.put("success", true);
        result.put("message", String.format("₹%.2f transferred to %s successfully!", amount, to.getHolderName()));
        result.put("newBalance", from.getBalance());
        result.put("recipientName", to.getHolderName());
        return result;
    }

    // ── TRANSACTION HISTORY ──────────────────────────────────────────────────────
    public Map<String, Object> getHistory(String accountNumber) {
        Map<String, Object> result = new HashMap<>();
        Account account = accounts.get(accountNumber);

        if (account == null) {
            result.put("success", false); result.put("message", "Account not found."); return result;
        }

        List<Transaction> txns = account.getTransactions();
        // Return last 10, most recent first
        List<Transaction> recent = new ArrayList<>(txns);
        Collections.reverse(recent);
        if (recent.size() > 10) recent = recent.subList(0, 10);

        result.put("success", true);
        result.put("transactions", recent);
        result.put("holderName", account.getHolderName());
        return result;
    }
}
