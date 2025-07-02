package com.sportresult.statsistics.dto;


import com.sportresult.game.dto.NbaGameDto;
import com.sportresult.team.dto.NbaTeamDto;

public record NbaTeamGameStatisticsDto(
        NbaTeamDto nbaTeam,
        NbaGameDto gameDto,
        Integer fastBreakPoints,
        Integer pointsInPaint,
        Integer biggestLead,
        Integer secondChancePoints,
        Integer pointsOffTurnovers,
        Integer longestRun,
        Integer points,
        Integer fgm,
        Integer fga,
        Double fgp,
        Integer ftm,
        Integer fta,
        Double ftp,
        Integer tpm,
        Integer tpa,
        Double tpp,
        Integer offReb,
        Integer defReb,
        Integer totReb,
        Integer assists,
        Integer pFouls,
        Integer steals,
        Integer turnovers,
        Integer blocks,
        Integer plusMinus,
        String min
) {
}
