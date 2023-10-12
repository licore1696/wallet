package com.wallet.in;

import java.util.Scanner;

public class InputConsole {
    private Scanner scanner;

    public InputConsole(Scanner scanner) {
        this.scanner = scanner;
    }

    public int getIntInput() {
        return scanner.nextInt();
    }

    public double getDoubleInput() {
        return scanner.nextDouble();
    }

    public String getStringInput() {
        return scanner.nextLine();
    }
}

