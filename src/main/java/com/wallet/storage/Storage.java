package com.wallet.storage;

import com.wallet.domain.Player;
import com.wallet.domain.Admin;
import com.wallet.domain.Transaction;

import java.util.List;

public interface Storage {
    Player getPlayer(String username);

    boolean addPlayer(Player player);

    void updatePlayer(Player player);

    void addTransaction(Transaction transaction);

    List<Transaction> getTransactionsForPlayer(Player player);

    boolean isTransactionIdUnique(String transaction_id);

    Admin getAdmin(String username);

    void addAdmin(Admin admin);

    List<Player> getBannedPlayers();

    List<Player> getUnBannedPlayers();

}
