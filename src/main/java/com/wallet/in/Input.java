package com.wallet.in;

import com.wallet.domain.Admin;
import com.wallet.domain.Player;

import java.util.Scanner;

public class Input {
    private Scanner ownscanner;

    public Input(Scanner GivenScanner) {
        ownscanner = GivenScanner;
    }

    public int getIntInput(String prompt) {
        System.out.print(prompt);

        return ownscanner.nextInt();
    }

    public double getDoubleInput(String prompt) {
        System.out.print(prompt);
        return ownscanner.nextDouble();
    }

    public String getStringInput(String prompt) {
        System.out.print(prompt);
        ownscanner.nextLine(); // Очистка буфера после ввода числа
        return ownscanner.nextLine();
    }

    public Player InputPlayer(Scanner scanner){
        System.out.print("Введите имя пользователя: ");
        String Username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String Password = scanner.nextLine();
        return new Player(Username, Password);
    }

    public String InputUsername(Scanner scanner){
        System.out.print("Введите имя пользователя: ");
        return scanner.nextLine();
    }
    public Admin InputAdmin(Scanner scanner){
        System.out.print("Введите имя администратора: ");
        String adminUsername = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String adminPassword = scanner.nextLine();
        return new Admin(adminUsername,adminPassword);
    }


}

