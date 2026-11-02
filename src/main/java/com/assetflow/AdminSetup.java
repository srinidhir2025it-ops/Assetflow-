
package com.assetflow;

import java.util.Scanner;

public class AdminSetup {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Admin username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter Admin password: ");
        String password = scanner.nextLine();

        String passwordHash = PasswordUtil.hashPassword(password);

        System.out.println("\nUsername: " + username);
        System.out.println("Password Hash: " + passwordHash);

        scanner.close();
    }
}
