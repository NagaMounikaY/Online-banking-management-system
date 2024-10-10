package com.crimsonlogic.onlinebankingsystem.repository;

import com.crimsonlogic.onlinebankingsystem.entity.Bank;
import com.crimsonlogic.onlinebankingsystem.entity.ManagerInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BankRepositoryTest {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private ManagerInfoRepository managerInfoRepository;

    private Bank bank;
    private ManagerInfo manager;

    @BeforeEach
    public void setUp() {
        // Set up a test manager and bank
        manager = new ManagerInfo();
        manager.setUserName("testManager");
        managerInfoRepository.save(manager); // Save manager to the repository first

        bank = new Bank();
        bank.setBankId("BNK001");
        bank.setBankName("Test Bank");
        bank.setBankCode(456);
        bank.setBankAddress("456 Bank St, City");
        bank.setBankEmail("test@bank.com");
        bank.setBankContactNumber("123-456-7891");
        bank.setBankCountry("TestCountry");
        bank.setUser(manager); // Associate bank with the manager

        bankRepository.save(bank); // Save bank to the repository
    }


}
