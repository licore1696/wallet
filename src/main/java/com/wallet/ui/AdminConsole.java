package com.wallet.ui;

import com.wallet.service.AdminService;
import com.wallet.in.Input;
import com.wallet.out.Output;

import java.util.Scanner;

public class AdminConsole {
    private Input input = new Input(new Scanner(System.in));
    private Output output;
    private AdminService adminService;

    public AdminConsole(Input input, AdminService adminService) {
        this.input = input;
        this.adminService = adminService;
    }

    public void startAdminConsole(Scanner scanner) {

        boolean isAdminAuthenticated = adminService.authenticateAdmin( input.InputAdmin(scanner));
        if (isAdminAuthenticated) {
            System.out.println("Admin authentication successful.");

            while (true) {
                displayMainMenu(); // Display the main menu options
                int adminChoice = input.getIntInput(" ");

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
                        displayMessage("Выход с админ панели. ");
                        return;
                    default:
                        displayMessage("Неверный выбор. Попробуйте снова. ");
                }
            }
        } else {
            displayMessage("Admin authentication failed.");

        }
    }

    private void displayMainMenu() {
        displayMessage("Меню администратора: ");
        displayMessage("1. Регистрация администратора ");
        displayMessage("2. Блокировка игрока");
        displayMessage("3. Разблокировка игрока");
        displayMessage("0. Выход из меню администратора");
    }

    private void registerAdmin() {
       displayMessage("Регистрация нового администратора");
        String newAdminUsername = input.getStringInput("Введите имя администратора: ");
        String newAdminPassword = input.getStringInput("Введите пароль: ");

        adminService.registerAdmin(newAdminUsername, newAdminPassword);
        displayMessage("Успешная регистрация администратора.");
    }

    private void banPlayer() {
        displayMessage("Блокировка игрока");
        String adminUsername = input.getStringInput("Введите имя администратора: ");
        String playerUsername = input.getStringInput("Введите имя игрока, что бы заблокировать: ");

        boolean result = adminService.banPlayer(adminUsername, playerUsername);

        if (result) {
            displayMessage("Игрок заблокирован успешно.");
        } else {
            displayMessage("Ошибка в процедуре блокировки.");
        }
    }

    private void unbanPlayer() {
        displayMessage("Разблокировка игрока");
        String adminUsername = input.getStringInput("Введите имя администратора: ");
        String playerUsername = input.getStringInput("Введите имя игрока для разблокировки: ");

        boolean result = adminService.unbanPlayer(adminUsername, playerUsername);

        if (result) {
            displayMessage("Игрок разблокирован успешно.");
        } else {
            displayMessage("Ошибка в процедуре разблокировки");
        }
    }
    public void displayMessage(String promt){
        System.out.println(promt);
    }
}
