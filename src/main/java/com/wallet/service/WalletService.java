package com.wallet.service;

import com.wallet.audit.Audit;
import com.wallet.domain.Player;
import com.wallet.domain.Transaction;
import com.wallet.storage.Storage;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, предоставляющий сервисы для управления кошельками игроков и транзакциями.
 */
public class WalletService {
    private Storage storage;
    private Audit audit;

    /**
     * Конструктор для создания экземпляра сервиса кошельков.
     *
     * @param storage Хранилище данных игроков.
     * @param audit   Служба аудита для регистрации действий пользователей.
     */
    public WalletService(Storage storage, Audit audit) {
        this.storage = storage;
        this.audit = audit;
    }

    /**
     * Регистрация нового игрока с указанным именем пользователя и паролем.
     *
     * @param player Аккаунт игрока.
     */
    public void registerPlayer(Player player) {
        Player existingPlayer = storage.getPlayer(player.getUsername());
        if (existingPlayer == null) {
            storage.addPlayer(player);
            audit.log(player.getUsername(), "Регистрация");
        } else {
            System.out.println("Такой логин уже существует. Ошибка регистрации.");
        }
    }

    /**
     * Аутентификация игрока с указанным именем пользователя и паролем.
     *
     @param player Аккаунт игрока.
     @return true, если аутентификация успешна, false в противном случае.
     */
    public boolean authenticatePlayer(Player player) {
        Player exitingPlayer = storage.getPlayer(player.getUsername());
        if (exitingPlayer != null && exitingPlayer.getPassword().equals(player.getPassword())) {
            audit.log(player.getUsername(), "Аутентификация");
            return true;
        }
        return false;
    }

    /**
     * Получить баланс игрока по его имени пользователя.
     *
     * @param username Имя пользователя игрока.
     * @return Текущий баланс игрока.
     */
    public double getPlayerBalance(String username) {
        Player player = storage.getPlayer(username);
        if (player != null) {
            audit.log(username, "Проверка баланса");
            return player.getBalance();
        }
        return 0.0;
    }

    /**
     * Снять средства с баланса игрока (дебетовая транзакция).
     *
     * @param username      Имя пользователя игрока.
     * @param transactionId Идентификатор транзакции.
     * @param amount        Сумма для снятия.
     * @return true, если дебетовая транзакция успешна, false в противном случае.
     */
    public boolean debit(String username, String transactionId, double amount) {
        Player player = storage.getPlayer(username);
        if (player != null) {
            synchronized (player) {
                if (player.getBalance() >= amount && isTransactionIdUnique(username, transactionId) && amount>0) {
                    player.setBalance(player.getBalance() - amount);
                    Transaction transaction = new Transaction(transactionId, -amount);
                    player.addTransaction(transaction);
                    storage.updatePlayer(player);
                    audit.log(username, "Дебетовая транзакция");
                    return true;
                } else {
                    System.out.println("Дебетовая транзакция не удалась. Неуникальный идентификатор транзакции или отрицательный дебет.");
                }
            }
        } else {
            System.out.println("Дебетовая транзакция не удалась. Игрок не найден");
        }
        return false;
    }

    /**
     * Пополнить счет игрока (кредитная транзакция).
     *
     * @param username      Имя пользователя игрока.
     * @param transactionId Идентификатор транзакции.
     * @param amount        Сумма для пополнения.
     */
    public void credit(String username, String transactionId, double amount) {
        Player player = storage.getPlayer(username);
        if (player != null) {
            synchronized (player) {
                if (isTransactionIdUnique(username, transactionId)&&amount>0) {

                    player.setBalance(player.getBalance() + amount);
                    Transaction transaction = new Transaction(transactionId, amount);
                    player.addTransaction(transaction);
                    storage.updatePlayer(player);
                    audit.log(username, "Кредитная транзакция");
                } else {
                    System.out.println("Кредитная транзакция не удалась. Неуникальный идентификатор транзакции или отрицательный кредит.");
                }
            }
        } else {
            System.out.println("Кредитная транзакция не удалась. Игрок не найден");
        }
    }

    /**
     * Проверить, что идентификатор транзакции уникален для игрока.
     *
     * @param username      Имя пользователя игрока.
     * @param transactionId Идентификатор транзакции для проверки.
     * @return true, если идентификатор транзакции уникален, false в противном случае.
     */
    public boolean isTransactionIdUnique(String username, String transactionId) {
        Player player = storage.getPlayer(username);
        if (player != null) {
            List<Transaction> transactions = player.getTransactionHistory();
            for (Transaction transaction : transactions) {
                if (transaction.getTransactionId().equals(transactionId)) {
                    return false; // Идентификатор транзакции не уникален
                }
            }
        }
        return true; // Идентификатор транзакции уникален
    }

    /**
     * Получить историю транзакций игрока по его имени пользователя.
     *
     * @param username Имя пользователя игрока.
     * @return Список транзакций игрока.
     */
    public List<Transaction> getPlayerTransactionHistory(String username) {
        Player player = storage.getPlayer(username);
        if (player != null) {
            audit.log(username, "Проверка Истории транзакций");
            return player.getTransactionHistory();
        }
        return new ArrayList<>();
    }
}
