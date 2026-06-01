package org.mrdarkimc.satanicseller.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.mrdarkimc.SatanicLib.messages.Message;
import org.mrdarkimc.itemworth.WorthProvider.PriceChecker;
import org.mrdarkimc.satanicmenus.utils.BlockedSlots;
import org.mrdarkimc.satanicseller.SatanicSeller;
import org.mrdarkimc.satanicseller.SellerMenu;


public class SellerMenuListener implements Listener {
    //    List<String> slots;
//    {
//        slots;
//    }
    public static boolean[] blockedSlots;
    static{
        blockedSlots = BlockedSlots.getUpperInventoryLeftAndRightColumnBlock();
        blockedSlots[46] = true;
        blockedSlots[47] = true;
        blockedSlots[48] = true;
        blockedSlots[49] = true;
        blockedSlots[50] = true;
        blockedSlots[51] = true;
        blockedSlots[52] = true;
    }
    int[] slots_for_sale = new int[]{
            1,2,3,4,5,6,7,
            10,11,12,13,14,15,16,
            19,20,21,22,23,24,25,
            28,29,30,31,32,33,34,
            37,38,39,40,41,42,43
    };
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof SellerMenu menu) {
            Player player = (Player) e.getPlayer();
            Inventory inventory = menu.getInventory();
            int price = 0;
            for (int i : slots_for_sale) {
                ItemStack item = inventory.getItem(i);
                if (item != null) {
                    price = price + PriceChecker.getPriceForSellingItem(item);
                    item.setAmount(0);
                }
            }
            if (price!=0) {
                SatanicSeller.getCurrency().addMoney(player, price);
                new Message(player, "Вы продали шмота на %s".formatted(price), null).send();
            }
        }
    }
    public boolean hasClickedToSellSlots(int slotID){ //todo оптимизировать поиск по индексу, а не итерацией. индекс - № слота
        for (int i = 0; i < slots_for_sale.length; i++) {
            if (slots_for_sale[i]==slotID){
                return true;
            }
        }
        return false;
    }
    @EventHandler
    public void onclick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof SellerMenu menu) {
            int slot = e.getSlot();
            Inventory clickedInventory = e.getClickedInventory();
            if (clickedInventory.getHolder() instanceof SellerMenu){
                if (!hasClickedToSellSlots(slot)){
                    e.setCancelled(true);
                    return;
                }
            }


        }
    }
}
