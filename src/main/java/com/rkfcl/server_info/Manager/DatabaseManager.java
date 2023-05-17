package com.rkfcl.server_info.Manager;

import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
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

        return job != null ? job : "초보자"; // 직업 정보가 없을 경우 "초보자"로 설정
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

    public int getPlayerMoney(Player player) {
        int money = 0;
        try (PreparedStatement statement = connection.prepareStatement("SELECT amount FROM money WHERE player_uuid = ?")) {
            statement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                money = resultSet.getInt("amount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 데이터베이스 조회 실패 처리
        }
        return money;
    }

    public void setPlayerMoney(Player player, int amount) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE money SET amount = ? WHERE player_uuid = ?")) {
            statement.setInt(1, amount);
            statement.setString(2, player.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void increasePlayerMoney(Player player, int amount) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE money SET amount = amount + ? WHERE player_uuid = ?")) {
            statement.setInt(1, amount);
            statement.setString(2, player.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void decreasePlayerMoney(Player player, int amount) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE money SET amount = amount - ? WHERE player_uuid = ?")) {
            statement.setInt(1, amount);
            statement.setString(2, player.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePlayerMoney(Player player, int amount) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO money (player_uuid, amount) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE amount = ?")) {
            statement.setString(1, player.getUniqueId().toString());
            statement.setInt(2, amount);
            statement.setInt(3, amount);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // 데이터베이스 업데이트 실패 처리
        }
    }

    public void createTables() {
        try (PreparedStatement statement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS player_data (player_uuid VARCHAR(36) PRIMARY KEY, job VARCHAR(255))")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS money (player_uuid VARCHAR(36) PRIMARY KEY, amount INT)")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadMoneyMap(Map<UUID, Integer> moneyMap) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM money")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UUID playerUUID = UUID.fromString(resultSet.getString("player_uuid"));
                int amount = resultSet.getInt("amount");
                moneyMap.put(playerUUID, amount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveMoneyMap(Map<UUID, Integer> moneyMap) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO money (player_uuid, amount) VALUES (?, ?) ON DUPLICATE KEY UPDATE amount = ?")) {
            for (Map.Entry<UUID, Integer> entry : moneyMap.entrySet()) {
                UUID playerUUID = entry.getKey();
                int amount = entry.getValue();
                statement.setString(1, playerUUID.toString());
                statement.setInt(2, amount);
                statement.setInt(3, amount);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

