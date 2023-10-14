package com.wallet.ui;

import com.wallet.domain.Player;
import com.wallet.in.Input;
import com.wallet.service.AdminService;
import com.wallet.service.WalletService;
import com.wallet.ui.AdminConsole;
import com.wallet.ui.UserConsole;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Scanner;

public class UserConsoleTest {
    private UserConsole userConsole;

    @Mock
    private WalletService walletService;
    @Mock
    private AdminService adminService;
    @Mock
    private Input input;
    @Mock
    private Scanner scanner;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userConsole = new UserConsole(walletService, adminService, scanner);
        userConsole.input = input;
    }

    @Test
    public void testRegisterPlayer() {
        // Create a player to be used as input
        Player player = new Player("username", "password");

        // Use Mockito to mock the InputPlayer method
        Mockito.when(input.InputPlayer(Mockito.any())).thenReturn(player);

        // Simulate a successful registration
        Mockito.doNothing().when(walletService).registerPlayer(player);

        // Call the method under test
        userConsole.registerPlayer(scanner);

        // Verify that registerPlayer was called with the expected player
        Mockito.verify(walletService).registerPlayer(player);
    }
}
