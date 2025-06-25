package com.sportresult.statsistics.service;

import com.sportresult.client.response.statistics.game.GameStatsData;
import com.sportresult.client.response.statistics.game.GameStatsResponse;
import com.sportresult.client.response.statistics.game.TeamStatsInGameData;
import com.sportresult.client.response.statistics.team.TeamStatsData;
import com.sportresult.client.response.statistics.team.TeamStatsResponse;
import com.sportresult.game.dto.NbaGameDto;
import com.sportresult.game.entity.NbaGameEntity;
import com.sportresult.game.service.NbaGameInjectorService;
import com.sportresult.season.entity.NbaSeasonEntity;
import com.sportresult.season.service.NbaSeasonInjectorService;
import com.sportresult.statsistics.dto.NbaGameStatisticsDto;
import com.sportresult.statsistics.dto.NbaTeamSeasonStatisticsDto;
import com.sportresult.statsistics.entity.NbaGameStatisticsEntity;
import com.sportresult.statsistics.entity.NbaTeamGameStatisticsEntity;
import com.sportresult.statsistics.entity.NbaTeamSeasonStatisticsEntity;
import com.sportresult.statsistics.repository.NbaGameStatisticInjectorRepository;
import com.sportresult.statsistics.repository.NbaTeamGameStatisticInjectorRepository;
import com.sportresult.statsistics.repository.NbaTeamSeasonStatisticInjectorRepository;
import com.sportresult.team.entity.NbaTeamEntity;
import com.sportresult.team.service.NbaTeamInjectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NbaStatisticInjectorService {

    private final NbaGameStatisticInjectorRepository nbaGameStatisticInjectorRepository;

    private final NbaTeamGameStatisticInjectorRepository nbaTeamGameStatisticInjectorRepository;

    private final NbaTeamSeasonStatisticInjectorRepository nbaTeamSeasonStatisticInjectorRepository;

    private final NbaGameInjectorService nbaGameInjectorService;

    private final NbaTeamInjectorService nbaTeamInjectorService;

    private final NbaSeasonInjectorService nbaSeasonInjectorService;

    public NbaGameStatisticsDto injectTeamGameStatistic(Long nbaGameEntityOldId, GameStatsResponse response) {
        if (response == null || response.getResponse().isEmpty()) {
            log.error("No game statistic to inject - empty response");
            return NbaGameStatisticsDto.builder().build();
        }

        List<GameStatsData> gameStatsDatas = response.getResponse().stream().toList();

        /*
        Get the Game and the 2 teams
         */
        NbaGameEntity nbaGameEntity = nbaGameInjectorService.getNbaGameDtoByOldId(nbaGameEntityOldId);
        NbaTeamEntity homeNbaTeam = nbaTeamInjectorService.getTeamByOldId(nbaGameEntity.getHome().getOldId()).orElseGet(NbaTeamEntity::new);
        NbaTeamEntity visitorNbaTeam = nbaTeamInjectorService.getTeamByOldId(nbaGameEntity.getVisitor().getOldId()).orElseGet(NbaTeamEntity::new);

        /*
        VISITOR STATS
         */
        TeamStatsInGameData visitorStatistics = gameStatsDatas.getFirst().getStatistics().getFirst();
        NbaTeamGameStatisticsEntity visitorNbaTeamGameStatisticsEntity = buildNbaTeamGameStatisticEntity(visitorNbaTeam, visitorStatistics);
        nbaTeamGameStatisticInjectorRepository.save(visitorNbaTeamGameStatisticsEntity);
        /*
        HOME STATS
         */
        TeamStatsInGameData homeStatistics = gameStatsDatas.getLast().getStatistics().getFirst();
        NbaTeamGameStatisticsEntity homeNbaTeamGameStatisticsEntity = buildNbaTeamGameStatisticEntity(homeNbaTeam, homeStatistics);
        nbaTeamGameStatisticInjectorRepository.save(homeNbaTeamGameStatisticsEntity);

        NbaGameStatisticsEntity nbaGameStatisticsEntity = NbaGameStatisticsEntity.builder()
                .nbaGameEntity(nbaGameEntity)
                .homeTeamStatisticsEntity(homeNbaTeamGameStatisticsEntity)
                .visitorTeamStatisticsEntity(visitorNbaTeamGameStatisticsEntity).build();

        nbaGameStatisticInjectorRepository.saveAndFlush(nbaGameStatisticsEntity);

        return nbaGameStatisticsEntity.toDto();
    }

    private NbaTeamGameStatisticsEntity buildNbaTeamGameStatisticEntity(NbaTeamEntity nbaTeam, TeamStatsInGameData teamStatsInGameData) {
        return NbaTeamGameStatisticsEntity.builder()
                .team(nbaTeam)
                .fga(teamStatsInGameData.getFga())
                .fgm(teamStatsInGameData.getFgm())
                .fgp(teamStatsInGameData.getFgp())
                .fta(teamStatsInGameData.getFta())
                .ftm(teamStatsInGameData.getFtm())
                .ftp(teamStatsInGameData.getFtp())
                .min(teamStatsInGameData.getMin())
                .assists(teamStatsInGameData.getAssists())
                .biggestLead(teamStatsInGameData.getBiggestLead())
                .fastBreakPoints(teamStatsInGameData.getFastBreakPoints())
                .blocks(teamStatsInGameData.getBlocks())
                .defReb(teamStatsInGameData.getDefReb())
                .offReb(teamStatsInGameData.getOffReb())
                .tpa(teamStatsInGameData.getTpa())
                .tpm(teamStatsInGameData.getTpm())
                .tpp(teamStatsInGameData.getTpp())
                .pFouls(teamStatsInGameData.getPFouls())
                .longestRun(teamStatsInGameData.getLongestRun())
                .steals(teamStatsInGameData.getSteals())
                .totReb(teamStatsInGameData.getTotReb())
                .secondChancePoints(teamStatsInGameData.getSecondChancePoints())
                .turnovers(teamStatsInGameData.getTurnovers())
                .plusMinus(teamStatsInGameData.getPlusMinus())
                .pointsInPaint(teamStatsInGameData.getPointsInPaint())
                .pointsOffTurnovers(teamStatsInGameData.getPointsOffTurnovers())
                .points(teamStatsInGameData.getPoints())
                .build();
    }

    public List<NbaGameStatisticsDto> injectAllTeamGameStatisticPerYear(Integer year, GameStatsResponse response) {
        List<NbaGameDto> yearsGame = nbaGameInjectorService.getNbaGameBySeasonYear(year);
        if (yearsGame == null || yearsGame.isEmpty()) {
            log.error("No game statistic to inject - empty response");
            return List.of();
        }

        List<NbaGameStatisticsDto> nbaGameStatisticsDtos = new ArrayList<>();

        for (NbaGameDto nbaGameDto : yearsGame) {
            NbaGameStatisticsDto nbaGameStatisticsDto = this.injectTeamGameStatistic(nbaGameDto.oldId(), response);
            nbaGameStatisticsDtos.add(nbaGameStatisticsDto);
        }

        return nbaGameStatisticsDtos;
    }

    public NbaTeamSeasonStatisticsDto injectTeamStatisticPerYear(long oldId, int year, TeamStatsResponse response) {
        log.info("Team stats injected by year: {}", year);
        TeamStatsData teamStatsData = response.getResponse().stream().toList().getFirst();

        Optional<NbaTeamEntity> nbaTeamEntity = nbaTeamInjectorService.getTeamByOldId(oldId);
        NbaSeasonEntity nbaSeason = nbaSeasonInjectorService.getNbaSeasonByYear(year);
        NbaTeamSeasonStatisticsEntity nbaTeamGameStatisticsEntity = createNbaTeamSeasonStatisticsEntity(nbaTeamEntity, nbaSeason, teamStatsData);

        nbaTeamSeasonStatisticInjectorRepository.saveAndFlush(nbaTeamGameStatisticsEntity);

        return nbaTeamGameStatisticsEntity.toDto();
    }

    private static NbaTeamSeasonStatisticsEntity createNbaTeamSeasonStatisticsEntity(Optional<NbaTeamEntity> nbaTeamEntity,
                                                                                     NbaSeasonEntity nbaSeason,
                                                                                     TeamStatsData teamStatsData) {
        return NbaTeamSeasonStatisticsEntity.builder()
                .team(nbaTeamEntity.orElse(NbaTeamEntity.builder().build()))
                .season(nbaSeason)
                .games(teamStatsData.getGames())
                .fastBreakPoints(teamStatsData.getFastBreakPoints())
                .pointsInPaint(teamStatsData.getPointsInPaint())
                .biggestLead(teamStatsData.getBiggestLead())
                .secondChancePoints(teamStatsData.getSecondChancePoints())
                .pointsOffTurnovers(teamStatsData.getPointsOffTurnovers())
                .longestRun(teamStatsData.getLongestRun())
                .points(teamStatsData.getPoints())
                .fgm(teamStatsData.getFgm())
                .fga(teamStatsData.getFga())
                .ftm(teamStatsData.getFtm())
                .fta(teamStatsData.getFta())
                .ftp(teamStatsData.getFtp())
                .tpm(teamStatsData.getTpm())
                .tpa(teamStatsData.getTpa())
                .tpp(teamStatsData.getTpp())
                .offReb(teamStatsData.getOffReb())
                .defReb(teamStatsData.getDefReb())
                .assists(teamStatsData.getAssists())
                .pFouls(teamStatsData.getPFouls())
                .steals(teamStatsData.getSteals())
                .turnovers(teamStatsData.getTurnovers())
                .blocks(teamStatsData.getBlocks())
                .plusMinus(teamStatsData.getPlusMinus())
                .build();
    }
}
