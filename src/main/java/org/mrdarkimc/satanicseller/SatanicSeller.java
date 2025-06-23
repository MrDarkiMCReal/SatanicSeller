package org.mrdarkimc.satanicseller;

import org.bukkit.plugin.java.JavaPlugin;
import org.mrdarkimc.SatanicLib.currency.Vault;
import org.mrdarkimc.SatanicLib.currency.interfaces.Currency;

public final class SatanicSeller extends JavaPlugin {
    private static Currency currency;
    private static SatanicSeller instance;

    public static SatanicSeller getInstance() {
        return instance;
    }

    public static Currency getCurrency() {
        return currency;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        currency = new Vault();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
