package org.mrdarkimc.satanicseller;

import org.bukkit.plugin.java.JavaPlugin;
import org.mrdarkimc.SatanicLib.SatanicLib;
import org.mrdarkimc.SatanicLib.configsetups.Configs;
import org.mrdarkimc.SatanicLib.currency.Vault;
import org.mrdarkimc.SatanicLib.currency.interfaces.Currency;
import org.mrdarkimc.satanicmenus.menus.BasicMenu;
import org.mrdarkimc.satanicseller.commands.OpenCommand;
import org.mrdarkimc.satanicseller.commands.PreviewCommand;
import org.mrdarkimc.satanicseller.menus.MenuFactory;


public final class SatanicSeller extends JavaPlugin {
    private static Currency currency;
    private static SatanicSeller instance;
    public static BasicMenu menu;
    private Configs satanicConfig;
    private MenuFactory factory;

    public MenuFactory getFactory() {
        return factory;
    }

    public Configs getSatanicConfig() {
        return satanicConfig;
    }

    public static SatanicSeller getInstance() {
        return instance;
    }

    public static Currency getCurrency() {
        return currency;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        SatanicLib.setupLib(this);
        instance = this;
        currency = new Vault();
        satanicConfig = Configs.Defaults.setupConfig();
        factory = new MenuFactory();
        menu = factory.create();
        //new SellerMenu()
        getServer().getPluginCommand("seller").setExecutor(new OpenCommand(factory));
        getServer().getPluginCommand("sprev").setExecutor(new PreviewCommand());
        //getServer().getPluginManager().registerEvents(new SellerMenuListener(),this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
