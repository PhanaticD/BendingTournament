package com.Jacksonnn.BendingTournament.Storage;

import com.Jacksonnn.BendingTournament.BendingTournament;

import java.io.File;
import java.sql.SQLException;

public class DatabaseManager {

    private Database database;
    private BendingTournament plugin;

    public DatabaseManager(BendingTournament BendingTournament) {
        plugin = BendingTournament;
        database = loadDatabase();
    }

    private Database loadDatabase() {
        Database database = null;
        if (plugin.getConfig().getString("Storage.engine").equalsIgnoreCase("sqlite")) {
            database = new SqlLite(plugin.getDataFolder() + File.separator + plugin.getConfig().getString("Storage.settings.path"));
        } else if (plugin.getConfig().getString("Storage.engine").equalsIgnoreCase("mysql")) {
            database = new Mysql(plugin.getConfig()
                    .getString("Storage.settings.username"), plugin.getConfig()
                    .getString("Storage.settings.password"), plugin.getConfig()
                    .getString("Storage.settings.host"), plugin.getConfig()
                    .getString("Storage.settings.port"), plugin.getConfig()
                    .getString("Storage.settings.database"), plugin.getConfig()
                    .getBoolean("Storage.settings.ssl"));
        }
        return database;
    }

    public void init() throws SQLException, ClassNotFoundException {
        database.init();
    }

    public Database getDatabase() {
        return database;
    }
}
