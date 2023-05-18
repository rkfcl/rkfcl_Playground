package com.rkfcl.server_info.Manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DatabaseManager {
    private Map<Player, Integer> moneyMap = new HashMap<>();
    private Connection connection;
    public void initializeDatabase() {
        // 데이터베이스 연결 설정
        String host = "localhost";
        String port = "3306";
        String database = "rkfcl_server";
        String username = "rkfcl";
        String password = "haking767@";

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            createTable(); // 테이블 생성

            // 이벤트 리스너 등록 및 명령어 등록
        } catch (SQLException e) {
            e.printStackTrace();
            // 데이터베이스 연결 실패 처리
        }
    }
    public void closeDatabase() {
        // 데이터베이스 연결 종료
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    private void createTable() {
        try (Statement statement = connection.createStatement()) {
            // 돈 테이블 생성 쿼리 실행
            String createMoneyTableQuery = "CREATE TABLE IF NOT EXISTS money (player_uuid VARCHAR(36) PRIMARY KEY, amount INT)";
            statement.executeUpdate(createMoneyTableQuery);
            // 직업 테이블 생성 쿼리 실행
            String createPlayerDataTableQuery = "CREATE TABLE IF NOT EXISTS player_data (player_uuid VARCHAR(36) PRIMARY KEY, job VARCHAR(255))";
            statement.executeUpdate(createPlayerDataTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            // 테이블 생성 실패 처리
        }
    }

    // 다른 데이터베이스 관련 메서드들...

    public DatabaseManager(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public String getPlayerJob(Player player) {
        String job = null;

        try (PreparedStatement statement = connection.prepareStatement("SELECT job FROM player_data WHERE player_uuid = ?")) {
            statement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                job = resultSet.getString("job");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return job != null ? job : "초보자"; // 직업 정보가 없을 경우 "초보자"로 설정
    }

    public void setPlayerJob(Player player, String job) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO player_data (player_uuid, job) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE job = ?")) {
            statement.setString(1, player.getUniqueId().toString());
            statement.setString(2, job);
            statement.setString(3, job);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // 직업 정보 업데이트 실패 처리
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
    public void increaseMoney(Player player, int amount) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE money SET amount = amount + ? WHERE player_uuid = ?")) {
            statement.setInt(1, amount);
            statement.setString(2, player.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void decreaseMoney(Player player, int amount) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE money SET amount = amount - ? WHERE player_uuid = ?")) {
            statement.setInt(1, amount);
            statement.setString(2, player.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
