package com.Jacksonnn.BendingTournament.Tournaments;

public class TournamentStruct {
    public String startedBy;
    public String tournamentName;
    public String winner;

    public TournamentStruct(String startedBy, String name, String winner){
        this.startedBy = startedBy;
        this.tournamentName = name;
        this.winner = winner;
    }
}