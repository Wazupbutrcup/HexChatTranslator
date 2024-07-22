package us.wazupbutrcup.hexChatTranslator.Database;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {

    private Connection connection;

    public DatabaseManager(JavaPlugin plugin) {
        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Establish a connection to the SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder() + "/players.db");

            // Create the 'players' table if it does not exist
            try (PreparedStatement statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS players (uuid TEXT PRIMARY KEY, language TEXT)")) {
                statement.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Sets or updates a player's language in the database
    public void setPlayerLanguage(String uuid, String language) {
        try (PreparedStatement statement = connection.prepareStatement(
                "REPLACE INTO players (uuid, language) VALUES (?, ?)")) {
            statement.setString(1, uuid);
            statement.setString(2, language);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieves a player's language from the database
    public String getPlayerLanguage(String uuid, String defaultLanguage) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT language FROM players WHERE uuid = ?")) {
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("language");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return defaultLanguage; // Return default language if not found
    }

    // Closes the database connection
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
