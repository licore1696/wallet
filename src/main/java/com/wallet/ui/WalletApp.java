package com.wallet.ui;

import com.wallet.audit.Audit;



import java.util.Scanner;

import com.wallet.audit.ConsoleAudit;
import com.wallet.service.AdminService;
import com.wallet.service.WalletService;
import com.wallet.storage.Storage;
import com.wallet.storage.StorageDB;
import jdbc.connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class WalletApp {
    public static void main(String[] args) {

            // Создаем объект Storage для работы с базой данных
        String url = "jdbc:postgresql://localhost:5432/Wallet";  // Замените на ваш URL базы данных
        String user = "postgres";  // Замените на своего пользователя
        String password = "password";  // Замените на свой пароль
            Storage storage = new StorageDB(DatabaseConnection.testDatabaseConnection(url, user, password));

            // Создаем объект Audit
            Audit audit = new ConsoleAudit();

            // Создаем сервисы
            WalletService walletService = new WalletService(storage, audit);
            AdminService adminService = new AdminService(storage, audit);

            // Создаем консольное приложение для пользователей
            UserConsole userConsole = new UserConsole(walletService, adminService, new Scanner(System.in), audit);
            userConsole.runUserInterface();



    }
}
