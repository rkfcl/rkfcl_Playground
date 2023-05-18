package com.rkfcl.server_info.Manager;


import com.rkfcl.server_info.ItemManagerCost.OreCost;
import com.rkfcl.server_info.inventoryClickListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;

public class ItemManager {

    private static OreCost orecost; // 정적 변수로 변경

    public ItemManager() {
        orecost = new OreCost(); // 정적 변수로 초기화
    }
    private static ItemStack buildItem(Material type, int amount, String displayName, String... lore) {
        ItemStack stack = new ItemStack(type, amount);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        stack.setItemMeta(meta);
        return stack;
    }
    public static ItemStack InvenDecoWHITE_STAINED_GLASS_PANE () {
        ItemStack check = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
        ItemMeta meta = check.getItemMeta();
        meta.setDisplayName("");
        check.setItemMeta(meta);
        return check;
    }
    public static ItemStack InvenDecoBLACK_STAINED_GLASS_PANE () {
        ItemStack check = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta meta = check.getItemMeta();
        meta.setDisplayName("");
        check.setItemMeta(meta);
        return check;
    }
    public static ItemStack MenuCheck() {
        ItemStack check = new ItemStack(Material.HEART_OF_THE_SEA, 1);
        ItemMeta meta = check.getItemMeta();
        meta.setDisplayName("§3[ 수표 ]");
        meta.setLore(Arrays.asList("§f클릭 후 숫자를 입력하시면 수표가 발행됩니다."));
        check.setItemMeta(meta);
        return check;
    }
    public static ItemStack createCheck(int value) {
        ItemStack check = new ItemStack(Material.HEART_OF_THE_SEA, 1);
        ItemMeta meta = check.getItemMeta();
        meta.setDisplayName("§6"+value + "골드");
//        meta.setLore(Arrays.asList(value + "골드"));
        check.setItemMeta(meta);
        return check;
    }
    public static final ItemStack check = buildItem(Material.PAPER, 1, ChatColor.DARK_GREEN + "수표", "골드");

    public static ItemStack createMineJobItem() {
        ItemStack jobItem = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = jobItem.getItemMeta();
        meta.setDisplayName("§6[ 직업 ] §f광부 1차");
        meta.setLore(Arrays.asList("§f우클릭 시 광부로 전직 합니다","§7광부 : §f블럭 50개를 캘때 마다 성급함1 효과를 얻습니다!","","§l§a| §f구매 가격: §e3,000§f$"," §l§7┗ §7좌클릭시 1개, 쉬프트+좌클릭 시 64개","","§l§c| §f판매 가격: §c판매 불가"," §l§7┗ §7우클릭시 1개, 쉬프트+우클릭 시 64개"));
        jobItem.setItemMeta(meta);
        return jobItem;
    }

    public static ItemStack createMineJob2Item() {
        ItemStack jobItem = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = jobItem.getItemMeta();
        meta.setDisplayName("§6[ 직업 ] §f광부 2차");
        meta.setLore(Arrays.asList("§f우클릭 시 광부로 전직 합니다","§7광부 : §f10%확률로 광물을 한개 더 얻습니다!","","§l§a| §f구매 가격: §e7,000§f$"," §l§7┗ §7좌클릭시 1개, 쉬프트+좌클릭 시 64개","","§l§c| §f판매 가격: §c판매 불가"," §l§7┗ §7우클릭시 1개, 쉬프트+우클릭 시 64개"));
        jobItem.setItemMeta(meta);
        return jobItem;
    }

    public static ItemStack createMineJob3Item() {
        ItemStack jobItem = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = jobItem.getItemMeta();
        meta.setDisplayName("§6[ 직업 ] §f광부 3차");
        meta.setLore(Arrays.asList("§f우클릭 시 광부로 전직 합니다","§7광부 : §f기본 패시브 화염저항이 생기며 블럭 50개를 캘때 마다 성급함2 효과를 얻습니다!","","§l§a| §f구매 가격: §e30,000§f$"," §l§7┗ §7좌클릭시 1개, 쉬프트+좌클릭 시 64개","","§l§c| §f판매 가격: §c판매 불가"," §l§7┗ §7우클릭시 1개, 쉬프트+우클릭 시 64개"));
        jobItem.setItemMeta(meta);
        return jobItem;
    }

    public static ItemStack createMineJob4Item() {
        ItemStack jobItem = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = jobItem.getItemMeta();
        meta.setDisplayName("§6[ 직업 ] §f광부 4차");
        meta.setLore(Arrays.asList("§f우클릭 시 광부로 전직 합니다","§7광부 : §f10%확률로 광물을 두개 더 얻습니다!","","§l§a| §f구매 가격: §e55,000§f$"," §l§7┗ §7좌클릭시 1개, 쉬프트+좌클릭 시 64개","","§l§c| §f판매 가격: §c판매 불가"," §l§7┗ §7우클릭시 1개, 쉬프트+우클릭 시 64개"));
        jobItem.setItemMeta(meta);
        return jobItem;
    }

    public static ItemStack createresetJobItem() {
        ItemStack jobItem = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = jobItem.getItemMeta();
        meta.setDisplayName("§6[ 직업 초기화권 ]");
        meta.setLore(Arrays.asList("§f우클릭 시 직업을 초기화 합니다","","§l§a| §f구매 가격: §e3,0000§f$"," §l§7┗ §7좌클릭시 1개, 쉬프트+좌클릭 시 64개","","§l§c| §f판매 가격: §c판매 불가"," §l§7┗ §7우클릭시 1개, 쉬프트+우클릭 시 64개"));
        jobItem.setItemMeta(meta);
        return jobItem;
    }
    public static ItemStack createOreItem(Material material) {
        ItemStack oreItem = new ItemStack(material, 1);
        ItemMeta meta = oreItem.getItemMeta();
        meta.setLore(Arrays.asList("", "§l§a| §f구매 가격: §c구매 불가", " §l§7┗ §7좌클릭시 1개, 쉬프트+좌클릭 시 64개", "", "§l§c| §f판매 가격: §e" + orecost.calculateIndividualCost(material) + "§f$", " §l§7┗ §7우클릭시 1개, 쉬프트+우클릭 시 전체"));
        oreItem.setItemMeta(meta);
        return oreItem;
    }


}