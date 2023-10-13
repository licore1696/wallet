package com.wallet.out;

import java.util.Scanner;

public class Output {
    Output(Scanner scanner){

    }
    public void displayMainMenu() {
        System.out.println("1. Регистрация игрока");
        System.out.println("2. Авторизация игрока");
        System.out.println("3. Проверка баланса игрока");
        System.out.println("4. Дебет");
        System.out.println("5. Кредит");
        System.out.println("6. История транзакций");
        System.out.println("7. Авторизация администратора");
        System.out.println("0. Выход");
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
