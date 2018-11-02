package com.Jacksonnn.BendingTournament.Tournaments;

import com.Jacksonnn.BendingTournament.BendingTournament;
import com.Jacksonnn.BendingTournament.GeneralMethods;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class TournamentCommand implements CommandExecutor {

    private BendingTournament btMain;
    private Map<UUID, GeneralMethods.Elements> players = new HashMap<>();
    private List<String> tournamentList = new ArrayList<>();

    private static final String[] createAliases = {"create", "new", "c"};
    private static final String[] joinAliases = {"join", "j"};
    private static final String[] leaveAliases = {"leave", "le", "exit"};
    private static final String[] winnerAliases = {"winner", "w"};
    private static final String[] infoAliases = {"info", "i"};
    private static final String[] listAliases = {"list", "l"};
    private static final String[] deleteAliases = {"delete", "del", "d"};

    public TournamentCommand(BendingTournament BendingTournament) {
        btMain = BendingTournament;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length != 0) {
            if (Arrays.asList(createAliases).contains(args[0].toLowerCase())) {
                if (args.length == 2) {
                    if (sender.hasPermission("btournament.admin.create")) {
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
            } else if (Arrays.asList(deleteAliases).contains(args[0].toLowerCase())) {
                if (args.length == 2) {
                    if (sender.hasPermission("btournament.admin.delete")) {
                        if (sender instanceof Player) {
                            tournamentList = btMain.getBtManager().getTournaments();
                            if (tournamentList.contains(args[1])) {
                                btMain.getBtManager().deleteTournament(args[1]);
                                sender.sendMessage(GeneralMethods.successColor + "Deleted tournament, " + args[1] + ".");
                            } else {
                                sender.sendMessage(GeneralMethods.errorColor + "The tournament you have entered, " + args[1] + ", does not exist.");
                            }
                        } else {
                            sender.sendMessage(GeneralMethods.errorColor + "You must be a player to delete a tournament.");
                        }
                    } else {
                        sender.sendMessage(GeneralMethods.errorColor + "You do not have sufficient permission to execute this command.");
                    }
                } else {
                    sender.sendMessage(GeneralMethods.errorColor + "You must provide the name for the tournament you want to delete.");
                }
            } else if (Arrays.asList(joinAliases).contains(args[0].toLowerCase())) {
                if (args.length >= 2) {
                    if (args.length == 3) {
                        if (args[2].equalsIgnoreCase("air") || args[2].equalsIgnoreCase("water") || args[2].equalsIgnoreCase("earth") || args[2].equalsIgnoreCase("fire") || args[2].equalsIgnoreCase("chi") || args[2].equalsIgnoreCase("avatar")) {
                            if (sender.hasPermission("btournament.player.join")) {
                                if (sender instanceof Player) {
                                    Player player = (Player) sender;
                                    tournamentList = btMain.getBtManager().getTournaments();
                                    if (tournamentList.contains(args[1])) {
                                        btMain.getBtManager().joinTournament(player.getUniqueId(), args[1], args[2]);
                                        sender.sendMessage(GeneralMethods.successColor + "You have successfully joined the tournament with the element, " + args[2] + ".");
                                    } else {
                                        sender.sendMessage(GeneralMethods.errorColor + "The tournament you have entered, " + args[1] + ", does not exist.");
                                    }
                                } else {
                                    sender.sendMessage(GeneralMethods.errorColor + "You must be a player to join a tournament!");
                                }
                            } else {
                                sender.sendMessage(GeneralMethods.errorColor + "You do not have sufficient permission to execute this command.");
                            }
                        } else {
                            sender.sendMessage(GeneralMethods.errorColor + "The elements you\'re allowed to use in the tournament are Air, Water, Earth, Fire, or Chi.");
                        }
                    } else {
                        sender.sendMessage(GeneralMethods.errorColor + "/bendingtournament join (tournament) (element).");
                    }
                } else {
                    sender.sendMessage(GeneralMethods.errorColor + "/bendingtournament join (tournament) (element).");
                }
            } else if (Arrays.asList(leaveAliases).contains(args[0].toLowerCase())) {
                if (args.length == 2) {
                    if (sender.hasPermission("btournament.player.leave")) {
                        if (sender instanceof Player) {
                            Player player = (Player) sender;
                            btMain.getBtManager().leaveTournament(player.getUniqueId(), args[1]);
                            sender.sendMessage(GeneralMethods.successColor + "You successfully left the tournament, " + args[1] + "!");
                        } else {
                            sender.sendMessage(GeneralMethods.errorColor + "You must be a player to leave a tournament!");
                        }
                    } else {
                        sender.sendMessage(GeneralMethods.errorColor + "You do not have sufficient permission to execute this command.");
                    }
                } else {
                    sender.sendMessage(GeneralMethods.errorColor + "You must specify a tournament to leave. /bendingtournament leave (tournament).");
                }
            } else if (Arrays.asList(winnerAliases).contains(args[0].toLowerCase())) {
                if (sender.hasPermission("bending.tournament.admin")) {
                    if (args.length >= 2) {
                        if (args.length == 3) {
                            Player player = Bukkit.getPlayer(args[2]);
                            btMain.getBtManager().setWinner(player.getUniqueId(), args[1]);
                            sender.sendMessage(GeneralMethods.successColor + "You made the winner of the tournament, " + args[1] + ", " + args[2] + ".");
                        } else {
                            sender.sendMessage(GeneralMethods.errorColor + "You must specify a player.");
                        }
                    } else {
                        sender.sendMessage(GeneralMethods.errorColor + "You must specify a tournament.");
                    }
                } else {
                    sender.sendMessage(GeneralMethods.errorColor + "You do not have sufficient permission to execute this command");
                }
            } else if (Arrays.asList(infoAliases).contains(args[0].toLowerCase())) {
                if (args.length == 2) {
                    if (sender.hasPermission("btournament.player.info")) {
                /*
                    Tournament ID: 1 | Name: Tournament
                    Started By: Jacksonnn | Winner: undefined
                    Players: TestUser, Player, Example, Depression
                 */

                        Integer tournamentID = btMain.getBtManager().getTournamentID(args[1]);

                        if (tournamentID == -1) {
                            sender.sendMessage(GeneralMethods.errorColor + "There is no tournament by that name.");
                            return true;
                        }

                        TournamentStruct struct =
                                btMain
                                        .getBtManager()
                                        .getTournamentData(
                                                args[1],
                                                tournamentID);
                        List<String> tournamentPlayers = new ArrayList<>();
                        players = btMain.getBtManager().getTournamentPlayers(args[1]);

                        for (UUID u : players.keySet()) {
                            Player player = Bukkit.getPlayer(u);
                            ChatColor color = players.get(u).getColor();

                            tournamentPlayers.add(color + player.getName());
                        }

                        String formatted = tournamentPlayers.stream().collect(Collectors.joining(", "));

                        sender.sendMessage(GeneralMethods.prefix + "Retrieving tournament information...");
                        sender.sendMessage(ChatColor.YELLOW + "Tournament ID: " + ChatColor.WHITE + tournamentID + ChatColor.YELLOW + " | Name: " + ChatColor.WHITE + struct.tournamentName);
                        sender.sendMessage(ChatColor.YELLOW + "Started By: " + ChatColor.WHITE + struct.startedBy + ChatColor.YELLOW + " | Winner: " + ChatColor.WHITE + struct.winner);
                        sender.sendMessage(ChatColor.YELLOW + "Players: " + formatted);

                        tournamentPlayers.clear();
                        players.clear();

                    } else {
                        sender.sendMessage(GeneralMethods.errorColor + "You do not have sufficient permission to execute this command.");
                    }
                } else {
                    sender.sendMessage(GeneralMethods.errorColor + "You must specify a tournament to get information about!");
                }
            } else if (Arrays.asList(listAliases).contains(args[0].toLowerCase())) {
                if (sender.hasPermission("bending.tournament.player")) {
                    tournamentList = btMain.getBtManager().getTournaments();

                    sender.sendMessage(GeneralMethods.prefix + "Current Tournaments:");
                    for (String t : tournamentList) {
                        sender.sendMessage(ChatColor.YELLOW + t);
                    }
                    sender.sendMessage(ChatColor.YELLOW + "For more information on a tournament, do /btournament info (tournament).");
                }
            } else {
                sender.sendMessage(GeneralMethods.prefix + "Bending Tournament Commands: ");
                sender.sendMessage(ChatColor.YELLOW + "/bt create <name> - Create a tournament.");
                sender.sendMessage(ChatColor.YELLOW + "/bt delete <name> - Delete a tournament.");
                sender.sendMessage(ChatColor.YELLOW + "/bt join <name> <element> - Join a tournament with the elements air, water, earth, fire, or chi.");
                sender.sendMessage(ChatColor.YELLOW + "/bt leave <name> - Leave a tournament.");
                sender.sendMessage(ChatColor.YELLOW + "/bt list - List all tournaments.");
                sender.sendMessage(ChatColor.YELLOW + "/bt info <name> - Displays information about a tournament.");
            }
        } else {
            sender.sendMessage(GeneralMethods.prefix + "Bending Tournament Commands: ");
            sender.sendMessage(ChatColor.YELLOW + "/bt create <name> - Create a tournament.");
            sender.sendMessage(ChatColor.YELLOW + "/bt delete <name> - Delete a tournament.");
            sender.sendMessage(ChatColor.YELLOW + "/bt join <name> <element> - Join a tournament with the elements air, water, earth, fire, chi, or avatar.");
            sender.sendMessage(ChatColor.YELLOW + "/bt leave <name> - Leave a tournament.");
            sender.sendMessage(ChatColor.YELLOW + "/bt list - List all tournaments.");
            sender.sendMessage(ChatColor.YELLOW + "/bt info <name> - Displays information about a tournament.");
        }
        return true;
    }
}
