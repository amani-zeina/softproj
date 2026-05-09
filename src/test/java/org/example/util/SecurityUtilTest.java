package org.example.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SecurityUtilTest {

    @Test
    void hashPasswordShouldReturnValue() {

        String hash = SecurityUtil.hashPassword("123");

        assertNotNull(hash);
        assertFalse(hash.isEmpty());
    }

    @Test
    void samePasswordShouldReturnSameHash() {

        String hash1 = SecurityUtil.hashPassword("password");
        String hash2 = SecurityUtil.hashPassword("password");

        assertEquals(hash1, hash2);
    }

    @Test
    void differentPasswordsShouldReturnDifferentHashes() {

        String hash1 = SecurityUtil.hashPassword("abc");
        String hash2 = SecurityUtil.hashPassword("xyz");

        assertNotEquals(hash1, hash2);
    }
}