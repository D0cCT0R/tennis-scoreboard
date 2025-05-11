package com.example.tennisscoreboard.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchDto {

    private int id;

    private PlayerDto player1;

    private PlayerDto player2;

    private PlayerDto winner;
}
