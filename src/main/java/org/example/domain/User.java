package org.example.domain;
/**
 * Represents a user in the appointment scheduling system.
 * A user can register, log in, and book appointments.
 *
 * @author Team
 * @version 1.0
 */
public class User {

    private String username;
    private String password;
    private String email;

    public User(String username, String password,String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public String getEmail() {
        return email;
    }
}
