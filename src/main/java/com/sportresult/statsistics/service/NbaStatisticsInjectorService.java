package com.sportresult.statsistics.service;

import com.sportresult.client.response.statistics.game.GameStatsData;
import com.sportresult.client.response.statistics.game.GameStatsResponse;
import com.sportresult.client.response.statistics.game.TeamStatsInGameData;
import com.sportresult.client.response.statistics.player.PlayerStatsPerGameData;
import com.sportresult.client.response.statistics.player.PlayerStatsPerGameResponse;
import com.sportresult.client.response.statistics.team.TeamStatsData;
import com.sportresult.client.response.statistics.team.TeamStatsResponse;
import com.sportresult.game.dto.NbaGameDto;
import com.sportresult.game.entity.NbaGameEntity;
import com.sportresult.game.service.NbaGameInjectorService;
import com.sportresult.player.entity.NbaPlayerEntity;
import com.sportresult.player.service.NbaPlayerInjectorService;
import com.sportresult.season.entity.NbaSeasonEntity;
import com.sportresult.season.service.NbaSeasonInjectorService;
import com.sportresult.statsistics.dto.NbaGameStatisticsDto;
import com.sportresult.statsistics.dto.NbaPlayerGameStatisticsDto;
import com.sportresult.statsistics.dto.NbaTeamSeasonStatisticsDto;
import com.sportresult.statsistics.entity.NbaGameStatisticsEntity;
import com.sportresult.statsistics.entity.NbaPlayerGameStatisticsEntity;
import com.sportresult.statsistics.entity.NbaTeamGameStatisticsEntity;
import com.sportresult.statsistics.entity.NbaTeamSeasonStatisticsEntity;
import com.sportresult.statsistics.repository.NbaGameStatisticsInjectorRepository;
import com.sportresult.statsistics.repository.NbaPlayerGameStatisticsInjectorRepository;
import com.sportresult.statsistics.repository.NbaTeamGameStatisticsInjectorRepository;
import com.sportresult.statsistics.repository.NbaTeamSeasonStatisticsInjectorRepository;
import com.sportresult.team.entity.NbaTeamEntity;
import com.sportresult.team.service.NbaTeamInjectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NbaStatisticsInjectorService {

    private final NbaGameStatisticsInjectorRepository nbaGameStatisticsInjectorRepository;

    private final NbaTeamGameStatisticsInjectorRepository nbaTeamGameStatisticsInjectorRepository;

    private final NbaTeamSeasonStatisticsInjectorRepository nbaTeamSeasonStatisticsInjectorRepository;

    private final NbaPlayerGameStatisticsInjectorRepository nbaPlayerGameStatisticsInjectorRepository;

    private final NbaGameInjectorService nbaGameInjectorService;

    private final NbaTeamInjectorService nbaTeamInjectorService;

    private final NbaSeasonInjectorService nbaSeasonInjectorService;

    private final NbaPlayerInjectorService nbaPlayerInjectorService;

    /**
     * PLAYER STATS
     *
     * @param response
     * @return List<NbaPlayerGameStatisticsDto>
     */
    public List<NbaPlayerGameStatisticsDto> injectPlayerGameStatistic(PlayerStatsPerGameResponse response) {
        log.info("START - injectPlayerGameStatistic");
        List<PlayerStatsPerGameData> playerStatsPerGameData = response.getResponse().stream().toList();

        List<NbaPlayerGameStatisticsEntity> entities = new ArrayList<>();

        for (PlayerStatsPerGameData playerStats : playerStatsPerGameData) {
            NbaPlayerGameStatisticsEntity nbaPlayerGameStatisticsEntity = createNbaPlayerGameStatisticsEntity(playerStats);
            entities.add(nbaPlayerGameStatisticsEntity);
        }

        log.info("START - injecting Players Game statistics");
        nbaPlayerGameStatisticsInjectorRepository.saveAll(entities);

        return entities.stream().map(NbaPlayerGameStatisticsEntity::toDto).collect(Collectors.toList());
    }


    private NbaPlayerGameStatisticsEntity createNbaPlayerGameStatisticsEntity(PlayerStatsPerGameData playerStats) {
        log.info("START - createNbaPlayerGameStatisticsEntity");
        NbaPlayerEntity nbaPlayer;
        String id = playerStats.getPlayer().getId();
        if (id == null) {
            nbaPlayer = NbaPlayerEntity.builder().build();
        } else {
            Optional<NbaPlayerEntity> optionalNbaPlayer = nbaPlayerInjectorService.getPlayerByOldId(Long.valueOf(playerStats.getPlayer().getId()));
            nbaPlayer = optionalNbaPlayer.orElseGet(() -> NbaPlayerEntity.builder().build());
        }
        NbaTeamEntity nbaTeam = nbaTeamInjectorService.getTeamByOldId(playerStats.getTeam().getId()).get();
        NbaGameEntity nbaGame = nbaGameInjectorService.getNbaGameByOldId(playerStats.getGame().getId());

        return createNbaPlayerGameStatisticsEntity(playerStats, nbaPlayer, nbaTeam, nbaGame);
    }


    /**
     * GAME STATS
     *
     * @param nbaGameEntityOldId
     * @param response
     * @return
     */
    public NbaGameStatisticsDto injectTeamGameStatistic(Long nbaGameEntityOldId, GameStatsResponse response) {
        log.info("START - injectTeamGameStatistic");
        if (response == null || response.getResponse().isEmpty()) {
            log.error("No game statistic to inject - empty response");
            return NbaGameStatisticsDto.builder().build();
        }

        List<GameStatsData> gameStatsDatas = response.getResponse().stream().toList();

        /*
        Get the Game and the 2 teams
         */
        NbaGameEntity nbaGameEntity = nbaGameInjectorService.getNbaGameByOldId(nbaGameEntityOldId);
        NbaTeamEntity homeNbaTeam = nbaTeamInjectorService.getTeamByOldId(nbaGameEntity.getHome().getOldId()).orElseGet(NbaTeamEntity::new);
        NbaTeamEntity visitorNbaTeam = nbaTeamInjectorService.getTeamByOldId(nbaGameEntity.getVisitor().getOldId()).orElseGet(NbaTeamEntity::new);

        /*
        VISITOR STATS
         */
        TeamStatsInGameData visitorStatistics = gameStatsDatas.getFirst().getStatistics().getFirst();
        NbaTeamGameStatisticsEntity visitorNbaTeamGameStatisticsEntity = buildNbaTeamGameStatisticEntity(nbaGameEntity, visitorNbaTeam, visitorStatistics);
        nbaTeamGameStatisticsInjectorRepository.save(visitorNbaTeamGameStatisticsEntity);
        /*
        HOME STATS
         */
        TeamStatsInGameData homeStatistics = gameStatsDatas.getLast().getStatistics().getFirst();
        NbaTeamGameStatisticsEntity homeNbaTeamGameStatisticsEntity = buildNbaTeamGameStatisticEntity(nbaGameEntity, homeNbaTeam, homeStatistics);
        nbaTeamGameStatisticsInjectorRepository.save(homeNbaTeamGameStatisticsEntity);

        NbaGameStatisticsEntity nbaGameStatisticsEntity = NbaGameStatisticsEntity.builder()
                .nbaGameEntity(nbaGameEntity)
                .homeTeamStatisticsEntity(homeNbaTeamGameStatisticsEntity)
                .visitorTeamStatisticsEntity(visitorNbaTeamGameStatisticsEntity)
                .build();

        nbaGameStatisticsInjectorRepository.saveAndFlush(nbaGameStatisticsEntity);

        return nbaGameStatisticsEntity.toDto();
    }

    public List<NbaGameStatisticsDto> injectAllTeamGameStatisticPerYear(Integer year, GameStatsResponse response) {
        List<NbaGameDto> yearsGame = nbaGameInjectorService.getNbaGameBySeasonYear(year);
        if (yearsGame == null || yearsGame.isEmpty()) {
            log.error("No statistic to inject - empty response");
            return List.of();
        }

        List<NbaGameStatisticsDto> nbaGameStatisticsDtos = new ArrayList<>();

        for (NbaGameDto nbaGameDto : yearsGame) {
            NbaGameStatisticsDto nbaGameStatisticsDto = this.injectTeamGameStatistic(nbaGameDto.oldId(), response);
            nbaGameStatisticsDtos.add(nbaGameStatisticsDto);
        }

        return nbaGameStatisticsDtos;
    }

    /**
     * TEAMS SEASON STATS
     *
     * @param year
     * @param response
     * @return
     */
    public NbaTeamSeasonStatisticsDto injectAllSeasonTeamsStatisticPerYear(int year, NbaTeamEntity nbaTeamEntity, TeamStatsResponse response) {
        log.info("START - injectAllTeamsStatisticPerYear: {}", year);
        TeamStatsData teamStatsData = response.getResponse().stream().toList().getFirst();

        NbaSeasonEntity nbaSeason = nbaSeasonInjectorService.getNbaSeasonByYear(year);
        NbaTeamSeasonStatisticsEntity nbaTeamGameStatisticsEntity = createNbaTeamSeasonStatisticsEntity(nbaTeamEntity, nbaSeason, teamStatsData);

        log.info("START - injecting AllTeamsStatisticPerYear");
        nbaTeamSeasonStatisticsInjectorRepository.saveAndFlush(nbaTeamGameStatisticsEntity);

        return nbaTeamGameStatisticsEntity.toDto();
    }

    public NbaTeamSeasonStatisticsDto injectSeasonTeamStatisticPerYear(int year, TeamStatsResponse response) {
        log.info("START - injectTeamStatisticPerYear: {}", year);
        TeamStatsData teamStatsData = response.getResponse().stream().toList().getFirst();

        // get existing season by year
        NbaSeasonEntity nbaSeason = nbaSeasonInjectorService.getNbaSeasonByYear(year);
        // get all existing teams
        List<NbaTeamEntity> nbaTeamEntities = nbaTeamInjectorService.findAllTeams();
        NbaTeamSeasonStatisticsEntity nbaTeamSeasonStatisticsEntity = new NbaTeamSeasonStatisticsEntity();
        for (NbaTeamEntity nbaTeamEntity : nbaTeamEntities) {
            nbaTeamSeasonStatisticsEntity = createNbaTeamSeasonStatisticsEntity(nbaTeamEntity, nbaSeason, teamStatsData);
        }

        log.info("START -  injecting AllTeamsStatisticPerYear");
        nbaTeamSeasonStatisticsInjectorRepository.saveAndFlush(nbaTeamSeasonStatisticsEntity);

        return nbaTeamSeasonStatisticsEntity.toDto();
    }


    private static NbaTeamSeasonStatisticsEntity createNbaTeamSeasonStatisticsEntity(NbaTeamEntity nbaTeamEntity,
                                                                                     NbaSeasonEntity nbaSeason,
                                                                                     TeamStatsData teamStatsData) {
        return NbaTeamSeasonStatisticsEntity.builder()
                .team(nbaTeamEntity)
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
                .fgp(teamStatsData.getFgp())
                .fga(teamStatsData.getFga())
                .ftm(teamStatsData.getFtm())
                .fta(teamStatsData.getFta())
                .ftp(teamStatsData.getFtp())
                .tpm(teamStatsData.getTpm())
                .tpa(teamStatsData.getTpa())
                .tpp(teamStatsData.getTpp())
                .offReb(teamStatsData.getOffReb())
                .defReb(teamStatsData.getDefReb())
                .totReb(teamStatsData.getTotReb())
                .assists(teamStatsData.getAssists())
                .pFouls(teamStatsData.getPFouls())
                .steals(teamStatsData.getSteals())
                .turnovers(teamStatsData.getTurnovers())
                .blocks(teamStatsData.getBlocks())
                .plusMinus(teamStatsData.getPlusMinus())
                .build();
    }

    private NbaTeamGameStatisticsEntity buildNbaTeamGameStatisticEntity(NbaGameEntity nbaGame,
                                                                        NbaTeamEntity nbaTeam,
                                                                        TeamStatsInGameData teamStatsInGameData) {
        return NbaTeamGameStatisticsEntity.builder()
                .team(nbaTeam)
                .game(nbaGame)
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

    private static NbaPlayerGameStatisticsEntity createNbaPlayerGameStatisticsEntity(PlayerStatsPerGameData playerStats,
                                                                                     NbaPlayerEntity nbaPlayer,
                                                                                     NbaTeamEntity nbaTeam,
                                                                                     NbaGameEntity nbaGame) {
        return NbaPlayerGameStatisticsEntity.builder()
                .player(nbaPlayer)
                .nbaTeam(nbaTeam)
                .game(nbaGame)
                .points(playerStats.getPoints())
                .pos(playerStats.getPos())
                .min(playerStats.getMin())
                .fgm(playerStats.getFgm())
                .fga(playerStats.getFga())
                .fgp(playerStats.getFgp())
                .fta(playerStats.getFta())
                .ftp(playerStats.getFtp())
                .ftm(playerStats.getFtm())
                .tpa(playerStats.getTpa())
                .tpp(playerStats.getTpp())
                .offReb(playerStats.getOffReb())
                .defReb(playerStats.getDefReb())
                .totReb(playerStats.getTotReb())
                .assists(playerStats.getAssists())
                .pFouls(playerStats.getPFouls())
                .steals(playerStats.getSteals())
                .turnovers(playerStats.getTurnovers())
                .blocks(playerStats.getBlocks())
                .plusMinus(playerStats.getPlusMinus())
                .comment(playerStats.getComment())
                .build();
    }
}
