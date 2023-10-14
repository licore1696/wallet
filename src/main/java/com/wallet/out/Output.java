package com.wallet.out;

import java.util.Scanner;

/**
 * Класс, представляющий вывод данных в системе кошельков.
 */
public class Output {
    /**
     * Конструктор для создания объекта класса Output.
     */
    public Output() {

    }

    /**
     * Отображает меню для пользователей с доступными опциями.
     */
    public void displayUserMenu() {
        System.out.println("1. Регистрация игрока");
        System.out.println("2. Авторизация игрока");
        System.out.println("3. Проверка баланса игрока");
        System.out.println("4. Дебет");
        System.out.println("5. Кредит");
        System.out.println("6. История транзакций");
        System.out.println("7. Авторизация администратора");
        System.out.println("0. Выход");
    }

    /**
     * Отображает меню для администратора с доступными опциями.
     */
    public void displayAdminMenu() {
        displayMessage("Меню администратора: ");
        displayMessage("1. Регистрация администратора ");
        displayMessage("2. Блокировка игрока");
        displayMessage("3. Разблокировка игрока");
        displayMessage("4. Список всех игроков");
        displayMessage("5. Список заблокированных игроков");
        displayMessage("0. Выход из меню администратора");
    }

    /**
     * Отображает заданное сообщение.
     *
     * @param message Сообщение для отображения.
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }
}
