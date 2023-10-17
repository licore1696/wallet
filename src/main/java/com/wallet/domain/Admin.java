package com.wallet.domain;

/**
 * Класс, представляющий администратора в системе кошельков.
 */
public class Admin {
    private int id;
    private String username;
    private String password;

    /**
     * Конструктор для создания объекта Admin.
     *
     * @param username Имя пользователя администратора.
     * @param password Пароль администратора.
     */
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Получить идентификатор администратора.
     *
     * @return Идентификатор администратора.
     */
    public int getId() {
        return id;
    }

    /**
     * Получить имя пользователя администратора.
     *
     * @return Имя пользователя администратора.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Установить имя пользователя администратора.
     *
     * @param username Новое имя пользователя администратора.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Получить пароль администратора.
     *
     * @return Пароль администратора.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Установить пароль администратора.
     *
     * @param password Новый пароль администратора.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

