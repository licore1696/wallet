package com.wallet.ui;

import com.wallet.audit.Audit;
import com.wallet.domain.Player;
import com.wallet.domain.Transaction;
import com.wallet.in.Input;
import com.wallet.out.Output;
import com.wallet.service.AdminService;
import com.wallet.service.WalletService;

import java.util.List;
import java.util.Scanner;

public class UserConsole {
    private WalletService walletService;
    private AdminService adminService;
    public Input input;
    private Output output;
    private Audit audit;
    private Scanner scanner;

    public UserConsole(WalletService walletService, AdminService adminService,Scanner scanner, Audit audit) {
        this.walletService = walletService;
        this.adminService = adminService;
        this.input = new Input(scanner);
        this.output = new Output(System.out);
        this.audit = audit;
        this.scanner = scanner;
    }

    public void runUserInterface() {

        while (true) {
            output.displayUserMenu();

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

    public void registerPlayer(Scanner scanner) {
        Player player = input.inputPlayer(scanner);
        boolean isRegistered = walletService.registerPlayer(player);
        if(isRegistered){
            output.displayMessage("Успешная регистрация.");
            audit.log(player.getUsername(),"Регистрация");
        }
        else {
            output.displayMessage("Решистрация не удалась. Такой пользователь уже существует.");
        }

    }

    public void authenticatePlayer(Scanner scanner) {
        Player authPlayer = input.inputPlayer(scanner);
        boolean isAuthenticated = walletService.authenticatePlayer(authPlayer);
        if (isAuthenticated) {
            output.displayMessage("Аутентификация успешная.");
        } else {
            output.displayMessage("Аутентификация не удалась.");
        }
    }

    public void checkPlayerBalance(Scanner scanner) {
        String checkBalanceUsername = input.inputUsername(scanner);
        double balance = walletService.getPlayerBalance(checkBalanceUsername);
        output.displayMessage("Баланс: " + balance);
        audit.log(checkBalanceUsername, "Проверка баланса");
    }

    public void debit(Scanner scanner) {
        String debitUsername = input.inputUsername(scanner);
        String debitTransactionId = input.inputTransactionId(scanner);
        double debitAmount = input.inputAmount(scanner);

        boolean result = walletService.debit(debitUsername, debitTransactionId, debitAmount);

        if (result) {
            output.displayMessage("Дебетовая транзакция выполнена успешно.");
            audit.log(debitUsername, "Дебетовая транзакция");
        } else {
            output.displayMessage("Дебетовая транзакция не выполнена.");
        }
    }

    public void credit(Scanner scanner) {
        String creditUsername = input.inputUsername(scanner);
        String creditTransactionId = input.inputTransactionId(scanner);
        double creditAmount = input.inputAmount(scanner);

        boolean result = walletService.credit(creditUsername, creditTransactionId, creditAmount);
        if (result){
            output.displayMessage("Кредитная транзакция выполнена успешно.");
            audit.log(creditUsername, "Кредитная транзакция");
        }
        else {
            output.displayMessage("Кредитная транзакция не выполнена.");
        }
    }

    public void displayTransactionHistory(Scanner scanner) {
        String historyUsername = input.inputUsername(scanner);

        List<Transaction> transactions = walletService.getPlayerTransactionHistory(historyUsername);
        for (Transaction transaction : transactions) {
            output.displayMessage("Идентификатор транзакции: " + transaction.getTransactionId() + ", Сумма: " + transaction.getAmount());
        }

        audit.log(historyUsername, "История транзакций");
    }

    private void authenticateAdmin(Scanner scanner) {
        AdminConsole adminConsole = new AdminConsole(input, adminService, audit);
        adminConsole.startAdminConsole(scanner);
    }
}
