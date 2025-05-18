package com.example.tennisscoreboard.services;

import com.example.tennisscoreboard.models.domain.CurrentMatch;
import com.example.tennisscoreboard.storage.CurrentMatchStorage;

import java.util.UUID;

public class OngoingMatchesService {

    public void addMatch(UUID matchId, CurrentMatch currentMatch) {
        CurrentMatchStorage.addMatch(matchId, currentMatch);
    }

    public CurrentMatch getMatch(UUID matchId) {
        return CurrentMatchStorage.getMatch(matchId);
    }

    public void removeMatch(UUID uuid) {
        CurrentMatchStorage.removeMatch(uuid);
    }
}
