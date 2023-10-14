package com.wallet.storage;

import static org.junit.Assert.*;

import com.wallet.domain.Admin;
import com.wallet.domain.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class InMemoryStorageTest {

    private InMemoryStorage storage;

    @Before
    public void setUp() {
        storage = new InMemoryStorage();
    }

    @Test
    public void testAddPlayerAndGetPlayer() {
        Player player = new Player("testUser", "password");
        storage.addPlayer(player);

        Player retrievedPlayer = storage.getPlayer("testUser");

        assertNotNull(retrievedPlayer);
        assertEquals(player, retrievedPlayer);
    }

    @Test
    public void testUpdatePlayer() {
        Player player = new Player("testUser", "password");
        storage.addPlayer(player);

        Player updatedPlayer = new Player("testUser", "newPassword");
        storage.updatePlayer(updatedPlayer);

        Player retrievedPlayer = storage.getPlayer("testUser");

        assertNotNull(retrievedPlayer);
        assertEquals(updatedPlayer.getPassword(), retrievedPlayer.getPassword());
    }

    @Test
    public void testAddAdmin() {
        Admin admin = new Admin("adminUser", "adminPassword");
        storage.addAdmin(admin);

        Admin retrievedAdmin = storage.getAdmin("adminUser");
        assertNotNull(retrievedAdmin);
        assertEquals("adminUser", retrievedAdmin.getUsername());
    }

    @Test
    public void testGetAllPlayers() {
        Player player1 = new Player("user1", "password1");
        Player player2 = new Player("user2", "password2");

        storage.addPlayer(player1);
        storage.addPlayer(player2);

        List<Player> allPlayers = storage.getAllPlayers();
        assertNotNull(allPlayers);
        assertEquals(2, allPlayers.size());
    }

    @Test
    public void testGetAdmin() {
        Admin admin = new Admin("adminUser", "adminPassword");
        storage.addAdmin(admin);

        Admin retrievedAdmin = storage.getAdmin("adminUser");
        assertNotNull(retrievedAdmin);
        assertEquals("adminUser", retrievedAdmin.getUsername());
    }

    @Test
    public void testGetPlayer_NonExistentUser() {
        Player retrievedPlayer = storage.getPlayer("nonExistentUser");
        assertNull(retrievedPlayer);
    }

    @Test
    public void testGetNonExistentAdmin() {
        Admin admin = storage.getAdmin("nonExistentAdmin");
        assertNull(admin);
    }
}

