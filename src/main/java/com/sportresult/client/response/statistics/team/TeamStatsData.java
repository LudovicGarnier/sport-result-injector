package com.sportresult.client.response.statistics.team;

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
public class TeamStatsData {
    private Integer games;
    private Integer fastBreakPoints;
    private Integer pointsInPaint;
    private Integer biggestLead;
    private Integer secondChancePoints;
    private Integer pointsOffTurnovers;
    private Integer longestRun;
    private Integer points;
    private Integer fgm;
    private Integer fga;
    private Double fgp;
    private Integer ftm;
    private Integer fta;
    private Double ftp;
    private Integer tpm;
    private Integer tpa;
    private Double tpp;
    private Integer offReb;
    private Integer defReb;
    private Integer totReb;
    private Integer assists;
    private Integer pFouls;
    private Integer steals;
    private Integer turnovers;
    private Integer blocks;
    private Integer plusMinus;
}
