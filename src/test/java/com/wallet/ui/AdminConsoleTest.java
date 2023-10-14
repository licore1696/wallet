package com.wallet.ui;

import com.wallet.domain.Admin;
import com.wallet.service.AdminService;
import com.wallet.in.Input;
import com.wallet.out.Output;

import com.wallet.ui.AdminConsole;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Scanner;

public class AdminConsoleTest {

    private AdminConsole adminConsole;

    @Mock
    private Input input;
    @Mock
    private AdminService adminService;
    @Mock
    private Output output;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        adminConsole = new AdminConsole(input, adminService);
        adminConsole.setOutput(output);
    }

    @Test
    public void testRegisterAdmin() {
        // Имитируем ввод админа
        Admin admin = new Admin("admin", "admin");
        Mockito.when(input.InputAdmin(Mockito.any(Scanner.class))).thenReturn(admin);

        // Имитируем аутентификацию админа
        Mockito.when(adminService.authenticateAdmin(admin)).thenReturn(true);

        // Имитируем ввод данных для нового админа
        Mockito.when(input.getStringInput(Mockito.eq("Введите имя администратора:"))).thenReturn("newAdminUsername");
        Mockito.when(input.getStringInputNoBuff(Mockito.eq("Введите пароль:"))).thenReturn("newAdminPassword");

        // Имитируем успешное выполнение registerAdmin
        Mockito.doNothing().when(adminService).registerAdmin("newAdminUsername", "newAdminPassword");

        adminConsole.startAdminConsole(new Scanner(System.in));

        // Проверка, что метод displayMessage был вызван
        Mockito.verify(output).displayMessage("Успешная регистрация администратора.");
    }
}
