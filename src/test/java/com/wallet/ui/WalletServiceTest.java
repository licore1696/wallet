package com.wallet.ui;

import com.wallet.audit.Audit;
import com.wallet.domain.Player;
import com.wallet.domain.Transaction;
import com.wallet.service.WalletService;
import com.wallet.storage.Storage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WalletServiceTest {

    @Mock
    private Storage storage;

    @Mock
    private Audit audit;

    private WalletService walletService;

    @Before
    public void setUp() {
        walletService = new WalletService(storage, audit);
    }

    @Test
    public void testRegisterPlayer_Success() {
        String username = "testUser";
        String password = "testPassword";

        when(storage.getPlayer(username)).thenReturn(null);
        Player player = new Player(username, password);
        walletService.registerPlayer(player);

        verify(storage).addPlayer(any(Player.class));
        verify(audit).log(username, "Registration");
    }

    @Test
    public void testRegisterPlayer_Failure_UsernameExists() {
        String username = "existingUser";
        String password = "testPassword";

        when(storage.getPlayer(username)).thenReturn(new Player(username, password));

        Player player = new Player(username, password);
        walletService.registerPlayer(player);

        verify(storage, never()).addPlayer(any(Player.class));
        verify(audit, never()).log(username, "Registration");
    }


    @Test
    public void testDebit_Success() {
        String username = "testUser";
        String transactionId = "123";
        double initialBalance = 100.0;
        double debitAmount = 50.0;

        Player player = new Player(username, "password");
        player.setBalance(initialBalance);

        when(storage.getPlayer(username)).thenReturn(player);


        boolean result = walletService.debit(username, transactionId, debitAmount);

        assertTrue(result);
        assertEquals(initialBalance - debitAmount, player.getBalance(), 0.01);
        verify(audit).log(username, "Debit Transaction");
    }

    @Test
    public void testDebit_Failure_InsufficientBalance() {
        String username = "testUser";
        String transactionId = "123";
        double initialBalance = 30.0; // Недостаточный баланс
        double debitAmount = 50.0;

        Player player = new Player(username, "password");
        player.setBalance(initialBalance);

        when(storage.getPlayer(username)).thenReturn(player);

        boolean result = walletService.debit(username, transactionId, debitAmount);

        assertFalse(result); // Ожидается, что дебет транзакция провалится из-за недостаточного баланса
        assertEquals(initialBalance, player.getBalance(), 0.01); // Баланс не должен измениться
        verify(audit, never()).log(username, "Debit Transaction"); // audit.log не должен быть вызван
    }

    @Test
    public void testDebit_Failure_NonUniqueTransactionId() {
        String username = "testUser";
        String transactionId = "123";
        double initialBalance = 100.0;
        double debitAmount = 50.0;

        Player player = new Player(username, "password");
        player.setBalance(initialBalance);
        Transaction transaction = new Transaction(transactionId, 50.0);
        player.addTransaction(transaction);

        when(storage.getPlayer(username)).thenReturn(player);

        boolean result = walletService.debit(username, transactionId, debitAmount);

        assertFalse(result); // Ожидается, что дебет транзакция провалится из-за неуникального идентификатора транзакции
        assertEquals(initialBalance, player.getBalance(), 0.01); // Баланс не должен измениться
        verify(audit, never()).log(username, "Debit Transaction"); // audit.log не должен быть вызван
    }


    @Test
    public void testCredit_Success() {
        String username = "testUser";
        String transactionId = "123";
        double initialBalance = 100.0;
        double creditAmount = 50.0;

        Player player = new Player(username, "password");
        player.setBalance(initialBalance);

        when(storage.getPlayer(username)).thenReturn(player);

        walletService.credit(username, transactionId, creditAmount);

        assertEquals(initialBalance + creditAmount, player.getBalance(), 0.01);
        verify(audit).log(username, "Credit Transaction");
    }

    @Test
    public void testCredit_NonUniqueTransactionId() {
        String username = "testUser";
        String transactionId = "123";
        double initialBalance = 100.0;
        double creditAmount = 50.0;

        Player player = new Player(username, "password");
        player.setBalance(initialBalance);
        Transaction transaction = new Transaction(transactionId, 50.0);
        player.addTransaction(transaction);

        when(storage.getPlayer(username)).thenReturn(player);

        walletService.credit(username, transactionId, creditAmount);

        assertEquals(initialBalance, player.getBalance(), 0.01); // Баланс не должен измениться
        verify(audit, never()).log(username, "Credit Transaction"); // audit.log не должен быть вызван
    }

    @Test
    public void testCredit_UsernameNotFound() {
        String username = "nonExistentUser";
        String transactionId = "123";
        double creditAmount = 50.0;

        when(storage.getPlayer(username)).thenReturn(null);

        walletService.credit(username, transactionId, creditAmount);

        verify(audit, never()).log(username, "Credit Transaction"); // audit.log не должен быть вызван
    }


    @Test
    public void testIsTransactionIdUnique_UniqueTransactionId() {
        String username = "testUser";
        String transactionId = "123";

        Player player = new Player(username, "password");
        Transaction transaction = new Transaction(transactionId, 50.0);
        player.addTransaction(transaction);

        boolean result = walletService.isTransactionIdUnique(username, transactionId);

        assertFalse(!result);
    }

    @Test
    public void testIsTransactionIdUnique_NonUniqueTransactionId() {
        String username = "testUser";
        String transactionId = "123";

        Player player = new Player(username, "password");

        boolean result = walletService.isTransactionIdUnique(username, transactionId);

        assertTrue(result);
    }
}

