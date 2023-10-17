package com.wallet.domain;

/**
 * Представляет транзакцию в сервисе кошельков.
 */
public class Transaction {
    private int playerId;
    private double amount;
    private String transactionId;

    /**
     * Конструктор для создания объекта Transaction с указанным идентификатором игрока, суммой и идентификатором транзакции.
     *
     * @param playerId      Идентификатор игрока, участвующего в транзакции.
     * @param amount        Сумма транзакции.
     * @param transactionId Идентификатор транзакции.
     */
    public Transaction(int playerId, double amount, String transactionId) {
        this.playerId = playerId;
        this.amount = amount;
        this.transactionId = transactionId;
    }

    /**
     * Конструктор для создания объекта Transaction без указания идентификатора игрока.
     *
     * @param transactionId Идентификатор транзакции.
     * @param amount        Сумма транзакции.
     */
    public Transaction(String transactionId, double amount) {
        this.playerId = 0;
        this.amount = amount;
        this.transactionId = transactionId;
    }

    /**
     * Получить идентификатор игрока, участвующего в транзакции.
     *
     * @return Идентификатор игрока.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Установить идентификатор игрока, участвующего в транзакции.
     *
     * @param playerId Новый идентификатор игрока.
     */
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    /**
     * Получить сумму транзакции.
     *
     * @return Сумма транзакции.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Установить сумму транзакции.
     *
     * @param amount Новая сумма транзакции.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Получить идентификатор транзакции.
     *
     * @return Идентификатор транзакции.
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Установить идентификатор транзакции.
     *
     * @param transactionId Новый идентификатор транзакции.
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
