package com.wallet.domain;

/**
 * Класс, представляющий администратора в системе кошельков.
 */
public class Admin {
    private String username;
    private String password;

    /**
     * Конструктор для создания объекта администратора с указанными данными.
     *
     * @param username Имя пользователя администратора.
     * @param password Пароль администратора.
     */
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
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
     * Получить пароль администратора.
     *
     * @return Пароль администратора.
     */
    public String getPassword() {
        return password;
    }
}
