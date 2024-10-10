package com.crimsonlogic.onlinebankingsystem.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crimsonlogic.onlinebankingsystem.dto.BankDTO;
import com.crimsonlogic.onlinebankingsystem.entity.Bank;
import com.crimsonlogic.onlinebankingsystem.entity.ManagerInfo;
import com.crimsonlogic.onlinebankingsystem.repository.BankRepository;
import com.crimsonlogic.onlinebankingsystem.repository.ManagerInfoRepository;
import com.crimsonlogic.onlinebankingsystem.service.BankService;

@Service
public class BankServiceImpl implements BankService {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private ManagerInfoRepository ManagerInfoRepository;

    @Autowired
    private ModelMapper modelMapper;

    // create the bank 
    public BankDTO createBank(BankDTO bankDTO) {
        // Fetch UserInfo entity based on userId from DTO
        Optional<ManagerInfo> user = ManagerInfoRepository.findByUserName(bankDTO.getUserName());

        // If user is not found, handle it appropriately
        if (!user.isPresent()) {
            throw new IllegalArgumentException("User not found with username: " + bankDTO.getUserName());
        }

        // Map BankDTO to Bank entity
        Bank bank = modelMapper.map(bankDTO, Bank.class);
        bank.setUser(user.get()); // Set the user to the bank entity

        // Save the bank entity
        bank = bankRepository.save(bank);

        // Return the mapped BankDTO
        return modelMapper.map(bank, BankDTO.class);
    }


    //get all banks
    @Override
    public List<BankDTO> getAllBanks() {
        return bankRepository.findAll().stream()
            .map(bank -> modelMapper.map(bank, BankDTO.class))
            .collect(Collectors.toList());
    }
    
    //check manager assigned or not 
    public boolean isManagerAssigned(String managerId) {
        return bankRepository.existsById(managerId);
    }
    
    // find by user Id
    public Optional<Bank> findBankByUserId(String userId) {
        return ManagerInfoRepository.findById(userId)
                .flatMap(user -> bankRepository.findByUser(user));
    }

    //convert into DTO class
    public BankDTO convertToDTO(Bank bank) {
        BankDTO dto = new BankDTO();
        dto.setBankId(bank.getBankId());  // Assuming the ID is Long, convert it to String
        dto.setBankName(bank.getBankName());
        dto.setBankCode(bank.getBankCode());
        dto.setBankAddress(bank.getBankAddress());
        dto.setBankEmail(bank.getBankEmail());
        dto.setBankContactNumber(bank.getBankContactNumber());
        dto.setBankCountry(bank.getBankCountry());
        dto.setUserName(bank.getUser().getUserName()); // Assuming you have a getUserName method in User
        return dto;
    }
    
}
