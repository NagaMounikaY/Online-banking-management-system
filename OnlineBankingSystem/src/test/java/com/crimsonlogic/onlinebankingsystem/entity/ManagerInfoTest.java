package com.crimsonlogic.onlinebankingsystem.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ManagerInfoTest {

    private ManagerInfo managerInfo;

    @BeforeEach
    void setUp() {
        managerInfo = new ManagerInfo();
    }

    @Test
    void testGettersAndSetters() {
        managerInfo.setUserId("MGR001");
        managerInfo.setUserName("John Doe");
        managerInfo.setUserMobileNumber("1234567890");
        managerInfo.setUserGender("Male");
        managerInfo.setUserAge(35);
        managerInfo.setUserStreet("123 Elm Street");
        managerInfo.setUserCity("Metropolis");
        managerInfo.setUserPincode(12345);

        assertEquals("MGR001", managerInfo.getUserId());
        assertEquals("John Doe", managerInfo.getUserName());
        assertEquals("1234567890", managerInfo.getUserMobileNumber());
        assertEquals("Male", managerInfo.getUserGender());
        assertEquals(35, managerInfo.getUserAge());
        assertEquals("123 Elm Street", managerInfo.getUserStreet());
        assertEquals("Metropolis", managerInfo.getUserCity());
        assertEquals(12345, managerInfo.getUserPincode());
    }

    @Test
    void testRelationships() {
        Bank bank = new Bank(); // Assuming you have a Bank class
        managerInfo.setBank(Collections.singletonList(bank));

        assertEquals(1, managerInfo.getBank().size());
        assertEquals(bank, managerInfo.getBank().get(0));
    }

    @Test
    void testBankUserRelationship() {
        BankUser bankUser = new BankUser(); // Assuming you have a BankUser class
        managerInfo.setBankUser(bankUser);

        assertEquals(bankUser, managerInfo.getBankUser());
    }
}
