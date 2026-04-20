/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.login_system;

/**
 *
 * @author Student
 */

public class Login {

    public boolean login(String username, String password, Registration reg) {

        if (reg.getUsername() == null) {
            System.out.println("Please register first.");
            return false;
        }

        if (username.equals(reg.getUsername()) &&
            password.equals(reg.getPassword())) {

            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Login failed.");
            return false;
        }
    }
}
