package com.crimsonlogic.onlinebankingsystem.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.crimsonlogic.onlinebankingsystem.dto.BankDTO;
import com.crimsonlogic.onlinebankingsystem.entity.Bank;
import com.crimsonlogic.onlinebankingsystem.service.BankService;
import com.fasterxml.jackson.databind.ObjectMapper;

class BankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private BankController bankController;

    @Mock
    private BankService bankService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(bankController).build();
    }

    @Test
    void testAddBank() throws Exception {
        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankName("Sample Bank");

        when(bankService.createBank(any(BankDTO.class))).thenReturn(bankDTO);

        mockMvc.perform(post("/banks/bankinsert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bankDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bankName").value("Sample Bank"));
    }

    @Test
    void testGetAllBanks() throws Exception {
        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankName("Sample Bank");
        when(bankService.getAllBanks()).thenReturn(Collections.singletonList(bankDTO));

        mockMvc.perform(get("/banks/getallbanks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bankName").value("Sample Bank"));
    }

    @Test
    void testGetBankByUserId() throws Exception {
        Bank bank = new Bank();
        bank.setBankName("Sample Bank");
        when(bankService.findBankByUserId("managerId")).thenReturn(Optional.of(bank));
        when(bankService.convertToDTO(bank)).thenReturn(new BankDTO("Sample Bank", null, 0, null, null, null, null, null));

        mockMvc.perform(get("/banks/manager/{userId}", "managerId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bankName").value("Sample Bank"));
    }

    @Test
    void testGetBankByUserId_NotFound() throws Exception {
        when(bankService.findBankByUserId("invalidId")).thenReturn(Optional.empty());

        mockMvc.perform(get("/banks/manager/{userId}", "invalidId"))
                .andExpect(status().isNotFound());
    }
}
