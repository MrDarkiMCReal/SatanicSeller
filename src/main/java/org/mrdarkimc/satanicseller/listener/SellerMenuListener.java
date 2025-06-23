package org.mrdarkimc.satanicseller.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.mrdarkimc.SatanicLib.messages.KeyedMessage;
import org.mrdarkimc.itemworth.WorthProvider.PriceChecker;
import org.mrdarkimc.satanicseller.SatanicSeller;
import org.mrdarkimc.satanicseller.SellerMenu;


public class SellerMenuListener implements Listener {
    //    List<String> slots;
//    {
//        slots;
//    }
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof SellerMenu menu) {
            Player player = (Player) e.getPlayer();
            Inventory inventory = menu.getInventory();
            int price = 0;
            for (int i = 0; i <= 44; i++) {
                ItemStack item = inventory.getItem(i);
                if (item != null) {
                    price = price + PriceChecker.checkSellPrice(item);
                    //item.setAmount(0);
                }
            }
            SatanicSeller.getCurrency().addMoney(player,price);
            new KeyedMessage(player,).send();
        }
    }
}
