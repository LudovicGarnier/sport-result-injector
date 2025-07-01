package com.sportresult.client.response.statistics.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sportresult.client.response.statistics.game.TeamData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerStatsPerGameData {
    private PlayerStatsData player;
    private TeamData team;
    private GameIdData game;
    private Integer points;
    private String pos;
    private String min;
    private Integer fgm;
    private Integer fga;
    private String fgp;
    private Integer ftm;
    private Integer fta;
    private String ftp;
    private Integer tpm;
    private Integer tpa;
    private String tpp;
    private Integer offReb;
    private Integer defReb;
    private Integer totReb;
    private Integer assists;
    private Integer pFouls;
    private Integer steals;
    private Integer turnovers;
    private Integer blocks;
    private String plusMinus;
    private String comment;
}

