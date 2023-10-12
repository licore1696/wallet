package com.wallet.ui;

import com.wallet.service.AdminService;
import com.wallet.in.InputConsole;
import com.wallet.out.OutputConsole;

public class AdminConsole {
    private InputConsole inputConsole;
    private OutputConsole outputConsole;
    private AdminService adminService;

    public AdminConsole(InputConsole inputConsole, OutputConsole outputConsole, AdminService adminService) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
        this.adminService = adminService;
    }

    public void startAdminConsole() {
        outputConsole.displayMessage("Admin Console");
        outputConsole.displayMessage("Enter your admin username and password:");

        outputConsole.displayMessage("Username: ");
        String adminUsername = inputConsole.getStringInput();
        outputConsole.displayMessage("Password: ");
        String adminPassword = inputConsole.getStringInput();

        boolean isAdminAuthenticated = adminService.authenticateAdmin(adminUsername, adminPassword);

        if (isAdminAuthenticated) {
            outputConsole.displayMessage("Admin authentication successful.");

            while (true) {
                displayMainMenu(); // Display the main menu options
                int adminChoice = inputConsole.getIntInput();

                switch (adminChoice) {
                    case 1:
                        registerAdmin();
                        break;
                    case 2:
                        banPlayer();
                        break;
                    case 3:
                        unbanPlayer();
                        break;
                    case 0:
                        outputConsole.displayMessage("Exiting admin panel.");
                        return;
                    default:
                        outputConsole.displayMessage("Invalid choice. Please try again.");
                }
            }
        } else {
            outputConsole.displayMessage("Admin authentication failed.");
        }
    }

    private void displayMainMenu() {
        outputConsole.displayMessage("Admin Options:");
        outputConsole.displayMessage("1. Register Admin");
        outputConsole.displayMessage("2. Ban Player");
        outputConsole.displayMessage("3. Unban Player");
        outputConsole.displayMessage("0. Exit");
    }

    private void registerAdmin() {
        outputConsole.displayMessage("Register New Admin");
        outputConsole.displayMessage("Enter username: ");
        String newAdminUsername = inputConsole.getStringInput();
        outputConsole.displayMessage("Enter password: ");
        String newAdminPassword = inputConsole.getStringInput();

        adminService.registerAdmin(newAdminUsername, newAdminPassword);
        outputConsole.displayMessage("Admin registration successful.");
    }

    private void banPlayer() {
        outputConsole.displayMessage("Ban Player");
        outputConsole.displayMessage("Enter admin username: ");
        String adminUsername = inputConsole.getStringInput();
        outputConsole.displayMessage("Enter player username to ban: ");
        String playerUsername = inputConsole.getStringInput();

        boolean result = adminService.banPlayer(adminUsername, playerUsername);

        if (result) {
            outputConsole.displayMessage("Player banned successfully.");
        } else {
            outputConsole.displayMessage("Ban operation failed.");
        }
    }

    private void unbanPlayer() {
        outputConsole.displayMessage("Unban Player");
        outputConsole.displayMessage("Enter admin username: ");
        String adminUsername = inputConsole.getStringInput();
        outputConsole.displayMessage("Enter player username to unban: ");
        String playerUsername = inputConsole.getStringInput();

        boolean result = adminService.unbanPlayer(adminUsername, playerUsername);

        if (result) {
            outputConsole.displayMessage("Player unbanned successfully.");
        } else {
            outputConsole.displayMessage("Unban operation failed.");
        }
    }
}
