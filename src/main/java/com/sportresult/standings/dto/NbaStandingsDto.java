package com.sportresult.standings.dto;

import com.sportresult.season.dto.NbaSeasonDto;
import com.sportresult.team.dto.NbaTeamDto;

public record NbaStandingsDto(
        NbaSeasonDto seasonDto,
        NbaTeamDto nbaTeamDto,
        String conferenceName,
        Integer conferenceRank,
        Integer conferenceWin,
        Integer conferenceLoss,
        String divisionName,
        Integer divisionRank,
        Integer divisionWin,
        Integer divisionLoss,
        Integer winHome,
        Integer winAway,
        Integer winTotal,
        String winPercentage,
        Integer lastTenWin,
        Integer lossHome,
        Integer lossAway,
        Integer lossTotal,
        String lossPercentage,
        Integer lastTenLoss,
        String gameBehind,
        Integer streak
) {
}
