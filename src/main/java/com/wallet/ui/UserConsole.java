package com.wallet.ui;

import com.wallet.domain.Player;
import com.wallet.domain.Transaction;
import com.wallet.in.Input;
import com.wallet.service.AdminService;
import com.wallet.service.WalletService;

import java.util.List;
import java.util.Scanner;

public class UserConsole {
    private WalletService walletService;
    private AdminService adminService;
    private AdminConsole adminConsole;
    private Input input=new Input(new Scanner(System.in));

    public UserConsole(WalletService walletService, AdminService adminService) {
        this.walletService = walletService;
        this.adminService = adminService;
        this.adminConsole = new AdminConsole(input, adminService);
    }

    public void runUserInterface() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Регистрация игрока");
            System.out.println("2. Аутентификация игрока");
            System.out.println("3. Проверка баланса игрока");
            System.out.println("4. Дебет");
            System.out.println("5. Кредит");
            System.out.println("6. История транзакций");
            System.out.println("7. Аутентификация администратора");
            System.out.println("0. Выход");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerPlayer(scanner);
                    break;
                case 2:
                    authenticatePlayer(scanner);
                    break;
                case 3:
                    checkPlayerBalance(scanner);
                    break;
                case 4:
                    debit(scanner);
                    break;
                case 5:
                    credit(scanner);
                    break;
                case 6:
                    displayTransactionHistory(scanner);
                    break;
                case 7:
                    authenticateAdmin(scanner);
                    break;
                case 0:
                    System.out.println("Выход из приложения.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Недопустимый выбор. Пожалуйста, попробуйте снова.");
            }
        }
    }

    private void registerPlayer(Scanner scanner) {
        walletService.registerPlayer(input.InputPlayer(scanner));
    }

    private void authenticatePlayer(Scanner scanner) {
        Player authplayer = input.InputPlayer(scanner);
        boolean isAuthenticated = walletService.authenticatePlayer(authplayer);
        if (isAuthenticated) {
            System.out.println("Аутентификация успешная.");
        } else {
            System.out.println("Аутентификация не удалась.");
        }
    }

    private void checkPlayerBalance(Scanner scanner) {
        String checkBalanceUsername = input.InputUsername(scanner);
        double balance = walletService.getPlayerBalance(checkBalanceUsername);
        System.out.println("Баланс: " + balance);
    }

    private void debit(Scanner scanner) {
        System.out.print("Введите имя пользователя: ");
        String debitUsername = scanner.nextLine();
        System.out.print("Введите идентификатор транзакции: ");
        String debitTransactionId = scanner.nextLine();
        System.out.print("Введите сумму дебета: ");
        double debitAmount = scanner.nextDouble();

        walletService.debit(debitUsername, debitTransactionId, debitAmount);
    }

    private void credit(Scanner scanner) {
        String creditUsername = input.getStringInput("Введите имя пользователя: ");
        String creditTransactionId = input.getStringInput("Введите идентификатор транзакции: ");
        double creditAmount = input.getDoubleInput("Введите сумму кредита: ");

        walletService.credit(creditUsername, creditTransactionId, creditAmount);
    }

    private void displayTransactionHistory(Scanner scanner) {
        String historyUsername = input.InputUsername(scanner);
        List<Transaction> transactions = walletService.getPlayerTransactionHistory(historyUsername);
        for (Transaction transaction : transactions) {
            System.out.println("Идентификатор транзакции: " + transaction.getTransactionId() + ", Сумма: " + transaction.getAmount());
        }
    }

    private void authenticateAdmin(Scanner scanner) {
            adminConsole.startAdminConsole(scanner);
    }
}
