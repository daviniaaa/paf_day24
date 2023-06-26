package day24.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import day24.model.BankAccount;
import day24.service.BankAccountService;

@RestController
@RequestMapping("/api/bankaccounts")
public class BankAccountController {
    
    @Autowired
    BankAccountService service;

    @PostMapping
    public ResponseEntity<Boolean> createAccount(@RequestBody BankAccount bankAccount) {
        Boolean accountCreated = service.createAccount(bankAccount);

        if (accountCreated) {
            return ResponseEntity.ok().body(accountCreated);
        } else {
            // (custom) exception handling
            return ResponseEntity.internalServerError().body(accountCreated);
        }
    }

    @GetMapping("/{account_id}")
    public ResponseEntity<BankAccount> getAccountById(@PathVariable("account_id") Integer id) {
        BankAccount bankAccount = service.retrieveAccountById(id);

        return new ResponseEntity<BankAccount>(bankAccount, HttpStatus.OK);
    }

    @PostMapping("/transfer/{transfer_id}/receive/{receive_id}/amount/{amount}")
    public ResponseEntity<Boolean> paynow(@PathVariable("transfer_id") Integer transferAccountId,
    @PathVariable("receive_id") Integer receiveAccountId, @PathVariable("amount") Float transferAmount) {
        Boolean transferSuccess = service.transferMoney(transferAccountId, receiveAccountId, transferAmount);

        return new ResponseEntity<Boolean>(transferSuccess, HttpStatus.OK);
    }
}
