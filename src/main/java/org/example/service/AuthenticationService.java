package org.example.service;

import org.example.domain.Administrator;
import org.example.repository.AdminRepository;

/**
 * Service responsible for administrator authentication.
 * Handles login, logout, and authentication status.
 *
 * @author Team
 * @version 1.0
 */
public class AuthenticationService {

    private Administrator currentAdmin;

    /**
     * Attempts to login administrator.
     *
     * @param username admin username
     * @param password admin password
     * @return true if credentials are valid, false otherwise
     */
    public boolean loginAdmin(String username, String password) {
        currentAdmin = AdminRepository.find(username, password);
        return currentAdmin != null;
    }

    /**
     * Logs out the current admin.
     */
    public void logoutAdmin() {
        currentAdmin = null;
    }

    /**
     * Checks if an admin is currently logged in.
     *
     * @return true if admin logged in
     */
    public boolean isAdminLoggedIn() {
        return currentAdmin != null;
    }

    /**
     * Returns the currently logged-in admin.
     * (Added to support testing)
     */
    public Administrator getCurrentAdmin() {
        return currentAdmin;
    }
}