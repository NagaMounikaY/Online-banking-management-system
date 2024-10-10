package com.crimsonlogic.onlinebankingsystem.entity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {

    @Test
    public void testAccountCreation() {
        // Arrange
        CustomerInfo customer = new CustomerInfo(); // Assume a constructor or setter methods exist
        customer.setCustomerId("AERY2345"); // Set a sample ID for the customer

        Bank bank = new Bank(); // Assume a constructor or setter methods exist
        bank.setBankId("whvyt588"); // Set a sample ID for the bank

        Account account = new Account();
        account.setAccountId("AC123");
        account.setBalance(1000.0f);
        account.setAccountType("SAVINGS");
        account.setStatus("ACTIVE");
        account.setCustomer(customer);
        account.setBank(bank);

        // Act & Assert
        assertThat(account.getAccountId()).isEqualTo("AC123");
        assertThat(account.getBalance()).isEqualTo(1000.0f);
        assertThat(account.getAccountType()).isEqualTo("SAVINGS");
        assertThat(account.getStatus()).isEqualTo("ACTIVE");
        assertThat(account.getCustomer()).isEqualTo(customer);
        assertThat(account.getBank()).isEqualTo(bank);
    }

    @Test
    public void testAccountBalanceUpdate() {
        // Arrange
        Account account = new Account();
        account.setBalance(1500.0f);

        // Act
        account.setBalance(2000.0f);

        // Assert
        assertThat(account.getBalance()).isEqualTo(2000.0f);
    }

    @Test
    public void testAccountTypeChange() {
        // Arrange
        Account account = new Account();
        account.setAccountType("CURRENT");

        // Act
        account.setAccountType("SAVINGS");

        // Assert
        assertThat(account.getAccountType()).isEqualTo("SAVINGS");
    }
}
