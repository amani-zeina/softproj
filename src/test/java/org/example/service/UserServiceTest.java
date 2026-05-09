package org.example.service;

import org.example.domain.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @BeforeEach
    void clearRepo() {
        UserRepository.getAll().clear();
    }

    // @Test
    // void registerShouldCreateUser() {
    //     UserService service = new UserService();
    //     boolean result = service.register("user1","123","testuser@example.com");
    //     assertTrue(result);
    //     assertNotNull(UserRepository.findByUsername("user1"));
    // }

    @Test
    void registerShouldFailIfUserExists() {
        UserService service = new UserService();
        service.register("user2","123","testuser@example.com");
        boolean result = service.register("user2","123","testuser@example.com");
        assertFalse(result);
    }

    @Test
    void loginShouldWorkWithCorrectCredentials() {
        UserService service = new UserService();
        service.register("loginuser","123","testuser@example.com");
        boolean logged = service.login("loginuser","123");
        assertTrue(logged);
        assertTrue(service.isLoggedIn());
        assertEquals("loginuser", service.getCurrentUser().getUsername());
    }

    @Test
    void loginShouldFailWithWrongPassword() {
        UserService service = new UserService();
        service.register("loginfail","123","testuser@example.com");
        boolean logged = service.login("loginfail","wrong");
        assertFalse(logged);
        assertFalse(service.isLoggedIn());
    }

    @Test
    void logoutShouldClearUser() {
        UserService service = new UserService();
        service.register("logoutuser","123","testuser@example.com");
        service.login("logoutuser","123");
        service.logout();
        assertFalse(service.isLoggedIn());
        assertNull(service.getCurrentUser());
    }

    @Test
    void isLoggedInShouldReturnFalseInitially() {
        UserService service = new UserService();
        assertFalse(service.isLoggedIn());
    }

    @Test
    void getCurrentUserShouldReturnNullInitially() {
        UserService service = new UserService();
        assertNull(service.getCurrentUser());
    }

    @Test
    void findByUsernameShouldReturnUser() {
        UserService service = new UserService();
        service.register("findme","123","findme@example.com");
        User found = UserRepository.findByUsername("findme");
        assertNotNull(found);
        assertEquals("findme", found.getUsername());
    }
}
