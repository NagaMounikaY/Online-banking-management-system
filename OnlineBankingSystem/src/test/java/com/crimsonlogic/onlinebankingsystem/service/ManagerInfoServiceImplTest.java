package com.crimsonlogic.onlinebankingsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.crimsonlogic.onlinebankingsystem.dto.LoginResponseDTO;
import com.crimsonlogic.onlinebankingsystem.dto.ManagerInfoDTO;
import com.crimsonlogic.onlinebankingsystem.entity.BankUser;
import com.crimsonlogic.onlinebankingsystem.entity.ManagerInfo;
import com.crimsonlogic.onlinebankingsystem.entity.UserRole;
import com.crimsonlogic.onlinebankingsystem.exception.ResourceNotFoundException;
import com.crimsonlogic.onlinebankingsystem.repository.BankUserRepository;
import com.crimsonlogic.onlinebankingsystem.repository.ManagerInfoRepository;
import com.crimsonlogic.onlinebankingsystem.repository.UserRoleRepository;
import com.crimsonlogic.onlinebankingsystem.service.impl.ManagerInfoServiceImpl;

@ExtendWith(MockitoExtension.class)
class ManagerInfoServiceImplTest {

    @InjectMocks
    private ManagerInfoServiceImpl managerInfoService;

    @Mock
    private ManagerInfoRepository userInfoRepository;

    @Mock
    private BankUserRepository bankUserRepository;

    @Mock
    private UserRoleRepository roleRepository;

    @Mock
    private ModelMapper modelMapper;

    private ManagerInfo managerInfo;
    private BankUser bankUser;

    @BeforeEach
    void setUp() {
        bankUser = new BankUser();
        bankUser.setUserEmail("test@example.com");
        bankUser.setUserPassword("password");

        managerInfo = new ManagerInfo();
        managerInfo.setUserId("1");
        managerInfo.setUserName("Test Manager");
        managerInfo.setBankUser(bankUser);
    }

    @Test
    void testCreateUser() {
        ManagerInfoDTO managerInfoDTO = new ManagerInfoDTO();
        //managerInfoDTO.setBankNames(bankUser);

        when(modelMapper.map(managerInfoDTO.getBankUser(), BankUser.class)).thenReturn(bankUser);
        when(bankUserRepository.save(bankUser)).thenReturn(bankUser);
        when(modelMapper.map(managerInfoDTO, ManagerInfo.class)).thenReturn(managerInfo);
        when(userInfoRepository.save(managerInfo)).thenReturn(managerInfo);
        when(modelMapper.map(managerInfo, ManagerInfoDTO.class)).thenReturn(managerInfoDTO);

        ManagerInfoDTO result = managerInfoService.createUser(managerInfoDTO);

        assertNotNull(result);
        assertEquals("Test Manager", result.getUserName());
        verify(bankUserRepository).save(bankUser);
        verify(userInfoRepository).save(managerInfo);
    }

    @Test
    void testGetUserById_UserFound() {
        when(userInfoRepository.findById("1")).thenReturn(Optional.of(managerInfo));
        when(modelMapper.map(managerInfo, ManagerInfoDTO.class)).thenReturn(new ManagerInfoDTO());

        ManagerInfoDTO result = managerInfoService.getUserById("1");

        assertNotNull(result);
        verify(userInfoRepository).findById("1");
    }

    @Test
    void testGetUserById_UserNotFound() {
        when(userInfoRepository.findById("2")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            managerInfoService.getUserById("2");
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testDeleteUser() {
        managerInfoService.deleteUser("1");
        verify(userInfoRepository).deleteById("1");
    }

    @Test
    void testLogin_Success() throws ResourceNotFoundException {
        BankUser bankUser = new BankUser();
        bankUser.setUserEmail("test@example.com");
        bankUser.setUserPassword("password");
        UserRole userRole = new UserRole();
        userRole.setUserRole("Manager");

        when(bankUserRepository.findByUserEmail("test@example.com")).thenReturn(bankUser);
        when(roleRepository.findByBankUser(bankUser)).thenReturn(userRole);

        LoginResponseDTO response = managerInfoService.login(new ManagerInfoDTO());

        assertNotNull(response);
        assertEquals("Manager", response.getUserRole());
    }

    @Test
    void testLogin_InvalidCredentials() {
        when(bankUserRepository.findByUserEmail("invalid@example.com")).thenReturn(null);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            managerInfoService.login(new ManagerInfoDTO());
        });

        assertEquals("Invalid username or password", exception.getMessage());
    }

    // Additional test cases for other methods can be added here.
}
