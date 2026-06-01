package org.mrdarkimc.satanicseller.menus.preview;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.mrdarkimc.SatanicLib.Utils;
import org.mrdarkimc.itemworth.WorthProvider.PriceList;
import org.mrdarkimc.itemworth.WorthProvider.Priceable;
import org.mrdarkimc.satanicmenus.buttons.DisplayButton;
import org.mrdarkimc.satanicmenus.buttons.MenuButton;
import org.mrdarkimc.satanicmenus.clickactions.MenuClickAction;
import org.mrdarkimc.satanicmenus.menus.PageableMenu;
import org.mrdarkimc.satanicmenus.utils.BlockedSlots;
import org.mrdarkimc.satanicseller.SatanicSeller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PreviewMenu extends PageableMenu {
    private static final List<MenuButton> contents;

    static {
        contents = PriceList.getPriceables().entrySet().stream()
                .sorted((o1, o2) -> Double.compare(o2.getValue().getSellprice(), o1.getValue().getSellprice()))
                .map(k -> PreviewMenu.createTemplate(k.getKey(), k.getValue()))
                .collect(Collectors.toList());

    }

    public PreviewMenu(String header) {
        super(header, 54, 44, 36);
        activeSlots = new int[]{
                1, 2, 3, 4, 5, 6, 7,
                10, 11, 12, 13, 14, 15, 16,
                19, 20, 21, 22, 23, 24, 25,
                28, 29, 30, 31, 32, 33, 34,
                // 37,38,39,40,41,42,43
        };

        addButton(createBackToSellerButton(), 49,true);
        calculateAndSetMaxPages(contents.size(), activeSlots.length);
        blockedSlots = BlockedSlots.getUpperInventoryBlock();
    }

    @Override
    protected void fillPage(int i) {
        removeButtonsBySlotIds(activeSlots, true);
        fillPage(contents, 28, true);
        System.out.println("current page: " + currentPageNum);
        System.out.println("total pages: " + maxPages);
    }

    private MenuButton createBackToSellerButton(){
        MenuClickAction action = (k, v) -> SatanicSeller.getInstance().getFactory().create("sellermenu").openTo(k);
        ItemStack itemStack = new ItemStack(Material.EMERALD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(PlaceholderAPI.setPlaceholders(null,"%design_clr_main%Вернуться к скупщику"));
        itemStack.setItemMeta(itemMeta);
        MenuButton menuButton = new MenuButton(itemStack, action);
        return menuButton;
    }
    @Override
    public void onOpen(Player player) {
        fillPage(contents, 28, true);
        createHead(player);
    }

    @Override
    public void onClose(Player player) {

    }
    private void createHead(Player player) {
        ItemStack stack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta itemMeta = (SkullMeta) stack.getItemMeta();
        itemMeta.setCustomModelData(10007);
        itemMeta.setOwningPlayer(player);
        stack.setItemMeta(itemMeta);
        getInventory().setItem(44, stack);
    }

    private static MenuButton createTemplate(String material, Priceable priceable) {
        ItemStack stack = defineStack(material);
        ItemMeta itemMeta = stack.getItemMeta();
        int price = priceable.getSellprice();
        List<String> lore = new ArrayList<>(List.of("\n","%design_clr_main%Цена: &f%img_money%%design_clr_money%" + formatPrice(price) + "\n"));
        lore.replaceAll(Utils::translateHex);
        lore.replaceAll(s -> PlaceholderAPI.setPlaceholders(null, s));
        itemMeta.setLore(lore);
        stack.setItemMeta(itemMeta);
        return new DisplayButton(stack);
    }

    private static ItemStack defineStack(String materialName) {
        Material material = Material.matchMaterial(materialName);
        if (material != null) {
            return new ItemStack(material);
        } else {
            Bukkit.getLogger().warning("Material: " + materialName + " невозможно создать итемстак");
        }
        return new ItemStack(Material.STONE);
    }

    private static String formatPrice(int price) {
        String priceStr = String.valueOf(price);
        StringBuilder result = new StringBuilder();
        int count = 0;

        for (int i = priceStr.length() - 1; i >= 0; i--) {
            result.append(priceStr.charAt(i));
            count++;

            if (count % 3 == 0 && i > 0) {
                result.append(",");
            }
        }

        return result.reverse().toString();
    }
}
