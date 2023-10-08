package com.wallet.audit;

public class ConsoleAudit implements Audit {
    /**
     * Регистрирует действие пользователя в журнале аудита.
     *
     * @param username Имя пользователя, совершившего действие.
     * @param action   Описание совершенного действия.
     */
    @Override
    public void log(String username, String action) {
        System.out.println("User: " + username + " - Action: " + action);
    }
}
