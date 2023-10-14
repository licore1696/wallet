package com.wallet.domain;

import java.util.ArrayList;
import java.util.List;
/**
 * Представляет игрока в сервисе кошельков.
 */
public class Player {
    private String username;
    private String password;
    private double balance;
    private List<Transaction> transactionHistory;
    private Boolean StatusBan;


    /**
     * Конструктор для создания нового игрока с указанными данными.
     *
     * @param username Имя пользователя игрока.
     * @param password Пароль игрока.
     */
    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
        this.StatusBan=false;
    }
    /**
     * Возвращает имя пользователя игрока.
     *
     * @return Имя пользователя игрока.
     */
    public String getUsername() {
        return username;
    }
    /**
     * Возвращает пароль игрока.
     *
     * @return Пароль игрока.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Получить текущий баланс игрока.
     *
     * @return Текущий баланс игрока.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Получить статус блокировки.
     *
     * @return Статус блокировки.
     */
    public boolean getStatusBan() { return StatusBan; }
    /**
     * Изменить статус блокировки.
     *
     * @param statusBan Новый статус блокировки.
     */
    public void setStatusBan(boolean statusBan) { StatusBan=statusBan; }

    /**
     * Получить историю транзакций игрока.
     *
     * @return Список транзакций игрока.
     */
    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
    /**
     * Установить баланс игрока на указанное значение.
     *
     * @param balance Новый баланс игрока.
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }
    /**
     * Добавить новую транзакцию в историю игрока.
     *
     * @param transaction Новая транзакция для добавления.
     */
    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }
}

