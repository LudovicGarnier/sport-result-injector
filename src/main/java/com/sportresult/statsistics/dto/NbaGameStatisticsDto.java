package com.sportresult.statsistics.dto;

import com.sportresult.game.dto.NbaGameDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NbaGameStatisticsDto {

    private NbaGameDto nbaGame;
    private NbaTeamGameStatisticsDto awayTeamStatistics;
    private NbaTeamGameStatisticsDto homeTeamStatistics;
}
