package com.rkfcl.server_info.Manager;

import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
    public String getPlayerJob(UUID playerUUID) {
        String job = null;

        try (PreparedStatement statement = connection.prepareStatement("SELECT job FROM player_data WHERE player_uuid = ?")) {
            statement.setString(1, playerUUID.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                job = resultSet.getString("job");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return job;
    }

    public void setPlayerJob(UUID playerUUID, String job) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO player_data (player_uuid, job) VALUES (?, ?) ON DUPLICATE KEY UPDATE job = ?")) {
            statement.setString(1, playerUUID.toString());
            statement.setString(2, job);
            statement.setString(3, job);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultPlayerJob(UUID playerUUID) {
        setPlayerJob(playerUUID, "초보자");
    }
}
