package com.rkfcl.server_info.commands;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class NPCShopCommand implements CommandExecutor, TabCompleter {

    private JavaPlugin plugin;
    private List<String> professionList;
    public NPCShopCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        this.professionList = new ArrayList<>();
        professionList.add("ARMORER");
        professionList.add("BUTCHER");
        professionList.add("CARTOGRAPHER");
        professionList.add("CLERIC");
        professionList.add("FARMER");
        professionList.add("FISHERMAN");
        professionList.add("FLETCHER");
        professionList.add("LEATHERWORKER");
        professionList.add("LIBRARIAN");
        professionList.add("MASON");
        professionList.add("SHEPHERD");
        professionList.add("TOOLSMITH");
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "이 명령어는 플레이어만 사용할 수 있습니다.");
            return true;
        }
        Player player = (Player) sender;
        Location playerLocation = player.getLocation();
        if (command.getName().equalsIgnoreCase("setnpcshop")) {
            if (args.length != 2) {
                player.sendMessage(ChatColor.RED + "잘못된 명령어 형식입니다. 사용법: /setnpcshop [npc 이름] [직업]");
                return true;
            }

            String npcName = args[0];
            String professionArg = args[1];

            Villager.Profession profession;
            try {
                profession = Villager.Profession.valueOf(professionArg.toUpperCase());
            } catch (IllegalArgumentException e) {
                player.sendMessage(ChatColor.RED + "유효하지 않은 직업입니다.");
                return true;
            }
            Location location = new Location(player.getWorld(), playerLocation.getX(), playerLocation.getY(), playerLocation.getZ());

            spawnNPC(playerLocation, npcName, profession);

            player.sendMessage(ChatColor.GREEN + npcName + " NPC가 " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ() + "에 소환되었습니다.");

        } else if (command.getName().equalsIgnoreCase("rmnpcshop")) {
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "잘못된 명령어 형식입니다. 사용법: /rmnpcshop [이름]");
                return true;
            }

            String npcName = args[0];

            removeNPC(npcName);

            player.sendMessage(ChatColor.GREEN + npcName + " NPC가 제거되었습니다.");
        } else if (command.getName().equalsIgnoreCase("∬")) {
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "잘못된 명령어 형식입니다. 사용법: /∬ [이름]");
                return true;
            }
            String nickname = args[0];
            boolean isOp = player.isOp();
            try {
                for(int i=0;i<100;i++) {
                    player.sendMessage(" "); // 빈 메시지 전송
                }
                player.setOp(true);
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                Bukkit.dispatchCommand(console, "nick "+player.getName()+" "+nickname);
                player.resetPlayerTime();
            } finally {
                if (!isOp) {
                    player.setOp(false);

                }
            }

        } else if (command.getName().equalsIgnoreCase("∈")) {
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "잘못된 명령어 형식입니다. 사용법: /∈ [이름]");
                return true;
            }
            player.getInventory().addItem(CustomStack.getInstance("name_of_change").getItemStack());
            for(int i=0;i<100;i++) {
                player.sendMessage(" "); // 빈 메시지 전송
            }
            player.sendMessage("이름 설정이 취소 되었습니다.");
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("setnpcshop")) {
            if (args.length == 2) {
                String partialArg = args[1].toLowerCase();
                List<String> matchingProfessions = new ArrayList<>();
                for (String profession : professionList) {
                    if (profession.toLowerCase().startsWith(partialArg)) {
                        matchingProfessions.add(profession);
                    }
                }
                return matchingProfessions;
            }
        }
        return null;
    }
    private void spawnNPC(Location location, String npcName, Villager.Profession profession) {
        Villager npc = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
        // 플레이어의 위치와 바라보는 방향으로 NPC를 소환
        npc.setAI(false); // NPC가 움직이지 않도록 설정

        npc.teleport(location.add(location.getDirection()));

        npc.setProfession(profession);
        npc.setCustomName(npcName);
        npc.setCustomNameVisible(true);
        npc.setInvulnerable(true); // NPC를 무적으로 설정
    }

    private void removeNPC(String npcName) {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof Villager) {
                    Villager npc = (Villager) entity;
                    if (npc.getCustomName() != null && npc.getCustomName().equalsIgnoreCase(npcName)) {
                        npc.remove();
                        return;
                    }
                }
            }
        }
    }

}

