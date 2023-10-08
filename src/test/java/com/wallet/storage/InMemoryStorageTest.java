package com.wallet.storage;

import static org.junit.Assert.*;

import com.wallet.domain.Player;
import org.junit.Before;
import org.junit.Test;

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
    public void testGetPlayer_NonExistentUser() {
        Player retrievedPlayer = storage.getPlayer("nonExistentUser");
        assertNull(retrievedPlayer);
    }
}

