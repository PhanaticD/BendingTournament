package com.Jacksonnn.BendingTournament;

import com.Jacksonnn.BendingTournament.Configuration.ConfigManager;
import com.Jacksonnn.BendingTournament.Storage.DatabaseManager;
import com.Jacksonnn.BendingTournament.Storage.Mysql;
import com.Jacksonnn.BendingTournament.Storage.SqlQueries;
import com.Jacksonnn.BendingTournament.Tournaments.TournamentCommand;
import com.Jacksonnn.BendingTournament.Tournaments.TournamentListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class BendingTournament extends JavaPlugin {

    public static BendingTournament plugin;
    private PluginManager pm = Bukkit.getServer().getPluginManager();
    private DatabaseManager databaseManager;
    private BTManager btManager;

    public void onEnable() {
        plugin = this;
        new ConfigManager();
        databaseManager = new DatabaseManager(this);
        try {
            databaseManager.init();
        } catch (SQLException e) {
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }

        try {
            installDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        btManager = new BTManager(this);
        pm.registerEvents(new TournamentListener(this), this);
        this.getCommand("bendingtournament").setExecutor(new TournamentCommand(this));
        Bukkit.getServer().getLogger().info(GeneralMethods.successColor + "BendingTournament has successfully been enabled!");
    }

    private void installDatabase() throws SQLException {
        if (databaseManager.getDatabase() instanceof Mysql) {
            databaseManager.getDatabase().getConnection().createStatement().execute(SqlQueries.CREATE_TOURNAMENTS.getMysqlQuery());
            databaseManager.getDatabase().getConnection().createStatement().execute(SqlQueries.CREATE_USERS.getMysqlQuery());
        } else {
            databaseManager.getDatabase().getConnection().createStatement().execute(SqlQueries.CREATE_TOURNAMENTS.getSqliteQuery());
            databaseManager.getDatabase().getConnection().createStatement().execute(SqlQueries.CREATE_USERS.getSqliteQuery());
        }
    }

    public void onDisable() {
        Bukkit.getServer().getLogger().info("BendingTournament has successfully been disabled!");
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public BTManager getBtManager() {
        return btManager;
    }

}
