package org.mrdarkimc.satanicseller;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.mrdarkimc.SatanicLib.ConfigAPI.Config;
import org.mrdarkimc.SatanicLib.configsetups.Configs;

import java.util.List;
import java.util.Set;

public class SellerMenu implements InventoryHolder {
    private final Inventory inventory;
    {
        SatanicSeller instance = SatanicSeller.getInstance();
        FileConfiguration configuration = instance.getSatanicConfig().get();
        String header = configuration.getString("sellermenu.header");
        int anInt = configuration.getInt("sellermenu.rows");
        header = PlaceholderAPI.setPlaceholders(null, header);
        inventory = instance.getServer().createInventory(this,anInt,header);
        fillDefaults();
    }
    public void fillDefaults(){
        Config conf = SatanicSeller.getInstance().getSatanicConfig();
        conf.get().getStringList("sellermenu.items");
        Set<String> keys = conf.get().getConfigurationSection("sellermenu.items").getKeys(false);
        for (String key : keys) {
            String  name = conf.get().getString("sellermenu.items." + key + ".name");
            List<String> stringList = conf.get().getStringList("sellermenu.items." + key + ".lore");
            int slot = conf.get().getInt("sellermenu.items." + key + ".slot");
            String  material = conf.get().getString("sellermenu.items." + key + ".id");
            int cmd = conf.get().getInt("sellermenu.items." + key + ".cmd");
            ItemStack stack = new ItemStack(Material.valueOf(material.toUpperCase()), 1);
            ItemMeta itemMeta = stack.getItemMeta();
            itemMeta.setLore(stringList);
            itemMeta.setDisplayName(name);
            itemMeta.setCustomModelData(cmd);
            stack.setItemMeta(itemMeta);
            inventory.setItem(slot,stack);
        }

    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
