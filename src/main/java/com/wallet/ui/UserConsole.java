package com.wallet.ui;

import com.wallet.domain.Transaction;
import com.wallet.service.AdminService;
import com.wallet.service.WalletService;

import java.util.List;
import java.util.Scanner;

public class UserConsole {
    private WalletService walletService;
    private AdminService adminService;

    public UserConsole(WalletService walletService, AdminService adminService) {
        this.walletService = walletService;
        this.adminService = adminService;
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
        System.out.print("Введите имя пользователя: ");
        String registerUsername = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String registerPassword = scanner.nextLine();
        walletService.registerPlayer(registerUsername, registerPassword);
    }

    private void authenticatePlayer(Scanner scanner) {
        System.out.print("Введите имя пользователя: ");
        String authUsername = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String authPassword = scanner.nextLine();
        boolean isAuthenticated = walletService.authenticatePlayer(authUsername, authPassword);
        if (isAuthenticated) {
            System.out.println("Аутентификация успешная.");
        } else {
            System.out.println("Аутентификация не удалась.");
        }
    }

    private void checkPlayerBalance(Scanner scanner) {
        System.out.print("Введите имя пользователя: ");
        String checkBalanceUsername = scanner.nextLine();
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
        System.out.print("Введите имя пользователя: ");
        String creditUsername = scanner.nextLine();
        System.out.print("Введите идентификатор транзакции: ");
        String creditTransactionId = scanner.nextLine();
        System.out.print("Введите сумму кредита: ");
        double creditAmount = scanner.nextDouble();

        walletService.credit(creditUsername, creditTransactionId, creditAmount);
    }

    private void displayTransactionHistory(Scanner scanner) {
        System.out.print("Введите имя пользователя: ");
        String historyUsername = scanner.nextLine();
        List<Transaction> transactions = walletService.getPlayerTransactionHistory(historyUsername);
        for (Transaction transaction : transactions) {
            System.out.println("Идентификатор транзакции: " + transaction.getTransactionId() + ", Сумма: " + transaction.getAmount());
        }
    }

    private void authenticateAdmin(Scanner scanner) {
        System.out.print("Введите имя администратора: ");
        String adminUsername = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String adminPassword = scanner.nextLine();
        boolean isAdminAuthenticated = adminService.authenticateAdmin(adminUsername, adminPassword);
        if (isAdminAuthenticated) {
            System.out.println("Аутентификация успешная.");
            // Реализуйте админ-панель здесь
        } else {
            System.out.println("Аутентификация не удалась.");
        }
    }
}
