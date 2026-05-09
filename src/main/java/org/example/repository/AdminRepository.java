package org.example.repository;

import java.util.ArrayList;
import java.util.List;
import org.example.domain.Administrator;

public class AdminRepository {

    private static final List<Administrator> admins = new ArrayList<>();

    static {
        admins.add(new Administrator("amani", "2005"));
        admins.add(new Administrator("zeina", "0000"));
    }

    /**
     * Finds administrator by username and password.
     *
     * @param username admin username
     * @param password admin password
     * @return Administrator if found, null otherwise
     */
    public static Administrator find(String username, String password) {
        return admins.stream()
                .filter(a -> a.getUsername().equals(username)
                          && a.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}