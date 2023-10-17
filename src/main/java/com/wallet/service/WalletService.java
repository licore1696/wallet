package com.wallet.service;

import com.wallet.audit.Audit;
import com.wallet.domain.Player;
import com.wallet.domain.Transaction;
import com.wallet.storage.Storage;

import java.util.List;

/**
 * Сервис для управления кошельками игроков.
 */
public class WalletService {
    private Storage storage;
    private Audit audit;

    public WalletService(Storage storage, Audit audit) {
        this.storage = storage;
        this.audit = audit;
    }

    /**
     * Регистрирует нового игрока в системе.
     *
     * @param player Игрок для регистрации.
     * @return true, если регистрация успешна, в противном случае - false.
     */
    public boolean registerPlayer(Player player) {
        boolean result = storage.addPlayer(player);
        return result;
    }

    /**
     * Проверяет аутентификацию игрока.
     *
     * @param player Игрок для аутентификации.
     * @return true, если аутентификация успешна, в противном случае - false.
     */
    public boolean authenticatePlayer(Player player) {
        Player existingPlayer = storage.getPlayer(player.getUsername());
        if (existingPlayer != null) {
            return existingPlayer.getPassword().equals(player.getPassword()) && !existingPlayer.getStatusBan();
        }
        return false;
    }

    /**
     * Получает баланс игрока по его имени пользователя.
     *
     * @param username Имя пользователя игрока.
     * @return Баланс игрока.
     */
    public double getPlayerBalance(String username) {
        Player player = storage.getPlayer(username);
        if (player != null) {
            return player.getBalance();
        }
        return 0.0;
    }

    /**
     * Осуществляет списание средств с баланса игрока.
     *
     * @param username      Имя пользователя игрока.
     * @param transactionId Идентификатор транзакции.
     * @param amount        Сумма списания.
     * @return true, если списание успешно, в противном случае - false.
     */
    public boolean debit(String username, String transactionId, double amount) {
        Player player = storage.getPlayer(username);
        if (player != null) {
            if (storage.isTransactionIdUnique(transactionId) && amount > 0 && player.getBalance() > amount && !player.getStatusBan()) {
                player.setBalance(player.getBalance() - amount);
                Transaction transaction = new Transaction(player.getId(), -amount, transactionId);
                storage.addTransaction(transaction);
                storage.updatePlayer(player);
                return true;
            }
        }
        return false;
    }

    /**
     * Осуществляет зачисление средств на баланс игрока.
     *
     * @param username      Имя пользователя игрока.
     * @param transactionId Идентификатор транзакции.
     * @param amount        Сумма зачисления.
     * @return true, если зачисление успешно, в противном случае - false.
     */
    public boolean credit(String username, String transactionId, double amount) {
        Player player = storage.getPlayer(username);
        if (player != null) {
            if (storage.isTransactionIdUnique(transactionId) && amount > 0 && !player.getStatusBan()) {
                player.setBalance(player.getBalance() + amount);
                Transaction transaction = new Transaction(player.getId(), amount, transactionId);
                storage.addTransaction(transaction);
                storage.updatePlayer(player);
                return true;
            }
        }
        return false;
    }

    /**
     * Получает историю транзакций игрока.
     *
     * @param username Имя пользователя игрока.
     * @return Список транзакций игрока.
     */
    public List<Transaction> getPlayerTransactionHistory(String username) {
        Player player = storage.getPlayer(username);
        if (player != null) {
            return storage.getTransactionsForPlayer(player);
        }
        return null;
    }
}
