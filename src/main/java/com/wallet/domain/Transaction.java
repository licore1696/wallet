package com.wallet.domain;
/**
 * Класс, представляющий финансовую транзакцию в системе кошельков.
 */
public class Transaction {
    // Уникальный идентификатор транзакции.
    private String transactionId;

    // Сумма транзакции.
    private double amount;

    /**
     * Конструктор для создания новой транзакции с указанными данными.
     *
     * @param transactionId Уникальный идентификатор транзакции.
     * @param amount       Сумма транзакции.
     */
    public Transaction(String transactionId, double amount) {
        this.transactionId = transactionId;
        this.amount = amount;
    }

    /**
     * Получить идентификатор транзакции
     *
     * @return идентификатор
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Получить сумму транзакции.
     *
     * @return Сумма транзакции.
     */
    public double getAmount() {
        return amount;
    }
}
