package com.wallet.storage;

import com.wallet.domain.Admin;
import com.wallet.domain.Player;
import com.wallet.domain.Transaction;
import jdbc.repository.AdminRepository;
import jdbc.repository.PlayerRepository;
import jdbc.repository.TransactionRepository;

import java.sql.*;
import java.util.List;

public class StorageDB implements Storage {
    private Connection connection;
    private PlayerRepository playerRepository;
    private TransactionRepository transactionRepository;
    private AdminRepository adminRepository;

    public StorageDB(Connection connection) {
        this.connection = connection;
        playerRepository = new PlayerRepository(connection);
        transactionRepository = new TransactionRepository(connection);
        adminRepository = new AdminRepository(connection);
    }

    @Override
    public Player getPlayer(String username) {
        Player player = playerRepository.getPlayerByUsername(username);
        return player;
    }

    @Override
    public boolean addPlayer(Player player) {
           boolean result = playerRepository.createPlayer(player);
           return result;
    }

    @Override
    public void updatePlayer(Player player) {
        playerRepository.updatePlayer(player);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transactionRepository.createTransaction(transaction);
    }

    public List<Transaction> getTransactionsForPlayer(Player player) {
        return transactionRepository.getTransactionsForPlayer(player.getId());
    }

    @Override
    public boolean isTransactionIdUnique(String transaction_id) {
        return transactionRepository.isTransactionIdUnique(transaction_id);
    }

    @Override
    public Admin getAdmin(String username) {
        return adminRepository.getAdminByUsername(username);
    }

    @Override
    public void addAdmin(Admin admin) {
        adminRepository.createAdmin(admin);
    }

    @Override
    public List<Player> getBannedPlayers() {
        return playerRepository.getBannedPlayers();
    }

    @Override
    public List<Player> getUnBannedPlayers() {
        return playerRepository.getUnBannedPlayers();
    }


}
