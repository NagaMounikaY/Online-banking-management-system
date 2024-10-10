package com.crimsonlogic.onlinebankingsystem.entity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

public class BankTest {

    @Test
    public void testBankCreation() {
        // Arrange
        ManagerInfo manager = new ManagerInfo(); // Assume a constructor or setter methods exist
        manager.setUserId("RTYUI356");; // Set a sample ID for the manager

        Bank bank = new Bank();
        bank.setBankId("BNK001");
        bank.setBankName("Crimson Bank");
        bank.setBankCode(123);
        bank.setBankAddress("123 Bank St, City");
        bank.setBankEmail("contact@crimsonbank.com");
        bank.setBankContactNumber("123-456-7890");
        bank.setBankCountry("CountryName");
        bank.setUser(manager);
        bank.setAccount(new ArrayList<>()); // Initialize with an empty list
        bank.setCustomer(new ArrayList<>()); // Initialize with an empty list

        // Act & Assert
        assertThat(bank.getBankId()).isEqualTo("BNK001");
        assertThat(bank.getBankName()).isEqualTo("Crimson Bank");
        assertThat(bank.getBankCode()).isEqualTo(123);
        assertThat(bank.getBankAddress()).isEqualTo("123 Bank St, City");
        assertThat(bank.getBankEmail()).isEqualTo("contact@crimsonbank.com");
        assertThat(bank.getBankContactNumber()).isEqualTo("123-456-7890");
        assertThat(bank.getBankCountry()).isEqualTo("CountryName");
        assertThat(bank.getUser()).isEqualTo(manager);
    }
}
