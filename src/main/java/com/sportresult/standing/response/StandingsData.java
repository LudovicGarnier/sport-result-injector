package com.sportresult.standing.response;

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
    private TeamStanding team;
    private ConferenceStanding conference;
    private DivisionStanding division;
    private WinStanding win;
    private LossStanding loss;
    private String gamesBehind;
    private Integer streak;
    private Boolean winStreak;
    private String tieBreakerPoints;
}
