package org.mrdarkimc.satanicseller.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.mrdarkimc.satanicmenus.MenuOpener;
import org.mrdarkimc.satanicmenus.menus.BasicMenu;
import org.mrdarkimc.satanicseller.SatanicSeller;

import java.util.function.Supplier;

public class PreviewCommand implements CommandExecutor {
    static {
        BasicMenu basicMenu = SatanicSeller.getInstance().getFactory().create("preview");
        Supplier<BasicMenu> supp = () -> basicMenu;
        MenuOpener.register("pricepreview",supp);
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        MenuOpener.open(player,"pricepreview");
        return true;
    }
}
