package com.example.tennisscoreboard.storage;

import com.example.tennisscoreboard.models.domain.CurrentMatch;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CurrentMatchStorage {
    private final static ConcurrentHashMap<UUID, CurrentMatch> currentMatches = new ConcurrentHashMap<>();
    private static final long MAX_AGE_MINUTES = 60;
    private static final Object lock = new Object();

    public static void addMatch(UUID matchId, CurrentMatch currentMatch) {
        synchronized (lock) {
            currentMatches.put(matchId, currentMatch);
        }
    }

    public static CurrentMatch getMatch(UUID matchId) {
        synchronized (lock) {
            return currentMatches.get(matchId);
        }
    }

    public static void removeMatch(UUID matchId) {
        synchronized (lock) {
            currentMatches.remove(matchId);
        }
    }

    public static void cleanUpOldMatches() {
        synchronized (lock) {
            Instant now = Instant.now();
            currentMatches.entrySet().removeIf(entry ->
                    Duration.between(entry.getValue().getCreatedAt(), now).toMinutes() > MAX_AGE_MINUTES);
        }
    }
}
