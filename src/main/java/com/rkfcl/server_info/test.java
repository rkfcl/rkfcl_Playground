package com.rkfcl.server_info;

import com.rkfcl.server_info.Manager.AbilityManager;
import com.rkfcl.server_info.Manager.PlayerDataManager;
import com.rkfcl.server_info.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.io.*;
import java.util.*;
import java.sql.*;

import static com.rkfcl.server_info.Manager.PlayerDataManager.playerBalances;
import static com.rkfcl.server_info.Manager.PlayerDataManager.playerJob;

public class test extends JavaPlugin implements Listener {
    private final File PlayerBalanceFile = new File(getDataFolder(), "/playerdata.txt");
    private final File PlayerJobFile = new File(getDataFolder(), "/playerJobdata.txt");
    private Scoreboard scoreboard;
    private Objective objective;
    // 플레이어별 소지금을 저장하는 HashMap
    private Connection connection;
    private PlayerDataManager playerDataManager;

    private AbilityManager abilityManager; // AbilityManager 인스턴스 추가
    private onPlayerInteractEntity interactEntity; // onPlayerInteractEntity 인스턴스 추가
    @Override
    public void onEnable() {
        abilityManager = new AbilityManager(playerDataManager,this);
        playerDataManager = new PlayerDataManager();
        makeFile(PlayerBalanceFile);
        makeFile(PlayerJobFile);
        mapToFile(PlayerBalanceFile,playerBalances);
        fileToMap(PlayerBalanceFile,playerBalances);
        mapToFileString(PlayerJobFile,playerJob);
        fileToMapString(PlayerJobFile,playerJob);
        getServer().getPluginManager().registerEvents(this, this);

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("RPGScoreboard", "dummy", "갈치의 놀이터");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        System.out.println("plugin on");
        getServer().getPluginCommand("메뉴").setExecutor(new menu(this));
        getServer().getPluginCommand("수표").setExecutor(new givecheck(this,playerDataManager));
        getServer().getPluginCommand("setmoney").setExecutor(new opcommand(this,playerDataManager));
        getServer().getPluginCommand("setnpcshop").setExecutor(new NPCShopCommand(this));
        getServer().getPluginCommand("rmnpcshop").setExecutor(new NPCShopCommand(this));

        inventoryClickListener inventoryClickListener = new inventoryClickListener(this,playerDataManager);
        getServer().getPluginManager().registerEvents(inventoryClickListener, this);
        getServer().getPluginManager().registerEvents(new AbilityManager(playerDataManager,this), this);
        interactEntity = new onPlayerInteractEntity(this); // 수정된 인수 전달
        interactEntity.enableTrade(); // 주민 거래 비활성화
        // 플레이어별 스코어보드 업데이트
        List<Player> players = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
        for (Player player : players) {
            updateScoreboard(player);
        }
    }


    @SuppressWarnings("deprecation")
    public void mapToFile(File f, HashMap<UUID, Integer> map) {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask( this , new Runnable() {

            @Override
            public void run() {
                try {
                    System.out.println(ChatColor.GREEN + "Data saved");
                    FileWriter writer = new FileWriter(f, false);
                    for(UUID uuid : map.keySet()){
                        writer.write(uuid.toString()+"|"+map.get(uuid)+"\n");
                    }
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 20 * 30, 20 * 30);

    }
    public void fileToMap(File f, HashMap<UUID, Integer> map) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String fileLine = null;
            while ((fileLine = reader.readLine()) != null) {

                UUID uuid = UUID.fromString(fileLine.split("\\|")[0]);
                String str = fileLine.split("\\|")[1];

                map.put(uuid, Integer.parseInt(str));
            }
        } catch (FileNotFoundException e3) {
            e3.printStackTrace();
        } catch (IOException e4) {
            e4.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    public void mapToFileString(File f, HashMap<UUID, String> map) {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask( this , new Runnable() {

            @Override
            public void run() {
                try {
                    System.out.println(ChatColor.GREEN + "Data saved");
                    FileWriter writer = new FileWriter(f, false);
                    for(UUID uuid : map.keySet()){
                        writer.write(uuid.toString()+"|"+map.get(uuid)+"\n");
                    }
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 20 * 30, 20 * 30);

    }
    public void fileToMapString(File f, HashMap<UUID, String> map) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String fileLine = null;
            while ((fileLine = reader.readLine()) != null) {

                UUID uuid = UUID.fromString(fileLine.split("\\|")[0]);
                String str = fileLine.split("\\|")[1];

                map.put(uuid, str);
            }
        } catch (FileNotFoundException e3) {
            e3.printStackTrace();
        } catch (IOException e4) {
            e4.printStackTrace();
        }
    }
    public void makeFile(File f) {
        File dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs(); // 데이터 폴더 생성
        }

        if (!f.exists() || !f.isFile()) {
            try {
                f.createNewFile(); // 파일 생성
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
        System.out.println("plugin off");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(!playerBalances.containsKey(player.getUniqueId())){
            playerDataManager.setPlayerBalance(player.getUniqueId(),0);
            playerDataManager.setPlayerJob(player.getUniqueId(),"초보자");
            System.out.println("new player join the game");
        }
        player.setScoreboard(scoreboard);
        updateScoreboard(player);
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

        String job = playerDataManager.getPlayerJob(player.getUniqueId()); // 데이터베이스에서 직업 가져오기
        Score scoreLine2 = objective.getScore("직업: " + job);
        scoreLine2.setScore(2);
        int money = playerDataManager.getPlayerBalance(player.getUniqueId()); // 데이터베이스에서 소지금 가져오기
        Score scoreLine3 = objective.getScore("소지금: " + money);
        scoreLine3.setScore(1);

        Score scoreLine4 = objective.getScore("---------------");
        scoreLine4.setScore(0);

        player.setScoreboard(scoreboard);
    }
}