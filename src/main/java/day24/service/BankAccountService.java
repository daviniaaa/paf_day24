package day24.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import day24.exception.AccountBlockedOrInactiveException;
import day24.exception.InsufficientFundsException;
import day24.model.BankAccount;
import day24.repository.BankAccountRepo;

@Service
public class BankAccountService {
    @Autowired
    BankAccountRepo repo;

    public BankAccount retrieveAccountById(Integer accountId) {
        return repo.getAccountById(accountId);
    }

    public Boolean createAccount(BankAccount bankAccount) {
        return repo.createAccount(bankAccount);
    }

    @Transactional
    public Boolean transferMoney(Integer withdrawAccountId, Integer depositAccountId, Float transferAmount) {
        // checks------------------------
        // 1. transferrer exists?
        Boolean transferExists = false;
        BankAccount transferAcc = repo.getAccountById(withdrawAccountId);

        if (transferAcc != null) {
            transferExists = true;
        }

        // 2. receiver exists?
        Boolean receiveExists = false;
        BankAccount receiveAcc = repo.getAccountById(depositAccountId);

        if (receiveAcc != null) {
            receiveExists = true;
        }

        // 3. transferrer active?
        // 5. transferrer not blocked?
        Boolean transferAllowed = false;

        if (transferAcc.getIsActive() && !transferAcc.getIsBlocked()) {
            transferAllowed = true;
        }

        // 4. receiver active?
        // 6. receiver not blocked?
        Boolean receiveAllowed = false;

        if (receiveAcc.getIsActive() && !receiveAcc.getIsBlocked()) {
            receiveAllowed = true;
        }

        // 7. transferrer has enough money?
        Boolean enoughMoney = false;
        if (transferAcc.getBalance() > transferAmount) {
            enoughMoney = true;
        }


        // transfer---------------------
        if (transferExists && transferAllowed && receiveAllowed && receiveExists && enoughMoney) {
            // 1. withdraw the amount from the transferrer
            repo.withdrawAmount(withdrawAccountId, transferAmount);
            
            // 2. deposit amount into receiver
            repo.depositAmount(depositAccountId, transferAmount);
        } else {
            if (!transferAllowed) {
                throw new AccountBlockedOrInactiveException("Transferrer is either blocked or inactive.");
            }

            if (!receiveAllowed) {
                throw new AccountBlockedOrInactiveException("Receiver is either blocked or inactive.");
            }

            if (!enoughMoney) {
                throw new InsufficientFundsException("Transferrer does not have sufficient balance for the transaction.");
            }
            
        }

        return true;
    }
}
