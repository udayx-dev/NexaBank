package com.bank.controller;

import com.bank.service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class BankingController {

    @Autowired
    private BankingService bankingService;

    // POST /api/account/create
    @PostMapping("/account/create")
    public ResponseEntity<Map<String, Object>> createAccount(@RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        String pin  = (String) body.get("pin");
        double initialDeposit = Double.parseDouble(body.getOrDefault("initialDeposit", 0).toString());
        return ResponseEntity.ok(bankingService.createAccount(name, pin, initialDeposit));
    }

    // POST /api/account/login
    @PostMapping("/account/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> body) {
        String accountNumber = (String) body.get("accountNumber");
        String pin           = (String) body.get("pin");
        return ResponseEntity.ok(bankingService.login(accountNumber, pin));
    }

    // GET /api/account/balance/{accountNumber}
    @GetMapping("/account/balance/{accountNumber}")
    public ResponseEntity<Map<String, Object>> getBalance(@PathVariable String accountNumber) {
        return ResponseEntity.ok(bankingService.getBalance(accountNumber));
    }

    // POST /api/transaction/deposit
    @PostMapping("/transaction/deposit")
    public ResponseEntity<Map<String, Object>> deposit(@RequestBody Map<String, Object> body) {
        String accountNumber = (String) body.get("accountNumber");
        double amount        = Double.parseDouble(body.get("amount").toString());
        String note          = (String) body.getOrDefault("note", "");
        return ResponseEntity.ok(bankingService.deposit(accountNumber, amount, note));
    }

    // POST /api/transaction/withdraw
    @PostMapping("/transaction/withdraw")
    public ResponseEntity<Map<String, Object>> withdraw(@RequestBody Map<String, Object> body) {
        String accountNumber = (String) body.get("accountNumber");
        double amount        = Double.parseDouble(body.get("amount").toString());
        String note          = (String) body.getOrDefault("note", "");
        return ResponseEntity.ok(bankingService.withdraw(accountNumber, amount, note));
    }

    // POST /api/transaction/transfer
    @PostMapping("/transaction/transfer")
    public ResponseEntity<Map<String, Object>> transfer(@RequestBody Map<String, Object> body) {
        String fromAcc = (String) body.get("fromAccount");
        String toAcc   = (String) body.get("toAccount");
        double amount  = Double.parseDouble(body.get("amount").toString());
        return ResponseEntity.ok(bankingService.transfer(fromAcc, toAcc, amount));
    }

    // GET /api/transaction/history/{accountNumber}
    @GetMapping("/transaction/history/{accountNumber}")
    public ResponseEntity<Map<String, Object>> getHistory(@PathVariable String accountNumber) {
        return ResponseEntity.ok(bankingService.getHistory(accountNumber));
    }
}
