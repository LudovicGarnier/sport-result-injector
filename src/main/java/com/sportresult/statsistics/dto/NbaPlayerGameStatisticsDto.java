package com.sportresult.statsistics.dto;

import com.sportresult.game.dto.NbaGameDto;
import com.sportresult.player.dto.NbaPlayerDto;
import com.sportresult.team.dto.NbaTeamDto;

public record NbaPlayerGameStatisticsDto(
        NbaPlayerDto player,
        NbaTeamDto nbaTeam,
        NbaGameDto game,
        Integer points,
        String pos,
        String min,
        Integer fgm,
        Integer fga,
        String fgp,
        Integer ftm,
        Integer fta,
        String ftp,
        Integer tpm,
        Integer tpa,
        String tpp,
        Integer offReb,
        Integer defReb,
        Integer totReb,
        Integer assists,
        Integer pFouls,
        Integer steals,
        Integer turnovers,
        Integer blocks,
        String plusMinus,
        String comment
) {
}
