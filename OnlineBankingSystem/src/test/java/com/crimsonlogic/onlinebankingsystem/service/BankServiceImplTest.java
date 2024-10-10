package com.crimsonlogic.onlinebankingsystem.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.crimsonlogic.onlinebankingsystem.dto.BankDTO;
import com.crimsonlogic.onlinebankingsystem.entity.Bank;
import com.crimsonlogic.onlinebankingsystem.entity.ManagerInfo;
import com.crimsonlogic.onlinebankingsystem.repository.BankRepository;
import com.crimsonlogic.onlinebankingsystem.repository.ManagerInfoRepository;
import com.crimsonlogic.onlinebankingsystem.service.impl.BankServiceImpl;

public class BankServiceImplTest {

    @InjectMocks
    private BankServiceImpl bankService;

    @Mock
    private BankRepository bankRepository;

    @Mock
    private ManagerInfoRepository managerInfoRepository;

    private Bank bank;
    private ManagerInfo managerInfo;
    private BankDTO bankDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize ManagerInfo
        managerInfo = new ManagerInfo();
        managerInfo.setUserName("john_doe");

        // Initialize Bank
        bank = new Bank();
        bank.setBankId("BNK001");
        bank.setBankName("Test Bank");
        bank.setBankCode(123);
        bank.setBankAddress("123 Bank St.");
        bank.setBankEmail("contact@testbank.com");
        bank.setBankContactNumber("1234567890");
        bank.setBankCountry("Testland");
        bank.setUser(managerInfo);

        // Initialize BankDTO
        bankDTO = new BankDTO();
        bankDTO.setBankName("Test Bank");
        bankDTO.setBankCode(123);
        bankDTO.setBankAddress("123 Bank St.");
        bankDTO.setBankEmail("contact@testbank.com");
        bankDTO.setBankContactNumber("1234567890");
        bankDTO.setBankCountry("Testland");
        bankDTO.setUserName("john_doe");
    }

    @Test
    public void testCreateBank_UserNotFound() {
        when(managerInfoRepository.findByUserName("john_doe")).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bankService.createBank(bankDTO);
        });

        assertThat(exception.getMessage()).isEqualTo("User not found with username: john_doe");
    }

    private Exception assertThrows(Class<IllegalArgumentException> class1, Object object) {
		// TODO Auto-generated method stub
		return null;
	}

    @Test
    public void testIsManagerAssigned() {
        when(bankRepository.existsById("BNK001")).thenReturn(true);

        boolean result = bankService.isManagerAssigned("BNK001");

        assertThat(result).isTrue();
    }

    @Test
    public void testFindBankByUserId() {
        when(managerInfoRepository.findById("1")).thenReturn(Optional.of(managerInfo));
        when(bankRepository.findByUser(managerInfo)).thenReturn(Optional.of(bank));

        Optional<Bank> result = bankService.findBankByUserId("1");

        assertThat(result).isPresent();
        assertThat(result.get().getBankName()).isEqualTo("Test Bank");
    }
}
