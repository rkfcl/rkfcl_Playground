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
            loadMoneyMap(); // 데이터베이스에서 HashMap 로드

            // 이벤트 리스너 등록 및 명령어 등록
        } catch (SQLException e) {
            e.printStackTrace();
            // 데이터베이스 연결 실패 처리
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

    private void loadMoneyMap() {
        try (Statement statement = connection.createStatement()) {
            // 데이터베이스에서 모든 플레이어의 돈 정보 가져오기
            String query = "SELECT * FROM money";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String playerUUID = resultSet.getString("player_uuid");
                int amount = resultSet.getInt("amount");
                // HashMap에 플레이어와 돈 정보 추가
                moneyMap.put(Bukkit.getPlayer(UUID.fromString(playerUUID)), amount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 데이터 로드 실패 처리
        }
    }

    // 다른 데이터베이스 관련 메서드들...

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
