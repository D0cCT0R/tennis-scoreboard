package com.example.tennisscoreboard.models.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class CurrentMatch {
    private UUID uuid;
    private final Instant createdAt;
    private String namePlayer1;
    private String namePlayer2;
    private String winnerPlayer;
    private int setsPlayer1;
    private int setsPlayer2;
    private int gamePlayer1;
    private int gamePlayer2;
    private int pointPlayer1;
    private int pointPlayer2;
    private int tieBreakPointPlayer1;
    private int tieBreakPointPlayer2;
    private int setsToWin = 2;
    private boolean tieBreak = false;
    private boolean deuce = false;
    private boolean isComplete = false;

    public CurrentMatch(UUID uuid, String namePlayer1, String namePlayer2) {
        this.uuid = uuid;
        this.namePlayer1 = namePlayer1;
        this.namePlayer2 = namePlayer2;
        this.createdAt = Instant.now();
    }

    public String translatePointPlayer(int points) {
        return switch (points) {
            case 0 -> "0";
            case 1 -> "15";
            case 2 -> "30";
            case 3 -> "40";
            case 4 -> "AD";
            default -> null;
        };
    }

    public void incrementPointPlayer1() {
        pointPlayer1++;
    }

    public void incrementPointPlayer2() {
        pointPlayer2++;
    }

    public void incrementTieBreakPointPlayer1() {
        tieBreakPointPlayer1++;
    }

    public void incrementTieBreakPointPlayer2() {
        tieBreakPointPlayer2++;
    }

    public void incrementGamePlayer1() {
        gamePlayer1++;
        checkTieBreak();
        startNewGame();
    }

    public void incrementGamePlayer2() {
        gamePlayer2++;
        checkTieBreak();
        startNewGame();
    }

    public void incrementSetsPlayer1() {
        setsPlayer1++;
        startNewSet();
    }

    public void incrementSetsPlayer2() {
        setsPlayer2++;
        startNewSet();
    }

    public void startNewGame() {
        pointPlayer1 = 0;
        pointPlayer2 = 0;
    }

    public void startNewSet() {
        gamePlayer1 = 0;
        gamePlayer2 = 0;
        tieBreakPointPlayer1 = 0;
        tieBreakPointPlayer2 = 0;
    }


    public void checkDeuce() {
        deuce = pointPlayer1 >= 3
                && pointPlayer2 >= 3;
    }


    public void checkTieBreak() {
        tieBreak = (gamePlayer1 == 6 && gamePlayer2 == 6);
    }
}
