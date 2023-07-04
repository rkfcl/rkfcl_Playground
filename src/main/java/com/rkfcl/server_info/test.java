package com.rkfcl.server_info;

import com.rkfcl.server_info.Manager.AbilityManager;
import com.rkfcl.server_info.Manager.FishingManager;
import com.rkfcl.server_info.Manager.ItemManager;
import com.rkfcl.server_info.Manager.PlayerDataManager;
import com.rkfcl.server_info.commands.*;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.util.*;
import java.sql.*;

import static com.rkfcl.server_info.ItemRegistration.registeredItems;
import static com.rkfcl.server_info.ItemReturn.ReturnedItems;
import static com.rkfcl.server_info.Manager.PlayerDataManager.playerBalances;
import static com.rkfcl.server_info.Manager.PlayerDataManager.playerJob;
import static com.rkfcl.server_info.ProtectBlock.*;
import static com.rkfcl.server_info.customcrops.*;
import static com.rkfcl.server_info.customdoor.LockDoorMap;

public class test extends JavaPlugin implements Listener {
    FileConfiguration config = getConfig();
    private final File PlayerBalanceFile = new File(getDataFolder(), "/playerdata.txt");
    private final File PlayerJobFile = new File(getDataFolder(), "/playerJobdata.txt");
    private final File CornFile = new File(getDataFolder(), "/corn.txt");
    private final File CabbageFile = new File(getDataFolder(), "/cabbage.txt");
    private final File OnionFile = new File(getDataFolder(), "/Onion.txt");
    private final File Sweet_potatoFile = new File(getDataFolder(), "/Sweet_potato.txt");
    private final File TomatoFile = new File(getDataFolder(), "/Tomato.txt");
    private final File RiceFile = new File(getDataFolder(), "/Rice.txt");
    private final File protectMapFile = new File(getDataFolder(), "/protectMap.txt");
    private final File AllowprotectMapFile = new File(getDataFolder(), "/AllowprotectMap.txt");
    private final File AccountprotectMapFile = new File(getDataFolder(), "/AccountprotectMap.txt");
    private final File LockDoorMapFile = new File(getDataFolder(), "/LockDoorMap.txt");
    private final File ReturnedItemsFile = new File(getDataFolder(), "/ReturnedItems.txt");
    private final File registeredItemsFile = new File(getDataFolder(), "/registeredItems.txt");

    private Scoreboard scoreboard;
    private Objective objective;
    // 플레이어별 소지금을 저장하는 HashMap
    private PlayerDataManager playerDataManager;
    private AbilityManager abilityManager; // AbilityManager 인스턴스 추가
    private onPlayerInteractEntity interactEntity; // onPlayerInteractEntity 인스턴스 추가
    private FishingManager fishingManager;
    private LetterOfReturn letterOfReturn;
    private customcrops customcrops;
    private ProtectBlock protectBlock;
    private customdoor customdoor;


