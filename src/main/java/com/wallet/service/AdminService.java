package com.wallet.service;

import com.wallet.audit.Audit;
import com.wallet.domain.Admin;
import com.wallet.domain.Player;
import com.wallet.storage.Storage;

    public class AdminService {
        private Storage storage;
        private Audit audit;

        public AdminService(Storage storage, Audit audit) {
            this.storage = storage;
            this.audit = audit;
            Admin admin= new Admin("admin", "admin");
            storage.addAdmin(admin);
        }

        public void registerAdmin(String username, String password){
            Admin existingAdmin = storage.getAdmin(username);
            if (existingAdmin == null) {
                Admin admin = new Admin(username, password);
                storage.addAdmin(admin);
                audit.log(username, "Регистрация администратора");
            } else {
                System.out.println("Username already exists. Registration failed.");
            }
        }

        public boolean authenticateAdmin(String username, String password) {
            Admin admin = storage.getAdmin(username);
            if (admin != null) {
                System.out.println(admin.getPassword());
                return admin.getPassword().equals(password);
            }
            return false;
        }

        public boolean banPlayer(String adminUsername, String playerUsername) {
            // Implement logic to ban a player
            // Check if the admin is authenticated
            Admin admin = storage.getAdmin(adminUsername);
            if (admin != null) {
                Player player = storage.getPlayer(playerUsername);
                if (player != null) {
                    // Implement the logic to ban the player (e.g., set a flag in player's data)
                    return true;
                }
            }
            return false;
        }

        public boolean unbanPlayer(String adminUsername, String playerUsername) {
            // Implement logic to unban a player
            // Check if the admin is authenticated
            Admin admin = storage.getAdmin(adminUsername);
            if (admin != null) {
                Player player = storage.getPlayer(playerUsername);
                if (player != null) {
                    // Implement the logic to unban the player (e.g., clear the ban flag)
                    return true;
                }
            }
            return false;
        }
    }
