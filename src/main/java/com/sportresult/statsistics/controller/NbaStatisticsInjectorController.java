package com.sportresult.statsistics.controller;

import com.sportresult.client.NbaApiClient;
import com.sportresult.client.response.statistics.game.GameStatsResponse;
import com.sportresult.client.response.statistics.player.PlayerStatsPerGameResponse;
import com.sportresult.client.response.statistics.team.TeamStatsResponse;
import com.sportresult.game.dto.NbaGameDto;
import com.sportresult.game.service.NbaGameInjectorService;
import com.sportresult.season.service.NbaSeasonInjectorService;
import com.sportresult.statsistics.dto.NbaGameStatisticsDto;
import com.sportresult.statsistics.dto.NbaPlayerGameStatisticsDto;
import com.sportresult.statsistics.dto.NbaTeamSeasonStatisticsDto;
import com.sportresult.statsistics.service.NbaStatisticsInjectorService;
import com.sportresult.team.entity.NbaTeamEntity;
import com.sportresult.team.service.NbaTeamInjectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("statistics")
@RequiredArgsConstructor
@Tag(name = "Statistics")
public class NbaStatisticsInjectorController {

    @Autowired
    private NbaApiClient nbaApiClient;

    private final NbaStatisticsInjectorService nbaStatisticsInjectorService;

    private final NbaSeasonInjectorService nbaSeasonInjectorService;

    private final NbaTeamInjectorService nbaTeamInjectorService;

    private final NbaGameInjectorService nbaGameInjectorService;

    /*
    GAME STATS
     */
    @Operation(summary = "Retrieve the game statistic from game oldId from RapidApi and inject them to Database")
    @GetMapping("/game")
    public ResponseEntity<NbaGameStatisticsDto> getStatisticsPerGameFromApi(String gameId) {
        Long nbaGameEntityOldId = Long.parseLong(gameId);
        log.info("Game statistics injected for game: {}", gameId);
        GameStatsResponse response = nbaApiClient.getStatisticsPerGamesOldId(gameId);

        NbaGameStatisticsDto nbaGameStatisticsDtos = nbaStatisticsInjectorService.injectTeamGameStatistic(nbaGameEntityOldId, response);

        return ResponseEntity.ok(nbaGameStatisticsDtos);
    }

    @Operation(summary = "Retrieve the game statistic from game oldId from RapidApi and inject them to Database")
    @GetMapping("/games/season")
    public ResponseEntity<List<NbaGameStatisticsDto>> getGameStatisticsPerSeasonFromApi(int season) {
        log.info("Game statistics injected for season: {}", season);
        List<NbaGameDto> nbaGameDtos = nbaGameInjectorService.getNbaGameBySeasonYear(season);
        List<NbaGameStatisticsDto> nbaGameStatisticsDtos = new ArrayList<>();
        for (NbaGameDto gameDto : nbaGameDtos) {
            GameStatsResponse response = nbaApiClient.getStatisticsPerGamesOldId(gameDto.oldId().toString());
            NbaGameStatisticsDto nbaGameStatisticsDto = nbaStatisticsInjectorService.injectTeamGameStatistic(gameDto.oldId(), response);
            nbaGameStatisticsDtos.add(nbaGameStatisticsDto);
        }

        return ResponseEntity.ok(nbaGameStatisticsDtos);
    }

    /*
    TEAM STATS
     */
    @Operation(summary = "Retrieve the team statistics per season from RapidApi and inject them to Database")
    @GetMapping("/team")
    public ResponseEntity<NbaTeamSeasonStatisticsDto> getAllTeamsStatisticsPerYearFromApi(long id, int season) {
        log.info("All Season Teams statistics injected by year: {}", season);

        TeamStatsResponse response = nbaApiClient.getStatisticsPerTeamAndPerYear(id, season);

        NbaTeamSeasonStatisticsDto nbaTeamSeasonStatisticsDto = nbaStatisticsInjectorService.injectSeasonTeamStatisticPerYear(season, response);

        return ResponseEntity.ok(nbaTeamSeasonStatisticsDto);
    }

    @Operation(summary = "Retrieve all season team statistics per season from RapidApi and inject them to Database")
    @GetMapping("/teams/season")
    public ResponseEntity<List<NbaTeamSeasonStatisticsDto>> getStatisticsPerTeamAndPerYearFromApi(int season) {
        log.info("Season Team statistics injected for all teams for season: {}", season);

        List<NbaTeamEntity> nbaTeamEntities = nbaTeamInjectorService.findAllTeams().stream().filter(NbaTeamEntity::getNbaFranchise).toList();
        List<NbaTeamSeasonStatisticsDto> statisticsDtos = new ArrayList<>();
        for (NbaTeamEntity nbaTeamEntity : nbaTeamEntities) {
            TeamStatsResponse response = nbaApiClient.getStatisticsPerTeamAndPerYear(nbaTeamEntity.getOldId(), season);
            NbaTeamSeasonStatisticsDto dto = nbaStatisticsInjectorService.injectAllSeasonTeamsStatisticPerYear(season, nbaTeamEntity, response);
            statisticsDtos.add(dto);
        }

        return ResponseEntity.ok(statisticsDtos);
    }

    /*
    PLAYER STATS
     */
    @Operation(summary = "Retrieve the player statistics per game from RapidApi and inject them to Database")
    @GetMapping("/player/game")
    public ResponseEntity<List<NbaPlayerGameStatisticsDto>> getPlayerGameStatisticsFromApi(long gameId) {
        log.info("Player statistics injected for game: {}", gameId);

        PlayerStatsPerGameResponse response = nbaApiClient.getPlayerGameStatisticsPerGameFromApi(gameId);

        List<NbaPlayerGameStatisticsDto> statisticsDtos = nbaStatisticsInjectorService.injectPlayerGameStatistic(response);

        return ResponseEntity.ok(statisticsDtos);
    }

    @Operation(summary = "Retrieve All player statistics per season from RapidApi and inject them to Database")
    @GetMapping("/player/game/season")
    public ResponseEntity<Map<Integer, List<NbaPlayerGameStatisticsDto>>> getPlayerGameStatisticsFromApi(int season) {
        Map<Integer, List<NbaPlayerGameStatisticsDto>> statisticsDtoMap = new HashMap<>();
        List<NbaGameDto> nbaGameDtos = nbaGameInjectorService.getNbaGameBySeasonYear(season);
        List<NbaPlayerGameStatisticsDto> statisticsDtos = List.of();
        for (NbaGameDto gameDto : nbaGameDtos) {
            PlayerStatsPerGameResponse response = nbaApiClient.getPlayerGameStatisticsPerGameFromApi(gameDto.oldId());
            statisticsDtos = nbaStatisticsInjectorService.injectPlayerGameStatistic(response);
        }
        statisticsDtoMap.put(season, statisticsDtos);

        return ResponseEntity.ok(statisticsDtoMap);
    }
}
