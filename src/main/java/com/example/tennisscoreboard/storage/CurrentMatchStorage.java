package com.example.tennisscoreboard.storage;

import com.example.tennisscoreboard.models.domain.CurrentMatch;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CurrentMatchStorage {
    private final static ConcurrentHashMap<UUID, CurrentMatch> currentMatches = new ConcurrentHashMap<>();

    public static void addMatch(UUID matchId, CurrentMatch currentMatch) {
        currentMatches.put(matchId, currentMatch);
    }

    public static CurrentMatch getMatch(UUID matchId) {
        return currentMatches.get(matchId);
    }

    public static void removeMatch(UUID matchId) {
        currentMatches.remove(matchId);
    }
}
