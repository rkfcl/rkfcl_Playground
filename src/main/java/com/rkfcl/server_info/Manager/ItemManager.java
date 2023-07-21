package com.rkfcl.server_info.Manager;


import com.rkfcl.server_info.ItemManagerCost.ItemCost;
import dev.lone.itemsadder.api.ItemsAdder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.rkfcl.server_info.ItemManagerCost.ItemCost.itemDefaultPrices;
import static com.rkfcl.server_info.ItemManagerCost.ItemCost.itemPrices;

public class ItemManager {

    private static ItemCost itemCost;

    public ItemManager() {
        itemCost = new ItemCost();
    }
    private static ItemStack buildItem(Material type, int amount, String displayName, String... lore) {
        ItemStack stack = new ItemStack(type, amount);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        stack.setItemMeta(meta);
        return stack;
    }
    public static ItemStack InvenDecoSign () {
        ItemStack check = new ItemStack(Material.OAK_SIGN, 1);
        ItemMeta meta = check.getItemMeta();
        meta.setDisplayName("거래소");
        meta.setLore(Arrays.asList("§f클릭 시 거래소 도움말을 확인합니다."));
        check.setItemMeta(meta);
        return check;
    }
    public static ItemStack InvenDecoWHITE_STAINED_GLASS_PANE () {
        ItemStack check = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
        ItemMeta meta = check.getItemMeta();
        meta.setDisplayName(" ");
        check.setItemMeta(meta);
        return check;
    }

    public static ItemStack InvenDecoGREEN_STAINED_GLASS_PANE_NEXT() {
        ItemStack check = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
        ItemMeta meta = check.getItemMeta();
        meta.setDisplayName("다음 페이지");
        check.setItemMeta(meta);
        return check;
    }
    public static ItemStack InvenDecoRED_STAINED_GLASS_PANE_BEFORE() {
        ItemStack check = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemMeta meta = check.getItemMeta();
        meta.setDisplayName("이전 페이지");
        check.setItemMeta(meta);
        return check;
    }
    public static ItemStack InvenDeco_CHEST() {
        ItemStack check = new ItemStack(Material.CHEST, 1);
        ItemMeta meta = check.getItemMeta();
        meta.setDisplayName("§l§6등록된 아이템");
        check.setItemMeta(meta);
        return check;
    }
    public static ItemStack InvenDeco_ENDERCHEST() {
        ItemStack check = new ItemStack(Material.ENDER_CHEST, 1);
        ItemMeta meta = check.getItemMeta();
        meta.setDisplayName("§l§c반환된 아이템");
        check.setItemMeta(meta);
        return check;
    }
    public static ItemStack InvenDecoBLACK_STAINED_GLASS_PANE () {
        ItemStack check = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta meta = check.getItemMeta();
        meta.setDisplayName(" ");
        check.setItemMeta(meta);
        return check;
    }
    public static ItemStack Menu_Quest () {
        ItemStack check = new ItemStack(Material.BOOK, 1);
        ItemMeta meta = check.getItemMeta();
        meta.setDisplayName("퀘스트 목록");
        check.setItemMeta(meta);
        return check;
    }
    public static ItemStack Menu_fishcollect() {
        // itemsadder에서 생성한 아이템을 가져오는 코드
        ItemStack fishItem = ItemsAdder.getCustomItem("tuna");
        ItemMeta meta = fishItem.getItemMeta();
        meta.setDisplayName("물고기 도감");
        fishItem.setItemMeta(meta);
        // 아이템 메타 수정 등 추가적인 작업을 수행할 수 있습니다.

        return fishItem;
    }
    public static ItemStack InvenDecoGREEN_STAINED_GLASS_PANE () {
        ItemStack check = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
        ItemMeta meta = check.getItemMeta();
        meta.setDisplayName("§a권한 부여");
        check.setItemMeta(meta);
        return check;
    }
    public static ItemStack InvenDecoRED_STAINED_GLASS_PANE () {
        ItemStack check = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemMeta meta = check.getItemMeta();
        meta.setDisplayName("§c건차 회수");
        meta.setLore(Arrays.asList("§f클릭시 건차 회수"));
        check.setItemMeta(meta);
        return check;
    }
    public static ItemStack MenuCheck() {
        ItemStack check = new ItemStack(Material.HEART_OF_THE_SEA, 1);
        ItemMeta meta = check.getItemMeta();
        meta.setDisplayName("§3[ 수표 ]");
        meta.setLore(Arrays.asList("§f클릭 후 숫자를 입력하시면 수표가 발행됩니다."));
        // 커스텀 모델 데이터 값 설정
        int customModelDataValue = 1; // 커스텀 모델 데이터 값 1로 설정
        meta.setCustomModelData(customModelDataValue);
        check.setItemMeta(meta);
        return check;
    }
    public static ItemStack createCheck(int value) {
        ItemStack check = new ItemStack(Material.HEART_OF_THE_SEA, 1);
        ItemMeta meta = check.getItemMeta();
        meta.setDisplayName(value + "§6골드");

        // 커스텀 모델 데이터 값 설정
        int customModelDataValue = 1; // 커스텀 모델 데이터 값 1로 설정
        meta.setCustomModelData(customModelDataValue);

        check.setItemMeta(meta);
        return check;
    }
    public static final ItemStack check = buildItem(Material.PAPER, 1, ChatColor.DARK_GREEN + "수표", "골드");

