package com.wallet.ui;

import com.wallet.audit.Audit;
import com.wallet.audit.ConsoleAudit;
import com.wallet.domain.Transaction;
import com.wallet.storage.InMemoryStorage;
import com.wallet.storage.Storage;
import com.wallet.service.WalletService;

import java.util.List;
import java.util.Scanner;

/**
 * Главный класс приложения для управления кошельками игроков.
 */
public class WalletApp {
    public static void main(String[] args) {
        // Создание хранилища данных в памяти и службы аудита
        Storage storage = new InMemoryStorage();
        Audit audit = new ConsoleAudit();
        WalletService walletService = new WalletService(storage, audit);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Отображение меню операций
            System.out.println("1. Регистрация игрока");
            System.out.println("2. Авторизация игрока");
            System.out.println("3. Проверка баланса игрока");
            System.out.println("4. Дебет");
            System.out.println("5. Кредит");
            System.out.println("6. История транзакций");
            System.out.println("0. Выход");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Введите имя пользователя: ");
                    String registerUsername = scanner.nextLine();
                    System.out.print("Введите пароль: ");
                    String registerPassword = scanner.nextLine();
                    walletService.registerPlayer(registerUsername, registerPassword);
                    break;
                case 2:
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
                    break;
                case 3:
                    System.out.print("Введите имя пользователя: ");
                    String checkBalanceUsername = scanner.nextLine();
                    double balance = walletService.getPlayerBalance(checkBalanceUsername);
                    System.out.println("Баланс: " + balance);
                    break;
                case 4:
                    System.out.print("Введите имя пользователя: ");
                    String debitUsername = scanner.nextLine();
                    System.out.print("Введите идентификатор транзакции: ");
                    String debitTransactionId = scanner.nextLine();
                    System.out.print("Введите сумму дебета: ");
                    double debitAmount = scanner.nextDouble();

                    walletService.debit(debitUsername, debitTransactionId, debitAmount);

                    break;

                case 5:
                    System.out.print("Введите имя пользователя: ");
                    String creditUsername = scanner.nextLine();
                    System.out.print("Введите идентификатор транзакции: ");
                    String creditTransactionId = scanner.nextLine();
                    System.out.print("Введите сумму кредита: ");
                    double creditAmount = scanner.nextDouble();

                    walletService.credit(creditUsername, creditTransactionId, creditAmount);
                    break;
                case 6:
                    System.out.print("Введите имя пользователя: ");
                    String historyUsername = scanner.nextLine();
                    List<Transaction> transactions = walletService.getPlayerTransactionHistory(historyUsername);
                    for (Transaction transaction : transactions) {
                        System.out.println("Идентификатор транзакции: " + transaction.getTransactionId() + ", Сумма: " + transaction.getAmount());
                    }
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
}
