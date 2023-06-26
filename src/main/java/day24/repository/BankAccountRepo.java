package day24.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import day24.exception.BankAccountNotFoundException;
import day24.model.BankAccount;

@Repository
public class BankAccountRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String GET_ACCOUNT_SQL = "select * from bank_account where id = ?";
    private final String WITHDRAW_SQL = "update bank_account set balance = balance - ? where id = ?";
    private final String DEPOSIT_SQL = "update bank_account set balance = balance + ? where id = ?";
    // private final String CREATE_ACCOUNT_SQL = "insert into bank_account (full_name, is_blocked, is_active, account_type, balance) values (?, ?, ?, ?, ?)";
    private final String CREATE_ACCOUNT_SQL = "insert into bank_account values (null, ?, ?, ?, ?, ?)";

    // public BankAccount getAccountById(Integer bankAccountId) {
    //     BankAccount bankAccount = jdbcTemplate.queryForObject(GET_ACCOUNT_SQL, 
    //         BeanPropertyRowMapper.newInstance(BankAccount.class), bankAccountId);
        
    //     return bankAccount;
    // } 

    public BankAccount getAccountById(Integer bankAccountId) {
        List<BankAccount> bankAccounts = jdbcTemplate.query(GET_ACCOUNT_SQL, 
            BeanPropertyRowMapper.newInstance(BankAccount.class), bankAccountId);
        
        if (bankAccounts.isEmpty()) {
            throw new BankAccountNotFoundException("Account does not exist.");
        }

        BankAccount bankAccount = bankAccounts.get(0);
        return bankAccount;
    } 

    // update bank_account set balance = balance - ? where id = ?
    public Boolean withdrawAmount(Integer withdrawAccountId, Float withdrawAmount) {
        Integer result = jdbcTemplate.update(WITHDRAW_SQL, withdrawAmount, withdrawAccountId);

        return (result > 0);
    }

    // update bank_account set balance = balance + ? where id = ?
    public Boolean depositAmount(Integer depositAccountId, Float depositAmount) {
        Integer result = jdbcTemplate.update(DEPOSIT_SQL, depositAmount, depositAccountId);
        return (result > 0);
    }

    // insert into bank_account (full_name, is_blocked, is_active, account_type, balance) values (?, ?, ?, ?, ?)
    public Boolean createAccount(BankAccount bankAccount) {
        Integer result = jdbcTemplate.update(CREATE_ACCOUNT_SQL,
            bankAccount.getFullName(), bankAccount.getIsBlocked(), bankAccount.getIsActive(),
            bankAccount.getAccountType(), bankAccount.getBalance());
        return (result > 0);
    }

}