    public static ItemStack createJobItem(String jobName, String jobDescription, int purchasePrice) {
        ItemStack jobItem = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = jobItem.getItemMeta();
        meta.setDisplayName("§6[ 직업 ] " + jobName);
        meta.setLore(Arrays.asList(
                "§f우클릭 시 " + jobName + "로 전직 합니다",
                jobDescription,
                "",
                "§l§a| §f구매 가격: §e" + purchasePrice + "§f$",
                " §l§7┗ §7좌클릭시 1개, 쉬프트+좌클릭 시 64개",
                "",
                "§l§c| §f판매 가격: §c판매 불가",
                " §l§7┗ §7우클릭시 1개, 쉬프트+우클릭 시 64개"
        ));
        jobItem.setItemMeta(meta);
        return jobItem;
    }

    public static ItemStack createMineJob1Item() {
        return createJobItem(
                "§f광부 1차",
                "§7광부 : §f블럭 50개를 캘때 마다 성급함1 효과를 얻습니다!",
                3000
        );
    }

    public static ItemStack createMineJob2Item() {
        return createJobItem(
                "§f광부 2차",
                "§7광부 : §f10%확률로 광물을 한개 더 얻습니다!",
                7000
        );
    }

    public static ItemStack createMineJob3Item() {
        return createJobItem(
                "§f광부 3차",
                "§7광부 : §f기본 패시브 화염저항이 생기며 블럭 50개를 캘때 마다 성급함2 효과를 얻습니다!",
                30000
        );
    }

    public static ItemStack createMineJob4Item() {
        return createJobItem(
                "§f광부 4차",
                "§7광부 : §f10%확률로 광물을 두개 더 얻습니다!",
                55000
        );
    }

    public static ItemStack createresetJobItem() {
        return createJobItem(
                "§f초기화권",
                "",
                10000
        );
    }
    public static ItemStack createOreItem(Material material, int data) {
        ItemStack oreItem = new ItemStack(material, 1);
        ItemMeta meta = oreItem.getItemMeta();

        // 커스텀 모델 데이터 값 설정
        int customModelDataValue = data;
        int currentPrice = itemPrices.getOrDefault(material.toString().toLowerCase() + "-" + customModelDataValue, itemDefaultPrices.getOrDefault(material.toString().toLowerCase() + "-" + customModelDataValue, 0));

        String priceText = currentPrice >= itemDefaultPrices.getOrDefault(material.toString().toLowerCase() + "-" + customModelDataValue, 0) ? "§c(" + (currentPrice - itemDefaultPrices.getOrDefault(material.toString().toLowerCase() + "-" + customModelDataValue, 0))+"▲)" : "§9(" + (itemDefaultPrices.getOrDefault(material.toString().toLowerCase() + "-" + customModelDataValue, 0) - currentPrice)+"▼)";

        meta.setLore(Arrays.asList("", "§l§a| §f구매 가격: §c구매 불가", " §l§7┗ §7좌클릭시 1개, 쉬프트+좌클릭 시 64개", "", "§l§c| §f판매 가격: §e"+ itemCost.getItemCost(oreItem,customModelDataValue) + "§f$" + priceText, " §l§7┗ §7우클릭시 1개, 쉬프트+우클릭 시 전체"));
        meta.setCustomModelData(customModelDataValue);
        oreItem.setItemMeta(meta);

        return oreItem;
    }


