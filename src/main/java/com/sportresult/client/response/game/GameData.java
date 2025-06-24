package com.sportresult.client.response.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameData {
    private Long id;
    private String league;
    private Integer season;
    private DateInfo date;
    private String stage;
    private GameStatus status;
    private Arena arena;
    private GameTeams teams;
    private Scores scores;
    private List<String> officials;
    private Integer timesTied;
    private Integer leadChanges;
    private String nuggets;
}
