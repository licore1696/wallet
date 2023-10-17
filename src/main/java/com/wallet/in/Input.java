package com.wallet.in;

import com.wallet.domain.Admin;
import com.wallet.domain.Player;

import java.util.Scanner;

/**
 * Класс, представляющий ввод данных в системе кошельков.
 */
public class Input {
    private Scanner ownScanner;

    /**
     * Конструктор для создания объекта класса Input с использованием заданного объекта Scanner.
     *
     * @param GivenScanner Объект Scanner для ввода данных.
     */
    public Input(Scanner GivenScanner) {
        ownScanner = GivenScanner;
    }

    /**
     * Получить целое число, запрашивая ввод с заданным приглашением.
     *
     * @param prompt Приглашение для ввода.
     * @return Введенное целое число.
     */
    public int getIntInput(String prompt) {
        System.out.print(prompt);
        return ownScanner.nextInt();
    }

    /**
     * Получить целое число без приглашения.
     *
     * @return Введенное целое число.
     */
    public int getIntInput() {
        return ownScanner.nextInt();
    }

    /**
     * Получить число с плавающей запятой, запрашивая ввод с заданным приглашением.
     *
     * @param scanner Поток ввода.
     * @return Введенное число с плавающей запятой.
     */
    public double inputAmount(Scanner scanner) {
        System.out.print("Введите сумму: ");
        return scanner.nextDouble();
    }

    /**
     * Получить строку, запрашивая ввод с заданным приглашением.
     *
     * @param prompt Приглашение для ввода.
     * @return Введенная строка.
     */
    public String getStringInput(String prompt) {
        System.out.print(prompt);
        ownScanner.nextLine(); // Очистка буфера после ввода числа
        return ownScanner.nextLine();
    }

    /**
     * Получить строку без использования буфера после числового ввода.
     *
     * @param promt Приглашение для ввода.
     * @return Введенная строка.
     */
    public String getStringInputNoBuff(String promt) {
        System.out.print(promt);
        return ownScanner.nextLine();
    }

    /**
     * Получить данные игрока, запрашивая имя пользователя и пароль.
     *
     * @param scanner Объект Scanner для ввода данных.
     * @return Объект Player с введенными данными.
     */
    public Player inputPlayer(Scanner scanner) {
        System.out.print("Введите имя пользователя: ");
        String Username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String Password = scanner.nextLine();
        return new Player(Username, Password, 0, false);
    }

    /**
     * Получить имя пользователя, запрашивая его ввод.
     *
     * @param scanner Объект Scanner для ввода данных.
     * @return Введенное имя пользователя.
     */
    public String inputUsername(Scanner scanner) {
        System.out.print("Введите имя пользователя: ");
        return scanner.nextLine();
    }

    /**
     * Получить данные администратора, запрашивая имя администратора и пароль.
     *
     * @param scanner Объект Scanner для ввода данных.
     * @return Объект Admin с введенными данными.
     */
    public Admin InputAdmin(Scanner scanner) {
        System.out.print("Введите имя администратора: ");
        String adminUsername = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String adminPassword = scanner.nextLine();
        return new Admin(adminUsername, adminPassword);
    }

    public String inputTransactionId(Scanner scanner) {
        System.out.print("Введите id транзакции: ");
        return scanner.nextLine();
    }
}
