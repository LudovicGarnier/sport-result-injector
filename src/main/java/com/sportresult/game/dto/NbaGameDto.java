package com.sportresult.game.dto;

import com.sportresult.arena.dto.NbaArenaDto;
import com.sportresult.team.dto.NbaTeamDto;

import java.time.LocalDateTime;
import java.util.List;

public record NbaGameDto(Long oldId,
                         Integer season,
                         LocalDateTime gameStart,
                         LocalDateTime gameEnd,
                         String duration,
                         Integer stage,
                         String status,
                         NbaArenaDto arenaDto,
                         NbaTeamDto homeTeam,
                         NbaTeamDto visitorTeam,
                         Integer timesTied,
                         Integer leadChanges,
                         List<String> officials,
                         Integer homeScore,
                         Integer visitorScore,
                         Integer homeWin,
                         Integer homeLoss,
                         Integer visitorWin,
                         Integer visitorLoss
) {
}
