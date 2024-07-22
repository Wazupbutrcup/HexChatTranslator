package us.wazupbutrcup.hexChatTranslator.Listeners;

import net.ess3.api.events.PrivateMessageSentEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import us.wazupbutrcup.hexChatTranslator.Database.DatabaseManager;
import us.wazupbutrcup.hexChatTranslator.HexChatTranslator;
import us.wazupbutrcup.hexChatTranslator.Translator;

public class EssentialsXChatListener implements Listener {

    private final HexChatTranslator plugin;
    private final Translator translator;
    private final DatabaseManager databaseManager;

    public EssentialsXChatListener(HexChatTranslator plugin, Translator translator, DatabaseManager databaseManager) {
        this.plugin = plugin;
        this.translator = translator;
        this.databaseManager = databaseManager;
    }

    @EventHandler
    public void onPrivateMessageSent(PrivateMessageSentEvent event) {
        // Translation logic for EssentialsXChat
    }
}
