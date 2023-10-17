package com.wallet.service;

import static org.junit.Assert.*;

import com.wallet.audit.Audit;
import com.wallet.audit.ConsoleAudit;
import com.wallet.domain.Player;
import com.wallet.storage.Storage;
import com.wallet.storage.StorageDB;
import jdbc.connection.DatabaseConnection;
import jdbc.repository.PlayerRepository;

import org.junit.Before;
import org.junit.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class WalletServiceTest {
    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpassword");

    private WalletService walletService;

    @Before
    public void setUp() {
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        // Создайте экземпляры классов Storage и Audit, которые используют настройки контейнера

        Storage storage = new StorageDB(DatabaseConnection.testDatabaseConnection());
        Audit audit = new ConsoleAudit();

        walletService = new WalletService(storage, audit);
    }

    @Test
    public void testRegisterPlayer() {
        Player player = new Player("testPlayer", "testPassword", 100.0, false);
        boolean result = walletService.registerPlayer(player);

        assertTrue(result);
        Player retrievedPlayer = walletService.getPlayerByUsername("testPlayer");
        assertNotNull(retrievedPlayer);
        assertEquals("testPlayer", retrievedPlayer.getUsername());
        assertEquals("testPassword", retrievedPlayer.getPassword());
    }

    @Test
    public void testAuthenticatePlayer_Success() {
        Player player = new Player("testPlayer", "testPassword", 100.0, false);
        walletService.registerPlayer(player);

        boolean result = walletService.authenticatePlayer(new Player("testPlayer", "testPassword", 0.0, false));
        assertTrue(result);
    }

    @Test
    public void testAuthenticatePlayer_Failure_WrongPassword() {
        Player player = new Player("testPlayer", "testPassword", 100.0, false);
        walletService.registerPlayer(player);

        boolean result = walletService.authenticatePlayer(new Player("testPlayer", "wrongPassword", 0.0, false));
        assertFalse(result);
    }

    @Test
    public void testAuthenticatePlayer_Failure_Banned() {
        Player player = new Player("testPlayer", "testPassword", 100.0, true);
        walletService.registerPlayer(player);

        boolean result = walletService.authenticatePlayer(new Player("testPlayer", "testPassword", 0.0, false));
        assertFalse(result);
    }

    @Test
    public void testDebit_Success() {
        Player player = new Player("testPlayer", "testPassword", 100.0, false);
        walletService.registerPlayer(player);

        boolean result = walletService.debit("testPlayer", "txn1", 50.0);
        assertTrue(result);

        double balance = walletService.getPlayerBalance("testPlayer");
        assertEquals(50.0, balance, 0.01);
    }

    @Test
    public void testDebit_Failure_InsufficientBalance() {
        Player player = new Player("testPlayer", "testPassword", 100.0, false);
        walletService.registerPlayer(player);

        boolean result = walletService.debit("testPlayer", "txn1", 150.0);
        assertFalse(result);
    }

    @Test
    public void testDebit_Failure_Banned() {
        Player player = new Player("testPlayer", "testPassword", 100.0, true);
        walletService.registerPlayer(player);

        boolean result = walletService.debit("testPlayer", "txn1", 50.0);
        assertFalse(result);
    }
}
