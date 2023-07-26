package com.rkfcl.server_info;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class DungeonKey implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item != null && item.isSimilar(CustomStack.getInstance("carnivoret_weapon_set_key").getItemStack())) {
            // 원하는 명령어를 여기에 작성합니다.
            player.performCommand("md play SandstoneCrypt");
        }
    }
}
