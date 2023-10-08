package com.wallet.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player player;

    @Before
    public void setUp() {
        player = new Player("testUser", "testPassword");
    }

    @Test
    public void testGetUsername() {
        assertEquals("testUser", player.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("testPassword", player.getPassword());
    }

    @Test
    public void testGetBalance() {
        assertEquals(0.0, player.getBalance(), 0.01);
    }

    @Test
    public void testSetBalance() {
        player.setBalance(50.0);
        assertEquals(50.0, player.getBalance(), 0.01);
    }

    @Test
    public void testAddTransaction() {
        Transaction transaction = new Transaction("123", 50.0);
        player.addTransaction(transaction);

        assertEquals(1, player.getTransactionHistory().size());
        assertTrue(player.getTransactionHistory().contains(transaction));
    }

}
