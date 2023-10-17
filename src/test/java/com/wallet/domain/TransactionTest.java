package com.wallet.domain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TransactionTest {
    private Transaction transaction;

    @Before
    public void setUp() {
        transaction = new Transaction(1, 100.0, "testTransaction");
    }

    @Test
    public void testGetPlayerId() {
        assertEquals(1, transaction.getPlayerId());
    }

    @Test
    public void testSetPlayerId() {
        transaction.setPlayerId(2);
        assertEquals(2, transaction.getPlayerId());
    }

    @Test
    public void testGetAmount() {
        assertEquals(100.0, transaction.getAmount(), 0.01);
    }

    @Test
    public void testSetAmount() {
        transaction.setAmount(200.0);
        assertEquals(200.0, transaction.getAmount(), 0.01);
    }

    @Test
    public void testGetTransactionId() {
        assertEquals("testTransaction", transaction.getTransactionId());
    }

    @Test
    public void testSetTransactionId() {
        transaction.setTransactionId("newTransaction");
        assertEquals("newTransaction", transaction.getTransactionId());
    }
}
