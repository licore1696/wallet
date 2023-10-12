package com.wallet.storage;

import com.wallet.domain.Admin;
import com.wallet.domain.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс, представляющий хранилище данных в памяти для игроков.
 */
public class InMemoryStorage implements Storage {

    // Хранилище игроков, где ключ - имя пользователя, значение - объект игрока.
    private Map<String, Player> players = new HashMap<>();
    private Map<String, Admin> admins = new HashMap<>();

    /**
     * Получить игрока по его имени пользователя.
     *
     * @param username Имя пользователя игрока.
     * @return Объект игрока или null, если игрок не найден.
     */
    @Override
    public Player getPlayer(String username) {
        return players.get(username);
    }

    /**
     * Добавить нового игрока в хранилище.
     *
     * @param player Объект игрока для добавления.
     */
    @Override
    public void addPlayer(Player player) {
        players.put(player.getUsername(), player);
    }

    /**
     * Обновить информацию об игроке в хранилище.
     *
     * @param player Объект игрока с обновленными данными.
     */
    @Override
    public void updatePlayer(Player player) {
        players.put(player.getUsername(), player);
    }

    /**
     * Получить админа по его имени пользователя.
     *
     * @param username Имя пользователя игрока.
     * @return Объект админа или null, если админ не найден.
     */
    @Override
    public Admin getAdmin(String username) {
        return admins.get(username); // Implement logic to retrieve an admin by username
    }

    /**
     * Добавить нового игрока в хранилище.
     *
     * @param admin Объект админа для добавления.
     */
    public void addAdmin(Admin admin){ admins.put(admin.getUsername(), admin);}


}
