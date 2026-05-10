package org.example.domain;
/**
 * Represents an administrator in the appointment scheduling system.
 * The administrator can manage appointments and reservations.
 *
 * @author Team
 * @version 1.0
 */
public class Administrator {

    private String username;
    private String password;

    public Administrator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
