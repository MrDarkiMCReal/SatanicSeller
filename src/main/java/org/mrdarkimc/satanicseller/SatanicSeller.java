package org.mrdarkimc.satanicseller;

import org.bukkit.plugin.java.JavaPlugin;
import org.mrdarkimc.SatanicLib.ConfigAPI.Config;
import org.mrdarkimc.SatanicLib.ConfigAPI.MessagesConfig;
import org.mrdarkimc.SatanicLib.currency.Vault;
import org.mrdarkimc.SatanicLib.currency.interfaces.Currency;
import org.mrdarkimc.satanicseller.commands.OpenCommand;
import org.mrdarkimc.satanicseller.commands.PreviewCommand;
import org.mrdarkimc.satanicseller.menus.MenuFactory;

import java.util.Locale;


public final class SatanicSeller extends JavaPlugin {
    private static Currency currencyDollar;
    private static SatanicSeller instance;

    public static SatanicSeller getInstance() {
        return instance;
    }

    public static Currency getDollarCurrency() {
        return currencyDollar;
    }

    private Config satanicConfig;
    private MessagesConfig messagesRu;
    private MenuFactory factory;

    public MenuFactory getFactory() {
        return factory;
    }

    public Config getSatanicConfig() {
        return satanicConfig;
    }


    @Override
    public void onEnable() {
        instance = this;
        currencyDollar = new Vault();
        this.satanicConfig = new Config(this, "config");
        this.messagesRu = new MessagesConfig(this, "messages_ru", Locale.forLanguageTag("ru"));
        this.factory = new MenuFactory();
        messagesRu.load();
        getServer().getPluginCommand("seller").setExecutor(new OpenCommand(factory));
        getServer().getPluginCommand("sprev").setExecutor(new PreviewCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
