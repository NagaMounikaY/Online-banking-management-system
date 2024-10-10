package com.crimsonlogic.onlinebankingsystem.service.impl;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crimsonlogic.onlinebankingsystem.dto.LoginResponseDTO;
import com.crimsonlogic.onlinebankingsystem.dto.ManagerInfoDTO;
import com.crimsonlogic.onlinebankingsystem.entity.Bank;
import com.crimsonlogic.onlinebankingsystem.entity.BankUser;
import com.crimsonlogic.onlinebankingsystem.entity.ManagerInfo;
import com.crimsonlogic.onlinebankingsystem.entity.UserRole;
import com.crimsonlogic.onlinebankingsystem.exception.ResourceNotFoundException;
import com.crimsonlogic.onlinebankingsystem.repository.BankUserRepository;
import com.crimsonlogic.onlinebankingsystem.repository.ManagerInfoRepository;
import com.crimsonlogic.onlinebankingsystem.repository.UserRoleRepository;
import com.crimsonlogic.onlinebankingsystem.service.ManagerInfoService;

@Service
public class ManagerInfoServiceImpl implements ManagerInfoService {

    @Autowired
    private ManagerInfoRepository userInfoRepository;

    @Autowired
    private BankUserRepository bankUserRepository;

    @Autowired
    private UserRoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    private String encodePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    private String decodePassword(String encodedPassword) {
        return new String(Base64.getDecoder().decode(encodedPassword));
    }

    @Override
    public ManagerInfoDTO createUser(ManagerInfoDTO managerInfoDTO) {
        // Map DTO to BankUser entity and encode the password
        BankUser bankUser = modelMapper.map(managerInfoDTO.getBankUser(), BankUser.class);
        bankUser.setUserPassword(encodePassword(bankUser.getUserPassword())); // Encode password
        bankUser = bankUserRepository.save(bankUser);

        // Map DTO to ManagerInfo entity
        ManagerInfo userInfo = modelMapper.map(managerInfoDTO, ManagerInfo.class);
        userInfo.setBankUser(bankUser); // Set the saved BankUser to the ManagerInfo

        // Save the ManagerInfo entity and return the mapped DTO
        userInfo = userInfoRepository.save(userInfo);
        return modelMapper.map(userInfo, ManagerInfoDTO.class);
    }

    @Override
    public ManagerInfoDTO getUserById(String userId) {
        // Fetch ManagerInfo entity by userId
        ManagerInfo userInfo = userInfoRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Map and return the ManagerInfo as ManagerInfoDTO
        return modelMapper.map(userInfo, ManagerInfoDTO.class);
    }

    @Override
    public List<ManagerInfoDTO> getAllUsers() {
        // Retrieve all users and map them to ManagerInfoDTO
        return userInfoRepository.findAll().stream()
                .map(user -> modelMapper.map(user, ManagerInfoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ManagerInfoDTO updateUser(String userId, ManagerInfoDTO userInfoDTO) {
        // Fetch existing user by userId
        ManagerInfo existingUser = userInfoRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Map updates from DTO to existing user
        modelMapper.map(userInfoDTO, existingUser);
        // Save updated user and return as DTO
        userInfoRepository.save(existingUser);
        return modelMapper.map(existingUser, ManagerInfoDTO.class);
    }

    @Override
    public void deleteUser(String userId) {
        // Delete user by userId
        userInfoRepository.deleteById(userId);
    }

    private static final String MANAGER_ROLE_ID = "RN10002"; // Role ID for bank managers

    @Override
    public List<ManagerInfoDTO> getBankManagers() {
        // Fetch users with the manager role and convert to DTOs
        List<ManagerInfo> managers = userInfoRepository.findUsersByRoleId(MANAGER_ROLE_ID);
        return managers.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ManagerInfoDTO convertToDTO(ManagerInfo userInfo) {
        // Convert ManagerInfo entity to ManagerInfoDTO
        ManagerInfoDTO dto = new ManagerInfoDTO();
        dto.setUserId(userInfo.getUserId());
        dto.setUserName(userInfo.getUserName());
        dto.setUserMobileNumber(userInfo.getUserMobileNumber());
        dto.setUserGender(userInfo.getUserGender());
        dto.setUserAge(userInfo.getUserAge());
        dto.setUserStreet(userInfo.getUserStreet());
        dto.setUserCity(userInfo.getUserCity());
        dto.setUserPincode(userInfo.getUserPincode());
        dto.setUserEmail(userInfo.getBankUser() != null ? userInfo.getBankUser().getUserEmail() : "N/A");

        // Set the bank names if available
        List<Bank> banks = userInfo.getBank();
        if (banks != null && !banks.isEmpty()) {
            dto.setBankNames(banks.get(0).getBankName()); // Get the name of the first bank
        } else {
            dto.setBankNames("N/A"); // Default value if no banks are associated
        }

        return dto;
    }

    private static final String CUSTOMER_ROLE_ID = "RN10003"; // Role ID for bank customers

    @Override
    public List<ManagerInfoDTO> getBankCustomers() {
        // Fetch users with the customer role and convert to DTOs
        List<ManagerInfo> managers = userInfoRepository.findUsersByRoleId(CUSTOMER_ROLE_ID);
        return managers.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public LoginResponseDTO login(ManagerInfoDTO userDTO) throws ResourceNotFoundException {
        // Retrieve BankUser by email
        BankUser user = bankUserRepository.findByUserEmail(userDTO.getUserEmail());
        // Validate user credentials
        if (user != null && decodePassword(user.getUserPassword()).equals(userDTO.getUserPassword())) {
            UserRole role = roleRepository.findByBankUser(user);
            String userId = user.getUser().getUserId();

            // Create and populate LoginResponseDTO
            LoginResponseDTO response = new LoginResponseDTO();
            response.setManagerInfo(modelMapper.map(user, ManagerInfoDTO.class));
            response.setUserRole(role.getUserRole());
            response.setUserIds(userId);

            return response;
        } else {
            throw new ResourceNotFoundException("Invalid username or password");
        }
    }
}
