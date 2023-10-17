package com.wallet.domain;

/**
 * Представляет игрока в сервисе кошельков.
 */
public class Player {
    private int id;
    private String username;
    private String password;
    private double balance;
    private boolean statusBan;

    /**
     * Конструктор для создания объекта Player с указанным идентификатором, именем пользователя, паролем, балансом и статусом блокировки.
     *
     * @param id         Идентификатор игрока.
     * @param username   Имя пользователя игрока.
     * @param password   Пароль игрока.
     * @param balance    Баланс игрока.
     * @param statusBan  Статус блокировки игрока (true - заблокирован, false - не заблокирован).
     */
    public Player(int id, String username, String password, double balance, boolean statusBan) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.statusBan = statusBan;
    }

    /**
     * Конструктор для создания объекта Player без указания идентификатора.
     *
     * @param username   Имя пользователя игрока.
     * @param password   Пароль игрока.
     * @param balance    Баланс игрока.
     * @param statusBan  Статус блокировки игрока (true - заблокирован, false - не заблокирован).
     */
    public Player(String username, String password, double balance, boolean statusBan) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.statusBan = statusBan;
    }

    /**
     * Получить идентификатор игрока.
     *
     * @return Идентификатор игрока.
     */
    public int getId() {
        return id;
    }

    /**
     * Установить идентификатор игрока.
     *
     * @param playerId Новый идентификатор игрока.
     */
    public void setId(int playerId) {
        this.id = playerId;
    }

    /**
     * Получить имя пользователя игрока.
     *
     * @return Имя пользователя игрока.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Установить имя пользователя игрока.
     *
     * @param username Новое имя пользователя игрока.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Получить пароль игрока.
     *
     * @return Пароль игрока.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Установить пароль игрока.
     *
     * @param password Новый пароль игрока.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Получить баланс игрока.
     *
     * @return Баланс игрока.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Установить баланс игрока.
     *
     * @param balance Новый баланс игрока.
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Проверить статус блокировки игрока.
     *
     * @return true, если игрок заблокирован, в противном случае - false.
     */
    public boolean isStatusBan() {
        return statusBan;
    }

    /**
     * Установить статус блокировки игрока.
     *
     * @param statusBan Новый статус блокировки игрока (true - заблокирован, false - не заблокирован).
     */
    public void setStatusBan(boolean statusBan) {
        this.statusBan = statusBan;
    }
    /**
     * Установить статус блокировки игрока.
     *
     * @return Статус игрока.
     */
    public boolean getStatusBan() {
        return statusBan;
    }
}
