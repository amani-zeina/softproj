package org.example.service;

import java.util.List;
import org.example.domain.User;
import org.example.repository.UserRepository;
import org.example.util.*;
/**
 * Handles user registration, login, logout, and current user management.
 * It also loads and stores user data through the repository and file utility.
 *
 * @author Team
 * @version 1.0
 */
public class UserService {

    private User currentUser;

    public UserService() {
        List<User> users = FileUtil.loadUsers();
        users.forEach(UserRepository::add);
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    /**
     * Registers a new user if the username does not already exist.
     *
     * @param username the entered username
     * @param password the entered password
     * @return true if registration succeeds, false otherwise
     */
    public boolean register(String username, String password,String email) {
        if (UserRepository.exists(username))
            return false;

        String hashed = SecurityUtil.hashPassword(password);
        User user = new User(username, hashed,email);
        UserRepository.add(user);
        FileUtil.saveUsers(UserRepository.getAll());
        return true;
    }
    /**
     * Logs in a user using the provided username and password.
     *
     * @param username the entered username
     * @param password the entered password
     * @return true if login succeeds, false otherwise
     */
    public boolean login(String username, String password) {
    	String hashed = SecurityUtil.hashPassword(password);
    	currentUser = UserRepository.find(username, hashed);
        return currentUser != null;
    }
    /**
     * Logs out the currently logged-in user.
     */
    public void logout() {
        currentUser = null;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