    @Override
    public void onEnable() {

        playerDataManager = new PlayerDataManager();
        abilityManager = new AbilityManager(playerDataManager,this);
        makeFile(PlayerBalanceFile);
        makeFile(PlayerJobFile);
        makeFile(CornFile);
        makeFile(CabbageFile);
        makeFile(OnionFile);
        makeFile(Sweet_potatoFile);
        makeFile(TomatoFile);
        makeFile(RiceFile);
        makeFile(protectMapFile);
        makeFile(AllowprotectMapFile);
        makeFile(AccountprotectMapFile);
        makeFile(LockDoorMapFile);
        makeFile(ReturnedItemsFile);
        makeFile(registeredItemsFile);
        mapToFile(PlayerBalanceFile,playerBalances);
        fileToMap(PlayerBalanceFile,playerBalances);
        mapToFileString(PlayerJobFile,playerJob);
        fileToMapString(PlayerJobFile,playerJob);
        loadProtectBlock(protectMapFile,protectMap);
        loadAllowprotectMap(AllowprotectMapFile,AllowprotectMap);
        loadAccountprotectMap(AccountprotectMapFile,AccountprotectMap);
        loadLockDoorMapFromFile(LockDoorMapFile,LockDoorMap);
        loadMapFromFile(ReturnedItemsFile,ReturnedItems);
        loadMapFromFile(registeredItemsFile,registeredItems);
        getServer().getPluginManager().registerEvents(this, this);

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("RPGScoreboard", "dummy", "갈치의 놀이터");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        System.out.println("plugin on");
        getServer().getPluginCommand("메뉴").setExecutor(new MenuCommand(this));
        getServer().getPluginCommand("수표").setExecutor(new GiveCheckCommand(this,playerDataManager));
        getServer().getPluginCommand("setmoney").setExecutor(new opcommand(this,playerDataManager));
        getServer().getPluginCommand("setnpcshop").setExecutor(new NPCShopCommand(this));
        getServer().getPluginCommand("rmnpcshop").setExecutor(new NPCShopCommand(this));
        getServer().getPluginCommand("∬").setExecutor(new NPCShopCommand(this));
        getServer().getPluginCommand("∈").setExecutor(new NPCShopCommand(this));
        getServer().getPluginCommand("거래소").setExecutor(new ExchangeCommand(this));

        inventoryClickListener inventoryClickListener = new inventoryClickListener(this,playerDataManager);
        getServer().getPluginManager().registerEvents(inventoryClickListener, this);
        getServer().getPluginManager().registerEvents(new AbilityManager(playerDataManager,this), this);
        interactEntity = new onPlayerInteractEntity(this); // 수정된 인수 전달
        interactEntity.enableTrade(); // 주민 거래 비활성화
        NameChange nameChange = new NameChange(this);
        getServer().getPluginManager().registerEvents(nameChange, this);
        fishingManager = new FishingManager(playerDataManager,this);
        getServer().getPluginManager().registerEvents(fishingManager, this);
        letterOfReturn = new LetterOfReturn(this);
        getServer().getPluginManager().registerEvents(letterOfReturn,this);
        customcrops = new customcrops(this);
        getServer().getPluginManager().registerEvents(customcrops,this);
        protectBlock = new ProtectBlock(this);
        getServer().getPluginManager().registerEvents(protectBlock,this);
        customdoor = new customdoor(this);
        getServer().getPluginManager().registerEvents(customdoor,this);
        // PlayerNameChanger 인스턴스 생성
        // 플레이어별 스코어보드 업데이트
        List<Player> players = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
        for (Player player : players) {
            updateScoreboard(player);
        }
        loadTasks(CornFile,cornstageMap,"corn_seed_stage_2", "corn_seed_stage_3", "corn_seed_stage_4", "corn_seed_stage_5");
        loadTasks(CabbageFile,CabbageMap,"cabbage_seed_stage_2", "cabbage_seed_stage_3", "cabbage_seed_stage_4");
        loadTasks(OnionFile,OnionMap,"onion_seed_stage_2", "onion_seed_stage_3", "onion_seed_stage_4");
        loadTasks(Sweet_potatoFile,Sweet_potatoMap,"sweet_potato_stage_2", "sweet_potato_stage_3");
        loadTasks(TomatoFile,TomatoMap,"tomato_stage_2", "tomato_stage_3", "tomato_stage_4","tomato_stage_5","tomato_stage_6");
        loadTasks(RiceFile,RiceMap,"Rice_stage_1", "Rice_stage_2", "Rice_stage_3","Rice_stage_4","Rice_stage_5","Rice_stage_6","Rice_stage_7");
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
        }, 20 * 60, 20 * 60);

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
        }, 20 * 60, 20 * 60);

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
    public void saveTasks(File f, HashMap<Location, Integer> map) {

        try (FileWriter writer = new FileWriter(f)) {
            for (Map.Entry<Location, Integer> entry : map.entrySet()) {
                Location location = entry.getKey();
                int taskId = entry.getValue();
                String taskLine = location.getWorld().getName() + "," + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ() + "," + taskId;
                writer.write(taskLine + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveProtectBlock(File file, HashMap<Location, Location> map) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Map.Entry<Location, Location> entry : map.entrySet()) {
                Location key = entry.getKey();
                Location value = entry.getValue();
                String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s%n",
                        key.getWorld().getName(), key.getBlockX(), key.getBlockY(), key.getBlockZ(),
                        value.getWorld().getName(), value.getBlockX(), value.getBlockY(), value.getBlockZ());
                writer.write(line);
            }
            System.out.println("The protectMap has been successfully saved to a file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadProtectBlock(File file, HashMap<Location, Location> map) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 8) {
                    String keyWorldName = data[0];
                    int keyX = Integer.parseInt(data[1]);
                    int keyY = Integer.parseInt(data[2]);
                    int keyZ = Integer.parseInt(data[3]);
                    String valueWorldName = data[4];
                    int valueX = Integer.parseInt(data[5]);
                    int valueY = Integer.parseInt(data[6]);
                    int valueZ = Integer.parseInt(data[7]);

                    World keyWorld = Bukkit.getWorld(keyWorldName);
                    World valueWorld = Bukkit.getWorld(valueWorldName);

                    if (keyWorld != null && valueWorld != null) {
                        Location keyLocation = new Location(keyWorld, keyX, keyY, keyZ);
                        Location valueLocation = new Location(valueWorld, valueX, valueY, valueZ);
                        map.put(keyLocation, valueLocation);
                    }
                }
            }
            System.out.println("The protectMap has been successfully loaded from a file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveAllowprotectMap(File file, HashMap<UUID, List<Location>> map) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Map.Entry<UUID, List<Location>> entry : map.entrySet()) {
                UUID playerUUID = entry.getKey();
                List<Location> locations = entry.getValue();

                // Write player UUID
                writer.write(playerUUID.toString() + System.lineSeparator());

                // Write each location in the list
                for (Location location : locations) {
                    String line = String.format("%s,%s,%s,%s",
                            location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
                    writer.write(line + System.lineSeparator());
                }

                // Write separator between each player's data
                writer.write("===" + System.lineSeparator());
            }
            System.out.println("The AllowprotectMap has been successfully saved to a file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadAllowprotectMap(File file, HashMap<UUID, List<Location>> map) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            UUID currentPlayerUUID = null;
            List<Location> currentLocations = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                if (line.equals("===")) {
                    // Separator found, add the collected data to the map
                    if (currentPlayerUUID != null) {
                        map.put(currentPlayerUUID, currentLocations);
                    }
                    currentPlayerUUID = null;
                    currentLocations = new ArrayList<>();
                } else if (currentPlayerUUID == null) {
                    // Line contains player UUID
                    currentPlayerUUID = UUID.fromString(line);
                } else {
                    // Line contains location data
                    String[] parts = line.split(",");
                    String worldName = parts[0];
                    int blockX = Integer.parseInt(parts[1]);
                    int blockY = Integer.parseInt(parts[2]);
                    int blockZ = Integer.parseInt(parts[3]);

                    Location location = new Location(Bukkit.getWorld(worldName), blockX, blockY, blockZ);
                    currentLocations.add(location);
                }
            }

            // Add the last player's data to the map (if any)
            if (currentPlayerUUID != null) {
                map.put(currentPlayerUUID, currentLocations);
            }

            System.out.println("The AllowprotectMap has been successfully loaded from the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadAccountprotectMap(File file, HashMap<Location, List<UUID>> map) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            Location currentLocation = null;
            List<UUID> currentPlayers = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                if (line.equals("===")) {
                    // Separator found, add the collected data to the map
                    if (currentLocation != null) {
                        map.put(currentLocation, currentPlayers);
                    }
                    currentLocation = null;
                    currentPlayers = new ArrayList<>();
                } else if (currentLocation == null) {
                    // Line contains location data
                    String[] parts = line.split(",");
                    String worldName = parts[0];
                    int blockX = Integer.parseInt(parts[1]);
                    int blockY = Integer.parseInt(parts[2]);
                    int blockZ = Integer.parseInt(parts[3]);

                    currentLocation = new Location(Bukkit.getWorld(worldName), blockX, blockY, blockZ);
                } else {
                    // Line contains player UUID
                    UUID playerUUID = UUID.fromString(line);
                    currentPlayers.add(playerUUID);
                }
            }

            // Add the last location's data to the map (if any)
            if (currentLocation != null) {
                map.put(currentLocation, currentPlayers);
            }

            System.out.println("The AccountprotectMap has been successfully loaded from the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveAccountprotectMap(File file, HashMap<Location, List<UUID>> map) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Map.Entry<Location, List<UUID>> entry : map.entrySet()) {
                Location location = entry.getKey();
                List<UUID> players = entry.getValue();

                // Write location data
                String locationLine = String.format("%s,%d,%d,%d%n",
                        location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
                writer.write(locationLine);

                // Write player UUIDs
                for (UUID playerUUID : players) {
                    String playerLine = playerUUID.toString() + System.lineSeparator();
                    writer.write(playerLine);
                }

                // Write separator
                writer.write("===" + System.lineSeparator());
            }

            System.out.println("The AccountprotectMap has been successfully saved to a file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveLockDoorMapToFile(File file, HashMap<Location, String> lockDoorMap) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (Map.Entry<Location, String> entry : lockDoorMap.entrySet()) {
                Location location = entry.getKey();
                String password = entry.getValue();
                String line = location.getWorld().getName() + "|" + location.getBlockX() + "|" + location.getBlockY() + "|" + location.getBlockZ() + "|" + password;
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadLockDoorMapFromFile(File file, HashMap<Location, String> lockDoorMap) {
        lockDoorMap.clear(); // 기존 맵 내용 초기화

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String worldName = parts[0];
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    int z = Integer.parseInt(parts[3]);
                    String password = parts[4];

                    World world = Bukkit.getWorld(worldName);
                    if (world != null) {
                        Location location = new Location(world, x, y, z);
                        lockDoorMap.put(location, password);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTasks(File f, HashMap<Location, Integer> map,String... growthStages) {
        taskMap.clear();
        cornstageMap.clear();
        CabbageMap.clear();
        OnionMap.clear();
        Sweet_potatoMap.clear();
        TomatoMap.clear();
        RiceMap.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String worldName = parts[0];
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    int z = Integer.parseInt(parts[3]);
                    int stage = Integer.parseInt(parts[4]);
                    Location location = new Location(Bukkit.getWorld(worldName), x, y, z);
                    int taskId = customcrops.createGrowthTask(location,map, stage,3000, growthStages);
                    taskMap.put(location, taskId);
                    map.put(location,stage);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 거래소 map txt파일로 저장 및 로드
    public static void saveMapToFile(File file, Map<ItemStack, UUID> map) {
        try (PrintWriter writer = new PrintWriter(file)) {
            for (Map.Entry<ItemStack, UUID> entry : map.entrySet()) {
                ItemStack item = entry.getKey();
                UUID uuid = entry.getValue();
                String line = uuid.toString() + "|" + itemToString(item);
                writer.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void loadMapFromFile(File file, Map<ItemStack, UUID> map) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    UUID uuid = UUID.fromString(parts[0]);
                    ItemStack item = stringToItem(parts[1]);
                    if (item != null) {
                        map.put(item, uuid);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String itemToString(ItemStack item) {
        if (item == null) {
            return null;
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(item);
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ItemStack stringToItem(String string) {
        if (string == null) {
            return null;
        }

        byte[] data = Base64.getDecoder().decode(string);
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
             BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(inputStream)) {
            return (ItemStack) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public void onDisable() {
        saveTasks(CornFile,cornstageMap);
        saveTasks(CabbageFile,CabbageMap);
        saveTasks(OnionFile,OnionMap);
        saveTasks(Sweet_potatoFile,Sweet_potatoMap);
        saveTasks(TomatoFile,TomatoMap);
        saveTasks(RiceFile,RiceMap);
        customcrops.cancelAllTasks();
        saveProtectBlock(protectMapFile,protectMap);
        saveAllowprotectMap(AllowprotectMapFile,AllowprotectMap);
        saveAccountprotectMap(AccountprotectMapFile,AccountprotectMap);
        saveLockDoorMapToFile(LockDoorMapFile,LockDoorMap);
        saveMapToFile(ReturnedItemsFile,ReturnedItems);
        saveMapToFile(registeredItemsFile,registeredItems);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
        mapToFile(PlayerBalanceFile,playerBalances);
        mapToFileString(PlayerJobFile,playerJob);
        System.out.println("갈치놀이터 플러그인 정상 종료");

    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(!playerBalances.containsKey(player.getUniqueId())){
            playerDataManager.setPlayerBalance(player.getUniqueId(),0);
            playerDataManager.setPlayerJob(player.getUniqueId(),"초보자");
            player.getInventory().addItem(CustomStack.getInstance("name_of_change").getItemStack());
            player.getInventory().addItem(ItemManager.lockdoor());
            player.getInventory().addItem(CustomStack.getInstance("small_construction_block").getItemStack());
            player.getInventory().addItem(CustomStack.getInstance("letter_of_return").getItemStack());
            ItemStack chur = CustomStack.getInstance("chur").getItemStack();
            chur.setAmount(32);
            player.getInventory().addItem(chur);
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