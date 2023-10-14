package com.wallet.ui;

import com.wallet.domain.Admin;
import com.wallet.domain.Player;
import com.wallet.service.AdminService;
import com.wallet.in.Input;
import com.wallet.out.Output;

import java.util.List;
import java.util.Scanner;

public class AdminConsole {
    private Input input;
    private Output output = new Output();
    private AdminService adminService;
    private Admin admin;


    public AdminConsole(Input input, AdminService adminService) {
        this.input = input;
        this.adminService = adminService;
    }

    public void startAdminConsole(Scanner scanner) {
        admin = input.InputAdmin(scanner);
        boolean isAdminAuthenticated = adminService.authenticateAdmin(admin);
        if (isAdminAuthenticated) {
            System.out.println("Admin authentication successful.");

            while (true) {
                output.displayAdminMenu(); // Display the main menu options
                int adminChoice = input.getIntInput();

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
                    case 4:
                        listPlayers();
                        break;
                    case 5:
                        listBannedPlayers();
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


    public void listPlayers() {
        List <Player> players = adminService.getPlayers();
        for (Player player : players) {
            displayMessage("Имя игрока: " + player.getUsername());
        }
    }

    public void listBannedPlayers() {
        List<Player> players = adminService.getBannedPlayers();
        for (Player player : players) {
            if (player.getStatusBan()) {
                displayMessage("Заблокированный игрок: " + player.getUsername());
            }
        }
    }


    public void registerAdmin() {
       displayMessage("Регистрация нового администратора");
        String newAdminUsername = input.getStringInput("Введите имя администратора: ");
        String newAdminPassword = input.getStringInputNoBuff("Введите пароль: ");

        adminService.registerAdmin(newAdminUsername, newAdminPassword);
        displayMessage("Успешная регистрация администратора.");
    }

    public void banPlayer() {
        displayMessage("Блокировка игрока");
        String playerUsername = input.getStringInputNoBuff("Введите имя игрока, что бы заблокировать: ");

        boolean result = adminService.banPlayer(admin.getUsername(), playerUsername);

        if (result) {
            displayMessage("Игрок заблокирован успешно.");
        } else {
            displayMessage("Ошибка в процедуре блокировки.");
        }
    }

    public void unbanPlayer() {
        displayMessage("Разблокировка игрока");
        String playerUsername = input.getStringInputNoBuff("Введите имя игрока для разблокировки: ");

        boolean result = adminService.unbanPlayer(admin.getUsername(), playerUsername);

        if (result) {
            displayMessage("Игрок разблокирован успешно.");
        } else {
            displayMessage("Ошибка в процедуре разблокировки");
        }
    }
    public void displayMessage(String promt){
        System.out.println(promt);
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public Output getOutput() {
        return output;
    }
}
