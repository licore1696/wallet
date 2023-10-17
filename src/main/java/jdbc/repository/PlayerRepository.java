package jdbc.repository;

import com.wallet.domain.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

public class PlayerRepository {
    private Connection connection;

    public PlayerRepository(Connection connection) {
        this.connection = connection;
    }

    public boolean createPlayer(Player player) {
        try {
            String query = "INSERT INTO wallet.players (username, password, balance, statusban) VALUES (?, ?, ?, ?) RETURNING id";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, player.getUsername());
            preparedStatement.setString(2, player.getPassword());
            preparedStatement.setDouble(3, player.getBalance());
            preparedStatement.setBoolean(4, player.getStatusBan());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int playerId = resultSet.getInt(1);
                player.setId(playerId);
                return true;
            }

        } catch (SQLException e) {
            return  false;

        }
        return false;
    }


    public Player getPlayerByUsername(String username) {
        try {
            String query = "SELECT * FROM wallet.players WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String password = resultSet.getString("password");
                double balance = resultSet.getDouble("balance");
                boolean statusban = resultSet.getBoolean("statusban");
                return new Player(id, username, password, balance, statusban);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updatePlayer(Player player) {
        try {
            String query = "UPDATE wallet.players " +
                    "SET password = ?, balance = ?, statusban = ? " +
                    "WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, player.getPassword());
            preparedStatement.setDouble(2, player.getBalance());
            preparedStatement.setBoolean(3, player.getStatusBan());
            preparedStatement.setString(4, player.getUsername());

            int rowsUpdated = preparedStatement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Player> getBannedPlayers() {
        List<Player> bannedPlayers = new ArrayList<>();
        try {
            String query = "SELECT * FROM wallet.players WHERE statusban = true";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                double balance = resultSet.getDouble("balance");
                boolean statusban = resultSet.getBoolean("statusban");
                Player player = new Player(id, username, password, balance, statusban);
                bannedPlayers.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bannedPlayers;
    }

    public List<Player> getUnBannedPlayers() {
        List<Player> UnBannedPlayers = new ArrayList<>();
        try {
            String query = "SELECT * FROM wallet.players WHERE statusban = false";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                double balance = resultSet.getDouble("balance");
                boolean statusban = resultSet.getBoolean("statusban");
                Player player = new Player(id, username, password, balance, statusban);
                UnBannedPlayers.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return UnBannedPlayers;
    }




}
