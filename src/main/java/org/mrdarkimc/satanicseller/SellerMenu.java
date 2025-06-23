package org.mrdarkimc.satanicseller;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class SellerMenu implements InventoryHolder {
    private final Inventory inventory;
    {
        SatanicSeller instance = SatanicSeller.getInstance();
        inventory = instance.getServer().createInventory(this,9);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
