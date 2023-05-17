package com.rkfcl.server_info;

import com.rkfcl.server_info.Manager.AbilityManager;
import com.rkfcl.server_info.Manager.DatabaseManager;
import com.rkfcl.server_info.Manager.PlayerManager;
import com.rkfcl.server_info.Manager.ShopInventoryManager;
import com.rkfcl.server_info.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class test extends JavaPlugin implements Listener {
    private Scoreboard scoreboard;
    private Objective objective;
    // 플레이어별 소지금을 저장하는 HashMap
    private Map<Player, Integer> moneyMap = new HashMap<>();
    private Connection connection;

    private DatabaseManager databaseManager; // DatabaseManager 인스턴스 추가
    private PlayerManager playerManager; // PlayerManager 인스턴스 추가
    private AbilityManager abilityManager; // AbilityManager 인스턴스 추가
    private onPlayerInteractEntity interactEntity; // onPlayerInteractEntity 인스턴스 추가
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("RPGScoreboard", "dummy", "갈치의 놀이터");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        System.out.println("plugin on");
        getServer().getPluginCommand("메뉴").setExecutor(new menu());
        getServer().getPluginCommand("수표").setExecutor(new givecheck(this));
        getServer().getPluginCommand("setmoney").setExecutor(new opcommand(this));
        getServer().getPluginCommand("setnpcshop").setExecutor(new NPCShopCommand(this));
        getServer().getPluginCommand("rmnpcshop").setExecutor(new NPCShopCommand(this));

        MenuClickListener menuClickListener = new MenuClickListener(databaseManager);
        getServer().getPluginManager().registerEvents(menuClickListener, this);
        playerManager = new PlayerManager(databaseManager); // PlayerManager 인스턴스 생성
        getServer().getPluginManager().registerEvents(new AbilityManager(), this);
        interactEntity = new onPlayerInteractEntity(this); // 수정된 인수 전달
        interactEntity.enableTrade(); // 주민 거래 비활성화

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

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
        saveMoneyMap(); // HashMap을 데이터베이스에 저장
        // 데이터베이스 연결 종료
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("plugin off");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setScoreboard(scoreboard);
        if (!moneyMap.containsKey(player)) {
            moneyMap.put(player, 0); // 초기 소지금을 0으로 설정
        }
        updateScoreboard(player);
    }



    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());

    }


    public void updateScoreboard(Player player) {
        for (String entry : scoreboard.getEntries()) {
            if (entry.startsWith("소지금: ")) {
                scoreboard.resetScores(entry);
            }
        }
        for (String entry : scoreboard.getEntries()) {
            if (entry.startsWith("직업: ")) {
                scoreboard.resetScores(entry);
            }
        }

        Score scoreLine1 = objective.getScore("--------------- ");
        scoreLine1.setScore(3);

        String job = getJobFromDatabase(player); // 데이터베이스에서 직업 가져오기
        Score scoreLine2 = objective.getScore("직업: " + job);
        scoreLine2.setScore(2);

        int money = getMoneyFromDatabase(player); // 데이터베이스에서 소지금 가져오기
        Score scoreLine3 = objective.getScore("소지금: " + money);
        scoreLine3.setScore(1);

        Score scoreLine4 = objective.getScore("---------------");
        scoreLine4.setScore(0);
    }

    public String getJobFromDatabase(Player player) {
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






    public String getJob(Player player) {
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
    public void setJob(Player player, String job) {
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

    public int getMoney(Player player) {
        // HashMap에서 플레이어의 소지금 정보 가져오기
        int money = moneyMap.getOrDefault(player, 0);
        return money;
    }
    public int getMoneyFromDatabase(Player player) {
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
    public void setMoney(Player player, int amount) {
        moneyMap.put(player, amount);
        updateScoreboard(player); // 소지금 변경 후 스코어보드 업데이트
    }

    public void setMoneyInDatabase(Player player, int amount) {
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


    public void updateMoneyInDatabase(Player player, int amount) {
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
    private void saveMoneyMap() {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO money (player_uuid, amount) VALUES (?, ?)")) {
            for (Map.Entry<Player, Integer> entry : moneyMap.entrySet()) {
                Player player = entry.getKey();
                int amount = entry.getValue();

                statement.setString(1, player.getUniqueId().toString());
                statement.setInt(2, amount);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            // 데이터 저장 실패 처리
        }
    }
}