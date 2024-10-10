package com.crimsonlogic.onlinebankingsystem.service.impl;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crimsonlogic.onlinebankingsystem.dto.TransactionDTO;
import com.crimsonlogic.onlinebankingsystem.entity.Account;
import com.crimsonlogic.onlinebankingsystem.entity.Transactions;
import com.crimsonlogic.onlinebankingsystem.repository.AccountRepository;
import com.crimsonlogic.onlinebankingsystem.repository.TransactionsRepository;
import com.crimsonlogic.onlinebankingsystem.service.TransactionsService;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

@Service
public class TransactionsServiceImpl implements TransactionsService {
    
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionsRepository transactionRepository;

    @Override
    public String processTransaction(String accountId, TransactionDTO request) {
        // Fetch the account by ID
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        float newBalance;

        // Handle deposit transaction
        if ("deposit".equalsIgnoreCase(request.getTransactionType())) {
            newBalance = account.getBalance() + request.getBalance();
        } 
        // Handle withdrawal transaction
        else if ("withdraw".equalsIgnoreCase(request.getTransactionType())) {
            if (account.getBalance() < request.getBalance()) {
                throw new RuntimeException("Insufficient balance");
            }
            newBalance = account.getBalance() - request.getBalance();
        } 
        // Handle invalid transaction type
        else {
            throw new RuntimeException("Invalid transaction type");
        }

        // Update the account balance and save it
        account.setBalance(newBalance);
        accountRepository.save(account);

        // Log the transaction
        logTransaction(account, newBalance, request.getTransactionType(), request.getBalance(), null, null);

        return String.format("Transaction successful. New balance: %.2f", newBalance);
    }

    public String transferFunds(String fromAccountId, String toAccountId, float amount, int bankCode) {
        // Fetch source and destination accounts
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        // Validate bank code
        if (toAccount.getBank().getBankCode() != bankCode) {
            throw new RuntimeException("Bank code does not match the destination account");
        }

        // Validate sufficient balance
        if (fromAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance for transfer");
        }

        // Update balances for both accounts
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        // Save updated accounts
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        // Log both transactions
        logTransaction(fromAccount, fromAccount.getBalance(), "Debit", amount, fromAccount, toAccount);
        logTransaction(toAccount, toAccount.getBalance(), "Credit", amount, fromAccount, toAccount);

        return String.format("Transfer successful. Amount: %.2f", amount);
    }

    private void logTransaction(Account account, float balance, String transactionType, float amount, Account fromAccount, Account toAccount) {
        // Create a new transaction entry
        Transactions transaction = new Transactions();
        transaction.setAccount(account);
        transaction.setBalance(balance);
        transaction.setTransactionType(transactionType);
        transaction.setDiscription(transactionType + " of amount " + amount);
        transaction.setTransactionDate(LocalDate.now());

        // Set from and to account details if applicable
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        
        if (fromAccount != null) {
            transaction.setFromBank(fromAccount.getBank().getBankName());
        }
        if (toAccount != null) {
            transaction.setToBank(toAccount.getBank().getBankName());
        }
        
        // Save the transaction
        transactionRepository.save(transaction);
    }

    public List<TransactionDTO> getTransactionsForAccount(String accountId) {
        // Fetch the account and retrieve its transactions
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        List<Transactions> transactions = transactionRepository.findByAccount(account);

        // Convert transactions to DTOs
        return transactions.stream()
            .map(transaction -> {
                TransactionDTO dto = new TransactionDTO();
                dto.setTransactionId(transaction.getTransactionId());
                dto.setBalance(transaction.getBalance());
                dto.setTransactionType(transaction.getTransactionType());
                dto.setDescription(transaction.getDiscription());
                dto.setTransactionDate(transaction.getTransactionDate());
                dto.setFromAccountId(transaction.getFromAccount() != null ? transaction.getFromAccount().getAccountId() : null);
                dto.setToAccountId(transaction.getToAccount() != null ? transaction.getToAccount().getAccountId() : null);
                dto.setFromBank(transaction.getFromBank());
                dto.setToBank(transaction.getToBank());
                return dto;
            })
            .collect(Collectors.toList());
    }

    // Uncomment this method if date range transaction fetching is needed
    // @Override
    // public List<Transactions> getTransactionsForDateRange(String accountId, LocalDate fromDate, LocalDate toDate) {
    //     return transactionRepository.findByAccountIdAndTransactionDateBetween(accountId, fromDate, toDate);
    // }

    public byte[] generateTransactionStatementPdf(List<Transactions> transactions, String bankName) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            
            // Set the page size to A4
            Document document = new Document(pdfDocument, PageSize.A4);
            
            // Add Bank Name as a Title
            document.add(new Paragraph(bankName)
                    .setBold()
                    .setFontSize(24)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(new DeviceRgb(0, 0, 255))); // Blue color for bank name

            document.add(new Paragraph("Transaction Statement")
                    .setBold()
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(new DeviceRgb(100, 100, 100))); // Dark gray for statement title

            document.add(new Paragraph("\n"));

            // Create a table for transaction data
            Table table = new Table(new float[]{1, 3, 3, 2, 2, 2}); // Adjust column widths
            table.setWidthPercent(100); // Set table width to 100%
            table.addHeaderCell(new Cell().add("Transaction ID").setBold().setBackgroundColor(new DeviceRgb(0, 102, 204)).setFontColor(new DeviceRgb(255, 255, 255))); // White
            table.addHeaderCell(new Cell().add("Account ID").setBold().setBackgroundColor(new DeviceRgb(0, 102, 204)).setFontColor(new DeviceRgb(255, 255, 255))); // White
            table.addHeaderCell(new Cell().add("Transaction Type").setBold().setBackgroundColor(new DeviceRgb(0, 102, 204)).setFontColor(new DeviceRgb(255, 255, 255))); // White
            table.addHeaderCell(new Cell().add("Description").setBold().setBackgroundColor(new DeviceRgb(0, 102, 204)).setFontColor(new DeviceRgb(255, 255, 255))); // White
            table.addHeaderCell(new Cell().add("Balance").setBold().setBackgroundColor(new DeviceRgb(0, 102, 204)).setFontColor(new DeviceRgb(255, 255, 255))); // White
            table.addHeaderCell(new Cell().add("Date").setBold().setBackgroundColor(new DeviceRgb(0, 102, 204)).setFontColor(new DeviceRgb(255, 255, 255))); // White

            // Populate the table with transaction data
            for (Transactions transaction : transactions) {
                table.addCell(new Cell().add(String.valueOf(transaction.getTransactionId())));
                table.addCell(new Cell().add(transaction.getAccount().getAccountId()));
                table.addCell(new Cell().add(transaction.getTransactionType()));
                table.addCell(new Cell().add(transaction.getDiscription()));
                table.addCell(new Cell().add(String.format("%.2f", transaction.getBalance())));
                table.addCell(new Cell().add(transaction.getTransactionDate().toString()));
            }

            // Add the table to the document
            document.add(table);
            document.close();
            return outputStream.toByteArray(); // Return the PDF as a byte array
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF: " + e.getMessage(), e);
        }
    }
}