    public static ItemStack createFishItem(Material material, int data) {
        ItemStack fishItem = new ItemStack(material, 1);
        ItemMeta meta = fishItem.getItemMeta();

        // 커스텀 모델 데이터 값 설정
        int customModelDataValue = data; // 커스텀 모델 데이터 값 설정
        int currentPrice = itemPrices.getOrDefault(material.toString().toLowerCase() + "-" + customModelDataValue, itemDefaultPrices.getOrDefault(material.toString().toLowerCase() + "-" + customModelDataValue, 0));

        String priceText = currentPrice >= itemDefaultPrices.getOrDefault(material.toString().toLowerCase() + "-" + customModelDataValue, 0) ? "§c(" + (currentPrice - itemDefaultPrices.getOrDefault(material.toString().toLowerCase() + "-" + customModelDataValue, 0))+"▲)" : "§9(" + (itemDefaultPrices.getOrDefault(material.toString().toLowerCase() + "-" + customModelDataValue, 0) - currentPrice)+"▼)";

        meta.setLore(Arrays.asList("", "§l§a| §f구매 가격: §c구매 불가", " §l§7┗ §7좌클릭시 1개, 쉬프트+좌클릭 시 64개", "", "§l§c| §f판매 가격: §e"+ itemCost.getItemCost(fishItem,customModelDataValue) + "§f$" + priceText, " §l§7┗ §7우클릭시 1개, 쉬프트+우클릭 시 전체"));
        meta.setCustomModelData(customModelDataValue);
        fishItem.setItemMeta(meta);

        return fishItem;
    }
    public static ItemStack createSaleItem(Material material, int data,String displayName) {
        ItemStack SaleItem = new ItemStack(material, 1);
        ItemMeta meta = SaleItem.getItemMeta();

        // 커스텀 모델 데이터 값 설정
        int customModelDataValue = data; // 커스텀 모델 데이터 값 설정
        meta.setLore(Arrays.asList("", "§l§a| §f구매 가격: §e"+ itemCost.getItemCost(SaleItem,customModelDataValue) +"§f$" ," §l§7┗ §7좌클릭시 1개, 쉬프트+좌클릭 시 64개", "", "§l§c| §f판매 가격: §c판매 불가" , " §l§7┗ §7우클릭시 1개, 쉬프트+우클릭 시 전체"));
        meta.setCustomModelData(customModelDataValue);
        // 디스플레이 이름 설정
        meta.setDisplayName(displayName);
        SaleItem.setItemMeta(meta);

        return SaleItem;
    }
    public static ItemStack createItemsAdderItem(String itemId) {
        // itemsadder에서 생성한 아이템을 가져오는 코드
        ItemStack itemsadderItem = ItemsAdder.getCustomItem(itemId);
        ItemMeta meta = itemsadderItem.getItemMeta();
        Material material = itemsadderItem.getType();
        int customModelData = meta.getCustomModelData();
        // 커스텀 모델 데이터 값 사용
        int currentPrice = itemPrices.getOrDefault(material.toString().toLowerCase() + "-" + customModelData, itemDefaultPrices.getOrDefault(material.toString().toLowerCase() + "-" + customModelData, 0));

        String priceText = currentPrice >= itemDefaultPrices.getOrDefault(material.toString().toLowerCase() + "-" + customModelData, 0) ? "§c(" + (currentPrice - itemDefaultPrices.getOrDefault(material.toString().toLowerCase() + "-" + customModelData, 0))+"▲)" : "§9(" + (itemDefaultPrices.getOrDefault(material.toString().toLowerCase() + "-" + customModelData, 0) - currentPrice)+"▼)";

        meta.setLore(Arrays.asList("", "§l§a| §f구매 가격: §c구매 불가", " §l§7┗ §7좌클릭시 1개, 쉬프트+좌클릭 시 64개", "", "§l§c| §f판매 가격: §e"+ itemCost.getItemCost(itemsadderItem,customModelData) + "§f$" + priceText, " §l§7┗ §7우클릭시 1개, 쉬프트+우클릭 시 전체"));
        itemsadderItem.setItemMeta(meta);
        // 아이템 메타 수정 등 추가적인 작업을 수행할 수 있습니다.

        return itemsadderItem;
    }
    public static ItemStack createSaleItemsAdderItem(String itemId) {
        // itemsadder에서 생성한 아이템을 가져오는 코드
        ItemStack fishItem = ItemsAdder.getCustomItem(itemId);
        ItemMeta meta = fishItem.getItemMeta();
        int customModelData = meta.getCustomModelData();

        // 기존 아이템의 설명 가져오기
        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }

