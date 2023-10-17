package com.wallet.ui;

import com.wallet.audit.Audit;
import com.wallet.audit.ConsoleAudit;
import com.wallet.domain.Admin;
import com.wallet.domain.Player;
import com.wallet.service.AdminService;
import com.wallet.in.Input;
import com.wallet.out.Output;

import java.util.List;
import java.util.Scanner;

public class AdminConsole {
    private Input input;
    private Output output = new Output(System.out);
    private AdminService adminService;
    private Admin admin;
    private Audit audit;


    public AdminConsole(Input input, AdminService adminService, Audit audit) {
        this.input = input;
        this.adminService = adminService;
        this.audit = audit;
    }

    public void startAdminConsole(Scanner scanner) {
        admin = input.InputAdmin(scanner);
        boolean isAdminAuthenticated = adminService.authenticateAdmin(admin);
        if (isAdminAuthenticated) {
            output.displayMessage("Аутентификация администратора успешная");

            while (true) {
                output.displayAdminMenu();
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
                        listUnBannedPlayers();
                        break;
                    case 5:
                        listBannedPlayers();
                        break;
                    case 0:
                        output.displayMessage("Выход с админ панели. ");
                        return;
                    default:
                        output.displayMessage("Неверный выбор. Попробуйте снова. ");
                }
            }
        } else {
            output.displayMessage("Аутентификация администратора не успешная");

        }
    }


    public void listUnBannedPlayers() {
        audit.log(admin.getUsername(), "Просмотр разблокированных игроков");
        List <Player> players = adminService.getUnBannedPlayers();
        for (Player player : players) {
            output.displayMessage("Имя игрока: " + player.getUsername());
        }
    }

    public void listBannedPlayers() {
        audit.log(admin.getUsername(), "Просмотр заблокированных игроков");
        List<Player> players = adminService.getBannedPlayers();
        for (Player player : players) {
            if (player.getStatusBan()) {
                output.displayMessage("Заблокированный игрок: " + player.getUsername());
            }
        }
    }


    public void registerAdmin() {
       output.displayMessage("Регистрация нового администратора");
        String newAdminUsername = input.getStringInput("Введите имя администратора: ");
        String newAdminPassword = input.getStringInputNoBuff("Введите пароль: ");
        adminService.registerAdmin(newAdminUsername, newAdminPassword);
    }

    public void banPlayer() {
        String playerUsername = input.getStringInput("Введите имя игрока, что бы заблокировать: ");

        boolean result = adminService.banPlayer(admin.getUsername(), playerUsername);

        if (result) {
            output.displayMessage("Игрок заблокирован успешно.");
            audit.log(admin.getUsername(), "Блокировка: " + playerUsername);
        } else {
            output.displayMessage("Ошибка в процедуре блокировки.");
        }
    }

    public void unbanPlayer() {
        output.displayMessage("Разблокировка игрока");
        String playerUsername = input.getStringInput("Введите имя игрока для разблокировки: ");

        boolean result = adminService.unbanPlayer(admin.getUsername(), playerUsername);

        if (result) {
            output.displayMessage("Игрок разблокирован успешно.");
            audit.log(admin.getUsername(), "Разблокировка: " + playerUsername);
        } else {
            output.displayMessage("Ошибка в процедуре разблокировки");
        }
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public Output getOutput() {
        return output;
    }

    void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
