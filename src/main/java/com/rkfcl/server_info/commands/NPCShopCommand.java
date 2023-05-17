package com.rkfcl.server_info.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.java.JavaPlugin;

public class NPCShopCommand implements CommandExecutor {

    private JavaPlugin plugin;

    public NPCShopCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "이 명령어는 플레이어만 사용할 수 있습니다.");
            return true;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("setnpcshop")) {
            if (args.length != 4) {
                player.sendMessage(ChatColor.RED + "잘못된 명령어 형식입니다. 사용법: /setnpcshop [npc 이름] [x] [y] [z]");
                return true;
            }

            String npcName = args[0];
            double x, y, z;

            try {
                x = Double.parseDouble(args[1]);
                y = Double.parseDouble(args[2]);
                z = Double.parseDouble(args[3]);
            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "좌표는 숫자로 입력해야 합니다.");
                return true;
            }

            Location location = new Location(player.getWorld(), x, y, z);

            spawnNPC(location, npcName);

            player.sendMessage(ChatColor.GREEN + npcName + " NPC가 " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ() + "에 소환되었습니다.");

        } else if (command.getName().equalsIgnoreCase("rmnpcshop")) {
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "잘못된 명령어 형식입니다. 사용법: /rmnpcshop [이름]");
                return true;
            }

            String npcName = args[0];

            removeNPC(npcName);

            player.sendMessage(ChatColor.GREEN + npcName + " NPC가 제거되었습니다.");
        }

        return true;
    }
    private void spawnNPC(Location location, String npcName) {
        Villager npc = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
        npc.setAI(false); // NPC가 움직이지 않도록 설정
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
