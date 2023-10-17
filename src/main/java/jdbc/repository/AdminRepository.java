package jdbc.repository;

import com.wallet.domain.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminRepository {
    private Connection connection;

    public AdminRepository(Connection connection) {
        this.connection = connection;
    }

    public void createAdmin(Admin admin) {
        String insertQuery = "INSERT INTO wallet.admins (username, password) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, admin.getUsername());
            preparedStatement.setString(2, admin.getPassword());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Admin getAdminByUsername(String username) {
        String selectQuery = "SELECT * FROM wallet.admins WHERE username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String adminUsername = resultSet.getString("username");
                    String adminPassword = resultSet.getString("password");
                    return new Admin(adminUsername, adminPassword);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteAdmin(String username) {
        String deleteQuery = "DELETE FROM wallet.admins WHERE username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, username);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

