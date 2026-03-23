package com.bank.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private String type;       // DEPOSIT, WITHDRAW, TRANSFER
    private double amount;
    private double balanceAfter;
    private String timestamp;
    private String description;

    public Transaction(String type, double amount, double balanceAfter, String description) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.description = description;
        this.timestamp = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));
    }

    public String getType() { return type; }
    public double getAmount() { return amount; }
    public double getBalanceAfter() { return balanceAfter; }
    public String getTimestamp() { return timestamp; }
    public String getDescription() { return description; }
}
