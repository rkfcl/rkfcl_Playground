package com.rkfcl.server_info.commands;

import com.rkfcl.server_info.ItemRegistration;
import com.rkfcl.server_info.Manager.PlayerDataManager;
import com.rkfcl.server_info.test;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ExchangeCommand implements CommandExecutor, TabCompleter {
    private test plugin;
    private List<String> subCommandslist;
    public ExchangeCommand(test plugin) {
        this.plugin = plugin;
        this.subCommandslist = new ArrayList<>();
        subCommandslist.add("등록");
        subCommandslist.add("등록아이템");
        subCommandslist.add("반환아이템");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "플레이어만 이 명령어를 사용할 수 있습니다.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "사용법: /거래소 <서브 명령어>");
            player.sendMessage(ChatColor.YELLOW + "가능한 서브 명령어: 등록, 등록아이템, 반환아이템");
            return true;
        }

        String subCommand = args[0];
        switch (subCommand) {
            case "등록":
                if (args.length < 3) {
                    player.sendMessage(ChatColor.RED + "사용법: /거래소 등록 [가격] [수량]");
                    return true;
                }

                ItemStack mainHandItem = player.getInventory().getItemInMainHand();
                if (mainHandItem.getType() == Material.AIR) {
                    player.sendMessage(ChatColor.RED + "등록할 아이템이 없습니다.");
                    return true;
                }

                int mainHandAmount = mainHandItem.getAmount();
                String priceString = args[1];
                String quantityString = args[2];

                try {
                    int price = Integer.parseInt(priceString);
                    int quantity = Integer.parseInt(quantityString);

                    if (price <= 0 || quantity <= 0) {
                        player.sendMessage(ChatColor.RED + "올바른 가격과 수량을 입력해주세요.");
                        return true;
                    }

                    if (quantity > mainHandAmount) {
                        player.sendMessage(ChatColor.RED + "등록할 아이템의 개수가 부족합니다.");
                        return true;
                    }

                    // TODO: 거래소에 아이템 등록 로직 추가
                    ItemStack registeredItem = mainHandItem.clone();
                    registeredItem.setAmount(quantity);
                    ItemRegistration.registerItem(registeredItem, price);

                    player.sendMessage(ChatColor.GREEN + "거래소에 아이템을 성공적으로 등록하였습니다.");
                    return true;
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "올바른 가격과 수량을 입력해주세요.");
                    return true;
                }

            case "등록아이템":
                // TODO: 거래소에 등록된 아이템 목록을 플레이어에게 보여주는 로직 추가
                player.sendMessage(ChatColor.YELLOW + "거래소에 등록된 아이템 목록을 표시합니다.");
                return true;

            case "반환아이템":
                // TODO: 플레이어의 거래소 아이템 반환 로직 추가
                player.sendMessage(ChatColor.YELLOW + "거래소 아이템을 반환합니다.");
                return true;

            default:
                player.sendMessage(ChatColor.RED + "알 수 없는 서브 명령어입니다.");
                return true;
        }
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("거래소")) {
            if (args.length == 1) {
                String partialArg = args[0].toLowerCase();
                List<String> matchingSubCommands = new ArrayList<>();
                for (String subCommand : subCommandslist) {
                    if (subCommand.toLowerCase().startsWith(partialArg)) {
                        matchingSubCommands.add(subCommand);
                    }
                }
                return matchingSubCommands;
            }
        }
        return null;
    }
}
