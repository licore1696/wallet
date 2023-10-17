package com.wallet.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    private Player player;

    @Before
    public void setUp() {
        player = new Player(1, "testPlayer", "testPassword", 100.0, false);
    }

    @Test
    public void testGetId() {
        assertEquals(1, player.getId());
    }

    @Test
    public void testSetId() {
        player.setId(2);
        assertEquals(2, player.getId());
    }

    @Test
    public void testGetUsername() {
        assertEquals("testPlayer", player.getUsername());
    }

    @Test
    public void testSetUsername() {
        player.setUsername("newPlayer");
        assertEquals("newPlayer", player.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("testPassword", player.getPassword());
    }

    @Test
    public void testSetPassword() {
        player.setPassword("newPassword");
        assertEquals("newPassword", player.getPassword());
    }

    @Test
    public void testGetBalance() {
        assertEquals(100.0, player.getBalance(), 0.01);
    }

    @Test
    public void testSetBalance() {
        player.setBalance(200.0);
        assertEquals(200.0, player.getBalance(), 0.01);
    }

    @Test
    public void testIsStatusBan() {
        assertFalse(player.isStatusBan());
    }

    @Test
    public void testSetStatusBan() {
        player.setStatusBan(true);
        assertTrue(player.isStatusBan());
    }
}
