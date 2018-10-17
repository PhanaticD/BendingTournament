package com.Jacksonnn.BendingTournament.Tournaments;

import com.Jacksonnn.BendingTournament.BendingTournament;
import com.Jacksonnn.BendingTournament.GeneralMethods;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TournamentListener implements Listener {
    private BendingTournament plugin;

    public TournamentListener(BendingTournament plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!plugin.getBtManager().hasUser(e.getPlayer().getUniqueId())) {
            plugin.getBtManager().createUser(e.getPlayer().getUniqueId());
            Bukkit.getServer().getLogger().warning(GeneralMethods.prefix + "Player, " + e.getPlayer().getName() + ", is not in the database... creating user.");
        }
    }
}
