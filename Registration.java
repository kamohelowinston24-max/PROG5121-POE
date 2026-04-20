/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.login_system;

/**
 *
 * @author Student
 */

import java.util.regex.Pattern;

public class Registration {

    private String storedUsername;
    private String storedPassword;
    private String storedPhone;

    // Username: must contain "_" and max 5 chars
    public boolean checkUserName(String username) {
        return Pattern.matches("^(?=.*_).{1,5}$", username);
    }

    // Password: min 8 chars, 1 uppercase, 1 number, 1 special char
    public boolean checkPassword(String password) {
        return Pattern.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$", password);
    }

    // Phone: must start with +27 and be 12 chars long
    public boolean checkPhone(String phone) {
        return Pattern.matches("^\\+27\\d{9}$", phone);
    }

    public boolean register(String username, String password, String phone) {

        if (!checkUserName(username)) {
            System.out.println("Invalid username.");
            return false;
        }

        if (!checkPassword(password)) {
            System.out.println("Invalid password.");
            return false;
        }

        if (!checkPhone(phone)) {
            System.out.println("Invalid phone.");
            return false;
        }

        storedUsername = username;
        storedPassword = password;
        storedPhone = phone;

        System.out.println("Registration successful!");
        return true;
    }

    public String getUsername() {
        return storedUsername;
    }

    public String getPassword() {
        return storedPassword;
    }
}
