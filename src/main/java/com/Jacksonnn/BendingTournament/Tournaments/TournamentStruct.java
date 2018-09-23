package com.Jacksonnn.BendingTournament.Tournaments;

import java.util.UUID;

public class TournamentStruct {
    public UUID startedUUID;
    public String tournamentName;
    public UUID winnerUUID;

    public TournamentStruct(UUID startedUUID, String name, UUID winner){
        this.startedUUID = startedUUID;
        this.tournamentName = name;
        this.winnerUUID = winner;
    }
}