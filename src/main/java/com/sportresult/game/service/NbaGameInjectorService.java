package com.sportresult.game.service;

import com.sportresult.arena.entity.NbaArenaEntity;
import com.sportresult.arena.service.NbaArenaInjectorService;
import com.sportresult.game.dto.NbaGameDto;
import com.sportresult.game.entity.NbaGameEntity;
import com.sportresult.game.repository.NbaGameInjectorRepository;
import com.sportresult.game.response.Arena;
import com.sportresult.game.response.GameData;
import com.sportresult.game.response.NbaGameResponse;
import com.sportresult.game.response.ParticipantTeam;
import com.sportresult.season.entity.NbaSeasonEntity;
import com.sportresult.season.service.NbaSeasonInjectorService;
import com.sportresult.team.entity.NbaTeamEntity;
import com.sportresult.team.service.NbaTeamInjectorService;
import com.sportresult.utils.Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NbaGameInjectorService {

    private final NbaGameInjectorRepository nbaGameInjectorRepository;

    private final NbaSeasonInjectorService nbaSeasonInjectorService;

    private final NbaTeamInjectorService nbaTeamInjectorService;

    private final NbaArenaInjectorService nbaArenaInjectorService;


    @Transactional
    public List<NbaGameDto> injectGames(NbaGameResponse response) {
        log.info("START - injectGames");

        if (response == null || response.getResponse().isEmpty()) {
            log.info("No Games to inject - empty response");
            return List.of();
        }

        List<GameData> games = response.getResponse().stream().toList();

        /*
         *  Check if oldIds already exists
         */
        Set<Long> gameIds = games.stream().map(GameData::getId).collect(Collectors.toSet());
        Set<Long> existingIds = new HashSet<>(nbaGameInjectorRepository.findByOldId(gameIds));

        /*
         * Filter non-existing Ids to add new Games
         */
        List<NbaGameEntity> newGames = games.stream()
                .filter(game -> !existingIds.contains(game.getId()))
                .map(this::createNbaGameEntity)
                .collect(Collectors.toList());

        if (newGames.isEmpty()) {
            log.info("All Games already exist in database");
            return List.of();
        }

        nbaGameInjectorRepository.saveAll(newGames);

        return newGames.stream().map(NbaGameEntity::toDto).collect(Collectors.toList());
    }

    @Transactional
    protected NbaGameEntity createNbaGameEntity(GameData gameData) {
        log.info("START - createNbaGameEntity: {}", gameData);

        NbaArenaEntity nbaArenaEntity;
        NbaTeamEntity homeTeamEntity;
        NbaTeamEntity visitorTeamEntity;

        nbaArenaEntity = checkIfArenaExist(gameData.getArena());

        /*
        Check if the teams exist and add them otherwise
         */
        homeTeamEntity = checkIfTeamExist(gameData.getTeams().getHome());
        visitorTeamEntity = checkIfTeamExist(gameData.getTeams().getVisitors());

        NbaSeasonEntity nbaSeason = nbaSeasonInjectorService.getNbaSeasonByYear(gameData.getSeason());

        return NbaGameEntity.builder()
                .oldId(gameData.getId())
                .season(nbaSeason)
                .gameStart(Utils.convertToLocalDateTime(gameData.getDate().getStart()))
                .gameEnd(Utils.convertToLocalDateTime(gameData.getDate().getEnd()))
                .duration(gameData.getDate().getDuration())
                .stage(Integer.valueOf(gameData.getStage()))
                .status(gameData.getStatus().getStatus())
                .arena(nbaArenaEntity)
                .visitor(visitorTeamEntity)
                .home(homeTeamEntity)
                .timesTied(gameData.getTimesTied())
                .leadChanges(gameData.getLeadChanges())
                .officials(Set.of(String.valueOf(gameData.getOfficials())))
                .homeScore(gameData.getScores().getHome().getPoints())
                .visitorScore(gameData.getScores().getVisitors().getPoints())
                .homeWin(gameData.getScores().getHome().getWin())
                //.homeScoreLine(gameData.getScores().getHome().getLinescore())
                //.visitorScoreLine(gameData.getScores().getVisitors().getLinescore())
                .homeLoss(gameData.getScores().getHome().getLoss())
                .visitorWin(gameData.getScores().getVisitors().getWin())
                .visitorLoss(gameData.getScores().getVisitors().getLoss())
                .build();
    }

    private NbaTeamEntity checkIfTeamExist(ParticipantTeam participantTeam) {
        log.info("START - checkIfTeamExist: {}", participantTeam);
        NbaTeamEntity nbaTeamEntity;
        Optional<NbaTeamEntity> optionalHomeTeamEntity = nbaTeamInjectorService.getTeamByOldId(participantTeam.getId());
        nbaTeamEntity = optionalHomeTeamEntity.orElseGet(() -> saveNewTeam(participantTeam));
        return nbaTeamEntity;
    }

    /*
    Check if Arena exist in Database and create it in Db otherwise
     */
    private NbaArenaEntity checkIfArenaExist(Arena arena) {
        log.info("START - checkIfArenaExist: {}", arena);
        Optional<NbaArenaEntity> optionalNbaArenaEntity = nbaArenaInjectorService.getNbaArenaEntitiesByName(arena.getName());
        return optionalNbaArenaEntity.orElseGet(() -> saveNewArena(arena));
    }

    private NbaTeamEntity saveNewTeam(ParticipantTeam team) {
        log.info("START - saveNewTeam: {}", team);
        return nbaTeamInjectorService.save(NbaTeamEntity.builder()
                .oldId(team.getId())
                .name(team.getNickname())
                .code(team.getCode())
                .nickname(team.getNickname())
                .nbaFranchise(false)
                .allStar(false)
                .build());
    }

    private NbaArenaEntity saveNewArena(Arena arena) {
        log.info("START - saveNewArena: {}", arena);
        return nbaArenaInjectorService.save(NbaArenaEntity.builder().name(arena.getName())
                .city(arena.getCity())
                .state(arena.getState())
                .country(arena.getCountry())
                .build());
    }

}
