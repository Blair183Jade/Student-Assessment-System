package com.assignment3version1.model;

/**
 * @author jiahong lin
 * @date Created in 24/03/2024 01:50
 * @modified By  jiahong lin in 24/03/2024 01:50
 * @description AddDescriptionHere
 */

import java.io.Serializable;

/**
 * Represents a user within the system, encapsulating details such as username, password,
 * first name, last name, phone number, and role. Supports operations for user management
 * and authentication.
 * <p>
 * Implements Serializable to allow User objects to be serialized for session management
 * or distributed computing scenarios.
 *
 * @author jiahong lin
 * @date Created in 24/03/2024 01:50
 * @modified By jiahong lin in 24/03/2024 01:50
 */

public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String role;


    /**
     * Constructs a new User instance without initializing its fields (no-argument constructor).
     */
    public User() {
    }

    /**
     * Constructs a new User with the specified details.
     *
     * @param username  The username for the user.
     * @param password  The password for the user (consider hashing this value in real applications).
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param phone     The phone number of the user.
     * @param role      The role of the user within the system.
     */

    public User(String username, String password, String firstName, String lastName, String phone, String role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.role = role;
    }

    // Getters and setters for each field

    /**
     * Gets the unique identifier of the user.
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
