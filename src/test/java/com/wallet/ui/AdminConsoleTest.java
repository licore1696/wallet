package com.wallet.ui;

import com.wallet.audit.Audit;
import com.wallet.domain.Admin;
import com.wallet.domain.Player;
import com.wallet.in.Input;
import com.wallet.out.Output;
import com.wallet.service.AdminService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class AdminConsoleTest {
    private AdminConsole adminConsole;

    @Mock
    private Input input;
    @Mock
    private Output output;
    @Mock
    private AdminService adminService;
    @Mock
    private Audit audit;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        adminConsole = new AdminConsole(input, adminService, audit);
        adminConsole.setOutput(output);
    }

    @Test
    public void testListPlayers() {

        List<Player> players = new ArrayList<>();
        players.add(new Player("player1", "password1"));
        players.add(new Player("player2", "password2"));
        Mockito.when(adminService.getPlayers()).thenReturn(players);
        adminConsole.setAdmin(new Admin("admin", "admin"));


        adminConsole.listPlayers();


        for (Player player : players) {
            Mockito.verify(output).displayMessage("Имя игрока: " + player.getUsername());
        }
    }

    @Test
    public void testListBannedPlayers() {

        List<Player> players = new ArrayList<>();
        players.add(new Player("player1", "password1"));
        players.get(0).setStatusBan(true);
        players.add(new Player("player2", "password2"));
        Mockito.when(adminService.getBannedPlayers()).thenReturn(players);
        adminConsole.setAdmin(new Admin("admin", "admin"));


        adminConsole.listBannedPlayers();


        for (Player player : players) {
            if (player.getStatusBan()) {
                Mockito.verify(output).displayMessage("Заблокированный игрок: " + player.getUsername());
            }
        }
    }

    @Test
    public void testRegisterAdmin() {
        Mockito.when(input.getStringInput("Введите имя администратора: ")).thenReturn("newAdmin");
        Mockito.when(input.getStringInputNoBuff("Введите пароль: ")).thenReturn("password");
        Mockito.doNothing().when(adminService).registerAdmin("newAdmin", "password");

        adminConsole.registerAdmin();

        Mockito.verify(output).displayMessage("Регистрация нового администратора");
        Mockito.verify(adminService).registerAdmin("newAdmin", "password");
    }

    @Test
    public void testBanPlayer() {
        Mockito.when(adminService.banPlayer("adminName", "playerToBan")).thenReturn(true);
        Mockito.when(input.getStringInputNoBuff("Введите имя игрока, что бы заблокировать: ")).thenReturn("playerToBan");
        adminConsole = new AdminConsole(input, adminService, audit);
        adminConsole.setOutput(output);
        adminConsole.setAdmin(new Admin("adminName", "adminPassword"));

        adminConsole.banPlayer();

        Mockito.verify(output).displayMessage("Игрок заблокирован успешно.");
        Mockito.verify(audit).log("adminName", "Блокировка: playerToBan");
    }

    @Test
    public void testUnbanPlayer() {
        Mockito.when(adminService.unbanPlayer("adminName", "playerToUnban")).thenReturn(true);
        Mockito.when(input.getStringInputNoBuff("Введите имя игрока для разблокировки: ")).thenReturn("playerToUnban");
        adminConsole = new AdminConsole(input, adminService, audit);
        adminConsole.setOutput(output);
        adminConsole.setAdmin(new Admin("adminName", "adminPassword"));

        adminConsole.unbanPlayer();

        Mockito.verify(output).displayMessage("Игрок разблокирован успешно.");
        Mockito.verify(audit).log("adminName", "Разблокировка: playerToUnban");
    }
}
