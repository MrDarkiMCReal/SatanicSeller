package org.mrdarkimc.satanicseller.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.mrdarkimc.satanicseller.menus.MenuFactory;

public class OpenCommand implements CommandExecutor {
    private MenuFactory factory;

    public OpenCommand(MenuFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player){
            //SatanicSeller.sellermenu.openTo(player);
            factory.create().openTo(player);
            //SellerMenu.sellermenu.openTo(player);
            //player.openInventory(new SellerMenu().getInventory());
        }
        return false;
    }
}
