package com.example.tennisscoreboard.services;

import com.example.tennisscoreboard.models.domain.CurrentMatch;

import java.util.Objects;

public class MatchScoreCalculationService {

    public void calculate(CurrentMatch currentMatch, String winner) {
        if (Objects.equals(winner, "player1")) {
            if(currentMatch.isDeuce()){
                currentMatch.incrementPointPlayer1();
                calculateDeuce(currentMatch);
                return;
            }
            if(currentMatch.isTieBreak()){
                currentMatch.incrementTieBreakPointPlayer1();
                calculateTieBreak(currentMatch);
                return;
            }
            currentMatch.incrementPointPlayer1();
            currentMatch.checkDeuce();
            checkGameWinner(currentMatch);
        } else if (Objects.equals(winner, "player2")) {
            if(currentMatch.isDeuce()){
                currentMatch.incrementPointPlayer2();
                calculateDeuce(currentMatch);
                return;
            }
            if(currentMatch.isTieBreak()){
                currentMatch.incrementTieBreakPointPlayer2();
                calculateTieBreak(currentMatch);
                return;
            }
            currentMatch.incrementPointPlayer2();
            currentMatch.checkDeuce();
            checkGameWinner(currentMatch);
        }
    }

    private void checkGameWinner(CurrentMatch currentMatch) {
        if ((currentMatch.getPointPlayer1() >= 5 || currentMatch.getPointPlayer2() >= 5)
                && Math.abs(currentMatch.getPointPlayer1() - currentMatch.getPointPlayer2()) >= 2) {
            if (currentMatch.getPointPlayer1() > currentMatch.getPointPlayer2()) {
                currentMatch.incrementGamePlayer1();
                checkSetsWinner(currentMatch);
                return;
            }
            if (currentMatch.getPointPlayer2() > currentMatch.getPointPlayer1()) {
                currentMatch.incrementGamePlayer2();
                checkSetsWinner(currentMatch);
            }
        }
    }

    private void checkSetsWinner(CurrentMatch currentMatch) {
        if ((currentMatch.getGamePlayer1() >= 6 || currentMatch.getGamePlayer2() >= 6)
                && Math.abs(currentMatch.getGamePlayer1() - currentMatch.getGamePlayer2()) >= 2) {
            if (currentMatch.getGamePlayer1() > currentMatch.getGamePlayer2()) {
                currentMatch.incrementSetsPlayer1();
                checkMatchWinner(currentMatch);
            } else if(currentMatch.getGamePlayer2() > currentMatch.getGamePlayer1()){
                currentMatch.incrementSetsPlayer2();
                checkMatchWinner(currentMatch);
            }
        }
    }

    public void checkMatchWinner(CurrentMatch currentMatch) {
        if (currentMatch.getSetsPlayer1() >= currentMatch.getSetsToWin() || currentMatch.getSetsPlayer2() >= currentMatch.getSetsToWin()) {
            currentMatch.setComplete(true);
            currentMatch.setWinnerPlayer((currentMatch.getSetsPlayer1() > currentMatch.getSetsPlayer2()) ? currentMatch.getNamePlayer1() : currentMatch.getNamePlayer2());
        }
    }

    private void calculateDeuce(CurrentMatch currentMatch) {
        if (currentMatch.getPointPlayer1() == 4 && currentMatch.getPointPlayer2() == 4) {
            currentMatch.setPointPlayer1(3);
            currentMatch.setPointPlayer2(3);
        }
        else if (currentMatch.getPointPlayer1() == 5) {
            currentMatch.incrementGamePlayer1();
            checkSetsWinner(currentMatch);
            currentMatch.setDeuce(false);
        }
        else if(currentMatch.getPointPlayer2() == 5){
            currentMatch.incrementGamePlayer2();
            checkSetsWinner(currentMatch);
            currentMatch.setDeuce(false);
        }
    }
    private void calculateTieBreak(CurrentMatch currentMatch) {
        if (currentMatch.getTieBreakPointPlayer1() >= 7 && (currentMatch.getTieBreakPointPlayer1() - currentMatch.getTieBreakPointPlayer2() >= 2)) {
            currentMatch.setTieBreak(false);
            currentMatch.incrementSetsPlayer1();
        } else if (currentMatch.getTieBreakPointPlayer2() >= 7 && (currentMatch.getTieBreakPointPlayer2() - currentMatch.getTieBreakPointPlayer1() >= 2)) {
            currentMatch.setTieBreak(false);
            currentMatch.incrementSetsPlayer2();
        }
    }
}


