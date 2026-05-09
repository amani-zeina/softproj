package org.example.repository;

import org.example.domain.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {

    @Test
    void addAndFindUserShouldWork() {

        User user = new User("testuser","1234","testuser@example.com");

        UserRepository.add(user);

        User found = UserRepository.find("testuser","1234");

        assertNotNull(found);
        assertEquals("testuser", found.getUsername());
    }

    @Test
    void existsShouldReturnTrueWhenUserExists() {

        User user = new User("existuser","111","testuser@example.com");

        UserRepository.add(user);

        assertTrue(UserRepository.exists("existuser"));
    }

    @Test
    void existsShouldReturnFalseWhenUserNotExists() {

        assertFalse(UserRepository.exists("unknown"));
    }

    @Test
    void getAllShouldReturnUsers() {

        List<User> users = UserRepository.getAll();

        assertNotNull(users);
    }
}