package dev.kopo.skyblockcore.providers;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class VaultProvider {

    private final Economy eco;

    @Inject
    public VaultProvider() {
        RegisteredServiceProvider<Economy> registration = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (registration == null) {
            throw new NullPointerException("Failed to register economy hook, is Vault enabled?");
        } else {
            this.eco = registration.getProvider();
        }
    }

    public Economy getEconomy() {
        return eco;
    }
}
