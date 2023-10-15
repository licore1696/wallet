package com.wallet.ui;

import com.wallet.audit.Audit;
import com.wallet.audit.ConsoleAudit;
import com.wallet.domain.Player;
import com.wallet.domain.Transaction;
import com.wallet.in.Input;
import com.wallet.service.AdminService;
import com.wallet.service.WalletService;
import com.wallet.out.Output;

import java.util.List;
import java.util.Scanner;

public class UserConsole {
    private WalletService walletService;
    private AdminConsole adminConsole;
    public Input input;
    Output output = new Output(System.out);
    private AdminService adminService;
    private Audit audit = new ConsoleAudit();

    public UserConsole(WalletService walletService, AdminService adminService, Scanner scanner,Audit audit) {
        this.walletService = walletService;
        this.adminService = adminService;
        this.input=new Input(scanner);
        this.audit=audit;
    }

    public void runUserInterface() {
        Scanner scanner = new Scanner(System.in);

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
        walletService.registerPlayer(input.InputPlayer(scanner));
    }

    void authenticatePlayer(Scanner scanner) {
        Player authplayer = input.InputPlayer(scanner);
        boolean isAuthenticated = walletService.authenticatePlayer(authplayer);
        if (isAuthenticated) {
            output.displayMessage("Аутентификация успешная.");
        } else {
            output.displayMessage("Аутентификация не удалась.");
        }
    }

    void checkPlayerBalance(Scanner scanner) {
        String checkBalanceUsername = input.InputUsername(scanner);
        double balance = walletService.getPlayerBalance(checkBalanceUsername);
        output.displayMessage("Баланс: " + balance);
        audit.log(checkBalanceUsername, "Проверка баланса");
    }

    void debit(Scanner scanner) {
        String debitUsername = input.InputUsername(scanner);
        String debitTransactionId = input.getStringInput("Введите идентификатор транзакции: ");
        double debitAmount = input.getDoubleInput("Введите сумму дебета: ");

        walletService.debit(debitUsername, debitTransactionId, debitAmount);
    }

    void credit(Scanner scanner) {
        String creditUsername = input.InputUsername(scanner);
        String creditTransactionId = input.getStringInputNoBuff("Введите идентификатор транзакции: ");
        double creditAmount = input.getDoubleInput("Введите сумму кредита: ");
        walletService.credit(creditUsername, creditTransactionId, creditAmount);
    }

    void displayTransactionHistory(Scanner scanner) {
        String historyUsername = input.InputUsername(scanner);
        audit.log(historyUsername, "История транзакций");
        List<Transaction> transactions = walletService.getPlayerTransactionHistory(historyUsername);
        for (Transaction transaction : transactions) {
            output.displayMessage("Идентификатор транзакции: " + transaction.getTransactionId() + ", Сумма: " + transaction.getAmount());
        }
    }

    private void authenticateAdmin(Scanner scanner) {
        adminConsole = new AdminConsole(input, adminService, audit);
        adminConsole.startAdminConsole(scanner);
    }
}
