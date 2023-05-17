package com.rkfcl.server_info.Manager;

import com.rkfcl.server_info.Manager.DatabaseManager;
import com.rkfcl.server_info.test;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerManager {
    private final DatabaseManager databaseManager;

    public PlayerManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void setJob(Player player, String job) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE player_data SET job = ? WHERE player_uuid = ?")) {
            statement.setString(1, job);
            statement.setString(2, player.getUniqueId().toString());
            statement.executeUpdate();

            // 스코어보드 업데이트
            test pluginInstance = (test) Bukkit.getPluginManager().getPlugin("server_info");
            pluginInstance.updateScoreboard(player);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getJob(Player player) {
        String job = null;

        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT job FROM player_data WHERE player_uuid = ?")) {
            statement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                job = resultSet.getString("job");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return job;
    }


    public void setDefaultJob(Player player) {
        setJob(player, "초보자");
    }
}
