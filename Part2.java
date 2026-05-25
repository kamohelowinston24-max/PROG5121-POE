/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PROG5121;

/**
 *
 * @author Student
 */
public class Part2 {
    
}
package PROG5121;

import java.util.Scanner;

/**
 * Part 2 - QuickChat Messaging Application
 * PROG5121POE
 * @author Student
 */
public class Part2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Registration reg = new Registration();

        System.out.println("=== Welcome to QuickChat ===");
        System.out.println("\n--- Register ---");

        // Registration
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Cell Phone (with international code e.g. +27...): ");
        String cellPhone = scanner.nextLine();

        String regResult = reg.registerUser(firstName, lastName, username, password, cellPhone);
        System.out.println(regResult);

        if (!regResult.contains("successful")) {
            System.out.println("Registration failed. Exiting.");
            scanner.close();
            return;
        }

        // Login
        System.out.println("\n--- Login ---");
        System.out.print("Username: ");
        String loginUser = scanner.nextLine();
        System.out.print("Password: ");
        String loginPass = scanner.nextLine();

        String loginStatus = reg.returnLoginStatus(loginUser, loginPass);
        System.out.println(loginStatus);

        if (!reg.loginUser(loginUser, loginPass)) {
            System.out.println("Login failed. Exiting.");
            scanner.close();
            return;
        }

        // Message sending
        System.out.println("\nWelcome to QuickChat.");
        System.out.print("How many messages do you want to send? ");
        int numMessages = Integer.parseInt(scanner.nextLine().trim());

        int messageCount = 0;
        boolean running = true;

        while (running) {
            System.out.println("\n1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    if (messageCount >= numMessages) {
                        System.out.println("You have sent all your messages.");
                        break;
                    }
                    messageCount++;
                    Message msg = buildMessage(scanner, messageCount);
                    if (msg == null) {
                        messageCount--; // don't count invalid
                        break;
                    }
                    System.out.println("\nMessage Hash: " + msg.getMessageHash());
                    System.out.println("1) Send Message");
                    System.out.println("2) Disregard Message");
                    System.out.println("3) Store Message to send later");
                    System.out.print("Choose: ");
                    String sendChoice = scanner.nextLine().trim();
                    String action = "send";
                    if (sendChoice.equals("2")) action = "discard";
                    else if (sendChoice.equals("3")) action = "store";
                    String result = msg.sentMessage(action);
                    System.out.println(result);

                    if (messageCount >= numMessages) {
                        System.out.println("\nAll messages sent.");
                        System.out.println(msg.printMessages());
                        System.out.println("Total messages sent: " + msg.returnTotalMessages());
                    }
                    break;

                case "2":
                    System.out.println("Coming Soon.");
                    break;

                case "3":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
        System.out.println("Goodbye!");
    }

    private static Message buildMessage(Scanner scanner, int msgNum) {
        Message tempMsg = new Message(msgNum, "", "");

        System.out.print("Recipient cell number: ");
        String recipient = scanner.nextLine().trim();
        String cellCheck = tempMsg.checkRecipientCell(recipient);
        System.out.println(cellCheck);
        if (!cellCheck.contains("successfully")) return null;

        System.out.print("Enter message (max 250 chars): ");
        String text = scanner.nextLine();
        String lengthCheck = tempMsg.checkMessageLength(text);
        System.out.println(lengthCheck);
        if (!lengthCheck.equals("Message ready to send.")) return null;

        return new Message(msgNum, recipient, text);
    }
}