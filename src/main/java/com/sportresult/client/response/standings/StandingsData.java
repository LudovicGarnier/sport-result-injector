package com.sportresult.client.response.standings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StandingsData {

    private String league;
    private Integer season;
    private TeamStandings team;
    private ConferenceStandings conference;
    private DivisionStandings division;
    private WinStandings win;
    private LossStandings loss;
    private String gamesBehind;
    private Integer streak;
    private Boolean winStreak;
    private String tieBreakerPoints;
}
