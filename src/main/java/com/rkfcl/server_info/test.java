package com.rkfcl.server_info;

import com.rkfcl.server_info.Manager.AbilityManager;
import com.rkfcl.server_info.Manager.DatabaseManager;
import com.rkfcl.server_info.Manager.PlayerManager;
import com.rkfcl.server_info.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.sql.*;
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
        databaseManager = new DatabaseManager(connection);
        databaseManager.initializeDatabase();
        abilityManager = new AbilityManager(databaseManager);

        getServer().getPluginManager().registerEvents(this, this);

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("RPGScoreboard", "dummy", "갈치의 놀이터");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        System.out.println("plugin on");
        getServer().getPluginCommand("메뉴").setExecutor(new menu(this));
        getServer().getPluginCommand("수표").setExecutor(new givecheck(this,databaseManager));
        getServer().getPluginCommand("setmoney").setExecutor(new opcommand(this,databaseManager));
        getServer().getPluginCommand("setnpcshop").setExecutor(new NPCShopCommand(this));
        getServer().getPluginCommand("rmnpcshop").setExecutor(new NPCShopCommand(this));

        inventoryClickListener inventoryClickListener = new inventoryClickListener(this,databaseManager);
        getServer().getPluginManager().registerEvents(inventoryClickListener, this);
        playerManager = new PlayerManager(databaseManager); // PlayerManager 인스턴스 생성
        getServer().getPluginManager().registerEvents(new AbilityManager(databaseManager), this);
        interactEntity = new onPlayerInteractEntity(this); // 수정된 인수 전달
        interactEntity.enableTrade(); // 주민 거래 비활성화
        // 플레이어별 스코어보드 업데이트
        List<Player> players = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
        for (Player player : players) {
            updateScoreboard(player);
        }
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
        databaseManager.closeDatabase();
        System.out.println("plugin off");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setScoreboard(scoreboard);
        updateScoreboard(player);
        if(databaseManager.getPlayerBuffs(player).contains("화염저항")){
            abilityManager.applyFireResistance(player); // 화염 저항 버프 함수 호출
        }
    }



    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        final Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(this, () -> {
            if(databaseManager.getPlayerBuffs(player).contains("화염저항")){
                abilityManager.applyFireResistance(player);
            }
        }, 20); // 1초 후에 실행되도록 지연 작업 예약
    }




    public void updateScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.getObjective("money");
        if (objective == null) {
            objective = scoreboard.registerNewObjective("money", "dummy", "소지금");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }

        for (String entry : scoreboard.getEntries()) {
            if (entry.startsWith("소지금: ")) {
                scoreboard.resetScores(entry);
            }
            if (entry.startsWith("직업: ")) {
                scoreboard.resetScores(entry);
            }
        }

        Score scoreLine1 = objective.getScore("--------------- ");
        scoreLine1.setScore(3);

        String job = databaseManager.getPlayerJob(player); // 데이터베이스에서 직업 가져오기
        Score scoreLine2 = objective.getScore("직업: " + job);
        scoreLine2.setScore(2);

        int money = databaseManager.getPlayerMoney(player); // 데이터베이스에서 소지금 가져오기
        Score scoreLine3 = objective.getScore("소지금: " + money);
        scoreLine3.setScore(1);

        Score scoreLine4 = objective.getScore("---------------");
        scoreLine4.setScore(0);

        player.setScoreboard(scoreboard);
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
}