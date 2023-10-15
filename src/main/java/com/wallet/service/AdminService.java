package com.wallet.service;

import com.wallet.audit.Audit;
import com.wallet.domain.Admin;
import com.wallet.domain.Player;
import com.wallet.storage.Storage;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис, предоставляющий функциональность для администраторов системы кошельков.
 */
public class AdminService {
    private Storage storage;
    private Audit audit;

    /**
     * Конструктор для создания объекта класса AdminService.
     *
     * @param storage Хранилище данных.
     * @param audit   Система аудита.
     */
    public AdminService(Storage storage, Audit audit) {
        this.storage = storage;
        this.audit = audit;
        Admin admin = new Admin("admin", "admin");
        storage.addAdmin(admin);
    }

    /**
     * Регистрирует нового администратора в системе.
     *
     * @param username Имя пользователя администратора.
     * @param password Пароль администратора.
     */
    public void registerAdmin(String username, String password) {
        Admin existingAdmin = storage.getAdmin(username);
        if (existingAdmin == null) {
            Admin admin = new Admin(username, password);
            storage.addAdmin(admin);
            audit.log(username, "Регистрация администратора");
        } else {
            System.out.println("Администратор с таким именем уже существует.");
        }
    }

    /**
     * Аутентифицирует администратора по имени пользователя и паролю.
     *
     * @param admin Администратор для аутентификации.
     * @return true, если аутентификация успешна, иначе false.
     */
    public boolean authenticateAdmin(Admin admin) {
        Admin existingAdmin = storage.getAdmin(admin.getUsername());
        if (existingAdmin != null) {
            return existingAdmin.getPassword().equals(admin.getPassword());
        }
        return false;
    }

    /**
     * Блокирует игрока с именем пользователя администратора.
     *
     * @param adminUsername Имя пользователя администратора.
     * @param playerUsername Имя пользователя игрока, которого нужно заблокировать.
     * @return true, если блокировка успешна, иначе false.
     */
    public boolean banPlayer(String adminUsername, String playerUsername) {
        Admin admin = storage.getAdmin(adminUsername);
        if (admin != null) {
            Player player = storage.getPlayer(playerUsername);
            if (player != null) {
                player.setStatusBan(true);
                return true;
            }
        }
        return false;
    }

    /**
     * Разблокирует игрока с именем пользователя администратора.
     *
     * @param adminUsername Имя пользователя администратора.
     * @param playerUsername Имя пользователя игрока, которого нужно разблокировать.
     * @return true, если разблокировка успешна, иначе false.
     */
    public boolean unbanPlayer(String adminUsername, String playerUsername) {
        Admin admin = storage.getAdmin(adminUsername);
        if (admin != null) {
            Player player = storage.getPlayer(playerUsername);
            if (player != null) {
                player.setStatusBan(false);
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает список всех игроков в системе.
     *
     * @return Список игроков.
     */
    public List<Player> getPlayers() {
        List<Player> players = storage.getAllPlayers();
        return players;
    }

    /**
     * Возвращает список заблокированных игроков в системе.
     *
     * @return Список заблокированных игроков.
     */
    public List<Player> getBannedPlayers() {
        List<Player> bannedPlayers = new ArrayList<>();
        List<Player> players = storage.getAllPlayers();

        for (Player player : players) {
            if (player.getStatusBan()) {
                bannedPlayers.add(player);
            }
        }

        return bannedPlayers;
    }
}
