package com.crimsonlogic.onlinebankingsystem.controller;

import com.crimsonlogic.onlinebankingsystem.dto.LoginResponseDTO;
import com.crimsonlogic.onlinebankingsystem.dto.ManagerInfoDTO;
import com.crimsonlogic.onlinebankingsystem.service.ManagerInfoService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ManagerInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ManagerInfoController managerInfoController;

    @Mock
    private ManagerInfoService managerInfoService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(managerInfoController).build();
    }

    @Test
    void testCreateUser() throws Exception {
        ManagerInfoDTO managerInfoDTO = new ManagerInfoDTO();
        managerInfoDTO.setUserName("Test Manager");

        when(managerInfoService.createUser(any(ManagerInfoDTO.class))).thenReturn(managerInfoDTO);

        mockMvc.perform(post("/users/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(managerInfoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value("Test Manager"));
    }

    @Test
    void testGetUserById() throws Exception {
        ManagerInfoDTO managerInfoDTO = new ManagerInfoDTO();
        managerInfoDTO.setUserName("Test Manager");

        when(managerInfoService.getUserById("1")).thenReturn(managerInfoDTO);

        mockMvc.perform(get("/users/get/{userId}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Test Manager"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        when(managerInfoService.getAllUsers()).thenReturn(Collections.singletonList(new ManagerInfoDTO()));

        mockMvc.perform(get("/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testUpdateUser() throws Exception {
        ManagerInfoDTO managerInfoDTO = new ManagerInfoDTO();
        managerInfoDTO.setUserName("Updated Manager");

        when(managerInfoService.updateUser(any(String.class), any(ManagerInfoDTO.class))).thenReturn(managerInfoDTO);

        mockMvc.perform(put("/users/update/{userId}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(managerInfoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Updated Manager"));
    }

    @Test
    void testLogin() throws Exception {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setUserRole("Manager");

        when(managerInfoService.login(any(ManagerInfoDTO.class))).thenReturn(loginResponseDTO);

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ManagerInfoDTO())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userRole").value("Manager"));
    }

    @Test
    void testGetCustomers() throws Exception {
        when(managerInfoService.getBankCustomers()).thenReturn(Collections.singletonList(new ManagerInfoDTO()));

        mockMvc.perform(get("/users/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
