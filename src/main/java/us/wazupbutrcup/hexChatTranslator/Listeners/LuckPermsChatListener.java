package us.wazupbutrcup.hexChatTranslator.Listeners;

import net.luckperms.api.LuckPerms;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import us.wazupbutrcup.hexChatTranslator.Database.DatabaseManager;
import us.wazupbutrcup.hexChatTranslator.HexChatTranslator;
import us.wazupbutrcup.hexChatTranslator.Translator;

public class LuckPermsChatListener implements Listener {

    private final HexChatTranslator plugin;
    private final Translator translator;
    private final DatabaseManager databaseManager;
    private final LuckPerms luckPerms;

    public LuckPermsChatListener(HexChatTranslator plugin, Translator translator, DatabaseManager databaseManager) {
        this.plugin = plugin;
        this.translator = translator;
        this.databaseManager = databaseManager;
        this.luckPerms = luckPerms;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        String playerUUID = event.getPlayer().getUniqueId().toString();
        String playerMessage = event.getMessage();

        // Retrieve the player's preferred language from the database
        String language = databaseManager.getPlayerLanguage(playerUUID, plugin.getConfig().getString("default-language"));

        // Translate the message
        String translatedMessage = translator.translate(playerMessage, language, playerUUID);

        // Send the translated message back to the player
        event.getPlayer().sendMessage(translatedMessage);

        // Optionally, cancel the original message to avoid double messages
        // event.setCancelled(true);
    }
}
