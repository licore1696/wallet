package jdbc.repository;

import com.wallet.domain.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    private Connection connection;

    public TransactionRepository(Connection connection) {
        this.connection = connection;
    }

    public void createTransaction(Transaction transaction) {
        try {
            String query = "INSERT INTO wallet.transactions (transaction_id, player_id, amount) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, transaction.getTransactionId());
            preparedStatement.setInt(2, transaction.getPlayerId());
            preparedStatement.setDouble(3, transaction.getAmount());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Вставка транзакции не удалась.");
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                // Обработка случая, когда transaction_id не уникальный
                System.out.println("Идентификатор транзакции не уникальный.");
            } else {
                e.printStackTrace();
            }
        }
    }


    public List<Transaction> getTransactionsForPlayer(int playerId) {
        List<Transaction> transactions = new ArrayList<>();

        try {
            String query = "SELECT  transaction_id, amount FROM wallet.transactions WHERE player_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, playerId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                String transactionId = resultSet.getString("transaction_id");
                double amount = resultSet.getDouble("amount");


                Transaction transaction = new Transaction(transactionId, amount);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public boolean isTransactionIdUnique( String transactionId) {
        try {
            String query = "SELECT COUNT(*) FROM wallet.transactions WHERE transaction_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, transactionId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0; // Если count равен 0, идентификатор транзакции уникален
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

}
