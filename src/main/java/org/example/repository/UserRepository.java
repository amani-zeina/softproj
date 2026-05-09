package org.example.repository;

import java.util.ArrayList;
import java.util.List;
import org.example.domain.User;

public class UserRepository {

    private static final List<User> users = new ArrayList<>();

    public static void add(User user) {
        users.add(user);
    }

    public static User find(String username, String password) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username)
                          && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public static boolean exists(String username) {
        return users.stream()
                .anyMatch(u -> u.getUsername().equals(username));
    }

    public static List<User> getAll() {
        return users;
    }
    public static User findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

}
