package com.sportresult.statsistics.controller;

import com.sportresult.client.NbaApiClient;
import com.sportresult.client.response.statistics.game.GameStatsResponse;
import com.sportresult.client.response.statistics.team.TeamStatsResponse;
import com.sportresult.game.dto.NbaGameDto;
import com.sportresult.statsistics.dto.NbaGameStatisticsDto;
import com.sportresult.statsistics.service.NbaStatisticInjectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("statistic")
@RequiredArgsConstructor
@Tag(name = "Statistic")
public class NbaStatisticInjectorController {

    @Autowired
    private NbaApiClient nbaApiClient;

    private final NbaStatisticInjectorService nbaStatisticInjectorService;

    @Operation(summary = "Retrieve the game statistic from game oldId from RapidApi and inject them to Database")
    @GetMapping("/game")
    public ResponseEntity<NbaGameStatisticsDto> getStatisticsPerGameFromApi(String id) {
        Long nbaGameEntityOldId = Long.parseLong(id);
        log.info("Statistics injected for game: {}", id);
        GameStatsResponse response = nbaApiClient.getStatisticsPerGamesOldId(id);

        NbaGameStatisticsDto nbaGameStatisticsDtos = nbaStatisticInjectorService.injectTeamGameStatistic(nbaGameEntityOldId, response);

        return ResponseEntity.ok(nbaGameStatisticsDtos);

    }

    @Operation(summary = "Retrieve the game statistic from season year from RapidApi and inject them to Database")
    @GetMapping("/game/year")
    public ResponseEntity<List<NbaGameStatisticsDto>> getStatisticsPerYearFromApi(String year) {
        int yearValue = Integer.parseInt(year);
        log.info("Statistics injected for year: {}", year);
        GameStatsResponse response = nbaApiClient.getStatisticsPerGamesOldId(year);

        List<NbaGameStatisticsDto> nbaGameStatisticsDtos = nbaStatisticInjectorService.injectAllTeamGameStatisticPerYear(yearValue, response);

        return ResponseEntity.ok(nbaGameStatisticsDtos);

    }


    @Operation(summary = "Retrieve the team statistic per season from RapidApi and inject them to Database")
    @GetMapping("/team")
    public ResponseEntity<List<NbaGameDto>> getStatisticsPerTeamFromApi(String id, String year) {
        log.info("Games injected by year: {}", id);

        TeamStatsResponse response = nbaApiClient.getStatisticsPerTeamAndPerYear(id, year);


        return ResponseEntity.ok(null);

    }
}
