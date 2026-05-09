package org.example.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationServiceTest {

    @Test
    void loginShouldSucceedWithValidAdminCredentials() {
        AuthenticationService authenticationService = new AuthenticationService();

        boolean result = authenticationService.loginAdmin("zeina", "0000");

        assertTrue(result);
    }

    @Test
    void loginShouldFailWithInvalidCredentials() {
        AuthenticationService authenticationService = new AuthenticationService();

        boolean result = authenticationService.loginAdmin("wrong", "1234");

        assertFalse(result);
    }
}
