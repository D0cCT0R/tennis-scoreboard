package com.example.tennisscoreboard.mapper;

import com.example.tennisscoreboard.dto.MatchDto;
import com.example.tennisscoreboard.dto.PlayerDto;
import com.example.tennisscoreboard.models.domain.CurrentMatch;
import com.example.tennisscoreboard.models.entity.Match;
import com.example.tennisscoreboard.models.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertToDto {

    public List<MatchDto> matchListToDtoList(List<Match> matches) {
        return matches.stream()
                .map(this::matchToDto)
                .collect(Collectors.toList());
    }

    public MatchDto matchToDto(Match match) {
        return MatchDto.builder()
                .id(match.getId())
                .player1(playerToDto(match.getPlayer1()))
                .player2(playerToDto(match.getPlayer2()))
                .winner(playerToDto(match.getWinner()))
                .build();
    }

    public PlayerDto playerToDto(Player player) {
        return PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .build();

    }
}
