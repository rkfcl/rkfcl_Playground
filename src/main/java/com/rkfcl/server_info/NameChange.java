package com.rkfcl.server_info;

import com.rkfcl.server_info.Manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.rkfcl.server_info.Manager.ItemManager.namechange;
import static org.bukkit.Bukkit.getServer;

public class NameChange implements Listener {
    private final test plugin;
    private Map<UUID, Boolean> isAwaitingChat = new HashMap<>();

    public NameChange(test plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack items = event.getPlayer().getInventory().getItemInMainHand();
        if (items != null && items.isSimilar(namechange())) {
            // 아이템이 "이름 설정권"인 경우
            player.sendMessage("§l§c| [ ! ] 설정할 이름을 채팅에 입력해 주세요!");

            // 채팅 입력 대기 상태로 설정
            isAwaitingChat.put(player.getUniqueId(), true);

        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (isAwaitingChat.containsKey(playerUUID)) {
            event.setCancelled(true); // 채팅 이벤트 취소

            String message = event.getMessage();

            // 채팅 입력 대기 상태 해제하고 메시지 처리 메서드 호출
            handleChatInput(player, message);
        }
    }
    private void handleChatInput(Player player, String message) {
        // 이곳에서 message 값을 활용하여 적절한 처리를 수행하거나 커맨드를 실행할 수 있습니다.
        // 예를 들면:

        player.sendMessage("[ ! ] 이름을 " + message +" 으(로) 설정 하시겠습니까? [확정] [취소]");

        Bukkit.getScheduler().runTask(plugin, () -> {
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            Bukkit.dispatchCommand(console, "nick " + player.getName() + " " + message);
        });
    }

}
