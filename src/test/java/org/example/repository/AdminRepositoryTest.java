package org.example.repository;

import org.example.domain.Administrator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdminRepositoryTest {

    @Test
    void findShouldReturnAdminWhenCredentialsCorrect() {

        Administrator admin = AdminRepository.find("amani", "2005");

        assertNotNull(admin);
        assertEquals("amani", admin.getUsername());
    }

    @Test
    void findShouldReturnNullWhenCredentialsWrong() {

        Administrator admin = AdminRepository.find("wrong", "wrong");

        assertNull(admin);
    }
}