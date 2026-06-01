package org.mrdarkimc.satanicseller.menus;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.mrdarkimc.SatanicLib.Utils;
import org.mrdarkimc.SatanicLib.configsetups.Configs;
import org.mrdarkimc.satanicmenus.menus.BasicMenu;
import org.mrdarkimc.satanicseller.SatanicSeller;
import org.mrdarkimc.satanicseller.menus.preview.PreviewMenu;
import org.mrdarkimc.satanicseller.menus.sellermenu.MenuService;
import org.mrdarkimc.satanicseller.menus.sellermenu.SellerMenu;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class MenuFactory {
    MenuService service = new MenuService();
    BasicMenu menu;

    public BasicMenu create() {
        SatanicSeller instance = SatanicSeller.getInstance();
        FileConfiguration configuration = instance.getSatanicConfig().get();
        String header = configuration.getString("sellermenu.header");
        int inventorySize = configuration.getInt("sellermenu.rows");
        SellerMenu sellerMenu = new SellerMenu(header, inventorySize, service);
        Inventory inventory = sellerMenu.getInventory();
        fillDefaults(inventory);
        return sellerMenu;
    }

    public BasicMenu create(String name) {
        SatanicSeller instance = SatanicSeller.getInstance();
        FileConfiguration configuration = instance.getSatanicConfig().get();
        String header = configuration.getString(name + ".header");
        int inventorySize = configuration.getInt(name + ".rows");
        BasicMenu sellerMenu = null;
        switch (name) {
            case "preview":
                sellerMenu = new PreviewMenu(header);
                break;
            case "sellermenu":
                sellerMenu = new SellerMenu(header, inventorySize, service);
                break;
            default:
                break;
        }
        Inventory inventory = sellerMenu.getInventory();
        fillDefaults(name, inventory);
        return sellerMenu;

    }

    public BasicMenu createPreview() {
        return create("preview");
    }

    public void fillDefaults(Inventory inventory) {
        Configs conf = SatanicSeller.getInstance().getSatanicConfig();
        conf.get().getStringList("sellermenu.items");
        Set<String> keys = conf.get().getConfigurationSection("sellermenu.items").getKeys(false);
        for (String key : keys) {
            String name = conf.get().getString("sellermenu.items." + key + ".name");
            name = Utils.hexAndPAPI(name, null);
            List<String> stringList = conf.get().getStringList("sellermenu.items." + key + ".lore");
            stringList.replaceAll(e -> Utils.hexAndPAPI(e, null));
            int slot = conf.get().getInt("sellermenu.items." + key + ".slot");
            String material = conf.get().getString("sellermenu.items." + key + ".id");
            int cmd = conf.get().getInt("sellermenu.items." + key + ".cmd");
            int playerhead = conf.get().getInt("sellermenu.items." + key + ".playerhead");
            ItemStack stack = new ItemStack(Material.valueOf(material.toUpperCase()), 1);
            ItemMeta itemMeta = stack.getItemMeta();
            itemMeta.setLore(stringList);
            itemMeta.setDisplayName(name);
            itemMeta.setCustomModelData(cmd);
            stack.setItemMeta(itemMeta);
            inventory.setItem(slot, stack);
        }
    }

    public void fillDefaults(String menuname, Inventory inventory) {
        Configs conf = SatanicSeller.getInstance().getSatanicConfig();
        conf.get().getStringList(menuname + ".items");
        Set<String> keys = conf.get().getConfigurationSection(menuname + ".items").getKeys(false);
        for (String key : keys) {
            String name = conf.get().getString(menuname + ".items." + key + ".name");
            name = Utils.hexAndPAPI(name, null);
            List<String> stringList = conf.get().getStringList(menuname + ".items." + key + ".lore");
            stringList.replaceAll(e -> Utils.hexAndPAPI(e, null));
            int slot = conf.get().getInt(menuname + ".items." + key + ".slot");
            String material = conf.get().getString(menuname + ".items." + key + ".id");
            int cmd = conf.get().getInt(menuname + ".items." + key + ".cmd");
            ItemStack stack = new ItemStack(Material.valueOf(material.toUpperCase()), 1);
            ItemMeta itemMeta = stack.getItemMeta();
            itemMeta.setLore(stringList);
            itemMeta.setDisplayName(name);
            itemMeta.setCustomModelData(cmd);
            stack.setItemMeta(itemMeta);
            inventory.setItem(slot, stack);
        }
    }
}