        // 추가 설명을 포함한 아이템 설명
        lore.add(""); // 빈 줄 추가
        lore.add("§l§a| §f구매 가격: §e" + itemCost.getItemCost(fishItem, customModelData) + " §f$");
        lore.add(" §l§7┗ §7좌클릭시 1개, 쉬프트+좌클릭 시 64개");
        lore.add("");
        lore.add("§l§c| §f판매 가격: §c판매 불가");
        lore.add(" §l§7┗ §7우클릭시 1개, 쉬프트+우클릭 시 전체");

        meta.setLore(lore);

        fishItem.setItemMeta(meta);
        // 아이템 메타 수정 등 추가적인 작업을 수행할 수 있습니다.

        return fishItem;
    }
    public static ItemStack createSaleCoinItemsAdderItem(String itemId) {
        // itemsadder에서 생성한 아이템을 가져오는 코드
        ItemStack fishItem = ItemsAdder.getCustomItem(itemId);
        ItemMeta meta = fishItem.getItemMeta();
        int customModelData = meta.getCustomModelData();

        // 기존 아이템의 설명 가져오기
        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }

        // 추가 설명을 포함한 아이템 설명
        lore.add(""); // 빈 줄 추가
        lore.add("§l§a| §f구매 가격: §e" + itemCost.itemCoinCost(fishItem, customModelData) + " §f갈치 코인");
        lore.add(" §l§7┗ §7좌클릭시 1개, 쉬프트+좌클릭 시 64개");
        lore.add("");
        lore.add("§l§c| §f판매 가격: §c판매 불가");
        lore.add(" §l§7┗ §7우클릭시 1개, 쉬프트+우클릭 시 전체");

        meta.setLore(lore);

        fishItem.setItemMeta(meta);
        // 아이템 메타 수정 등 추가적인 작업을 수행할 수 있습니다.

        return fishItem;
    }
    public static ItemStack createSaleHuntCoinItemsAdderItem(String itemId) {
        // itemsadder에서 생성한 아이템을 가져오는 코드
        ItemStack fishItem = ItemsAdder.getCustomItem(itemId);
        ItemMeta meta = fishItem.getItemMeta();
        int customModelData = meta.getCustomModelData();

        // 기존 아이템의 설명 가져오기
        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }

        // 추가 설명을 포함한 아이템 설명
        lore.add(""); // 빈 줄 추가
        lore.add("§l§a| §f구매 가격: §e" + itemCost.itemCoinCost(fishItem, customModelData) + " §f사냥 코인");
        lore.add(" §l§7┗ §7좌클릭시 1개, 쉬프트+좌클릭 시 64개");
        lore.add("");
        lore.add("§l§c| §f판매 가격: §c판매 불가");
        lore.add(" §l§7┗ §7우클릭시 1개, 쉬프트+우클릭 시 전체");

        meta.setLore(lore);

        fishItem.setItemMeta(meta);
        // 아이템 메타 수정 등 추가적인 작업을 수행할 수 있습니다.

        return fishItem;
    }

    public static ItemStack createFarmerJob1Item() {
        return createJobItem(
                "§f농부 1차",
                "§7농부 : §f10% 확률로 농작물 추가+1",
                5000
        );
    }

    public static ItemStack createFarmerJob2Item() {
        return createJobItem(
                "§f농부 2차",
                "§7농부 : §f귀속 물뿌리개 지급",
                10000
        );
    }

    public static ItemStack createFarmerJob3Item() {
        return createJobItem(
                "§f농부 3차",
                "§7농부 : §f기본 작물 씨앗 자동 심기 기능 추가",
                30000
        );
    }

    public static ItemStack createFarmerJob4Item() {
        return createJobItem(
                "§f농부 4차",
                "§7농부 : §f10% 확률로 농작물 추가+2",
                55000
        );
    }

    public static ItemStack createFisherJob1Item() {
        return createJobItem(
                "§f어부 1차",
                "§7어부 : §f잡을 수 있는 물고기 개체 수 증가",
                5000
        );
    }
    public static ItemStack createFisherJob2Item() {
        return createJobItem(
                "§f어부 2차",
                "§7어부 : §f10% 확률로 추가 물고기+1",
                8000
        );
    }
    public static ItemStack createFisherJob3Item() {
        return createJobItem(
                "§f어부 3차",
                "§7어부 : §f기본 패시브 수중호흡 적용",
                30000
        );
    }
    public static ItemStack createFisherJob4Item() {
        return createJobItem(
                "§f어부 4차",
                "§7어부 : §f10% 확률로 추가 물고기+2",
                45000
        );
    }
    public static ItemStack createCookerJob1Item() {
        return createJobItem(
                "§f요리사 1차",
                "§7요리사 : §f10% 확률로 요리 추가 +1",
                7000
        );
    }
    public static ItemStack createCookerJob2Item() {
        return createJobItem(
                "§f요리사 2차",
                "§7요리사 : §f요리 한번에 5개 가능",
                7000
        );
    }
    public static ItemStack createCookerJob3Item() {
        return createJobItem(
                "§f요리사 3차",
                "§7요리사 : §f랜덤 레시피 북 1개+ 요리 한번에 10개 가능",
                7000
        );
    }
    public static ItemStack createCookerJob4Item() {
        return createJobItem(
                "§f요리사 4차",
                "§7요리사 : §f10% 확률로 요리 추가 +2",
                7000
        );
    }
    public static ItemStack namechange() {
        ItemStack check = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = check.getItemMeta();
        meta.setDisplayName("이름 설정권");
        //커스텀 모델 데이터 값 설정
        int customModelDataValue = 5000; // 커스텀 모델 데이터 값 5000로 설정
        meta.setCustomModelData(customModelDataValue);
        check.setItemMeta(meta);
        return check;
    }
    public static ItemStack InvenSaveScroll() {
        ItemStack check = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = check.getItemMeta();
        meta.setDisplayName("인벤 세이브권");
        //커스텀 모델 데이터 값 설정
        int customModelDataValue = 5000; // 커스텀 모델 데이터 값 5000로 설정
        meta.setCustomModelData(customModelDataValue);
        check.setItemMeta(meta);
        return check;
    }
    public static ItemStack lockdoor() {
        ItemStack check = new ItemStack(Material.IRON_DOOR, 1);
        ItemMeta meta = check.getItemMeta();
        meta.setDisplayName("비밀 번호 문");

        // 커스텀 모델 데이터 값 설정
        int customModelDataValue = 1; // 커스텀 모델 데이터 값 1로 설정
        meta.setCustomModelData(customModelDataValue);

        check.setItemMeta(meta);
        return check;
    }
    public static ItemStack locksign(String key) {
        ItemStack check = new ItemStack(Material.OAK_SIGN, 1);
        ItemMeta meta = check.getItemMeta();
        meta.setDisplayName("비밀번호:"+key);


        check.setItemMeta(meta);
        return check;
    }
    public static ItemStack locknum(int key) {
        ItemStack check = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta meta = check.getItemMeta();
        meta.setDisplayName(String.valueOf(key));


        check.setItemMeta(meta);
        return check;
    }
    public static ItemStack lockdelete() {
        ItemStack check = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemMeta meta = check.getItemMeta();
        meta.setDisplayName(ChatColor.RED+"삭제");


        check.setItemMeta(meta);
        return check;
    }
    public static ItemStack lockconfirm() {
        ItemStack check = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
        ItemMeta meta = check.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN+"확인");


        check.setItemMeta(meta);
        return check;
    }
}