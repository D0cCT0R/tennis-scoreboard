package com.example.tennisscoreboard.services;

import com.example.tennisscoreboard.dao.MatchesDao;
import com.example.tennisscoreboard.dao.PlayersDao;
import com.example.tennisscoreboard.dto.MatchDto;
import com.example.tennisscoreboard.exception.MatchSaveException;
import com.example.tennisscoreboard.exception.MatchSearchException;
import com.example.tennisscoreboard.exception.PlayerCreateException;
import com.example.tennisscoreboard.exception.PlayerSearchException;
import com.example.tennisscoreboard.mapper.ConvertToDto;
import com.example.tennisscoreboard.models.domain.CurrentMatch;
import com.example.tennisscoreboard.models.entity.Match;
import com.example.tennisscoreboard.models.entity.Player;

import java.util.List;

public class CompleteMatchService {

    private final PlayersDao playersDao;
    private final MatchesDao matchesDao;

    public CompleteMatchService(PlayersDao playersDao, MatchesDao matchesDao) {
        this.playersDao = playersDao;
        this.matchesDao = matchesDao;
    }

    public List<MatchDto> getMatches(int page, int size, String playerName) throws MatchSearchException {
        List<Match> allMatches = matchesDao.getAll(page, size, playerName);
        return ConvertToDto.matchListToDtoList(allMatches);
    }

    public int getTotalMatches(String playerName) {
        return matchesDao.getTotalProducts(playerName).intValue();
    }

    public void saveMatch(CurrentMatch currentMatch) throws MatchSaveException, PlayerSearchException, PlayerCreateException {
        Player player1 = playersDao.findOrCreate(currentMatch.getNamePlayer1());
        Player player2 = playersDao.findOrCreate(currentMatch.getNamePlayer2());
        Player winner = playersDao.findOrCreate(currentMatch.getWinnerPlayer());
        Match match = Match.builder()
                .player1(player1)
                .player2(player2)
                .winner(winner)
                .build();
        matchesDao.save(match);
    }
}
