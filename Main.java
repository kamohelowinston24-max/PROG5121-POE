/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.login_system;

/**
 *
 * @author Student
 */

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            Registration reg = new Registration();
            Login login = new Login();
            
            int choice;
            
            do {
                System.out.println("\n=== MENU ===");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose option: ");
                
                choice = sc.nextInt();
                sc.nextLine(); // fix buffer
                
                switch (choice) {
                    
                    case 1 -> {
                        System.out.print("Enter username: ");
                        String username = sc.nextLine();
                        
                        System.out.print("Enter password: ");
                        String password = sc.nextLine();
                        
                        System.out.print("Enter phone (+27...): ");
                        String phone = sc.nextLine();
                        
                        reg.register(username, password, phone);
                    }
                        
                    case 2 -> {
                        System.out.print("Enter username: ");
                        String user = sc.nextLine();
                        
                        System.out.print("Enter password: ");
                        String pass = sc.nextLine();
                        
                        login.login(user, pass, reg);
                    }
                        
                    case 3 -> System.out.println("Goodbye!");
                        
                    default -> System.out.println("Invalid choice.");
                }
                
            } while (choice != 3);
        }
    }
}
