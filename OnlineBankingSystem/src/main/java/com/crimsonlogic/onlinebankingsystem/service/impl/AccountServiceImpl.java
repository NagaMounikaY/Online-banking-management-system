package com.crimsonlogic.onlinebankingsystem.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crimsonlogic.onlinebankingsystem.dto.AccountDTO;
import com.crimsonlogic.onlinebankingsystem.entity.Account;
import com.crimsonlogic.onlinebankingsystem.entity.Bank;
import com.crimsonlogic.onlinebankingsystem.entity.CustomerInfo;
import com.crimsonlogic.onlinebankingsystem.repository.AccountRepository;
import com.crimsonlogic.onlinebankingsystem.repository.BankRepository;
import com.crimsonlogic.onlinebankingsystem.repository.CustomerInfoRepository;
import com.crimsonlogic.onlinebankingsystem.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private BankRepository bankRepository;
    

    @Autowired
    private ModelMapper modelMapper; // Autowire ModelMapper for DTO mapping

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        // Map DTO to Account entity
        Account account = modelMapper.map(accountDTO, Account.class);
        
        // Ensure bank name is provided
        if (accountDTO.getBankName() == null) {
            throw new RuntimeException("Bank Name must be provided");
        }
        
        // Retrieve Customer and Bank entities based on IDs from DTO
        CustomerInfo customer = customerInfoRepository.findById(accountDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Bank bank = bankRepository.findByBankName(accountDTO.getBankName());

        // Set Customer and Bank in the Account entity
        account.setCustomer(customer);
        account.setBank(bank);
        account.setStatus(accountDTO.getStatus());
        
        // Save the Account entity and map back to DTO for return
        account = accountRepository.save(account);
        return modelMapper.map(account, AccountDTO.class);
    }
    
    @Override
    public List<AccountDTO> getAccountsByCustomerId(String customerId) {
        // Retrieve the CustomerInfo entity
        CustomerInfo customer = customerInfoRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));
        
        // Get accounts associated with the customer
        List<Account> accounts = accountRepository.findByCustomer(customer);
        
        // Map Account entities to AccountDTOs
        return accounts.stream()
                       .map(account -> modelMapper.map(account, AccountDTO.class))
                       .collect(Collectors.toList());
    }
    
    @Override
    public List<AccountDTO> getAllAccounts() {
        // Retrieve all accounts from the repository
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(account -> {
                    AccountDTO dto = modelMapper.map(account, AccountDTO.class);
                    // Populate additional customer and bank details
                    dto.setCustomerName(account.getCustomer().getCustomerName());
                    dto.setUserEmail(account.getCustomer().getBankUser().getUserEmail());
                    dto.setCustomerMobileNumber(account.getCustomer().getCustomerMobileNumber());
                    dto.setBankName(account.getBank().getBankName());
                    dto.setBankCode(account.getBank().getBankCode());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public List<AccountDTO> getAccountsByBankName(String bankName) {
        // Fetch the Bank entity using the bank name
        Bank bank = bankRepository.findByBankName(bankName);
        if (bank == null) {
            throw new RuntimeException("No bank found with name: " + bankName);
        }

        // Retrieve accounts associated with the bank
        List<Account> accounts = accountRepository.findByBank(bank);

        // Map Account entities to AccountDTOs
        return accounts.stream()
                       .map(account -> {
                           AccountDTO dto = modelMapper.map(account, AccountDTO.class);
                           // Populate additional customer and bank details
                           dto.setCustomerName(account.getCustomer().getCustomerName());
                           dto.setUserEmail(account.getCustomer().getBankUser().getUserEmail());
                           dto.setCustomerMobileNumber(account.getCustomer().getCustomerMobileNumber());
                           dto.setBankName(account.getBank().getBankName());
                           dto.setBankCode(account.getBank().getBankCode());
                           return dto;
                       })
                       .collect(Collectors.toList());
    }

    @Override
    public Optional<Account> findById(String accountId) {
        // Find account by ID
        return accountRepository.findById(accountId);
    }
    
    @Override
    public void updateAccountStatus(String accountId, String newStatus) {
        // Find account by ID and update its status
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        
        account.setStatus(newStatus);
        accountRepository.save(account);
    }
}
