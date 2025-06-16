package com.sportresult.team.service;

import com.sportresult.team.dto.NbaTeamDto;
import com.sportresult.team.entity.NbaTeamEntity;
import com.sportresult.team.repository.NbaTeamInjectorRepository;
import com.sportresult.team.response.LeagueInfo;
import com.sportresult.team.response.LeaguesData;
import com.sportresult.team.response.NbaTeamResponse;
import com.sportresult.team.response.TeamData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NbaTeamInjectorService {

    private final NbaTeamInjectorRepository nbaTeamInjectorRepository;

    @Transactional
    public List<NbaTeamDto> injectTeams(NbaTeamResponse response) {

        if (response == null || response.getResponse().isEmpty()) {
            log.info("No teams to inject - empty response");
            return List.of();
        }

        List<TeamData> validTeams = response.getResponse().stream()
                .filter(this::shouldProcessTeam)
                .toList();

        if (validTeams.isEmpty()) {
            log.info("No valid NBA franchise teams found");
            return List.of();
        }

        /*
         *  Check if oldIds already exists
         */
        Set<Long> teamIds = validTeams.stream().map(TeamData::getId).collect(Collectors.toSet());
        Set<Long> existingIds = new HashSet<>(nbaTeamInjectorRepository.findByOldId(teamIds));

        /*
         * Filter non-existing Ids to add new Teams
         */
        List<NbaTeamEntity> newTeams = validTeams.stream()
                .filter(team -> !existingIds.contains(team.getId()))
                .map(this::createNbaTeamEntity)
                .collect(Collectors.toList());

        if (newTeams.isEmpty()) {
            log.info("All teams already exist in database");
            return List.of();
        }

        nbaTeamInjectorRepository.saveAll(newTeams);

        return newTeams.stream().map(NbaTeamEntity::toDto).collect(Collectors.toList());
    }

    private NbaTeamEntity createNbaTeamEntity(TeamData teamData) {
        LeaguesData leaguesData = teamData.getLeagues();
        if (teamData.getLeagues() == null) {
            leaguesData = LeaguesData.builder().leagueInfo(LeagueInfo.builder().build()).build();
        }
        return NbaTeamEntity.builder()
                .oldId(teamData.getId())
                .name(teamData.getName())
                .nickname(teamData.getNickname())
                .code(teamData.getCode())
                .city(teamData.getCity())
                .logo(teamData.getLogo())
                .allStar(teamData.getAllStar())
                .nbaFranchise(teamData.getNbaFranchise())
                .conference(leaguesData.getLeagueInfo().getConference())
                .division(leaguesData.getLeagueInfo().getDivision())
                .build();
    }

    /**
     * Only Nba Teams and no All Star are validated
     */
    private boolean shouldProcessTeam(TeamData teamData) {
        return teamData.getNbaFranchise() && (!teamData.getAllStar());
    }

}
