package com.crimsonlogic.onlinebankingsystem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.crimsonlogic.onlinebankingsystem.dto.AccountDTO;
import com.crimsonlogic.onlinebankingsystem.entity.Account;
import com.crimsonlogic.onlinebankingsystem.entity.Bank;
import com.crimsonlogic.onlinebankingsystem.entity.CustomerInfo;
import com.crimsonlogic.onlinebankingsystem.repository.AccountRepository;
import com.crimsonlogic.onlinebankingsystem.repository.BankRepository;
import com.crimsonlogic.onlinebankingsystem.repository.CustomerInfoRepository;
import com.crimsonlogic.onlinebankingsystem.service.impl.AccountServiceImpl;

public class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerInfoRepository customerInfoRepository;

    @Mock
    private BankRepository bankRepository;

    private Account account;
    private CustomerInfo customerInfo;
    private Bank bank;
    private AccountDTO accountDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize CustomerInfo
        customerInfo = new CustomerInfo();
        customerInfo.setCustomerName("Jane Doe");
        customerInfo.setCustomerMobileNumber("1234567890");
        // Set additional properties as needed

        // Initialize Bank
        bank = new Bank();
        bank.setBankName("Test Bank");
        bank.setBankCode(123);

        // Initialize Account
        account = new Account();
        account.setAccountId("ACC001");
        account.setCustomer(customerInfo);
        account.setBank(bank);
        account.setStatus("Active");

        // Initialize AccountDTO
        accountDTO = new AccountDTO();
        accountDTO.setCustomerId("CUST001");
        accountDTO.setBankName("Test Bank");
        accountDTO.setStatus("Active");
    }

    @Test
    public void testUpdateAccountStatus() {
        when(accountRepository.findById("ACC001")).thenReturn(Optional.of(account));

        accountService.updateAccountStatus("ACC001", "Inactive");

        assertThat(account.getStatus()).isEqualTo("Inactive");
        verify(accountRepository).save(account);
    }
}
