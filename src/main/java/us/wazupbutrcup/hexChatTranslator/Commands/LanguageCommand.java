package us.wazupbutrcup.hexChatTranslator.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.wazupbutrcup.hexChatTranslator.Database.DatabaseManager;
import us.wazupbutrcup.hexChatTranslator.HexChatTranslator;

public class LanguageCommand implements CommandExecutor {

    private final DatabaseManager databaseManager;
    private final HexChatTranslator plugin;

    public LanguageCommand(DatabaseManager databaseManager, HexChatTranslator plugin) {
        this.databaseManager = databaseManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getPrefix() + " " + plugin.getConfig().getString("messages.non-player"));
            return true;
        }

        Player player = (Player) sender;
        if (args.length != 1) {
            player.sendMessage(plugin.getPrefix() + " " + plugin.getConfig().getString("messages.usage"));
            return true;
        }

        String language = args[0];
        databaseManager.setPlayerLanguage(player.getUniqueId().toString(), language);
        player.sendMessage(plugin.getPrefix() + " " + plugin.getConfig().getString("messages.language-set").replace("{language}", language));
        return true;
    }
}
