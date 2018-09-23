package com.Jacksonnn.BendingTournament.Tournaments;

import com.Jacksonnn.BendingTournament.BendingTournament;
import com.Jacksonnn.BendingTournament.GeneralMethods;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
                if (args[2].equalsIgnoreCase("air") || args[2].equalsIgnoreCase("water") || args[2].equalsIgnoreCase("earth") || args[2].equalsIgnoreCase("fire") || args[2].equalsIgnoreCase("chi")) {
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
                    sender.sendMessage(GeneralMethods.errorColor + "The elements you\'re allowed to use in the tournament are Air, Water, Earth, Fire, or Chi.");
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
                /*
                    Tournament ID: 1 | Name: Tournament
                    Started By: Jacksonnn | Winner: undefined
                    Players: TestUser, Player, Example, Depression
                 */

                HashMap<Integer, TournamentStruct> tournamentInfo = btMain.getBtManager().getTournamentInfo(args[1]);

                Integer tournamentID = btMain.getBtManager().getTournamentID(args[1]);

                if (tournamentID == -1) {
                    sender.sendMessage(GeneralMethods.errorColor + "There is no tournament by that name.");
                    return false;
                }

                TournamentStruct struct = btMain.getBtManager().getTournamentData(tournamentID);
                List<String> tournamentPlayers = new ArrayList<>();
                HashMap<UUID, GeneralMethods.Elements> players = btMain.getBtManager().getTournamentPlayers(args[1]);

                for (UUID u : players.keySet()) {
                    Player player = Bukkit.getPlayer(u);
                    ChatColor color = players.get(u).getColor();

                    tournamentPlayers.add(color + player.getName());
                }

                String formatted = tournamentPlayers.stream().collect(Collectors.joining(", "));

                sender.sendMessage(GeneralMethods.prefix + "Retrieving tournament information...");
                sender.sendMessage(ChatColor.YELLOW + "Tournament ID: " + tournamentID + " | Name: " + struct.tournamentName);
                sender.sendMessage(ChatColor.YELLOW + "Started By: " + Bukkit.getPlayer(struct.startedUUID).getName() + " | Winner: " + Bukkit.getPlayer(struct.winnerUUID).getName());
                sender.sendMessage(ChatColor.YELLOW + "Players: " + formatted);
            } else {
                sender.sendMessage(GeneralMethods.errorColor + "You do not have sufficient permission to execute this command");
            }
        } else if (args[0].equalsIgnoreCase("list")) {
            if (sender.hasPermission("bending.tournament.player")) {
                List<String> tournaments = btMain.getBtManager().getTournaments();

                sender.sendMessage(GeneralMethods.prefix + "Current Tournaments:");
                for (String t : tournaments) {
                    sender.sendMessage(ChatColor.GRAY + t);
                }
                sender.sendMessage(GeneralMethods.prefix + "For more information on a tournament, do /btournament info (tournament).");
            }
        }
        return true;
    }
}
