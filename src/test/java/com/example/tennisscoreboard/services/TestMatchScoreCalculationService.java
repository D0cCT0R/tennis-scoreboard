package com.example.tennisscoreboard.services;

import com.example.tennisscoreboard.models.domain.CurrentMatch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class TestMatchScoreCalculationService {

    MatchScoreCalculationService service = new MatchScoreCalculationService();
    String player1 = "player1";
    String player2 = "player2";

    @Test
    public void testCalculateIncrementPointsPlayer1() {
        String namePlayer1 = "V";
        String namePlayer2 = "M";
        CurrentMatch currentMatch = new CurrentMatch(UUID.randomUUID(), namePlayer1, namePlayer2);
        service.calculate(currentMatch, player1);
        Assertions.assertEquals(1, currentMatch.getPointPlayer1());
    }

    @Test
    public void testCalculateIncrementPointsPlayer2() {
        String namePlayer1 = "V";
        String namePlayer2 = "M";
        CurrentMatch currentMatch = new CurrentMatch(UUID.randomUUID(), namePlayer1, namePlayer2);
        service.calculate(currentMatch, player2);
        Assertions.assertEquals(1, currentMatch.getPointPlayer2());
    }

    @Test
    public void testCalculateIncrementGamePlayer1() {
        String namePlayer1 = "V";
        String namePlayer2 = "M";
        CurrentMatch currentMatch = new CurrentMatch(UUID.randomUUID(), namePlayer1, namePlayer2);
        for (int i = 0; i <= 5; i++) {
            service.calculate(currentMatch, player1);
        }
        Assertions.assertEquals(1, currentMatch.getGamePlayer1());
    }

    @Test
    public void testCalculateIncrementGamePlayer2() {
        String namePlayer1 = "V";
        String namePlayer2 = "M";
        CurrentMatch currentMatch = new CurrentMatch(UUID.randomUUID(), namePlayer1, namePlayer2);
        for (int i = 0; i <= 5; i++) {
            service.calculate(currentMatch, player2);
        }
        Assertions.assertEquals(1, currentMatch.getGamePlayer2());
    }

    @Test
    public void testCalculateIncrementSetPlayer1() {
        String namePlayer1 = "V";
        String namePlayer2 = "M";
        CurrentMatch currentMatch = new CurrentMatch(UUID.randomUUID(), namePlayer1, namePlayer2);
        currentMatch.setGamePlayer1(5);
        for (int i = 0; i <= 30; i++) {
            service.calculate(currentMatch, player1);
        }
        Assertions.assertEquals(1, currentMatch.getSetsPlayer1());
    }

    @Test
    public void testCalculateIncrementSetPlayer2() {
        String namePlayer1 = "V";
        String namePlayer2 = "M";
        CurrentMatch currentMatch = new CurrentMatch(UUID.randomUUID(), namePlayer1, namePlayer2);
        currentMatch.setGamePlayer2(5);
        for (int i = 0; i <= 30; i++) {
            service.calculate(currentMatch, player2);
        }
        Assertions.assertEquals(1, currentMatch.getSetsPlayer2());
    }

    @Test
    public void deuceCheck() {
        String namePlayer1 = "V";
        String namePlayer2 = "M";
        CurrentMatch currentMatch = new CurrentMatch(UUID.randomUUID(), namePlayer1, namePlayer2);
        for (int i = 0; i <= 2; i++) {
            service.calculate(currentMatch, player1);
            service.calculate(currentMatch, player2);
        }
        Assertions.assertTrue(currentMatch.isDeuce());
    }

    @Test
    public void tieBrakeCheck() {
        String namePlayer1 = "V";
        String namePlayer2 = "M";
        CurrentMatch currentMatch = new CurrentMatch(UUID.randomUUID(), namePlayer1, namePlayer2);
        currentMatch.setGamePlayer1(5);
        currentMatch.setGamePlayer2(5);
        for (int i = 0; i < 5; i++) {
            service.calculate(currentMatch, player1);
        }
        for (int i = 0; i < 5; i++) {
            service.calculate(currentMatch, player2);
        }
        Assertions.assertTrue(currentMatch.isTieBreak());
    }

    @Test
    public void completeMatchCheckWinPlayer1() {
        String namePlayer1 = "V";
        String namePlayer2 = "M";
        CurrentMatch currentMatch = new CurrentMatch(UUID.randomUUID(), namePlayer1, namePlayer2);
        currentMatch.setSetsPlayer1(1);
        currentMatch.setGamePlayer1(5);
        for (int i = 0; i <= 30; i++) {
            service.calculate(currentMatch, player1);
        }
        Assertions.assertTrue(currentMatch.isComplete());
    }

    @Test
    public void completeMatchCheckWinPlayer2() {
        String namePlayer1 = "V";
        String namePlayer2 = "M";
        CurrentMatch currentMatch = new CurrentMatch(UUID.randomUUID(), namePlayer1, namePlayer2);
        currentMatch.setSetsPlayer2(1);
        currentMatch.setGamePlayer2(5);
        for (int i = 0; i <= 30; i++) {
            service.calculate(currentMatch, player2);
        }
        Assertions.assertTrue(currentMatch.isComplete());
    }

    @Test
    public void testCalculateIncrementTieBreakPointPlayer1() {
        String namePlayer1 = "V";
        String namePlayer2 = "M";
        CurrentMatch currentMatch = new CurrentMatch(UUID.randomUUID(), namePlayer1, namePlayer2);
        currentMatch.setTieBreak(true);
        service.calculate(currentMatch, player1);
        Assertions.assertEquals(1, currentMatch.getTieBreakPointPlayer1());
    }

    @Test
    public void testCalculateIncrementTieBreakPointPlayer2() {
        String namePlayer1 = "V";
        String namePlayer2 = "M";
        CurrentMatch currentMatch = new CurrentMatch(UUID.randomUUID(), namePlayer1, namePlayer2);
        currentMatch.setTieBreak(true);
        service.calculate(currentMatch, player2);
        Assertions.assertEquals(1, currentMatch.getTieBreakPointPlayer2());
    }

    @Test
    public void correctIncrementSetAfterTieBrakePlayer1() {
        String namePlayer1 = "V";
        String namePlayer2 = "M";
        CurrentMatch currentMatch = new CurrentMatch(UUID.randomUUID(), namePlayer1, namePlayer2);
        currentMatch.setTieBreak(true);
        for (int i = 0; i < 7; i++) {
            service.calculate(currentMatch, player1);
        }
        Assertions.assertFalse(currentMatch.isTieBreak());
        Assertions.assertEquals(1, currentMatch.getSetsPlayer1());
    }

    @Test
    public void correctIncrementSetAfterTieBrakePlayer2() {
        String namePlayer1 = "V";
        String namePlayer2 = "M";
        CurrentMatch currentMatch = new CurrentMatch(UUID.randomUUID(), namePlayer1, namePlayer2);
        currentMatch.setTieBreak(true);
        for (int i = 0; i < 7; i++) {
            service.calculate(currentMatch, player2);
        }
        Assertions.assertFalse(currentMatch.isTieBreak());
        Assertions.assertEquals(1, currentMatch.getSetsPlayer2());
    }

    @Test
    public void correctIncrementGameAfterDeucePlayer1() {
        String namePlayer1 = "V";
        String namePlayer2 = "M";
        CurrentMatch currentMatch = new CurrentMatch(UUID.randomUUID(), namePlayer1, namePlayer2);
        currentMatch.setPointPlayer1(3);
        currentMatch.setPointPlayer2(3);
        currentMatch.setDeuce(true);
        for (int i = 0; i < 2; i++) {
            service.calculate(currentMatch, player1);
        }
        Assertions.assertFalse(currentMatch.isDeuce());
        Assertions.assertEquals(1, currentMatch.getGamePlayer1());
    }

    @Test
    public void correctIncrementGameAfterDeucePlayer2() {
        String namePlayer1 = "V";
        String namePlayer2 = "M";
        CurrentMatch currentMatch = new CurrentMatch(UUID.randomUUID(), namePlayer1, namePlayer2);
        currentMatch.setPointPlayer1(3);
        currentMatch.setPointPlayer2(3);
        currentMatch.setDeuce(true);
        for (int i = 0; i < 2; i++) {
            service.calculate(currentMatch, player2);
        }
        Assertions.assertFalse(currentMatch.isDeuce());
        Assertions.assertEquals(1, currentMatch.getGamePlayer2());
    }
}
