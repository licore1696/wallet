package com.wallet.storage;

import com.wallet.domain.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс, представляющий хранилище данных в памяти для игроков.
 */
public class InMemoryStorage implements Storage {

    // Хранилище игроков, где ключ - имя пользователя, значение - объект игрока.
    private Map<String, Player> players = new HashMap<>();

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
}
