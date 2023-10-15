package com.wallet.service;

import com.wallet.audit.Audit;
import com.wallet.domain.Admin;
import com.wallet.domain.Player;
import com.wallet.storage.Storage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

public class AdminServiceTest {
    private AdminService adminService;

    @Mock
    private Storage storage;
    @Mock
    private Audit audit;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        adminService = new AdminService(storage, audit);
    }



    @Test
    public void testRegisterAdmin() {
        // Arrange
        Mockito.when(storage.getAdmin("newAdmin")).thenReturn(null);
        Mockito.doNothing().when(storage).addAdmin(Mockito.any(Admin.class));

        // Act
        adminService.registerAdmin("newAdmin", "password");

        // Assert
        Mockito.verify(storage).getAdmin("newAdmin");
        Mockito.verify(audit).log("newAdmin", "Регистрация администратора");
    }



    @Test
    public void testAuthenticateAdmin() {
        // Arrange
        Admin existingAdmin = new Admin("existingAdmin", "password");
        Mockito.when(storage.getAdmin("existingAdmin")).thenReturn(existingAdmin);

        // Act & Assert
        // Проверяем успешную аутентификацию
        assert adminService.authenticateAdmin(new Admin("existingAdmin", "password"));
        // Проверяем неуспешную аутентификацию
        assert !adminService.authenticateAdmin(new Admin("existingAdmin", "wrongPassword"));
        assert !adminService.authenticateAdmin(new Admin("nonExistingAdmin", "password"));
    }

    @Test
    public void testBanPlayer() {
        // Arrange
        Admin admin = new Admin("adminName", "adminPassword");
        Player playerToBan = new Player("playerToBan", "password");
        Mockito.when(storage.getAdmin("adminName")).thenReturn(admin);
        Mockito.when(storage.getPlayer("playerToBan")).thenReturn(playerToBan);

        // Act
        boolean result = adminService.banPlayer("adminName", "playerToBan");

        // Assert
        assert result;
        assert playerToBan.getStatusBan();
    }

    @Test
    public void testUnbanPlayer() {
        // Arrange
        Admin admin = new Admin("adminName", "adminPassword");
        Player playerToUnban = new Player("playerToUnban", "password");
        playerToUnban.setStatusBan(true);
        Mockito.when(storage.getAdmin("adminName")).thenReturn(admin);
        Mockito.when(storage.getPlayer("playerToUnban")).thenReturn(playerToUnban);

        // Act
        boolean result = adminService.unbanPlayer("adminName", "playerToUnban");

        // Assert
        assert result;
        assert !playerToUnban.getStatusBan();
    }

    @Test
    public void testGetPlayers() {
        // Arrange
        List<Player> players = new ArrayList<>();
        players.add(new Player("player1", "password1"));
        players.add(new Player("player2", "password2"));
        Mockito.when(storage.getAllPlayers()).thenReturn(players);

        // Act
        List<Player> result = adminService.getPlayers();

        // Assert
        assert result.size() == 2;
        assert result.containsAll(players);
    }

    @Test
    public void testGetBannedPlayers() {
        // Arrange
        List<Player> players = new ArrayList<>();
        players.add(new Player("player1", "password1"));
        players.get(0).setStatusBan(true);
        players.add(new Player("player2", "password2"));
        Mockito.when(storage.getAllPlayers()).thenReturn(players);

        // Act
        List<Player> result = adminService.getBannedPlayers();

        // Assert
        assert result.size() == 1;
        assert result.contains(players.get(0));
    }
}
