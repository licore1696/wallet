package com.wallet.domain;

import com.wallet.domain.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AdminTest {
    private Admin admin;

    @BeforeEach
    public void setUp() {
        admin = new Admin("admin", "password");
    }

    @Test
    public void testGetUsername() {
        String username = admin.getUsername();
        assertEquals("admin", username);
    }

    @Test
    public void testGetPassword() {
        String password = admin.getPassword();
        assertEquals("password", password);
    }

    @Test
    public void testUsernameNotEquals() {
        String username = admin.getUsername();
        assertNotEquals("otherAdmin", username);
    }

    @Test
    public void testPasswordNotEquals() {
        String password = admin.getPassword();
        assertNotEquals("otherPassword", password);
    }
}
