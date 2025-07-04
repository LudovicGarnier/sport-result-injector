package com.sportresult.player.service;

import com.sportresult.client.NbaApiClient;
import com.sportresult.client.response.player.NbaPlayerResponse;
import com.sportresult.client.response.player.PlayerData;
import com.sportresult.client.response.player.PlayerLeaguesData;
import com.sportresult.client.response.player.PlayerLeaguesInfo;
import com.sportresult.player.dto.NbaPlayerDto;
import com.sportresult.player.entity.NbaPlayerEntity;
import com.sportresult.player.repository.NbaPlayerInjectorRepository;
import com.sportresult.team.dto.NbaTeamDto;
import com.sportresult.team.service.NbaTeamInjectorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NbaPlayerInjectorService {

    private final NbaPlayerInjectorRepository nbaPlayerInjectorRepository;

    private final NbaTeamInjectorService nbaTeamInjectorService;

    @Transactional
    public Map<Integer, List<List<NbaPlayerDto>>> injectAllPlayerPerYear(NbaApiClient nbaApiClient, int year) {
        Map<Integer, List<List<NbaPlayerDto>>> players = new HashMap<>();
        List<NbaTeamDto> nbaTeamDtos = nbaTeamInjectorService.findAllTeamsDto();
        List<List<NbaPlayerDto>> teams = new ArrayList<>();

        for (NbaTeamDto nbaTeam : nbaTeamDtos) {
            if (nbaTeam.oldId() != null) {
                NbaPlayerResponse response = nbaApiClient.getPlayersPerTeamAndSeason(Long.toString(nbaTeam.oldId()), Integer.toString(year));
                List<NbaPlayerDto> playerDtos = injectPlayers(response);
                teams.add(playerDtos);
            }
        }
        players.put(year, teams);
        return players;
    }

    @Transactional
    public List<NbaPlayerDto> injectPlayers(NbaPlayerResponse response) {

        if (response == null || response.getResponse().isEmpty()) {
            log.info("No Players to inject - empty response");
            return List.of();
        }

        List<PlayerData> validPlayers = response.getResponse().stream().toList();

        /*
         *  Check if oldIds already exists
         */
        Set<Long> playerIds = validPlayers.stream().map(PlayerData::getId).collect(Collectors.toSet());
        Set<Long> existingIds = new HashSet<>(nbaPlayerInjectorRepository.findByOldId(playerIds));

        /*
         * Filter non-existing Ids to add new Players
         */
        List<NbaPlayerEntity> newPlayers = validPlayers.stream()
                .filter(player -> !existingIds.contains(player.getId()))
                .map(this::createNbaPlayerEntity)
                .toList();

        if (newPlayers.isEmpty()) {
            log.error("All players already exist in database");
            return List.of();
        }

        log.info("START - injecting Players");
        nbaPlayerInjectorRepository.saveAll(newPlayers);

        return newPlayers.stream().map(NbaPlayerEntity::toDto).collect(Collectors.toList());
    }

    @Transactional
    public NbaPlayerEntity save(NbaPlayerEntity nbaPlayerEntity) {
        return nbaPlayerInjectorRepository.saveAndFlush(nbaPlayerEntity);
    }

    public Optional<NbaPlayerEntity> getPlayerByOldId(Long oldId) {
        return nbaPlayerInjectorRepository.findByOldId(oldId);
    }

    private static void buildPlayerLeagueData(PlayerData playerData) {
        if (playerData.getLeagues() == null) {
            playerData.setLeagues(PlayerLeaguesData.builder().build());
        }
    }

    private static void buildPlayerLeagueInfo(PlayerData playerData) {
        if (playerData.getLeagues().getStandard() == null) {
            playerData.getLeagues().setStandard(PlayerLeaguesInfo.builder().build());
        }
    }

    private LocalDate buildBirthDate(PlayerData playerData) {
        LocalDate date;
        if (playerData.getBirth().getDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = LocalDate.parse(playerData.getBirth().getDate(), formatter);
            return date;
        }
        return null;
    }

    private NbaPlayerEntity createNbaPlayerEntity(PlayerData playerData) {

        buildPlayerLeagueData(playerData);
        buildPlayerLeagueInfo(playerData);

        return NbaPlayerEntity.builder()
                .oldId(playerData.getId())
                .firstname(playerData.getFirstname())
                .lastname(playerData.getLastname())
                .birthDate(buildBirthDate(playerData))
                .country(playerData.getBirth().getCountry())
                .startYear(playerData.getNba().getStart())
                .proYear(playerData.getNba().getPro())
                .height(playerData.getHeight().getMeters())
                .weight(playerData.getWeight().getKilograms())
                .college(playerData.getCollege())
                .affiliation(playerData.getAffiliation())
                .jerseyNumber(playerData.getLeagues().getStandard().getJersey())
                .isActive(playerData.getLeagues().getStandard().isActive())
                .position(playerData.getLeagues().getStandard().getPos())
                .build();
    }
}
