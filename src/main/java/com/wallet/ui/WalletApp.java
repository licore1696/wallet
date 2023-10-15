package com.wallet.ui;

import com.wallet.audit.Audit;
import com.wallet.audit.ConsoleAudit;
import com.wallet.service.AdminService;
import com.wallet.service.WalletService;
import com.wallet.storage.InMemoryStorage;
import com.wallet.storage.Storage;

import java.util.Scanner;

public class WalletApp {
    public static void main(String[] args) {
        Storage storage = new InMemoryStorage();
        Audit audit = new ConsoleAudit();
        WalletService walletService = new WalletService(storage, audit);
        AdminService adminService = new AdminService(storage, audit);
        UserConsole userConsole = new UserConsole(walletService, adminService, new Scanner(System.in), audit);
        userConsole.runUserInterface();
    }
}
