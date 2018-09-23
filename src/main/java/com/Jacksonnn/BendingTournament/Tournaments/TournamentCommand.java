package com.Jacksonnn.BendingTournament.Tournaments;

import com.Jacksonnn.BendingTournament.BendingTournament;
import com.Jacksonnn.BendingTournament.GeneralMethods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TournamentCommand implements CommandExecutor {

    private BendingTournament btMain;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args[0].equalsIgnoreCase("create")) {
            if (args[1] != null) {
                if (sender.hasPermission("bending.tournament.admin")) {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        btMain.getBtManager().createTournament(player.getUniqueId(), args[1]);
                        sender.sendMessage(GeneralMethods.successColor + "Created tournament, " + args[1] + ", by " + player.getDisplayName() + ".");
                    } else {
                        sender.sendMessage(GeneralMethods.errorColor + "You must be a player to create a tournament.");
                    }
                } else {
                    sender.sendMessage(GeneralMethods.errorColor + "You do not have sufficient permission to execute this command.");
                }
            } else {
                sender.sendMessage(GeneralMethods.errorColor + "You must provide the name for the tournament.");
            }
        } else if (args[0].equalsIgnoreCase("join")) {
            if (args[1] != null && args[2] != null) {
                if (args[2].equalsIgnoreCase(GeneralMethods.Elements.values().toString())) {
                    if (sender.hasPermission("bending.tournament.player")) {
                        if (sender instanceof Player) {
                            Player player = (Player) sender;
                            btMain.getBtManager().joinTournament(player.getUniqueId(), args[1], args[2]);
                            sender.sendMessage(GeneralMethods.successColor + "You have successfully joined the tournament with the element, " + args[2]);
                        } else {
                            sender.sendMessage(GeneralMethods.errorColor + "You must be a player to join a tournament!");
                        }
                    } else {
                        sender.sendMessage(GeneralMethods.errorColor + "You do not have sufficient permission to execute this command");
                    }
                } else {
                    sender.sendMessage(GeneralMethods.errorColor + "The elements you\'re allowed to use in the tournament are Air, Water, Earth, Fire, or Chi");
                }
            } else {
                sender.sendMessage(GeneralMethods.errorColor + "/bendingtournament join (tournament) (element)");
            }
        } else if (args[0].equalsIgnoreCase("leave")) {
            if (args[1] != null) {
                if (sender.hasPermission("bending.tournament.player")) {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        btMain.getBtManager().leaveTournament(player.getUniqueId(), args[1]);
                        sender.sendMessage(GeneralMethods.successColor + "You successfully left the tournament, " + args[1] + "!");
                    } else {
                        sender.sendMessage(GeneralMethods.errorColor + "You must be a player to leave a tournament!");
                    }
                } else {
                    sender.sendMessage(GeneralMethods.errorColor + "You do not have sufficient permission to execute this command");
                }
            } else {
                sender.sendMessage(GeneralMethods.errorColor + "You must specify a tournament to leave. /bendingtournament leave (tournament)");
            }
        } else if (args[0].equalsIgnoreCase("winner")) {
            if (sender.hasPermission("bending.tournament.admin")) {
                if (args[1] != null) {
                    if (args[2] != null) {
                        Player player = Bukkit.getPlayer(args[2]);
                            btMain.getBtManager().setWinner(player.getUniqueId(), args[1]);
                    } else {
                        sender.sendMessage(GeneralMethods.errorColor + "You must specify a player.");
                    }
                } else {
                    sender.sendMessage(GeneralMethods.errorColor + "You must specify a tournament.");
                }
            } else {
                sender.sendMessage(GeneralMethods.errorColor + "You do not have sufficient permission to execute this command");
            }
        } else if (args[0].equalsIgnoreCase("info")) {
            if (sender.hasPermission("bending.tournament.player")) {
            } else {
                sender.sendMessage(GeneralMethods.errorColor + "You do not have sufficient permission to execute this command");
            }
        }
        return true;
    }
}
