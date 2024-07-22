package us.wazupbutrcup.hexChatTranslator.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import us.wazupbutrcup.hexChatTranslator.HexChatTranslator;
import us.wazupbutrcup.hexChatTranslator.Translator;
import us.wazupbutrcup.hexChatTranslator.Database.DatabaseManager;

public class ChatListener implements Listener {

    private final HexChatTranslator plugin;
    private final Translator translator;
    private final DatabaseManager databaseManager;

    public ChatListener(HexChatTranslator plugin, Translator translator, DatabaseManager databaseManager) {
        this.plugin = plugin;
        this.translator = translator;
        this.databaseManager = databaseManager;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        // Get the player's preferred language from the database or default to English
        String language = databaseManager.getPlayerLanguage(player.getUniqueId().toString(), "en");

        // Translate the message
        String translatedMessage = translator.translate(message, "en", language); // Assuming 'en' is the source language

        // Send the translated message
        event.setMessage(ChatColor.translateAlternateColorCodes('&', translatedMessage));
    }
}
