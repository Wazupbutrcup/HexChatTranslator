package us.wazupbutrcup.hexChatTranslator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import us.wazupbutrcup.hexChatTranslator.Database.DatabaseManager;
import us.wazupbutrcup.hexChatTranslator.Listeners.*;
import us.wazupbutrcup.hexChatTranslator.Commands.LanguageCommand;

public final class HexChatTranslator extends JavaPlugin {

    private DatabaseManager databaseManager;
    private Translator translator;
    private String prefix;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        String apiKey = getConfig().getString("google-api-key");
        translator = new Translator(apiKey);
        databaseManager = new DatabaseManager(this);

        prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("plugin-prefix", "&7[&aHexChatTranslator&7]&r "));

        // Register default chat listener
        Bukkit.getPluginManager().registerEvents(new ChatListener(this, translator, databaseManager), this);

        // Check for and register chat listeners for supported plugins
        if (Bukkit.getPluginManager().isPluginEnabled("EssentialsX")) {
            Bukkit.getPluginManager().registerEvents(new EssentialsXChatListener(this, translator, databaseManager), this);
        }
        if (Bukkit.getPluginManager().isPluginEnabled("LuckPerms")) {
            Bukkit.getPluginManager().registerEvents(new LuckPermsChatListener(this, translator, databaseManager), this);
        }
        getCommand("language").setExecutor(new LanguageCommand(databaseManager, this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (databaseManager != null) {
            databaseManager.close();
        }
    }

    public Translator getTranslator() {
        return translator;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public String getPrefix() {
        return prefix;
    }
}
