package org.mrdarkimc.satanicseller.menus.sellermenu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.mrdarkimc.itemworth.WorthProvider.PriceChecker;
import org.mrdarkimc.satanicmenus.menus.BasicMenu;

public class MenuService {
    int[] slots_for_sale = new int[]{
            1,2,3,4,5,6,7,
            10,11,12,13,14,15,16,
            19,20,21,22,23,24,25,
            28,29,30,31,32,33,34
    };
    public int sellAndGetTotalPriceForItems(Player player, BasicMenu menu){
        Inventory inventory = menu.getInventory();
        int price = 0;
        for (int i : slots_for_sale) {
            ItemStack item = inventory.getItem(i);
            if (item != null) {
                int itemprice = PriceChecker.getPriceForSellingItem(item);
                if (itemprice==0) {
                    player.getWorld().dropItemNaturally(player.getLocation(),item);
                }
                price = price + itemprice;
                item.setAmount(0);
            }
        }
        return price;
    }
}
