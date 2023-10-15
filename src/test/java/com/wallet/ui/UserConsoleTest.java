package com.wallet.ui;

import com.wallet.audit.Audit;
import com.wallet.domain.Player;
import com.wallet.domain.Transaction;
import com.wallet.in.Input;
import com.wallet.out.Output;
import com.wallet.service.WalletService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserConsoleTest {
    private UserConsole userConsole;

    @Mock
    private WalletService walletService;
    @Mock
    private AdminConsole adminConsole;
    @Mock
    private Input input;
    @Mock
    private Audit audit;
    @Mock
    private Output output;
    @Mock
    private Scanner scanner;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userConsole = new UserConsole(walletService, null, scanner, audit);
        userConsole.input = input;
        userConsole.output = output;
    }

    @Test
    public void testRegisterPlayer() {
        Player player = new Player("username", "password");

        Mockito.when(input.InputPlayer(Mockito.any())).thenReturn(player);

        userConsole.registerPlayer(scanner);

        Mockito.verify(walletService).registerPlayer(player);
    }

    @Test
    public void testAuthenticatePlayer_Success() {
        Player player = new Player("username", "password");

        Mockito.when(input.InputPlayer(Mockito.any())).thenReturn(player);
        Mockito.when(walletService.authenticatePlayer(player)).thenReturn(true);

        userConsole.authenticatePlayer(scanner);

        Mockito.verify(output).displayMessage("Аутентификация успешная.");
    }

    @Test
    public void testAuthenticatePlayer_Failure() {
        Player player = new Player("username", "password");

        Mockito.when(input.InputPlayer(Mockito.any())).thenReturn(player);
        Mockito.when(walletService.authenticatePlayer(player)).thenReturn(false);

        userConsole.authenticatePlayer(scanner);

        Mockito.verify(output).displayMessage("Аутентификация не удалась.");
    }

    @Test
    public void testCheckPlayerBalance() {
        String username = "username";
        double balance = 100.0;

        Mockito.when(input.InputUsername(Mockito.any())).thenReturn(username);
        Mockito.when(walletService.getPlayerBalance(username)).thenReturn(balance);

        userConsole.checkPlayerBalance(scanner);

        Mockito.verify(output).displayMessage("Баланс: " + balance);
        Mockito.verify(audit).log(username, "Проверка баланса");
    }

    @Test
    public void testDebit() {
        String username = "username";
        String transactionId = "txn123";
        double amount = 50.0;

        Mockito.when(input.InputUsername(scanner)).thenReturn(username);
        Mockito.when(input.getStringInput(Mockito.anyString())).thenReturn(transactionId);
        Mockito.when(input.getDoubleInput(Mockito.anyString())).thenReturn(amount);

        userConsole.debit(scanner);

        Mockito.verify(walletService).debit(username, transactionId, amount);

    }

    @Test
    public void testCredit() {
        String username = "username";
        String transactionId = "txn123";
        double amount = 50.0;

        Mockito.when(input.InputUsername(scanner)).thenReturn(username);
        Mockito.when(input.getStringInputNoBuff(Mockito.anyString())).thenReturn(transactionId);
        Mockito.when(input.getDoubleInput(Mockito.anyString())).thenReturn(amount);


        userConsole.credit(scanner);

        Mockito.verify(walletService).credit(username, transactionId, amount);

    }

    @Test
    public void testDisplayTransactionHistory() {
        String username = "username";
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("txn1", 50.0));
        transactions.add(new Transaction("txn2", 75.0));

        Mockito.when(input.InputUsername(Mockito.any())).thenReturn(username);
        Mockito.when(walletService.getPlayerTransactionHistory(username)).thenReturn(transactions);

        userConsole.displayTransactionHistory(scanner);

        for (Transaction transaction : transactions) {
            Mockito.verify(output).displayMessage("Идентификатор транзакции: " + transaction.getTransactionId() + ", Сумма: " + transaction.getAmount());
        }

        Mockito.verify(audit).log(username, "История транзакций");
    }
}
