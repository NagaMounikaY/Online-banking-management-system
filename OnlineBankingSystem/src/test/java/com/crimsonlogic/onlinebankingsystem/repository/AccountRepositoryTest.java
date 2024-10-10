package com.crimsonlogic.onlinebankingsystem.repository;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.crimsonlogic.onlinebankingsystem.entity.Account;
import com.crimsonlogic.onlinebankingsystem.entity.Bank;
import com.crimsonlogic.onlinebankingsystem.entity.CustomerInfo;

@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private BankRepository bankRepository;

    private CustomerInfo customer;
    private Bank bank;
    private Account account1;
    private Account account2;

    @BeforeEach
    public void setUp() {
        // Create and save customer
        customer = new CustomerInfo();
        customer.setCustomerName("John Doe");
        customerInfoRepository.save(customer);

        // Create and save bank
        bank = new Bank();
        bank.setBankName("Test Bank");
        bankRepository.save(bank);

        // Create and save accounts
        account1 = new Account();
        account1.setBalance(1000.0f);
        account1.setAccountType("SAVINGS");
        account1.setStatus("ACTIVE");
        account1.setCustomer(customer);
        account1.setBank(bank);
        accountRepository.save(account1);

        account2 = new Account();
        account2.setBalance(2000.0f);
        account2.setAccountType("CURRENT");
        account2.setStatus("ACTIVE");
        account2.setCustomer(customer);
        account2.setBank(bank);
        accountRepository.save(account2);
    }

  
}
