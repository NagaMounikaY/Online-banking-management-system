package com.crimsonlogic.onlinebankingsystem.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.crimsonlogic.onlinebankingsystem.entity.BankUser;
import com.crimsonlogic.onlinebankingsystem.entity.ManagerInfo;
import com.crimsonlogic.onlinebankingsystem.entity.UserRole;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
class ManagerInfoRepositoryTest {

    @Autowired
    private ManagerInfoRepository managerInfoRepository;

    private ManagerInfo managerInfo;

    @BeforeEach
    void setUp() {
        BankUser bankUser = new BankUser();
        bankUser.setUserEmail("manager@example.com");
        bankUser.setUserPassword("password");

        managerInfo = new ManagerInfo();
        managerInfo.setUserName("Test Manager");
        managerInfo.setBankUser(bankUser);
        managerInfo.getBankUser().setRole(new UserRole("RN10002", null, null)); // Assuming UserRole has a constructor like this

        managerInfoRepository.save(managerInfo);
    }

    @Test
    void testFindUsersByRoleId() {
        List<ManagerInfo> managers = managerInfoRepository.findUsersByRoleId("RN10002");

        assertThat(managers).isNotEmpty();
        assertThat(managers.get(0).getUserName()).isEqualTo("Test Manager");
    }

    @Test
    void testFindByUserName() {
        Optional<ManagerInfo> foundManager = managerInfoRepository.findByUserName("Test Manager");

        assertThat(foundManager).isPresent();
        assertThat(foundManager.get().getUserName()).isEqualTo("Test Manager");
    }

    @Test
    void testFindByUserName_NotFound() {
        Optional<ManagerInfo> foundManager = managerInfoRepository.findByUserName("Nonexistent User");

        assertThat(foundManager).isNotPresent();
    }
}
