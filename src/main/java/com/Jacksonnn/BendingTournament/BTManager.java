package com.Jacksonnn.BendingTournament;

import com.Jacksonnn.BendingTournament.Storage.Mysql;
import com.Jacksonnn.BendingTournament.Storage.SqlLite;
import com.Jacksonnn.BendingTournament.Storage.SqlQueries;
import com.Jacksonnn.BendingTournament.Tournaments.TournamentStruct;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class BTManager {
    private BendingTournament btMain;
    static List<String> tournaments = new ArrayList<>();
    static List<String> users = new ArrayList<>();
    Map<UUID, GeneralMethods.Elements> players = new HashMap<>();
    Map<Integer, TournamentStruct> tournamentData = new HashMap<>();
    Map<Integer, TournamentStruct> tournamentInfo = new HashMap<>();


    public BTManager(BendingTournament btMain) {
        this.btMain = btMain;
    }

    public void addTournamentData(int id, String startedBy, String name, String winner){
        tournamentInfo.put(id, new TournamentStruct(startedBy, name, winner));
    }

    public TournamentStruct getTournamentData(String name, Integer id) {
        tournamentData = getTournamentInfo(name);
        return tournamentData.get(id);
    }

    public void createUser(UUID createUser) {
        String query;
        if (btMain.getDatabaseManager().getDatabase() instanceof SqlLite) {
            query = SqlQueries.CREATE_USER.getSqliteQuery();
        } else {
            query = SqlQueries.CREATE_USER.getMysqlQuery();
        }
        try {
            PreparedStatement preparedStatement = btMain.getDatabaseManager().getDatabase()
                    .getConnection().prepareStatement(query);
            preparedStatement.setString(1, createUser.toString());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasUser(UUID uniqueId) {

        String query;
        if (btMain.getDatabaseManager().getDatabase() instanceof Mysql) {
            query = SqlQueries.GET_USER.getMysqlQuery();
        } else {
            query = SqlQueries.GET_USER.getSqliteQuery();
        }
        boolean ret = false;

        try {
            PreparedStatement preparedStatement = btMain.getDatabaseManager().getDatabase()
                    .getConnection().prepareStatement(query);
            preparedStatement.setString(1, uniqueId.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isClosed()) {
                return false;
            }
            while (resultSet.next()) {
                ret = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return ret;
    }

    public List<String> getTournaments() {
        tournaments.clear();
        String query;
        if (btMain.getDatabaseManager().getDatabase() instanceof Mysql) {
            query = SqlQueries.GET_TOURNAMENTS.getMysqlQuery();
        } else {
            query = SqlQueries.GET_TOURNAMENTS.getSqliteQuery();
        }
        try {
            PreparedStatement preparedStatement = btMain.getDatabaseManager().getDatabase().getConnection().prepareStatement(query);
            ResultSet getTournaments = preparedStatement.executeQuery();


            while (getTournaments.next()) {
                tournaments.add(getTournaments.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tournaments;
    }

    public List<String> getUsers() {
        String query;
        if (btMain.getDatabaseManager().getDatabase() instanceof Mysql) {
            query = SqlQueries.GET_USERS.getMysqlQuery();
        } else {
            query = SqlQueries.GET_USERS.getSqliteQuery();
        }
        try {
            PreparedStatement preparedStatement = btMain.getDatabaseManager().getDatabase().getConnection().prepareStatement(query);
            ResultSet getUsers = preparedStatement.executeQuery();


            while (getUsers.next()) {
                users.add(getUsers.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void createTournament(UUID startedBy, String name) {
        String query;
        if (btMain.getDatabaseManager().getDatabase() instanceof SqlLite) {
            query = SqlQueries.CREATE_TOURNAMENT.getSqliteQuery();
        } else {
            query = SqlQueries.CREATE_TOURNAMENT.getMysqlQuery();
        }
        try {
            PreparedStatement preparedStatement = btMain.getDatabaseManager().getDatabase()
                    .getConnection().prepareStatement(query);
            preparedStatement.setString(1, startedBy.toString());
            preparedStatement.setString(2, name);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setWinner(UUID winner, String name) {
        String query;
        if (btMain.getDatabaseManager().getDatabase() instanceof SqlLite) {
            query = SqlQueries.SET_WINNER.getSqliteQuery();
        } else {
            query = SqlQueries.SET_WINNER.getMysqlQuery();
        }
        try {
            PreparedStatement preparedStatement = btMain.getDatabaseManager().getDatabase()
                    .getConnection().prepareStatement(query);
            preparedStatement.setString(1, winner.toString());
            preparedStatement.setString(2, name);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void joinTournament(UUID user, String tournament, String element) {
        String query;
        if (btMain.getDatabaseManager().getDatabase() instanceof SqlLite) {
            query = SqlQueries.JOIN_TOURNAMENT.getSqliteQuery();
        } else {
            query = SqlQueries.JOIN_TOURNAMENT.getMysqlQuery();
        }
        try {
            PreparedStatement preparedStatement = btMain.getDatabaseManager().getDatabase()
                    .getConnection().prepareStatement(query);
            preparedStatement.setString(1, tournament);
            preparedStatement.setString(2, element);
            preparedStatement.setString(3, user.toString());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void leaveTournament(UUID user, String tournament) {
        String query;
        if (btMain.getDatabaseManager().getDatabase() instanceof SqlLite) {
            query = SqlQueries.LEAVE_TOURNAMENT.getSqliteQuery();
        } else {
            query = SqlQueries.LEAVE_TOURNAMENT.getMysqlQuery();
        }
        try {
            PreparedStatement preparedStatement = btMain.getDatabaseManager().getDatabase()
                    .getConnection().prepareStatement(query);
            preparedStatement.setString(1, null);
            preparedStatement.setString(2, null);
            preparedStatement.setString(3, user.toString());
            preparedStatement.setString(4, tournament);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<UUID, GeneralMethods.Elements> getTournamentPlayers(String tournament) {
        String query;
        if (btMain.getDatabaseManager().getDatabase() instanceof Mysql) {
            query = SqlQueries.GET_TOURNAMENT_USERS.getMysqlQuery();
        } else {
            query = SqlQueries.GET_TOURNAMENT_USERS.getSqliteQuery();
        }
        try {
            PreparedStatement preparedStatement = btMain.getDatabaseManager().getDatabase().getConnection().prepareStatement(query);
            preparedStatement.setString(1, tournament);
            ResultSet getUsers = preparedStatement.executeQuery();


            while (getUsers.next()) {
                UUID uuid = UUID.fromString(getUsers.getString("uuid"));

                GeneralMethods.Elements element = GeneralMethods.Elements.getByName(getUsers.getString("tournament_element"));

                players.put(uuid, element);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return players;
    }
    public Map<Integer, TournamentStruct> getTournamentInfo(String tournament) {
        String query;
        if (btMain.getDatabaseManager().getDatabase() instanceof Mysql) {
            query = SqlQueries.GET_TOURNAMENT_INFO.getMysqlQuery();
        } else {
            query = SqlQueries.GET_TOURNAMENT_INFO.getSqliteQuery();
        }
        try {
            PreparedStatement preparedStatement = btMain.getDatabaseManager().getDatabase().getConnection().prepareStatement(query);
            preparedStatement.setString(1, tournament);
            ResultSet getInfo = preparedStatement.executeQuery();


            while (getInfo.next()) {
                String winner;
                Integer id = getInfo.getInt("id");
                String startedBy = Bukkit.getPlayer(UUID.fromString(getInfo.getString("startedBy"))).getName();
                String name = getInfo.getString("name");

                if (getInfo.getString("winner") == null) {
                    winner = "None";
                } else {
                    winner = Bukkit.getPlayer(UUID.fromString(getInfo.getString("winner"))).getName();
                }

                addTournamentData(id, startedBy, name, winner);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tournamentInfo;
    }

    public Integer getTournamentID(String tournament) {
        String query;
        if (btMain.getDatabaseManager().getDatabase() instanceof Mysql) {
            query = SqlQueries.GET_TOURNAMENT_KEY.getMysqlQuery();
        } else {
            query = SqlQueries.GET_TOURNAMENT_KEY.getSqliteQuery();
        }
        try {
            PreparedStatement preparedStatement = btMain.getDatabaseManager().getDatabase().getConnection().prepareStatement(query);
            preparedStatement.setString(1, tournament);
            ResultSet getID = preparedStatement.executeQuery();


            while (getID.next()) {
                Integer id = getID.getInt("id");

                return id;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
