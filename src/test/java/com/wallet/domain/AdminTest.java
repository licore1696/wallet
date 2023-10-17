package com.wallet.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AdminTest {
    private Admin admin;

    @Before
    public void setUp() {
        admin = new Admin("testAdmin", "testPassword");
    }

    @Test
    public void testGetUsername() {
        assertEquals("testAdmin", admin.getUsername());
    }

    @Test
    public void testSetUsername() {
        admin.setUsername("newAdmin");
        assertEquals("newAdmin", admin.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("testPassword", admin.getPassword());
    }

    @Test
    public void testSetPassword() {
        admin.setPassword("newPassword");
        assertEquals("newPassword", admin.getPassword());
    }
}
