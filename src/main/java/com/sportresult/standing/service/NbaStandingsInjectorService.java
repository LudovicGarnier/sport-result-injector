package com.sportresult.standing.service;

import com.sportresult.season.entity.NbaSeasonEntity;
import com.sportresult.season.service.NbaSeasonInjectorService;
import com.sportresult.standing.dto.NbaStandingsDto;
import com.sportresult.standing.entity.NbaStandingsEntity;
import com.sportresult.standing.repository.NbaStandingsInjectorRepository;
import com.sportresult.standing.response.NbaStandingsResponse;
import com.sportresult.standing.response.StandingsData;
import com.sportresult.team.entity.NbaTeamEntity;
import com.sportresult.team.service.NbaTeamInjectorService;
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
public class NbaStandingsInjectorService {

    private final NbaStandingsInjectorRepository nbaStandingsInjectorRepository;

    private final NbaTeamInjectorService nbaTeamInjectorService;

    private final NbaSeasonInjectorService nbaSeasonInjectorService;

    public List<NbaStandingsDto> injectStandings(NbaStandingsResponse response) {

        if (response == null || response.getResponse().isEmpty()) {
            log.info("No players to inject - empty response");
            return List.of();
        }

        List<StandingsData> standingsData = response.getResponse().stream().toList();

        /*
         *  Check if year already exists
         */
        Set<Integer> standingsIds = standingsData.stream().map(StandingsData::getSeason).collect(Collectors.toSet());
        Set<Integer> existingIds = new HashSet<>(nbaStandingsInjectorRepository.findBySeasonStandings(standingsIds));

        /*
         * Filter non-existing Ids to add new Standings
         */
        List<NbaStandingsEntity> newStandings = standingsData.stream()
                .filter(standings -> !existingIds.contains(standings.getSeason()))
                .map(this::createNbaStandingsEntity)
                .toList();

        if (newStandings.isEmpty()) {
            log.info("All standings already exist in database");
            return List.of();
        }

        nbaStandingsInjectorRepository.saveAll(newStandings);
        return newStandings.stream().map(NbaStandingsEntity::toDto).collect(Collectors.toList());
    }

    private NbaStandingsEntity createNbaStandingsEntity(StandingsData standingsData) {
        NbaSeasonEntity nbaSeason = nbaSeasonInjectorService.getNbaSeasonByYear(standingsData.getSeason());
        Optional<NbaTeamEntity> optionalNbaTeam = nbaTeamInjectorService.getTeamByOldId(standingsData.getTeam().getId());
        return NbaStandingsEntity.builder().seasonStandings(nbaSeason)
                .teamStandings(optionalNbaTeam.get())
                .conferenceName(standingsData.getConference().getName())
                .conferenceRank(standingsData.getConference().getRank())
                .conferenceWin(standingsData.getConference().getWin())
                .conferenceLoss(standingsData.getConference().getLoss())
                .divisionName(standingsData.getDivision().getName())
                .divisionRank(standingsData.getDivision().getRank())
                .divisionWin(standingsData.getDivision().getWin())
                .divisionLoss(standingsData.getDivision().getLoss())
                .winHome(standingsData.getWin().getHome())
                .winAway(standingsData.getWin().getAway())
                .winTotal(standingsData.getWin().getHome())
                .winPercentage(standingsData.getWin().getPercentage())
                .lastTenWin(standingsData.getWin().getLastTen())
                .lossHome(standingsData.getLoss().getHome())
                .lossAway(standingsData.getLoss().getAway())
                .lossTotal(standingsData.getLoss().getTotal())
                .lossPercentage(standingsData.getLoss().getPercentage())
                .lastTenLoss(standingsData.getLoss().getLastTen())
                .gameBehind(standingsData.getGamesBehind())
                .streak(standingsData.getStreak())
                .build();
    }
}
