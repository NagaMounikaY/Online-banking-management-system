package com.crimsonlogic.onlinebankingsystem.controller;

import com.crimsonlogic.onlinebankingsystem.dto.AccountDTO;
import com.crimsonlogic.onlinebankingsystem.entity.Account;
import com.crimsonlogic.onlinebankingsystem.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    void testAddAccount() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountId("123456");
        accountDTO.setBalance(100);

        when(accountService.createAccount(any(AccountDTO.class))).thenReturn(accountDTO);

        mockMvc.perform(post("/account/addaccount")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("123456"));
    }

    @Test
    void testGetAccountsByCustomerId() throws Exception {
        List<AccountDTO> accountList = Collections.singletonList(new AccountDTO());
        
        when(accountService.getAccountsByCustomerId("customerId")).thenReturn(accountList);

        mockMvc.perform(get("/account/viewaccount/{customerId}", "customerId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetAllAccounts() throws Exception {
        List<AccountDTO> accountList = Collections.singletonList(new AccountDTO());

        when(accountService.getAllAccounts()).thenReturn(accountList);

        mockMvc.perform(get("/account/accountList"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetAccountsByBankName() throws Exception {
        List<AccountDTO> accountList = Collections.singletonList(new AccountDTO());

        when(accountService.getAccountsByBankName("BankName")).thenReturn(accountList);

        mockMvc.perform(get("/account/byBankName").param("bankName", "BankName"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testUpdateAccountStatus() throws Exception {
        Map<String, String> statusUpdate = new HashMap<>();
        statusUpdate.put("status", "ACTIVE");

        mockMvc.perform(patch("/account/updateStatus/{accountId}", "accountId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(statusUpdate)))
                .andExpect(status().isOk())
                .andExpect(content().string("Account status updated to ACTIVE"));

        verify(accountService, times(1)).updateAccountStatus("accountId", "ACTIVE");
    }

    @Test
    void testGetAccountById() throws Exception {
        Account account = new Account();
        account.setAccountId("123456");

        when(accountService.findById("accountId")).thenReturn(Optional.of(account));

        mockMvc.perform(get("/account/{accountId}", "accountId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("123456"));
    }

    @Test
    void testGetAccountById_NotFound() throws Exception {
        when(accountService.findById("accountId")).thenReturn(Optional.empty());

        mockMvc.perform(get("/account/{accountId}", "accountId"))
                .andExpect(status().isNotFound());
    }
}
